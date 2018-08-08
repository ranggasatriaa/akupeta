package com.astagenta.rangga.akupeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

//  private static final String TAG = "MainActivity";
  private String tempat_id;
  private TextView mText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
//    Log.d(TAG, "onCreate: HERE");
    mText = findViewById(R.id.text);

    tempat_id = getIntent().getStringExtra("TEMPAT_ID");


    mText.setText(tempat_id);
  }
}
