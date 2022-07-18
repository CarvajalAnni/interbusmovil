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

public class Perfil extends AppCompatActivity {
    TextView etxcorreo, etxcontrasenia;
    Bundle extras;
    ImageButton btnAttrass;


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
        btnAttrass = findViewById(R.id.Imagen1);
        btnAttrass.setOnClickListener(new View.OnClickListener() {
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
        etxcorreo.setText(correo);
        etxcontrasenia.setText(contrasenia);

    }
    private void referenciar(){
        etxcorreo=findViewById(R.id.textNombreP);
        etxcontrasenia=findViewById(R.id.textCorreoP);

    }


}





