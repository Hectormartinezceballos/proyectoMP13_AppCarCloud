package com.example.carcloudapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class PantallaPrincipal extends AppCompatActivity {

    private Button btngaleria, btnsubirfoto, btncerrarsesion;
     private TextView bienvenida;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private static final int parametro =1;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        mAuth=FirebaseAuth.getInstance();
        mprogressdialog=new ProgressDialog(this);

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

        //Abrir la galeria
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Selecciona una imagen"),parametro);



    }
    //Subida de fotos al Firebase Storage*********************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==parametro && resultCode==RESULT_OK){
            final Uri uri=data.getData();
            //mensaje de subida de foto a firebase
            mprogressdialog.setTitle("Almacenando foto ");
            mprogressdialog.setMessage("Almacenando foto en firebase");
            mprogressdialog.setCancelable(false);
            mprogressdialog.show();

            StorageReference filepath = mStorageRef.child("Fotos 2").child(uri.getLastPathSegment());//almacena las fotos en fotos 2 en el firebase storage revisar para poder crear carpetas y renombrar***************


            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mprogressdialog.dismiss();
                    Task<Uri> UrldescargarFoto=taskSnapshot.getStorage().getDownloadUrl();

                    Toast.makeText(PantallaPrincipal.this,"Foto subida con exito",Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}