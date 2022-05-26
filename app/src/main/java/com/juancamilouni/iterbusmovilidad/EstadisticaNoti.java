package com.juancamilouni.iterbusmovilidad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EstadisticaNoti extends AppCompatActivity implements View.OnClickListener {
    RadioGroup rg;
    ImageView imgEstadistica;
    RadioButton btnCarrilcortadoE, btnObrasE, btnObjetivoenlaviaE,btnRetencionE, bntColisionE, btnFallasmecanicasE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica_noti);
        imgEstadistica =findViewById(R.id.imgEstadistica);
        btnCarrilcortadoE = findViewById(R.id.btnCarrilcortadoE);
        btnObrasE = findViewById(R.id.btnObrasE);
        btnObjetivoenlaviaE = findViewById(R.id.btnObjetivoenlaviaE);
        btnRetencionE = findViewById(R.id.btnRetencionE);
        bntColisionE = findViewById(R.id.bntColisionE);
        btnFallasmecanicasE = findViewById(R.id.btnFallasmecanicasE);
        rg = findViewById(R.id.rg);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btnCarrilcortadoE:
                btnCarrilcortadoE.setText("Información");
                break;
            case  R.id.btnObrasE:
                btnObrasE.setText("Información");
                break;
            case  R.id.btnObjetivoenlaviaE:
                btnObjetivoenlaviaE.setText("Información");
                break;
            case  R.id.btnRetencionE:
                btnRetencionE.setText("Información");
                break;
            case  R.id.bntColisionE:
                bntColisionE.setText("Información");
                break;
            case  R.id.btnFallasmecanicasE:
                btnFallasmecanicasE.setText("Información");
                break;

        }
    }
}