package com.example.carcloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PantallaPrincipal extends AppCompatActivity {

    private Button btngaleria, btnsubirfoto, btncerrarsesion;
     private TextView bienvenida;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private static final int parametro =1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        mAuth=FirebaseAuth.getInstance();

        bienvenida=findViewById(R.id.bienvenido_textView);
        btngaleria=findViewById(R.id.Galeria_button);
        btnsubirfoto=findViewById(R.id.SubirFoto_Button);
        btncerrarsesion=findViewById(R.id.logout);

        //al pulsar sobre el boton galeria
        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Al pulsar sobre el boton subir foto
        btnsubirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               abrirFotoGaleria();


            }
        });

        btncerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PantallaPrincipal.this,MainActivity.class));
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void abrirFotoGaleria(){

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Selecciona una imagen"),parametro);
    }


}