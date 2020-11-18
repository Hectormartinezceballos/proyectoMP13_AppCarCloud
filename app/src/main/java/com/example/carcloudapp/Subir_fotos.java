package com.example.carcloudapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carcloudapp.Objetos.Foto;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Subir_fotos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseStorage storage;///*****
    private FirebaseUser user;
    private FirebaseFirestore mData;
    private FirebaseDatabase databaseref;
    private ProgressDialog mprogressdialog;
    private static final int parametro=1;

    private Button subirfoto;
    private View foto;
    private EditText nombre,descripcion,carpeta;
    private String snombre,sdescripcion,scarpeta;
    private String intentoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_fotos);
        mprogressdialog=new ProgressDialog(this);


        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        //storage=FirebaseStorage.getInstance();//****
        //mStorageRef=storage.getReference();//******
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mData=FirebaseFirestore.getInstance();

        subirfoto=findViewById(R.id.button_subirfoto);

        foto =findViewById(R.id.view_foto);

        nombre       =findViewById(R.id.nombre_foto);
        descripcion  =findViewById(R.id.Descripcion_foto);
        carpeta      =findViewById(R.id.Carpeta_foto);


        subirfoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                snombre      =nombre.getText().toString();
                sdescripcion =descripcion.getText().toString();
                scarpeta     =carpeta.getText().toString();
                if (snombre.isEmpty()) {
                    nombre.setError("Introduzca Nombre de Archivo");
                }else if (sdescripcion.isEmpty()) {
                    descripcion.setError("Indique Breve Descripción");
                }else if (scarpeta.isEmpty()) {
                    carpeta.setError("Indique carpeta de almacenamiento");
                }   else {
                    abrirFotoGaleria();

                }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==parametro && resultCode==RESULT_OK){
            final Uri uri=data.getData();
            //mensaje de subida de foto a firebase
            mprogressdialog.setTitle("Almacenando foto ");
            mprogressdialog.setMessage("Almacenando foto en firebase");
            mprogressdialog.setCancelable(false);
            mprogressdialog.show();

            //StorageReference filepath = mStorageRef.child("Fotos 2").child(uri.getLastPathSegment());//almacena las fotos en fotos 2 en el firebase storage revisar para poder crear carpetas y renombrar***************
            //para pruebas como sacar las carpetas existentes en firebase
            final StorageReference filepath = mStorageRef.child(user.getUid()).child(carpeta.getText().toString()).child(nombre.getText().toString());//Funciona pone nombre a la carpeta y al archivo en el Firebase Storage
            //********************************************************************************************************************************************************


            //********************************************************************************************************************************************************
           filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mprogressdialog.dismiss();
                    Task<Uri> urldescargarFoto=taskSnapshot.getStorage().getDownloadUrl();
                    //pruebas para sacar por pantalla un url valido
                    System.out.println(" ");
                    intentoUrl=filepath.getDownloadUrl().toString();
                    System.out.println(intentoUrl);
                    System.out.println(" ");
                    Toast.makeText(Subir_fotos.this,"Foto subida con exito",Toast.LENGTH_LONG).show();
                    //*********************************************************************************************

                    urlconector(filepath.toString());

                }
            });
        }

    }
    private void urlconector(String  url){
        
        //Conecta la url de la foto del storage creando una lista nueva en cloud firestore en la coleccion fotos dentro de la coleccion users
        Foto foto=new Foto(snombre,sdescripcion,scarpeta,url);

        mData.collection("users")
                .document(user.getUid())
                .collection("Fotos")
                .document(foto.getCarpeta())
                //.collection(foto.getNombre())
                //.add(foto)
                .set(foto)
                /*.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Almacenado correctamente",Toast.LENGTH_LONG).show();
                    }
                })*/


                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"No se ha podido añadir", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}