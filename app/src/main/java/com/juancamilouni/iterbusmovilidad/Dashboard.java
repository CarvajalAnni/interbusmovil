package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard extends Activity {
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    LinearLayout salir;
    //Button  bntsing;
    private FirebaseAuth mAuth;
    private TextView textView2, textView4, textView6;
    private ImageView imagenUser;
    String nombredas , correodas;
    FirebaseFirestore db;
    View include ;
    ImageView bntimagen, bntusuario, bntincidenn;

    public String getNombredas() {
        return nombredas;
    }

    public void setNombredas(String nombredas) {
        this.nombredas = nombredas;
    }

    public String getCorreodas() {
        return correodas;
    }

    public void setCorreodas(String correodas) {
        this.correodas = correodas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        include = findViewById(R.id.include);

            imagenUser = findViewById(R.id.imagenUser);
            textView2 = findViewById(R.id.textView2);
            textView4 = findViewById(R.id.textView4);
            textView6 = findViewById(R.id.textView6);
            //bntsing = findViewById(R.id.bntsing);
            salir = findViewById(R.id.Salir);

            // Inicializar Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            //set datos:
            textView2.setText(currentUser.getUid());
            textView4.setText(currentUser.getDisplayName());
            textView6.setText(currentUser.getEmail());

            nombredas = textView4.getText().toString();
            correodas = textView6.getText().toString();


            //cargar imágen con glide:
            Glide.with(this).load(currentUser.getPhotoUrl()).into(imagenUser);


            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        /*bntsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();



                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Abrir MainActivity con SigIn button
                        if(task.isSuccessful()){
                            Intent mainActivity = new Intent(getApplicationContext(), IniciarSesion.class);
                            startActivity(mainActivity);
                            Dashboard.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });*/


        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Abrir MainActivity con SigIn button
                        if(task.isSuccessful()){
                            Intent mainActivity = new Intent(getApplicationContext(), IniciarSesion.class);
                            startActivity(mainActivity);
                            Dashboard.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


        navegacio();
    }

    private void navegacio() { bntimagen = findViewById(R.id.bntimagen);
        bntusuario = findViewById(R.id.bntusuario);
        bntincidenn = findViewById(R.id.bntincidenn);
        bntimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Inicio.class);
                startActivity(intent);

            }
        });
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent);
            }
        });
        bntincidenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Incidente.class);
                startActivity(intent);
            }
        });

    }

}