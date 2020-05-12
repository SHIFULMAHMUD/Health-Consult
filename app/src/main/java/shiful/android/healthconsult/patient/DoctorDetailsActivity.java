package shiful.android.healthconsult.patient;

import androidx.appcompat.app.AppCompatActivity;
import shiful.android.healthconsult.R;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView txtName, txtQualification, txtDesignation,txtPlace, txtCost;
    String getName, getQualification, getDesignation,getPlace,getCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        txtName=findViewById(R.id.doc_name_tv);
        txtQualification=findViewById(R.id.qualification_tv);
        txtDesignation=findViewById(R.id.designation_tv);
        txtPlace=findViewById(R.id.location_tv);
        txtCost=findViewById(R.id.visit_tv);


        getName = getIntent().getExtras().getString("name");
        getQualification = getIntent().getExtras().getString("qualification");
        getDesignation = getIntent().getExtras().getString("designation");
        getPlace = getIntent().getExtras().getString("place");
        getCost = getIntent().getExtras().getString("visit");

        txtName.setText(getName);
        txtQualification.setText(getQualification);
        txtDesignation.setText(getDesignation);
        txtPlace.setText(getPlace);
        txtCost.setText(getCost);
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