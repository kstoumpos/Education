package com.education;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.ResultListAdapter;
import util.CommonClass;
import util.JSONReader;

public class ResultListActivity extends CommonAppCompatActivity {
    CommonClass common;
    JSONReader j_reader;
    JSONArray objResultArray;
    ResultListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String exam_id = getIntent().getExtras().getString("exam_id");

        common = new CommonClass(this);
        j_reader = new JSONReader(this);
        objResultArray = new JSONArray();
ListView listview = (ListView)findViewById(R.id.listview);
        adapter = new ResultListAdapter(this,objResultArray,exam_id);
listview.setAdapter(adapter);
        LayoutInflater inflaterFooter = this.getLayoutInflater();
        View footerview = inflaterFooter.inflate(R.layout.row_of_result_footer, null);
        final TextView sum_totla_marks = (TextView) footerview.findViewById(R.id.sum_total_marks);
        final TextView sum_obtain_mark = (TextView) footerview.findViewById(R.id.sum_obtain_marks);
        final TextView txtpercentage = (TextView) findViewById(R.id.percentage);
        listview.addFooterView(footerview);

        listview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
float obtain = adapter.getSum_obtain_marks();
                float total = adapter.getSum_total_marks();
                sum_obtain_mark.setText(String.valueOf(obtain));
                sum_totla_marks.setText(String.valueOf(total));

                float average = obtain * 100 / total;
                txtpercentage.setText(String.valueOf(average)+"%");


            }
        });


        TextView txtexamtitle = (TextView)findViewById(R.id.exam_title);
        TextView txtexamdate = (TextView)findViewById(R.id.exam_date);
        TextView txtstudname = (TextView)findViewById(R.id.stud_name);
        try {
            JSONObject exam = new JSONObject(getIntent().getExtras().getString("exam"));
            txtexamtitle.setText(exam.getString("exam_title"));
            txtexamdate.setText(exam.getString("exam_date"));
            txtstudname.setText(j_reader.getJSONkeyString("student_data","student_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
