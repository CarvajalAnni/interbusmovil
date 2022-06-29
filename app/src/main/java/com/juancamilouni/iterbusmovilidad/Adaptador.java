package com.juancamilouni.iterbusmovilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import Model.Datos;
import Model.Observaciones;
import Model.Ubicacion;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    List<Datos> listDatos;
    Context context;

    public Adaptador(Context context, List<Datos> listDatos) {
        this.listDatos = listDatos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datos datos=listDatos.get(position);



        holder.fecha.setText(datos.getTiempo().toString());
        Glide.with(context).load(datos.getUrl()).into(holder.foto);
        holder.ubicacion.setText(datos.getUbicacion());
        holder.observa.setText(datos.getObservaciones());

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fecha;
        TextView ubicacion;
        TextView observa;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.Fecha);
            ubicacion = itemView.findViewById(R.id.Latitud);
            observa = itemView.findViewById(R.id.Observacion);
            foto = (ImageView) itemView.findViewById(R.id.Imagen1);
        }
    }
}
