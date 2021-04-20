package com.android.healthconsult.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.R;
import com.android.healthconsult.prescription.PrescriptionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DoctorActivity extends AppCompatActivity {
    CardView doctor_cardview,assistant_cardview,logout_cardview,allassistant_cardview,patient_req_cardview,prescription_cardview;
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult | Doctor");
        patient_req_cardview=findViewById(R.id.patient_req_cardId);
        patient_req_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, AppointmentHistoryActivity.class);
                startActivity(intent);
            }
        });
        doctor_cardview=findViewById(R.id.doctor_cardview);
        doctor_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, DoctorProfileActivity.class);
                startActivity(intent);
            }
        });
        assistant_cardview=findViewById(R.id.assistant_cv);
        assistant_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, AddAssistantActivity.class);
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
        allassistant_cardview=findViewById(R.id.allassist_cardview);
        allassistant_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, AllAssistantActivity.class);
                startActivity(intent);
            }
        });
        prescription_cardview=findViewById(R.id.prescription_cardview);
        prescription_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, PrescriptionActivity.class);
                startActivity(intent);
            }
        });
    }

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