package shiful.android.healthconsult.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity{
    EditText etxtTitle,etxtMessage;
    Button btnSend;
    ProgressDialog loading;
    String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        btnSend = findViewById(R.id.btn_send);
        etxtTitle = findViewById(R.id.etxt_title);
        etxtMessage = findViewById(R.id.etxt_message);
        GetToken();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etxtTitle.getText().toString();
                String message = etxtMessage.getText().toString();
                String token=to;

                if (!title.isEmpty() || !message.isEmpty()) {

                    SaveContact(title,message,token);
                }
            }
        });
    }
    public void GetToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            to = task.getResult().getToken();
                        }
                    }
                });
    }
    //Save contact method
    public void  SaveContact(String get_title, String msg, final String token)
    {
        final String title=get_title;
        final String message=msg;

        if (message.isEmpty())
        {
            Toast.makeText(this, "Cell Can't Empty", Toast.LENGTH_SHORT).show();
        }

        else if(title.isEmpty())
        {
            Toast.makeText(this, "Email Can't Empty", Toast.LENGTH_SHORT).show();
        }

        else {
            loading = new ProgressDialog(this);

            loading.setMessage("Please wait....");
            loading.show();

            String URL = Constant.NOTIFICATION_API_URL;

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //for track response in logcat
                            Log.d("RESPO", response);
                            // Log.d("RESPONSE", userCell);

                            //If we are getting success from server
                            /*if (response.trim().equals("success")) {

                                loading.dismiss();
                                //Starting profile activity
                                Toasty.success(NotificationActivity.this, " Successfully Saved!", Toast.LENGTH_SHORT).show();
                            }
                            //If we are getting success from server
                            else if (response.trim().equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                //Intent intent = new Intent(AddContactsActivity.this, HomeActivity.class);
                                Toasty.error(NotificationActivity.this, " Save failed!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);
                            } else {

                                loading.dismiss();
                                Toasty.error(NotificationActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }*/
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(NotificationActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put("title", title);
                    params.put("message",message);
                    params.put("token",token);

                    Log.d("msg",title+ " "+message+" "+token);
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
            requestQueue.add(stringRequest);
        }
    }
}