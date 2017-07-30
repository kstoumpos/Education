package util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.education.AttendenceActivity;
import com.education.ExamActivity;
import com.education.GrowthActivity;
import com.education.HolidaysActivity;
import com.education.NewsActivity;
import com.education.NoticeActivity;
import com.education.ProfileActivity;
import com.education.ResultActivity;
import com.education.TeacherActivity;

import Config.ConstValue;

/**
 * Created by LENOVO on 4/20/2016.
 */
public class CommonClass {
    Activity activity;
    public SharedPreferences settings;

    public CommonClass(Activity activity){
        this.activity = activity;
        settings = activity.getSharedPreferences(ConstValue.PREF_NAME, 0);
    }
    public void setSession(String key,String value){
        settings.edit().putString(key,value).commit();
    }
    public String getSession(String key){
        return settings.getString(key,"");
    }
    public boolean is_user_login(){
        String key = getSession(ConstValue.COMMON_KEY);
        if (key==null || key.equalsIgnoreCase("")){
            return  false;
        }else {
            return  true;
        }
    }
    public  void open_screen(int position){
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(activity, ProfileActivity.class);
                break;
            case 1:
                intent = new Intent(activity, AttendenceActivity.class);
                break;
            case 2:
                intent = new Intent(activity, ExamActivity.class);
                break;
            case 3:
                intent = new Intent(activity, ResultActivity.class);
                break;
            case 4:
                intent = new Intent(activity, TeacherActivity.class);
                break;
            case 5:
                intent = new Intent(activity, GrowthActivity.class);
                break;
            case 6:
                intent = new Intent(activity, HolidaysActivity.class);
                break;
            case 7:
                intent = new Intent(activity, NewsActivity.class);
                break;
            case 8:
                intent = new Intent(activity, NoticeActivity.class);
                break;

        }
        if (intent!=null){
            activity.startActivity(intent);
        }
    }
}
