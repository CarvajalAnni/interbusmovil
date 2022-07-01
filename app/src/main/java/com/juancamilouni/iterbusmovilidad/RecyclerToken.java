package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Model.Token;

public class RecyclerToken extends AppCompatActivity {

    List<Token> listToken;
    AdaptadorToken adaptadortoken;

    FirebaseFirestore db;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_token);

        db= FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.despachadores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listToken= new ArrayList<Token>();
        llenarLista();
    }

    private void llenarLista() {
        //Traer la coleccion de url
        db.collection("token")/*.orderBy("tiempo", Query.Direction.DESCENDING)*/.limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //Toast.makeText(RecyclerActivity.this, ""+document.getData(), Toast.LENGTH_SHORT).show();
                                //String url = document.getData().toString();

                                String nombre = document.getString("nombre");
                                String correo = document.getString("correo");
                                String tokena = document.getString("token");

                                //String sSubCadena1 = Cadenaurl.substring(5);
                                //Toast.makeText(RecyclerActivity.this, ""+ sSubCadena, Toast.LENGTH_SHORT).show();
                                Token token = new Token (nombre,correo,tokena);
                                listToken.add(token);
                                Toast.makeText(RecyclerToken.this, ""+listToken, Toast.LENGTH_SHORT).show();
                                // listDatos.add(new Datos(url));
                                adaptadortoken= new AdaptadorToken(RecyclerToken.this,listToken);
                                recyclerView.setAdapter(adaptadortoken);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
    }
}