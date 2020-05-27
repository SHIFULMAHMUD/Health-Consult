package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import shiful.android.healthconsult.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientActivity extends AppCompatActivity {
    CardView profile_cv,patient_covid_check_cv,doctor_cv,ambulance_cv,history_cv,health_tips_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult");
        profile_cv=findViewById(R.id.profile_cv);
        profile_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, PatientProfileActivity.class);
                startActivity(i);
            }
        });
        patient_covid_check_cv=findViewById(R.id.patient_covid_check_cv);
        patient_covid_check_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, WebActivity.class);
                i.putExtra("url", "https://coronachecker.dh.health/");
                startActivity(i);
            }
        });
        doctor_cv=findViewById(R.id.doctor_cv);
        doctor_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, CategoryActivity.class);
                startActivity(i);
            }
        });
        ambulance_cv=findViewById(R.id.ambulance_cv);
        ambulance_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, AmbulanceActivity.class);
                startActivity(i);
            }
        });
        history_cv=findViewById(R.id.history_cv);
        history_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, AppointmentHistoryActivity.class);
                startActivity(i);
            }
        });
        health_tips_cv=findViewById(R.id.health_tips_cv);
        health_tips_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, HealthTipsActivity.class);
                startActivity(i);
            }
        });
    }
}
