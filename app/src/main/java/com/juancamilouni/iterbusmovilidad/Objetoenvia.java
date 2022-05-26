package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import Model.Datos;
import Model.Observaciones;
import Model.Ubicacion;

public class Objetoenvia extends AppCompatActivity implements View.OnClickListener{

    Button btntomarfotovia,btnCargarvia, btnubicacionvia,btnincidentevia;
    EditText textVia;


    ImageView imgobjeto;
    int indice = 0;
    String id, urlObtenida, stringlati,stringlongi;
    private Uri imageUri = null;

    //UBICACION
    TextView latitud, longitud;
    LocationManager locationManager;
    Location location;
    private static final int VALUE_UBI = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetoenvia);

        imgobjeto= findViewById(R.id.imgObjeto);

        referenciar();
        permiso();
    }

    private void permiso() {

        int PermisoUbi = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(PermisoUbi == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objetoenvia.this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, VALUE_UBI);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case VALUE_UBI: {
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
        btntomarfotovia= findViewById(R.id.btnTomarFotoVia);
        btntomarfotovia.setOnClickListener(this);

        //BOTON PARA OBTENER UBICACION
        latitud = findViewById(R.id.textlatitudvia);
        longitud = findViewById(R.id.textlongitudvia);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        btnubicacionvia = findViewById(R.id.btnUbicacionVia);
        btnubicacionvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitud.setText(""+ String.valueOf(location.getLatitude()));
                longitud.setText(""+String.valueOf(location.getLongitude()));

                //convertir a string
                stringlati = latitud.getText().toString();
                stringlongi = longitud.getText().toString();

            }
        });




        textVia = findViewById(R.id.textObservacionVia);

        btnincidentevia = findViewById(R.id.btnReportarIncidenteVia);
        btnincidentevia.setOnClickListener(this);

        btnCargarvia = findViewById(R.id.btnCargarVia);
        btnCargarvia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnTomarFotoVia:

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                // if (intent.resolveActivity(getPackageManager()) != null) {
                File imagenArchivo = null;
                try {
                    imagenArchivo = crearImagen();
                } catch (Exception x) {
                    x.printStackTrace();
                }

                if (imagenArchivo != null) {
                    Uri fotoUri = FileProvider.getUriForFile(Objetoenvia.this, "com.juancamilouni.iterbusmovilidad", imagenArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                    startActivityForResult(intent, 1);
                    imageUri = fotoUri;
                }

                break;
            case R.id.btnCargarVia:

                Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                startActivityForResult(intent2.createChooser(intent2, "Seleccione la aplicacion"), 10);

                break;

            case R.id.btnUbicacionVia:

                break;

            case R.id.btnReportarIncidenteVia:

                indice = indice + 1;
                id = String.valueOf(indice);

                long timestamp = System.currentTimeMillis();
                String filePathAndName = "images/" + timestamp;

                StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            String uploadedImageUri = "" + uriTask.getResult();
                            //sendList(uploadedImageUri, timestamp);
                            Toast.makeText(Objetoenvia.this, "Reporte enviado correctamente ", Toast.LENGTH_LONG).show();
                            //urlimagen.setText(uploadedImageUri);

                            //String Url = urlimagen.getText().toString();

                            Datos datos = new Datos(uploadedImageUri);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("ObjetoenlaviaUrl")
                                    .add(datos)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                            Toast.makeText(Objetoenvia.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                            Toast.makeText(Objetoenvia.this, "Datos f", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }).addOnFailureListener(e -> {

                        });


                // guardar ubicacion

                //Ubicacion ubicacion = new Ubicacion(latitud, longitud);

                Ubicacion ubicacion = new Ubicacion(stringlati, stringlongi);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("UbicacionObjeto")
                        .add(ubicacion)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(Objetoenvia.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Objetoenvia.this, "Datos f", Toast.LENGTH_SHORT).show();
                            }
                        });


                //guardar observaciones
                String Observaciones = textVia.getText().toString();

                Observaciones observaciones = new Observaciones(Observaciones);


                db.collection("ObservacionesObjeto")
                        .add(observaciones)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(Objetoenvia.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Objetoenvia.this, "Datos f", Toast.LENGTH_SHORT).show();
                            }
                        });

                break;

        }

    }

    private File crearImagen() throws IOException {
        String nombreImagen = "fotoLugar";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        urlObtenida = imagen.getAbsolutePath();
        return imagen;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Objetoenvia.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            //Uri path= data.getData();
            //img.setImageURI(path);

            assert data != null;
            imageUri = data.getData();
            imgobjeto.setImageURI(data.getData());

        }else if(requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = Uri.parse(urlObtenida);
            imgobjeto.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}