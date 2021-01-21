package com.example.aklatiapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.aklatiapp.ui.main.SectionsPagerAdapter1;
import com.google.android.material.tabs.TabLayout;

public class CookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        SectionsPagerAdapter1 sectionsPagerAdapter1 = new SectionsPagerAdapter1(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter1);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Intent in=new Intent(this,MyReceiver.class);
        PendingIntent pe=PendingIntent.getBroadcast(getApplicationContext(),0,in,0);

        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);

        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),1000,pe);

    }


    @Override
    public void onBackPressed() {


        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void logout(View view) {

        {

            PopupMenu popup = new PopupMenu(this, view);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    Backendless.UserService.logout(new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            Intent in = new Intent(CookActivity.this,LoginActivity.class);
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
        }}}


//        FloatingActionButton fab = findViewById(R.id.fab);

//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(CookActivity.this,DetailsActivity.class);
//                startActivity(in);
//            }
//        });
//    }}
//}}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//return true;
//    }
//
//    public void meal(MenuItem item) {
//        Intent in = new Intent(this,DetailsActivity.class);
//        startActivity(in);
//    }
//}