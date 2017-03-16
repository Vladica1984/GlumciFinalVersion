package com.example.alen.glumcifinalverzion.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alen.glumcifinalverzion.R;
import com.example.alen.glumcifinalverzion.model.Glumci;
import com.example.alen.glumcifinalverzion.model.ORMliteHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ORMliteHelper databaseHelper;
    public static String ACTOR_KEY = "ACTOR_KEY";
    public static String TOAST = "notif_toast";
    public static String NOTIFICATION = "notif_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.lv_listaGlumaca);

        try {
            List<Glumci> list = getDatabaseHelper().getGlumciDao().queryForAll();
            ListAdapter adapter = new ArrayAdapter<>(FirstActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Glumci glumci = (Glumci) listView.getItemAtPosition(position);
                    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                    intent.putExtra(ACTOR_KEY, glumci.getIdGlumci());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:

                final Dialog dialog = new Dialog(FirstActivity.this);
                dialog.setTitle("Osnovni podaci o glumcu");
                dialog.setContentView(R.layout.dialog_glumci);

                Button ok = (Button) dialog.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ime = (EditText) dialog.findViewById(R.id.et_ime);
                        EditText bio = (EditText) dialog.findViewById(R.id.et_bio);
                        EditText ocena = (EditText) dialog.findViewById(R.id.et_ocena);
                        EditText datum = (EditText) dialog.findViewById(R.id.et_datum);

                        Glumci g = new Glumci();
                        g.setImeGlumci(ime.getText().toString());

                        if (g.getImeGlumci().equals("")) {
                            g.setImeGlumci("No name glumac");
                            Toast.makeText(FirstActivity.this, "Niste uneli ime glumca", Toast.LENGTH_SHORT).show();
                        }

                        g.setBioGlumcmi(bio.getText().toString());
                        g.setOcenaGlumci(ocena.getText().toString());
                        g.setDatum(datum.getText().toString());

                        try {
                            getDatabaseHelper().getGlumciDao().create(g);
                            showMessage("Glumac dodat na listu");
                            refresh();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                final Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.action_about:
                AlertDialog.Builder aboutdialog = new AlertDialog.Builder(FirstActivity.this);
                aboutdialog.setIcon(R.drawable.ic_action_about);
                aboutdialog.setTitle("Autor aplikacije");
                aboutdialog.setMessage("Vladimir Popovic, 15.03.2017");
                aboutdialog.setCancelable(false);

                aboutdialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                aboutdialog.show();
                break;
            case R.id.action_settings:
                startActivity(new Intent(FirstActivity.this, SettingActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public ORMliteHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMliteHelper.class);
        }
        return databaseHelper;
    }

    void refresh() {
        ListView listview = (ListView)findViewById(R.id.lv_listaGlumaca);

        if (listview != null) {

            ArrayAdapter<Glumci> adapter = (ArrayAdapter<Glumci>) listview.getAdapter();

            if (adapter != null) {
                try {
                    adapter.clear();
                    List<Glumci> list = getDatabaseHelper().getGlumciDao().queryForAll();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void  showMessage(String message) {
        boolean toast = preferences.getBoolean(TOAST, false);
        boolean notification = preferences.getBoolean(NOTIFICATION, false);

        if (toast) {
            Toast.makeText(FirstActivity.this, message, Toast.LENGTH_SHORT).show();
        }
        if (notification) {
            statusMessage(message);
        }
    }

    private void statusMessage(String message){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notif_icon);
        builder.setContentTitle("pripremni test");
        builder.setContentText(message);
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}

