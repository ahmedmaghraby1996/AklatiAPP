package com.example.aklatiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.rt.data.EventHandler;
import com.example.aklatiapp.ui.main.ui.main.Request;

public class MyReceiver extends BroadcastReceiver {


    BackendlessUser user;
String mail;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("mytag", "onReceive: ");
        user = Backendless.UserService.CurrentUser();
        if(!(user==null)){
        EventHandler<Request> orderEventHandler = Backendless.Data.of( Request.class ).rt();




            orderEventHandler.addCreateListener("cookmail="+'\''+user.getEmail()+ '\'',new AsyncCallback<Request>()
        {
            @Override
            public void handleResponse( Request createdOrder )
            {
                Log.d("mytag", "onReceive: ");

                Intent in = new Intent(context,MyIntentService.class);
                context.startService(in);





            }

            @Override
            public void handleFault( BackendlessFault fault )
            {

            }
        } );}
    }
}