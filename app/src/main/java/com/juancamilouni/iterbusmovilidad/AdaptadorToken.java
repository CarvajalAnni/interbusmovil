package com.juancamilouni.iterbusmovilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Model.Token;

public class AdaptadorToken extends RecyclerView.Adapter<AdaptadorToken.ViewHolder> {
    List<Token> listToken;
    Context context;

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
        holder.correo.setText(token.getCorreo().toString());
        holder.token.setText(token.getToken());

    }

    @Override
    public int getItemCount() {
        return listToken.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre;
        TextView correo;
        TextView token;
        Button enviar;
        Context context1;

        public ViewHolder(View view) {
            super(view);

            context1= view.getContext();
            nombre = view.findViewById(R.id.nombre1);
            correo= view.findViewById(R.id.correo1);
            token = view.findViewById(R.id.tokens);
            enviar = view.findViewById(R.id.enviarnoti);

        }
        void setOnClickListener() {
            enviar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //boton enviar



        }
    }
}
