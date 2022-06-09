package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class olvidarcon extends AppCompatActivity {

    TextView txtsi;
    EditText txtnumero;
    Button btncancelado;
    String TAG = "GoogleSignIn",numeroString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidarcon);
        referenciar();


    }

    private void referenciar() {
        txtsi = findViewById(R.id.si);
        txtsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(olvidarcon.this, verificarsi.class);
                startActivity(intent);
            }
        });
        txtnumero = findViewById(R.id.número);
        txtnumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroString = txtnumero.getText().toString();
                if ( !(validarnumero(numeroString))) {


                }
            }
        });




        btncancelado = findViewById(R.id.btnCancelado);
        btncancelado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(olvidarcon.this, IniciarSesion.class);
                startActivity(intent);
            }
        });



    }

    private boolean validarnumero(String numero) {
        Boolean esValido = true;

        Pattern numeros = Pattern.compile("[0-10]");


        if (!numeros.matcher(numero).find()) {
            txtnumero.setError("contraseña invalida");

            esValido = false;

        } else {

            esValido = true;

        }
        if (numero.length() > 10) {
            txtnumero.setError("contraseña invalida");
            esValido = false;
            //charcount.setTextColor(Color.RED);
        } else {
            esValido = true;
            // charcount.setTextColor(Color.GREEN);
        }
        return esValido;
    }
    }
