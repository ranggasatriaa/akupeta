package com.astagenta.rangga.akupeta;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;


public class DetailActivity extends AppCompatActivity {
  private static final String TAG = "DetailActivity";
  private Context context;

  private Toolbar toolbar;

  private GoogleMap mMap;
  private RequestQueue mRequestQueue;
  private String url;
  private String tempat_id;
  private ImageView mImage;
  private TextView mKategori;
  private TextView mDescription;
  private TextView mAddress;
  private TextView mTelepone;
  private String gambarDirektori;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    Log.d(TAG, "onCreate: DetailActivity");
    mImage = findViewById(R.id.image);
    mKategori = findViewById(R.id.kategoriTextView);
    mAddress = findViewById(R.id.alamatTextView);
    mTelepone = findViewById(R.id.teleponTextView);
    mDescription = findViewById(R.id.deskripsiTextView);
    mRequestQueue = Volley.newRequestQueue(this);

    tempat_id = getIntent().getStringExtra("TEMPAT_ID");
//    tempat_id = 1;
    url = "http://nearyou.ranggasatria.com/api/detail/" + tempat_id;
    Log.d(TAG, "onCreate: " + url);
    jsonParse(url);

    Log.d(TAG, "onCreate: " + tempat_id);

    /*TOOLBAR*/
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  private void jsonParse(String url) {
    StringRequest request = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

            Log.d(TAG, "onResponse: Masuk JSON");
//            Log.d(TAG, "onResponse: url di json" + url);
            try {
              Log.d(TAG, "onResponse: " + response);

              JSONObject detail = new JSONObject(response);
              Log.d(TAG, "onResponse: masuk perulangan");
              String tempatId = detail.getString("id");
              String tempatNama = detail.getString("tempat_nama");
              Double latitude = detail.getDouble("tempat_latitude");
              Double longitude = detail.getDouble("tempat_longitude");
              String kategoriNama = detail.getString("kategori_name");
              String deskripsi = detail.getString("tempat_deskripsi");
              String telepon = detail.getString("tempat_telphone");
              String alamat = detail.getString("tempat_alamat");
              String imageUrl = detail.getString("kategori_icon");

              /*SET GAMBAR*/
              JSONArray listGambar = detail.getJSONArray("picture");
              for (int i = 0; i < listGambar.length(); i++) {
                Log.d(TAG, "onResponse: masuk perulangan picture");
                JSONObject gambar = listGambar.getJSONObject(i);
                gambarDirektori = gambar.getString("gambar_direktori");
                Log.d(TAG, "onResponse: gambar direktori: " + gambarDirektori);
              }

              Log.d(TAG, "onResponse: id=>"+tempatId+", image=>" + imageUrl+", nama=>"+tempatNama);

              getSupportActionBar().setTitle(tempatNama);

              mKategori.setText(kategoriNama + "->" + tempatId);
              mDescription.setText(deskripsi);
              mAddress.setText(alamat);
              mTelepone.setText(telepon);
//              Glide.with(context)
//                  .load("http://nearyou.ranggasatria.com/assets/"+imageUrl)
//                  .into(mImage);
              Picasso.with(DetailActivity.this).load("http://nearyou.ranggasatria.com/assets/image/" + gambarDirektori).fit().centerInside().into(mImage);
//              }
            } catch (JSONException e) {
              StringWriter stack = new StringWriter();
              e.printStackTrace(new PrintWriter(stack));
//              e.printStackTrace();
              Log.d(TAG, "onResponse:" + stack.toString());
            }
          }
        }
        , new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
      }
    });

    mRequestQueue.add(request);
  }

}
