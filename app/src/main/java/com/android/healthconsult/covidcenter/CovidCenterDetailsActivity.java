package com.android.healthconsult.covidcenter;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.healthconsult.ConnectionDetector;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;

public class CovidCenterDetailsActivity extends AppCompatActivity {
    TextView name_tv,cell_tv,address_tv,website_tv,facility_tv;
    String id,name,cell,address,website,km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_center_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearby Hospital");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        name_tv=findViewById(R.id.center_name_tv);
        cell_tv=findViewById(R.id.center_cell_tv);
        address_tv=findViewById(R.id.address_tv);
        website_tv=findViewById(R.id.web_tv);
        facility_tv=findViewById(R.id.facility_tv);
        id = getIntent().getStringExtra("id");
        //km = getIntent().getStringExtra("km");
        // Check if Internet present
        ConnectionDetector cd = new ConnectionDetector(CovidCenterDetailsActivity.this);
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(CovidCenterDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getData("");
        }
    }
    private void getData(String text) {

        String URL = Constant.COVID_CENTER_DETAILS_URL+id;
        Log.d("SP_URL",URL);
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toasty.error(CovidCenterDetailsActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(CovidCenterDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        //Create json object for receiving json data
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toasty.info(CovidCenterDetailsActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    final String name = jo.getString(Constant.KEY_NAME);
                    final String cell = jo.getString(Constant.KEY_CELL);
                    final String address = jo.getString(Constant.KEY_ADDRESS);
                    final String website = jo.getString(Constant.KEY_WEBSITE);
                    final String facility = jo.getString(Constant.KEY_FACILITY);
                    //insert data into array for put extra

                    name_tv.setText(name);
                    cell_tv.setText(cell);
                    address_tv.setText(address);
                    facility_tv.setText(facility);
                    website_tv.setText(website);

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