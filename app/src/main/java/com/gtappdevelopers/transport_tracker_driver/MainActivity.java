package com.gtappdevelopers.transport_tracker_driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements LocationListener {

    private FirebaseAuth mAuth;
    protected LocationManager locationManager;
    double originalat,originallon;
    protected LocationListener locationListener;
    private static final int PERMISSIONS_REQUEST = 100;

    TextView nametxt;

    TextView btn1,btn2,btn3;

    private RequestQueue requestQueue;
    private JSONObject response1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        nametxt=findViewById(R.id.name);

        btn1=findViewById(R.id.button);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);


        setContentView(R.layout.activity_main);

        requestQueue= Volley.newRequestQueue(this);
        parseJson();

        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();

            //requestpermission();
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
            //finish();
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();


            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);






        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
    }

    private void parseJson() {
        String url="https://api.covid19india.org/data.json";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                response1=response;
                try {
                    JSONArray array=response.getJSONArray("statewise");
                    JSONObject object=array.getJSONObject(0);

                    Log.d("dataaa", object.toString());
                   // txt_active.setText(object.getString("active"));
                   // txt_total.setText(object.getString("confirmed"));
                   // txt_recovered.setText(object.getString("recovered"));
                  //  txt_deaths.setText(object.getString("deaths"));


                    String active=object.getString("active");
                    String confir=object.getString("confirmed");
                    String recover=object.getString("recovered");
                    String death=object.getString("deaths");





                    String lastUpdated="Last Updated: "+ object.getString("lastupdatedtime");
                   /// Toast.makeText(MainActivity.this, "update = "+lastUpdated+"\n"+active+"\n"+recover+"\n"+confir+"\n"+death, Toast.LENGTH_SHORT).show();
                    Log.e("DATA","covid data = "+active+"\n"+confir+"\n"+recover+"\n"+death);

                    //btn1.setText(death);
//                    btn2.setText(recover);
                  //  btn3.setText(confir);
                   // Log.e("DATA = "+"covid DATA = "+lastUpdated+active+confir+"\n"+recover+"\n"+death+"\n");

                    //txt_updated.setText(lastUpdated);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }



    private void requestpermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this).setTitle("Permission Needed..").setMessage("Please Grant the Permission")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST);

                        }
                    }).create().show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST);

        }
    }



    private void startTrackerService() {

        if (mAuth.getCurrentUser() != null) {


            startService(new Intent(this, TrackingService.class));
            // finish();
        }
        else {
            Intent i=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startTrackerService();
        } else {
            Toast.makeText(this, "Permission Denined..", Toast.LENGTH_SHORT).show();
            // finish();

        }
    }


    @Override
    public void onLocationChanged(Location location) {

        originalat=location.getLatitude();
        originallon=location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void helpcenters(View view) {

        Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mygov.in/covid-19/"));
        startActivity(Getintent);
    }

    public void getinfo(View view) {

        Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/events-as-they-happen"));
        startActivity(Getintent);


    }


    public void btn(View view) {
        Intent i=new Intent(MainActivity.this,CoronaCounter.class);
        startActivity(i);

    }

    public void coronameter(View view) {
        Intent i=new Intent(MainActivity.this,Login.class);
        startActivity(i);


    }
}
