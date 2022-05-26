package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.Observaciones;

public class Otros extends AppCompatActivity {
    Button rpBtn;
    EditText etxtOt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);

        referenciar();
    }

    private void referenciar() {

        etxtOt = findViewById(R.id.textInformacionOtr);
        rpBtn = findViewById(R.id.btnOtr);
        rpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Observaciones = etxtOt.getText().toString();

                Observaciones observaciones = new Observaciones(Observaciones);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("ObservacionesOtros")
                        .add(observaciones)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(Otros.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Otros.this, "Datos f", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}