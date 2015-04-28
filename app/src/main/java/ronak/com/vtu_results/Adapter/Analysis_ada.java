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

import ronak.com.vtu_results.R;

/**
 * Created by ronak on 14-04-2015.
 */
public class Analysis_ada extends ArrayAdapter {

    Context context;
    ArrayList<String> avg;
    ArrayList<String> uni_avg;
    ArrayList<String> top;
    ArrayList<String> uni_top;
    ArrayList<String> self;
    ArrayList<String> name;
    public Analysis_ada(Context context, int resource,ArrayList<String> name,ArrayList<String> avg,ArrayList<String> uni_avg,ArrayList<String> top,ArrayList<String> uni_top,ArrayList<String> self ) {
        super(context, resource, uni_avg);
        this.context = context;
        this.avg = avg;
        this.uni_avg = uni_avg;
        this.name = name;
        this.self = self;
        this.top = top;
        this.uni_top = uni_top;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView!=null)
        {
            view = convertView;
        }
        else
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.analysis_ada,parent,false);
        }
        ProgressBar branchT,branchA,universityT,universityA,self1;
        TextView branchT_tv,branchA_tv,universityT_tv,universityA_tv,self1_tv,name1;
        branchA_tv = (TextView) view.findViewById(R.id.ana_tvbrava);
        branchT_tv = (TextView) view.findViewById(R.id.ana_tvbrat);
        universityA_tv = (TextView) view.findViewById(R.id.ana_tvavg);
        universityT_tv = (TextView) view.findViewById(R.id.ana_tvt);
        self1_tv = (TextView) view.findViewById(R.id.ana_tvself);
        name1 = (TextView) view.findViewById(R.id.ana_tvname);
        name1.setText(name.get(position));
        branchA_tv.setText("Branch Average: "+avg.get(position));
        branchT_tv.setText("Branch Topper: "+top.get(position));
        universityA_tv.setText("University Average: "+uni_avg.get(position));
        universityT_tv.append("University Topper"+uni_top.get(position));
        self1_tv.setText("Your Score: "+self.get(position));
        branchA = (ProgressBar) view.findViewById(R.id.branch_top);
        branchT = (ProgressBar) view.findViewById(R.id.branch_avg);
        universityA = (ProgressBar) view.findViewById(R.id.uni_avg);
        universityT = (ProgressBar) view.findViewById(R.id.uni_top);
        self1 = (ProgressBar) view.findViewById(R.id.score);
        int max;
        if(name.get(position).contains("Lab"))
            max = 75;
        else
            max = 125;
        branchA.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.dark_blue), PorterDuff.Mode.SRC_IN);
        branchT.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
        universityA.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.orange_dark), PorterDuff.Mode.SRC_IN);
        universityT.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
        self1.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);
        branchA.setMax(max);
        branchT.setMax(max);
        universityA.setMax(max);
        universityT.setMax(max);
        self1.setMax(max);
        branchA.setProgress((int) Float.parseFloat(avg.get(position)));
        branchT.setProgress((int)Float.parseFloat(top.get(position)));
        universityA.setProgress((int)Float.parseFloat(uni_avg.get(position)));
        universityT.setProgress((int)Float.parseFloat(uni_top.get(position)));
        self1.setProgress((int) Float.parseFloat(self.get(position)));



        return view;
    }
}
