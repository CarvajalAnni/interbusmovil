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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Model.Datos;
import Model.Observaciones;
import Model.Ubicacion;

public class Formulario extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Menor", "Moderada", "Seria", "Grave", "Crítica", "Máxima"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    LinearLayout LnTomarFoto, LnSubirFoto, LnUbicacion, LnReportar,LnReportarDespachador;
    ImageView ImgFotoReporte;
    TextInputLayout TxtGravedad;
    TextView TxtLatitud, TxtLongitud;
    EditText EtxtObservaciones;
    int indice = 0;
    String id, urlObtenida,stringlati,stringlongi;
    private Uri imageUri = null;
    LocationManager locationManager;
    Location location;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        FirebaseMessaging.getInstance().subscribeToTopic("envieratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView5);

        adapterItems = new ArrayAdapter<String>(this,R.layout.lista_nivel_gravedad, items);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"Nivel gravedad: "+item, Toast.LENGTH_SHORT).show();
            }
        });
        referenciar();

        if(Incidente.bandera == 1){
            TxtGravedad.setVisibility(View.GONE);
            formulario();
        } else {
            formulario();
        }
    }

    private void referenciar() {
        LnTomarFoto=findViewById(R.id.lnTomarfoto);
        LnSubirFoto=findViewById(R.id.lnOtro);
        LnUbicacion=findViewById(R.id.lnUbicacion);
        LnReportar=findViewById(R.id.btnReporte);
        ImgFotoReporte=findViewById(R.id.imgColicion);
        TxtGravedad=findViewById(R.id.textInputLayout3);
        TxtLatitud=findViewById(R.id.textlatitudcolision);
        TxtLongitud=findViewById(R.id.textlongitudcolision);
        EtxtObservaciones=findViewById(R.id.textObservacionC);
        LnReportarDespachador=findViewById(R.id.btnReporteDespachador);
    }

    private void formulario() {


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxtLatitud.setText(""+ String.valueOf(location.getLatitude()));
                TxtLongitud.setText(""+String.valueOf(location.getLongitude()));

                //convertir a string
                stringlati = TxtLatitud.getText().toString();
                stringlongi = TxtLongitud.getText().toString();
            }
        });
        LnTomarFoto.setOnClickListener(this);
        LnSubirFoto.setOnClickListener(this);
        LnReportar.setOnClickListener(this);



    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lnTomarfoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // if (intent.resolveActivity(getPackageManager()) != null) {
                File imagenArchivo = null;
                try {
                    imagenArchivo = crearImagen();
                } catch (Exception x) {
                    x.printStackTrace();
                }

                if (imagenArchivo != null) {
                    Uri fotoUri = FileProvider.getUriForFile(Formulario.this, "com.juancamilouni.iterbusmovilidad", imagenArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                    startActivityForResult(intent, 1);
                    imageUri = fotoUri;
                }

                break;

            case R.id.lnOtro:

                Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                startActivityForResult(intent2.createChooser(intent2, "Seleccione la aplicacion"), 10);

                break;

            case R.id.btnReporte:
                indice = indice + 1;
                id = String.valueOf(indice);

                long timestamp = System.currentTimeMillis();
                String filePathAndName = "Colision/" + timestamp;

                StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            String uploadedImageUri = "" + uriTask.getResult();
                            //sendList(uploadedImageUri, timestamp);
                            Toast.makeText(Formulario.this, "Reporte enviado correctamente ", Toast.LENGTH_LONG).show();
                            //urlimagen.setText(uploadedImageUri);

                            //String Url = urlimagen.getText().toString();

                            Datos datos = new Datos(uploadedImageUri);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("ColicionUrl")
                                    .add(datos)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                            Toast.makeText(Formulario.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                            Toast.makeText(Formulario.this, "Datos f", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }).addOnFailureListener(e -> {

                });

                // guardar ubicacion

                //Ubicacion ubicacion = new Ubicacion(latitud, longitud);

                Ubicacion ubicacion = new Ubicacion(stringlati, stringlongi);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("UbicacionColi")
                        .add(ubicacion)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(Formulario.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Formulario.this, "Datos f", Toast.LENGTH_SHORT).show();
                            }
                        });
                //guardar observaciones
                String Observaciones = EtxtObservaciones.getText().toString();

                Model.Observaciones observaciones = new Observaciones(Observaciones);

                db.collection("ObservacionesColicion")
                        .add(observaciones)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(Formulario.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(Formulario.this, "Datos f", Toast.LENGTH_SHORT).show();
                            }
                        });
                            llamaratopico();
                            Intent intentt = new Intent(Formulario.this, Incidente.class );
                            startActivity(intentt);
                            break;

            case R.id.btnReporteDespachador:
//                llamarespecifico();
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
        Formulario.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            //Uri path= data.getData();
            //img.setImageURI(path);

            assert data != null;
            imageUri = data.getData();
            ImgFotoReporte.setImageURI(data.getData());

        }else if(requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = Uri.parse(urlObtenida);
            ImgFotoReporte.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void llamaratopico() {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

       /* String mensaje = especifico.getText().toString();
        String title =  titulo.getText().toString();*/

        try {
            json.put("to", "/topics/"+"envieratodos");
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Interbus");
            notificacion.put("detalle", "Se Reporto un Incidente");

            json.put("data",notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){

                @Override
                public Map<String, String> getHeaders(){
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAALeT1rgo:APA91bH4bT4E70reoTjsbCPH63nnoZN2ioq_LvuU3KZgHngw48wJWqkrBxLmvL3OSDIwu0zsLBM54J2ovzPKh08tVEHhUjs-YYUkukMAKVzQHcPgvL6Itw6lz3d43NcgBm3KkydbZOiS");// key configuraciones del cloud menssagin

                    return header;
                }
            };
            myrequest.add(request);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

//    private void llamarespecifico() {
//        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
//        JSONObject json = new JSONObject();
//
//        try {
//            // tomar el valor de token
//            String token = "d5qwpYVGTjmbVtyJqoLnSe:APA91bHZYhLjNt8IbHrh-LhrisdiHMS5akfxA8gVAPFIHnFfgpLYV4B-7fpz8hc9PqtXi4NCB5tAcoAw-l61q4EZN5dAIPSrPiYlJ-IsqLEcZdn8rUIY70Cz66_-atSyh4fNU9F3Y7SK";
//            json.put("to", token);
//            JSONObject notificacion = new JSONObject();
//
//            // notificacion que se recibe
//            notificacion.put("titulo", "Interbus");
//            notificacion.put("detalle", "Un conductor te ha notificado");
//
//            json.put("data",notificacion);
//
//            String URL = "https://fcm.googleapis.com/fcm/send";
//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
//                @Override
//                public Map<String, String> getHeaders(){
//                    Map<String,String> header = new HashMap<>();
//                    header.put("content-type", "application/json");
//                    header.put("authorization", "key=AAAALeT1rgo:APA91bH4bT4E70reoTjsbCPH63nnoZN2ioq_LvuU3KZgHngw48wJWqkrBxLmvL3OSDIwu0zsLBM54J2ovzPKh08tVEHhUjs-YYUkukMAKVzQHcPgvL6Itw6lz3d43NcgBm3KkydbZOiS");// key configuraciones del cloud menssagin
//
//                    return header;
//                }
//            };
//            myrequest.add(request);
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//    }
}