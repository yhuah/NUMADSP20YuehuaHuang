package com.example.numadsp20yuehuahuang;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/*

We will be adding to the app from assignment 1.
This way, publishing your internal test version to the Google Play Store should
be faster than the first time because it is not a new app.

Add a "Link collector" button that brings up a new screen when tapped
This should have a list of links that will initially be empty.
The user can tap a floating action button to store a new link,
in which case the user enters a name and a URL for the link, which is added to the list.
 Use messages in a snackbar to inform the user that the link was successfully created.
When the user taps a link in the list, launch the URL in a web browser.
Link data does not have to persist when the app is stopped (since we haven't learned how to do this yet).

Test it with the emulator and with a physical device.

Submit a link to your repo (which we should already
be invited to collaborate from assignment 1) and a link to your app in the Play Store.

As a reminder, this is an individual assignment to be completed by yourself.

 */


public class LinkCollectorActivity extends AppCompatActivity {


    public static class DataPair {
        private String name;
        private String url;

        public DataPair(String name, String url) {
            if (name == null || url == null || name.equals("") || url.equals("")) {
                throw new IllegalArgumentException();

            }
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return this.name;
        }

        public String getUrl() {
            return this.url;
        }
    }

    private ArrayList<DataPair> links= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        final MySqliteHandler db = new MySqliteHandler(this);


        //FloatingActionButton fab= findViewById(R.id.add_fab);
        FloatingActionButton add_fab = findViewById(R.id.add_fab);
        FloatingActionButton del_fab = findViewById(R.id.fab_delete_link);

        RecyclerView rv = findViewById(R.id.linkRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        final MyAdapter myAdapter = new MyAdapter(db.getAllLinks());
        rv.setHasFixedSize(true);
        rv.setAdapter(myAdapter);



        add_fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String name = ((EditText) findViewById(R.id.link_name_input)).getText().toString();
                String url = ((EditText) findViewById(R.id.link_url_input)).getText().toString();
                //links.add(new DataPair(name, url));
               // db.addLink(new DataPair(name, url));
                //links.add(new DataPair(name, url));

                try {
                    db.addLink(new DataPair(name, url));
                } catch (IllegalArgumentException e) {
                    Snackbar.make(v, "No Null Value Allowed.", Snackbar.LENGTH_LONG)
                            .setAction("Error", null).show();
                    return;
                } catch ( SQLException e) {
                    Snackbar.make(v, "No Same Combination of Name & URL Allowed.", Snackbar.LENGTH_LONG)
                            .setAction("Error", null).show();
                    return;
                }

                myAdapter.updateLinkList(db.getAllLinks());
                myAdapter.notifyDataSetChanged();
                Snackbar.make(v, "Your link has been successfully added!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }


        });

        del_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.clearAllLinks();
                myAdapter.updateLinkList(db.getAllLinks());
                myAdapter.notifyDataSetChanged();
                Snackbar.make(v, "All links have been deleted!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
}



