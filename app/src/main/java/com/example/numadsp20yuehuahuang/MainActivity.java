package com.example.numadsp20yuehuahuang;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


/*
Using the same app that you've been working on individually, add the following features and release
 an internal test version.  Submit a link to the app in the Play Store and a link to your repo.

1.  A "Find Primes" button that starts a search for prime numbers.
Do it the slow, old-fashioned way: start with 2 and increment by 1.
(This isn't a test of your algorithmic sophistication.  We're keeping the CPU busy.)
Make sure that the user can see the search in action.
 Each time a prime is found indicate that it is prime.  Implement a way to stop the search.
  In case you haven't gotten the hint from this week's topics, keep your UI snappy while the search is running.

2.  A "Watch Time" button that wakes the device up every minute to display the time.
 Implement a way to stop watching the time.

3.  Display "POWER!!!" or "battery", updating when the power is connected or disconnected from the device.


 */


public class MainActivity extends AppCompatActivity {

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        // show gps

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final TextView gps_view = findViewById(R.id.location);
                if (isChecked) {
                    // The toggle is enabled
                    mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    mLocationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            String gps_data = "Longitude: " + location.getLongitude()
                                    + "\nLatitude: " + location.getLatitude();

                                gps_view.setVisibility(View.VISIBLE);
                                gps_view.setText(gps_data);


                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(i);
                        }
                    };
                    getGPS();

                } else {
                    // The toggle is disabled
                    gps_view.setVisibility(View.INVISIBLE);

                }
            }
        });


        Button about = findViewById(R.id.button3);
        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                TextView info = findViewById(R.id.textView4);
                TextView hello=findViewById(R.id.textView5);
                if (info.getVisibility()==View.VISIBLE) {
                    info.setVisibility(View.INVISIBLE);

                }else{
                    info.setVisibility(View.VISIBLE);
                    info.setText("Yuehua Huang. xxx@husky.neu.edu");
                }


                if (hello.getVisibility()==View.VISIBLE) {
                    hello.setVisibility(View.INVISIBLE);

                }else{
                    hello.setVisibility(View.VISIBLE);
                    hello.setText("Hello World!");
                }


            }
        });



        Button linkCollectorButton = findViewById(R.id.link_collector_button);
        linkCollectorButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                startLinkCollectorActivity();

            }
        });

        //Display "POWER!!!" or "battery", updating when the power is connected or disconnected
        // from the device.
        //reference https://github.com/greenhub-project/batteryhub/blob/master/app/src/main/java/com/hmatalonga/greenhub/ui/MainActivity.java
        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                if(status==BatteryManager.BATTERY_STATUS_CHARGING){
                    Toast.makeText(context, "POWER!!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context, "battery", Toast.LENGTH_SHORT).show();
                }

            }

        };
        this.registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //this.unregisterReceiver(batteryReceiver);


        //2.  A "Watch Time" button that wakes the device up every minute to display the time.
        //Implement a way to stop watching the time.
        Button start_watch_time = findViewById(R.id.start_watch_time__button);
        Button stop_watch_time = findViewById(R.id.stop_watch_time_button);
        final AlertReceiver alarm = new AlertReceiver();
        start_watch_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                alarm.setAlarm(MainActivity.this);

            }

        });

        stop_watch_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                alarm.cancelAlarm(MainActivity.this);

            }

        });


        //3 Find Prime
        // reference https://www.journaldev.com/9708/android-asynctask-example-tutorial
        Button start_find_prime= findViewById((R.id.start_find_prime__button));
        Button stop_find_prime = findViewById(R.id.stop_find_prime__button);
        final FindPrimeProcess primeProcess = new FindPrimeProcess();


        start_find_prime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                primeProcess.start();

            }

        });

        stop_find_prime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                primeProcess.stop();

            }

        });



    }

    public void startLinkCollectorActivity(){

        Intent linkIntent= new Intent(this, LinkCollectorActivity.class);
        startActivity(linkIntent);
    }

    /**
     * GPS
     */

    private void getGPS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 10);
                return;
            }
        }
        mLocationManager.requestLocationUpdates("gps", 5000, 0, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getGPS();
        }

    }




    /*
    1.  A "Find Primes" button that starts a search for prime numbers.
Do it the slow, old-fashioned way: start with 2 and increment by 1.
(This isn't a test of your algorithmic sophistication.  We're keeping the CPU busy.)
Make sure that the user can see the search in action.
 Each time a prime is found indicate that it is prime.  Implement a way to stop the search.
  In case you haven't gotten the hint from this week's topics, keep your UI snappy while the sear
     */

    private class PrimeTask extends AsyncTask<Integer, Integer, String >{

        TextView primeText;



        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            primeText = findViewById(R.id.prime_number);

        }

        @Override
        protected String doInBackground(Integer...integers){
            Integer start =1;
            for (; start<integers[0];start+=1){
                if (this.isCancelled()){
                    break;
                }
                publishProgress(start);
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Search Completed";


        }


        @Override
        protected void onProgressUpdate(Integer...values){
            super.onProgressUpdate(values);
            String res = values[0] + " " + isPrime(values[0]);
            primeText.setText(res);

        }

        @Override

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onCancelled(String s){
            super.onCancelled(s);
            Toast.makeText(MainActivity.this, s,Toast.LENGTH_SHORT).show();
        }

        private boolean isPrime(Integer number) {
            if (number ==1){
                return false;
            }
            if (number == 2 || number == 3) {
                return true;
            }
            if (number % 2 == 0) {
                return false;
            }
            int sqrt = (int) Math.sqrt(number) + 1;
            for (int i = 3; i < sqrt; i += 2) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }



    }


    private class FindPrimeProcess {
        private PrimeTask task;

        private void start() {
            if (task != null && !task.isCancelled()) {
                task.cancel(true);
            }
            task = new PrimeTask();
            task.execute(Integer.MAX_VALUE);
        }
        private void stop() {
            task.cancel(true);
        }
    }


 // Using the same app that you have been working on individually, add a button that will
    // display geographic location (latitude and longitude).
    // Submit the Play Store link and GitHub link.  Do not start a new app.  Do not generate a new key.
    //
    //Display the latitude and longitude as two numbers
    // (latitude is one number and longitude is one number).
    // Do not call Google Maps to display the location.  Display the latitude and longitude.


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
