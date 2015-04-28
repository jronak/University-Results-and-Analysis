package ronak.com.vtu_results;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by ronak on 01-04-2015.
 */
public class Background_servcice extends Service {
    String usn;
    Boolean status=false;
    Student student;
    Intent main_intent;
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        usn = intent.getStringExtra("usn");
        this.main_intent = intent;
        student = new Student();
        String link = "http://results.vtu.ac.in/vitavi.php?rid="+usn+"&submit=SUBMIT";
        Vtu_download vtu_download = new Vtu_download();
        vtu_download.execute(link);
        return super.onStartCommand(intent,flags,startId);
    }

    public class Vtu_download extends AsyncTask<String,Integer,String>
    {



        @Override
        protected String doInBackground(String... s) {
            Document document = null;
            String url = s[0];
            try {
                document = Jsoup.connect(url).timeout(10000).get();
                if(document==null)
                {
                    status = false;
                    return null;
                }
                Element element = null;
                element = document.select("table").get(16);
                if(element.text().contains("Results are not yet available "))
                {
                    status = false;
                    return null;
                }
                status = true;
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

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(status == false)
            {

                status = false;
                PendingIntent pendingIntent2= PendingIntent.getService(getApplicationContext(),1000,main_intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Log.e("Fail","Repeating");
                alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+15*60*1000,pendingIntent2);
            }
            else
            {

                Intent intent1 = new Intent(getApplicationContext(),Results.class);
                intent1.putExtra("student",student);
                PendingIntent pendingIntent =  PendingIntent.getActivity(getApplicationContext(),0,intent1,0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Results & Analysis")
                        .setContentText("Result is available")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .build();
                notificationManager.notify(10,notification);
                SharedPreferences.Editor editor = getSharedPreferences("USN",MODE_PRIVATE).edit();
                editor.putString("done","1");
                editor.commit();

            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
