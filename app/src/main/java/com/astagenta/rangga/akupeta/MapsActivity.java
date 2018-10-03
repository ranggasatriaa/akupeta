package com.astagenta.rangga.akupeta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private static final String TAG = "MapsActivity";
  private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
  private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
  private static final int DEFAULT_ZOOM = 14;
  public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

  //kode untuk permision mengakses lokasi
  private FusedLocationProviderClient mFusedLocationProviderClient;
  private Circle circle;
  private SeekBar mSeekbar;
  private GoogleMap mMap;
  private RequestQueue mRequestQueue;
  private Context context;
  private ArrayList<Tempat> listTempat = new ArrayList<>();
  int temp = 0;
  int zoom;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    mRequestQueue = Volley.newRequestQueue(this);
    mSeekbar = findViewById(R.id.seekbar);

    /*meminta permission user untuk mengakses lokasi untuk versi marshmallow keatas butuh penanganan permission yang berbeda dari versi sebelumnya*/
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkLocationPermission();
    }
    getDeviceLocation();
    mSeekbar.setMax(9000);
    mSeekbar.setProgress(0);
    mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onStopTrackingTouch(SeekBar arg0) {
      }

      @Override
      public void onStartTrackingTouch(SeekBar arg0) {
      }

      @Override
      public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
        circle.setRadius(progress + 1000);
