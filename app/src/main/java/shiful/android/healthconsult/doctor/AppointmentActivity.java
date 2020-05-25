package shiful.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;

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

public class AppointmentActivity extends AppCompatActivity {
    ListView CustomList;
    private ProgressDialog loading;
    Button btnSearch;
    EditText txtSearch;
    String getCell;
    int MAX_SIZE=999;

    public String patientName[]=new String[MAX_SIZE];
    public String patientCell[]=new String[MAX_SIZE];
    public String patientGender[]=new String[MAX_SIZE];
    public String patientEmail[]=new String[MAX_SIZE];
    public String patientRequest[]=new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        CustomList=(ListView)findViewById(R.id.patient_list);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        //call function to get data
        getData("");

    }


    private void getData(String text) {

        //for showing progress dialog
        loading = new ProgressDialog(AppointmentActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.PATIENT_VIEW_URL+getCell;
        Log.d("patient_url",URL);

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
                        Toasty.error(AppointmentActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(AppointmentActivity.this);
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
                Toasty.info(AppointmentActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String name = jo.getString(Constant.KEY_NAME);
                    String gender = jo.getString(Constant.KEY_GENDER);
                    String cell = jo.getString(Constant.KEY_CELL);
                    String email = jo.getString(Constant.KEY_EMAIL);
                    String request = jo.getString(Constant.KEY_PATIENT_REQ);

                    //insert data into array for put extra

                    patientName[i] = name;
                    patientGender[i] = gender;
                    patientCell[i] = cell;
                    patientEmail[i] = email;
                    patientRequest[i] = request;


                    //put value into Hashmap
                    HashMap<String, String> patient_data = new HashMap<>();
                    patient_data.put(Constant.KEY_NAME, name);
                    patient_data.put(Constant.KEY_GENDER, gender);
                    patient_data.put(Constant.KEY_CELL, cell);
                    patient_data.put(Constant.KEY_EMAIL, email);
                    patient_data.put(Constant.KEY_PATIENT_REQ, request);

                    list.add(patient_data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                AppointmentActivity.this, list, R.layout.patient_list_items,
                new String[]{Constant.KEY_NAME, Constant.KEY_PATIENT_REQ},
                new int[]{R.id.txt_patient_name, R.id.txt_request});
        CustomList.setAdapter(adapter);

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Intent intent=new Intent(AppointmentActivity.this, AppointmentDetailsActivity.class);
                intent.putExtra("name",patientName[position]);
                intent.putExtra("cell",patientCell[position]);
                intent.putExtra("email",patientEmail[position]);
                intent.putExtra("gender",patientGender[position]);
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
