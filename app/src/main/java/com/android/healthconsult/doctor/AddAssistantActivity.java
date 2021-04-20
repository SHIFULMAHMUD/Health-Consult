package com.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.Constant;
import com.android.healthconsult.R;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddAssistantActivity extends AppCompatActivity {
EditText nameEt,cellEt,passEt,docNameEt;
    String getCell;
    Button addBtn;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assistant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Assistant");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        nameEt=findViewById(R.id.editTextAssistantName);
        cellEt=findViewById(R.id.editTextAssistantPhone);
        passEt=findViewById(R.id.editTextAssistantPassword);
        docNameEt=findViewById(R.id.editTextDoctorName);
        addBtn=findViewById(R.id.cirAddAssistantButton);
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        getData("");
    addBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        addAssistant();
    }
    });
    }
    private void addAssistant() {

        //Getting values from edit texts
        final String name = nameEt.getText().toString().trim();
        final String cell = cellEt.getText().toString().trim();
        final String password = passEt.getText().toString().trim();
        final String doc_name = docNameEt.getText().toString().trim();

        //Checking  field/validation
        if (name.isEmpty()) {
            nameEt.setError("Please enter assistant name !");
            requestFocus(nameEt);
        }
        else if (cell.isEmpty()) {

            cellEt.setError("Please enter assistant phone number !");
            requestFocus(cellEt);

        }

        else if (cell.length()!=11 || !cell.startsWith("01")) {

            cellEt.setError("Please enter valid phone number !");
            requestFocus(cellEt);

        }
        else if (password.isEmpty()) {

            passEt.setError("Please enter password !");
            requestFocus(passEt);
        }
        else if (password.length() < 4) {

            passEt.setError("Password should be more than 3 characters!");
            requestFocus(passEt);
        }
        else if (doc_name.isEmpty()) {
            docNameEt.setError("Please enter doctor name !");
            requestFocus(docNameEt);
        }


        else
        {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Adding Assistant");
            loading.setMessage("Please wait....");
            loading.show();

            final StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.ADD_ASSISTANT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //for track response in logcat
                    Log.d("res", response);
                    loading.dismiss();

                    if (response.trim().equals("success")) {
                        Intent intent = new Intent(AddAssistantActivity.this, AllAssistantActivity.class);
                        Toasty.success(AddAssistantActivity.this, "Assistant Added", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    } else if (response.trim().equals("exists")) {

                        Toasty.warning(AddAssistantActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.trim().equals("failure")) {

                        Toasty.error(AddAssistantActivity.this, "Adding Assistant Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(AddAssistantActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Constant.KEY_DOC_NAME, doc_name);
                    params.put(Constant.KEY_DOCTOR_CELL, getCell);
                    params.put(Constant.KEY_NAME, name);
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
    private void getData(String text) {


        //for showing progress dialog
        loading = new ProgressDialog(AddAssistantActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.DOC_VIEW_URL+getCell;
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
                        Toasty.error(AddAssistantActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(AddAssistantActivity.this);
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
                Toasty.info(AddAssistantActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    final String name = jo.getString(Constant.KEY_NAME);
                    //insert data into array for put extra
                    docNameEt.setText(name);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
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