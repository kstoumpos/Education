package util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Config.ConstValue;

/**
 * Created by LENOVO on 4/20/2016.
 */
public class JSONReader {
    Context context;
    public SharedPreferences settings;
    public JSONReader(Context con){
        context = con;
        settings = context.getSharedPreferences(ConstValue.PREF_NAME, 0);
    }

    public void setJSONPref(String key, String json){
        settings.edit().putString(key,json).commit();
    }
    public JSONObject getJSONObject(String key){
        try {
            return new JSONObject(settings.getString(key,""));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public JSONArray getJSONArray(String key){
        try {
            return new JSONArray(settings.getString(key,""));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getJSONkeyString(String objKey, String stringkey){
        JSONObject obj = getJSONObject(objKey);
        if (objKey!=null){
            try {
                return obj.getString(stringkey);
            } catch (JSONException e) {
                e.printStackTrace();
                return  "";
            }
        }else {
            return  "";
        }
    }
}
