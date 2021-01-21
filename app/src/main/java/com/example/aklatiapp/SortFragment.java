package com.example.aklatiapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.greenrobot.eventbus.EventBus;



public class SortFragment extends DialogFragment {

 Spinner type,cost;

    public SortFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sort_layout, null);
        type=view.findViewById(R.id.type);
        cost=view.findViewById(R.id.cost);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Sort").setView(view).setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
Message m= new Message();
m.first=type.getSelectedItem().toString();
m.second=cost.getSelectedItem().toString();
                EventBus.getDefault().post(m);
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

        return dialog;

    }

public static  class Message{
        public String first;
        public String second;
}
}

