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


//  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//      = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//      switch (item.getItemId()) {
//        case R.id.navigation_home:
//          mTextMessage.setText(R.string.title_home);
//          return true;
//        case R.id.navigation_dashboard:
//          mTextMessage.setText(R.string.title_dashboard);
//          return true;
//        case R.id.navigation_notifications:
//          mTextMessage.setText(R.string.title_notifications);
//          return true;
//      }
//      return false;
//    }
//  };



  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate: ");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tambah);

    mTextMessage = (TextView) findViewById(R.id.message);
//    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    ActionBar actionbar = getSupportActionBar();
    actionbar.setTitle("Tambah Tempat");
//    Toolbar mToolbar = findViewById(R.id.toolbar);
//    mToolbar.setTitle(getString(R.string.app_name));
//    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
  }

}
