package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Token;

public class RecyclerToken extends AppCompatActivity {

    List<Token> listToken;
    AdaptadorToken adaptadortoken;

    FirebaseFirestore db;
    RecyclerView recyclerView;
    String tok,conductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_token);

        db= FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.despachadores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listToken= new ArrayList<Token>();
        llenarLista();
    }

    private void llenarLista() {
        //Traer la coleccion de url
        db.collection("token")/*.orderBy("tiempo", Query.Direction.DESCENDING)*/.limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //Toast.makeText(RecyclerActivity.this, ""+document.getData(), Toast.LENGTH_SHORT).show();
                                //String url = document.getData().toString();

                                String nombre = document.getString("nombre");
                                String correo = document.getString("correo");
                                String tokena = document.getString("token");

                                //String sSubCadena1 = Cadenaurl.substring(5);
                                //Toast.makeText(RecyclerActivity.this, ""+ sSubCadena, Toast.LENGTH_SHORT).show();
                                Token token = new Token (nombre,correo,tokena);
                                listToken.add(token);
                               // Toast.makeText(RecyclerToken.this, ""+listToken, Toast.LENGTH_SHORT).show();
                                // listDatos.add(new Datos(url));
                                adaptadortoken= new AdaptadorToken(RecyclerToken.this,listToken);

                                //metodo click
                                adaptadortoken.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(RecyclerToken.this, "selecciono: "+listToken.get(recyclerView.getChildAdapterPosition(view)).getToken(), Toast.LENGTH_SHORT).show();
                                        tok=listToken.get(recyclerView.getChildAdapterPosition(view)).getToken();
                                        //conductor=listToken.get(recyclerView.getChildAdapterPosition(view)).getNombre();
                                        Toast.makeText(RecyclerToken.this, tok, Toast.LENGTH_LONG).show();
                                        llamarespecifico();
                                    }
                                });

                                recyclerView.setAdapter(adaptadortoken);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
    }


    private void llamarespecifico() {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            // tomar el valor de token
            //String token = "c4TL4LrkQjWWXuQWmmXXZA:APA91bHlVCTd-SOZWibl31J8XSG9qu4MaA3EfSEDJnUJcaiDl_gRyP2o8nhY8shutkBnBoRKHww3_LHE2MorP7lDmMt2j8yiuUUitArlvT-n7PsYwkw5aLE4SeB4OF7OtcnqpGzD5qw4";
            String token= tok;
            json.put("to", token);
            JSONObject notificacion = new JSONObject();

            // notificacion que se recibe
            notificacion.put("titulo", "Interbus");
            notificacion.put("detalle", "Un conductor te ha notificado ");
            //notificacion.put("nombre", conductor);

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
}