package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Emergencia extends AppCompatActivity {
    ImageView btnLlamarAmbulanciaE, btnLlamarPoliciaE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);   referenciar();
        llamadaAmbulancia();
    }

    public void llamadaAmbulancia() {
        btnLlamarAmbulanciaE = findViewById(R.id.btnLlamarAmbulanciaE);
        btnLlamarAmbulanciaE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:(2) 8334898"));
                startActivity(intent);
            }
        });

    }

    public void referenciar() {
        btnLlamarPoliciaE = findViewById(R.id.btnLlamarPoliciaE);
        btnLlamarPoliciaE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:123"));
                startActivity(intent);

            }
        });

    }
}