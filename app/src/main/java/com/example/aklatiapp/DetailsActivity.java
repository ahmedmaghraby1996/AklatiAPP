package com.example.aklatiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

public class DetailsActivity extends AppCompatActivity implements IPickResult {
EditText title,descp,time,price;
Spinner type;
ImageView pic;
Bitmap b;
Boolean checked=false;
    BackendlessUser user ;


    Food item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        title=findViewById(R.id.mtitle);
        descp=findViewById(R.id.mdiscp);
        time=findViewById(R.id.preptime);
        price=findViewById(R.id.mprice);
        pic=findViewById(R.id.mealpic);
        type=findViewById(R.id.mtype);
        user = Backendless.UserService.CurrentUser();
        pic.setBackground(getResources().getDrawable(R.drawable.foodbackground));
        if(getIntent().getBooleanExtra("edit",false)==true){
          item = new Food();
                item= (Food) getIntent().getSerializableExtra("item");
            title.setText(item.getTitle().toString());
            pic.setBackgroundColor(Color.argb(0,0,0,0));

            descp.setText(item.getDescription().toString());
            price.setText(item.getPrice().toString());
            if(item.getType().equals("meal"))
                type.setSelection(1);
            else if (item.getType().equals("dessert"))
                type.setSelection(2);
            else type.setSelection(3);
            time.setText(item.getTime());
            Picasso.get().load(item.getPhoto()).into(pic);

        }



    }


    public void save(View view) {

if(getIntent().getBooleanExtra("edit",false)==false){
        if(!(title.getText().toString().equals("")) && !(descp.getText().toString().equals("")) && !(time.getText().toString().equals("")) &&  !(type.getSelectedItem().toString().equals("select type")) && !(price.getText().toString().equals("")) && checked==true){






            ProgressDialog dialog =new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();
        Backendless.Files.Android.upload(b, Bitmap.CompressFormat.WEBP, 30, title.getText().toString() + user.getObjectId()+".WEBP", "images", new AsyncCallback<BackendlessFile>() {

            @Override
            public void handleResponse(BackendlessFile response) {
                Food food = new Food();
                BackendlessUser user = Backendless.UserService.CurrentUser();
                food.setType(type.getSelectedItem().toString());
                food.setTitle(title.getText().toString());
                food.setPrice(Integer.parseInt(price.getText().toString()));
                food.setDescription(descp.getText().toString());
                food.setTime(time.getText().toString());
                food.setCookname(user.getProperty("name").toString());
                food.setCookphone(user.getProperty("phone").toString());
                food.setPhoto(response.getFileURL());
                food.setCookmail(user.getEmail().toString());
                Backendless.Persistence.save(food, new AsyncCallback<Food>() {
                    @Override
                    public void handleResponse(Food response) {
                        Toast.makeText(DetailsActivity.this, "Meal saved", Toast.LENGTH_SHORT).show();
                        title.setText("");
                        descp.setText("");
                        price.setText("");
                        pic.setImageBitmap(null);
                        dialog.cancel();
                        onBackPressed();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(DetailsActivity.this, "problem uploading data", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }

                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            dialog.cancel();
                Toast.makeText(DetailsActivity.this, "error uploading image", Toast.LENGTH_SHORT).show();
            }
        });}
        else if (checked==false)
            Toast.makeText(this, "Upload meal picture", Toast.LENGTH_SHORT).show();
    else Toast.makeText(this, "Fill all required data correctly", Toast.LENGTH_SHORT).show();
    }
else
{

    ProgressDialog dialog =new ProgressDialog(this);
    dialog.setTitle("Loading");
    dialog.show();
    if(checked==true && getIntent().getBooleanExtra("edit",false)==true ) {

        Backendless.Files.Android.upload(b, Bitmap.CompressFormat.WEBP, 30, title.getText().toString()+user.getObjectId() + ".WEBP", "images", new AsyncCallback<BackendlessFile>() {
            @Override
            public void handleResponse(BackendlessFile response) {
                item.setTime(time.getText().toString());
                item.setPhoto(response.getFileURL());
                item.setTitle(title.getText().toString());
                item.setPrice(Integer.valueOf(price.getText().toString()));
                item.setType(type.getSelectedItem().toString());


                Backendless.Data.of(Food.class).save(item, new AsyncCallback<Food>() {
                    @Override
                    public void handleResponse(Food response) {
                        Toast.makeText(DetailsActivity.this, "Meal edited", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                        Intent in = new Intent(DetailsActivity.this, CookActivity.class);
                        startActivity(in);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(DetailsActivity.this, "error during upload", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(DetailsActivity.this, "error saving data", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }
    else {

        item.setTime(time.getText().toString());

        item.setTitle(title.getText().toString());
        item.setPrice(Integer.valueOf(price.getText().toString()));
        item.setType(type.getSelectedItem().toString());
        Backendless.Data.of(Food.class).save(item, new AsyncCallback<Food>() {
            @Override
            public void handleResponse(Food response) {
                Toast.makeText(DetailsActivity.this, "Meal edited", Toast.LENGTH_SHORT).show();
                dialog.cancel();

                Intent in = new Intent(DetailsActivity.this, CookActivity.class);
                startActivity(in);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(DetailsActivity.this, "error during upload", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

    }


    public void take(View view) {
        PickImageDialog.build(new PickSetup()).show(this);
}


    @Override
    public void onPickResult(PickResult r) {
        if(r.getError()==null){
            
            checked=true;
            b=r.getBitmap();
            pic.setBackgroundColor(Color.argb(0,0,0,0));
            pic.setImageBitmap(r.getBitmap());
            if(getIntent().getBooleanExtra("edit",false)==true) {
                Backendless.Files.remove("images/" + item.getTitle().toString() + ".WEBP", new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }}
        else{ Toast.makeText(this, "problem loading image", Toast.LENGTH_SHORT).show();



    }
    }}


