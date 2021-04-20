package com.android.healthconsult.prescription;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.healthconsult.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.fragment.app.Fragment;

import com.android.healthconsult.customfonts.MyTextView;
import es.dmoral.toasty.Toasty;

import com.android.healthconsult.Constant;

public class AddPrescriptionFragment extends Fragment {
    EditText etxtCell,etxtName,etxtGender,etxtAge,etxtInfo,etxtDate,etxtWeight,etxtPulse,etxtDiease;
    int MAX_SIZE=999;
    public String userName[]=new String[MAX_SIZE];
    public String userSpecialist[]=new String[MAX_SIZE];
    public String userQualification[]=new String[MAX_SIZE];
    MultiAutoCompleteTextView etxtPrescriptionDetails,etxtTest;
    //for date
    public Calendar myCalendar = Calendar.getInstance();
    public DatePickerDialog.OnDateSetListener date;

    MyTextView btnSave;
    private ProgressDialog loading;
    String doctorCell;

    String[] prescription={"Napa","Napa Extra","Paracitamol","Pantonix","Sliepump","Eplim","Duloxetine","Doxepin","Dosulepin","Fluconazole","Flagil","Felbamate","Felbetol","Flupentixol","Abraxen","Acetic acid","Aceon","Abelcet","Acidol"," Algin","Abemaciclib Tablet","Promazine","Penciclovir Cream","Pazopanib Tablets","Pancrelipase Tablets","Paclitexel Tabelet","1+1+1","1+0+1","1+1+0","0+1+1","0+0+1","0+1+0"};

    String[]test_list={"Blood Test","Cardiac","Vitamin D test","Vitamin B12 test","Thyroid Function test","Prothrombin Time (PT)","Liver Function Test","kidney function test","Full blood count test","Colonoscopy Computer Axial Tomography (CT or CAT Scan)"," C-reactive protein Test","Cholesterol & lipid test","Bone Density Study"," Calcium blood test,","Blood Glucose Test","Biopsy","ECR Test","Electrocardiogram (EKG)","Endoscopy","Echocardiography","Complete Blood Count (CBC)","Magnesium blood test","Magnetic Resonance Imaging (MRI)","Diabetics Test","Mammography","ECG","Urine Test"};

