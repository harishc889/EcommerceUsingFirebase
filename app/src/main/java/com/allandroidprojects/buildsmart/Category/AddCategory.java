package com.allandroidprojects.buildsmart.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.allandroidprojects.buildsmart.R;


public class AddCategory extends AppCompatActivity {
private ImageView Offers,Laptops,Mobiles,More;
private ImageView Purses,Lifestyle,Shoes,Sports;
private ImageView Glasses,Books,Hats,Sweaters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Offers=(ImageView)findViewById(R.id.headphones);
        Laptops=findViewById(R.id.laptops);
        Mobiles=findViewById(R.id.mobiles);
        More=findViewById(R.id.more);

        Purses=findViewById(R.id.purses);
        Lifestyle=findViewById(R.id.tshirts);
        Shoes=findViewById(R.id.shoes);
        Sports=findViewById(R.id.sports);

        Glasses=findViewById(R.id.glasses);
        Books=findViewById(R.id.books);
        Hats=findViewById(R.id.hats);
        Sweaters=findViewById(R.id.sweaters);

        Offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                    i.putExtra("name","Offers");
                    startActivity(i);
            }
        });
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Laptop");
                startActivity(i);
            }
        });
        Mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Mobile");
                startActivity(i);
            }
        });
        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","More");
                startActivity(i);
            }
        });
        Purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Purse");
                startActivity(i);
            }
        });


        Lifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Lifestyle");
                startActivity(i);
            }
        });
        Shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Shoe");
                startActivity(i);
            }
        });
        Sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Sport");
                startActivity(i);
            }
        });
        Glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Glasses");
                startActivity(i);
            }
        });


        Hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Hat");
                startActivity(i);
            }
        });


        Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Book");
                startActivity(i);
            }
        });
        Sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCategory.this,UploadCategoryItem.class);
                i.putExtra("name","Sweater");
                startActivity(i);
            }
        });

    }
}
