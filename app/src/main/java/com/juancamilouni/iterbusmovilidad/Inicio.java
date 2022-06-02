package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class Inicio extends AppCompatActivity {

    ImageView btnPerfil, btnNotificacion, btnIncidentes, btnEmergencia, btnAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        referencia();
    }

    private void referencia() {

        btnIncidentes = findViewById(R.id.btnIncidentes);
        btnIncidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Incidente.class);
                startActivity(intent);
            }
        });
        btnEmergencia = findViewById(R.id.btnEmergencia);
        btnEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Emergencia.class);
                startActivity(intent);
            }
        });

        btnAyuda = findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Ayuda.class);
                startActivity(intent);
            }
        });

        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Perfil.class);
                startActivity(intent);

            }
        });
        btnNotificacion = findViewById(R.id.btnNotificacion);
        btnNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Notificacion.class);
                startActivity(intent);

            }
        });


    }
}