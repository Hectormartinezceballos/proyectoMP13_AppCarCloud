package com.example.carcloudapp;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcloudapp.Adaptador.EditFotoAdapter;
import com.example.carcloudapp.Objetos.Foto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Editar_fotos extends AppCompatActivity {


        RecyclerView recyclerViewFoto;
        EditFotoAdapter mAdapter;
        FirebaseFirestore mFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String usuario=user.getUid();
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


}