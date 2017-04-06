package com.example.chinthanbhat.advisorq;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button submitButton, button3;
    EditText name, UTAid, mobNo, Reason, UTAidAR;
    //Spinner advisor;
    Spinner spinner;
    private Handler mHandler = new Handler();
    ArrayList<String> Details = new ArrayList<>();

    static InputStream is = null;

    private BluetoothAdapter mBluetoothAdapter;
    private String url,Burl;

    private static final int REQUEST_ENABLE_BT = 1;
    private String timestamp;
    private String devname;





    /*Spinner spinner = (Spinner) findViewById(R.id.spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
    spinner.setAdapter(adapter);
    */


    private static String TAG = "MainActivity";
    public static final String url_insert_location = "***PHP File Location****";
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UTAidAR = (EditText) findViewById(R.id.UTAidar);
        name = (EditText) findViewById(R.id.Name);
        UTAid = (EditText) findViewById(R.id.UTAid);
        mobNo = (EditText) findViewById(R.id.MobNo);
        Reason = (EditText) findViewById(R.id.Reason);
        submitButton = (Button) findViewById(R.id.button);
        button3 = (Button)findViewById(R.id.button2);

        Toast.makeText(this, " Switch On Location Services ", Toast.LENGTH_LONG).show();

        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().set
        getSupportActionBar().setLogo(R.drawable.usersgroup);


        String[] Permisions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH
        };
//        ActivityCompat.requestPermissions(MainActivity.this, Permisions, 1);
//
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        boolean bad=mBluetoothAdapter.isEnabled();
        Log.d(TAG, "Bluetooth adapter ena " + Boolean.toString((bad)));
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivity(enableBtIntent);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }
//        Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
//        startActivity(i);
        if (mBluetoothAdapter != null) {

        }


        final Spinner spinner = (Spinner) findViewById(R.id.AdvList);

        // Spinner click listener
        // spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Sajib Dutta");
        categories.add("Khalili");
        categories.add("Yu Lei");
        categories.add("Yonghe Liu");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        submitButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkInternetConnection()) {
                            String token = FirebaseInstanceId.getInstance().getToken();
                            System.out.println("Token:" + token);
                            new SendToServer3().execute();

//                            String [] Permisions = {
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.BLUETOOTH
//                            };
//                            ActivityCompat.requestPermissions(MainActivity.this, Permisions, 1);
//                            //stopScan();
                            //startScan();
                            int item2;
                            //String Adv = spinner.getItemAtPosition()
                            String item = String.valueOf(spinner.getSelectedItem());
                            String pass = UTAid.getText().toString();
                            if(TextUtils.isEmpty(pass) || pass.length() < 10)
                            {
                                UTAid.setError("You must have 10 numbers in your UTA ID");
                                return;
                            }

                            String pass2 = name.getText().toString();
                            if(TextUtils.isEmpty(pass2))
                            {
                                UTAid.setError("You must enter your name");
                                return;
                            }


//                                 params.add(new BasicNameValuePair("operation", "register"));
//                                 params.add(new BasicNameValuePair("name", name.getText().toString()));
//                                 params.add(new BasicNameValuePair("uta_id", UTAid.getText().toString()));
//                                 params.add(new BasicNameValuePair("advisor_id", item));
//                                 params.add(new BasicNameValuePair("mobile_number", mobNo.getText().toString()));
//                                 params.add(new BasicNameValuePair("reason", Reason.getText().toString()));
//                             String name2 = name.getText().toString();
//                             String UTAid2 = UTAid.getText().toString();
//                             String mobNo2 = mobNo.getText().toString();
//                             String Reason2 = Reason.getText().toString();

                            //loginObject.makeHttpRequest(url_insert_location,"POST",params);
                            System.out.println(name.getText().toString());
                            new SendToServer3().execute(name.getText().toString(), UTAid.getText().toString(), item, mobNo.getText().toString(), Reason.getText().toString(), token);
                            //(name.getText().toString(),UTAid.getText().toString(),item,mobNo.getText().toString(),Reason.getText().toString())
                        }
                    }

                });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {

//                    //Submit(v);
//
                    String[] Permisions = {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.BLUETOOTH
                    };
                    ActivityCompat.requestPermissions(MainActivity.this, Permisions, 1);
                    //stopScan();
//
//
//                    //startScan();
//
////                    String idToSend = UTAidAR.getText().toString();
////                    ArrayList<NameValuePair> params = new ArrayList<>();
////                    params.add(new BasicNameValuePair("operation","AddMeToQueue"));
////                    params.add(new BasicNameValuePair("uta_id2",idToSend));
////
////                    refObject.makeHttpRequest(url_insert_location,"GET",params);
                    //goToAlreadyRegPage();

                }
            }
        });


    }

    private boolean checkInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
