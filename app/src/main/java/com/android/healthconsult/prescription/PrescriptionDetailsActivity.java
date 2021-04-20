package com.android.healthconsult.prescription;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.android.healthconsult.R;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PrescriptionDetailsActivity extends AppCompatActivity {

    //how many headers or column you need, add here by using ,
    //headers and get clients para meter must be equal
    private  String[] header ={"Type","Descriptions"};

    String longText,shortText,getPrescription,getDisease,getTest,getInfo;

    TextView txtGeneratePdf,txtName,txtName2,txtDate,txtAge,txtWeight,txtPulse,txtDisease,txtTest, txtGender,txtPrescription,txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Prescription Details");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        txtName=findViewById(R.id.txtName);
        txtName2=findViewById(R.id.txtName2);
        txtGender=findViewById(R.id.txtGender);
        txtDate=findViewById(R.id.txtDate);
        txtPrescription=findViewById(R.id.txtPrescription);
        txtInfo=findViewById(R.id.txtInfo);
        txtAge=findViewById(R.id.txtAge);
        txtWeight=findViewById(R.id.txtWeight);
        txtPulse=findViewById(R.id.txtPulse);
        txtDisease=findViewById(R.id.txtDisease);
        txtTest=findViewById(R.id.txtTest);

        String getPatientName=getIntent().getExtras().getString("name");
        String getGender=getIntent().getExtras().getString("gender");
        String getDate=getIntent().getExtras().getString("date");
        final String getSpeciality=getIntent().getExtras().getString("dr_speciality");
        final String getQualification=getIntent().getExtras().getString("dr_qualification");
        getPrescription=getIntent().getExtras().getString("prescription");
        String getDrName=getIntent().getExtras().getString("dr_name");
        getInfo=getIntent().getExtras().getString("info");

        String getAge=getIntent().getExtras().getString("age");
        String getWeight=getIntent().getExtras().getString("weight");
        String getPulse=getIntent().getExtras().getString("pulse");
        getDisease=getIntent().getExtras().getString("disease");
        getTest=getIntent().getExtras().getString("test");

        txtName.setText(getDrName);
        txtName2.setText(getPatientName);
        txtDate.setText(getDate);
        txtGender.setText(getGender);
        txtPrescription.setText(getPrescription);
        txtInfo.setText(getInfo);

        txtAge.setText(getAge);
        txtWeight.setText(getWeight);
        txtPulse.setText(getPulse);
        txtDisease.setText(getDisease);
        txtTest.setText(getTest);

    }


    //for pdf
    private ArrayList<String[]> getClients(){
        ArrayList<String[]> rows=new ArrayList<>();
        rows.add(new String[] {"Medicine",getPrescription});
        rows.add(new String[] {"Disease",getDisease});
        rows.add(new String[] {"General Information",getInfo});
//        you can add more row above format
        return rows;
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
