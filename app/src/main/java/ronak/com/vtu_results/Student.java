package ronak.com.vtu_results;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ronak on 27-03-2015.
 */
public class Student implements Serializable {


    private String name;
    private String Semester;
    private String USN;
    private String total;
    private String result;
    private ArrayList<String> subjects;
    private ArrayList<String> internals;
    private ArrayList<String> externals;
    private ArrayList<String> total_subjects;
    private ArrayList<String> avg_uni;
    private ArrayList<String> top_uni;
    private ArrayList<String> top_class;
    private ArrayList<String> avg_class;

    public ArrayList<String> getAvg_uni() {
        return avg_uni;
    }

    public void setAvg_uni(ArrayList<String> avg_uni) {
        this.avg_uni = avg_uni;
    }

    public ArrayList<String> getTop_uni() {
        return top_uni;
    }

    public void setTop_uni(ArrayList<String> top_uni) {
        this.top_uni = top_uni;
    }

    public ArrayList<String> getTop_class() {
        return top_class;
    }

    public void setTop_class(ArrayList<String> top_class) {
        this.top_class = top_class;
    }

    public ArrayList<String> getAvg_class() {
        return avg_class;
    }

    public void setAvg_class(ArrayList<String> avg_class) {
        this.avg_class = avg_class;
    }

    public Student()
    {
        this.name = null;
        this.Semester = null;
        this.USN = null;
        this.total = null;
        this.result = null;
        this.subjects = new ArrayList<String>();
        this.internals = new ArrayList<String>();
        this.externals =  new ArrayList<String>();
        this.total_subjects = new ArrayList<String>();
        this.avg_class = new ArrayList<String>();
        this.avg_uni = new ArrayList<String>();
        this.top_class = new ArrayList<String>();
        this.top_uni = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<String> getInternals() {
        return internals;
    }

    public void setInternals(ArrayList<String> internals) {
        this.internals = internals;
    }

    public ArrayList<String> getExternals() {
        return externals;
    }

    public void setExternals(ArrayList<String> externals) {
        this.externals = externals;
    }

    public ArrayList<String> getTotal_subjects() {
        return total_subjects;
    }

    public void setTotal_subjects(ArrayList<String> total_subjects) {
        this.total_subjects = total_subjects;
    }
}
