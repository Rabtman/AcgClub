package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Rabtman
 */
public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
