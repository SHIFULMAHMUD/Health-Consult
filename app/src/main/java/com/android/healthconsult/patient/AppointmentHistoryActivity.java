package com.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentHistoryActivity extends AppCompatActivity {
    ListView CustomList;
    private ProgressDialog loading;
    int MAX_SIZE=999;
    String getCell;

    public String doctorName[]=new String[MAX_SIZE];
    public String doctorCell[]=new String[MAX_SIZE];
    public String appointmentDate[]=new String[MAX_SIZE];
    public String appointmentTime[]=new String[MAX_SIZE];
    public String appointmentPlace[]=new String[MAX_SIZE];
    public String patientRequest[]=new String[MAX_SIZE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Appointment History");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        CustomList=(ListView)findViewById(R.id.patient_history_list);
        //call function to get data
        getData("");
    }
    private void getData(String text) {

        //for showing progress dialog
        loading = new ProgressDialog(AppointmentHistoryActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.PATIENT_HISTORY_URL+getCell;
        Log.d("url",URL);

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toasty.error(AppointmentHistoryActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(AppointmentHistoryActivity.this);
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
                Toasty.info(AppointmentHistoryActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String name = jo.getString(Constant.KEY_NAME);
                    String cell = jo.getString(Constant.KEY_CELL);
                    String date = jo.getString(Constant.KEY_DATE);
                    String time = jo.getString(Constant.KEY_TIME);
                    String place = jo.getString(Constant.KEY_PLACE);
                    String request = jo.getString(Constant.KEY_PATIENT_REQ);
                    //insert data into array for put extra
                    doctorName[i] = name;
                    doctorCell[i] = cell;
                    appointmentDate[i] = date;
                    appointmentTime[i] = time;
                    appointmentPlace[i] = place;
                    patientRequest[i] = request;

                    //put value into Hashmap
                    HashMap<String, String> history_data = new HashMap<>();
                    history_data.put(Constant.KEY_NAME, name);
                    history_data.put(Constant.KEY_CELL, cell);
                    history_data.put(Constant.KEY_DATE, date);
                    history_data.put(Constant.KEY_TIME, time);
                    history_data.put(Constant.KEY_PLACE, place);
                    history_data.put(Constant.KEY_PATIENT_REQ, request);
                    list.add(history_data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                AppointmentHistoryActivity.this, list, R.layout.patient_history_list_items,
                new String[]{Constant.KEY_NAME,Constant.KEY_CELL,Constant.KEY_DATE,Constant.KEY_TIME,Constant.KEY_PLACE,Constant.KEY_PATIENT_REQ},
                new int[]{R.id.text_doc_name,R.id.text_doc_phone,R.id.text_patient_date,R.id.text_patient_time,R.id.text_patient_place,R.id.txt_doc_req});
        CustomList.setAdapter(adapter);
    }

    //for back button
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