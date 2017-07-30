package com.education;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import util.RoundedImageView;

public class ProfileActivity extends CommonAppCompatActivity {
    CommonClass common;
    JSONReader j_reader;
    JSONObject objStudData;
    DisplayImageOptions options;
    ImageLoaderConfiguration imgconfig;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_11));
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(ProfileActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });*/

        File cacheDir = StorageUtils.getCacheDirectory(this);
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

        imgconfig = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(imgconfig);


        j_reader = new JSONReader(this);
        common = new CommonClass(this);
        new getUserData().execute();
    }

    public void loadCView(){
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsToolbar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
       // collapsToolbar.setBackgroundResource(R.drawable.ic_home_logo);
//collapsToolbar.setBackgroundDrawable();
        try {
            //Bitmap bitmap = ImageLoader.getInstance().loadImageSync(ConstValue.BASE_URL+"/uploads/studentphoto/"+ objStudData.getString("student_photo"));
            //BitmapDrawable background = new BitmapDrawable(bitmap);
            //collapsToolbar.setBackgroundDrawable(background);
            RoundedImageView top_image = (RoundedImageView)findViewById(R.id.top_image);
            ImageLoader.getInstance().displayImage(ConstValue.BASE_URL + "/uploads/studentphoto/" + objStudData.getString("student_photo"), top_image, options, animateFirstListener);


            //getSupportActionBar().setTitle(objStudData.getString("student_name"));
            getSupportActionBar().setTitle("");

            TextView txtname = (TextView)findViewById(R.id.fullname);
            txtname.setText(objStudData.getString("student_name"));

            TextView txtbdate = (TextView)findViewById(R.id.birthdate);
            txtbdate.setText(objStudData.getString("student_birthdate"));

            TextView txtrollno = (TextView)findViewById(R.id.rollno);
            txtrollno.setText(objStudData.getString("student_roll_no"));

            TextView txtstandard = (TextView)findViewById(R.id.standard);
            txtstandard.setText(objStudData.getString("standard_title"));

            TextView txtaddress = (TextView)findViewById(R.id.address);
            txtaddress.setText(objStudData.getString("student_address"));

            TextView txtcity = (TextView)findViewById(R.id.city);
            txtcity.setText(objStudData.getString("student_city"));

            TextView txtparentphone = (TextView)findViewById(R.id.parent_contact);
            txtparentphone.setText(objStudData.getString("student_parent_phone"));

            TextView txtbranch = (TextView)findViewById(R.id.branch);
            txtbranch.setText(objStudData.getString("student_branch"));

            TextView txtsemester = (TextView)findViewById(R.id.semester);
            txtsemester.setText(objStudData.getString("student_semester"));

            TextView txtdivision = (TextView)findViewById(R.id.division);
            txtdivision.setText(objStudData.getString("student_division"));

            TextView txtbatch = (TextView)findViewById(R.id.batch);
            txtbatch.setText(objStudData.getString("student_batch"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public class getUserData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String responseString = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("student_id", common.getSession(ConstValue.COMMON_KEY)));
            JSONParser jsonParser = new JSONParser();

            try {
                String json_responce = jsonParser.makeHttpRequest(ConstValue.STUDENT_PROFILE_URL,"POST", nameValuePairs);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                }else {
                    if(jObj.has("data")){
                        objStudData = jObj.getJSONObject("data");

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
                loadCView();
            } else {
                Toast.makeText(getApplicationContext(),success,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }


}