//        if (temp <= (progress/1000)){
//          temp = (progress/1000);
//          zoom = 1;
//        }else{
//          temp = (progress/1000);
//          zoom = 0;
//        }
//        if (zoom == 1){
//          mMap.moveCamera(CameraUpdateFactory.zoomOut());
//        }else{
//          mMap.moveCamera(CameraUpdateFactory.zoomIn());
//        }

      }
    });

    /*Obtain the SupportMapFragment and get notified when the map is ready to be used.*/
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  public void checkLocationPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

      /* Asking user if explanation is needed*/
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

        /* Prompt the user once explanation has been shown*/
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        Log.d(TAG, "checkLocationPermission: jika");

      } else {
        /* No explanation needed, we can request the permission.*/
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        Log.d(TAG, "checkLocationPermission: else");
      }
    }
  }

  private void getDeviceLocation() {
    Log.d(TAG, "getDeviceLocation: Getting device current location");
    mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    Task location = mFusedLocationProviderClient.getLastLocation();
    location.addOnCompleteListener(new OnCompleteListener() {
      @Override
      public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
          Log.d(TAG, "onComplete:  found location");
          Location curretLocation = (Location) task.getResult();
          Log.d(TAG, "onComplete: Lat: " + curretLocation.getLatitude() + "Long: " + curretLocation.getLongitude());
          /*CENTER MAPS TO MY LOCATION*/
          moveCamera(new LatLng(curretLocation.getLatitude(), curretLocation.getLongitude()), DEFAULT_ZOOM);
          /*CREATE CIRCLE*/
          circle = mMap.addCircle(new CircleOptions()
              .center(new LatLng(curretLocation.getLatitude(), curretLocation.getLongitude()))
              .fillColor(0x300000ff)
              .strokeWidth(2)
              .strokeColor(Color.BLUE)
              .radius(1000));
        } else {
          Log.d(TAG, "onComplete: current location is null");
          Toast.makeText(MapsActivity.this, "unnable to get curent location", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  /*MERUBAH CENTER PETA*/
  private void moveCamera(LatLng latLng, float zoom) {
    Log.d(TAG, "MoveCamera: moving camera to lat: " + latLng.latitude + " long " + latLng.longitude);
    //Toast.makeText(this, "lat: "+latLng.latitude+" long: "+latLng.longitude+"zoom: "+zoom,Toast.LENGTH_SHORT).show();
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
  }

  private void drawCircle(LatLng point, int radius) {
    Log.d(TAG, "drawCircle: masuk circle");
    // Instantiating CircleOptions to draw a circle around the marker
    CircleOptions circleOptions = new CircleOptions();
    // Specifying the center of the circle
    circleOptions.center(point);
    // Radius of the circle
    circleOptions.radius(radius);
    // Border color of the circle
    circleOptions.strokeColor(Color.BLACK);
    // Fill color of the circle
    circleOptions.fillColor(0x30ff0000);
    // Border width of the circle
    circleOptions.strokeWidth(2);
    // Adding the circle to the GoogleMap
    mMap.addCircle(circleOptions);
  }

  /*menyimpan permission yang diizinkan oleh user*/
  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_LOCATION: {
        /*If request is cancelled, the result arrays are empty.*/
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          /* permission was granted. Do the*/
          /* contacts-related task you need to do.*/
          if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getDeviceLocation();
          }
        } else {
          /*Permission denied, Disable the functionality that depends on this permission.*/
          Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
        }
        break;
      }
    }
  }

  @Override
  /*pemanggilan tampilan pada google maps*/
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
//  mMap.setMapStyle(GoogleMap.MAP_TYPE_NONE);
    //permisalan untuk android marsmellow
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        mMap.setMyLocationEnabled(true);
      }
    } else {
      /*android dibawah masrsmelow*/
      mMap.setMyLocationEnabled(true);
    }
    jsonParse("http://nearyou.ranggasatria.com/index.php/api/read2");
  }

  /*JSON PARSE*/
  private void jsonParse(String url) {
    Log.d(TAG, "parseJSON: masuk parsing");
    StringRequest request = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {

          @Override
          public void onResponse(String response) {
            Log.d(TAG, "onResponse: Masuk onResponse");
            try {
//              JSONArray jsonArray = response.getJSONArray("tempat");
              JSONArray jsonArray = new JSONArray(response);

              for (int i = 0; i < jsonArray.length(); i++) {
                Log.d(TAG, "onResponse: masuk perulangan");
                JSONObject tempat = jsonArray.getJSONObject(i);
                int tempatId = tempat.getInt("id");
                String tempatName = tempat.getString("tempat_nama");
                Double latitude = tempat.getDouble("tempat_latitude");
                Double longitude = tempat.getDouble("tempat_longitude");
                String kategoriName = tempat.getString("kategori_name");
                String kategoriIcon = tempat.getString("kategori_icon");

                createMarker(latitude, longitude, Integer.toString(tempatId), tempatName, kategoriIcon);
//                Tempat mTempat = new Tempat(tempatId, tempatName, latitude, longitude, kategoriName, kategoriIcon);
//                listTempat.set(i,mTempat);
//                Log.d(TAG, "List tempat: "+ mTempat.toString());
//                Log.d(TAG, "List array: "+ listTempat.toString());
              }
            } catch (JSONException e) {
              StringWriter stack = new StringWriter();
              e.printStackTrace(new PrintWriter(stack));
//              e.printStackTrace();
              Log.d(TAG, "onResponse: "+e);
              Log.d(TAG, "onResponse:" + stack.toString());
            }
          }
        }
        , new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
//        error.printStackTrace();
        Log.d(TAG, "onErrorResponse: " + error.getMessage());
      }
    });

    mRequestQueue.add(request);
  }

  protected Marker createMarker(double latitude, double longitude, final String id, String title, String snippet) {
    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
      @Override
      public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
        intent.putExtra("TEMPAT_ID", id);
        startActivity(intent);
      }
    });
    /*merubah text jadi Integer*/
//      Integer i = Integer.parseInt("map_marker_blue");
//    Integer icons =  R.drawable.i;
    Integer icons = R.drawable.map_marker_blue;

    return mMap.addMarker(new MarkerOptions()
            .position(new LatLng(latitude, longitude))
            .anchor(0.5f, 0.5f)
            .title(title)
            .snippet(snippet + " -> "+ id)
            .icon(BitmapDescriptorFactory.fromResource(icons))
    );
  }
}
