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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Perfil extends AppCompatActivity {
    TextView etxcorreo, etxcontrasenia,etxrol;
    Bundle extras;
    ImageButton btnAttrass;
    FloatingActionButton fbtnatras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        referenciar();
        recibeDatos();
       referencio();

    }

    private void referencio() {
        fbtnatras = findViewById(R.id.fbtnatras);
        fbtnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intennt6 = new Intent(Perfil.this, Inicio.class);
                startActivity(intennt6);
            }
        });
    }


    private void recibeDatos() {
        extras = getIntent().getExtras();
        String correo = extras.getString("correo");
        String contrasenia = extras.getString("contraseña");
        String rol = extras.getString("rol");

        etxcorreo.setText(correo);
        etxcontrasenia.setText(contrasenia);
        etxrol.setText(rol);

    }
    private void referenciar(){
        etxcorreo=findViewById(R.id.textNombreP);
        etxcontrasenia=findViewById(R.id.textCorreoP);
        etxrol=findViewById(R.id.textRolP);

    }


}





