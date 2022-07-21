package com.juancamilouni.iterbusmovilidad;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import Model.Datos;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{
    List<Datos> listDatos;
    Context context;

    public Adaptador(Context context, List<Datos> listDatos) {
        this.listDatos = listDatos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        //view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Datos datos = listDatos.get(position);

        //viewHolder.dateView.setText(DateFormat.format("dd/MM/yyyy", dateColumnIndex));

        holder.url.setText(datos.getUrl());
        holder.fecha.setText(DateFormat.format("EEEE dd/LLLL/yyyy HH:mm:ss",datos.getTiempo()));
        //holder.fecha.setText(datos.getTiempo().toString());
        Glide.with(context).load(datos.getUrl()).into(holder.foto);
        holder.ubicacion.setText(datos.getUbicacion());
        holder.observa.setText(datos.getObservaciones());

        holder.setOnClickListener();

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    /*@Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }

    }*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context1;
        TextView fecha;

        TextView url;
        TextView ubicacion;
        TextView observa;
        ImageView foto;
        Button detalles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context1= itemView.getContext();

            url= itemView.findViewById(R.id.urlItem);
            fecha = itemView.findViewById(R.id.Fecha);
            ubicacion = itemView.findViewById(R.id.Latitud);
            observa = itemView.findViewById(R.id.Observacion);
            foto = (ImageView) itemView.findViewById(R.id.Imagen1);
            detalles = itemView.findViewById(R.id.verDetalle);
        }

         void setOnClickListener() {
            detalles.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.verDetalle:
                    Intent intent= new Intent(context1,DetallesRecycler.class);
                    intent.putExtra("ubicacion",ubicacion.getText());
                    intent.putExtra("fecha",fecha.getText());
                    intent.putExtra("observacion",observa.getText());
                    intent.putExtra("foto",url.getText());
                    context1.startActivity(intent);
                    break;
            }
        }
    }
}
