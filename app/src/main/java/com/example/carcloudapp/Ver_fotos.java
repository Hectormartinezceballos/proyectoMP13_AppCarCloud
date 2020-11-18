package com.example.carcloudapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.carcloudapp.Adaptador.FotoAdapter;
import com.example.carcloudapp.Objetos.Foto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Ver_fotos extends AppCompatActivity {


    RecyclerView recyclerViewFoto;
    FotoAdapter mAdapter;
    FirebaseFirestore mFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    String usuario=user.getUid();
    //********************************************************************en pruebas debajo
    FirebaseStorage storage=FirebaseStorage.getInstance();
   // StorageReference storageRef=storage.getReferenceFromUrl(usuario);
    public static Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_fotos);

        recyclerViewFoto=findViewById(R.id.recyclerview_fotos);
        recyclerViewFoto.setLayoutManager(new LinearLayoutManager(this));
        System.out.println();
        System.out.println("users/"+usuario+"/Fotos");
        System.out.println();
        CollectionReference query=mFirestore.collection("users/"+usuario+"/Fotos");
        FirestoreRecyclerOptions<Foto> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<Foto>().setQuery(query,Foto.class).build();
       ctx=getApplicationContext();
        mAdapter=new FotoAdapter(firestoreRecyclerOptions,ctx);
        mAdapter.notifyDataSetChanged();
        recyclerViewFoto.setAdapter(mAdapter);
        //************en pruebas debajo para glide


        //*********************************



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
}