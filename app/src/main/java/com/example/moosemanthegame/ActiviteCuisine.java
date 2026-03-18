package com.example.moosemanthegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ActiviteCuisine extends AppCompatActivity
{
    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_cuisine);

        // 1. Récupération des vues
        FrameLayout container = findViewById(R.id.drawing_container);
        ImageView elanImage = findViewById(R.id.elan_image);
        Button btnDonner = findViewById(R.id.btn_donner);

        // 2. Initialisation de l'image de l'élan (MooseMan.png)
        // Note: Assure-toi que MooseMan.png est bien dans res/drawable
        elanImage.setImageResource(R.drawable.mooseman);

        // 3. Ajout de la zone de dessin
        drawingView = new DrawingView(this);
        container.addView(drawingView);

        // 4. Logique du bouton "Donner"
        btnDonner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentRetour = new Intent();
                intentRetour.putExtra("repas_pret", true);
                setResult(RESULT_OK, intentRetour);
                finish();
            }
        });
    }
}