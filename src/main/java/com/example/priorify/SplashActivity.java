package com.example.priorify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Asegúrate de tener este layout

        // Simula una carga de 2 segundos y luego redirige a la MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finaliza SplashActivity para que no se pueda volver atrás
            }
        }, 2000); // 2 segundos de espera
    }
}