//            Toast.makeText(this, " Connected ", Toast.LENGTH_SHORT).show();

            Log.d("checkInternetConenction", " Connected");
            return true;
        } else {
             Toast.makeText(this, " No Internet Connection ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }








        /*public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
            ...

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                advisor = (Spinner)spinner.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        }
        Spinner spinner = (Spinner) findViewById(R.id.textView5);
        spinner.setOnItemSelectedListener(this);*/


    // Spinner item selection Listener
    //addListenerOnSpinnerItemSelection();


    public void Submit (View view) {
        String [] Permisions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH
        };
        ActivityCompat.requestPermissions(MainActivity.this, Permisions, 1);
        return;
        //startScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                    //
                    //
                    try {
                        startScan();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi,
                                     final byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Test");
                            Log.d(TAG, "Test");
                            devname = device.getName();
                            String devaddress = device.getAddress();
                            int devrssi = rssi;
                            UriBeacon record = UriBeacon.parseFromBytes(scanRecord);
                            System.out.println("record"+record);
                            if (record != null)
                                Burl = record.getUriString();

                            System.out.println("urlTest"+Burl);
//                            goToAlreadyRegPage();
//                            System.exit(0);

                            if (Burl!=null && Burl.equals("***published url***"))
                            {
                                //return url;
                                System.out.println("Testinside");
                                goToAlreadyRegPage();
                                System.exit(0);
                            }
                            else{
//                                Toast.makeText(getApplicationContext(),"Go near Office",Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    });
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        getMenuInflater().inflate(R.menu.action, item);
//        return super.onCreateOptionsMenu(item);
        switch (item.getItemId()) {
//            case R.id.action_settings:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                new SendToServer2().execute();
                //goToQueuingListPage();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    class SendToServer3 extends AsyncTask<String, String, String> {
        //ProgressDialog dialog;
        //Integer success_value;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //dialog = ProgressDialog.show(MainActivity.this, "", "Sending");
        }

        @Override
        protected String doInBackground(String... auth) {


            try {
//
                Log.i("string", auth[0]);
                Log.i("string", auth[1]);
                Log.i("string", auth[2]);
                Log.i("string", auth[3]);
                Log.i("string", auth[4]);
                Log.i("token", auth[5]);
                String name2 = auth[0];
                String utaid = auth[1];
                String reason2 = auth[4];
                String reason, reason3;
                String advid = auth[2];
                String mobno = auth[3];
                String token = auth[5];
                int item2 =1;
                if(advid.compareTo("Sajib Dutta")==1)
                    item2 = 1;
                else if(advid.compareTo("Khalili")==1)
                    item2 = 2;
                else if(advid.compareTo("Yu Lei")==1)
                    item2 = 3;
                else if(advid.compareTo("Yonghe Liu")== 1)
                    item2 = 4;
                reason = reason2.replaceAll("'", "\\\\'");
//                reason = reason3.replaceAll("\"","\\\\\"" );
                System.out.println("TestR"+ reason2);
                ArrayList<NameValuePair> params = new ArrayList<>();
//            String item = String.valueOf(spinner.getSelectedItem());
            //String Adv = spinner.getItemAtPosition()
                params.add(new BasicNameValuePair("operation", "Register"));
                params.add(new BasicNameValuePair("name", name2));
                params.add(new BasicNameValuePair("uta_id", utaid));
                params.add(new BasicNameValuePair("advisor_id", "1"));
                params.add(new BasicNameValuePair("mobile_number", mobno));
                params.add(new BasicNameValuePair("reason", reason));
                params.add(new BasicNameValuePair("token", token));
                System.out.println(params);



//
//            loginObject.makeHttpRequest(url_insert_location,"GET",params);

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url_insert_location);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                //System.out.println("Response5"+httpResponse.toString());
                //HttpEntity httpEntity = httpResponse.getEntity();
//                BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                String line = br.readLine();
//                System.out.println("Response3" + line);
                //is = httpEntity.getContent();
                String json;
                if (httpResponse.getStatusLine().getStatusCode() == 200)
                {
                    HttpEntity entity = httpResponse.getEntity();
                    json = EntityUtils.toString(entity);
                    System.out.println("Response6" + json);
                    final String toastIt = json.toString();
                    System.out.println("toast" + toastIt);
                    //final String result = toastIt.replaceFirst(Pattern.quote("Register"), "");

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), toastIt, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                return "";
            } catch (Exception e) {
                Log.i("error", e.toString());
                return "call";
            }
//            return "call";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            return;



            //dialog.dismiss();
            //Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();


        }
    }

    class SendToServer2 extends AsyncTask<String, String, String> {
        //ProgressDialog dialog;
        //Integer success_value;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //dialog = ProgressDialog.show(MainActivity.this, "", "Sending");
        }

        @Override
        protected String doInBackground(String... auth) {


            try {
//
                ArrayList<NameValuePair> params = new ArrayList<>();
//            String item = String.valueOf(spinner.getSelectedItem());
                //String Adv = spinner.getItemAtPosition()
                params.add(new BasicNameValuePair("operation", "RealTimeQueue"));

//
//            loginObject.makeHttpRequest(url_insert_location,"GET",params);

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url_insert_location);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                //System.out.println("Response5"+httpResponse.toString());
                //HttpEntity httpEntity = httpResponse.getEntity();
//                BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                String line = br.readLine();
//                System.out.println("Response3" + line);
                //is = httpEntity.getContent();
                String json;
                if (httpResponse.getStatusLine().getStatusCode() == 200)
                {
                    HttpEntity entity = httpResponse.getEntity();
                    json = EntityUtils.toString(entity);
                    System.out.println("Response6" + json);
                    final String toastIt = json.toString();
                    System.out.println("toast" + toastIt);
                    //final String result = toastIt.replaceFirst(Pattern.quote("RealTimeQueue"), "");

                    //JSONArray obj = new JSONArray(result);
//                    String json2 = "[{\"uta_id\":\"12312312\",\"status\":\"Registered\"},{\"uta_id\":\"1\",\"status\":\"Queued\"}]";
//
//                    try {
//
//                        JSONArray obj = new JSONArray(json2);
//                        JSONObject mJsonObject = new JSONObject();
//                        for (int i = 0; i < json2.length(); i++) {
//                            mJsonObject = obj.getJSONObject(i);
//                            String ii = mJsonObject.getString("uta_id");
//                            String iii = mJsonObject.getString("status");
//                            System.out.println("JSONTEST"+ ii+iii);
////                            mJsonObject.getString("1");
////                            mJsonObject.getString("name");
//                        }
//
//                       // System.out.println("My App"+ obj.toString());
//
//                    } catch (Throwable t) {
//                        Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
//                    }


                    runOnUiThread(new Runnable() {
                        public void run() {
                            goToQueuingListPage(toastIt);
                        }
                    });
                }
                return "";
            } catch (Exception e) {
                Log.i("error", e.toString());
                return "call";
            }
//            return "call";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            return;



            //dialog.dismiss();
            //Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();


        }
    }






        private void startScan() throws InterruptedException {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            System.out.println("Testhere"+Burl);
            if(Burl == null)
                Toast.makeText(getApplicationContext(),"Go near Advisor's Office & try again",Toast.LENGTH_LONG).show();
            Thread.sleep(2500);
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            return;

        }



    public void goToAlreadyRegPage (){
        Intent intent = new Intent(getApplicationContext(),AlreadyReg.class);
        String message = "wants to log out";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
    public void goToQueuingListPage (String result){
        Intent intent = new Intent(getApplicationContext(),QueueListing.class);
        String message = "wants to log out";
        intent.putExtra("jsonObject", result);
        startActivity(intent);

    }

}




