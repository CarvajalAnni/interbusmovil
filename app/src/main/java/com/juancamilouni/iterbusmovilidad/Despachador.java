package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Datos;

public class Despachador extends AppCompatActivity {

    ImageView bntimagen, bntusuario, bntincidenn;

    List<Datos> listDatos;
    Adaptador adaptador;
    FirebaseStorage storageRef;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    public static String obser1,url1,ubicacion1,fecha1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despachador);

        db= FirebaseFirestore.getInstance();
        storageRef=FirebaseStorage.getInstance();
        recyclerView = findViewById(R.id.rcreportes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDatos= new ArrayList<Datos>();
        //llenarLista();

    }

    private void llenarLista() {
        //Traer la coleccion de url
        /*db.collection("Reportes").orderBy("tiempo", Query.Direction.DESCENDING).limit(10).get()
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

                });*/





        /*Actualizacion de datos sin conexion
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);*/





        ////////detectar actualizaciones
        /*db.collection("Reportes").orderBy("tiempo", Query.Direction.DESCENDING).limit(10)
                .addSnapshotListener(*//*MetadataChanges.INCLUDE,*//* new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    //Log.d(TAG, "New city: " + dc.getDocument().getData());

                                    Date tiempo= dc.getDocument().getDate("tiempo");
                                    String Cadenaurl = dc.getDocument().getString("url");
                                    String Cadenaobs = dc.getDocument().getString("observaciones");
                                    String Cadenaubi = dc.getDocument().getString("ubicacion");

                                    Datos datos = new Datos(tiempo,Cadenaurl,Cadenaubi,Cadenaobs);
                                    listDatos.add(datos);
                                    // Toast.makeText(RecyclerActivity.this, ""+listDatos, Toast.LENGTH_SHORT).show();
                                    adaptador= new Adaptador(Despachador.this,listDatos);

                                    //metodo click
                                    adaptador.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent= new Intent(Despachador.this,DetallesRecycler.class);

                                            fecha1= DateFormat.format("EEEE dd/LLLL/yyyy HH:mm:ss",listDatos.get(recyclerView.getChildAdapterPosition(view)).getTiempo()).toString();
                                            obser1= listDatos.get(recyclerView.getChildAdapterPosition(view)).getObservaciones();
                                            url1= listDatos.get(recyclerView.getChildAdapterPosition(view)).getUrl();
                                            ubicacion1= listDatos.get(recyclerView.getChildAdapterPosition(view)).getUbicacion();

                                            *//*intent.putExtra("ubicacion",ubicacion1.toString());
                                            intent.putExtra("fecha",fecha1.toString());
                                            intent.putExtra("observacion",obser1.toString());
                                            intent.putExtra("foto",url1.toString());*//*
                                            startActivity(intent);
                                        }
                                    });

                                    recyclerView.setAdapter(adaptador);



                                    break;
                                *//*case MODIFIED:
                                    Log.d(TAG, "Modified city: " + dc.getDocument().getData());
                                    break;*//*
                                case REMOVED:
                                    Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                    break;
                            }
                        }

                    }
                });



*/
    }

}