package com.example.carcloudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Principal extends AppCompatActivity {

    private Button btnlogout,btnsubirfoto,btngaleria;
    private EditText bienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        bienvenida.findViewById(R.id.bienvenido_textView);

        btngaleria.findViewById(R.id.Galeria_button);
        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnsubirfoto.findViewById(R.id.SubirFoto_Button);
        btnsubirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}