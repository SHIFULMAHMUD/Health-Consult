package shiful.android.healthconsult;

import androidx.appcompat.app.AppCompatActivity;
import shiful.android.healthconsult.doctor.DoctorLoginActivity;
import shiful.android.healthconsult.doctor.DoctorRegisterActivity;
import shiful.android.healthconsult.patient.PatientLoginActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDialog = new Dialog(this);
    }
    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.custompopupmenu);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        btnFollow = (Button) myDialog.findViewById(R.id.btnskip);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    public void ShowLoginPatient(View v){
        Intent intent=new Intent(HomeActivity.this, PatientLoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void ShowLoginDoctor(View v){
        Intent intent=new Intent(HomeActivity.this, DoctorLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
