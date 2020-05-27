package shiful.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;
import shiful.android.healthconsult.patient.CategoryActivity;
import shiful.android.healthconsult.patient.PatientLoginActivity;
import shiful.android.healthconsult.patient.PatientRegisterActivity;
import shiful.android.healthconsult.patient.ViewDoctorActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorRegisterActivity extends AppCompatActivity {
    TextView goto_login_tv;
    Button signupBtn;
    EditText sptypeET,genderET,nameET,mobileET,emailET,passwordET,qualificationEt,designationET,placeET,visitET;
    ProgressDialog loading;
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        goto_login_tv=findViewById(R.id.goto_doc_login_text);
        nameET=findViewById(R.id.editTextRegisterDocName);
        sptypeET=findViewById(R.id.editTextRegisterSpType);
        qualificationEt=findViewById(R.id.editTextRegisterQualification);
        designationET=findViewById(R.id.editTextRegisterDesignation);
        placeET=findViewById(R.id.editTextRegisterDocPlace);
        visitET=findViewById(R.id.editTextRegisterVisitCost);
        mobileET=findViewById(R.id.editTextRegisterDocMobile);
        emailET=findViewById(R.id.editTextRegisterDocEmail);
        passwordET=findViewById(R.id.editTextRegisterDocPassword);
        genderET=findViewById(R.id.editTextRegisterDocGender);
        signupBtn=findViewById(R.id.cirDocRegisterButton);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up();
            }
        });
        goto_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorRegisterActivity.this, DoctorLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sptypeET=findViewById(R.id.editTextRegisterSpType);
//For choosing account type and open alert dialog
        sptypeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] specialityList = {"Allergist", "Anaesthesiologist", "Andrologist", "Cardiologist", "Cardiac Electrophysiologist", "Dermatologist", "Endocrinologist", "Epidemiologist", "Family Medicine Physician", "Gastroenterologist", "Geriatrician", "Hyperbaric Physician", "Hematologist", "Hepatologist", "Immunologist", "Infectious Disease Specialist", "Intensivist", "Internal Medicine Specialist", "Medical Geneticist", "Neonatologist", "Nephrologist", "Neurologist", "Neurosurgeon", "Nuclear Medicine Specialist", "Obstetrician/Gynecologist (OB/GYN)", "Occupational Medicine Specialist", "Oncologist", "Ophthalmologist", "Oral Surgeon", "Orthopedic Surgeon / Orthopedist", "Otolaryngologist (also ENT Specialist)", "Parasitologist", "Pathologist", "Perinatologist", "Periodontist", "Pediatrician", "Physiatrist", "Plastic Surgeon", "Psychiatrist", "Pulmonologist", "Radiologist", "Rheumatologist", "Sleep Doctor / Sleep Disorders Specialist", "Spinal Cord Injury Specialist", "Sports Medicine Specialist", "Surgeon", "Thoracic Surgeon", "Urologist", "Vascular Surgeon", "Veterinarian"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorRegisterActivity.this);
                builder.setTitle("Choose Speciality Type");
                builder.setCancelable(false);
                builder.setItems(specialityList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 1:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 2:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 3:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 4:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 5:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 6:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 7:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 8:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 9:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 10:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 11:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 12:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 13:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 14:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 15:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 16:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 17:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 18:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 19:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 20:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 21:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 22:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 23:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 24:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 25:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 26:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 27:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 28:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 29:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 30:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 31:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 32:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 33:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 34:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 35:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 36:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 37:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 38:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 39:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 40:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 41:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 42:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 43:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 44:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 45:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 46:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 47:
                                sptypeET.setText(specialityList[position]);
                                break;
                            case 48:
                                sptypeET.setText(specialityList[position]);
                                break;

                            case 49:
                                sptypeET.setText(specialityList[position]);
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
        genderET=findViewById(R.id.editTextRegisterDocGender);
        genderET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorRegisterActivity.this);
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
        final String speciality_type = sptypeET.getText().toString().trim();
        final String qualification = qualificationEt.getText().toString().trim();
        final String designation = designationET.getText().toString().trim();
        final String gender = genderET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();
        final String location = placeET.getText().toString().trim();
        final String visit = visitET.getText().toString().trim();

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
        else if (speciality_type.isEmpty()) {

            sptypeET.setError("Please select speciality type !");
            requestFocus(sptypeET);
            Toasty.error(this, "Please select speciality type !", Toast.LENGTH_SHORT).show();
        }
        else if (qualification.isEmpty()) {
            qualificationEt.setError("Please enter qualification !");
            requestFocus(qualificationEt);
        }
        else if (designation.isEmpty()) {
            designationET.setError("Please enter designation !");
            requestFocus(designationET);
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
        else if (location.isEmpty()) {
            placeET.setError("Please enter location !");
            requestFocus(placeET);
        }
        else if (visit.isEmpty()) {
            visitET.setError("Please enter visit cost amount !");
            requestFocus(visitET);
        }

        else
        {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Sign up");
            loading.setMessage("Please wait....");
            loading.show();

            final StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.DOC_SIGNUP_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("res", response);
                    if (response.trim().equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(DoctorRegisterActivity.this, DoctorLoginActivity.class);
                        Toasty.success(DoctorRegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    } else if (response.trim().equals("exists")) {

                        Toasty.warning(DoctorRegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(DoctorRegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(DoctorRegisterActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_DOC_NAME, name);
                    params.put(Constant.KEY_DOC_CELL, cell);
                    params.put(Constant.KEY_SPECIALITY, speciality_type);
                    params.put(Constant.KEY_QUALIFICATION, qualification);
                    params.put(Constant.KEY_DESIGNATION, designation);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_EMAIL, email);
                    params.put(Constant.KEY_PASSWORD, password);
                    params.put(Constant.KEY_PLACE, location);
                    params.put(Constant.KEY_COST, visit);


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
