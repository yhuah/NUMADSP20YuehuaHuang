package com.example.numadsp20yuehuahuang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;



public class MainActivity extends AppCompatActivity {

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



        Button about = (Button) findViewById(R.id.button3);
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

    }

    public void startLinkCollectorActivity(){

        Intent linkIntent= new Intent(this, LinkCollectorActivity.class);
        startActivity(linkIntent);
    }

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
