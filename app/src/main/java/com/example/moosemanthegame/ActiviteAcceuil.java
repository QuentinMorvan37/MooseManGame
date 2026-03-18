package com.example.moosemanthegame;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class ActiviteAcceuil extends AppCompatActivity
{
    private String sexeSelectionne = "M";
    private String langueSelectionnee = "fr";

    private ImageView imgMale, imgFemale, imgFr, imgEn, imgEs;
    private EditText editNomElan;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_acceuil);

        editNomElan = findViewById(R.id.editNomElan);
        imgMale = findViewById(R.id.imgMale);
        imgFemale = findViewById(R.id.imgFemale);
        imgFr = findViewById(R.id.imgFr);
        imgEn = findViewById(R.id.imgEn);
        imgEs = findViewById(R.id.imgEs);
        btnStart = findViewById(R.id.btnStart);

        imgMale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sexeSelectionne = "M";
                mettreAJourAffichageSexe();
            }
        });

        imgFemale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sexeSelectionne = "F";
                mettreAJourAffichageSexe();
            }
        });

        imgFr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                langueSelectionnee = "fr";
                mettreAJourAffichageLangue();
            }
        });

        imgEn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                langueSelectionnee = "en";
                mettreAJourAffichageLangue();
            }
        });

        imgEs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                langueSelectionnee = "es";
                mettreAJourAffichageLangue();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changerLangueApplication(langueSelectionnee);

                Intent intent = new Intent(ActiviteAcceuil.this, ActiviteJeu.class);
                intent.putExtra("NOM_ELAN", editNomElan.getText().toString());
                intent.putExtra("SEXE_ELAN", sexeSelectionne);
                startActivity(intent);
            }
        });
    }

    private void mettreAJourAffichageSexe()
    {
        imgMale.setBackgroundResource(sexeSelectionne.equals("M") ? R.drawable.cercle_jaune : 0);
        imgFemale.setBackgroundResource(sexeSelectionne.equals("F") ? R.drawable.cercle_jaune : 0);
    }

    private void mettreAJourAffichageLangue()
    {
        imgFr.setBackgroundResource(langueSelectionnee.equals("fr") ? R.drawable.cercle_jaune : 0);
        imgEn.setBackgroundResource(langueSelectionnee.equals("en") ? R.drawable.cercle_jaune : 0);
        imgEs.setBackgroundResource(langueSelectionnee.equals("es") ? R.drawable.cercle_jaune : 0);
    }

    private void changerLangueApplication(String codeLangue)
    {
        Locale locale = new Locale(codeLangue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}