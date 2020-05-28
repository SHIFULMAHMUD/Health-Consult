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

public class AmbulanceActivity extends AppCompatActivity {
    ListView CustomList;
    private ProgressDialog loading;
    int MAX_SIZE=999;
    Button btnSearch;
    EditText txtSearch;
    public String ambName[]=new String[MAX_SIZE];
    public String ambCell[]=new String[MAX_SIZE];
    public String ambPlace[]=new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Ambulance");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        btnSearch=findViewById(R.id.btn_search);
        txtSearch=findViewById(R.id.txt_search);
        CustomList=(ListView)findViewById(R.id.ambulance_list);
        //call function to get data
        getData("");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search=txtSearch.getText().toString();

                if (search.isEmpty())
                {
                    Toasty.info(AmbulanceActivity.this, "Please enter name or location!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    getData(search);

                }

            }
        });

    }
    private void getData(String text) {

        //for showing progress dialog
        loading = new ProgressDialog(AmbulanceActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.AMBULANCE_URL+"&text="+text;
        Log.d("amb_url",URL);

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
                        Toasty.error(AmbulanceActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(AmbulanceActivity.this);
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
                Toasty.info(AmbulanceActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String name = jo.getString(Constant.KEY_AMB_NAME);
                    String cell = jo.getString(Constant.KEY_AMB_CELL);
                    String place = jo.getString(Constant.KEY_AMB_PLACE);
                    //insert data into array for put extra
                    ambName[i] = name;
                    ambCell[i] = cell;
                    ambPlace[i] = place;

                    //put value into Hashmap
                    HashMap<String, String> ambulance_data = new HashMap<>();
                    ambulance_data.put(Constant.KEY_AMB_NAME, name);
                    ambulance_data.put(Constant.KEY_AMB_CELL, cell);
                    ambulance_data.put(Constant.KEY_AMB_PLACE, place);
                    list.add(ambulance_data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                AmbulanceActivity.this, list, R.layout.ambulance_list_items,
                new String[]{Constant.KEY_AMB_NAME,Constant.KEY_AMB_CELL,Constant.KEY_AMB_PLACE},
                new int[]{R.id.txt_ambulance_name,R.id.txt_ambulance_phone,R.id.txt_ambulance_location});
        CustomList.setAdapter(adapter);
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
