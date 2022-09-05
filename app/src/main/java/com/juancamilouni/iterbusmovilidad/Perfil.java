package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Model.Userdesp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfil extends AppCompatActivity {
    TextView etxcorreo, etxcontrasenia,etxrol;
    Bundle extras;
    String correo,contrasenia;
    public static String nombrerol,rol;
    FloatingActionButton atras;



    View include ;
    ImageView bntimagen, bntusuario, bntincidenn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        include = findViewById(R.id.include);

        referenciar();
        //recibeDatos();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://interbusapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterbusApi interbusApi = retrofit.create(InterbusApi.class);
        Call<ArrayList<Userdesp>> call = interbusApi.login(correo = IniciarSesion.correoenvia, contrasenia= IniciarSesion.contraenvia);
        call.enqueue(new Callback<ArrayList<Userdesp>>() {


            @Override
            public void onResponse(Call<ArrayList<Userdesp>> call, Response<ArrayList<Userdesp>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Perfil.this, "Email o contraseña incorecto", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Userdesp> listausu = response.body();

                    for (Userdesp userdesp : listausu) {

                        etxcorreo.setText(userdesp.getEmail());
                        etxcontrasenia.setText(userdesp.getNombre());
                        etxrol.setText(userdesp.getRol());

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Userdesp>> call, Throwable t) {

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
                Intent intent = new Intent(Perfil.this, Inicio.class);
                startActivity(intent);

            }
        });
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Perfil.class);
                startActivity(intent);
            }
        });
        bntincidenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Incidente.class);
                startActivity(intent);
            }
        });

    }




    private void recibeDatos() {
        extras = getIntent().getExtras();
        String correo = extras.getString("correo");
        String contrasenia = extras.getString("contraseña");
        String rol = extras.getString("rol");

        /*etxcorreo.setText(correo);
        etxcontrasenia.setText(contrasenia);
        etxrol.setText(rol);*/

    }
    private void referenciar(){
        etxcorreo=findViewById(R.id.textNombreP);
        etxcontrasenia=findViewById(R.id.textCorreoP);
        etxrol=findViewById(R.id.textRolP);
        /*atras=findViewById(R.id.fbtnatras);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, RecyclerToken.class);
                startActivity(intent);
            }
        });*/

    }
}

