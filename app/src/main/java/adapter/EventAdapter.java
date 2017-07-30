package adapter;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.education.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Config.ConstValue;
import util.AnimateFirstDisplayListener;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

public class EventAdapter extends BaseAdapter {

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

    DisplayImageOptions options;
    ImageLoaderConfiguration imgconfig;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public EventAdapter(Activity act, JSONArray arraylist){
        this.context = act;
        common = new CommonClass(act);
        j_reader = new JSONReader(act);
        postItems = arraylist;
        new loadDataTask().execute();

        File cacheDir = StorageUtils.getCacheDirectory(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_home_logo)
                .showImageForEmptyUri(R.drawable.ic_home_logo)
                .showImageOnFail(R.drawable.ic_home_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .imageScaleType(ImageScaleType.NONE)
                .build();

        imgconfig = new ImageLoaderConfiguration.Builder(context)
                .build();
        ImageLoader.getInstance().init(imgconfig);



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
            convertView = mInflater.inflate(R.layout.row_for_event, null);
        }

        lastPosition = position;

        try {
            JSONObject jObj = postItems.getJSONObject(position);
            TextView start_date = (TextView)convertView.findViewById(R.id.start_date);
            TextView end_date = (TextView)convertView.findViewById(R.id.end_date);
            TextView txttitle = (TextView)convertView.findViewById(R.id.title);
            TextView txtDescription = (TextView)convertView.findViewById(R.id.description);


            start_date.setText("From : "+jObj.getString("event_start"));
            end_date.setText("To : "+jObj.getString("event_end"));
            txtDescription.setText(jObj.getString("event_description"));
            txttitle.setText(jObj.getString("event_title"));

            ImageView top_image = (ImageView)convertView.findViewById(R.id.imageView);
            if(jObj.getString("event_image").equalsIgnoreCase("")){
                top_image.setVisibility(View.GONE);
            }else {
                ImageLoader.getInstance().displayImage(ConstValue.BASE_URL + "/uploads/eventphoto/" + jObj.getString("event_image"), top_image, options, animateFirstListener);
                top_image.setVisibility(View.VISIBLE);
            }
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
                String json = jParser.makeHttpRequest(ConstValue.EVENT_URL,"POST", nameValuePairs);

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

