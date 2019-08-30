package com.example.rohan.projectmajor;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rohan.projectmajor.Fragments.RecyclerAdapter;
import com.example.rohan.projectmajor.Fragments.Routine;
import com.example.rohan.projectmajor.Fragments.adapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    CircleImageView imageProfile;
    TextView nameProfile, emailProfile, timetble;
    LocationManager lm;
    private static final int RC=1;
    String Lat, Lng;
    double lati,longi;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReference2;
    ImageView gps,facerecog,timetable;

    //String topic[]={"GO FOR YOGA CLASS", "HAVE BREAKFAST","MEDICINE","HAVE LUNCH","MEDICINE","HAVE DINNER","MEDICINE"};
    //String time2[]={"7:00","9:00","11:00","13:00","14:00","19:00","20:00"};

    List<Routine> routineList = new ArrayList<Routine>();

    RecyclerView recyclerView;
    ArrayList<MyLatLng> latLngArrayList = new ArrayList<>();
    //private static final String MYChannel_ID="com.example.rohan.projectmajor.PROJECT";

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        gps=findViewById(R.id.imagemap);
        facerecog=findViewById(R.id.imagefacerecog);
        timetable=findViewById(R.id.imagetimetable);

        mAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };



        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permission2=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        int permission3=ContextCompat.checkSelfPermission(this,Manifest.permission.CAPTURE_VIDEO_OUTPUT);
        //int permission2=ContextCompat.checkSelfPermission(this,Manifest.permission.);
        if(permission!= PackageManager.PERMISSION_GRANTED || permission2!=PackageManager.PERMISSION_GRANTED || permission3!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.CAPTURE_VIDEO_OUTPUT},RC);
        }
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String userid= firebaseUser.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("UsersLocation").child(userid);


        MyLatLng jaypeehospital = new MyLatLng("28.514434","77.371749");
        MyLatLng market = new MyLatLng("28.570776","77.326095");
        MyLatLng office = new MyLatLng("28.537856", "77.342037");
        MyLatLng home = new MyLatLng("28.629915", "77.372483");
        MyLatLng gym = new MyLatLng("28.538572", "77.364504");

        latLngArrayList.add(jaypeehospital);
        latLngArrayList.add(market);
        latLngArrayList.add(office);
        latLngArrayList.add(home);
        latLngArrayList.add(gym);

        lm=(LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                Lat=String.valueOf(location.getLatitude());
                Lng=String.valueOf(location.getLongitude());
                lati= location.getLatitude();
                longi= location.getLongitude();
                //Toast.makeText(MainActivity.this, Lat+"  "+Lng, Toast.LENGTH_SHORT).show();
                HashMap<String,String> LatLong=  new HashMap<>();
                LatLong.put("Lat",Lat);
                LatLong.put("Lng",Lng);
                databaseReference.setValue(LatLong).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //Toast.makeText(MainActivity.this, "We have done it !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "OOOOOPS !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,MapsActivity.class);
                i.putExtra("Lat",lati);
                i.putExtra("Lng",longi);
                i.putExtra("latlngarraylist", latLngArrayList);
                startActivity(i);
            }
        });
        facerecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "Under progress", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, FaceActivity.class));
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,TimeTableActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ChatBotActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        imageProfile=headerView.findViewById(R.id.imageProfile);
        nameProfile= headerView.findViewById(R.id.nameProfile);
        emailProfile=headerView.findViewById(R.id.emailProfile);
        FirebaseUser user = mAuth.getCurrentUser();
        // assert user != null;
        if(user!=null)
        {
            Uri image=user.getPhotoUrl();
            if(image!=null)
            {
                //imageProfile.setImageURI(image);
                Glide.with(headerView).load(image).into(imageProfile);

            }
            nameProfile.setText(user.getDisplayName());
            emailProfile.setText(user.getEmail());


        }

        databaseReference2=FirebaseDatabase.getInstance().getReference("UsersTimeTable").child("gj8YkpyEEhN3y80eVOu5cOfxc7q2");

        /*for (int i=0;i<7;i++)
        {
            Routine routine= new Routine();

            routine.setTopic(topic[i]);
            routine.setTiming(time2[i]);

            routineList.add(routine);
        }
        databaseReference2.child("3").setValue(routineList);*/
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...Make sure you have internet connection !!!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Calendar c=Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_WEEK);
        if(day==2 || day==6)
        {
            databaseReference2.child("1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    routineList.clear();
                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                    {
                        Routine routine= dataSnapshot1.getValue(Routine.class);
                        routineList.add(routine);
                    }
                    RecyclerAdapter recyclerAdapter=new RecyclerAdapter(routineList,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(day==3|| day==5)
        {
            databaseReference2.child("2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    routineList.clear();
                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                    {
                        Routine routine= dataSnapshot1.getValue(Routine.class);
                        routineList.add(routine);
                    }
                    RecyclerAdapter recyclerAdapter=new RecyclerAdapter(routineList,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(day==4)
        {
            databaseReference2.child("0").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    routineList.clear();
                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                    {
                        Routine routine= dataSnapshot1.getValue(Routine.class);
                        routineList.add(routine);
                    }
                    RecyclerAdapter recyclerAdapter=new RecyclerAdapter(routineList,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            databaseReference2.child("3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    routineList.clear();
                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                    {
                        Routine routine= dataSnapshot1.getValue(Routine.class);
                        routineList.add(routine);
                    }
                    RecyclerAdapter recyclerAdapter=new RecyclerAdapter(routineList,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        //c.getTime();
        //c.getTime().getHours();
        //long l=c.getTimeInMillis();
        String s= c.getTime().toString();
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(day==6 || day==2)
        {
            if (hour>=15 && hour<=16)
            {
                showNotification(MainActivity.this,"Client Meeting", "Time to meet your clients");
            }
        }
        if (day==4)
        {
            if (hour>=15 && hour <=16)
            {
                showNotification(MainActivity.this, "Dentist Appointment", "Visit your dentist");
            }
        }
        if (hour==11)
        {
            showNotification(MainActivity.this, "Medicine", "Take medicine");
        }
        if(hour==14)
        {
            showNotification(MainActivity.this, "Medicine", "Take medicine");
        }
        if (hour==20)
        {
            showNotification(MainActivity.this, "Medicine", "Take medicine");
        }
        if(day==2 || day==3 || day==4 || day==5 || day==6)
        {
            if (hour>=9 && hour<=10)
            {
                showNotification(MainActivity.this,"Office", "Time to go for your work.");
            }
        }



        //Toast.makeText(this, String.valueOf(l), Toast.LENGTH_LONG).show();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    public void showNotification(Context context, String title, String body) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body);


        notificationManager.notify(notificationId, mBuilder.build());
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.map) {

            Intent i= new Intent(MainActivity.this,MapsActivity.class);
            i.putExtra("Lat",lati);
            i.putExtra("Lng",longi);
            startActivity(i);

        } else if (id == R.id.bot) {
            startActivity(new Intent(MainActivity.this, ChatBotActivity.class));
            
        } else if (id == R.id.logOut) {

            mAuth.signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
