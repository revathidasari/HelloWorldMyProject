package com.example.helloworldgrpc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // commented lines are for custom splash screen for Android 12
        //SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // Keep the splash screen visible for this Activity
       // splashScreen.setKeepOnScreenCondition(() -> true );


        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}
