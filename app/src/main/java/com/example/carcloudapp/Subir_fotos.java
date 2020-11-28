package com.example.carcloudapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Subir_fotos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    private FirebaseUser user;
    private FirebaseFirestore mData;
    private DatabaseReference mdatabaseReference;

    private ProgressDialog mprogressdialog;
    private static final int parametro=1;



    private Button subirfoto;
    private EditText nombre,descripcion,evento;
    private String snombre,sdescripcion,sevento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_fotos);
        mprogressdialog=new ProgressDialog(this);



        mAuth       = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mStorageRef  =FirebaseStorage.getInstance().getReference();
        mData        =FirebaseFirestore.getInstance();
        subirfoto    =findViewById(R.id.button_subirfoto);
        nombre       =findViewById(R.id.nombre_foto);
        descripcion  =findViewById(R.id.Descripcion_foto);
        evento      =findViewById(R.id.Carpeta_foto);

        subirfoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                snombre      =nombre.getText().toString();
                sdescripcion =descripcion.getText().toString();
                sevento     =evento.getText().toString();
////////////////////////////////////////////////////////////////////////////////////////////////





//////////////////////////////////////////////////////////////////////////////////////////////

                if (snombre.isEmpty()) {
                    nombre.setError("Introduzca Nombre de Archivo");
                }else if (sdescripcion.isEmpty()) {
                    descripcion.setError("Indique Breve Descripción");
                }else if (sevento.isEmpty()) {
                    evento.setError("Indique  Evento");
                }   else {
                    String path=("users/"+user.getUid()+"/Fotos/"+snombre);

                    DocumentReference reference =mData.collection("users")
                            .document(user.getUid())
                            .collection("Fotos")
                            .document(snombre);
                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                    nombre.setError("El archivo ya existe");
                                    return;
                                } else if (document.exists()==false){
                                    Log.d("TAG", "No such document");
                                }   abrirFotoGaleria();
                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                                nombre.setError("Error de conexion");

                            }
                        }
                    });


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
            //le ponemos la direccion donde se va a guardar en filebaseStorage.
            final StorageReference filepath = mStorageRef.child(user.getUid()).child(nombre.getText().toString());

           filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               //Sube la foto a firebase Storage y recupera el url que almacenamos en el metodo urlconector para crear un objeto del tipo foto
               @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String url=task.getResult().toString();
                            Log.i("URL",url);
                            urlconector(url);
                            mprogressdialog.dismiss();
                        }
                    });
                }
           })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                           Toast.makeText(Subir_fotos.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
                    Toast.makeText(Subir_fotos.this,"Foto subida con exito",Toast.LENGTH_LONG).show();

        }

    }
    private void urlconector(String  url){
        
        //Conecta la url de la foto del storage creando una lista nueva en cloud firestore en la coleccion fotos dentro de la coleccion users
        Foto foto=new Foto(snombre,sdescripcion,sevento,url);
        Query documentos=mData.collection("users/"+user.getUid()+"/Fotos").whereEqualTo("nombre",true);

        mData.collection("users")
                .document(user.getUid())
                .collection("Fotos")
                .document(foto.getNombre())
                .set(foto)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"No se ha podido añadir", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}