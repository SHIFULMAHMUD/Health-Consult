package shiful.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;
import shiful.android.healthconsult.patient.PatientProfileActivity;
import shiful.android.healthconsult.patient.PatientUpdateProfileActivity;

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

public class DoctorProfileUpdateActivity extends AppCompatActivity {
    String getCell,getName,getEmail,getPassword,getGender,getSpecialist,getQualification,getDesignaiton,getLocation,getVisit;
    EditText update_NameEt,update_emailET,update_passwordEt,update_genderEt,update_QualificationET,update_DesignationEt,update_LocationEt,update_VisitEt,sptypeET;
    private ProgressDialog loading;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getName = getIntent().getExtras().getString("name");
        getSpecialist = getIntent().getExtras().getString("specialist");
        getQualification = getIntent().getExtras().getString("qualification");
        getDesignaiton = getIntent().getExtras().getString("designation");
        getEmail = getIntent().getExtras().getString("email");
        getPassword = getIntent().getExtras().getString("password");
        getGender = getIntent().getExtras().getString("gender");
        getLocation = getIntent().getExtras().getString("location");
        getVisit = getIntent().getExtras().getString("visit");

        update_NameEt=findViewById(R.id.heading_update_name);
        update_emailET=findViewById(R.id.heading_update_mail);
        update_passwordEt=findViewById(R.id.heading_update_pass);
        update_genderEt=findViewById(R.id.heading_update_gender);
        sptypeET=findViewById(R.id.heading_update_category);
        update_QualificationET=findViewById(R.id.heading_update_qualification);
        update_DesignationEt=findViewById(R.id.heading_update_designation);
        update_LocationEt=findViewById(R.id.heading_update_location);
        update_VisitEt=findViewById(R.id.heading_visit);
        updateBtn=findViewById(R.id.doc_profile_update_button);

        update_NameEt.setText(getName);
        sptypeET.setText(getSpecialist);
        update_QualificationET.setText(getQualification);
        update_DesignationEt.setText(getDesignaiton);
        update_emailET.setText(getEmail);
        update_passwordEt.setText(getPassword);
        update_genderEt.setText(getGender);
        update_LocationEt.setText(getLocation);
        update_VisitEt.setText(getVisit);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
//For choosing account type and open alert dialog
        sptypeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] specialityList = {"Allergist", "Anaesthesiologist", "Andrologist", "Cardiologist", "Cardiac Electrophysiologist", "Dermatologist", "Endocrinologist", "Epidemiologist", "Family Medicine Physician", "Gastroenterologist", "Geriatrician", "Hyperbaric Physician", "Hematologist", "Hepatologist", "Immunologist", "Infectious Disease Specialist", "Intensivist", "Internal Medicine Specialist", "Medical Geneticist", "Neonatologist", "Nephrologist", "Neurologist", "Neurosurgeon", "Nuclear Medicine Specialist", "Obstetrician/Gynecologist (OB/GYN)", "Occupational Medicine Specialist", "Oncologist", "Ophthalmologist", "Oral Surgeon", "Orthopedic Surgeon / Orthopedist", "Otolaryngologist (also ENT Specialist)", "Parasitologist", "Pathologist", "Perinatologist", "Periodontist", "Pediatrician", "Physiatrist", "Plastic Surgeon", "Psychiatrist", "Pulmonologist", "Radiologist", "Rheumatologist", "Sleep Doctor / Sleep Disorders Specialist", "Spinal Cord Injury Specialist", "Sports Medicine Specialist", "Surgeon", "Thoracic Surgeon", "Urologist", "Vascular Surgeon", "Veterinarian"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorProfileUpdateActivity.this);
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
        //For choosing division and open alert dialog
        update_genderEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorProfileUpdateActivity.this);
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
        final String specialist = sptypeET.getText().toString().trim();
        final String qualification = update_QualificationET.getText().toString().trim();
        final String designation = update_DesignationEt.getText().toString().trim();
        final String email = update_emailET.getText().toString().trim();
        final String password = update_passwordEt.getText().toString().trim();
        final String gender = update_genderEt.getText().toString().trim();
        final String location = update_LocationEt.getText().toString().trim();
        final String visit = update_VisitEt.getText().toString().trim();

        //Checking  field/validation
        if (name.isEmpty()) {
            update_NameEt.setError("Please enter name !");
            requestFocus(update_NameEt);
        }
        else if (specialist.isEmpty()) {
            sptypeET.setError("Please select speciality type !");
            requestFocus(sptypeET);
            Toasty.error(DoctorProfileUpdateActivity.this, "Please select speciality type !", Toast.LENGTH_SHORT).show();
        }
        else if (qualification.isEmpty()) {
            update_QualificationET.setError("Please enter qualification !");
            requestFocus(update_QualificationET);
        }
        else if (designation.isEmpty()) {
            update_DesignationEt.setError("Please enter designation !");
            requestFocus(update_DesignationEt);
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
            Toasty.error(DoctorProfileUpdateActivity.this, "Please select gender !", Toast.LENGTH_SHORT).show();
        }
        else if (location.isEmpty()) {
            update_LocationEt.setError("Please enter location !");
            requestFocus(update_LocationEt);
        }
        else if (visit.isEmpty()) {
            update_VisitEt.setError("Please enter visit cost amount !");
            requestFocus(update_VisitEt);
        }
        else
        {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Updating Profile");
            loading.setMessage("Please wait....");
            loading.show();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.UPDATE_DOC_PROFILE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);

                    if (response.trim().equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(DoctorProfileUpdateActivity.this, DoctorProfileActivity.class);
                        Toasty.success(DoctorProfileUpdateActivity.this, "Information Updated", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(DoctorProfileUpdateActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(DoctorProfileUpdateActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_UPDATE_NAME, name);
                    params.put(Constant.KEY_SPECIALITY, specialist);
                    params.put(Constant.KEY_QUALIFICATION, qualification);
                    params.put(Constant.KEY_DESIGNATION, designation);
                    params.put(Constant.KEY_UPDATE_CELL, getCell);
                    params.put(Constant.KEY_UPDATE_EMAIL, email);
                    params.put(Constant.KEY_UPDATE_PASSWORD, password);
                    params.put(Constant.KEY_UPDATE_GENDER, gender);
                    params.put(Constant.KEY_AMB_PLACE, location);
                    params.put(Constant.KEY_DOC_VISIT, visit);

                    Log.d("url_info",Constant.UPDATE_DOC_PROFILE_URL);

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
