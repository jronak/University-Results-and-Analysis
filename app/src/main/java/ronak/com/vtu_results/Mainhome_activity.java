package ronak.com.vtu_results;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by ronak on 01-04-2015.
 */
public class Mainhome_activity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainhome);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Internet Required")
                    .setMessage("To download the results, your phone should be connected to the internet")
                    .setPositiveButton("Go to Settings",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            builder.show();
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.live));
        actionBar.setTitle("Results & Analysis");
    }

    public void liveresults(View view){
        startActivity(new Intent(this,Home.class));
    }

    public void schedule_check(View view){

        startActivity(new Intent(this, Schedule.class));
    }

    public void class_results(View view){
        startActivity(new Intent(this,Class_room.class));
    }

    public void analyze(View view){

        Intent intent = new Intent(getApplicationContext(),Home.class);
        intent.putExtra("analysis",true);
        startActivity(intent);

    }

}
