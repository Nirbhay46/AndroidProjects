package com.example.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import au.com.bytecode.opencsv.CSVWriter;

public class EventActivity extends AppCompatActivity {
    ArrayAdapter arrayAdapter ;
    ListView listView;
    SharedPreferences sharedPreferences;
    SharedPreferences eventSharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> eventmembers = new ArrayList<>();
    ArrayList<String> eventID = new ArrayList<>();
    ArrayList<String > attendance;
    String eventName = "Random";
    AlertDialog alertDialog;
    TextView date;
    TextView time;
    SimpleDateFormat filedateFormat;
    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    java.util.Date date1;
    TextView total;
    TextView name;
    String filedate ="";
    String filetime = "";
    Random rand;

    public static HashMap<String, String> sortByValue(HashMap<String, String> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String> > list =
                new LinkedList<Map.Entry<String, String> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, String> >() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, String> temp = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    public HashMap<String,String> tempMap(){
        HashMap<String,String> NewHashMap = new HashMap<String,String>();
        HashMap<String, String> hm1 = (HashMap<String, String>) sharedPreferences.getAll();
        Iterator hmIterator = hm1.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            String value = ((String) mapElement.getValue());
            System.out.println(mapElement.getKey() + " : " + value);
            String[]temp = value.split("#");
            NewHashMap.put((String) mapElement.getKey(),temp[0]);
        }
        return NewHashMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDone(View view) {
        int count = 0;
        attendance = new ArrayList<>(eventmembers.size());
        for (int i = 0; i < eventmembers.size(); i++) {
            attendance.add("Absent");
        }
        Log.i("Boo", String.valueOf(eventmembers.size())+"\n"+String.valueOf(attendance.size()));
        SparseBooleanArray ro = listView.getCheckedItemPositions();
        Log.i("Boo1", ro.toString());
        if (ro.size() > 0){
            for (int i = 0; i < ro.size(); i++) {
                Log.i("See1", ro.toString());
                Log.i("See2", String.valueOf(ro.valueAt(i)));
                Log.i("See4", String.valueOf(ro.keyAt(i)));
                if(ro.get(ro.keyAt(i))==true){
                    attendance.set(ro.keyAt(i), "Present");
                    Log.i("Working","working");
                }
            }
        }
        try{
            File exportDir = Environment.getExternalStorageDirectory();
            File newdir = new File(exportDir.getAbsolutePath()+"/Attendance Data/");
            Log.i("path",newdir.toString());
            if (!newdir.exists()) {
                newdir.mkdirs();
            }
            File file = new File(newdir, eventName+"("+filedate+").csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                //Headers
                String arrStr1[] ={"Time :"+filetime,"Member Name","Attendance"};
                csvWrite.writeNext(arrStr1);
                for(int i = 0 ; i<eventmembers.size();i++){
                    String arrStr[] ={" ",eventmembers.get(i), attendance.get(i)};
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
            }catch (Exception e){
                Toast.makeText(this, "Internal File Write Exception", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "File Write Exception", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "File Successfully created in Internal Storage", Toast.LENGTH_SHORT).show();
        eventSharedPreferences = getSharedPreferences("sharedPrefsEvent",MODE_PRIVATE);
        editor = eventSharedPreferences.edit();
        count =Integer.parseInt(eventSharedPreferences.getString("count", "0"));
        count++;
        editor.putString(String.valueOf(count),eventName+"    "+"Date : "+filedateFormat.format(date1)+"    "+"Time : "+timeFormat.format(date1));
//        editor.putString(String.valueOf(count),eventName);
        editor.putString("count", String.valueOf(count));
        editor.apply();
        eventmembers.clear();
        eventID.clear();
        attendance.clear();
        Intent intent = new Intent(this, OptionsDisplay.class);
        startActivity(intent);
        Log.i("Preferences",eventSharedPreferences.getAll().toString());
        Log.i("Count", String.valueOf(count));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.newmember:
                final EditText extraMember = new EditText(this);
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Enter the name of the new Member")
                        .setView(extraMember)
                        .setIcon(android.R.drawable.btn_star_big_off)
                        .setPositiveButton("Add member", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int n = rand.nextInt(10000);
                                int i=0;
                                while(i!= sharedPreferences.getAll().size()) {
                                    if(sharedPreferences.contains(String.valueOf(n))) {
                                        n = rand.nextInt(10000);
                                    }
                                    i++;
                                }
                                eventID.add(String.valueOf(n));
                                eventmembers.add(extraMember.getText().toString());
                                arrayAdapter.notifyDataSetChanged();
                                total.setText("Total : "+eventmembers.size());
                                Log.i("New",eventID.toString());
                                Log.i("New",eventmembers.toString());
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

        }
        return  false;
    }
    public void getData(){
        try {
            sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
//            Log.i("HashMap","Before"+tempMap().toString());
            Log.i("HashMap","After"+sortByValue(tempMap()));
            HashMap<String, String> sortedHashMap = sortByValue(tempMap());
            Iterator hmIterator = sortedHashMap.entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) hmIterator.next();
                String value = ((String) mapElement.getValue());
                System.out.println(mapElement.getKey() + " : " + value);
                eventID.add(mapElement.getKey().toString());
                eventmembers.add((String) mapElement.getValue());
            }
            Log.i("List",eventID.toString()+"\n"+eventmembers.toString());
        }catch(Exception e){
            Toast.makeText(this, "Shared Preference Exception", Toast.LENGTH_SHORT).show();
        }
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, eventmembers);
        listView.setAdapter(arrayAdapter);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        final EditText eventName1 = new EditText(this);
        date = findViewById(R.id.date);
        time= findViewById(R.id.time);
        name = findViewById(R.id.eventName);
        total = findViewById(R.id.total);
        rand = new Random();
        setTitle("Event Attendance");
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Enter The Name of this event")
                .setView(eventName1)
                .setIcon(android.R.drawable.btn_star)
                .setPositiveButton("Take Attendance", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (eventName1.getText().toString().equals("")){
                            eventName = "RandomEvent";
                        }else {
                            eventName = eventName1.getText().toString().replaceAll("\\s+","");
                        }
                        name.setText(eventName);
                        total.setText("Total : "+eventmembers.size());
                        date1=new java.util.Date();
                        dateFormat = new SimpleDateFormat("dd/MM/yyy");
                        filedateFormat = new SimpleDateFormat("dd-MM-yyy");
                        timeFormat = new SimpleDateFormat("HH:mm");
                        filedate = filedateFormat.format(date1);
                        filetime = timeFormat.format(date1);
                        date.setText("Date : "+dateFormat.format(date1)+"\n"+"Time : "+timeFormat.format(date1));
                        Log.i("Date",String.valueOf(timeFormat.format(date1)));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), OptionsDisplay.class);
                        startActivity(intent);
                    }
                }).show();
        listView = findViewById(R.id.eventMembers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if(checkedTextView.isChecked()){

                }else if(!checkedTextView.isChecked()) {

                }
            }
        });
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,eventmembers);
        listView.setAdapter(arrayAdapter);
    }
}
