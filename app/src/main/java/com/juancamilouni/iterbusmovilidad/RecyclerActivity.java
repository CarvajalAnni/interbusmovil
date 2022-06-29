package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

public class RecyclerActivity extends AppCompatActivity {

    List<Datos> listDatos;
    Adaptador adaptador;
    FirebaseStorage storageRef;
    FirebaseFirestore db;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);


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
        db.collection("Reportes")./*whereGreaterThanOrEqualTo("idauto",1).*/orderBy("tiempo", Query.Direction.DESCENDING).limit(10).get()
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
                                Datos datos = new Datos(tiempo,Cadenaurl,Cadenaubi,Cadenaobs);
                                listDatos.add(datos);
                              // Toast.makeText(RecyclerActivity.this, ""+listDatos, Toast.LENGTH_SHORT).show();
                                // listDatos.add(new Datos(url));
                                adaptador= new Adaptador(RecyclerActivity.this,listDatos);
                                recyclerView.setAdapter(adaptador);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });

    }
}