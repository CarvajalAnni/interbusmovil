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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Inicio extends AppCompatActivity {

    ImageView imgemergencia;
    Button button2;
    LottieAnimationView sos;
    FloatingActionButton btnnotificacion;

    //actualizacion
    private TextView nombretok,correotok;
    private FirebaseAuth mAuth;
    String nombredas , correodas;
    FirebaseFirestore db;


    //navegacion
   BottomNavigationView navegacion;

    private static final int VALUE_TOTAL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //navegacion
        navegacion=findViewById(R.id.botton);
        navegacion.setSelectedItemId(R.id.emergencia);
        navegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.emergencia:
                        return true;
                    case R.id.incidente:
                        startActivity(new Intent(getApplicationContext(),Incidente.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.perfil:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        db= FirebaseFirestore.getInstance();

        //actualizar

        nombretok= findViewById(R.id.nombreToken);
        correotok = findViewById(R.id.correoToken);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        nombretok.setText(currentUser.getDisplayName());
        correotok.setText(currentUser.getEmail());

        nombredas = nombretok.getText().toString();
        correodas = correotok.getText().toString();

        db.collection("token").document("A9EIWAOiMheWVX0pSTuH").update("nombre",nombredas);
        db.collection("token").document("A9EIWAOiMheWVX0pSTuH").update("correo",correodas);



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

        btnnotificacion = findViewById(R.id.fbottnotificacion);
        btnnotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, RecyclerActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.verPerfil:
                Intent intent7= new Intent(Inicio.this,Perfil.class);
                startActivity(intent7);
                return true;

            case R.id.CambiarContraseña:
                Intent intent1 = new Intent(Inicio.this, Ayuda.class);
                startActivity(intent1);
                return true;

            case R.id.CerrarSesion:
                Intent intent2 = new Intent(Inicio.this, Dashboard.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}