package com.example.aklatiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {
EditText email,passowrd;
CheckBox logged;
Boolean loggedin;
Button sign,log;

    SharedPreferences pref;
    ConstraintLayout layout;
    String mail,pass;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        email = findViewById(R.id.email);
        passowrd = findViewById(R.id.password);
getSupportActionBar().hide();
        layout=findViewById(R.id.layout);
sign=findViewById(R.id.sign);
log=findViewById(R.id.log);
        pref=getPreferences(MODE_PRIVATE);
        if(getIntent().getBooleanExtra("signed",false)==true)
            sign.setEnabled(false);
        if(getIntent().getBooleanExtra("logged",true)) {

            sign.setVisibility(View.INVISIBLE);
            log.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            passowrd.setVisibility(View.INVISIBLE);
            layout.setBackground(getResources().getDrawable(R.drawable.ic_main_background));
        }
        else {
            editor = pref.edit();
            editor.putString("mail", "");
            editor.putString("pass", "");

            editor.apply();

            getSupportActionBar().show();
        }


        Backendless.initApp(this, "7BCAF38E-FB20-2EE1-FF67-DF363D884800", "FB12F53F-08F0-410B-A119-B6D774BD2D4B");






if(getIntent().getBooleanExtra("logged",true)) {


    Backendless.UserService.login(pref.getString("mail", ""), pref.getString("pass", ""), new AsyncCallback<BackendlessUser>() {
        @Override
        public void handleResponse(BackendlessUser response) {

            BackendlessUser user = Backendless.UserService.CurrentUser();

            if (user.getProperty("role").equals("client")) {

                Intent in = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(in);
            } else {

                Intent in = new Intent(LoginActivity.this, CookActivity.class);
                startActivity(in);
            }
        }

        @Override
        public void handleFault(BackendlessFault fault) {
            sign.setVisibility(View.VISIBLE);
            log.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            passowrd.setVisibility(View.VISIBLE);
getSupportActionBar().show();
            layout.setBackgroundColor(Color.argb(0,0,0,0));
        }
    });
}
    }

    public void signup(View view) {
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }


    public void save(View view) {

Backendless.UserService.login(email.getText().toString(), passowrd.getText().toString(), new AsyncCallback<BackendlessUser>() {
    @Override
    public void handleResponse(BackendlessUser response) {


        BackendlessUser user = Backendless.UserService.CurrentUser();



            mail = email.getText().toString();
            pass = passowrd.getText().toString();
        editor = pref.edit();
            editor.putString("mail", mail);
            editor.putString("pass", pass);

            editor.apply();


        if(user.getProperty("role").equals("client")){

            Intent in = new Intent(LoginActivity.this,UserActivity.class);

            startActivity( in);}
        else{

            Intent in = new Intent(LoginActivity.this,CookActivity.class);
            startActivity(in);
        }
    }

    @Override
    public void handleFault(BackendlessFault fault) {

        if (fault.getCode().equals("3033"))
            Toast.makeText(LoginActivity.this, "user exists", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(LoginActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
    }
});

    }

}