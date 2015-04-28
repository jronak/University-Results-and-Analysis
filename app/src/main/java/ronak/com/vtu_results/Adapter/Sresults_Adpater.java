package ronak.com.vtu_results.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ronak.com.vtu_results.R;
import ronak.com.vtu_results.Student;

/**
 * Created by ronak on 27-03-2015.
 */
public class Sresults_Adpater extends ArrayAdapter {

    int[] color = {R.color.percent_70,R.color.percent_60,R.color.percent_45,R.color.percent_35,R.color.percent_20};
    ArrayList<String> subject,external,internal,total;
    int id;
    Context context;
    public Sresults_Adpater(Context context, int resource,ArrayList<String> subject,
                            ArrayList<String> external, ArrayList<String> internal, ArrayList<String> total) {
        super(context, resource, subject);
        this.subject = subject;
        this.external = external;
        this.internal = internal;
        this.total = total;
        this.id = resource;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.results_list,parent,false);
        }
        else
        {
            view = convertView;
        }
        TextView tv = (TextView) view.findViewById(R.id.list_tv);
        String s = subject.get(position);
        tv.setText(s);
        TextView in, ex, tot;
        in = (TextView) view.findViewById(R.id.internal);
        ex = (TextView) view.findViewById(R.id.external);
        tot = (TextView) view.findViewById(R.id.total);
        int marks ;
        try{
            marks = Integer.parseInt(total.get(position));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            marks=1;
        }

        int percentage;
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        int color_id;
        if(s.contains("Lab"))
        {
            percentage = (int) (marks/.75);
        }
        else
        {
            percentage = (int) (marks/1.25);
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
        in.setText(internal.get(position));
        ex.setText(external.get(position));
        tot.setText(total.get(position));
        progressBar.setProgress(percentage);
        progressBar.setMax(100);
        progressBar.getProgressDrawable().setColorFilter(context.getResources().getColor(color_id), PorterDuff.Mode.SRC_IN);
        return view;
    }
}
