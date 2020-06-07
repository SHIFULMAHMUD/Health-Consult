package shiful.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;
import shiful.android.healthconsult.notification.NotificationActivity;
import shiful.android.healthconsult.patient.PatientActivity;
import shiful.android.healthconsult.patient.PatientLoginActivity;
import shiful.android.healthconsult.patient.PatientProfileActivity;
import shiful.android.healthconsult.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DoctorActivity extends AppCompatActivity {
    CardView doctor_cardview,appointment_cardview,history_cardview,logout_cardview;
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult");

        doctor_cardview=findViewById(R.id.doctor_cardview);
        doctor_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, DoctorProfileActivity.class);
                startActivity(intent);
            }
        });
        appointment_cardview=findViewById(R.id.appointment_cv);
        appointment_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, AppointmentActivity.class);
                startActivity(intent);
            }
        });
        history_cardview=findViewById(R.id.history_cardview);
        history_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, PatientHistoryActivity.class);
                startActivity(intent);
            }
        });
        logout_cardview=findViewById(R.id.logout_cardview);
        logout_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, DoctorLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(DoctorActivity.this, NotificationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
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
}
