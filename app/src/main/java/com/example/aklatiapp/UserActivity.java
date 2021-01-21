package com.example.aklatiapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.aklatiapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

public class UserActivity extends AppCompatActivity implements TextWatcher {
EditText search;
    SharedPreferences pref;
    String email , password;
    SharedPreferences.Editor editor;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        in = new Intent(this, MyReceiver.class);
        PendingIntent pe = PendingIntent.getBroadcast(getApplicationContext(), 0, in, 0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 1000, pe);


        search = findViewById(R.id.search);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
//
//        EventHandler<Request> orderEventHandler = Backendless.Data.of( Request.class ).rt();
//        BackendlessUser user = Backendless.UserService.CurrentUser();

//String whereClause="usermail"+'\''+user.getEmail()+ '\'';
//        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
//        queryBuilder.setWhereClause(whereClause);


//        Backendless.UserService.login(pref.getString("mail",""),pref.getString("pass","") , new AsyncCallback<BackendlessUser>() {
//            @Override
//            public void handleResponse(BackendlessUser response) {
//
//                BackendlessUser user = Backendless.UserService.CurrentUser();
//                Toast.makeText(UserActivity.this, "Log in success", Toast.LENGTH_SHORT).show();
//                if(user.getProperty("role").equals("cook")){
//
//                    Intent in = new Intent(UserActivity.this,CookActivity.class);
//                    startActivity(in);
//                }
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Intent in = new Intent(UserActivity.this,LoginActivity.class);
//                startActivity(in);
//            }
//        });


        search.addTextChangedListener(this);
//        FloatingActionButton fab = findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

}


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Searched searched= new Searched();
        searched.find=search.getText().toString();
        EventBus.getDefault().post(searched);
    }



    public void logout(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Intent in = new Intent(UserActivity.this,LoginActivity.class);
                        in.putExtra("logged",false);
                        startActivity(in);


                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });


                return true;
            }

        });
        popup.inflate(R.menu.main2);
        popup.show();
    }

    static class Searched{
        public String find;
    }

    @Override
    public void onBackPressed() {


        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }







}