package com.android.healthconsult.assistant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

public class AssistantProfileActivity extends AppCompatActivity {
    TextView nametv, docCellTV, celltv, docNameTV;
    private ProgressDialog loading;
    ImageView profile_image;
    String getCell;
    int MAX_SIZE=999;
    Button updateBtn;
    public String userName[]=new String[MAX_SIZE];
    public String userCell[]=new String[MAX_SIZE];
    public String docCell[]=new String[MAX_SIZE];
    public String docName[]=new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assistant Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        nametv=findViewById(R.id.profile_name);
        docNameTV=findViewById(R.id.profile_doc_name);
        celltv=findViewById(R.id.profile_assistant_cell);
        docCellTV=findViewById(R.id.profile_doctor_cell);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Log
        Log.d("SP_CELL",getCell);
        //call function to get data
        getData("");
    }
    private void getData(String text) {


        //for showing progress dialog
        loading = new ProgressDialog(AssistantProfileActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.ASSISTANT_PROFILE_URL+getCell;
        Log.d("SP_URL",URL);
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
                        Toasty.error(AssistantProfileActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(AssistantProfileActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        //Create json object for receiving json data
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toasty.info(AssistantProfileActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    final String doc_name = jo.getString(Constant.KEY_DOC_NAME);
                    final String doc_cell = jo.getString(Constant.KEY_DOCTOR_CELL);
                    final String name = jo.getString(Constant.KEY_NAME);
                    final String cell = jo.getString(Constant.KEY_CELL);

                    //insert data into array for put extra

                    userName[i] = name;
                    userCell[i] = cell;
                    docCell[i] = doc_cell;
                    docName[i]=doc_name;
                    nametv.setText(name);
                    celltv.setText(cell);
                    docCellTV.setText(doc_cell);
                    docNameTV.setText(doc_name);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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