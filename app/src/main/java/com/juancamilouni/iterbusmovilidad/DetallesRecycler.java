package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import Model.Datos;

public class DetallesRecycler extends Activity {
    View include ;
    ImageView bntimagen, bntusuario, bntincidenn;
    LinearLayout eliminar;
    String idrecibido;

    FirebaseFirestore db;
    TextView recfecha,recubi,recobs;
    ImageView img;

    //navegacion
    BottomNavigationView navegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_recycler);

        //instanciar base de datos
        db=FirebaseFirestore.getInstance();

        include = findViewById(R.id.include);

        recfecha= findViewById(R.id.detFecha);
        recubi= findViewById(R.id.detLatitud);
        recobs= findViewById(R.id.detObservacion);
        img= findViewById(R.id.detImg);

        //Eliminar de base de datos
        idrecibido= RecyclerActivity.idDoc;
        eliminar= findViewById(R.id.Eliminar);

        if (IniciarSesion.formainicio==2){
            eliminar.setVisibility(View.VISIBLE);
        }else {
            eliminar.setVisibility(View.INVISIBLE);
        }

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(DetallesRecycler.this,RecyclerActivity.class);
                 db.collection("Reportes").document(idrecibido).delete();
                startActivity(intent);

                Toast.makeText(DetallesRecycler.this, idrecibido, Toast.LENGTH_SHORT).show();
            }
        });



        //mostrar detalles del recycler
        String ubicacion= RecyclerActivity.ubicacion1;
        String fecha=RecyclerActivity.fecha1;
        String observacion=RecyclerActivity.obser1;
        String foto=RecyclerActivity.url1;

        if (ubicacion== null) {
            recubi.setText("Ubicacion no obtenida");
        }else{
            recubi.setText(ubicacion);
        }

        //recfecha.setText(DateFormat.format("EEEE dd/LLLL/yyyy HH:mm:ss", ));
        recfecha.setText(fecha);
        recobs.setText(observacion);

        Glide.with(DetallesRecycler.this)
                .load(foto)
                //.apply(new RequestOptions()
                .centerCrop()
                //.override(500,500))
                .into(img)
                ;

        navegacio();
    }

    private void navegacio() { bntimagen = findViewById(R.id.bntimagen);
        bntusuario = findViewById(R.id.bntusuario);
        bntincidenn = findViewById(R.id.bntincidenn);
        bntimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesRecycler.this, Inicio.class);
                startActivity(intent);

            }
        });
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IniciarSesion.formainicio == 1) {
                    Intent intent = new Intent(DetallesRecycler.this, Dashboard.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DetallesRecycler.this, Perfil.class);
                    startActivity(intent);
                }
            }
        });
        bntincidenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesRecycler.this, Incidente.class);
                startActivity(intent);
            }
        });

    }
}