package com.example.aklatiapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
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


public class Requests extends Fragment {
ListView lv;
int count=0;
    Request request;
    ArrayList<Request> collect= new ArrayList<>();
RequestAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        lv=view.findViewById(R.id.lvreq);

        BackendlessUser user = Backendless.UserService.CurrentUser();

        EventHandler<Request> orderEventHandler = Backendless.Data.of( Request.class ).rt();


        if(!(user==null)){

        orderEventHandler.addCreateListener( "cookmail = "+'\''+user.getEmail()+ '\'',new AsyncCallback<Request>()
        {
            @Override
            public void handleResponse( Request createdOrder )
            {

                String whereClause = "cookmail =" +'\''+user.getEmail().toString()+'\'' ;
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                queryBuilder.setWhereClause( whereClause ).setPageSize(100).addSortBy("created ASC");

                Backendless.Data.of(Request.class).find(queryBuilder,new AsyncCallback<List<Request>>() {
                    @Override
                    public void handleResponse(List<Request> response) {
                            Collections.reverse(response);
                        collect.clear();
                        for (Request request1 : response) {
                            collect.add(request1);
                        }
                        adapter=new RequestAdapter(getActivity(),response);



                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });

            }

            @Override
            public void handleFault( BackendlessFault fault )
            {

            }
        } );
        String whereClause = "cookmail =" +'\''+user.getEmail().toString()+'\'' ;
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause( whereClause ).setPageSize(100).addSortBy("created ASC");

        Backendless.Data.of(Request.class).find(queryBuilder,new AsyncCallback<List<Request>>() {

            @Override
            public void handleResponse(List<Request> response) {
                Collections.reverse(response);
                collect.clear();
                for (Request request1 : response) {
                    collect.add(request1);}
          adapter=new RequestAdapter(getActivity(),response);



          lv.setAdapter(adapter);



            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });}


            return view;


    }

    public class RequestAdapter extends ArrayAdapter<Request>{
        public RequestAdapter(@NonNull Context context, List<Request> response) {
            super(context,0,response);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Requests.RequestAdapter.ViewHolder holder;
            if (convertView==null) {
                convertView = getLayoutInflater().inflate(R.layout.request_layout, parent, false);

                holder = new Requests.RequestAdapter.ViewHolder();
                holder.content=convertView.findViewById(R.id.content);
                holder.menu=convertView.findViewById(R.id.menu);
                holder.time=convertView.findViewById(R.id.time1);
                holder.order=convertView.findViewById(R.id.order);

                holder.menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(getActivity(), v);
                         popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                             @Override
                             public boolean onMenuItemClick(MenuItem item) {
                                 switch (item.getItemId()) {
                                     case R.id.call:{

                                         Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getItem(position).getPhone()));
                                         startActivity(intent);                                     return true;}
                                     case R.id.note:


                                         AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                         builder1.setMessage(getItem(position).getNote());
                                         builder1.setCancelable(true).show();

                                         return true;
                                     case R.id.address:

                                         AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                         builder2.setMessage(getItem(position).getContent());
                                         builder2.setCancelable(true).show();
                                         return true;

                                     default:
                                         return false;
                             }}
                         });
                        popup.inflate(R.menu.main);
                        popup.show();
                    }
                });
                holder.pic=convertView.findViewById(R.id.rimg);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        {




                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Accept order ? ");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {




                                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                            final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                            builder2.setView(input);
                                            builder2.setTitle("Enter Estimated delivery time in mins ");
                                            builder2.setCancelable(false);

                                            builder2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                        getItem(position).setFtime(input.getText().toString());
                                                    getItem(position).setAproval("accept");
                                                    Backendless.Data.of( Request.class ).save( getItem(position), new AsyncCallback<Request>() {
                                                        @Override
                                                        public void handleResponse(Request response) {
                                                            holder.order.setImageBitmap(null);
                                                            holder.order.setBackground(getResources().getDrawable(R.drawable.ic_check));
                                                            Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void handleFault(BackendlessFault fault) {

                                                        }
                                                    });



                                                    dialog.cancel();
                                                }
                                            }).setNegativeButton("Cancel",null);


                                            AlertDialog alert12 = builder2.create();
                                            alert12.show();

                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            getItem(position).setFtime("");
                                            getItem(position).setAproval("denied");
                                            Backendless.Data.of( Request.class ).save( getItem(position), new AsyncCallback<Request>() {
                                                @Override
                                                public void handleResponse(Request response) {
                                                    holder.order.setImageBitmap(null);
                                                    holder.order.setBackground(getResources().getDrawable(R.drawable.ic_wrong));
                                                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void handleFault(BackendlessFault fault) {

                                                }
                                            });

                                            dialog.cancel();
                                        }
                                    });
                            builder1.setNeutralButton("later",null);

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                });


              convertView.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {
                      {
                          {




                              AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                              builder1.setMessage("Remove order ? ");
                              builder1.setCancelable(true);

                              builder1.setPositiveButton(
                                      "Yes",
                                      new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int id) {

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
                  }
              });
                convertView.setTag(holder);
            }

            else{
                holder= (Requests.RequestAdapter.ViewHolder) convertView.getTag();
            }
            if(getItem(position).getAproval().equals("accept")){
                holder.order.setImageBitmap(null);
            holder.order.setBackground(getResources().getDrawable(R.drawable.ic_check));}
            else if(getItem(position).getAproval().equals("denied")){
                holder.order.setImageBitmap(null);
            holder.order.setBackground(getResources().getDrawable(R.drawable.ic_wrong));}


            holder.time.setText("Order time : "+getItem(position).getCreated().toString().substring(11,16));
            holder.content.setText("Mr "+getItem(position).getName()+" has orderd "+getItem(position).getNumber()+" "+getItem(position).getTitle());

            Picasso.get().load(getItem(position).getPic()).into(holder.pic);


            return convertView;

        }

        class ViewHolder{
            TextView content,time;
            ImageView pic,menu,order;
        }
}}