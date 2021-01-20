package com.example.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MembersActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> members = new ArrayList<>();
    ArrayList<String> ID=new ArrayList<>();;
    ArrayList<String> phone=new ArrayList<>();
    ArrayList<String> emergency=new ArrayList<>();
    ArrayList<String> memberSince=new ArrayList<>();
    ArrayList<String> birthdate=new ArrayList<>();
    ArrayList<String> profession=new ArrayList<>();
    ArrayList<String> email=new ArrayList<>();
    ArrayList<String> designation=new ArrayList<>();;
    ArrayList<String> address=new ArrayList<>();;
    ArrayList<String> info=new ArrayList<>();;
    static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public void getSharedPrefs(){
        sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
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

        // put data from sorted list to hashzmap
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


    public void getData(){
        try {
            Log.i("HashMap","Before"+tempMap().toString());
            Log.i("HashMap","After"+sortByValue(tempMap()));
            HashMap<String, String> sortedHashMap = sortByValue(tempMap());
            Iterator hmIterator = sortedHashMap.entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) hmIterator.next();
                String value = ((String) mapElement.getValue());
                System.out.println(mapElement.getKey() + " : " + value);
                ID.add(mapElement.getKey().toString());
                members.add((String) mapElement.getValue());
            }
            HashMap<String, String> originalHashMap = (HashMap<String, String>) sharedPreferences.getAll();
            for(int i = 0 ;  i<ID.size();i++){
                String b[] = originalHashMap.get(ID.get(i)).split("#");
                phone.add(b[1]);
                emergency.add(b[2]);
                designation.add(b[6]);
                address.add(b[3]);
                email.add(b[4]);
                birthdate.add(b[5]);
                profession.add(b[7]);
                memberSince.add(b[8]);
                info.add(b[9]);
            }
            Log.i("Final","Original Data  "+originalHashMap.toString());
            Log.i("Final","Converted "+ID.toString() +"\n"+members.toString()+"\n"+designation.toString()+"\n"+address.toString()+"\n"+info.toString());

        }catch(Exception e){
            Toast.makeText(this, "Shared Preference Exception", Toast.LENGTH_SHORT).show();

        }
        Log.i("See",members.toString()+"\n"+phone.toString());

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, members);
        listView.setAdapter(arrayAdapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        listView = findViewById(R.id.membersListView);
        setTitle("MembersActivity");
        getSharedPrefs();
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MembersActivity.this)
                        .setTitle("Member Info")
                        .setMessage("Designation: "+designation.get(position)+"\n\n"
                                +"Profession: "+profession.get(position)+"\n\n"
                                +"Email Address: "+email.get(position)+"\n\n"
                                +"Address: "+address.get(position)+"\n\n"
                                +"Mobile Number: "+phone.get(position)+"\n\n"
                                +"Emergency Contact: "+emergency.get(position)+"\n\n"
                                +"Birth Date: "+birthdate.get(position)+"\n\n"
                                +"Member Since: "+memberSince.get(position)+"\n\n"
                                +"Info: "+info.get(position))
                        .show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                new AlertDialog.Builder(MembersActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure you want to delete this member?")
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("pos",ID.get(position));
                                editor.remove(ID.get(position));
                                editor.commit();
                                ID.remove(position);
                                members.remove(position);
                                info.remove(position);
                                email.remove(position);
                                designation.remove(position);
                                profession.remove(position);
                                birthdate.remove(position);
                                memberSince.remove(position);
                                emergency.remove(position);
                                phone.remove(position);
                                address.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(MembersActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                                Log.i("SharedPref",sharedPreferences.getAll().toString());
                                Log.i("SharedPref",ID.toString());
                                Log.i("SharedPref",members.toString());
                                Log.i("Lists",ID.toString() +"\n"+members.toString()+"\n"+designation.toString()+"\n"+address.toString()+"\n"+info.toString());
                            }
                        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                }).show();

                return true;
            }
        });
    }
}
