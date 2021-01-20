package com.example.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class OptionsDisplay extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Random rand;
    int count;
    boolean first = true;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.displaymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case (R.id.logout):
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            case(R.id.about):
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("About Developer")
                        .setMessage("Name : Nirbhay Narkhede\nContact Email : nirbhay4646@gmail.com")
                        .show();

        }
        return false;
    }

    public void showBtnClick(View view){
        Intent intent = new Intent(this, MembersActivity.class);
        startActivity(intent);

    }
    public void viewAttendance(View view){
        Intent intent = new Intent(this, ViewAttendanceActivity.class);
        startActivity(intent);

    }
    public void AddBtnClick(View view){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Add a new Member");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText memberName = new EditText(this);
        memberName.setHint("Name");
        layout.addView(memberName);
        final EditText phone = new EditText(this);
        phone.setHint("Mobile Number");
        layout.addView(phone);
        final EditText emergencyContact = new EditText(this);
        emergencyContact.setHint("Emergency Contact Number");
        layout.addView(emergencyContact);
        final EditText birthdate = new EditText(this);
        birthdate.setHint("Birthday :- dd/mm/yyyy");
        layout.addView(birthdate);
        final EditText Email = new EditText(this);
        Email.setHint("Email Address");
        layout.addView(Email);
        final EditText address = new EditText(this);
        address.setHint("Address");
        layout.addView(address);
        final EditText designation = new EditText(this);
        designation.setHint("Designation");
        layout.addView(designation);
        final EditText Profession = new EditText(this);
        Profession.setHint("Profession");
        layout.addView(Profession);
        final EditText memberSince = new EditText(this);
        memberSince.setHint("Member Since");
        layout.addView(memberSince);
        final EditText Info = new EditText(this);
        Info.setHint("Info");
        layout.addView(Info);
        alertDialog.setView(layout);
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int n = rand.nextInt(10000);;
                int i=0;
                while(i!= sharedPreferences.getAll().size()) {
                    if (sharedPreferences.contains(String.valueOf(n))) {
                        n = rand.nextInt(10000);
                    }
                    i++;
                }
                if(phone.getText().toString().isEmpty()==true){
                    phone.setText("Null");
                }
                if(address.getText().toString().isEmpty()==true) {
                    address.setText("Null");
                }
                if(designation.getText().toString().isEmpty()==true) {
                    designation.setText("Null");
                }
                if(Info.getText().toString().isEmpty()==true) {
                    Info.setText("Null");
                }
                if(memberName.getText().toString().isEmpty()==true){
                    memberName.setText("Null");
                }
                if(Profession.getText().toString().isEmpty()==true){
                    Profession.setText("Null");
                }
                if(Email.getText().toString().isEmpty()==true){
                    Email.setText("Null");
                }
                if(birthdate.getText().toString().isEmpty()==true){
                    birthdate.setText("Null");
                }
                if(memberSince.getText().toString().isEmpty()==true){
                    memberSince.setText("Null");
                }
                if(emergencyContact.getText().toString().isEmpty()==true){
                    emergencyContact.setText("Null");
                }

                char firstchar = Character.toUpperCase(memberName.getText().toString().charAt(0));
                String lstname = firstchar+memberName.getText().toString().substring(1);
                Log.i("Sample",designation.getText().toString());
                Log.i("Sample1",phone.getText().toString());
                Log.i("Sample2",address.getText().toString());
                Log.i("Sample3",Info.getText().toString());

                Log.i("Name",lstname);
                editor.putString(String.valueOf(n),lstname +"#"
                        +phone.getText().toString()+"#"
                        +emergencyContact.getText().toString()+"#"
                        +address.getText().toString()+ "#"
                        +Email.getText().toString()+ "#"
                        +birthdate.getText().toString()+ "#"
                        +designation.getText().toString()+"#"
                        +Profession.getText().toString()+ "#"
                        +memberSince.getText().toString()+ "#"
                        +Info.getText().toString());
                Log.i("Shared",sharedPreferences.getAll().toString());
                editor.commit();
                Log.i("Shared",sharedPreferences.getAll().toString());
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    public void eventBtnClick(View view){
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);

    }
    public void clearBtn(View view){
        new AlertDialog.Builder(this)
                .setTitle("This will erase all the MembersActivity.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            if(sharedPreferences.getAll() !=null) {
                                editor.clear();
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "Member List Cleared", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Unable to clear", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_display);
        rand= new Random();
//        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, OptionsDisplay.class);
        startActivity(intent);
    }
}
