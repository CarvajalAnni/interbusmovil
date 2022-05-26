package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Incidente extends AppCompatActivity implements View.OnClickListener {
    ImageButton btncarrilcor,btnobrai,btnobjetoi,btnreteni,btncolicioni,btnfallasi,btnotro;
    TextView txtcarri,txtobra,txtobjvia,txtreten,txtcoli,txtfalla,txtotros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);

        referenciar();

    }

    private void referenciar() {
        btncarrilcor = findViewById(R.id.btnCarrilCortadoE);
        btncarrilcor.setOnClickListener(this);
        btnobrai = findViewById(R.id.btnObrasE);
        btnobrai.setOnClickListener(this);
        btnobjetoi = findViewById(R.id.btnObjetoViaE);
        btnobjetoi.setOnClickListener(this);
        btnreteni = findViewById(R.id.btnRetencionE);
        btnreteni.setOnClickListener(this);
        btncolicioni = findViewById(R.id.btnColisionE);
        btncolicioni.setOnClickListener(this);
        btnfallasi = findViewById(R.id.btnFallasMecanicasE);
        btnfallasi.setOnClickListener(this);
        btnotro = findViewById(R.id.btnOtroE);
        btnotro.setOnClickListener(this);


        txtcarri = findViewById(R.id.textCarril);
        txtcarri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this, Carrilcortado.class);
                startActivity(intent);
            }
        });


        txtobra = findViewById(R.id.textObras);
        txtobra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Incidente.this, Obras.class);
                startActivity(intent3);
            }
        });
        txtobjvia = findViewById(R.id.textObjetovia);
        txtobjvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Incidente.this, Objetoenvia.class);
                startActivity(intent);
            }
        });
        txtreten = findViewById(R.id.textRetencion);
        txtreten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Incidente.this, Retencion.class);
                startActivity(intent);
            }
        });
        txtcoli =findViewById(R.id.textColicion);
        txtcoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Incidente.this, Colision.class);
                startActivity(intent);
            }
        });
        txtfalla = findViewById(R.id.textFallas);
        txtfalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Incidente.this, Fallasmecanicas.class);
                startActivity(intent5);

            }
        });

        txtotros = findViewById(R.id.textOtro);
        txtotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Incidente.this, Otros.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCarrilCortadoE:
                Intent intent = new Intent(Incidente.this, Carrilcortado.class);
                startActivity(intent);
                break;
            case R.id.btnObrasE:
                Intent intent2 = new Intent(Incidente.this,  Obras.class);
                startActivity(intent2);
                break;
            case R.id.btnObjetoViaE:
                Intent intent3 = new Intent(Incidente.this,  Objetoenvia.class);
                startActivity(intent3);
                break;
            case R.id.btnRetencionE:
                Intent intent4 = new Intent(Incidente.this,  Retencion.class);
                startActivity(intent4);
                break;
            case R.id.btnColisionE:
                Intent intent5 = new Intent(Incidente.this, Colision.class);
                startActivity(intent5);
                break;
            case  R.id.btnFallasMecanicasE:
                Intent intent6 = new Intent(Incidente.this, Fallasmecanicas.class);
                startActivity(intent6);
                break;
            case R.id.btnOtroE:
                Intent intent7 = new Intent(Incidente.this, Otros.class);
                startActivity(intent7);
                break;
        }
    }
}