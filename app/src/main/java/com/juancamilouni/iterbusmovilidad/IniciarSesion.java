package com.juancamilouni.iterbusmovilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.regex.Pattern;

import Model.Userdesp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IniciarSesion extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    LinearLayout bntGoogle;
    int RC_SIGN_IN = 1;
    String content = "", buscar="despachador", buscar2="admin";
    EditText etxemail, etxcontra;
    Button btningresar;
    public static String TAG = "GoogleSignIn", correoString, contraaseniaString;
    public static EditText correo, contrasenia;

    public static int formainicio=0;

    public static String correoenvia,nombreenvia,rolenvia,contraenvia;


    //navegacion
    //BottomNavigationView navegacion;


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInCliebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //quita el titulo de la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        referenciar();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void referenciar() {
        correo = findViewById(R.id.idTxtCorreo);
        contrasenia = findViewById(R.id.idTxtContrasenia);

        //botones de ingreso

        //boton ingresar con GOOGLE
        bntGoogle = findViewById(R.id.bntGoogle);
        bntGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
                formainicio=1;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInCliebt = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        //boton ingresar con formulario

        //formainicio=0;
        btningresar = findViewById(R.id.button);
        btningresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                formainicio=2;

                correoString = correo.getText().toString();
                contraaseniaString = contrasenia.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://interbusapi.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InterbusApi interbusApi = retrofit.create(InterbusApi.class);
                Call<ArrayList<Userdesp>> call = interbusApi.login(correo.getText().toString(), contrasenia.getText().toString());

                call.enqueue(new Callback<ArrayList<Userdesp>>() {
                                 @Override
                                 public void onResponse(Call<ArrayList<Userdesp>> call, Response<ArrayList<Userdesp>> response) {
                                     if (!response.isSuccessful()) {
                                         Toast.makeText(IniciarSesion.this, "Email o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                                     } else {
                                         ArrayList<Userdesp> listausu = response.body();

                                         for (Userdesp userdesp : listausu) {
                                             /*content += "email = " + userdesp.getEmail() + " \n";
                                             content += "clave = " + userdesp.getClave() + " \n";
                                             content += "rol = " + userdesp.getRol() + " \n";*/

                                             correoenvia= userdesp.getEmail();
                                             nombreenvia= userdesp.getNombre();
                                             contraenvia= userdesp.getClave();
                                             rolenvia= userdesp.getRol();
                                         }

                                         for (int i=0; i<listausu.size();i++){
                                             if (listausu.get(i).getRol().equalsIgnoreCase(buscar)) {
                                                 Intent intent1 = new Intent(IniciarSesion.this, Inicio.class);
                                                 startActivity(intent1);

                                             } else if (listausu.get(i).getRol().equalsIgnoreCase(buscar2)){
                                                 Intent intent = new Intent(IniciarSesion.this, Inicio.class);
                                                 startActivity(intent);
                                             } else {
                                                 Toast.makeText(IniciarSesion.this, "Debes ser Despachador o Admin ", Toast.LENGTH_LONG).show();
                                             }

                                         }

                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<ArrayList<Userdesp>> call, Throwable t) {
                                     Log.e("ErrorFailure=> ", String.valueOf(t.getMessage()));
                                 }
                });


                if (!(validarEmail(correoString)) || !(validarcontrasenas(contraaseniaString))) {
                    //
                } else {
                    //Intent intennt6 = new Intent(IniciarSesion.this, Inicio.class);

                    //finish();
                }





            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In fallido, actualizar GUI
                    Log.w(TAG, "Google sign in failed", e);
                }
            } else {
                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) { //si no es null el usuario ya esta logueado
            //mover al usuario al dashboard
            Intent dashboardActivity = new Intent(IniciarSesion.this, Inicio.class);
            startActivity(dashboardActivity);
        }
        super.onStart();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
//Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                            Intent dashboardActivity = new Intent(IniciarSesion.this, Inicio.class);
                            startActivity(dashboardActivity);
                            IniciarSesion.this.finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInCliebt.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Boolean esValido = true;
        if (!pattern.matcher(email).find()) {
            correo.setError("Email invalido");
            esValido = false;
        } else {
            esValido = true;
        }
        return pattern.matcher(email).matches();
    }

    public boolean validarcontrasenas(String contrasenas) {

        Boolean esValido = true;
        Pattern mayusculas = Pattern.compile("[A-Z]");
        Pattern minusculas = Pattern.compile("[a-z]");
        Pattern numeros = Pattern.compile("[0-9]");

        if (contrasenas.isEmpty()){
            contrasenia.setError("El campo no puede estar vacio");
            return false;

        }


        if (!minusculas.matcher(contrasenas).find()) {
            contrasenia.setError("contraseña invalida");
            return false;
        }
        if (!mayusculas.matcher(contrasenas).find()) {
            contrasenia.setError("contraseña invalida");

            return false;
        }
        if (!numeros.matcher(contrasenas).find()) {
            contrasenia.setError("contraseña invalida");
            return false;

        }
        if (contrasenas.length() < 8) {
            contrasenia.setError("contraseña invalida");
            return false;
        }

        return esValido;
    }

}






