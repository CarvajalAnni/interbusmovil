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

public class AdaptadorToken extends RecyclerView.Adapter<AdaptadorToken.ViewHolder> implements View.OnClickListener  {
    List<Token> listToken;
    Context context;

    private View.OnClickListener listener;


    public AdaptadorToken(Context context,List<Token> listToken) {
        this.listToken = listToken;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_token, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Token token = listToken.get(position);
            holder.nombre.setText(token.getNombre());
            holder.correo.setText(token.getCorreo());
            holder.token1.setText(token.getToken());


    }

    @Override
    public int getItemCount() {
        return listToken.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }


    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView correo;
        TextView token1;
        Context context2;

        public ViewHolder(View view) {
            super(view);

            context2= view.getContext();
            nombre = view.findViewById(R.id.nombre1);
            correo= view.findViewById(R.id.correo1);
            token1 = view.findViewById(R.id.tokens);


        }
    }



}
