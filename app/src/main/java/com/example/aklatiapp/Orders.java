package com.example.aklatiapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.rt.data.EventHandler;
import com.example.aklatiapp.ui.main.ui.main.Request;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Orders extends Fragment {
ListView lv ;
    ArrayList<Request> collect= new ArrayList<>();
OrderAdapter adapter;

    public Orders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        lv=view.findViewById(R.id.lvorder);


        BackendlessUser user = Backendless.UserService.CurrentUser();

        if(!(user==null)) {

            EventHandler<Request> orderEventHandler = Backendless.Data.of(Request.class).rt();


            orderEventHandler.addCreateListener("usermail=" + '\'' + user.getEmail() + '\'', new AsyncCallback<Request>() {
                @Override
                public void handleResponse(Request createdOrder) {
                    String whereClause = "usermail =" + '\'' + user.getEmail().toString() + '\'';
                    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                    queryBuilder.setWhereClause(whereClause).setPageSize(100).addSortBy("created DSC");

                    Backendless.Data.of(Request.class).find(queryBuilder, new AsyncCallback<List<Request>>() {
                        @Override
                        public void handleResponse(List<Request> response) {
                            Collections.reverse(response);
                            adapter = new OrderAdapter(getActivity(), response);
                                collect.clear();
                            for (Request request : response) {
                                collect.add(request);
                            }
                            lv.setAdapter(adapter);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
            String whereClause = "usermail =" + '\'' + user.getEmail().toString() + '\'';
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(whereClause).addSortBy("created DSC").setPageSize(100);

            Backendless.Data.of(Request.class).find(queryBuilder, new AsyncCallback<List<Request>>() {
                @Override
                public void handleResponse(List<Request> response) {
                    Collections.reverse(response);

                    adapter = new OrderAdapter(getActivity(), response);

                    for (Request request : response) {
                        collect.add(request);
                    }
                    lv.setAdapter(adapter);
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }






        return view;


    }


    public class OrderAdapter extends ArrayAdapter<Request> {
        public OrderAdapter(@NonNull Context context, List<Request> response) {
            super(context,0,response);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

           OrderAdapter.ViewHolder holder;
            if (convertView==null) {
                convertView = getLayoutInflater().inflate(R.layout.request_layout, parent, false);

                holder = new OrderAdapter.ViewHolder();
                holder.content=convertView.findViewById(R.id.content);
                holder.call=convertView.findViewById(R.id.menu);
                holder.time=convertView.findViewById(R.id.time1);
                holder.order=convertView.findViewById(R.id.order);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(getItem(position).getAproval().equals("pending") || getItem(position).getAproval().equals("denied") )
                        Toast.makeText(getActivity(),"order "+ getItem(position).getAproval(), Toast.LENGTH_SHORT).show();
                        else if (getItem(position).getAproval().equals("accept")){

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Your Order will arrive after "+getItem(position).getFtime()+" mins starting from "+getItem(position).getUpdated().toString().substring(11,16));
                            builder1.setCancelable(true);

                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                    }
                });
                holder.call.setImageBitmap(null);
                holder.call.setBackground(getResources().getDrawable(R.drawable.ic_call));
                holder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getItem(position).getCookphone()));
                        startActivity(intent);
                    }
                });
                holder.pic=convertView.findViewById(R.id.rimg);
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        {
                            {
                                {




                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setMessage("Remove order ? ");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    getItem(position).setAproval("accept");
                                                    Backendless.Data.of( Request.class ).remove( getItem(position), new AsyncCallback<Long>() {
                                                        @Override
                                                        public void handleResponse(Long response) {
                                                            Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
                                                            collect.remove(getItem(position));
                                                            adapter.clear();
                                                            adapter.addAll(collect);
                                                        }

                                                        @Override
                                                        public void handleFault(BackendlessFault fault) {
                                                            Toast.makeText(getActivity(), "error during remove", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });



                                                    dialog.cancel();
                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "No",null);



                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                }
                            }
                            return true;

                    }}
                });

                convertView.setTag(holder);
            }

            else{
                holder= (OrderAdapter.ViewHolder) convertView.getTag();
            }

            holder.time.setText("Order time : "+getItem(position).getCreated().toString().substring(11,16));

            holder.content.setText("You orderd "+getItem(position).getNumber()+" "+getItem(position).getTitle()+" from Chief "+getItem(position).getCookname());
            if(getItem(position).getAproval().equals("accept")){
                holder.order.setImageBitmap(null);
            holder.order.setBackground(getResources().getDrawable(R.drawable.ic_check));}

            else if(getItem(position).getAproval().equals("denied")){
                holder.order.setImageBitmap(null);
            holder.order.setBackground(getResources().getDrawable(R.drawable.ic_wrong));}
            Picasso.get().load(getItem(position).getPic()).into(holder.pic);


            return convertView;

        }

        class ViewHolder{
            TextView content,time;
            ImageView pic,order,call;
        }
}}