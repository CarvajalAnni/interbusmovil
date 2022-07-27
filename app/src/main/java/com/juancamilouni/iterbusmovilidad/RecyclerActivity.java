package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Datos;

public class RecyclerActivity extends Activity {

    List<Datos> listDatos;
    Adaptador adaptador;
    FirebaseStorage storageRef;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    String obser1,url1,ubicacion1,fecha1;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
            }
        });



        db= FirebaseFirestore.getInstance();
        storageRef=FirebaseStorage.getInstance();
        recyclerView = findViewById(R.id.rcreportes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDatos= new ArrayList<Datos>();
        llenarLista();
    }

    private void llenarLista() {
        //Traer la coleccion de url
        db.collection("Reportes").orderBy("tiempo", Query.Direction.DESCENDING).limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //Toast.makeText(RecyclerActivity.this, ""+document.getData(), Toast.LENGTH_SHORT).show();
                                Date tiempo= document.getDate("tiempo");
                                String Cadenaurl = document.getString("url");
                                String Cadenaobs = document.getString("observaciones");
                                String Cadenaubi = document.getString("ubicacion");

                                Datos datos = new Datos(tiempo,Cadenaurl,Cadenaubi,Cadenaobs);
                                listDatos.add(datos);
                                // Toast.makeText(RecyclerActivity.this, ""+listDatos, Toast.LENGTH_SHORT).show();
                                adaptador= new Adaptador(RecyclerActivity.this,listDatos);

                                //metodo click
                                adaptador.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent= new Intent(RecyclerActivity.this,DetallesRecycler.class);

                                        fecha1= DateFormat.format("EEEE dd/LLLL/yyyy HH:mm:ss",listDatos.get(recyclerView.getChildAdapterPosition(view)).getTiempo()).toString();
                                        obser1= listDatos.get(recyclerView.getChildAdapterPosition(view)).getObservaciones();
                                        url1= listDatos.get(recyclerView.getChildAdapterPosition(view)).getUrl();
                                        ubicacion1= listDatos.get(recyclerView.getChildAdapterPosition(view)).getUbicacion();

                                        intent.putExtra("ubicacion",ubicacion1.toString());
                                        intent.putExtra("fecha",fecha1.toString());
                                        intent.putExtra("observacion",obser1.toString());
                                        intent.putExtra("foto",url1.toString());
                                        startActivity(intent);
                                    }
                                });

                                recyclerView.setAdapter(adaptador);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
    }
}