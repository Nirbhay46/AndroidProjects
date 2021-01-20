package com.example.attendance;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginbtn;
    TextView changeview;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                }
            }else{
                Toast.makeText(this, "Kindly provide GPS", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void change(View view){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText oldpass = new EditText(this);
        oldpass.setHint("Old Password");
        layout.addView(oldpass);
        final EditText newpass = new EditText(this);
        newpass.setHint("New Password");
        layout.addView(newpass);
        new AlertDialog.Builder(this)
                .setTitle("Change Password.")
                .setMessage("Enter your Password")
                .setView(layout)
                .setIcon(android.R.drawable.btn_dialog)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(oldpass.getText().toString().equals(sharedPreferences.getString("password","a*gh5434.||"))){
                            editor.putString("password",newpass.getText().toString());
                            editor.commit();
                            Log.i("See2",sharedPreferences.getAll().toString());
                            Toast.makeText(MainActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();


    }
    public void btnOnClick(View view){
        if(username.getText().toString().equals("TORCH BEARERS")&&password.getText().toString().equals(sharedPreferences.getString("password",null))){
            Intent intent = new Intent(this, OptionsDisplay.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        username = findViewById(R.id.username);
        username.setText("TORCH BEARERS");
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        changeview = findViewById(R.id.changePassword);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //asking for permission in this IF
        }
        sharedPreferences = getSharedPreferences("sharedPrefsAuth",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Log.i("See","Before"+sharedPreferences.getAll().toString());
        if(sharedPreferences.getAll().size()==0) {
            editor.putString("password", "password");
            editor.commit();
        }
        SharedPreferences sharedPreferences2 = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor2 = sharedPreferences2.edit();
        if(sharedPreferences2.getAll().size()==0){
            sharedEditor2.putString("1","Rtr. Kruttika#9920407437#9819680595#A-25/403, Happy Valley, Chitalsar Road#k17a90@yahoo.com#4/17/1990#Joint Secretary#Patent analyst#8/15/2017#null");
            sharedEditor2.putString("2","Rtr. Arun Nair#7738423962#25385940#204, Om Sai Ganesh, besides St. Anthony church, Vikram Nagar, Kalwa(W)#rtr.arunn.rctn@gmail.com#2/23/1997#GBM#-#4/24/2016#null");
            sharedEditor2.putString("3","Rtr. Sejal Bansal#9930212061#9930212061#66/A/2, Vrindavan Society, Thane(W)#rtrsejalbansal@gmail.com#6/26/1997#General Body Member#Student#1/14/2017#null");
            sharedEditor2.putString("4","Rtr. Twinkle Rana#9769363429#9870428198#5/42, Ashish-A wing, Aban park, Dhokali Naka, Kolshet road, Thane-west#twinklerana09@gmail.com#11/24/1997#Secretary#Sales executive#07/11/2018#null");
            sharedEditor2.putString("5","Rtr. Sapan Agarwal#9867023670#9867023670#4/404, Tulsidham, G.B. Road#sapanagarwal96@gmail.com#8/17/1996#GB member#Junior Business Analyst#8/30/2015#null");
            sharedEditor2.putString("6","Rtr. Mithila Salvi#9870439221#9833319029#B-401, Shree Pooja CHS, near Vijay Nagari, Kavesar, Ghodbander Road, Waghbil, Thane West 400610#mithilasalvi2@gmail.com#10/24/1989#Vice President#Ecommerce Manager at The Mommy Collective#06/01/2016#null");
            sharedEditor2.putString("7","Rtr. Mahanand Vishwakarma#9892678476#9892678476#Room 30 & 31, Opp Hanuman Temple, Old Agra Road, Gokul Nagar, Thane (w) - 400601#rtr.mahi01@gmail.com#34338#IPP#CA - Student#9/22/2013#null");
            sharedEditor2.putString("8","Rtr. Branden Drego#9757191769#9833389565#202, Vinod Apartment. 326 Edulji Road, Thane (W), 400601#drego.brandy@gmail.com#2/23/1996#GBM#-#03/10/2014#null");
            sharedEditor2.putString("9","Rtr. Ishanee Pophale#9833860290#9869524231#Runwal plaza#ishanee.pophale@gmail.com#6/27/1996#GB member#Student#07/01/2015#null");
            sharedEditor2.putString("10","Rtr. Suyash Khanvilkar#8369658272#8369658272#303/Rose-21, Highland annexe, opposite swad hotel, Thane west#khanvilkarsuyash1234@gmail.com#10/13/1998#Club service director#Student#08/02/2018#null");
            sharedEditor2.putString("11","Rtr. Khushabu Agarwal#8767996734#9322360916#Voltas employees vasant vihar#khushabuagarwal2910@gmail.com#10/28/1998#Sports Director#student#12/28/2018#null");
            sharedEditor2.putString("12","Rtr. Gopalakrishnan Vilwadri#8928500105#25814618#A-14,Om Sadguru Apartments,Sector-7,#rtr.gopalv@gmail.com#8/13/1991#Treasurer#Human Resources#10/31/2015#null");
            sharedEditor2.putString("13","Rtr. Prabhat Pandey#9867079340#9870849954#101, Mishra Chawl, Ramnagar, Wagle Estate, Thane (w) 400604#pprabhat245@gmail.com#5/24/1995#Digital Communications Director#ORM#08/04/2018#null");
            sharedEditor2.putString("14","Rtr. Sanika#88508852007#8850852007#C/602,laxmi residency opposite aplab near mulund checknaka thane west 400604#sanika2612@gmail.com#12/26/1999#General body member#Medical#07/01/2017#null");
            sharedEditor2.putString("15","Rtr. Surabhi tapre#9930354101#9773607791#A2/601, Vedant complex , Vartak Nagar, Thane(w)#tsurabhi97@gmail.com#7/24/1997#GB member#Persuing LLB#07/12/2018#null");
            sharedEditor2.putString("16","Rtr. Akshita changan#9619092543#9619092543#202,A wing Sai Vatsalya Panchpakhadi near nitin co.Thane west#akshitachangan25@gmail.com#7/25/2000#Community service director#Studying#01/05/2019#null");
            sharedEditor2.putString("17","Rtr. Girish Pillai#8879066010#8104913109#Shree laxmi park, Phase-1, C-3, A wing, 101, Lokmanya Nagar, Thane-W#girishpillai239@gmail.com#2/17/1996#SAA#Software Developer#04/01/2016#null");
            sharedEditor2.putString("18","Rtr. Abhishek Pandey#8652436899#9820736127#Church Road, Ganesh Nagar, Manpada#abhishekpandey8686@gmail.com#7/20/1997#ISD#Engineer#6/19/2016#null");
            sharedEditor2.putString("19","Rtr. Kinjal#8291434410#8928133181#88/A/1 , Vrindavan Society Thane West#rtrkinjalgandhi@gmail.com#8/29/1997#Editor#Teacher#12/03/2017#null");
            sharedEditor2.putString("20","Rtr. Ramaswamy Iyer#9920247772#9920247773#401, A/2, Anand Savli Residency#ramos311995@gmail.com#8/31/1995#General Body Member#Post Graduate Student#10/01/2018#null");
            sharedEditor2.putString("21","Rtr. Pooja#7506101964#9769527051#D-403, Matoshree Appartment, Sawarkar Nagar, Thane West. 400606#dspraturi1234@gmail.com#6/26/1997#Entrepreneurship Development Director#Student#07/01/2019#null");
            sharedEditor2.apply();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
