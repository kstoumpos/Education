package com.education;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.ResultsAdapter;
import util.CommonClass;
import util.JSONReader;

public class ResultActivity extends CommonAppCompatActivity {
    CommonClass common;
    JSONReader j_reader;
    JSONArray objResultArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_41));
        }

        common = new CommonClass(this);
        j_reader = new JSONReader(this);

        ListView listView = (ListView)findViewById(R.id.listView2);
        final ResultsAdapter adapter = new ResultsAdapter(this, new JSONArray());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject obj = adapter.getItem(position);
                try {
                    //new getResultTask().execute(obj.getString("exam_id"));
                    Intent intent = new Intent(ResultActivity.this, ResultListActivity.class);
                    intent.putExtra("exam_id",obj.getString("exam_id"));
                    intent.putExtra("exam",obj.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });
    }


    public  void  showNoticePopup(){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View dview = inflater.inflate(R.layout.dialog_result, null);
        TextView txtnote = (TextView)dview.findViewById(R.id.note);
        ListView listview = (ListView)dview.findViewById(R.id.listView3);
        ResultListAdapter radapter = new ResultListAdapter(this,objResultArray);
        listview.setAdapter(radapter);

        LayoutInflater inflaterFooter = this.getLayoutInflater();
        View footerview = inflaterFooter.inflate(R.layout.row_of_result_footer, null);
        TextView sum_totla_marks = (TextView)footerview.findViewById(R.id.sum_total_marks);
        TextView sum_obtain_mark = (TextView)footerview.findViewById(R.id.sum_obtain_marks);

        sum_obtain_mark.setText(String.valueOf(radapter.getSum_obtain_marks()));
        sum_totla_marks.setText(String.valueOf(radapter.getSum_total_marks()));

        listview.addFooterView(footerview);

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
        builder.show();
        */
    }




}
