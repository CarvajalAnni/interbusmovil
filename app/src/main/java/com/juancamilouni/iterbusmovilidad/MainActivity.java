package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refereciar();
        TimerTask espera = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Emergencia.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(espera, 1800);


    }

    private void refereciar() {
        auto = findViewById(R.id.autobusmovimiento);
        TranslateAnimation translateAnimation1 = new TranslateAnimation(0.0f, 1600.0f, 0.0f, 0.0f);
        translateAnimation1.setDuration(2166);
        auto.startAnimation(translateAnimation1);


    }
}