package com.example.carcloudapp.Adaptador;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.carcloudapp.Objetos.Foto;
import com.example.carcloudapp.R;
import com.example.carcloudapp.Ver_fotos;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FotoAdapter extends FirestoreRecyclerAdapter<Foto,FotoAdapter.ViewHolder> {

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

    public FotoAdapter(@NonNull FirestoreRecyclerOptions<Foto> options,Context ctex) {


        super(options);
        ctx=ctex;
    }

    @Override//Establece los valores de las vistas
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull Foto model) {
        holder.textViewNombre.setText(model.getNombre());
        holder.textViewDescripcion.setText(model.getDescripcion());
        holder.imageview2.setImageResource(R.drawable.ic_launcher_background);

        //cargar foto desde URL en firebase

       String string=storage.toString();
        Glide.with(ctx )
                .load(model.getUrl())
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                   @Override
                   public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                       holder.mProgressbar.setVisibility(View.GONE);
                       holder.imageview2.setVisibility(View.VISIBLE);
                       holder.imageview2.setImageResource(R.drawable.bmwe39);
                       return false;
                   }

                   @Override
                   public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       holder.mProgressbar.setVisibility(View.GONE);
                       holder.imageview2.setVisibility(View.VISIBLE);
                       return false;
                   }
               })

                .into(holder.imageview2);

    }

    @NonNull
    @Override//metodo que va a crear las vistas que necesitamos mostrar
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_fotos,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNombre;
        TextView textViewDescripcion;

        ImageView imageview2;
        ProgressBar mProgressbar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre=itemView.findViewById(R.id.textView_nombre);
            textViewDescripcion=itemView.findViewById(R.id.textView_descripcion);
            imageview2=itemView.findViewById(R.id.imageView2);
            mProgressbar=itemView.findViewById(R.id.progressBar);

        }
    }

}
