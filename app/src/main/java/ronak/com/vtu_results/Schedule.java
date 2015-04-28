package ronak.com.vtu_results;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by ronak on 01-04-2015.
 */


public class Schedule extends ActionBarActivity {

    TextView tv;
    Button button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String usn;
    private final String file = "USN";
    static PendingIntent pendingIntent;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.live));
        tv = (TextView) findViewById(R.id.sch_tv);
        button = (Button) findViewById(R.id.sch_button);
        sharedPreferences = getSharedPreferences(file, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if(sharedPreferences.getString("done",null)!=null)
        {
            editor.putString("usn",null);
            editor.putString("done",null);
            editor.commit();
        }
        usn = sharedPreferences.getString("usn",null);
        if(usn == null)
        {
            tv.setText("Automatic Results checking is currently disabled\nIf enabled, the university number is stored and the result is checked periodically. If the result is available notification will be issued");
            button.setText("Enable");
        }
        else
        {
            tv.setText("Automatic Results check is active for: "+usn);
            button.setText("Disable");
        }
    }

    public void add_sch(View view)
    {

        usn = sharedPreferences.getString("usn",null);
        if(usn==null)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Enable Automatic Check")
                    .setMessage("Results will be checked every hour in the background. Once results are available you will be notified");
            final EditText editText = new EditText(this);
            editText.setHint("Enter the university number");
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            editText.setLayoutParams(lp);
            editText.setAllCaps(true);
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setGravity(Gravity.CENTER);
            alertDialog.setView(editText);
            alertDialog.setPositiveButton("Enable",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String s = editText.getText().toString().trim();
                    String pattern = "[1-4]\\w{2}\\d{2}\\w{2}\\d{3}";
                    if(s.matches(pattern))
                    {
                        editor.putString("usn",s);
                        editor.commit();
                        button.setText("Disable");
                        tv.setText("Automatic Results check is active: "+s);
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(),Background_servcice.class);
                        intent.putExtra("usn",s);
                        pendingIntent = PendingIntent.getService(getApplicationContext(),1000,intent,0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+15*60*000,pendingIntent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Bad USN",Toast.LENGTH_LONG).show();
                    }
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();

        }
        else
        {
            alarmManager.cancel(pendingIntent);
            editor.putString("usn",null);
            editor.commit();
            button.setText("Enable");
            tv.setText("Automatic Results check currently not in use");
        }
    }
}
