package ronak.com.vtu_results.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ronak.com.vtu_results.R;
import ronak.com.vtu_results.Student;

/**
 * Created by ronak on 03-04-2015.
 */
public class Class_adp extends ArrayAdapter {

    ArrayList<Student> students;
    int[] color = {R.color.percent_70,R.color.percent_60,R.color.percent_45,R.color.percent_35,R.color.percent_20};

    Context context;
    public Class_adp(Context context, int resource, ArrayList<Student> students) {
        super(context, resource, students);
        this.students = students;
        this.context= context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.class_list_layout, parent, false);
        }
        else
        {
            view = convertView;
        }
        TextView name, marks;
        ProgressBar progressBar;
        progressBar = (ProgressBar) view.findViewById(R.id.progress_class);
        name = (TextView) view.findViewById(R.id.tv_name);
        marks = (TextView) view.findViewById(R.id.tv_marks);
        Student student = students.get(position);
        String r = String.format("%2d: %s %s",(position+1),student.getName(),student.getUSN());
        name.setText(r);
        int m = Integer.parseInt(student.getTotal());
        float percentage =(float) (m*100)/900;
        int color_id;
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
        progressBar.getProgressDrawable().setColorFilter(context.getResources().getColor(color_id), PorterDuff.Mode.SRC_IN);
        progressBar.setProgress((int)percentage);
        String f = String.format("%d (%.2f",m,percentage);
        marks.setText(f+"%)");
        return view;

    }
}
