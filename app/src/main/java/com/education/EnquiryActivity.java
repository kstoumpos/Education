package com.education;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Config.ConstValue;
import adapter.EnquiryAdapter;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

public class EnquiryActivity extends CommonAppCompatActivity {
    AlertDialog.Builder builder;
//    JSONArray jArray;
    ArrayList<HashMap<String,String>> enquiry_array;
    CommonClass common;
    JSONReader j_reader;
    EnquiryAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);

        common = new CommonClass(this);
        j_reader = new JSONReader(this);

        Button btnAsk = (Button)findViewById(R.id.btnAsk);
        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticePopup();
            }
        });

        enquiry_array = new ArrayList<HashMap<String, String>>();

        listview = (ListView)findViewById(R.id.listview);
        adapter = new EnquiryAdapter(this,enquiry_array);
        listview.setAdapter(adapter);

        new loadDataTask().execute();
    }

    public  void  showNoticePopup(){
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View dview = inflater.inflate(R.layout.dialog_enquiry, null);
        final TextView txtsubject = (TextView)dview.findViewById(R.id.subject);
        final TextView txtmessage = (TextView)dview.findViewById(R.id.message);

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        if (txtmessage.getText().length() > 0  && txtsubject.getText().length() > 0){
                            new SendMessageTask().execute(txtsubject.getText().toString(),txtmessage.getText().toString());
                        }
                    }
                });
        builder.show();
    }

    class loadDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("school_id", j_reader.getJSONkeyString("student_data", "school_id")));
                nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));

                JSONParser jParser = new JSONParser();
                String json = jParser.makeHttpRequest(ConstValue.ENQUIRY_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        JSONArray jArray = jObj.getJSONArray("data");
                        enquiry_array.clear();
                        for (int i = 0 ; i < jArray.length() ; i++){
                            JSONObject obj = jArray.getJSONObject(i);
                            HashMap<String,String> map = new HashMap<String,String>();
                            map.put("chat_id",obj.getString("chat_id"));
                            map.put("student_id",obj.getString("student_id"));
                            map.put("school_id",obj.getString("school_id"));
                            map.put("message",obj.getString("message"));
                            map.put("subject",obj.getString("subject"));
                            map.put("reply",obj.getString("reply"));
                            map.put("on_date",obj.getString("on_date"));
                            enquiry_array.add(map);
                        }
                    }else{
                        responseString = "Not Data found";
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return responseString;

        }


        @Override
        protected void onPostExecute(String result) {
            if(result!=null){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }else{
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
        }

    }


    public class SendMessageTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            String responseString = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("school_id", j_reader.getJSONkeyString("student_data", "school_id")));
            nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));

            nameValuePairs.add(new BasicNameValuePair("subject", params[0]));
            nameValuePairs.add(new BasicNameValuePair("message",params[1]));
            JSONParser jsonParser = new JSONParser();

            try {
                String json_responce = jsonParser.makeHttpRequest(ConstValue.SEND_ENQUIRY_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        JSONObject objUser = jObj.getJSONObject("data");
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("chat_id",objUser.getString("chat_id"));
                        map.put("student_id",objUser.getString("student_id"));
                        map.put("school_id",objUser.getString("school_id"));
                        map.put("message",objUser.getString("message"));
                        map.put("subject",objUser.getString("subject"));
                        map.put("reply",objUser.getString("reply"));
                        map.put("on_date",objUser.getString("on_date"));
                        enquiry_array.add(map);

                    }else{
                        responseString = "User not found";
                    }
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
                e.printStackTrace();
            }

            // TODO: register the new account here.
            return responseString;
        }

        @Override
        protected void onPostExecute(final String success) {
            if (success==null) {
                adapter.notifyDataSetChanged();
            } else {

            }
        }

    }
}
