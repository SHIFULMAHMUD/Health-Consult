package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class ViewDoctorActivity extends AppCompatActivity {
    ListView CustomList;
    private ProgressDialog loading;
    int MAX_SIZE=999;
    String get_category_name;

    public String docName[]=new String[MAX_SIZE];
    public String docCell[]=new String[MAX_SIZE];
    public String docSpeciality[]=new String[MAX_SIZE];
    public String docQualification[]=new String[MAX_SIZE];
    public String docDesignation[]=new String[MAX_SIZE];
    public String docGender[]=new String[MAX_SIZE];
    public String docEmail[]=new String[MAX_SIZE];
    public String docPlace[]=new String[MAX_SIZE];
    public String docVisit[]=new String[MAX_SIZE];
    public String docToken[]=new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Doctor's List");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        CustomList=(ListView)findViewById(R.id.doctor_list);
        get_category_name = getIntent().getExtras().getString("name");
        //call function to get data
        getData("");

    }
    private void getData(String text) {

        //for showing progress dialog
        loading = new ProgressDialog(ViewDoctorActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.DOCTOR_URL+get_category_name;
        Log.d("url",URL);

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toasty.error(ViewDoctorActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewDoctorActivity.this);
        requestQueue.add(stringRequest);

    }



    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toasty.info(ViewDoctorActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String doc_name = jo.getString(Constant.KEY_DOC_NAME);
                    String doc_cell = jo.getString(Constant.KEY_DOC_CELL);
                    String doc_speciality = jo.getString(Constant.KEY_SPECIALITY);
                    String qualification = jo.getString(Constant.KEY_QUALIFICATION);
                    String designation = jo.getString(Constant.KEY_DESIGNATION);
                    String gender = jo.getString(Constant.KEY_GENDER);
                    String email = jo.getString(Constant.KEY_EMAIL);
                    String place = jo.getString(Constant.KEY_PLACE);
                    String cost = jo.getString(Constant.KEY_COST);
                    String token = jo.getString(Constant.KEY_DOC_TOKEN);

                    //insert data into array for put extra

                        docName[i] = doc_name;
                        docCell[i]=doc_cell;
                        docSpeciality[i]=doc_speciality;
                        docQualification[i] = qualification;
                        docDesignation[i] = designation;
                        docGender[i] = gender;
                        docEmail[i] = email;
                        docPlace[i] = place;
                        docVisit[i] = cost;
                        docToken[i] = token;

                    //put value into Hashmap
                    HashMap<String, String> doctor_data = new HashMap<>();

                    doctor_data.put(Constant.KEY_DOC_NAME, doc_name);
                    doctor_data.put(Constant.KEY_DOC_CELL, doc_cell);
                    doctor_data.put(Constant.KEY_SPECIALITY, doc_speciality);
                    doctor_data.put(Constant.KEY_QUALIFICATION, qualification);
                    doctor_data.put(Constant.KEY_DESIGNATION, designation);
                    doctor_data.put(Constant.KEY_GENDER, gender);
                    doctor_data.put(Constant.KEY_EMAIL, email);
                    doctor_data.put(Constant.KEY_PLACE, place);
                    doctor_data.put(Constant.KEY_COST, cost);
                    doctor_data.put(Constant.KEY_DOC_TOKEN, token);

                    list.add(doctor_data);
                    }
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewDoctorActivity.this, list, R.layout.doctor_list_items,
                new String[]{Constant.KEY_DOC_NAME,Constant.KEY_DESIGNATION,Constant.KEY_QUALIFICATION},
                new int[]{R.id.txt_doc_name,R.id.txt_doc_designation,R.id.txt_doc_qualification});
        CustomList.setAdapter(adapter);

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent=new Intent(ViewDoctorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("name",docName[position]);
                intent.putExtra("cell",docCell[position]);
                intent.putExtra("sp_type",docSpeciality[position]);
                intent.putExtra("qualification",docQualification[position]);
                intent.putExtra("designation",docDesignation[position]);
                intent.putExtra("gender",docGender[position]);
                intent.putExtra("email",docEmail[position]);
                intent.putExtra("place",docPlace[position]);
                intent.putExtra("visit",docVisit[position]);
                intent.putExtra("token",docToken[position]);
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
