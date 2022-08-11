package com.juancamilouni.iterbusmovilidad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import Model.Datos;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> implements View.OnClickListener{
    List<Datos> listDatos;
    Context context;
    private View.OnClickListener listener;
    public static int ban=0;


    public Adaptador(Context context, List<Datos> listDatos) {
        this.listDatos = listDatos;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datos datos = listDatos.get(position);

        holder.url.setText(datos.getUrl());
        holder.fecha.setText(DateFormat.format("EEEE dd/LLLL/yyyy HH:mm:ss",datos.getTiempo()));
        Glide.with(context).load(datos.getUrl()).into(holder.foto);
        holder.ubicacion.setText(datos.getUbicacion());
        holder.observa.setText(datos.getObservaciones());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        Context context1;
        TextView fecha;
        TextView url;
        TextView ubicacion;
        TextView observa;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context1= itemView.getContext();
            url= itemView.findViewById(R.id.urlItem);
            fecha = itemView.findViewById(R.id.Fecha);
            ubicacion = itemView.findViewById(R.id.Latitud);
            observa = itemView.findViewById(R.id.Observacion);
            foto = (ImageView) itemView.findViewById(R.id.Imagen1);

        }
    }
}
