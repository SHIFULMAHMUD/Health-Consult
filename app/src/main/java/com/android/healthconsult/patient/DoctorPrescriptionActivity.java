package com.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.healthconsult.Constant;
import com.android.healthconsult.R;
import com.android.healthconsult.prescription.PrescriptionDetailsActivity;
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

public class DoctorPrescriptionActivity extends AppCompatActivity {
    ListView CustomList;

    private ProgressDialog loading;
    String UserCell;

    int MAX_SIZE=999;
    public String drName[]=new String[MAX_SIZE];
    public String PatientName[]=new String[MAX_SIZE];
    public String PatientGender[]=new String[MAX_SIZE];
    public String PatientDate[]=new String[MAX_SIZE];
    public String drCell[]=new String[MAX_SIZE];
    public String PatientPrescription[]=new String[MAX_SIZE];
    public String PatientInfo[]=new String[MAX_SIZE];
    public String drSpeciality[]=new String[MAX_SIZE];
    public String drQualification[]=new String[MAX_SIZE];
    public String PatientAge[]=new String[MAX_SIZE];
    public String PatientWeight[]=new String[MAX_SIZE];
    public String PatientPulse[]=new String[MAX_SIZE];
    public String PatientTest[]=new String[MAX_SIZE];
    public String PatientDisease[]=new String[MAX_SIZE];



    Button btnSearch;
    EditText etxtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescription);
        CustomList=(ListView) findViewById(R.id.listView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Doctor Prescription");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button


        btnSearch=(Button) findViewById(R.id.btnSearch);
        etxtSearch=(EditText) findViewById(R.id.etxt_search);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        UserCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        //call function
        getData("");


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText=etxtSearch.getText().toString().trim();

                if (searchText.isEmpty())
                {
                    Toasty.error(DoctorPrescriptionActivity.this, "Please write text!", Toast.LENGTH_SHORT).show();
                }

                else {
                    getData(searchText);
                }
            }
        });

    }
    private void getData(String s) {

        String getSearchText=s;

        loading = new ProgressDialog(DoctorPrescriptionActivity.this);
        loading.setMessage("Please wait....");
        loading.show();


        if (!s.isEmpty()) {
            getSearchText = s;
        }

        String url = Constant.SHOW_PRESCRIPTION_URL+UserCell+"&text="+getSearchText ;
        Log.d("url",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toasty.error(DoctorPrescriptionActivity.this, "Network Error!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(DoctorPrescriptionActivity.this);
        requestQueue.add(stringRequest);
    }



    private void showJSON(String response) {



        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);

            if (result.length()==0)
            {
                Toasty.error(DoctorPrescriptionActivity.this, "No Prescription Found!", Toast.LENGTH_SHORT).show();
                // finish();

//                Intent intent=new Intent(PatientPrescriptionActivity.this,Patient_Home.class);
//                startActivity(intent);
            }
            else {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String patient_name= jo.getString(Constant.KEY_NAME);
                    String gender = jo.getString(Constant.KEY_GENDER);
                    String date = jo.getString(Constant.KEY_DATE);
                    String prescription= jo.getString(Constant.KEY_PRESCRIPTION);
                    String info = jo.getString(Constant.KEY_INFO);
                    String dr_name = jo.getString(Constant.KEY_DR_NAME);
                    String dr_speciality = jo.getString(Constant.KEY_SPECIALITY);
                    String dr_qualification = jo.getString(Constant.KEY_QUALIFICATION);
                    String age = jo.getString(Constant.KEY_AGE);
                    String weight= jo.getString(Constant.KEY_WEIGHT);
                    String pulse= jo.getString(Constant.KEY_PULSE);
                    String disease = jo.getString(Constant.KEY_DISEASE);
                    String test = jo.getString(Constant.KEY_TEST);

                    PatientName[i] =patient_name;
                    PatientGender[i] = gender;
                    PatientDate[i] = date;
                    PatientPrescription[i] = prescription;
                    PatientInfo[i] = info;
                    drName[i] = dr_name;
                    drSpeciality[i] = dr_speciality;
                    drQualification[i] = dr_qualification;
                    PatientAge[i]=age;
                    PatientWeight[i]=weight;
                    PatientPulse[i]=pulse;
                    PatientDisease[i]=disease;
                    PatientTest[i]=test;



                    HashMap<String, String> user_msg = new HashMap<>();
                    user_msg.put(Constant.KEY_NAME, "Patient Name: "+patient_name);
                    user_msg.put(Constant.KEY_DATE, "Date: "+date);


                    list.add(user_msg);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                DoctorPrescriptionActivity.this, list, R.layout.prescription_list_item,
                new String[]{Constant.KEY_NAME,Constant.KEY_DATE},
                new int[]{ R.id.txt_patient_name2,R.id.txt_date});
        CustomList.setAdapter(adapter);


        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent=new Intent(DoctorPrescriptionActivity.this, PrescriptionDetailsActivity.class);
                intent.putExtra("name",PatientName[position]);
                intent.putExtra("gender",PatientGender[position]);
                intent.putExtra("date",PatientDate[position]);
                intent.putExtra("prescription",PatientPrescription[position]);
                intent.putExtra("dr_name",drName[position]);
                intent.putExtra("info",PatientInfo[position]);
                intent.putExtra("dr_speciality",drSpeciality[position]);
                intent.putExtra("dr_qualification",drQualification[position]);
                intent.putExtra("age",PatientAge[position]);
                intent.putExtra("weight",PatientWeight[position]);
                intent.putExtra("pulse",PatientPulse[position]);
                intent.putExtra("disease",PatientDisease[position]);
                intent.putExtra("test",PatientTest[position]);

                startActivity(intent);


            }
        });

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