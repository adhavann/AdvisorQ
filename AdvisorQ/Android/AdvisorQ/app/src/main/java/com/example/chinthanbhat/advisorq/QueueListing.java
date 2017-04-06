package com.example.chinthanbhat.advisorq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueueListing extends AppCompatActivity {

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    List<String> QueueList=new ArrayList<String>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        //String jsonString = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String jsonString = intent.getStringExtra("jsonObject");
        System.out.println("JString"+ jsonString);

        try {

            JSONArray obj = new JSONArray(jsonString);
            JSONObject mJsonObject = new JSONObject();
            String temp=null;
            for (int i = 0; i < obj.length(); i++) {
                mJsonObject = obj.getJSONObject(i);
                String ii = mJsonObject.getString("uta_id");
                String iii = mJsonObject.getString("status");
                System.out.print("asdasdasdasdasdas"+ii);
                temp=Integer.toString(i+1)+"            "+ii+"            "+iii;
                //temp.concat(iii);
                System.out.print("aaaaaaaaaaaaaaaaaaaaa" + temp);
                QueueList.add(temp);


                System.out.println("QQ"+ QueueList.get(i));
//                            mJsonObject.getString("1");
//                            mJsonObject.getString("name");
            }
            // System.out.println("My App"+ obj.toString());

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
            Toast.makeText(this, "Error", Toast.LENGTH_LONG);
        }
        System.out.println("intentTest");


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.layout, QueueList);


        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);


    }

}
