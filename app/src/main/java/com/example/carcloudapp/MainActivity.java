package com.example.carcloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etusuario,etpassword;
    private Button btiniciarsesion,btcrearcuenta;
    private String email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etpassword=findViewById(R.id.Password_editText);
        etusuario=findViewById(R.id.Usuario_editText);
        btiniciarsesion=findViewById(R.id.start_session_button);
        btcrearcuenta=findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();

        btiniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etusuario.getText().toString().trim();
                password = etpassword.getText().toString().trim();

                if (email.isEmpty()) {
                    etusuario.setError("Debe introducir un correo electrónico válido");
                } else if (password.isEmpty()) {
                    etpassword.setError("Debe introducir la contraseña");
                } else {
                    iniciarSesion();
                }
            }
        });

        btcrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser logedUser=mAuth.getCurrentUser();
        if(logedUser!=null){
            updateUI(logedUser);
        }










    }
    private void iniciarSesion(){
         //proceso de autenticación de los usuarios

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(MainActivity.this,"Bienvenido "+ email,Toast.LENGTH_LONG).show();
                    Intent principal=new Intent(MainActivity.this,PantallaPrincipal.class);
                    startActivity(principal);

                }else{
                    if(task.getException()instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(MainActivity.this, "El usuario ya existe",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this,"Usuario, email y/o contraseña incorrectos",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(MainActivity.this, PantallaPrincipal.class);
            startActivity(intent);
            finish();

        } else {

                etpassword.setError("Usuario, email y/o contraseña incorrectos");
                etpassword.requestFocus();
            }

    }
}
