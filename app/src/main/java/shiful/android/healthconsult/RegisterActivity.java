package shiful.android.healthconsult;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

public class RegisterActivity extends AppCompatActivity {
    TextView goto_login_tv;
    Button signupBtn;
    EditText actypeET,genderET,nameET,mobileET,emailET,passwordET;
    ProgressDialog loading;
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        goto_login_tv=findViewById(R.id.goto_login_text);
        nameET=findViewById(R.id.editTextRegisterName);
        mobileET=findViewById(R.id.editTextRegisterMobile);
        emailET=findViewById(R.id.editTextRegisterEmail);
        passwordET=findViewById(R.id.editTextRegisterPassword);
        genderET=findViewById(R.id.editTextRegisterGender);
        signupBtn=findViewById(R.id.cirRegisterButton);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up();
            }
        });
        goto_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        actypeET=findViewById(R.id.editTextRegisterAccountType);
//For choosing account type and open alert dialog
        actypeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] accountList = {"Doctor", "Patient"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Choose Account Type");
                builder.setCancelable(false);
                builder.setItems(accountList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                actypeET.setText(accountList[position]);
                                break;

                            case 1:
                                actypeET.setText(accountList[position]);
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
        genderET=findViewById(R.id.editTextRegisterGender);
        genderET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Select Gender");
                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                genderET.setText(genderList[position]);
                                break;

                            case 1:
                                genderET.setText(genderList[position]);
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
    private void sign_up() {

        //Getting values from edit texts
        final String name = nameET.getText().toString().trim();
        final String cell = mobileET.getText().toString().trim();
        final String account_type = actypeET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String gender = genderET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();




        //Checking  field/validation
        if (name.isEmpty()) {
            nameET.setError("Please enter name !");
            requestFocus(nameET);
        }
        else if (cell.isEmpty()) {

            mobileET.setError("Please enter phone number !");
            requestFocus(mobileET);

        }

        else if (cell.length()!=11) {

            mobileET.setError("Please enter valid phone number !");
            requestFocus(mobileET);

        }
        else if (account_type.isEmpty()) {

            actypeET.setError("Please select account type !");
            requestFocus(actypeET);
            Toasty.error(this, "Please select account type !", Toast.LENGTH_SHORT).show();
        }
        else if (gender.isEmpty()) {

            genderET.setError("Please select your gender !");
            requestFocus(genderET);
            Toasty.error(this, "Please select your gender !", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {

            emailET.setError("Please enter your email !");
            requestFocus(emailET);
        }
        else if (!email.contains("@") || !email.contains(".")) {

            emailET.setError("Please enter valid email !");
            requestFocus(emailET);

        }
        else if (password.isEmpty()) {

            passwordET.setError("Please enter password !");
            requestFocus(passwordET);
        }
        else if (password.length() < 4) {

            passwordET.setError("Password should be more than 3 characters!");
            requestFocus(passwordET);
        }

        else
        {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Sign up");
            loading.setMessage("Please wait....");
            loading.show();

            final StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.SIGNUP_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat

                    if (response.equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        Log.d("res", response);
                        Toasty.success(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else if (response.equals("exists")) {

                        Toasty.warning(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.equals("failure")) {

                        Toasty.error(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(RegisterActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_NAME, name);
                    params.put(Constant.KEY_CELL, cell);
                    params.put(Constant.KEY_AC_TYPE, account_type);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_EMAIL, email);
                    params.put(Constant.KEY_PASSWORD, password);


                    Log.d("url_info",Constant.SIGNUP_URL);

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
}
