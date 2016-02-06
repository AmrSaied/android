package com.example.amrsaid.isksecurity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amrsaid.isksecurity.Fragments.AddUser;
import com.example.amrsaid.isksecurity.Fragments.Battery;
import com.example.amrsaid.isksecurity.Fragments.Home;
import com.example.amrsaid.isksecurity.Fragments.Log;
import com.example.amrsaid.isksecurity.Fragments.PinMassage;
import com.example.amrsaid.isksecurity.ble.BluetoothHandler;
import com.example.amrsaid.isksecurity.helper.BleHelper;
import com.example.amrsaid.isksecurity.helper.SessionManager;
import com.example.amrsaid.isksecurity.helper.UserInformationManger;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BluetoothHandler bluetoothHandler;
    private BluetoothDevice connectedDevice;
    private boolean isConnected;
    private AlertDialog bleScanDialog;
    Fragment fragment = null;

    Class fragmentClass = null;
    private SessionManager session;
    private UserInformationManger informationManger;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());
        informationManger = new UserInformationManger(getApplicationContext());
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



       headerData();


        getSupportActionBar().setTitle("ISK ( Home )");


        fragmentClass = Home.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.home) {
            fragmentClass = Home.class;
            getSupportActionBar().setTitle("ISK ( Home )");
            // Handle the camera action
        } else if (id == R.id.user_log) {
            fragmentClass = Log.class;
            getSupportActionBar().setTitle("ISK ( User log )");
        } else if (id == R.id.add_user) {
            fragmentClass = AddUser.class;
            getSupportActionBar().setTitle("ISK ( Add new user )");
        } else if (id == R.id.massage) {
            fragmentClass = PinMassage.class;
            getSupportActionBar().setTitle("ISK ( Add pin massage )");

        } else if (id == R.id.battery) {
            fragmentClass = Battery.class;
            getSupportActionBar().setTitle("ISK ( Battery status )");
        } else if (id == R.id.log_out) {
            session.setLogin(false);
            Intent intent = new Intent( MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.add_lock) {
            BleHelper bleHelper=new BleHelper(getApplicationContext());
            bleHelper.scan();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void headerData(){
        CircleImageView imageViewFP= (CircleImageView) findViewById(R.id.imageViewFP);
        Picasso.with(this)
                .load(informationManger.getProfilePicture())
                .resize(200,200)
                .into(imageViewFP);

        TextView pesonNameTV= (TextView) findViewById(R.id.pesonName);
        pesonNameTV.setText(informationManger.getFullName());
        TextView pesonmailTV= (TextView) findViewById(R.id.pesonmail);
        pesonmailTV.setText(informationManger.getEmail());

    }






}
