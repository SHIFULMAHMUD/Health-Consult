package shiful.android.healthconsult;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class ProfileActivity extends AppCompatActivity {
    TextView nametv, emailtv, celltv, gendertv,accounttypetv;
    private ProgressDialog loading;
    String getCell;
    int MAX_SIZE=999;
    Button updateBtn;
    public String userName[]=new String[MAX_SIZE];
    public String userCell[]=new String[MAX_SIZE];
    public String userEmail[]=new String[MAX_SIZE];
    public String userGender[]=new String[MAX_SIZE];
    public String userAccounttype[]=new String[MAX_SIZE];
    public String userPassword[]=new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nametv=findViewById(R.id.profile_name);
        emailtv=findViewById(R.id.profile_email);
        celltv=findViewById(R.id.profile_cell);
        gendertv=findViewById(R.id.profile_gender);
        accounttypetv=findViewById(R.id.profile_ac_type);
        updateBtn=findViewById(R.id.profile_update_btn);
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
        loading = new ProgressDialog(ProfileActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.USER_VIEW_URL+getCell;
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
                        Toasty.error(ProfileActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
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
                Toasty.info(ProfileActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    final String name = jo.getString(Constant.KEY_NAME);
                    final String cell = jo.getString(Constant.KEY_CELL);
                    final String account_type = jo.getString(Constant.KEY_AC_TYPE);
                    final String gender = jo.getString(Constant.KEY_GENDER);
                    final String email = jo.getString(Constant.KEY_EMAIL);
                    final String password = jo.getString(Constant.KEY_PASSWORD);
                    //insert data into array for put extra

                    userName[i] = name;
                    userCell[i] = cell;
                    userAccounttype[i] = account_type;
                    userGender[i] = gender;
                    userEmail[i] = email;
                    userPassword[i] = password;

                    nametv.setText(name);
                    celltv.setText(cell);
                    accounttypetv.setText(account_type);
                    gendertv.setText(gender);
                    emailtv.setText(email);

                    updateBtn=findViewById(R.id.profile_update_btn);
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(ProfileActivity.this,UpdateProfileActivity.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            intent.putExtra("gender",gender);
                            startActivity(intent);
                        }
                    });
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