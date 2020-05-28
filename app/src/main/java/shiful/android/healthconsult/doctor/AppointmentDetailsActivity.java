package shiful.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;
import shiful.android.healthconsult.patient.DoctorDetailsActivity;
import shiful.android.healthconsult.patient.PatientProfileActivity;
import shiful.android.healthconsult.patient.PatientUpdateProfileActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String getName, getCell, getEmail,getGender,getPhone,txttime;
    Button confirmBtn;
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
        getName = getIntent().getExtras().getString("name");
        getCell = getIntent().getExtras().getString("cell");
        getEmail = getIntent().getExtras().getString("email");
        getGender = getIntent().getExtras().getString("gender");
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getPhone = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

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