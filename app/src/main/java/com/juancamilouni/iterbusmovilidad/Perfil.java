package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class Perfil extends AppCompatActivity {
    ImageView btnatras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        referenciar();
    }

    private void referenciar() {

        btnatras = findViewById(R.id.btnatrasflecha);
        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Perfil.this, Inicio.class);
                startActivity(intent);
            }
        });

    }
}