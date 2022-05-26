package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Notificacion extends AppCompatActivity {
    Button btnInfoNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        referenciar();
    }

    private void referenciar() {
        btnInfoNotificacion = findViewById(R.id.btnInfoNotificacion);
        btnInfoNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notificacion.this, InfoNotificacion.class);
                startActivity(intent);
            }
        });
    }
}