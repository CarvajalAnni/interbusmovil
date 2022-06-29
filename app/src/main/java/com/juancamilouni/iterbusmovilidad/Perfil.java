package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    TextView etxcorreo, etxcontrasenia;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        referenciar();
        recibeDatos();

    }
    private void recibeDatos() {
        extras = getIntent().getExtras();
        String correo = extras.getString("correo");
        String contrasenia = extras.getString("contraseña");
        etxcorreo.setText(correo);
        etxcontrasenia.setText(contrasenia);

    }
    private void referenciar(){
        etxcorreo=findViewById(R.id.textNombreP);
        etxcontrasenia=findViewById(R.id.textCorreoP);

    }
}





