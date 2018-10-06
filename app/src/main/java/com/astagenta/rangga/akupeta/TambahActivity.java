package com.astagenta.rangga.akupeta;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

public class TambahActivity extends AppCompatActivity {

  private TextView mTextMessage;
  private static final String TAG = "TambahActivity";

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate: ");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tambah);

    mTextMessage = findViewById(R.id.message);

    ActionBar actionbar = getSupportActionBar();
    actionbar.setTitle("Tambah Tempat");
  }
}
