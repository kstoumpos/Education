package adapter;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.education.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Config.ConstValue;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

public class NoticeAdapter extends BaseAdapter {

    private Activity context;
    //private ArrayList<HashMap<String, String>> postItems;
    private JSONArray postItems;
    CommonClass common;
    JSONReader j_reader;

    Double cLat,cLog;
    int count = 0;
    TextView txtLikes;
    private int lastPosition = -1;
    ProgressDialog dialog;
    public NoticeAdapter(Activity act, JSONArray arraylist){
        this.context = act;
        common = new CommonClass(act);
        j_reader = new JSONReader(act);
        postItems = arraylist;
        new loadDataTask().execute();
    }

    @Override
    public int getCount() {
        return postItems.length();
    }
    @Override
    public JSONObject getItem(int position) {
        try {
            return postItems.getJSONObject(position);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_for_notice, null);
        }

        lastPosition = position;

        try {
            JSONObject jObj = postItems.getJSONObject(position);
            TextView date = (TextView)convertView.findViewById(R.id.date);
            TextView notice_type = (TextView)convertView.findViewById(R.id.notice_type);
            TextView txtDescription = (TextView)convertView.findViewById(R.id.description);


            date.setText(jObj.getString("on_date"));
            notice_type.setText(jObj.getString("notice_type"));
            txtDescription.setText(jObj.getString("notice_description"));


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return convertView;
    }

    class loadDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "",
                    "Loading. Please wait...", true);
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

                JSONParser jParser = new JSONParser();
                String json = jParser.makeHttpRequest(ConstValue.NOTICEBOARD_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        postItems = jObj.getJSONArray("data");
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
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }else{

                notifyDataSetChanged();
            }
            dialog.dismiss();
        }
        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
        }

    }


}

