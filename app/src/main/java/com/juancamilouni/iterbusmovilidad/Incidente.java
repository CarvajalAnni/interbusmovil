package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import Model.Userdesp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Incidente extends AppCompatActivity{
    TextView nombrecond, rolincidente;
    ImageView fotocond;
    String correo2,contrasenia2;
    LinearLayout lnColision, lnViaCerrada, lnFalla, lnObras, lnRetencion, lnCarrilCortado, lnAmbulancia, lnOtro, lnPolicia;
    public static int bandera;
    private static final int VALUE_TOTAL = 200;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth mAuth;

    View include ;
    ImageView bntimagen, bntusuario, bntincidenn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);
        include = findViewById(R.id.include);


        Permiso();
        referenciar();

        if (IniciarSesion.formainicio==1){
            //datos del conductor
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            nombrecond.setText(currentUser.getDisplayName());
            //cargar imágen con glide:
            Glide.with(this).load(currentUser.getPhotoUrl()).into(fotocond);
        }else {

            //base de datos
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://interbusapi.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            InterbusApi interbusApi = retrofit.create(InterbusApi.class);
            Call<ArrayList<Userdesp>> call = interbusApi.login(correo2 = IniciarSesion.correoenvia, contrasenia2= IniciarSesion.contraenvia);
            call.enqueue(new Callback<ArrayList<Userdesp>>() {


                @Override
                public void onResponse(Call<ArrayList<Userdesp>> call, Response<ArrayList<Userdesp>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(Incidente.this, "Email o contraseña incorecto", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<Userdesp> listausu = response.body();

                        for (Userdesp userdesp : listausu) {

                            fotocond.setImageResource(R.drawable.usuarioconductor);
                            nombrecond.setText(userdesp.getNombre());
                            rolincidente.setText(userdesp.getRol());

                        }
                    }

                }
                @Override
                public void onFailure(Call<ArrayList<Userdesp>> call, Throwable t) {

                }
            });
        }



        navegacio();
    }

    private void navegacio() { bntimagen = findViewById(R.id.bntimagen);
        bntusuario = findViewById(R.id.bntusuario);
        bntincidenn = findViewById(R.id.bntincidenn);
        bntimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Incidente.this, Inicio.class);
                startActivity(intent);

            }
        });
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IniciarSesion.formainicio == 1) {
                    Intent intent = new Intent(Incidente.this, Dashboard.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Incidente.this, Perfil.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void referenciar() {
        lnColision=findViewById(R.id.lnColision);
        lnViaCerrada=findViewById(R.id.lnViaCerrada);
        lnFalla=findViewById(R.id.lnFalla);
        lnObras=findViewById(R.id.lnObras);
        lnRetencion=findViewById(R.id.lnRetencion);
        lnCarrilCortado=findViewById(R.id.lnCarrilCortado);
        lnAmbulancia=findViewById(R.id.lnAmbulancia);
        lnOtro=findViewById(R.id.lnOtro);
        lnPolicia=findViewById(R.id.lnPolicia);
        nombrecond=findViewById(R.id.Nombrecond);
        fotocond=findViewById(R.id.Fotocond);
        rolincidente=findViewById(R.id.Rolincidente);

        lnCarrilCortado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this, Formulario.class);
                startActivity(intent);
            }
        });

        lnColision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=2;
                Intent intent = new Intent(Incidente.this, Formulario.class);
                startActivity(intent);
            }
        });

        lnViaCerrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this,  Formulario.class);
                startActivity(intent);
            }
        });

        lnFalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=2;
                Intent intent = new Intent(Incidente.this, Formulario.class);
                startActivity(intent);
            }
        });

        lnObras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this,  Formulario.class);
                startActivity(intent);
            }
        });

        lnRetencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this,  Formulario.class);
                startActivity(intent);
            }
        });

        lnAmbulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:6028334898"));
                startActivity(intent1);
            }
        });

        lnOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this, Otros.class);
                startActivity(intent);
            }
        });

        lnPolicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:123"));
                startActivity(intent);
            }
        });

    }

    private void Permiso() {

        int estadoPermisol = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int estadoPermisoal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int estadoPermisoc = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int estadoPermisophone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);



        if (estadoPermisol == PackageManager.PERMISSION_GRANTED && estadoPermisoal == PackageManager.PERMISSION_GRANTED && estadoPermisoc == PackageManager.PERMISSION_GRANTED && estadoPermisophone == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, VALUE_TOTAL);

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
}