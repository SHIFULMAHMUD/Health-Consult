package com.android.healthconsult.assistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentDetailsActivity extends AppCompatActivity {
    TextView txtName, txtCell, txtEmail,txtGender;
    String getName, getCell, getEmail,getGender,getPhone,txttime,getToken;
    Button confirmBtn,rejectBtn;
    EditText txtDate, txtTime,txtPlace;
    private ProgressDialog loading;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Appointment Details");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        txtName=findViewById(R.id.patient_name_tv);
        txtEmail=findViewById(R.id.patient_email_tv);
        txtGender=findViewById(R.id.patient_gender_tv);
        txtCell=findViewById(R.id.patient_cell_tv);
        txtPlace=findViewById(R.id.appointment_location_et);
        confirmBtn=findViewById(R.id.confirm_appointment_btn);
        rejectBtn=findViewById(R.id.reject_appointment_btn);
        getName = getIntent().getExtras().getString("name");
        getCell = getIntent().getExtras().getString("cell");
        getEmail = getIntent().getExtras().getString("email");
        getGender = getIntent().getExtras().getString("gender");
        getToken = getIntent().getExtras().getString("token");
        getPhone = getIntent().getExtras().getString("doccell");

        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentDetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if(hourOfDay>=0 && hourOfDay<12){
                                    txttime = hourOfDay + " : " + minute + " AM";
                                } else {
                                    if(hourOfDay == 12){
                                        txttime = hourOfDay + " : " + minute + " PM";
                                    } else{
                                        hourOfDay = hourOfDay -12;
                                        txttime = hourOfDay + " : " + minute + " PM";
                                    }
                                }

                                txtTime.setText(txttime);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }

        });


        txtName.setText(getName);
        txtCell.setText(getCell);
        txtEmail.setText(getEmail);
        txtGender.setText(getGender);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentDetailsActivity.this);
                builder.setTitle("Confirm Appointment?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                ConfirmRequest();
                                String title = "Appointment Confirmed";
                                String message = "Your appointment has been confirmed. Know details from Health Consult app.";
                                String token=getToken;
                                Log.d("getto",token);
                                SaveContact(title,message,token);
                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentDetailsActivity.this);
                builder.setTitle("Reject Appointment?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                RejectRequest();
                                String title = "Appointment Rejected";
                                String message = "Your appointment has been rejected by Doctor. Know details from Health Consult app.";
                                String token=getToken;
                                SaveContact(title,message,token);
                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
            }
        });
    }

    public void  ConfirmRequest()
    {
        //Getting values from edit texts
        final String patient_cell = getCell;
        final String doc_cell = getPhone;
        final String date = txtDate.getText().toString().trim();
        final String time = txtTime.getText().toString().trim();
        final String place = txtPlace.getText().toString().trim();
        final String request = "Confirmed";

    if (date.isEmpty()) {

        txtDate.setError("Please select date !");
        requestFocus(txtDate);
        Toasty.error(this, "Please select date !", Toast.LENGTH_SHORT).show();
    }
    else if (time.isEmpty()) {

        txtTime.setError("Please select time !");
        requestFocus(txtTime);
        Toasty.error(this, "Please select time !", Toast.LENGTH_SHORT).show();
    }
    else if (place.isEmpty()) {

        txtPlace.setError("Please enter location !");
        requestFocus(txtPlace);
    }
    else{
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Confirming Request");
            loading.setMessage("Please wait....");
            loading.show();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.CONFIRM_APPOINTMENT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);

                    if (response.trim().equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(AppointmentDetailsActivity.this, PatientHistoryActivity.class);
                        Toasty.success(AppointmentDetailsActivity.this, "Appointment Confirmed", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(AppointmentDetailsActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(AppointmentDetailsActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_UPDATE_CELL, patient_cell);
                    params.put(Constant.KEY_PHONE, doc_cell);
                    params.put(Constant.KEY_DATE, date);
                    params.put(Constant.KEY_TIME, time);
                    params.put(Constant.KEY_PLACE, place);
                    params.put(Constant.KEY_PATIENT_REQ, request);


                    Log.d("url_info",Constant.CONFIRM_APPOINTMENT_URL);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void  RejectRequest()
    {
        //Getting values from edit texts
        final String patient_cell = getCell;
        final String doc_cell = getPhone;
        final String date = "Rejected";
        final String time = "Rejected";
        final String place = "Rejected";
        final String request = "Rejected";

            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Rejecting Request");
            loading.setMessage("Please wait....");
            loading.show();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.REJECT_APPOINTMENT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);

                    if (response.trim().equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(AppointmentDetailsActivity.this, PatientHistoryActivity.class);
                        Toasty.success(AppointmentDetailsActivity.this, "Appointment Rejected", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(AppointmentDetailsActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(AppointmentDetailsActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_UPDATE_CELL, patient_cell);
                    params.put(Constant.KEY_PHONE, doc_cell);
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

    public void  SaveContact(String get_title, String msg, final String token)
    {
        final String title=get_title;
        final String message=msg;


        loading = new ProgressDialog(this);

        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.NOTIFICATION_API_URL;

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //for track response in logcat
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toasty.error(AppointmentDetailsActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("title", title);
                params.put("message",message);
                params.put("token",token);

                Log.d("msg",title+ " "+message+" "+token);
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(AppointmentDetailsActivity.this);
        requestQueue.add(stringRequest);
    }
    //for request focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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