package com.example.aklatiapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.aklatiapp.ui.main.ui.main.BuyFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class Meals extends Fragment {
    FloatingActionButton fab ;
ListView lv;
String search;

    FoodAdapter adapter;
    FloatingTextButton sort;

    ArrayList<Food> collect=new ArrayList();
    ArrayList<Food> collect1=new ArrayList();
    String type="",cost="";

    public Meals() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_meals, container, false);
ProgressDialog dialog =new ProgressDialog(getActivity());
dialog.setTitle("Loading");
dialog.show();
//        cost=view.findViewById(R.id.cost);
//        type=view.findViewById(R.id.type);
        EventBus.getDefault().register(this);
        sort=view.findViewById(R.id.sorts);
        fab=view.findViewById(R.id.fab);

   sort.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           SortFragment fragment = new SortFragment();
           fragment.show(getFragmentManager(),"");
       }
   });
        if(getActivity() instanceof UserActivity){
        fab.setVisibility(View.INVISIBLE);}
        if(getActivity() instanceof CookActivity) {

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getActivity(), DetailsActivity.class);
                    startActivity(in);
                }
            });
        }




        lv=view.findViewById(R.id.lv);

        if(getActivity() instanceof CookActivity){


            BackendlessUser user = Backendless.UserService.CurrentUser();
            if(!(user==null)){
            EventHandler<Food> orderEventHandler = Backendless.Data.of( Food.class ).rt();




            orderEventHandler.addCreateListener( "cookmail="+'\''+user.getEmail()+ '\'',new AsyncCallback<Food>()
            {
                @Override
                public void handleResponse( Food createdOrder )
                {
                    String whereClause = "cookmail =" +'\''+user.getEmail().toString()+'\'' ;
                    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                    queryBuilder.setWhereClause( whereClause ).setPageSize(100);

                    Backendless.Data.of(Food.class).find(queryBuilder,new AsyncCallback<List<Food>>() {
                        @Override
                        public void handleResponse(List<Food> response) {

                            adapter.clear();
                            collect.clear();
                            collect1.clear();
                            for (Food food : response) {
                                collect.add(food);
                                collect1.add(food);
                            }
                            adapter= new FoodAdapter(getActivity(),collect1);
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
            queryBuilder.setWhereClause( whereClause ).setPageSize(100);

            Backendless.Data.of(Food.class).find(queryBuilder,new AsyncCallback<List<Food>>() {
                @Override
                public void handleResponse(List<Food> response) {
                    dialog.cancel();


                    for (Food food : response) {
                        collect.add(food);
                        collect1.add(food);
                    }
                    adapter= new FoodAdapter(getActivity(),collect1);
                    lv.setAdapter(adapter);
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }}

        else{
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
                    Bundle b = new Bundle();
                    b.putString("title",collect1.get(position).getTitle());
                    b.putString("descp",collect1.get(position).getDescription());
                    b.putString("time",collect1.get(position).getTime());
                    b.putInt("price",collect1.get(position).getPrice());
                    b.putString("pic",collect1.get(position).getPhoto());
                    b.putString("cookphone",collect1.get(position).getCookphone());
                    b.putString("cookname",collect1.get(position).getCookname());

                    b.putString("cookmail",collect1.get(position).getCookmail());

                    BuyFragment buyFragment= new BuyFragment();
                    buyFragment.setArguments(b);
                    buyFragment.show(getFragmentManager(),"");


                }
            });









            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setPageSize(100);
        Backendless.Data.of(Food.class).find(queryBuilder,new AsyncCallback<List<Food>>() {



            @Override
            public void handleResponse(List<Food> response) {
dialog.cancel();



                for (Food food : response) {
                    collect.add(food);
                    collect1.add(food);
                }
                adapter= new FoodAdapter(getActivity(),collect1);
//                if(!(type.equalsIgnoreCase(""))){
//                    adapter.clear();
//                }
                lv.setAdapter(adapter);
            }
            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });}

        return view;
    }

    class FoodAdapter extends ArrayAdapter<Food>{
        public FoodAdapter(@NonNull Context context, List<Food> response) {
            super(context,0,response);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder holder;
            if (convertView==null) {
                convertView = getLayoutInflater().inflate(R.layout.meals_layout, parent, false);

                holder = new ViewHolder();
                holder.title=convertView.findViewById(R.id.plateTitle);
                holder.desc=convertView.findViewById(R.id.plateDiscp);
                holder.pic=convertView.findViewById(R.id.platepic);
                holder.price=convertView.findViewById(R.id.price);
                if(getActivity() instanceof CookActivity){

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Edit Meal ? ");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent in = new Intent(getActivity(),DetailsActivity.class);
                                            Food item = new Food();
                                            item=getItem(position);
                                            in.putExtra("pos",position);
                                            in.putExtra("edit",true);
                                            in.putExtra("item",  item);


                                                  startActivity(in);



                                        }
                                    }).setNeutralButton("cancel",null);

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });
                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            {
                                {




                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setMessage("Remove Meal ? ");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Backendless.Data.of( Food.class ).remove( getItem(position), new AsyncCallback<Long>() {
                                                        @Override
                                                        public void handleResponse(Long response) {

                                                            BackendlessUser user = Backendless.UserService.CurrentUser();
                                                            Backendless.Files.remove("images/" + getItem(position).getTitle().toString() +user.getObjectId()+ ".WEBP", new AsyncCallback<Integer>() {
                                                                @Override
                                                                public void handleResponse(Integer response) {

                                                                }

                                                                @Override
                                                                public void handleFault(BackendlessFault fault) {

                                                                }
                                                            });


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
                }
                convertView.setTag(holder);

            }

            else{
                holder= (ViewHolder) convertView.getTag();
            }


            holder.price.setText(getItem(position).getPrice()+" L.E");
            holder.title.setText(getItem(position).getTitle());
            holder.desc.setText(getItem(position).getDescription());
            Picasso.get().load(getItem(position).getPhoto()).into(holder.pic);


            return convertView;

        }

        class ViewHolder{
            TextView title,desc,price;
            ImageView pic;
        }


        }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
@Subscribe
public void onSearch(UserActivity.Searched s){
    adapter.clear();
    collect1.clear();
if(!(s.find.equals(""))){
    for (int i = 0; i <collect.size() ; i++) {
        if(collect.get(i).getTitle().contains(s.find))
            collect1.add(collect.get(i));
    }
    Toast.makeText(getActivity(),collect1.size(), Toast.LENGTH_SHORT).show();
    adapter.addAll(collect1);

}
//
// {
//    String whereClause="title like"+'\''+s.find+"%"+ '\'';
//    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
//    queryBuilder.setWhereClause( whereClause );
//
//    Backendless.Data.of(Food.class).find(queryBuilder,new AsyncCallback<List<Food>>() {
//        @Override
//        public void handleResponse(List<Food> response) {
//            adapter.clear();
//            adapter.addAll(response);
//        }
//
//        @Override
//        public void handleFault(BackendlessFault fault) {
//
//        }
//    });}
else {

adapter.clear();
adapter.addAll(collect);
}

    }

    @Subscribe
    public void onMessage(SortFragment.Message m){
type=m.first;
cost=m.second;
        collect1.clear();
    adapter.clear();


if(type.equalsIgnoreCase("select type")){
    for (Food food : collect) {
        collect1.add(food);
    }}




else{
        for (int i = 0; i <collect.size() ; i++) {
            if(collect.get(i).getType().equalsIgnoreCase(type))
                collect1.add(collect.get(i));
        }}

if(cost.equalsIgnoreCase("lower to higher")){
        Collections.sort(collect1, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return o1.getPrice()-o2.getPrice();
            }
        }) ;}

else if(cost.equalsIgnoreCase("higher to lower")){
    Collections.sort(collect1, new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return o2.getPrice()-o1.getPrice();
        }
    }) ;
}





    }

    }
