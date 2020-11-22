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
   /* private void eliminarElemento() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_delete_forever_36)
                .setTitle("¿Borrar definitivamente?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        docRef.collection(elementList.getNombre())
                                .document(element.getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Elemento eliminado", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"No se ha podido eliminar", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        docRef.update("numElementos", FieldValue.increment(-1));
                    }
                }).show();
    }
    public void loadElement(){
        element = (Element) getIntent().getSerializableExtra("element");
        elementList = (ElementList) getIntent().getSerializableExtra("elementList");
        toolbar.setTitle(element.getNombre());
        etName.setText(element.getNombre());
        etDescription.setText(element.getDescripcion());
        etDonde.setText(element.getDonde());
        etImage.setText(element.getImagen());
        ratingBar.setRating(element.getPuntuacion());

        if (!"".equals(element.getImagen())){
            Glide.with(getApplicationContext())
                    .load(element.getImagen())
                    .fitCenter()
                    .into(ivImage);
        } else{
            String noImageAvailable = "https://syslint.com/wp-content/themes/cool/images/no_image.jpg";
            Glide.with(getApplicationContext())
                    .load(noImageAvailable)
                    .centerInside()
                    .into(ivImage);
        }
    }*/
}