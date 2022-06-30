package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import Model.Datos;

public class DetallesRecycler extends AppCompatActivity {

    FirebaseFirestore db;
    TextView recfecha,recubi,recobs;
    ImageView img;

    //navegacion
    BottomNavigationView navegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_recycler);


        //navegacion
        navegacion=findViewById(R.id.botton);
        navegacion.setSelectedItemId(R.id.emergencia);
        navegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.emergencia:
                        startActivity(new Intent(getApplicationContext(),Inicio.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.incidente:
                        startActivity(new Intent(getApplicationContext(),Incidente.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.perfil:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        recfecha= findViewById(R.id.detFecha);
        recubi= findViewById(R.id.detLatitud);
        recobs= findViewById(R.id.detObservacion);
        img= findViewById(R.id.detImg);




        /*db= FirebaseFirestore.getInstance();
        db.collection("Reportes").*//*whereGreaterThanOrEqualTo("idauto",1).*//*orderBy("tiempo", Query.Direction.DESCENDING).limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //Toast.makeText(RecyclerActivity.this, ""+document.getData(), Toast.LENGTH_SHORT).show();
                                //String url = document.getData().toString();
                                Date tiempo= document.getDate("tiempo");
                                String Cadenaurl = document.getString("url");
                                String Cadenaobs = document.getString("observaciones");
                                String Cadenaubi = document.getString("ubicacion");

                                //String sSubCadena1 = Cadenaurl.substring(5);
                                //Toast.makeText(RecyclerActivity.this, ""+ sSubCadena, Toast.LENGTH_SHORT).show();
                               *//* Datos datos = new Datos(tiempo,Cadenaurl,Cadenaubi,Cadenaobs);
                                listDatos.add(datos);*//*
                                // Toast.makeText(RecyclerActivity.this, ""+listDatos, Toast.LENGTH_SHORT).show();
                                // listDatos.add(new Datos(url));
                               *//* adaptador= new Adaptador(RecyclerActivity.this,listDatos);
                                recyclerView.setAdapter(adaptador);*//*
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });*/


        String ubicacion="";
        String fecha="";
        String observacion="";
        String foto="";
        Bundle extras= getIntent().getExtras();
        if(extras != null){
            ubicacion= extras.getString("ubicacion");
            fecha= extras.getString("fecha");
            observacion= extras.getString("observacion");
            foto= extras.getString("foto");
        }

        recubi.setText(ubicacion);
        recfecha.setText(fecha);
        recobs.setText(observacion);

        Glide.with(DetallesRecycler.this)
                .load(foto)
                .into(img);


    }
}