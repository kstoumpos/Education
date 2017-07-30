package com.education;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by LENOVO on 4/23/2016.
 */
public class CommonAppCompatActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = null;
        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case  R.id.action_profile:
                intent = new Intent(this,ProfileActivity.class);
                break;
            case  R.id.action_result:
                intent = new Intent(this,ResultActivity.class);
                break;
            case  R.id.action_exam:
                intent = new Intent(this,ExamActivity.class);
                break;
            case  R.id.action_teacher:
                intent = new Intent(this,TeacherActivity.class);
                break;
            case  R.id.action_growth:
                intent = new Intent(this,GrowthActivity.class);
                break;
            case  R.id.action_holiday:
                intent = new Intent(this,HolidaysActivity.class);
                break;
            case  R.id.action_news:
                intent = new Intent(this,NewsActivity.class);
                break;
            case  R.id.action_notice:
                intent = new Intent(this,NoticeActivity.class);
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
