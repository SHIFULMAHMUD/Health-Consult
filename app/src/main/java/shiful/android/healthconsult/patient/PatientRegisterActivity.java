package shiful.android.healthconsult.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;
import shiful.android.healthconsult.doctor.DoctorLoginActivity;
import shiful.android.healthconsult.doctor.DoctorRegisterActivity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class PatientRegisterActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "health_consult";
    private static final String CHANNEL_NAME= "Health Consult";
    private static final String CHANNEL_DESC = "Android Push Notification Tutorial";
    TextView goto_login_tv;
    Button signupBtn;
    String token;
    private FirebaseAuth mAuth;
    EditText genderET,nameET,mobileET,emailET,passwordET;
    ProgressDialog loading;
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        mAuth=FirebaseAuth.getInstance();
        goto_login_tv=findViewById(R.id.goto_login_text);
        nameET=findViewById(R.id.editTextRegisterName);
        mobileET=findViewById(R.id.editTextRegisterMobile);
        emailET=findViewById(R.id.editTextRegisterEmail);
        passwordET=findViewById(R.id.editTextRegisterPassword);
        genderET=findViewById(R.id.editTextRegisterGender);
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
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
                Intent intent=new Intent(PatientRegisterActivity.this, PatientLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        genderET=findViewById(R.id.editTextRegisterGender);
        genderET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(PatientRegisterActivity.this);
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
        final String email = emailET.getText().toString().trim();
        final String gender = genderET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token =task.getResult().getToken();
                            Log.d("token",token);
                        }
                    }
                });
        //Checking  field/validation
        if (name.isEmpty()) {
            nameET.setError("Please enter name !");
            requestFocus(nameET);
        }
        else if (cell.isEmpty()) {

            mobileET.setError("Please enter phone number !");
            requestFocus(mobileET);

        }

        else if (cell.length()!=11 || !cell.startsWith("01")) {

            mobileET.setError("Please enter valid phone number !");
            requestFocus(mobileET);

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
                    Log.d("res", response);
                    if (response.trim().equals("success")) {
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(PatientRegisterActivity.this, PatientLoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Toasty.success(PatientRegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                    loading.dismiss();
                                }else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()){
                                                    Intent intent = new Intent(PatientRegisterActivity.this, PatientLoginActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    Toasty.success(PatientRegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                    finish();
                                                    loading.dismiss();
                                                }else {
                                                    loading.dismiss();
                                                    Toasty.error(PatientRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        loading.dismiss();
                                        Toasty.error(PatientRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    } else if (response.trim().equals("exists")) {

                        Toasty.warning(PatientRegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(PatientRegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(PatientRegisterActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
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
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_EMAIL, email);
                    params.put(Constant.KEY_PASSWORD, password);
                    params.put(Constant.KEY_TOKEN, token);

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
