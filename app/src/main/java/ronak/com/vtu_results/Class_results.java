package ronak.com.vtu_results;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import ronak.com.vtu_results.Adapter.Class_adp;

/**
 * Created by ronak on 03-04-2015.
 */
public class Class_results extends ActionBarActivity {
    ListView listView;
    Class_adp class_adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);
        String class_name = getIntent().getStringExtra("class");
        final ArrayList<Student> arrayList = (ArrayList<Student>) getIntent().getSerializableExtra("array");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.live));
        actionBar.setTitle("Class Results: " + class_name);
        Log.e("check", arrayList.get(1).getName());
        listView = (ListView) findViewById(R.id.class_list);
        Class_adp class_adp = new Class_adp(this, R.layout.class_list_layout, arrayList);
        listView.setAdapter(class_adp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("usn", arrayList.get(position).getUSN());
                intent.putExtra("called", true);
                startActivity(intent);
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);/*
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                class_adp.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                class_adp.getFilter().filter(newText);
                return true;}
        };
        searchView.setOnQueryTextListener(textListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Search");
            EditText editText = new EditText(this);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    class_adp.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            builder.setView(editText);
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
