package com.example.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ViewAttendanceActivity extends AppCompatActivity {
    SharedPreferences eventSharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> allevents = new ArrayList<String>();
    ArrayList<String> alleventKeys = new ArrayList<String>();
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case (R.id.clear):
                editor.clear();
                editor.putString("count","0");
                editor.commit();
                allevents.clear();
                arrayAdapter.notifyDataSetChanged();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        setTitle("View Attendance");
        eventSharedPreferences = getSharedPreferences("sharedPrefsEvent",MODE_PRIVATE);
        editor = eventSharedPreferences.edit();
        listView = findViewById(R.id.attendanceListView);
        final Map<String ,String> events = (Map<String, String>) eventSharedPreferences.getAll();
        for(int i = 1 ; i < events.size() ; i++){
            if(events.containsKey(String.valueOf(i))){
                alleventKeys.add(String.valueOf(i));
                allevents.add(events.get(String.valueOf(i)));
            }
        }
        Log.i("Loop",allevents.toString()+"\n"+alleventKeys.toString());
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, allevents);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp[] = allevents.get(position).split(" ");
                String filename = temp[0]+"("+temp[6]+").csv";
                Log.i("filename",filename);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Attendance Data/"+filename);
                Log.i("Exists", String.valueOf(file.exists()));
                Uri path = FileProvider.getUriForFile(ViewAttendanceActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",file);
                Intent openFile = new Intent(Intent.ACTION_VIEW);
                openFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openFile.setDataAndType(path,null);
                try {
                    startActivity(openFile);
                }catch (Exception e){
                    Toast.makeText(ViewAttendanceActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                Log.i("Path",file.toString());
                Log.i("Path1",path.getPath());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ViewAttendanceActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete This Event")
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int tempcount =Integer.parseInt(eventSharedPreferences.getString("count","0"));
                                editor.remove(alleventKeys.get(position));
                                tempcount--;
                                editor.putString("count", String.valueOf(tempcount));
                                editor.apply();
                                allevents.remove(position);
                                alleventKeys.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                Log.i("Preferences1",eventSharedPreferences.getAll().toString());

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
                return true;

            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.i("Preferences2",eventSharedPreferences.getAll().toString());
        super.onBackPressed();
    }
}
