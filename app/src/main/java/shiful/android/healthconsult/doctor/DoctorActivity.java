package shiful.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import shiful.android.healthconsult.patient.PatientProfileActivity;
import shiful.android.healthconsult.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorActivity extends AppCompatActivity {
    CardView doctor_cardview,appointment_cardview,history_cardview,logout_cardview;

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
}
