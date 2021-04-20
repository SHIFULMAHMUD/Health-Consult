package com.android.healthconsult.covidcenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.healthconsult.ConnectionDetector;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import es.dmoral.toasty.Toasty;

public class CovidTestCenterActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    int MAX_SIZE = 999;
    String getCell, userLatitude, userLongitude;
    SharedPreferences sharedPreferences;
    public double userLat;
    public double userLong;
    public String centerId[] = new String[MAX_SIZE];
    public String kilo[] = new String[MAX_SIZE];
    public String centerName[] = new String[MAX_SIZE];
    public String centerCell[] = new String[MAX_SIZE];
    public String centerAddress[] = new String[MAX_SIZE];
    public double centerLatitude[] = new double[MAX_SIZE];
    public double centerLongitude[] = new double[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_test_center);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearby Covid Test Center");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
//Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
//Fetching latitude, longitude from shared preferences
        userLatitude = sharedPreferences.getString(Constant.LATITUDE_SHARED_PREF,"22.3237516");
        userLongitude = sharedPreferences.getString(Constant.LONGITUDE_SHARED_PREF, "91.8091193");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(CovidTestCenterActivity.this);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(CovidTestCenterActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        } else {
            getData();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        getData();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                String id = marker.getSnippet();
                //String km = marker.getSnippet();
                Intent intent = new Intent(CovidTestCenterActivity.this, CovidCenterDetailsActivity.class);
                intent.putExtra("id", id);
                //intent.putExtra("km", km);
                startActivity(intent);
                return true;
            }
        });

    }
    private void getData() {

        String URL = Constant.COVID_CENTER_VIEW_URL;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toasty.error(CovidTestCenterActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(CovidTestCenterActivity.this);
        requestQueue.add(stringRequest);

    }
    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toasty.info(CovidTestCenterActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    //insert data into array for put extra

                    final String center_id = jo.getString(Constant.KEY_ID);
                    final String center_name = jo.getString(Constant.KEY_NAME);
                    final String center_cell = jo.getString(Constant.KEY_CELL);
                    final String center_latitude = jo.getString(Constant.KEY_LATITUDE);
                    final String center_longitude = jo.getString(Constant.KEY_LONGITUDE);

                    if (!center_latitude.trim().equals("null")) {
                        centerLatitude[i] = Double.parseDouble(center_latitude);
                    }
                    if (!center_longitude.trim().equals("null")) {
                        centerLongitude[i] = Double.parseDouble(center_longitude);
                    }
                    //insert data into array for put extra
                    centerId[i] = center_id;
                    centerName[i] = center_name;
                    centerCell[i] = center_cell;
                    userLat = Double.parseDouble(userLatitude);
                    userLong = Double.parseDouble(userLongitude);
                    double startLatitude = userLat;
                    double startLongitude = userLong;
                    double endLatitude = centerLatitude[i];
                    double endLongitude = centerLongitude[i];

                    //calculate distance //
                    float[] results = new float[1];
                    Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
                    float distance = results[0];
                    int kilometer = (int) (distance / 1000);
                    //kilo[i]=""+kilometer;
                    if (kilometer <= 20) {
                        LatLng sydney = new LatLng(endLatitude, endLongitude);
                        mMap.addMarker(new MarkerOptions().position(sydney).title(centerName[i]).snippet(centerId[i]));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(endLatitude, endLongitude), 15.0f));
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}