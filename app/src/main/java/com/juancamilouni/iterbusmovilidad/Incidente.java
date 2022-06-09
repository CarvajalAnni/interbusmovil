package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    LinearLayout lnColision, lnViaCerrada, lnFalla, lnObras, lnRetencion, lnCarrilCortado, lnAmbulancia, lnOtro, lnPolicia;
    public static int bandera;
    private static final int VALUE_TOTAL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);
        Permiso();
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
                bandera=1;
                Intent intent = new Intent(Incidente.this, Formulario.class);
                startActivity(intent);
            }
        });

        lnColision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=2;
                Intent intent = new Intent(Incidente.this, Formulario.class);
                startActivity(intent);
            }
        });

        lnViaCerrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this,  Formulario.class);
                startActivity(intent);
            }
        });

        lnFalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=2;
                Intent intent = new Intent(Incidente.this, Formulario.class);
                startActivity(intent);
            }
        });

        lnObras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this,  Formulario.class);
                startActivity(intent);
            }
        });

        lnRetencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=1;
                Intent intent = new Intent(Incidente.this,  Formulario.class);
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

    private void Permiso() {

        int estadoPermisol = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int estadoPermisoal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int estadoPermisoc = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int estadoPermisophone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);



        if (estadoPermisol == PackageManager.PERMISSION_GRANTED && estadoPermisoal == PackageManager.PERMISSION_GRANTED && estadoPermisoc == PackageManager.PERMISSION_GRANTED && estadoPermisophone == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, VALUE_TOTAL);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case VALUE_TOTAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    referenciar();
                } else {
                    Toast.makeText(this, "Para acceder debes aceptar los permisos", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }
}