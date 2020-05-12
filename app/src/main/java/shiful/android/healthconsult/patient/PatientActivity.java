package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import shiful.android.healthconsult.LoginActivity;
import shiful.android.healthconsult.ProfileActivity;
import shiful.android.healthconsult.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientActivity extends AppCompatActivity {
    CardView profile_cv,logout_cv,doctor_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        profile_cv=findViewById(R.id.profile_cv);
        profile_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        logout_cv=findViewById(R.id.patient_logout_cv);
        logout_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PatientActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
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
    }
}
