package ronak.com.vtu_results;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import ronak.com.vtu_results.Adapter.Analysis_ada;

/**
 * Created by ronak on 14-04-2015.
 */
public class Analysis extends ActionBarActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.live));
        Student student = (Student) getIntent().getExtras().getSerializable("student");
        actionBar.setTitle("Analysis: "+student.getName()+" "+student.getTotal());
        listView  = (ListView) findViewById(R.id.ana_list);
        Analysis_ada analysis_ada = new Analysis_ada(getApplicationContext(),0,student.getSubjects(),student.getAvg_class(),student.getAvg_uni(),student.getTop_class(),student.getTop_uni(),student.getTotal_subjects());
        listView.setAdapter(analysis_ada);
    }
}
