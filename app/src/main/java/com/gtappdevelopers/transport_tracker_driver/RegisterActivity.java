package com.gtappdevelopers.transport_tracker_driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone,edtaltphno,fulladdress,countryedt,quartdate;
    private ProgressBar progressBar;
    Button registerbtn;
    private FirebaseAuth mAuth;
    double lat,lon;
    double originlat,originlon;
    Location currentlocation;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int PERMISSIONS_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextName=findViewById(R.id.edit_text_name);
        editTextEmail=findViewById(R.id.edit_text_email);
        editTextPassword=findViewById(R.id.edit_text_password);
        editTextPhone=findViewById(R.id.edit_text_phone);
        edtaltphno=findViewById(R.id.edit_text_alternate_phone);
        fulladdress=findViewById(R.id.edit_text_address);
        countryedt=findViewById(R.id.edit_text_travelcountry);
        quartdate=findViewById(R.id.edit_date_quarintine);
        registerbtn=findViewById(R.id.button_register);
        progressBar=findViewById(R.id.progressbar);

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            Task<Location> task=fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        currentlocation=location;
                        originlat=currentlocation.getLatitude();
                        originlon=currentlocation.getLongitude();

                       // Toast.makeText(RegisterActivity.this, "data = "+currentlocation.getLatitude()+"\n"+currentlocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Fail to get location...", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }

        mAuth = FirebaseAuth.getInstance();
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            Intent i=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(i);
            finish();
            //handle the already login user
        }
    }


    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
       final  String altphone=edtaltphno.getText().toString().trim();
        final String address=fulladdress.getText().toString().trim();
        final String travelcountry=countryedt.getText().toString().trim();
        final String quarintine_dae=quartdate.getText().toString().trim();



        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError(getString(R.string.input_error_phone));
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            editTextPhone.setError(getString(R.string.input_error_phone_invalid));
            editTextPhone.requestFocus();
            return;
        }
        if (altphone.length() != 10) {
            edtaltphno.setError(getString(R.string.input_error_phone_invalid));
            edtaltphno.requestFocus();
            return;
        }
        if (altphone.isEmpty()) {
            edtaltphno.setError(getString(R.string.input_error_phone));
            edtaltphno.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            fulladdress.setError("Enter Full Address");
            fulladdress.requestFocus();
            return;
        }

        if (travelcountry.isEmpty()) {
            countryedt.setError("Enter the Country you have Travelled");
            countryedt.requestFocus();
            return;
        }
        if (quarintine_dae.isEmpty()) {
            quartdate.setError("Enter Valid Quarintine Date");
            quartdate.requestFocus();
            return;
        }









        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                           // Toast.makeText(RegisterActivity.this, "User Registered Successfully...", Toast.LENGTH_SHORT).show();




                            double speed=123.445;
                            double status=0;
                            //User user = null;

                                 User user = new User(
                                        name,originlon,originlat,lat,lon, (float) speed,email,phone,altphone,address,travelcountry,quarintine_dae
                                        ,status);

                               // Toast.makeText(RegisterActivity.this, "data = "+originlat+originlon, Toast.LENGTH_SHORT).show();
                            String authuid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registered Succesfully...", Toast.LENGTH_LONG).show();
                                        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(i);




                                    } else {
                                        //display a failure message

                                        Toast.makeText(RegisterActivity.this, "Fail to Register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });






                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    public boolean havenetwork(){
        boolean HAVEWIFI=false;
        boolean DATA=false;

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    HAVEWIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    DATA=true;

        }
        return DATA|| HAVEWIFI;
    }

}
