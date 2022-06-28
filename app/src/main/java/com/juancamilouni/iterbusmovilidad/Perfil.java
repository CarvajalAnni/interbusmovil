package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Perfil extends AppCompatActivity {
    ImageView btnatras;

    //navegacion
    BottomNavigationView navegacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //navegacion
        navegacion=findViewById(R.id.botton);
        navegacion.setSelectedItemId(R.id.perfil);
        navegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.emergencia:
                        startActivity(new Intent(getApplicationContext(),Inicio.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.incidente:
                        startActivity(new Intent(getApplicationContext(),Incidente.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.perfil:
                        return true;
                }
                return false;
            }
        });


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