package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Model.Token;

// Para cuando el telefono este bloqueado o apagada la pantalla
public class Fcm extends FirebaseMessagingService {

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    DatabaseReference mDatabase;
    FirebaseFirestore db;
    static String id;



    @Override
    // el id del dispositivo notificacion especifica
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.e("token", "Tu token es:" + token);
        guardartoken(token);

        db= FirebaseFirestore.getInstance();

    }


    private void guardartoken(String token) {

        Token token1 = new Token();
        token1.setToken(token);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("token")
                .add(token1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        //Toast.makeText(Fcm.this, "token obtenido", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Fcm.this, documentReference.getId(), Toast.LENGTH_SHORT).show();
                        id= documentReference.getId();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(Fcm.this, "Datos f", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    // recibir todas las notificaciones validar si es mensaje version de cel
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remotemessage) {
        super.onMessageReceived(remotemessage);
        // nos dira el id de la persona que la envia

        //String from = remotemessage.getFrom();


        //clave valor desde firebase
        if(remotemessage.getData().size() >0){
            String titulo = remotemessage.getData().get("titulo");
            String detalle = remotemessage.getData().get("detalle");
            //String nombre = remotemessage.getFrom();

            // enviar la notificacion enviar
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
                mayorqueoreo(titulo,detalle);

            }


        }
    }

    private void mayorqueoreo(String titulo, String detalle) {

        String id= "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new  NotificationChannel(id, "nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);

        }



        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())// a que hora se envio la notificacion
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                //.setContentText(nombre)
                .setContentIntent(clicknoti())
                .setContentInfo("nuevo");



        // si queremos varias notificaciones se crean varias id y se remplazan entonces se crea esto
        Random random = new Random();
        int idNotify = random.nextInt(8000);// para que cree numeros aleatorios grandes
        // no colapse la aplicacion

        nm.notify(idNotify,builder.build());
    }
    // para que cuando presione la notificacion se vaya alguna actividad
    public PendingIntent clicknoti(){
        Intent nf = new Intent(getApplicationContext(),RecyclerActivity.class);
        nf.putExtra("color","rojo");
        // si ya esta abierta no se vuelva abrir sino que cargue la informacion y muestre la notificacion y evitar estar abriendo varias notificaciones
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, 0, nf,0);

    }


    //

}
