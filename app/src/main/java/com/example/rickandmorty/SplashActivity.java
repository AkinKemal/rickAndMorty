package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    /*
    Burada SharedPreferences nesnesi,
    özellikle uygulama ayarlarının depolanması için tasarlanmış bir veritabanıdır.
    İlk açılışta first_time adlı bir boolean değeri true olarak kaydedilir.
    Daha sonra, bu değer kontrol edilir ve mesaj değiştirilir.
    Son olarak, first_time değeri false olarak kaydedilir,
    böylece sonraki açılışlarda "Hello" mesajı görüntülenir.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        String message = mPrefs.getBoolean("first_time", true) ? "Welcome" : "Hello";

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        mPrefs.edit().putBoolean("first_time", false).apply();

        // 2 saniye sonra MainActivity'e geçiş yap
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}