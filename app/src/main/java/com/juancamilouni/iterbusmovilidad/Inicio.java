package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Userdesp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inicio extends Activity {


    View include ;
    ImageView bntimagen, bntusuario, bntincidenn;
    LottieAnimationView sos;
    FloatingActionButton btnnotificacion;
    LinearLayout salirinicio;

    //actualizacion
    TextView nombretok,correotok;
    private FirebaseAuth mAuth;
    String nombredas , correodas,idDoc;
    FirebaseFirestore db;
    String correo3,contrasenia3;

    //cerrar google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    //private FirebaseAuth mAuth;




    private static final int VALUE_TOTAL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        include = findViewById(R.id.include);


        db= FirebaseFirestore.getInstance();

        //actualizar

        nombretok= findViewById(R.id.nombreToken);
        correotok = findViewById(R.id.correoToken);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(Fcm.id != null) {
            if(IniciarSesion.formainicio==1) {

                //actualizar BD con cuenta google
                nombretok.setText(currentUser.getDisplayName());
                correotok.setText(currentUser.getEmail());

                nombredas = nombretok.getText().toString();
                correodas = correotok.getText().toString();



                idDoc = Fcm.id;
               // Toast.makeText(Inicio.this, idDoc, Toast.LENGTH_LONG).show();
                db.collection("token").document(idDoc).update("nombre", nombredas);
                db.collection("token").document(idDoc).update("correo", correodas);
                db.collection("token").document(idDoc).update("rol", "conductor");
            }else {
                //base de datos
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://interbusapi.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InterbusApi interbusApi = retrofit.create(InterbusApi.class);
                Call<ArrayList<Userdesp>> call = interbusApi.login(correo3 = IniciarSesion.correoenvia, contrasenia3 = IniciarSesion.contraenvia);
                call.enqueue(new Callback<ArrayList<Userdesp>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Userdesp>> call, Response<ArrayList<Userdesp>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(Inicio.this, "Email o contraseña incorecto", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<Userdesp> listausu = response.body();

                            for (Userdesp userdesp : listausu) {

                                nombretok.setText(userdesp.getNombre());
                                correotok.setText(userdesp.getEmail());

                                nombredas = nombretok.getText().toString();
                                correodas = correotok.getText().toString();

                                Toast.makeText(Inicio.this, nombredas, Toast.LENGTH_LONG).show();


                                idDoc = Fcm.id;
                                Toast.makeText(Inicio.this, idDoc, Toast.LENGTH_LONG).show();
                                db.collection("token").document(idDoc).update("nombre", nombredas);
                                db.collection("token").document(idDoc).update("correo", correodas);
                                db.collection("token").document(idDoc).update("rol", "despachador");
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Userdesp>> call, Throwable t) {

                    }
                });

            }

        }


        //boton cerar sesion
        salirinicio= findViewById(R.id.Salirinicio);
        salirinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(Inicio.this, gso);

                mAuth.signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Abrir MainActivity con SigIn button
                        if(task.isSuccessful()){
                            Intent mainActivity = new Intent(getApplicationContext(), IniciarSesion.class);
                            startActivity(mainActivity);
                            Inicio.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Intent intent2 = new Intent(Inicio.this, IniciarSesion.class);
                startActivity(intent2);

            }
        });


        Permiso();
        referenciar();
        navegacio();
    }

    private void navegacio() { bntimagen = findViewById(R.id.bntimagen);
        bntusuario = findViewById(R.id.bntusuario);
        bntincidenn = findViewById(R.id.bntincidenn);
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IniciarSesion.formainicio==1){
                Intent intent = new Intent(Inicio.this, Dashboard.class);
                startActivity(intent);}
                else {
                    Intent intent = new Intent(Inicio.this, Perfil.class);
                    startActivity(intent);
                }
            }
        });
        bntincidenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Incidente.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() { moveTaskToBack(true); }




    private void Permiso() {

        int estadoPermisophone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);



        if (estadoPermisophone == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE}, VALUE_TOTAL);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case VALUE_TOTAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    referenciar();
                } else {
                    Toast.makeText(this, "Para acceder debes aceptar los permisos", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }
    private void referenciar() {


        //animacion del boton sos
        LottieAnimationView sos = (LottieAnimationView) findViewById(R.id.sos);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:123"));
                startActivity(intent);
            }
        });

        btnnotificacion = findViewById(R.id.fbottnotificacion);
        btnnotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.interbus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.verPerfil:
                /*Intent intent7= new Intent(Inicio.this,Perfil.class);
                startActivity(intent7);*/

                Intent intent4 = new Intent(Inicio.this, RecyclerToken.class);
                startActivity(intent4);

                return true;


            case R.id.CerrarSesion:


               /* gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


                mAuth.signOut();



                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Abrir MainActivity con SigIn button
                        if(task.isSuccessful()){
                            Intent mainActivity = new Intent(getApplicationContext(), IniciarSesion.class);
                            startActivity(mainActivity);
                            Inicio.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Intent intent2 = new Intent(Inicio.this, IniciarSesion.class);
                startActivity(intent2);*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}