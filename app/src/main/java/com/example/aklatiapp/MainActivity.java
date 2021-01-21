package com.example.aklatiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends AppCompatActivity implements AsyncCallback<BackendlessUser> {
EditText email,passowrd,name,phone;
CheckBox cook;
    BackendlessUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.regemail);
        passowrd=findViewById(R.id.regpassowrd);
        name=findViewById(R.id.regname);
        phone=findViewById(R.id.regphone);
        cook=findViewById(R.id.cook);
//        Backendless.initApp(this,"52C3B5A5-262E-1101-FF57-D6683AFC2500","D35DFD32-9D27-4B7C-A6AB-68FCE118C85A");

    }

    public void save(View view) {
        if(!(email.getText().toString().equals("")) && !(passowrd.getText().toString().equals("")) && !(phone.getText().toString().equals("")) ){
         user = new BackendlessUser();
        user.setEmail(email.getText().toString());
        user.setPassword(passowrd.getText().toString());
        user.setProperty("name",name.getText().toString());
        user.setProperty("phone",phone.getText().toString());
        if(cook.isChecked())
            user.setProperty("role","cook");
        else user.setProperty("role","client");
        Backendless.UserService.register(user,this);}
        else if (phone.getText().length()<10 && phone.getText().length()<12){
            Toast.makeText(this, "enter correct phone number", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Fill all required data", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void handleResponse(BackendlessUser response) {
        Toast.makeText(this, "register succsess ", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(this,LoginActivity.class);
            in.putExtra("signed",true);
            startActivity(in);
//        if(user.getProperty("role").equals("client")){
//            Intent in = new Intent(this,UserActivity.class);
//        startActivity(in);}
//else{
//            Toast.makeText(this, "cook", Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(this,CookActivity.class);
//            startActivity(in);
//        }
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        if (fault.getCode().equals("3033"))
            Toast.makeText(this, "user exists", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "failed to register", Toast.LENGTH_SHORT).show();
    }
}