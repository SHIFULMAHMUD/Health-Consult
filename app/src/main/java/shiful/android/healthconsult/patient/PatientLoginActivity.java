package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;
import shiful.android.healthconsult.doctor.DoctorActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class PatientLoginActivity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener{
    TextView goto_signup_tv;
    Button signinBtn;
    EditText mobileEt,passwordEt;
    String getCell;
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
