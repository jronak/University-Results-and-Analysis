package ronak.com.vtu_results;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ronak.com.vtu_results.Adapter.Sresults_Adpater;


public class Home extends ActionBarActivity {

    EditText usnBox;
    Vtu_download vtu_download;
    Student student;
    ProgressDialog progressDialog;
    Download download;
    TextView textView;
    Boolean aBoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usnBox = (EditText)findViewById(R.id.USN_box);
        boolean called = false;
        called = getIntent().getBooleanExtra("called",false);
        setup_dialog();
        textView = (TextView) findViewById(R.id.tv);
        aBoolean =getIntent().getBooleanExtra("analysis",false);
        student = new Student();
        usnBox.setText("");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Live Results");
        if(aBoolean)
        {
            textView.setText("All the results form the university are computed to generate topper and average scores of each subject, including topper and average scores from the branch");
            actionBar.setTitle("Analysis");
        }
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.live));
        if(called)
        {
            download = new Download();
            String usn = null;
            usn= getIntent().getStringExtra("usn");
            Log.e("Home","Under called "+usn);
            if(usn!=null)
            {
                String link = "http://tvamsisai.co/vtu/public/usn/"+usn;
                download.execute(link);
            }
        }

    }

    public void getResults(View view)
    {
        String pattern = "[1-4]\\w{2}\\d{2}\\w{2}\\d{3}";
        String usn = usnBox.getText().toString();
        download =  new Download();
        vtu_download = new Vtu_download();
        if(usn.matches(pattern))
        {
            if(aBoolean)
            {
                //Link removed :P
                String link = "Link removed"+usn;
                download.execute(link);
            }
            else
            {
                String url[] = {"http://results.vtu.ac.in/vitavi.php?rid="+usn+"&submit=SUBMIT"};
                vtu_download.execute(url[0]);
            }
        }
        else {
            Toast.makeText(getBaseContext(),"Bad usn",Toast.LENGTH_SHORT).show();
            usnBox.setText("");
        }
    }


    private void setup_dialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Connecting to the server");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
                download.cancel(true);
            }
        });
    }

    public class Download extends AsyncTask< String, Integer , String > {

        String result= null;
        String vtu;
        Context context;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... link) {

            URL url;
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader;
            HttpURLConnection httpConnection = null;
           // result = "{\"usn\":\"1CR13CS083\",\"name\":\"RISHITHA KALAKATA\",\"semester\":\"3\",\"total\":\"612\",\"result\":\"FIRST CLASS\",\"subjects\":[{\"name\":\"Data Structures with C (10CS35)\",\"internal\":\"24\",\"external\":\"48\",\"total\":\"72\",\"result\":\"P\"},{\"name\":\"Data Structures with C\\/C++ Lab (10CSL37)\",\"internal\":\"23\",\"external\":\"44\",\"total\":\"67\",\"result\":\"P\"},{\"name\":\"Discrete Mathematical Structures (10CS34)\",\"internal\":\"25\",\"external\":\"50\",\"total\":\"75\",\"result\":\"P\"},{\"name\":\"Electronic Circuits & Logic Design Lab (10CSL38)\",\"internal\":\"23\",\"external\":\"40\",\"total\":\"63\",\"result\":\"P\"},{\"name\":\"Electronic Circuits (10CS32)\",\"internal\":\"25\",\"external\":\"39\",\"total\":\"64\",\"result\":\"P\"},{\"name\":\"Engineering Mathematics-III (10MAT31)\",\"internal\":\"22\",\"external\":\"76\",\"total\":\"98\",\"result\":\"P\"},{\"name\":\"Logic Design (10CS33)\",\"internal\":\"25\",\"external\":\"54\",\"total\":\"79\",\"result\":\"P\"},{\"name\":\"Object Oriented Programming with C++ (10CS36)\",\"internal\":\"22\",\"external\":\"72\",\"total\":\"94\",\"result\":\"P\"}]}";
            try {
                url = new URL(link[0]);
                try {
                    httpConnection = (HttpURLConnection) url.openConnection();
                    httpConnection.setConnectTimeout(5000);
                    httpConnection.setReadTimeout(5000);
                    httpConnection.connect();
                    if(httpConnection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                    {
                        Log.e("Connection failed", "Returning null");
                        return null;
                    }
                    int length = httpConnection.getContentLength();
                    inputStreamReader = new InputStreamReader(httpConnection.getInputStream());
                    bufferedReader =  new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    result = line;
                    publishProgress(50);
                    bufferedReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(result==null)
            {
                Log.e("No json","failed");
                progressDialog.setMessage("Querying Server 2\nPlease wait");
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Unreliable response from server",Toast.LENGTH_SHORT).show();
                //vtu_download.execute(vtu);
                finish();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                Log.e("JSON", "Looks fine with name");
                student.setName(jsonObject.getString("name"));
                Log.e("Name",student.getName());
                student.setUSN(jsonObject.getString("usn"));
                student.setResult(jsonObject.getString("result"));
                student.setSemester(jsonObject.getString("semester"));
                student.setTotal(jsonObject.getString("total"));
                JSONArray jsonArray = jsonObject.getJSONArray("subjects");
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> external = new ArrayList<String>();
                ArrayList<String> internal = new ArrayList<String>();
                ArrayList<String> total = new ArrayList<String>();
                ArrayList<String> avg = new ArrayList<String>();
                ArrayList<String> avg_uni = new ArrayList<String>();
                ArrayList<String> top = new ArrayList<String>();
                ArrayList<String> top_uni = new ArrayList<String>();
                int i;
                JSONObject subs;
                for( i=0; i<jsonArray.length(); i++)
                {
                    publishProgress((int)5);
                    subs = jsonArray.getJSONObject(i);
                    name.add(subs.getString("name"));
                    external.add(subs.getString("external"));
                    internal.add(subs.getString("internal"));
                    total.add(subs.getString("total"));
                    avg.add(subs.getString("avgInBranch"));
                    avg_uni.add(subs.getString("avg"));
                    top.add(subs.getString("topInBranch"));
                    top_uni.add(subs.getString("top"));
                }
                Log.e("Top"," "+top_uni.get(2));
                student.setExternals(external);
                student.setInternals(internal);
                student.setSubjects(name);
                student.setTotal_subjects(total);
                student.setAvg_class(avg);
                student.setAvg_uni(avg_uni);
                student.setTop_class(top);
                student.setTop_uni(top_uni);
                progressDialog.dismiss();
                if(aBoolean)
                {
                    Intent intent = new Intent(getApplicationContext(),Analysis.class);
                    intent.putExtra("student",student);
                    startActivity(intent);
                    progressDialog.dismiss();
                }
                else
                {
                    Intent intent =  new Intent(getApplicationContext(),Results.class);
                    intent.putExtra("student",student);
                    intent.putExtra("click",true);
                    startActivity(intent);
                }
                progressDialog.dismiss();
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class Vtu_download extends AsyncTask<String,Integer,String>
    {

        int fail;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... s) {
            fail=0;
            Document document = null;
            String url = s[0];
            try {
                document = Jsoup.connect(url).get();
                if(document==null)
                {
                    fail = 1;
                    return null;
                }
                Element element = null;
                element = document.select("table").get(16);
                if(element.text().contains("Results are not yet available "))
                {
                    fail=2;
                    return null;
                }
                publishProgress((int)30);
                element = element.select("td").get(1);
                String temp = element.select("b").get(0).text();
                //StringBuilder name = new StringBuilder();
               // StringBuilder usn = new StringBuilder();
                temp.replace("(","");
                temp.replace(")","");
                student.setName(temp);
                student.setUSN("");
                String semester;
                String result;

               /* Pattern pattern = Pattern.compile("\\w*\\s");
                Matcher matcher = pattern.matcher(temp);
                while(matcher.find())
                {
                    if(matcher.group()!=null)
                        name.append(matcher.group());
                }
                student.setName(name.toString().trim());
                pattern = Pattern.compile("[a-z0-9]*");
                matcher = pattern.matcher(temp);
                while(matcher.find())
                {
                    if(matcher.group()!=null)
                    {
                        usn.append(matcher.group());
                    }
                }
                student.setUSN(usn.toString().trim());*/
                student.setSemester(element.select("b").get(2).text().trim().toUpperCase());
                student.setResult(element.select("b").get(3).text().replace("Result:","").trim());
                ArrayList<String> subject = new ArrayList<String>();
                ArrayList<String> external = new ArrayList<String>();
                ArrayList<String> internal = new ArrayList<String>();
                ArrayList<String> total = new ArrayList<String>();
                student.setTotal(null);
                element = element.select("table").get(1);
                Elements elements = element.getElementsByTag("tr");
                publishProgress((int)60);
                Element element1;
                for(int i=1; i<elements.size(); i++)
                {
                    element1 = element.select("tr").get(i);
                    subject.add(element1.select("td").get(0).text());
                    external.add(element1.select("td").get(1).text());
                    internal.add(element1.select("td").get(2).text());
                    total.add(element1.select("td").get(3).text().trim());
                }
                //student.setTotal("0");
                student.setExternals(external);
                student.setInternals(internal);
                student.setSubjects(subject);
                student.setTotal_subjects(total);
                publishProgress(90);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(fail==1)
            {
                Toast.makeText(getApplicationContext(),"Unable to connect to the server",Toast.LENGTH_LONG).show();
                return;
            }
            else if(fail==2)
            {
                Toast.makeText(getApplicationContext(),"Incorrect USN or results not available",Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                publishProgress(100);
                Intent intent =  new Intent(getApplicationContext(),Results.class);
                intent.putExtra("student",student);
                startActivity(intent);

            }
        }
    }


}
