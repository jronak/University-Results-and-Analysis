package ronak.com.vtu_results;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ronak on 02-04-2015.
 */
public class Class_room extends ActionBarActivity {

    EditText editText;
    Button button;
    ArrayList<Student> arrayList;
    Download_class download_class;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_layout);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.live));
        editText = (EditText)findViewById(R.id.class_ed);
        button = (Button)findViewById(R.id.getclass);
        setup_progress();
        arrayList = new ArrayList<Student>();

    }

    private void setup_progress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Connecting to the server");
        progressDialog.setMessage("Please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                download_class.cancel(true);
            }
        });
    }

    public void getclass_results(View view)
    {
        String top_link, class_link;
        String cls = editText.getText().toString();
        String pattern = "[1-4]\\w{2}\\d{2}\\w{2}";
        if(cls.matches(pattern))
        {
            arrayList.clear();
            download_class = new Download_class();
            download_class.execute("http://tvamsisai.co/vtu/public/branch/"+cls+"/top");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Bad class usn",Toast.LENGTH_LONG).show();
        }
    }

    public class  Download_class extends AsyncTask<String,Integer,String>{

        String result;
        String class_name;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            result=null;
            HttpURLConnection httpURLConnection;
            URL url;
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader;
            Log.e("Asyc", "Started");
            try {
                url = new URL(params[0]);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK)
                {
                    Log.e("Class","Unreliable response");
                    return null;
                }
                publishProgress(10);
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                line = bufferedReader.readLine();
                result = line;
               // Log.e("Asyc","Read the data");
                httpURLConnection.disconnect();
                inputStreamReader.close();
                bufferedReader.close();
                JSONObject jsonObject = new JSONObject(result);
                int count = Integer.parseInt(jsonObject.getString("count").trim());
                class_name = jsonObject.getString("for");
                JSONArray all = jsonObject.getJSONArray("results");
                if(all.length()==0)
                {
                    result=null;
                   // Toast.makeText(getApplicationContext(),"Invalid Registration number",Toast.LENGTH_LONG).show();
                    return null;
                }
                JSONObject object;
                Student student;
                Log.e("Length","Length is"+all.length());
                for(int i=0; i<all.length(); i++)
                {
                    publishProgress((int)(i*100)/count);
                    student = new Student();
                    object = all.getJSONObject(i);
                    student.setName(object.getString("name"));
                    student.setUSN(object.getString("usn"));
                    student.setTotal(object.getString("total"));
                    student.setResult(object.getString("result"));
                    arrayList.add(student);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
            if(result == null)
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Results not available or incorrect number",Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                progressDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),Class_results.class);
                intent.putExtra("class",class_name);
                intent.putExtra("array",arrayList);
                startActivity(intent);
                return;
            }
        }
    }


}
