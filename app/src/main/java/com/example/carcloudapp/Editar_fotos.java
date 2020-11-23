package com.example.carcloudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carcloudapp.Adaptador.EditFotoAdapter;
import com.example.carcloudapp.Adaptador.FotoAdapter;
import com.example.carcloudapp.Objetos.Foto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Editar_fotos extends AppCompatActivity {


        RecyclerView recyclerViewFoto;
        EditFotoAdapter mAdapter;
        FirebaseFirestore mFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String usuario=user.getUid();
        //********************************************************************en pruebas debajo
        FirebaseStorage storage=FirebaseStorage.getInstance();

        public static Context ctx;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editar_fotos);
            //apuntamos hacia el layout que vamos a representar en el recyclerview
            recyclerViewFoto=findViewById(R.id.recyclerview_fotos);
            recyclerViewFoto.setLayoutManager(new LinearLayoutManager(this));

            CollectionReference query=mFirestore.collection("users/"+usuario+"/Fotos");
            FirestoreRecyclerOptions<Foto> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<Foto>().setQuery(query,Foto.class).build();
            ctx=getApplicationContext();
            mAdapter=new EditFotoAdapter(firestoreRecyclerOptions,ctx);
            mAdapter.notifyDataSetChanged();
            recyclerViewFoto.setAdapter(mAdapter);



        }

        @Override
        protected void onStart() {
            super.onStart();
            mAdapter.startListening();
        }

        @Override
        protected void onStop() {
            super.onStop();
            mAdapter.stopListening();
        }
       private void editarElementosFireBase() {
            String FotoId="users/"+usuario+"/Fotos/".toString();
            mFirestore.collection(FotoId);

            //Crear metodo para leer y modificar datos obtenidos de firebase

       }

}