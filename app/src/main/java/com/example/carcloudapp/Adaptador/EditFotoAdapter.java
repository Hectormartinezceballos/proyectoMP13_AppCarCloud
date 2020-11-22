package com.example.carcloudapp.Adaptador;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditFotoAdapter extends FirestoreRecyclerAdapter<Foto,EditFotoAdapter.ViewHolder> {
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private static  Context ctx;
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
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull Foto model) {
        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.carpeta.setText(model.getCarpeta());

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Imageviewfoto=itemView.findViewById(R.id.imageViewFoto);
            nombre=itemView.findViewById(R.id.EditText_nombre);
            descripcion=itemView.findViewById(R.id.EditText_descripcion);
            carpeta=itemView.findViewById(R.id.EditText_carpeta);

            mProgressbar=itemView.findViewById(R.id.progressBar);

        }
    }
}
