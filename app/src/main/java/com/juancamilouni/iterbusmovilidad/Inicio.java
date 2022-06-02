package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    ImageView btnPerfil, btnNotificacion, btnIncidentes, btnEmergencia, btnAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        referencia();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inicio_tolba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.verPerfil:
                Toast.makeText(this, "Ir a la  vista de ver perfil", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.CambiarContra:
                Intent intent = new Intent(Inicio.this, Ayuda.class);
                startActivity(intent);
                return true;
            case R.id.CerrarSesion:
                Intent intent1 = new Intent(Inicio.this, IniciarSesion.class);
                startActivity(intent1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void referencia() {


           }
}