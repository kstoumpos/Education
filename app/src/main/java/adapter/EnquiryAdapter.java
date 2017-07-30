package adapter;


import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.R;

import java.util.ArrayList;
import java.util.HashMap;

import util.CommonClass;
import util.JSONReader;

public class EnquiryAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<HashMap<String, String>> postItems;

    CommonClass common;
    JSONReader j_reader;


    public EnquiryAdapter(Activity act, ArrayList<HashMap<String, String>>  arraylist){
        this.context = act;
        common = new CommonClass(act);
        j_reader = new JSONReader(act);
        postItems = arraylist;
    }

    @Override
    public int getCount() {
        return postItems.size();
    }
    @Override
    public Object getItem(int position) {
            return null;

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
            convertView = mInflater.inflate(R.layout.row_for_enquiry, null);
        }

            HashMap<String,String> map = postItems.get(position);
            TextView subject = (TextView)convertView.findViewById(R.id.subject);
            TextView message = (TextView)convertView.findViewById(R.id.message);
            TextView reaply = (TextView)convertView.findViewById(R.id.reaply);
            TextView date = (TextView)convertView.findViewById(R.id.date);

            message.setText(map.get("message"));
            subject.setText(map.get("subject"));
            reaply.setText(Html.fromHtml( map.get("reply") ));
            date.setText(map.get("on_date"));
        LinearLayout replyview = (LinearLayout)convertView.findViewById(R.id.replyview);

if (map.get("reply").equalsIgnoreCase("")){
    replyview.setVisibility(View.GONE);
}else{
    replyview.setVisibility(View.VISIBLE);
}

        return convertView;
    }



}

