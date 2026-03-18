package com.example.moosemanthegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ActiviteJeu extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;

    private ImageView mooseMan;
    private RelativeLayout layoutPrincipal;
    private TextView txtVies, txtNourriture;

    private int vies = 3;
    private int nourriture = 3;

    private float posX = 0;
    private float posY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_jeu);

        mooseMan = findViewById(R.id.mooseMan);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);
        txtVies = findViewById(R.id.txtVies);
        txtNourriture = findViewById(R.id.txtNourriture);

        ImageButton btnCuisine = findViewById(R.id.btnCuisine);
        ImageButton btnCalcul = findViewById(R.id.btnCalcul);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null)
        {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        btnCuisine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ActiviteJeu.this, ActiviteCuisine.class));
            }
        });

        btnCalcul.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // startActivity(new Intent(JeuActivity.this, ActiviteCalcul.class));
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (accelerometer != null)
        {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
        if (lightSensor != null)
        {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        calculerTempsEcoule();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
        sauvegarderTemps();
    }

    private void calculerTempsEcoule()
    {
        SharedPreferences prefs = getSharedPreferences("MooseData", MODE_PRIVATE);
        long dernierTemps = prefs.getLong("DernierTemps", System.currentTimeMillis());
        vies = prefs.getInt("Vies", 3);
        nourriture = prefs.getInt("Nourriture", 3);

        long tempsActuel = System.currentTimeMillis();
        long differenceMillis = tempsActuel - dernierTemps;

        // 6 heures = 21 600 000 millisecondes
        long heuresEcoulees = differenceMillis / (1000 * 60 * 60);
        int tranchesDe6Heures = (int) (heuresEcoulees / 6);

        if (tranchesDe6Heures > 0)
        {
            for (int i = 0; i < tranchesDe6Heures; i++)
            {
                if (nourriture > 0)
                {
                    nourriture--;
                }
                else if (vies > 0)
                {
                    vies--;
                }
            }
            sauvegarderTemps(); // On reset le chrono
        }

        txtVies.setText("Vies : " + vies);
        txtNourriture.setText("Nourriture : " + nourriture);
    }

    private void sauvegarderTemps()
    {
        SharedPreferences prefs = getSharedPreferences("MooseData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("DernierTemps", System.currentTimeMillis());
        editor.putInt("Vies", vies);
        editor.putInt("Nourriture", nourriture);
        editor.apply();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            // Physique basique : on inverse les valeurs pour que ça glisse du côté penché
            posX -= event.values[0] * 5;
            posY += event.values[1] * 5;

            // Appliquer la translation
            mooseMan.setTranslationX(posX);
            mooseMan.setTranslationY(posY);
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            int heure = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            boolean estJour = (heure >= 8 && heure < 20);

            if (estJour)
            {
                if (event.values[0] > 100)
                {
                    layoutPrincipal.setBackgroundResource(R.drawable.FondSoleil);
                }
                else
                {
                    // S'il fait jour mais sombre (nuageux ou pluie)
                    layoutPrincipal.setBackgroundResource(R.drawable.FondNuage);
                }
            }
            else
            {
                layoutPrincipal.setBackgroundResource(R.drawable.FondNuit);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Non utilisé ici
    }
}