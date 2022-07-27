package com.juancamilouni.iterbusmovilidad;

import java.util.ArrayList;
import java.util.List;

import Model.Userdesp;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterbusApi {

    @GET("desp")
    public Call<List<Userdesp>> listarDeps();


    @FormUrlEncoded
    @POST("login")
    Call<ArrayList<Userdesp>> login (
            @Field("email") String email,
            @Field("clave") String clave
    );

}
