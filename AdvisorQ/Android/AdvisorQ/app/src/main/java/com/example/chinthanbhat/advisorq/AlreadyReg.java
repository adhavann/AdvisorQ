package com.example.chinthanbhat.advisorq;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AlreadyReg extends AppCompatActivity {

    public static final String url_insert_location = "****PHP File Location****";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Button button3;
        final EditText UTAidAR;




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String intentTest = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        System.out.println("intentTest");

        button3 = (Button)findViewById(R.id.button3);
        UTAidAR = (EditText)findViewById(R.id.UTAidar);

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                if(checkInternetConnection()){
                    String idToSend = UTAidAR.getText().toString();
//                    ArrayList<NameValuePair> params = new ArrayList<>();
//                    params.add(new BasicNameValuePair("operation", "AddMeToQueue"));
//                    params.add(new BasicNameValuePair("uta_id", idToSend));
                    System.out.println("id" + idToSend);

                    new SendToServer4().execute(UTAidAR.getText().toString());
                    //Toast.makeText(getApplicationContext(), toastIt, Toast.LENGTH_LONG).show();


                }
            }
        });


    }

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
                    final String result = toastIt.replaceFirst(Pattern.quote("RealTimeQueue"), "");

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
                            goToQueuingListPage(result);
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

    public void goToQueuingListPage (String result){
        Intent intent = new Intent(getApplicationContext(),QueueListing.class);
        String message = "wants to log out";
        intent.putExtra("jsonObject", result);
        startActivity(intent);

    }



    private boolean checkInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();

            Log.d("checkInternetConenction", " Connected");
            return true;
        } else {
            Toast.makeText(this, " No Internet ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    class SendToServer4 extends AsyncTask<String, String, String> {
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
                //Log.i("string", auth[1]);

//                String name2 = auth[0];
                String utaid = auth[0];
                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("operation", "AddMeToQueue"));
                params.add(new BasicNameValuePair("uta_id", utaid));
                System.out.println("parametr" + params);

//

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
                //final String toastIt = null;
                String json;
                if (httpResponse.getStatusLine().getStatusCode() == 200)
                {
                    HttpEntity entity = httpResponse.getEntity();
                    json = EntityUtils.toString(entity);
                    System.out.println("Response6" + json);
                    final String toastIt = json.toString();
                    System.out.println("toast" + toastIt);
                    final String result = toastIt.replaceFirst(Pattern.quote("AddMeToQueue"), "");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                return "";
////
            } catch (Exception e) {
                Log.i("error", e.toString());
                return "call";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            //dialog.dismiss();
            //Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();


        }
    }


}
