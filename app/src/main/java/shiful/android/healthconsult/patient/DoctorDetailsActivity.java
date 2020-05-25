package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;
import shiful.android.healthconsult.doctor.AppointmentActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView txtName, txtQualification, txtDesignation,txtPlace, txtCost,txtSpeciality,txtGender,txtEmail,txtCell;
    String getPhone,getName, getQualification, getDesignation,getPlace,getCost,getSpeciality,getGender,getEmail;
    Button appointmentBtn;
    private ProgressDialog loading;
    String getCell,userName, userCell, userGender, userEmail, userPassword,date,time,place,request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        txtName=findViewById(R.id.doc_name_tv);
        txtQualification=findViewById(R.id.qualification_tv);
        txtDesignation=findViewById(R.id.designation_tv);
        txtPlace=findViewById(R.id.location_tv);
        txtCost=findViewById(R.id.visit_tv);
        txtSpeciality=findViewById(R.id.speciality_tv);
        txtGender=findViewById(R.id.gender_tv);
        txtEmail=findViewById(R.id.email_tv);
        txtCell=findViewById(R.id.cell_tv);
        appointmentBtn=findViewById(R.id.get_appointment_btn);

        getName = getIntent().getExtras().getString("name");
        getPhone = getIntent().getExtras().getString("cell");
        getSpeciality = getIntent().getExtras().getString("sp_type");
        getQualification = getIntent().getExtras().getString("qualification");
        getDesignation = getIntent().getExtras().getString("designation");
        getGender = getIntent().getExtras().getString("gender");
        getEmail = getIntent().getExtras().getString("email");
        getPlace = getIntent().getExtras().getString("place");
        getCost = getIntent().getExtras().getString("visit");

        txtName.setText(getName);
        txtSpeciality.setText(getSpeciality);
        txtCell.setText(getPhone);
        txtQualification.setText(getQualification);
        txtDesignation.setText(getDesignation);
        txtGender.setText(getGender);
        txtEmail.setText(getEmail);
        txtPlace.setText(getPlace);
        txtCost.setText(getCost);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Log
        Log.d("SP_CELL",getCell);
        //call function to get data
        getData("");

        appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }
    private void getData(String text) {

        //for showing progress dialog
        loading = new ProgressDialog(DoctorDetailsActivity.this);
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
                        Toasty.error(DoctorDetailsActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(DoctorDetailsActivity.this);
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
                Toasty.info(DoctorDetailsActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
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

                    userName = name;
                    userCell = cell;
                    userGender = gender;
                    userEmail = email;
                    userPassword = password;
                    date="Pending";
                    time="Pending";
                    place="Pending";
                    request="Pending";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void submit() {

            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Submitting Request");
            loading.setMessage("Please wait....");
            loading.show();


            String URL = Constant.APPOINTMENT_URL;
            Log.d("find",URL);

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            //for track response in logcat
                            Log.d("RESP", response);
                            // Log.d("RESPONSE", userCell);


                            //If we are getting success from server
                            if (response.trim().equals("success")) {

                                loading.dismiss();
                                //Starting profile activity
                                Toasty.success(DoctorDetailsActivity.this, " Request Submitted!", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(DoctorDetailsActivity.this,AppointmentHistoryActivity.class);
                                startActivity(intent);
                                finish();
                            }


                            //If we are getting success from server
                            else if (response.trim().equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                //Intent intent = new Intent(AddContactsActivity.this, HomeActivity.class);
                                Toasty.error(DoctorDetailsActivity.this, " Submission Failed!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toasty.error(DoctorDetailsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(DoctorDetailsActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Constant.KEY_DOC_NAME, getName);
                    params.put(Constant.KEY_DOC_CELL, getPhone);
                    params.put(Constant.KEY_PATIENT_NAME, userName);
                    params.put(Constant.KEY_PATIENT_CELL, userCell);
                    params.put(Constant.KEY_PATIENT_EMAIL, userEmail);
                    params.put(Constant.KEY_PATIENT_GENDER, userGender);
                    params.put(Constant.KEY_DATE, date);
                    params.put(Constant.KEY_TIME, time);
                    params.put(Constant.KEY_PLACE, place);
                    params.put(Constant.KEY_PATIENT_REQ, request);
                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
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