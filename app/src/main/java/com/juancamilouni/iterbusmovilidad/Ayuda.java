package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.view.Window;

import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Ayuda extends AppCompatActivity {
    ImageView btnAtras;
    Button btnguargar;
    String contrasenas,contrasenas2;
    EditText antigua, nueva, repetir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        referenciar();

    }

    private void referenciar() {
        btnAtras = findViewById(R. id. btnatrasflecha);
        antigua=findViewById(R.id.texContraseñaAntigua);
        nueva=findViewById(R.id.TextNuevaContraseña);
        repetir=findViewById(R.id.textRepetirContraseña);
        btnguargar=findViewById(R.id.btnCambiarContraseña);
        btnguargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        if (antigua.length() < 10 ){
                            antigua.setError("Contraseña no anterior");
                        }else{
                            antigua.setError(null);
                        }
                        contrasenas=nueva.getText().toString();

                        contrasenas2=repetir.getText().toString();
                        if (validarcontrasenas(contrasenas)) {

                            if (contrasenas.equals(contrasenas2)){
                                Toast.makeText(Ayuda.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Ayuda.this, Inicio.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(Ayuda.this, "contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        }



            }
        });
    }

    public boolean validarcontrasenas(String contrasenas) {
        Boolean esValido = true;
        Pattern mayusculas = Pattern.compile("[A-Z]");
        Pattern minusculas = Pattern.compile("[a-z]");
        Pattern numeros = Pattern.compile("[0-9]");

        if (!minusculas.matcher(contrasenas).find()) {
            nueva.setError("contraseña invalida");
            esValido = false;
        } else {
            esValido = true;
        }

        if (!mayusculas.matcher(contrasenas).find()) {
            nueva.setError("contraseña invalida");

            esValido = false;
        } else {
            esValido = true;

        }
        if (!numeros.matcher(contrasenas).find()) {
          nueva.setError("contraseña invalida");

            esValido = false;

        } else {

            esValido = true;

        }
        if (contrasenas.length() < 8) {
            nueva.setError("contraseña invalida");
            esValido = false;
            //charcount.setTextColor(Color.RED);
        } else {
            esValido = true;
            // charcount.setTextColor(Color.GREEN);
        }
        return esValido;
    }

}
