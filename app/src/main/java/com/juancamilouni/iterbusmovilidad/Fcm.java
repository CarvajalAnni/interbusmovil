package com.juancamilouni.iterbusmovilidad;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;
import java.util.UUID;

// Para cuando el telefono este bloqueado o apagada la pantalla
public class Fcm extends FirebaseMessagingService {
    public static  String tk;


    @Override
    // el id del dispositivo notificacion especifica
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //String correo = Dashboard.correo;
        Log.e("token", "mi token es:" + token);
        guardartoken(token);
        tk=token;

    }

    private void guardartoken(String token) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String id = UUID.randomUUID().toString();
        Token token1 = new Token();
        token1.setId(id);
        token1.setNombre("");
        token1.setCorreo("");
        token1.setToken(token);
        ref.child("Datos").child(token1.getId()).setValue(token1);//orden de los usuarios

    }
    // recibir todas las notificaciones validar si es mensaje version de cel
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remotemessage) {
        super.onMessageReceived(remotemessage);
        // nos dira el id de la persona que la envia

        String from = remotemessage.getFrom();

        //clave valor desde firebase
        if(remotemessage.getData().size() >0){
            String titulo = remotemessage.getData().get("titulo");
            String detalle = remotemessage.getData().get("detalle");
            // enviar la notificacion enviar

            mayorqueoreo(titulo,detalle);

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
        Intent nf = new Intent(getApplicationContext(),Inicio.class);
        nf.putExtra("color","rojo");
        // si ya esta abierta no se vuelva abrir sino que cargue la informacion y muestre la notificacion y evitar estar abriendo varias notificaciones
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, 0, nf,0);

    }
}
