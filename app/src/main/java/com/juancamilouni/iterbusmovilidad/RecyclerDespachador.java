package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecyclerDespachador extends AppCompatActivity {
    Button btnInicioDespachador;
    FloatingActionButton fbtnnotificaciond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_despachador);
        referenciar();
    }



    private void referenciar() {
        fbtnnotificaciond = findViewById(R.id.fbtnnotificaciond);
        fbtnnotificaciond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerDespachador.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });

        btnInicioDespachador = findViewById(R.id.btnInicioDespachador);
        btnInicioDespachador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecyclerDespachador.this, IniciarSesion.class);
                startActivity(intent);
            }
        });

    }
}