package com.example.listview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

public class Activity_ListView extends AppCompatActivity {

    private ConnectivityCheck connect;
    private DownloadTask myTask;
	private SharedPreferences myPreference;
	private SharedPreferences.OnSharedPreferenceChangeListener listener;
	ListView my_listview;
    String[] tmp;
    Integer[] imageid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


        imageid = new Integer[5];
        tmp = new String[5];
		myAdapter adapter = new myAdapter(Activity_ListView.this, tmp, imageid);


		// Change title to indicate sort by
		setTitle("Sort by:");



        myPreference = PreferenceManager.getDefaultSharedPreferences(this);

        //On Update prefrences
        listener = new SharedPreferences.OnSharedPreferenceChangeListener(){
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key){
                if(key.equals("listpref")){


                }
            }
        };
        //updates when prefrences change
        myPreference.registerOnSharedPreferenceChangeListener(listener);

        checkConnection();

		//listview that you will operate on
		my_listview = (ListView)findViewById(R.id.lv);

		//toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();

		setupSimpleSpinner();

		//set the listview onclick listener
		setupListViewOnClickListener();

		//TODO call a thread to get the JSON list of bikes
		//TODO when it returns it should process this data with bindData
	}

    public void checkConnection(){

        connect = new ConnectivityCheck();

        if(connect.isNetworkReachable(this)){
            runDownloadTask();
        }
        else{
            Toast.makeText(this,"Could not connect to the Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void printValue(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

        
    }


    public void processJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
        }
        catch (Exception e){

        }
    }

    public void runDownloadTask(){
        if(myTask != null){
            myTask.detach();
            myTask= null;
        }

        myTask = new DownloadTask(this);

        myTask.execute(myPreference.getString("listpref","http://www.tetonsoftware.com/pets/pets.json"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switches between the options
        switch(item.getItemId()){
            case(R.id.action_settings):
                //starts new preference activity
                Intent myIntent = new Intent(this, activityPreference.class);
                startActivity(myIntent);
                break;
            default:
                break;

        }
        return true;
    }

	private void setupListViewOnClickListener() {
		//TODO you want to call my_listviews setOnItemClickListener with a new instance of android.widget.AdapterView.OnItemClickListener() {
	}

	/**
	 * Takes the string of bikes, parses it using JSONHelper
	 * Sets the adapter with this list using a custom row layout and an instance of the CustomAdapter
	 * binds the adapter to the Listview using setAdapter
	 *
	 * @param JSONString  complete string of all bikes
	 */
	private void bindData(String JSONString) {

	}

	Spinner spinner;
	/**
	 * create a data adapter to fill above spinner with choices(Company,Location and Price),
	 * bind it to the spinner
	 * Also create a OnItemSelectedListener for this spinner so
	 * when a user clicks the spinner the list of bikes is resorted according to selection
	 * dontforget to bind the listener to the spinner with setOnItemSelectedListener!
	 */
	private void setupSimpleSpinner() {

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