    public AddPrescriptionFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_prescription, container, false);
        btnSave=(MyTextView) view.findViewById(R.id.btnSave);

        etxtCell= view.findViewById(R.id.etxtCell);
        etxtName= view.findViewById(R.id.etxtName);
        etxtGender= view.findViewById(R.id.gender);
        etxtDate= view.findViewById(R.id.etxtDate);
        etxtInfo= view.findViewById(R.id.etxtGeneralInfo);
        etxtPrescriptionDetails= view.findViewById(R.id.etxtPrescription);
        etxtWeight= view.findViewById(R.id.etxtWeight);
        etxtPulse= view.findViewById(R.id.etxtPulseRate);
        etxtDiease= view.findViewById(R.id.etxtDisease);
        etxtAge= view.findViewById(R.id.etxtAge);
        etxtTest=view.findViewById(R.id.etxtTest);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getContext().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        doctorCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        getData("");

        ArrayAdapter adapter = new
                ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,prescription);

        etxtPrescriptionDetails.setAdapter(adapter);
        etxtPrescriptionDetails.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        ArrayAdapter adapter2 = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,test_list);

        etxtTest.setAdapter(adapter2);
        etxtTest.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        //For choosing account type and open alert dialog
        etxtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Gender:");
                builder.setIcon(R.drawable.ic_gender);


                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtGender.setText("Male");
                                break;

                            case 1:
                                etxtGender.setText("Female");
                                break;


                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog genderTypeDialog = builder.create();

                genderTypeDialog.show();
            }

        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.loading)
                        .setMessage("Want to Save Prescription?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                SaveData(); //call SaveContact function
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform Your Task Here--When No is pressed
                                dialog.cancel();
                            }
                        }).show();


            }
        });




        //for input date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        etxtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    //for date input
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etxtDate.setText(sdf.format(myCalendar.getTime()));
    }


    //Save contact method
    public void  SaveData()
    {

        final String cell=etxtCell.getText().toString();
        final String name=etxtName.getText().toString();

        final String gender=etxtGender.getText().toString();
        final String date=etxtDate.getText().toString();

        final String prescription=etxtPrescriptionDetails.getText().toString().trim();
        final String info=etxtInfo.getText().toString().trim();
        final String weight=etxtWeight.getText().toString();
        final String pulse=etxtPulse.getText().toString();
        final String disease=etxtDiease.getText().toString();
        final String age=etxtAge.getText().toString();
        final String test=etxtTest.getText().toString();

        if (name.isEmpty())
        {
            Toast.makeText(getContext(), "Name Can't Empty", Toast.LENGTH_SHORT).show();
        }
        else if (cell.length()!=11)
        {
            Toast.makeText(getContext(), "Invalid Cell!", Toast.LENGTH_SHORT).show();
        }

        else if(age.isEmpty())
        {
            Toast.makeText(getContext(), "Age Can't Empty", Toast.LENGTH_SHORT).show();
        }
        else if(gender.isEmpty())
        {
            Toast.makeText(getContext(), "Gender Can't Empty", Toast.LENGTH_SHORT).show();
        }

        else if(date.isEmpty())
        {
            Toast.makeText(getContext(), "Date Can't Empty", Toast.LENGTH_SHORT).show();
        }

        else if(prescription.isEmpty())
        {
            Toast.makeText(getContext(), "Prescription Can't Empty", Toast.LENGTH_SHORT).show();
        }
        else if(info.isEmpty())
        {
            Toast.makeText(getContext(), "Info Can't Empty", Toast.LENGTH_SHORT).show();
        }


        else {
            loading = new ProgressDialog(getActivity());
            loading.setMessage("Please wait....");
            loading.show();

            String URL = Constant.SAVE_PRESCRIPTION_URL;


            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //for track response in logcat
                            Log.d("RESPONSE", response);
                            // Log.d("RESPONSE", userCell);


                            //If we are getting success from server
                            if (response.equals("success")) {

                                loading.dismiss();
                                //Starting profile activity
                                Toasty.success(getActivity(), " Successfully Saved!", Toast.LENGTH_SHORT).show();


                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity
                                Toasty.error(getActivity(), " Save failed!", Toast.LENGTH_SHORT).show();


                            } else {

                                loading.dismiss();
                                Toasty.error(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(getActivity(), "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request


                    params.put(Constant.KEY_CELL, cell);
                    params.put(Constant.KEY_NAME, name);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_AGE, age);
                    params.put(Constant.KEY_PULSE, pulse);
                    params.put(Constant.KEY_WEIGHT, weight);
                    params.put(Constant.KEY_DISEASE, disease);
                    params.put(Constant.KEY_TEST, test);
                    params.put(Constant.KEY_DATE, date);
                    params.put(Constant.KEY_PRESCRIPTION, prescription);
                    params.put(Constant.KEY_INFO, info);
                    params.put(Constant.KEY_DR_NAME, userName[0]);
                    params.put(Constant.KEY_SPECIALITY, userSpecialist[0]);
                    params.put(Constant.KEY_QUALIFICATION, userQualification[0]);
                    params.put(Constant.KEY_DR_CELL, doctorCell);

                    Log.d("values",doctorCell+" "+cell+" "+name+ " "+gender+" "+prescription);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

    }
    private void getData(String text) {


        //for showing progress dialog
        loading = new ProgressDialog(getActivity());
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.DOC_VIEW_URL+doctorCell;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toasty.error(getActivity(), "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    private void showJSON(String response) {

        //Create json object for receiving json data
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toasty.info(getActivity(), "No Data Available!", Toast.LENGTH_SHORT).show();
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    final String name = jo.getString(Constant.KEY_NAME);
                    final String specialist = jo.getString(Constant.KEY_SPECIALITY);
                    final String qualification = jo.getString(Constant.KEY_QUALIFICATION);

                    //insert data into array for put extra

                    userName[i] = name;
                    userSpecialist[i] = specialist;
                    userQualification[i] = qualification;


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
