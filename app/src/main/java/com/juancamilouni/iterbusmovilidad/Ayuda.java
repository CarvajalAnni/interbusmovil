package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ayuda extends AppCompatActivity {
    ImageView btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        referenciar();

    }

    private void referenciar() {
        btnAtras = findViewById(R. id. btnatrasflecha);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ayuda.this, Inicio.class);
                startActivity(intent);
            }
        });
    }
}
