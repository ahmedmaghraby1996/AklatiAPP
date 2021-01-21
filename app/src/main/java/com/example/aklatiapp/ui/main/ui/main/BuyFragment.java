package com.example.aklatiapp.ui.main.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.aklatiapp.R;
import com.squareup.picasso.Picasso;


public class BuyFragment extends DialogFragment {

ImageView pic,plus,minus;
TextView descp,time,price,count,note,address;
String m="";
int counter=1;

    public BuyFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.buy_layout, null);
          price=view.findViewById(R.id.bprice);
          address=view.findViewById(R.id.add);
          time=view.findViewById(R.id.prepTime);
          note=view.findViewById(R.id.note);
        descp=view.findViewById(R.id.bdescp);
        pic=view.findViewById(R.id.bpic);
        plus=view.findViewById(R.id.plus);
        minus=view.findViewById(R.id.minus);
        count=view.findViewById(R.id.count);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           counter++;
           count.setText(String.valueOf(counter));
              price.setText("Price : "+(counter*getArguments().getInt("price")+"L.E"));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                if(counter<1)
                    counter=1;
                count.setText(String.valueOf(counter));
                price.setText("Price : "+(counter*getArguments().getInt("price")+"L.E"));
            }
        });


time.setText("Prep time : "+getArguments().getString("time")+" Mins");
descp.setText("Detalis : "+getArguments().getString("descp"));
price.setText("Price : "+getArguments().getInt("price")+" L.E");
        Picasso.get().load(getArguments().getString("pic")).into(pic);


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("title")).setView(view).setPositiveButton("Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                 Request request = new Request();
                BackendlessUser user = Backendless.UserService.CurrentUser();
                request.setUsermail(user.getEmail().toString());
                 request.setNumber(String.valueOf(counter));
                 request.setContent(address.getText().toString());
                 request.setNote(note.getText().toString());
                 request.setCookmail(getArguments().getString("cookmail"));
                 request.setAproval("pending");

                 request.setPhone(user.getProperty("phone").toString());
                 request.setName(user.getProperty("name").toString());
                 request.setPic(getArguments().getString("pic"));
                 request.setTitle(getArguments().getString("title"));
                 request.setCookname(getArguments().getString("cookname"));
                 request.setCookphone(getArguments().getString("cookphone"));
                 request.setNote(note.getText().toString());
                ProgressDialog dialog1 =new ProgressDialog(getActivity());
                dialog1.setTitle("Loading");
                dialog1.show();
                Backendless.Persistence.save(request, new AsyncCallback<Request>() {

                    @Override
                    public void handleResponse(Request response) {

                  dialog1.cancel();


                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });}
        }).setNegativeButton("Cancel",null);
        AlertDialog dialog=builder.create();
        return dialog;
    }




}