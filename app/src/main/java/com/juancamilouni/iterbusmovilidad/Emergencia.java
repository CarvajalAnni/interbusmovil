package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Emergencia extends Activity {
    private static final int VALUE_TOTAL = 200;

    FloatingActionButton btnnotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);

        Permiso();
        referenciar();

    }

    @Override
    public void onBackPressed() { moveTaskToBack(true); }




    private void Permiso() {

        int estadoPermisophone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);



        if (estadoPermisophone == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE}, VALUE_TOTAL);

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
    private void referenciar() {

        LottieAnimationView sos = (LottieAnimationView) findViewById(R.id.sos);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:123"));
                startActivity(intent);
            }
        });

        btnnotificacion = findViewById(R.id.inisecion);
        btnnotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emergencia.this, IniciarSesion.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.interbus,menu);
        return true;
    }

}