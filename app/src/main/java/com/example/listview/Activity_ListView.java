package com.example.listview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activity_ListView extends AppCompatActivity {

    private ConnectivityCheck connect;
    private DownloadTask myTask;
	private SharedPreferences myPreference;
	private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private List<BikeData> imList;
	ListView my_listview;
    String[] tmp;
    Integer[] imageid;
   private int position;
    private final String JSON_EXTENSION = "bikes.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        imageid = new Integer[5];


		// Change title to indicate sort by
		setTitle("Sort by:");

        myPreference = PreferenceManager.getDefaultSharedPreferences(this);

        //On Update prefrences
        listener = new SharedPreferences.OnSharedPreferenceChangeListener(){
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key){
                if(key.equals("options")){
                    Toast.makeText(Activity_ListView.this,"Succes", Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }
        };
        //updates when prefrences change
        myPreference.registerOnSharedPreferenceChangeListener(listener);


        /////////////////////////myPreference.getString(getString(R.string.tetonURL),"http://www.tetonsoftware.com/bikes/")


		//listview that you will operate on
		//my_listview = (ListView)findViewById(R.id.lv);

		//toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();



		//set the listview onclick listener
        my_listview = (ListView)findViewById(R.id.lv);
        setupListViewOnClickListener();

        setupSimpleSpinner();

        checkConnection();


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

    public String getLink(){

        return  myPreference.getString("options","http://www.pcs.cnu.edu/~kperkins/bikes/");
    }
    public void runDownloadTask(){
        if(myTask != null){
            myTask.detach();
            myTask= null;
        }

        myTask = new DownloadTask(this);

        myTask.execute(getLink() + JSON_EXTENSION);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switches between the options
        switch(item.getItemId()){
            case(R.id.action_settings):
                //starts new preference activity
                Intent myIntent = new Intent(this, activityPreference.class);
                startActivity(myIntent);
                return true;

            case(R.id.refresh):
                refresh();



            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListViewOnClickListener() {
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ListView.this);
                builder.setMessage(imList.get(position) + "");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

	/**
	 * Takes the string of bikes, parses it using JSONHelper
	 * Sets the adapter with this list using a custom row layout and an instance of the CustomAdapter
	 * binds the adapter to the Listview using setAdapter
	 *
	 * @param JSONString  complete string of all bikes
	 */
	public void bindData(String JSONString) {
        setList(JSONHelper.parseAll(JSONString));
        ListView listView = (ListView)findViewById(R.id.lv);
        ArrayAdapter<BikeData> adapter = new listAdapter(this,imList,getLink());
        listView.setAdapter(adapter);
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

        tmp = new String[] {"Company", "Price", "Location", "Model"};
        spinner = (Spinner)findViewById(R.id.spinner);

        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, tmp));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Activity_ListView.this.setPosition(position);
                if(imList != null) {
                    runDownloadTask();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



	}

    public void setPosition(int position){
        this.position = position;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
    public void setList(List<BikeData> list){
        imList = new ArrayList<BikeData>();
        imList.addAll(list);

        if(position == 0){
            sortCompany(imList);

        }
        else if(position == 1){
            sortPrice(imList);
        }
        else if(position == 2){
            sortLocation(imList);
        }
        else{
            sortModel(imList);
        }

        //for(int i = 0; i < imList.size(); i++){
         // Toast.makeText( this, imList.get(i) + "",Toast.LENGTH_SHORT).show();
       // }
    }

    public void refresh(){
        spinner.setSelection(0);
        imList.removeAll(imList);
        runDownloadTask();



    }

    public void sortCompany(List<BikeData> List){

        Collections.sort(List, new ComparatorCompany());

    }
    public void sortPrice(List<BikeData> List){
        Collections.sort(List, new ComparatorPrice());
    }
    public void sortLocation(List<BikeData> List){
        Collections.sort(List, new ComparatorLocation());
    }

    public void sortModel(List<BikeData> List){
        Collections.sort(List, new ComparatorModel());
    }
}
