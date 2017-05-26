package com.example.rajat.mymap;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static android.support.v7.appcompat.R.id.time;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    LatLng l, m;
    public double b, c = 1.1;
    double d, g;
    double dd = 0.0;
    private GoogleMap mMap;
    Button bb, bb1, bb2;
    private MediaPlayer media;
    Button bh;
    AlarmManager alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bb = (Button) findViewById(R.id.button3);
        bh = (Button) findViewById(R.id.button4);

//GPS POSITION
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = onSearch(v);
                b = l.latitude;
                c = l.longitude;


            }
        });
        bh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.pause();
            }
        });
        final Runnable stopPlayerTask = new Runnable() {
            @Override
            public void run() {
                media.stop();
            }
        };


        LocationManager m = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        if (m.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            m.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng l = new LatLng(lat, lon);
                    d = l.latitude;
                    g = l.longitude;

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> ad = geocoder.getFromLocation(lat, lon, 1);
                        String c1 = ad.get(0).getCountryName();
                        if (l == null) {
                            dd = distance(d, g, 1.1, 1.1);
                        } else if (l != null) {
                            dd = distance(d, g, b, c);
                        }
                        String d1 = ad.get(0).getLocality();
                        mMap.addMarker(new MarkerOptions().position(l).title(d1));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
                        pointToPosition(l);

                        Intent i = getIntent();
                        Bundle e = i.getExtras();
                        String f = e.getString("key");
                        double bn = Double.parseDouble(f);
                        if (dd <= bn)

                        {
                            media = MediaPlayer.create(MapsActivity.this, R.raw.dev);
                            media.start();
                            Handler handler = new Handler();
                            handler.postDelayed(stopPlayerTask, 5000);
                            CountDownTimer cntr_aCounter = new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {

                                    media.start();
                                }

                                public void onFinish() {
                                    //code fire after finish
                                    media.stop();
                                }
                            };
                            cntr_aCounter.start();

                          /*  int time=10*1000;
                            Intent intent=new Intent(MapsActivity.this,Alarm.class);
                            alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            PendingIntent pIntent = PendingIntent.getBroadcast(MapsActivity.this, 0, intent, 0);
                            alarm.setRepeating(AlarmManager.RTC_WAKEUP, time, time, pIntent);
*/

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


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
            });


        }
        if (m.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            m.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng l = new LatLng(lat, lon);
                    d = l.latitude;
                    g = l.longitude;
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> ad = geocoder.getFromLocation(lat, lon, 1);
                        String c1 = ad.get(0).getCountryName();
                        String d1 = ad.get(0).getLocality();
                        mMap.addMarker(new MarkerOptions().position(l).title(d1));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
                        pointToPosition(l);
                        if (l == null) {
                            dd = distance(d, g, 1.1, 1.1);
                        } else if (l != null) {
                            dd = distance(d, g, b, c);
                        }
                        Intent i = getIntent();
                        Bundle e = i.getExtras();
                        String f = e.getString("key");
                        double bn = Double.parseDouble(f);
                        if (dd <= bn) {
                            media = MediaPlayer.create(MapsActivity.this, R.raw.dev);
                            media.start();
                            Handler handler = new Handler();
                            handler.postDelayed(stopPlayerTask, 5000);
                            CountDownTimer cntr_aCounter = new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {

                                    media.start();
                                }

                                public void onFinish() {
                                    //code fire after finish
                                    media.stop();
                                }
                            };
                            cntr_aCounter.start();

                          /*  Intent intent=new Intent(MapsActivity.this,Alarm.class);
                            PendingIntent pp= PendingIntent.getBroadcast(MapsActivity.this,256487,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(2*1000),pp);
                        */
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


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
            });


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

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private void pointToPosition(LatLng position) {
        //Build camera position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(17).build();
        //Zoom in and animate the camera.
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public LatLng onSearch(View v) {
        LatLng ll = null;
        EditText e = (EditText) findViewById(R.id.textView2);

        List<Address> addresses = null;
        String loc = e.getText().toString();
        if (loc != null || loc != "") {
            Geocoder g = new Geocoder(this);
            try {
                addresses = g.getFromLocationName(loc, 1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Address address = addresses.get(0);
            ll = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ll).title(loc));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
            pointToPosition(ll);

        }
        return ll;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}