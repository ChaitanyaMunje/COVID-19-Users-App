package com.gtappdevelopers.transport_tracker_driver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    // for checking corona test....
    private RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6;

    String q1,q2,q3,q4,q5,q6;
    String ans1,ans2,ans3,ans4,ans5,ans6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      //  radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //radioGroup.clearCheck();
            radioGroup1=findViewById(R.id.radiogrp1);
            radioGroup2=findViewById(R.id.radiogroup2);
            radioGroup3=findViewById(R.id.radiogroup3);
            radioGroup4=findViewById(R.id.radiogroup4);
            radioGroup6=findViewById(R.id.radiogroup6);
            radioGroup5=findViewById(R.id.radiogroup5);
            radioGroup1.clearCheck();
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        radioGroup4.clearCheck();
        radioGroup5.clearCheck();
        radioGroup6.clearCheck();

/*
  radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

 */
radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @SuppressLint("ResourceType")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton=group.findViewById(checkedId);
        if (null!= radioButton && checkedId > -1){
            q1= (String) radioButton.getText();
        }
    }
});

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=group.findViewById(checkedId);
                if (null!= radioButton && checkedId > -1){
                    q2= (String) radioButton.getText();
                }
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=group.findViewById(checkedId);
                if (null!= radioButton && checkedId > -1){
                    q3= (String) radioButton.getText();
                }
            }
        });

        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=group.findViewById(checkedId);
                if (null!= radioButton && checkedId > -1){
                    q4= (String) radioButton.getText();
                }
            }
        });

        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=group.findViewById(checkedId);
                if (null!= radioButton && checkedId > -1){
                    q5= (String) radioButton.getText();
                }
            }
        });

        radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=group.findViewById(checkedId);
                if (null!= radioButton && checkedId > -1){
                    q6= (String) radioButton.getText();
                }
            }
        });


    }

    public void clickresult(View view) {

        int id1,id2,id3,id4,id5,id6;
        id1=radioGroup1.getCheckedRadioButtonId();
        id2=radioGroup2.getCheckedRadioButtonId();
        id3=radioGroup3.getCheckedRadioButtonId();
        id4=radioGroup4.getCheckedRadioButtonId();
        id5=radioGroup5.getCheckedRadioButtonId();
        id6=radioGroup6.getCheckedRadioButtonId();

        Toast.makeText(this, "id = "+id1+id2+id3+id4+id5+id6, Toast.LENGTH_SHORT).show();


        generateresult(q1,q2,q3,q4,q5,q6);

        /// ans3 for age
        // ans 4 for country
        // ans 5 for contact with covid patients
        //ans 6 for symptoms for covid
        if (ans3.equals("Safe") && ans4.equals("Safe") && ans5.equals("Safe") && ans6.equals("Safe")){
            String safe="You are Safe";
            new AlertDialog.Builder(this,R.style.GreenDialog)
                    .setTitle("COVID-19 Tracker")
                    .setCancelable(true)
                    .setMessage(safe)

                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            radioGroup1.clearCheck();
                            radioGroup2.clearCheck();
                            radioGroup3.clearCheck();
                            radioGroup4.clearCheck();
                            radioGroup5.clearCheck();
                            radioGroup6.clearCheck();
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.drawable.ic_digital_map)
                    .show();
        }
        else {

            String danger = "You should take care of yourself and quarintine yorselves at your Home. ";

            new AlertDialog.Builder(this, R.style.RedDialog)
                    .setTitle("COVID-19 Tracker")
                    .setCancelable(true)
                    .setMessage(danger)

                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            radioGroup1.clearCheck();
                            radioGroup2.clearCheck();

                            radioGroup3.clearCheck();
                            radioGroup4.clearCheck();
                            radioGroup5.clearCheck();
                            radioGroup6.clearCheck();
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.drawable.ic_digital_map)
                    .show();

        }

    }

    private void generateresult(String ques1,String ques2,String ques3,String ques4,String ques5,String ques6){

        if (ques4.equals("China") || ques4.equals("Italy") || ques4.equals("Spain") || ques4.equals("Iran")|| ques4.equals("Europe")){
            ans4="Danger";
        }
        else{
            ans4="Safe";


        }

        if (ques3.equals("51-60 years") || ques3.equals("Above 60 years")){
           ans3="Danger";
        }
        else {
            ans3="Safe";
        }

        if (ques5.equals("Yes")){
            ans5="Danger";
        }
        else {
            ans5="Safe";

        }

        if (ques6.equals("Yes")){
            ans6="Danger";
        }
        else {
            ans6="Safe";


        }

    }
}
