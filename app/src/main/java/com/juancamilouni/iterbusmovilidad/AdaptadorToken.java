package com.juancamilouni.iterbusmovilidad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Token;

public class AdaptadorToken extends RecyclerView.Adapter<AdaptadorToken.ViewHolder> {
    List<Token> listToken;
    Context context;

    String tok;

    public AdaptadorToken(Context context,List<Token> listToken) {
        this.listToken = listToken;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_token, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Token token = listToken.get(position);
        holder.nombre.setText(token.getNombre());
        holder.correo.setText(token.getCorreo());
        holder.token1.setText(token.getToken());

        tok= token.getToken();

        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return listToken.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre;
        TextView correo;
        TextView token1;
        Button enviar;
        Context context2;

        public ViewHolder(View view) {
            super(view);

            context2= view.getContext();
            nombre = view.findViewById(R.id.nombre1);
            correo= view.findViewById(R.id.correo1);
            token1 = view.findViewById(R.id.tokens);
            enviar = view.findViewById(R.id.enviarnoti);


        }
        void setOnClickListener() {
            enviar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //boton enviar




            switch (v.getId()){
                case R.id.enviarnoti:
                    Intent intent= new Intent(context2,DetallesRecycler.class);
                    //Toast.makeText(RecyclerToken.this, Toast.LENGTH_LONG, "boton presionado").show();
                    Toast.makeText(context, tok, Toast.LENGTH_LONG).show();
                    llamarespecifico();
                    context2.startActivity(intent);

                    break;
            }


        }
    }

    private void llamarespecifico() {
        RequestQueue myrequest = Volley.newRequestQueue(context.getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            // tomar el valor de token
            //String token = "c4TL4LrkQjWWXuQWmmXXZA:APA91bHlVCTd-SOZWibl31J8XSG9qu4MaA3EfSEDJnUJcaiDl_gRyP2o8nhY8shutkBnBoRKHww3_LHE2MorP7lDmMt2j8yiuUUitArlvT-n7PsYwkw5aLE4SeB4OF7OtcnqpGzD5qw4";
            String token= tok;
            json.put("to", token);
            JSONObject notificacion = new JSONObject();

            // notificacion que se recibe
            notificacion.put("titulo", "Interbus");
            notificacion.put("detalle", "Un conductor te ha notificado");

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
