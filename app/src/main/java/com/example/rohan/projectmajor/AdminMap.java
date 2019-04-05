package com.example.rohan.projectmajor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminMap extends FragmentActivity implements OnMapReadyCallback {

    DatabaseReference databaseReference;
    List<MyLatLng> patients=new ArrayList<>();
    public  static final int RC=1;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    private void insertMarkers(List<MyLatLng> list) {
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //Toast.makeText(this, "qwertyuiop", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < list.size(); i++) {
            final LatLng position = new LatLng(Double.parseDouble(list.get(i).getlat()), Double.parseDouble(list.get(i).getlng()));
           // Toast.makeText(this, String.valueOf(Double.parseDouble(list.get(i).getlat())), Toast.LENGTH_SHORT).show();
            final MarkerOptions options = new MarkerOptions().position(position);

            mMap.addMarker(options);

            builder.include(position);
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        databaseReference= FirebaseDatabase.getInstance().getReference("UsersLocation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                patients.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                   // Toast.makeText(AdminMap.this, "asdfghjkl;", Toast.LENGTH_SHORT).show();
                    MyLatLng patient = snapshot.getValue(MyLatLng.class);
                    patients.add(patient);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        insertMarkers(patients);
        //LatLng myLoc = new LatLng(lati, longi);
        LatLng p1 = new LatLng(28.5574132,77.366343);
        LatLng p2 = new LatLng(28.6292281,77.3721442);
        LatLng p3 = new LatLng(28.5338185, 77.3717408);
        LatLng p4 = new LatLng(28.6301615, 77.371186);
        //LatLng gym = new LatLng(28.538572, 77.364504);
        mMap.addMarker(new MarkerOptions().position(p1).title("p1"));
        mMap.addMarker(new MarkerOptions().position(p2).title("p2"));
        mMap.addMarker(new MarkerOptions().position(p3).title("p3"));
        mMap.addMarker(new MarkerOptions().position(p4).title("p4"));
        //mMap.addMarker(new MarkerOptions().position(gym).title("GYM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p4));

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},RC);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

}
