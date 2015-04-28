package ronak.com.vtu_results;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import ronak.com.vtu_results.Adapter.Sresults_Adpater;

/**
 * Created by ronak on 27-03-2015.
 */
public class Results extends ActionBarActivity {

    Student student;
    int[] color = {R.color.percent_70,R.color.percent_60,R.color.percent_45,R.color.percent_35,R.color.percent_20};
    ProgressDialog progressDialog;
    ListView listView;
    Sresults_Adpater adpater;
    TextView name_tv,total_tv;
    android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle extra = getIntent().getExtras();
        student = (Student) extra.getSerializable("student");
        Boolean clickable = extra.getBoolean("click");
        name_tv = (TextView) findViewById(R.id.result_name);
        listView = (ListView) findViewById(R.id.result_list);
        name_tv.setText(student.getName()+"\t"+student.getUSN());
        actionBar = getSupportActionBar();
        int semester;
        ArrayList<String> total = student.getTotal_subjects();
        try
        {
            semester = Integer.parseInt(student.getSemester());
        }
        catch (Exception e)
        {
            semester = 0;
            e.printStackTrace();
        }
        int max;
        if(semester>2)
            max=900;
        else
            max=775;
        int color_id;
        float percentage;
        if(student.getTotal()==null)
        {
            int marks=0;
            for(int i=0; i<total.size(); i++)
            {
                marks+=Integer.parseInt(total.get(i));
            }
            student.setTotal(""+marks);
            Log.e("Fail","marks"+marks);
        }
        try{
            percentage =(float) (Integer.parseInt(student.getTotal().trim())*100)/max;
        }
        catch (Exception e)
        {
            percentage = 0;
            e.printStackTrace();
        }
        if(percentage>=70)
            color_id=color[0];
        else if(percentage>=60)
            color_id=color[1];
        else if(percentage>=45)
            color_id=color[2];
        else if (percentage>=35)
            color_id=color[3];
        else
            color_id=color[4];
        if(student.getResult().contains("FAIL"))
            color_id=color[4];
        String per = String.format("%.2f",percentage);
        actionBar.setTitle("Total: "+student.getTotal()+" ("+per+"%) "+student.getResult());
        actionBar.setBackgroundDrawable(getResources().getDrawable(color_id));
        try
        {
            adpater = new Sresults_Adpater(this,100,student.getSubjects(),student.getExternals(),student.getInternals(),
                    student.getTotal_subjects());
            listView.setAdapter(adpater);
        }
        catch (Exception e)
        {
            finish();
        }
    }

}
