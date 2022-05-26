package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class InfoNotificacion extends AppCompatActivity {
    Button btnEstadistica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_notificacion);

        referenciar();
    }

   public void referenciar() {
       btnEstadistica = findViewById(R.id.btnEstadistica);
       btnEstadistica.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(InfoNotificacion.this, EstadisticaNoti.class);
               startActivity(intent);
           }
       });
    }
}