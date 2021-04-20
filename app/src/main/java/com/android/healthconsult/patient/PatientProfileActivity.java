package com.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class PatientProfileActivity extends AppCompatActivity {
    TextView nametv, emailtv, celltv, gendertv;
    private ProgressDialog loading;
    ImageView profile_image;
    String getCell;
    int MAX_SIZE=999;
    Button updateBtn;
    public String userName[]=new String[MAX_SIZE];
    public String userCell[]=new String[MAX_SIZE];
    public String userEmail[]=new String[MAX_SIZE];
    public String userGender[]=new String[MAX_SIZE];
    public String userPassword[]=new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        nametv=findViewById(R.id.profile_name);
        emailtv=findViewById(R.id.profile_email);
        celltv=findViewById(R.id.profile_cell);
        gendertv=findViewById(R.id.profile_gender);
        updateBtn=findViewById(R.id.profile_update_btn);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Log
        Log.d("SP_CELL",getCell);
        //call function to get data
        getData("");
    }
    private void getData(String text) {


        //for showing progress dialog
        loading = new ProgressDialog(PatientProfileActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.USER_VIEW_URL+getCell;
        Log.d("SP_URL",URL);
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
                        Toasty.error(PatientProfileActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(PatientProfileActivity.this);
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
                Toasty.info(PatientProfileActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    final String name = jo.getString(Constant.KEY_NAME);
                    final String cell = jo.getString(Constant.KEY_CELL);
                    final String gender = jo.getString(Constant.KEY_GENDER);
                    final String email = jo.getString(Constant.KEY_EMAIL);
                    final String password = jo.getString(Constant.KEY_PASSWORD);
                    //insert data into array for put extra

                    userName[i] = name;
                    userCell[i] = cell;
                    userGender[i] = gender;
                    userEmail[i] = email;
                    userPassword[i] = password;

                    nametv.setText(name);
                    celltv.setText(cell);
                    gendertv.setText(gender);
                    emailtv.setText(email);
                    updateBtn=findViewById(R.id.profile_update_btn);
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(PatientProfileActivity.this, PatientUpdateProfileActivity.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            intent.putExtra("gender",gender);
                            startActivity(intent);
                        }
                    });
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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