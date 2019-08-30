package com.example.rohan.projectmajor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lati, longi;
    final static int RC=1;
    FirebaseUser firebaseUser;
    DatabaseReference dr;


    //LatLng myLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lati = getIntent().getDoubleExtra("Lat", 0);
        longi = getIntent().getDoubleExtra("Lng", 0);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }



        //String lati,longi;
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        dr=FirebaseDatabase.getInstance().getReference("UsersSavedLocation").child(firebaseUser.getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyLatLng loc=new MyLatLng();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        LatLng myLoc = new LatLng(lati, longi);
//        LatLng jaypeehospital = new LatLng(28.514434,77.371749);
//        LatLng market = new LatLng(28.570776,77.326095);
//        LatLng office = new LatLng(28.537856, 77.342037);
//        LatLng home = new LatLng(28.629915, 77.372483);
//        LatLng gym = new LatLng(28.538572, 77.364504);
        ArrayList<MyLatLng> myLatLngArrayList = new ArrayList<>();
        myLatLngArrayList= getIntent().getParcelableArrayListExtra("latlngarraylist");
        Log.d("tag", "onMapReady: "+myLatLngArrayList);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(myLatLngArrayList.get(0).getlat())
                        ,Double.parseDouble(myLatLngArrayList.get(0).getlng())))
                .title("My Hospital"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(myLatLngArrayList.get(1).getlat())
                        ,Double.parseDouble(myLatLngArrayList.get(1).getlng())))
                .title("Market"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(myLatLngArrayList.get(2).getlat())
                        ,Double.parseDouble(myLatLngArrayList.get(2).getlng())))
                .title("My OFFICE"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(myLatLngArrayList.get(3).getlat())
                        ,Double.parseDouble(myLatLngArrayList.get(3).getlng())))
                .title("MY HOME"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(myLatLngArrayList.get(4).getlat())
                        ,Double.parseDouble(myLatLngArrayList.get(4).getlng())))
                .title("GYM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},RC);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


    }


}
