package com.example.carcloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carcloudapp.Objetos.Foto;
import com.example.carcloudapp.Objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private EditText etnombre,etapellidos,etemail,etpassword,etphone,etdireccion;
    private Button btvalidarcuenta;
    private FirebaseAuth mAuth;
    private String email,password,nombre,apellidos,telefono,direccion;
    private FirebaseFirestore mData=FirebaseFirestore.getInstance();
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_registro);
        mAuth=FirebaseAuth.getInstance();


        etnombre=findViewById(R.id.nombre_registro);
        etapellidos=findViewById(R.id.apellidos_registro);
        etemail=findViewById(R.id.email_registro);
        etpassword=findViewById(R.id.contraseña_registro);
        btvalidarcuenta=findViewById(R.id.btn_validar);

        btvalidarcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email       = etemail.getText().toString();
                password    = etpassword.getText().toString();
                nombre      = etnombre.getText().toString();
                apellidos   = etapellidos.getText().toString();

                if (email.isEmpty()) {
                    etemail.setError("Debe introducir un correo electrónico válido");
                } else if (password.isEmpty()) {
                    etpassword.setError("Debe introducir la contraseña");
                }else if (password.length()<6){
                    etpassword.setError("La contraseña debe tener al menos 6 caracteres");
                }else if (apellidos.isEmpty()) {
                    etapellidos.setError("Debe introducir sus apellidos");
                }else if (nombre.isEmpty()) {
                    etnombre.setError("Debe introducir Un nombre");
                }else {
                    registroUsuario();

                }
            }
        });
    }


    private void registroUsuario(){
        //proceso de Registro de los usuarios

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Usuario  " +nombre+ " autenticado" ,Toast.LENGTH_LONG).show();
                    user=mAuth.getCurrentUser();
                    crearusuariobd();

                }else{
                    if(task.getException()instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(RegisterActivity.this, "El usuario ya existe",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisterActivity.this,"No fue posible registrar el usuario",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
    private void crearusuariobd(){
            //En este metodo creamos el usuario con su clase y lo subimos a firebase.
        if(user !=null) {
            Usuario usuario = new Usuario(nombre,apellidos,email,password);
            mData.collection("users").document(user.getUid())
                    .set(usuario)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RegisterActivity.this, "Registro realizado con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });



        }
    }
}
