package com.android.healthconsult.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class PatientLoginActivity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener{
    TextView goto_signup_tv;
    Button signinBtn;
    public static final int REQUEST_CHECK_SETTINGS = 99;
    EditText mobileEt,passwordEt;
    String getCell,lat,lng;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    //ProgressDialog object declaration
    private ProgressDialog loading;
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;
    int MAX_SIZE=999;
    public String userAccountStatus[]=new String[MAX_SIZE];
    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERCELL = "usercell";
    private static final String KEY_PASS = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        if (ContextCompat.checkSelfPermission(PatientLoginActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT>=23) //Android MarshMellow Version or above
            {
                createLocationRequest();
            }
        }
//Runtime permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PatientLoginActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
        goto_signup_tv=findViewById(R.id.goto_signup_text);
        goto_signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientLoginActivity.this, PatientRegisterActivity.class);
                startActivity(intent);
            }
        });

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        rem_userpass=findViewById(R.id.ch_rememberme);
        signinBtn=findViewById(R.id.cirLoginButton);
        mobileEt=findViewById(R.id.editTextLoginPhone);
        passwordEt=findViewById(R.id.editTextLoginPassword);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        mobileEt.setText(sharedPreferences.getString(KEY_USERCELL, ""));
        passwordEt.setText(sharedPreferences.getString(KEY_PASS, ""));

        mobileEt.addTextChangedListener(this);
        passwordEt.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);
        //Click listener in LoginActivity Button
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Call AppSingleton function
                AppSingleton();

            }
        });
    }
    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(PatientLoginActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toasty.error(this, "Permission Denied!", Toasty.LENGTH_SHORT).show();
            }
        }
    }
    private void getLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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
        LocationServices.getFusedLocationProviderClient(PatientLoginActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(PatientLoginActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    lat = String.valueOf(latitude);
                    lng = String.valueOf(longitude);
                }
            }
        }, Looper.getMainLooper());
    }

    //LoginActivity function
    private void AppSingleton() {

        //Getting values from edit texts
        final String cell = mobileEt.getText().toString().trim();
        final String password = passwordEt.getText().toString().trim();


        //Checking usercell field/validation
       if (cell.isEmpty()) {
            mobileEt.setError("Please enter phone number !");
            requestFocus(mobileEt);

        }

        else if (cell.length()!=11 || !cell.startsWith("01")) {

            mobileEt.setError("Please enter valid phone number !");
            requestFocus(mobileEt);

        }
       else if (password.isEmpty()) {

           passwordEt.setError("Please enter password !");
           requestFocus(passwordEt);
       }
       else if (password.length() < 4) {

           passwordEt.setError("Password should be more than 3 characters!");
           requestFocus(passwordEt);
       }

        //showing progress dialog

        else {

            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Logging in");
            loading.setMessage("Please wait....");
            loading.show();

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("response",""+response);
                            //If we are getting success from server
                            if (response.trim().equals("success")) {
                                //Creating a shared preference

                                SharedPreferences sp = PatientLoginActivity.this.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sp.edit();
                                //Adding values to editor
                                editor.putString(Constant.CELL_SHARED_PREF, cell);
                                editor.putString(Constant.LATITUDE_SHARED_PREF, lat);
                                editor.putString(Constant.LONGITUDE_SHARED_PREF, lng);
                                //Saving values to editor
                                editor.commit();
                                //Starting Home activity

                                    Intent intent = new Intent(PatientLoginActivity.this, PatientActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toasty.success(PatientLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                loading.dismiss();
                            }

                            else if(response.trim().equals("failure")) {
                                //If the server response is not success
                                //Displaying an error message on toast
                                Toasty.error(PatientLoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }

                            else {
                                //If the server response is not success
                                //Displaying an error message on toast
                                Toasty.error(PatientLoginActivity.this, "Invalid user cell or password", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(PatientLoginActivity.this, "There is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Constant.KEY_CELL, cell);
                    params.put(Constant.KEY_PASSWORD, password);

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


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERCELL, mobileEt.getText().toString().trim());
            editor.putString(KEY_PASS, passwordEt.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USERCELL);
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toasty.info(getBaseContext(), R.string.press_once_again_to_exit,
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
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
