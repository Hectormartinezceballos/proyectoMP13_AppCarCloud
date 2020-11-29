package com.example.carcloudapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PantallaPrincipal extends AppCompatActivity {

    private Button btngaleria, btnsubirfoto,btnEditar, btncerrarsesion;
     private TextView bienvenida;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private static final int parametro =1;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mData;

    private ProgressDialog mprogressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mData=FirebaseFirestore.getInstance();

        mprogressdialog=new ProgressDialog(this);


        bienvenida=findViewById(R.id.bienvenido_textView);
        btngaleria=findViewById(R.id.Galeria_button);
        btnsubirfoto=findViewById(R.id.SubirFoto_Button);
        btnEditar=findViewById(R.id.Editar_foto);
        btncerrarsesion=findViewById(R.id.logout);

        //al pulsar sobre el boton galeria cambiamos de activity a Ver_fotos
        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(PantallaPrincipal.this,Ver_fotos.class);
            startActivity(intent);
            }
        });

        //Al pulsar sobre el boton subir foto cambiamos de activity a Subir_fotos
        btnsubirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PantallaPrincipal.this,Subir_fotos.class);
                startActivity(intent);

            }
        });
        //Al pulsar sobre el b oton editar cambiamos de activity a Editar_fotos
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PantallaPrincipal.this,Editar_fotos.class);
                startActivity(intent);
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


}