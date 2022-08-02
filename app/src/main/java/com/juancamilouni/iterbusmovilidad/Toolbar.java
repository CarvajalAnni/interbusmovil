package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Toolbar extends AppCompatActivity {
    ImageView bntimagen, bntusuario, bntincidenn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        navegacion();
    }

    private void  navegacion() { bntimagen = findViewById(R.id.bntimagen);
        bntusuario = findViewById(R.id.bntusuario);
        bntincidenn = findViewById(R.id.bntincidenn);
        bntimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toolbar.this, Inicio.class);
                startActivity(intent);

            }
        });
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Toolbar.this, Perfil.class);
                startActivity(intent);*/

                if (IniciarSesion.formainicio==1){
                    Intent intent = new Intent(Toolbar.this, Dashboard.class);
                    startActivity(intent);}
                else {
                    Intent intent = new Intent(Toolbar.this, Perfil.class);
                    startActivity(intent);
                }
            }
        });
        bntincidenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toolbar.this, Incidente.class);
                startActivity(intent);
            }
        });

    }
}