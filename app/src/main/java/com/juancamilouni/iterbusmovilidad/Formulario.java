package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Model.Datos;

public class Formulario extends AppCompatActivity implements View.OnClickListener {

    LinearLayout LnTomarFoto, LnSubirFoto, LnUbicacion, LnReportar,LnReportarDespachador;
    ImageView ImgFotoReporte;
    TextView TxtLatitud, TxtLongitud, Txtimagen;
    static EditText EtxtObservaciones;
    int indice = 0;
    String id, urlObtenida,stringlati,url,Observaciones;
    private Uri imageUri = null;
    LocationManager locationManager;
    Location location;
    Date fechasub;



    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;
    Uri imagenUri1;
    View include ;
    ImageView bntimagen, bntusuario, bntincidenn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        include = findViewById(R.id.include);


        FirebaseMessaging.getInstance().subscribeToTopic("envieratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });





        /*autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"Nivel gravedad: "+item, Toast.LENGTH_SHORT).show();
            }
        });*/
        referenciar();

        /*if(Incidente.bandera == 1){
            TxtGravedad.setVisibility(View.GONE);
            formulario();
        } else {
            formulario();
        }*/
        formulario();
        navegacio();
    }

    private void navegacio() { bntimagen = findViewById(R.id.bntimagen);
        bntusuario = findViewById(R.id.bntusuario);
        bntincidenn = findViewById(R.id.bntincidenn);
        bntimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Formulario.this, Inicio.class);
                startActivity(intent);

            }
        });
        bntusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IniciarSesion.formainicio == 1) {
                    Intent intent = new Intent(Formulario.this, Dashboard.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Formulario.this, Perfil.class);
                    startActivity(intent);
                }
            }
        });
        bntincidenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Formulario.this, Incidente.class);
                startActivity(intent);
            }
        });

    }

    private void referenciar() {
        LnTomarFoto=findViewById(R.id.lnTomarfoto);
        LnSubirFoto=findViewById(R.id.lnOtro);
        LnUbicacion=findViewById(R.id.lnUbicacion);
        LnReportar=findViewById(R.id.btnReporte);
        ImgFotoReporte=findViewById(R.id.imgColicion);

        TxtLatitud=findViewById(R.id.textlatitudcolision);
        TxtLongitud=findViewById(R.id.textlongitudcolision);
        EtxtObservaciones=findViewById(R.id.textObservacionC);
        Txtimagen=findViewById(R.id.textView33);
        LnReportarDespachador=findViewById(R.id.btnReporteDespachador);
    }

    private void formulario() {

        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TxtLatitud.setText(""+ String.valueOf(location.getLatitude()));
                TxtLongitud.setText(""+String.valueOf(location.getLongitude()));

                //convertir a string
                *//*stringlati = TxtLatitud.getText().toString();*//*
                //stringlongi = TxtLongitud.getText().toString();

                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    try {
                        Geocoder geocoder = new Geocoder(Formulario.this,Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        if (!list.isEmpty()) {
                            Address DirCalle = list.get(0);
                            TxtLatitud.setText(DirCalle.getAddressLine(0));
                            stringlati = TxtLatitud.getText().toString();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }else {
            LnUbicacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UbicacionStart();
                }
            });

        }


        LnTomarFoto.setOnClickListener(this);
        LnSubirFoto.setOnClickListener(this);
        LnReportar.setOnClickListener(this);
        LnReportarDespachador.setOnClickListener(this);
    }


    //metodos para ubicacion/////////////////////////////////////////////
    private void UbicacionStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(Formulario.this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        TxtLatitud.setText("Obteniendo Ubicacion...");
        //TxtLongitud.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UbicacionStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    TxtLatitud.setText(""+DirCalle.getAddressLine(0));
                    stringlati=TxtLatitud.getText().toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        Formulario mainActivity;

        public Formulario getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(Formulario mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = "" + loc.getLatitude();
            String Text2 = "" + loc.getLongitude();
            TxtLatitud.setText(Text);
            TxtLongitud.setText(Text2);
        /*    lagvet=TxtLongitud.getText().toString();
            lonvet=TxtLatitud.getText().toString();*/
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            TxtLatitud.setText("Ubicacion Desactivada");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            TxtLatitud.setText("Ubicacion Activada");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }



    ///////////////////////////////////////////////////////////////////////////////////////



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
                    startActivityForResult(intent, TOMAR_FOTO);
                    imageUri = fotoUri;
                }

                break;

            case R.id.lnOtro:

                Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                startActivityForResult(intent2, SELEC_IMAGEN);

                /*Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                startActivityForResult(intent2.createChooser(intent2, "Seleccione la aplicacion"), 10);*/

                break;

            case R.id.btnReporte:
                indice = indice + 1;
                id = String.valueOf(indice);
                if (validarfor()==true){

                    long timestamp = System.currentTimeMillis();
                    String filePathAndName = "Colision/" + timestamp;

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
                    storageReference.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                String uploadedImageUri = "" + uriTask.getResult();
                                //sendList(uploadedImageUri, timestamp);
                                //Toast.makeText(Formulario.this, "foto enviada correctamente ", Toast.LENGTH_LONG).show();
                                //urlimagen.setText(uploadedImageUri);

                                url = uploadedImageUri;
                                Observaciones = EtxtObservaciones.getText().toString();


                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date date = new Date();

                                String fecha = dateFormat.format(date);

                                String idd= TxtLongitud.getText().toString();

                                fechasub = date;

                                Datos datos = new Datos(fechasub, url, stringlati, Observaciones,idd);

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Reportes")
                                        .add(datos)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                                                // documentReference.update("idauto", FieldValue.increment(1));
                                                //Toast.makeText(Formulario.this, "Datos guardados", Toast.LENGTH_SHORT).show();
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


                    llamaratopico();
                    Toast.makeText(Formulario.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    Intent intentt = new Intent(Formulario.this, Inicio.class);
                    startActivity(intentt);

                }else {
                    Toast.makeText(Formulario.this, "Faltan campos por llenar ", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.btnReporteDespachador:


                if (validarfor()==true) {

                    long timestampp = System.currentTimeMillis();
                    String filePathAndNamee = "Colision/" + timestampp;

                    StorageReference storageReferencee = FirebaseStorage.getInstance().getReference(filePathAndNamee);
                    storageReferencee.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                String uploadedImageUri = "" + uriTask.getResult();
                                //sendList(uploadedImageUri, timestamp);
                                //Toast.makeText(Formulario.this, "foto enviada correctamente ", Toast.LENGTH_LONG).show();
                                //urlimagen.setText(uploadedImageUri);

                                url = uploadedImageUri;
                                Observaciones = EtxtObservaciones.getText().toString();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date date = new Date();

                                String fecha = dateFormat.format(date);

                                fechasub = date;
                                String idd= TxtLongitud.getText().toString();

                                Datos datos = new Datos(fechasub, url, stringlati, Observaciones,idd);

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Reportes")
                                        .add(datos)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                                // documentReference.update("idauto", FieldValue.increment(1));
                                                //Toast.makeText(Formulario.this, "Datos guardados", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(Formulario.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(Formulario.this, RecyclerToken.class);
                    startActivity(intent4);
                    break;
                }


                else {
                    Toast.makeText(Formulario.this, "Faltan campos por llenar ", Toast.LENGTH_SHORT).show();
                }
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

        if (resultCode==RESULT_OK && requestCode == SELEC_IMAGEN) {


            assert data != null;
            imageUri = data.getData();
            ImgFotoReporte.setImageURI(data.getData());

            if (ImgFotoReporte.getDrawable()==null) {
                Txtimagen.setVisibility(View.VISIBLE);
            }else {
                Txtimagen.setVisibility(View.INVISIBLE);
            }

        }else if(resultCode == RESULT_OK && requestCode==TOMAR_FOTO) {
            Uri uri = Uri.parse(urlObtenida);
            ImgFotoReporte.setImageURI(uri);

            if (ImgFotoReporte.getDrawable()==null) {
                Txtimagen.setVisibility(View.VISIBLE);
            }else {
                Txtimagen.setVisibility(View.INVISIBLE);
            }

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

    public boolean validarfor(){
        Boolean esValido = true;

        /*if (TextUtils.isEmpty(EtxtObservaciones.getText().toString())){
            EtxtObservaciones.setError("campo requerido");
            esValido = false;
        } else {
            EtxtObservaciones.setError(null);
        }
        if (TextUtils.isEmpty(TxtLatitud.getText())){
            TxtLatitud.setError("campo requerido");
            esValido = false;
        }else{
            TxtLatitud.setError(null);
        }
        if (imageUri != null){
            Txtimagen.setVisibility(View.VISIBLE);
            esValido = false;
        }else{
            Txtimagen.setError(null);
        }*/

        if(TextUtils.isEmpty(EtxtObservaciones.getText().toString()) || TextUtils.isEmpty(TxtLatitud.getText()) || imageUri ==null){
            EtxtObservaciones.setError("campo requerido");
            TxtLatitud.setError("campo requerido");
            Txtimagen.setVisibility(View.VISIBLE);
            esValido = false;

        }else{
            EtxtObservaciones.setError(null);
            TxtLatitud.setError(null);
            Txtimagen.setError(null);
        }

        return esValido;
    }
}