package com.astagenta.rangga.akupeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DetailActivity extends AppCompatActivity {
  private static final String TAG = "DetailActivity";

  private GoogleMap mMap;
  private RequestQueue mRequestQueue;
  private String url;
  private String tempat_id;
  private ImageView mImage;
  private TextView mKategori;
  private TextView mDescription;
  private TextView mAddress;
  private TextView mTelepone;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    Log.d(TAG, "onCreate: HERE");
    mImage = findViewById(R.id.image);
    mKategori = findViewById(R.id.kategoriTextView);
    mAddress= findViewById(R.id.alamatTextView);
    mTelepone= findViewById(R.id.teleponTextView);

    tempat_id = getIntent().getStringExtra("TEMPAT_ID");
    Log.d(TAG, "onCreate: "+tempat_id);
    url = "http://192.168.100.21/nearyou/index.php/api/detail/1";
    Log.d(TAG, "onCreate: " + url);
    jsonParse(url);

  }

  private void jsonParse(String url) {
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.d(TAG, "onResponse: Masuk JSON");
            try {
              JSONArray jsonArray = response.getJSONArray("detail");
              for (int i = 0; i < jsonArray.length(); i++) {
                Log.d(TAG, "onResponse: masuk perulangan");
                JSONObject tempat = jsonArray.getJSONObject(i);
//                String tempatId = tempat.getString("id");
//                String tempatNama = tempat.getString("tempat_nama");
//                Double latitude = tempat.getDouble("tempat_latitude");
//                Double longitude = tempat.getDouble("tempat_longitude");
                String kategoriNama = tempat.getString("kategori_name");
                String deskripsi = tempat.getString("tempat_deskripsi");
                String telepon = tempat.getString("tempat_telphone");
                String alamat = tempat.getString("tempat_alamat");

                mKategori.append(kategoriNama);
                mDescription.append(deskripsi);
                mAddress.append(alamat);
                mTelepone.append(telepon);
//                String imageUrl = tempat.getString("kategori_icon");
//              Picasso.with(MapsActivity.this).load("http://nearyou.ranggasatria.com/assets/"+imageUrl).fit().centerInside().into(mImage);
              }
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
