package com.example.carcloudapp.Adaptador;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.carcloudapp.Objetos.Foto;
import com.example.carcloudapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditFotoAdapter extends FirestoreRecyclerAdapter<Foto,EditFotoAdapter.ViewHolder> {
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private FirebaseFirestore mData;
    private static  Context ctx;
    String url;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EditFotoAdapter(@NonNull FirestoreRecyclerOptions<Foto> options, Context ctex) {
        super(options);
        ctx=ctex;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final Foto model) {
        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.carpeta.setText(model.getCarpeta());
        url=model.getUrl();


        //cargar foto desde URL en firebase

        String string=storage.toString();
        Glide.with(ctx )
                .load(model.getUrl())
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.mProgressbar.setVisibility(View.GONE);
                        holder.Imageviewfoto.setVisibility(View.VISIBLE);
                        holder.Imageviewfoto.setImageResource(R.drawable.bmwe39);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.mProgressbar.setVisibility(View.GONE);
                        holder.Imageviewfoto.setVisibility(View.VISIBLE);
                        return false;
                    }
                })

                .into(holder.Imageviewfoto);
        //Introducimos la funcionalidad en el boton actualizar y guardar datos en el cual podremos variar nombre, carpeta o descripción y guardar datos.
        holder.editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre1,descripcion1,carpeta1,url1;
                nombre1=holder.nombre.getText().toString();
                descripcion1=holder.descripcion.getText().toString();
                carpeta1=holder.carpeta.getText().toString();
                url1=url;


                Foto foto=new Foto(nombre1,descripcion1,carpeta1,url1);
                mData=FirebaseFirestore.getInstance();
                mData.collection("users")
                        .document(user.getUid())
                        .collection("Fotos")
                        .document(foto.getCarpeta())
                        .set(foto)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ctx,"No se ha podido añadir", Toast.LENGTH_SHORT).show();
                            }
                        });
                Toast.makeText(ctx,"Cambios guardados correctamente",Toast.LENGTH_LONG).show();
            }
        });

        holder.borrarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData=FirebaseFirestore.getInstance();
                mData.collection("users")
                        .document(user.getUid())
                        .collection("Fotos")
                        .document(model.getCarpeta())
                        .delete();
                Toast.makeText(ctx,"Archivo eliminado",Toast.LENGTH_LONG).show();
            }
        });



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.editarfotoview,parent,false);
        return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Imageviewfoto;
        EditText nombre,descripcion,carpeta;
        ProgressBar mProgressbar;
        Button editarFoto,borrarFoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Imageviewfoto=itemView.findViewById(R.id.imageViewFoto);
            nombre=itemView.findViewById(R.id.EditText_nombre);
            descripcion=itemView.findViewById(R.id.EditText_descripcion);
            carpeta=itemView.findViewById(R.id.EditText_carpeta);
            editarFoto=itemView.findViewById(R.id.btn_editarfoto);
            borrarFoto=itemView.findViewById(R.id.btn_borrarfoto);

            mProgressbar=itemView.findViewById(R.id.progressBar);

        }
    }
}