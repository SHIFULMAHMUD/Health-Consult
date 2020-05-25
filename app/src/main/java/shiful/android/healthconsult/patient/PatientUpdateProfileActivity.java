package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.EditText;
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

public class PatientUpdateProfileActivity extends AppCompatActivity {
    String getCell,getName,getEmail,getPassword,getGender;
    EditText update_NameEt,update_emailET,update_passwordEt,update_genderEt;
    private ProgressDialog loading;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getName = getIntent().getExtras().getString("name");
        getEmail = getIntent().getExtras().getString("email");
        getPassword = getIntent().getExtras().getString("password");
        getGender = getIntent().getExtras().getString("gender");

        update_NameEt=findViewById(R.id.heading_account);
        update_emailET=findViewById(R.id.heading_mail);
        update_passwordEt=findViewById(R.id.heading_pass);
        update_genderEt=findViewById(R.id.heading_gender);
        updateBtn=findViewById(R.id.profile_update_button);

        update_NameEt.setText(getName);
        update_emailET.setText(getEmail);
        update_passwordEt.setText(getPassword);
        update_genderEt.setText(getGender);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        //For choosing division and open alert dialog
        update_genderEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(PatientUpdateProfileActivity.this);
                builder.setTitle("Select Gender");
                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                update_genderEt.setText(genderList[position]);
                                break;

                            case 1:
                                update_genderEt.setText(genderList[position]);
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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });
    }
    public void  UpdateProfile()
    {
        //Getting values from edit texts
        final String name = update_NameEt.getText().toString().trim();
        final String email = update_emailET.getText().toString().trim();
        final String password = update_passwordEt.getText().toString().trim();
        final String gender = update_genderEt.getText().toString().trim();


        //Checking  field/validation
        if (name.isEmpty()) {
            update_NameEt.setError("Please enter name !");
            requestFocus(update_NameEt);
        }
        else if (email.isEmpty()) {
            update_emailET.setError("Please enter email !");
            requestFocus(update_emailET);
        }
        else if (!email.contains("@") || !email.contains(".")) {
            update_emailET.setError("Please enter valid email !");
            requestFocus(update_emailET);
        }
        else if (password.isEmpty()) {
            update_passwordEt.setError("Please enter password !");
            requestFocus(update_passwordEt);
        }
        else if (gender.isEmpty()) {
            update_genderEt.setError("Please select gender !");
            requestFocus(update_genderEt);
            Toasty.error(PatientUpdateProfileActivity.this, "Please select gender !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Updating Profile");
            loading.setMessage("Please wait....");
            loading.show();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.UPDATE_PROFILE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);

                    if (response.trim().equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(PatientUpdateProfileActivity.this, PatientProfileActivity.class);
                        Toasty.success(PatientUpdateProfileActivity.this, "Information Updated", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(PatientUpdateProfileActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(PatientUpdateProfileActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_UPDATE_NAME, name);
                    params.put(Constant.KEY_UPDATE_CELL, getCell);
                    params.put(Constant.KEY_UPDATE_EMAIL, email);
                    params.put(Constant.KEY_UPDATE_PASSWORD, password);
                    params.put(Constant.KEY_UPDATE_GENDER, gender);

                    Log.d("url_info",Constant.UPDATE_PROFILE_URL);

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
