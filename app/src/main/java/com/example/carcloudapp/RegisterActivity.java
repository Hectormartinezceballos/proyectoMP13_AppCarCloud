package com.example.carcloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    private EditText etnombre,etapellidos,etemail,etpassword,etphone,etdireccion;
    private Button btvalidarcuenta;
    private FirebaseAuth mAuth;
    private String email,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_registro);
        mAuth=FirebaseAuth.getInstance();

        etnombre=findViewById(R.id.nombre_registro);
        etapellidos=findViewById(R.id.apellidos_registro);
        etemail=findViewById(R.id.email_registro);
        etpassword=findViewById(R.id.contraseña_registro);
        etphone=findViewById(R.id.telefono_registro);
        etdireccion=findViewById(R.id.direccion_registro);
        btvalidarcuenta=findViewById(R.id.btn_validar);

        btvalidarcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= etemail.getText().toString().trim();
                password= etpassword.getText().toString().trim();

                if (email.isEmpty()) {
                    etemail.setError("Debe introducir un correo electrónico válido");
                } else if (password.isEmpty()) {
                    etpassword.setError("Debe introducir la contraseña");
                } else {
                    registroUsuario();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        }

    private void registroUsuario(){
        //proceso de Registro de los usuarios

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(RegisterActivity.this,"Usuario  " +email+ " registrado" ,Toast.LENGTH_LONG).show();
                    Intent Principal=new Intent(RegisterActivity.this,PantallaPrincipal.class);
                    startActivity(Principal);

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
}
