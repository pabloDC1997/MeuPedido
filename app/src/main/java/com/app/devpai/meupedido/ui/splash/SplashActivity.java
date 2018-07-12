package com.app.devpai.meupedido.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.ui.companie.CompaniesActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startActivity(new Intent(this, CompaniesActivity.class));
        finish();
    }

}
