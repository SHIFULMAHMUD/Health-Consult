package com.android.healthconsult.assistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;
import com.android.healthconsult.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AssistantActivity extends AppCompatActivity {
    private static long back_pressed;
    private static final int TIME_DELAY = 2000;
    CardView profileCV,appointmentCV,historyCV,logoutCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Consult | Assistant");
        profileCV=findViewById(R.id.profile_cardview);
        profileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AssistantActivity.this,AssistantProfileActivity.class);
                startActivity(intent);
            }
        });
        appointmentCV=findViewById(R.id.appointment_cv);
        appointmentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AssistantActivity.this,AppointmentActivity.class);
                startActivity(intent);
            }
        });
        historyCV=findViewById(R.id.history_cardview);
        historyCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AssistantActivity.this,PatientHistoryActivity.class);
                startActivity(intent);
            }
        });
        logoutCV=findViewById(R.id.logout_cardview);
        logoutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AssistantActivity.this,AssistantLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
