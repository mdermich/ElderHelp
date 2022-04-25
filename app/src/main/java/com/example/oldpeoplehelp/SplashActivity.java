package com.example.oldpeoplehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    // Initialisation des variables
    ImageView logo;
    TextView appName;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Instanciation des variables par IDs
        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        // Etablir le plein ecran = Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Initialiser l'obejct animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(logo, PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f)
                );
        // Duration
        objectAnimator.setDuration(500);
        // repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        // commencer animator
        objectAnimator.start();

        // commencer l'animation du texte
        animatText("Gold");
        // Initialiser le Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(SplashActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        },4000);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Etablir le nom de l'app
            appName.setText(charSequence.subSequence(0,index++));
            if(index <= charSequence.length()){
                handler.postDelayed(runnable,delay);
            }

        }
    };

    // La methode d'animation du texte
    public void animatText(CharSequence cs){
        charSequence = cs;
        index =0;
        appName.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}