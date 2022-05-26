package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Incidente extends AppCompatActivity{
    ImageView imgColision, imgViaCerrada, imgFalla,imgObras ,imgRetencion ,imgCarrilCortado ,imgAmbulancia , imgOtro,imgPolicia;
    TextView txtColision, txtViaCerrada, txtFalla,txtObras ,txtRetencion ,txtCarrilCortado ,txtAmbulancia , txtOtro,txtPolicia;
    LinearLayout lnColision, lnViaCerrada, lnFalla, lnObras, lnRetencion, lnCarrilCortado, lnAmbulancia, lnOtro, lnPolicia;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);
        referenciar();

    }

    private void referenciar() {
        lnColision=findViewById(R.id.lnColision);
        lnViaCerrada=findViewById(R.id.lnViaCerrada);
        lnFalla=findViewById(R.id.lnFalla);
        lnObras=findViewById(R.id.lnObras);
        lnRetencion=findViewById(R.id.lnRetencion);
        lnCarrilCortado=findViewById(R.id.lnCarrilCortado);
        lnAmbulancia=findViewById(R.id.lnAmbulancia);
        lnOtro=findViewById(R.id.lnOtro);
        lnPolicia=findViewById(R.id.lnPolicia);

        lnCarrilCortado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this, Carrilcortado.class);
                startActivity(intent);
            }
        });

        lnColision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this, Colision.class);
                startActivity(intent);
            }
        });

        lnViaCerrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this,  Objetoenvia.class);
                startActivity(intent);
            }
        });

        lnFalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this, Fallasmecanicas.class);
                startActivity(intent);
            }
        });

        lnObras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this,  Objetoenvia.class);
                startActivity(intent);
            }
        });

        lnRetencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this,  Retencion.class);
                startActivity(intent);
            }
        });

        lnAmbulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:6028334898"));
                startActivity(intent1);
            }
        });

        lnOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Incidente.this, Otros.class);
                startActivity(intent);
            }
        });

        lnPolicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:123"));
                startActivity(intent);
            }
        });

    }
}