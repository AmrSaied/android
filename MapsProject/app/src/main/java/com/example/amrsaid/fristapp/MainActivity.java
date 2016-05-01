package com.example.amrsaid.fristapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends ActionBarActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private final String LOG_TAG = "My";
    private TextView txtOutput;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    GoogleMap m_map;
    boolean mapReady = false;

    MarkerOptions renton;

    MarkerOptions kirkland;

    MarkerOptions everett;

    MarkerOptions lynnwood;

    MarkerOptions montlake;

    MarkerOptions kent;

    MarkerOptions showare;


    static final CameraPosition NEWYORK = CameraPosition.builder()
            .target(new LatLng(47.6204, -122.2491))
            .zoom(21)
            .bearing(0)
            .tilt(45)
            .build();

    static final CameraPosition SEATTLE = CameraPosition.builder()
            .target(new LatLng(47.6204, -122.3491))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();

    static final CameraPosition DUBLIN = CameraPosition.builder()
            .target(new LatLng(53.3478, -6.2597))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();


    static final CameraPosition TOKYO = CameraPosition.builder()
            .target(new LatLng(35.6895, 139.6917))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();


    LatLng rentonLng = new LatLng(47.489805, -122.120502);

    LatLng kirklandLng = new LatLng(47.7301986, -122.1768858);

    LatLng everettLng = new LatLng(47.978748, -122.202001);

    LatLng lynnwoodLng = new LatLng(47.819533, -122.32288);

    LatLng montlakeLng = new LatLng(47.7973733, -122.3281771);

    LatLng kentLng = new LatLng(47.385938, -122.258212);

    LatLng showareLng = new LatLng(47.38702, -122.23986);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        txtOutput = (TextView) findViewById(R.id.txtOutput);


        renton = new MarkerOptions()
                .position(new LatLng(47.489805, -122.120502))
                .title("Renton")
        ;

        kirkland = new MarkerOptions()
                .position(new LatLng(47.7301986, -122.1768858))
                .title("Kirkland")
        ;

        everett = new MarkerOptions()
                .position(new LatLng(47.978748, -122.202001))
                .title("Everett")
        ;

        lynnwood = new MarkerOptions()
                .position(new LatLng(47.819533, -122.32288))
                .title("Lynnwood")
        ;

        montlake = new MarkerOptions()
                .position(new LatLng(47.7973733, -122.3281771))
                .title("Montlake Terrace")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cast_ic_notification_0));

        kent = new MarkerOptions()
                .position(new LatLng(47.385938, -122.258212))
                .title("Kent Valley")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cast_ic_notification_0));

        showare = new MarkerOptions()
                .position(new LatLng(47.38702, -122.23986))
                .title("Showare Center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cast_ic_notification_0));


        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        Button btnSatellite = (Button) findViewById(R.id.btnSatellite);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        Button btnHybrid = (Button) findViewById(R.id.btnHybrid);
        btnHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            }
        });


        Button btnSeattle = (Button) findViewById(R.id.btnSeattle);
        btnSeattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    flyTo(SEATTLE);
            }
        });

        Button btnDublin = (Button) findViewById(R.id.btnDublin);
        btnDublin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    flyTo(DUBLIN);
            }
        });

        Button btnTokyo = (Button) findViewById(R.id.btnTokyo);
        btnTokyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    flyTo(TOKYO);
            }
        });


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mapReady = true;
        m_map = map;
//        LatLng newYork = new LatLng(40.7484, -73.9857);
//        CameraPosition target = CameraPosition.builder().target(newYork).zoom(14).build();
//        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));

        m_map.addPolyline(new PolylineOptions().geodesic(true).
                add(rentonLng)
                .add(kirklandLng)
                .add(everettLng)
                .add(lynnwoodLng)
                .add(montlakeLng)
                .add(kentLng)
                .add(showareLng)

        );


        m_map.addCircle(new CircleOptions().center(kirklandLng).fillColor(Color.GREEN).strokeColor(Color.BLACK).radius(5000));
        m_map.addMarker(renton);
        m_map.addMarker(kirkland);
        m_map.addMarker(everett);
        m_map.addMarker(lynnwood);
        m_map.addMarker(montlake);
        m_map.addMarker(kent);
        m_map.addMarker(showare);

        flyTo(NEWYORK);
    }


    private void flyTo(CameraPosition target) {
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);


    }


    ///all method for my location
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Update location every second
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, location.toString());
        //txtOutput.setText(location.toString());

        txtOutput.setText(Double.toString(location.getLatitude()));
    }


}