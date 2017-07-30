package adapter;

import android.app.Activity;
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

/**
 * Created by LENOVO on 4/19/2016.
 */
public class ResultListAdapter extends BaseAdapter {
    Activity context;
    CommonClass common;
    private JSONArray postItems;
    public static float sum_total_marks, sum_obtain_marks;

    LayoutInflater inflater;
    public ResultListAdapter(Activity con, JSONArray array, String exam_id){
            context = con;
postItems = array;
        sum_obtain_marks = 0;
        sum_total_marks = 0;
        common = new CommonClass(con);
        inflater = LayoutInflater.from(context);
        new getResultTask().execute(exam_id);
    }
    @Override
    public int getCount() {
        return postItems.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public  float getSum_total_marks(){
        for (int i=0;i<postItems.length(); i++) {
            JSONObject obj = null;
            try {
                obj = postItems.getJSONObject(i);
                //sum_obtain_marks = sum_obtain_marks + Float.valueOf(obj.getString("mark_obtain"));
                sum_total_marks = sum_total_marks + Float.valueOf(obj.getString("total_mark"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.sum_total_marks;
    }
    public  float getSum_obtain_marks(){
        for (int i=0;i<postItems.length(); i++) {
            JSONObject obj = null;
            try {
                obj = postItems.getJSONObject(i);
                sum_obtain_marks = sum_obtain_marks + Float.valueOf(obj.getString("mark_obtain"));
                //sum_total_marks = sum_total_marks + Float.valueOf(obj.getString("total_mark"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return  this.sum_obtain_marks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= inflater.inflate(R.layout.row_of_result_list, null);
        TextView subject = (TextView)convertView.findViewById(R.id.subject);
        TextView obtain_mark = (TextView)convertView.findViewById(R.id.obtain_marks);
        TextView total_mark = (TextView)convertView.findViewById(R.id.total_marks);

        try {
            JSONObject obj = postItems.getJSONObject(position);
            subject.setText(obj.getString("subject"));
            obtain_mark.setText(obj.getString("mark_obtain"));
            total_mark.setText(obj.getString("total_mark"));
           } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }


    public class getResultTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            String responseString = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));
            nameValuePairs.add(new BasicNameValuePair("exam_id", params[0]));

            JSONParser jsonParser = new JSONParser();

            try {
                String json_responce = jsonParser.makeHttpRequest(ConstValue.RESULTS_REPORT_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        postItems = jObj.getJSONArray("data");

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
                if (postItems!=null){
                    notifyDataSetChanged();
                }
            } else {
                Toast.makeText(context, success, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
