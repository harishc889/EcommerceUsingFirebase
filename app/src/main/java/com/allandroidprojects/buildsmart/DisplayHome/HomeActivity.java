package com.allandroidprojects.buildsmart.DisplayHome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.allandroidprojects.buildsmart.LoginAndsignup.Login;
import com.allandroidprojects.buildsmart.R;
import com.allandroidprojects.buildsmart.startup.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
 DatabaseReference dref;
 GridView gridView;
 Button nextofNothing;
 ListView listView;
 ProgressDialog pd;
public ArrayList<modell> booksDetails,laptopsDetails,offerDetails,lifestyleDetails,mobilesDetails,moreDetails;
// JSONObject singlesnapshot;
  modell m;
    public static HomeActivity homeActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nothing);
     // String category="Laptop";
        nextofNothing=findViewById(R.id.nextnothing);
        pd=new ProgressDialog(this);
        homeActivity=this;
            String [] category={"Offers","Laptop","Mobile","Book","Lifestyle","More"};
            pd.setTitle("Please Wait");
            pd.setMessage("Wait Getting category Details...");
            pd.show();
        getImageDetails(category[0]);
        getImageDetails(category[1]);
        getImageDetails(category[2]);
        getImageDetails(category[3]);
        getImageDetails(category[4]);
        getImageDetails(category[5]);
//      listView=findViewById(R.id.lis)

     //   displayInGrid();
        m=new modell();
        nextofNothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }

    private void displayInGrid() {
//        int gridWidth=getResources().getDisplayMetrics().widthPixels;
//        int imageWidth=gridWidth/2;
//        gridView.setColumnWidth(imageWidth);

//        ArrayList<String> imageDownloadLinks=new ArrayList<>();
//
//
//
//
//        for(int i=0;i<booksimageDetails.size();i++)
//        {
//            imageDownloadLinks.add(booksimageDetails.get(i).toString());
//
//        }
//        for(int i=0;i<laptopsimageDetails.size();i++)
//        {
//            imageDownloadLinks.add(laptopsimageDetails.get(i).toString());
//
//        }
//        for(int i=0;i<mobilesimageDetails.size();i++)
//        {
//            imageDownloadLinks.add(mobilesimageDetails.get(i).toString());
//
//        }
//        for(int i=0;i<booksimageDetails.size();i++)
//        {
//            imageDownloadLinks.add(tshirtsimageDetails.get(i).toString());
//
//        }

//        ArrayAdapter arrayAdapter= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,imageDetails);
//
//        listView.setAdapter(arrayAdapter);


    }

    private void getImageDetails(final String category) {
        booksDetails=new ArrayList();
        laptopsDetails=new ArrayList();
        lifestyleDetails=new ArrayList();
        mobilesDetails=new ArrayList();
        offerDetails=new ArrayList();
        moreDetails=new ArrayList();
        //imageDetails.add("https://firebasestorage.googleapis.com/v0/b/fir-imagessavingandretreav.appspot.com/o/ProductImage%2Fimage%3A162810May%2005%2C%20201912%3A58%3A18%20PM.jpg?alt=media&token=a58a2d3a-8675-4ca0-b0ea-54536987630d");
        dref= FirebaseDatabase.getInstance().getReference("products");
        dref.child(category)
              .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(category.equals("Laptop")) {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                modell model1 = singleSnapshot.getValue(modell.class);

                                //  boolean f= singleSnapshot.hasChild("downloadurl");
                                //  int n= (int) singleSnapshot.getChildrenCount();
                                // singleSnapshot.getChildren().forEach((x)->{Log.d("msg",x.getKey());});
                                // Log.d("child",""+f);
                                //Log.d("no.of child",""+n);
                                //  Log.d("child i got",singleSnapshot.child("downloadurl").getValue(String.class)+"hi");
                                // imageDetails.add(singleSnapshot.child("downloadurl").getValue(String.class));
                                laptopsDetails.add(model1);
                            }
                        }
                        else if(category.equals("Book"))
                        {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                modell model2 = singleSnapshot.getValue(modell.class);
                                booksDetails.add(model2);
                            }

                        }
                        else if(category.equals("Offers"))
                        {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                modell model3 = singleSnapshot.getValue(modell.class);
                                offerDetails.add(model3);
                            }
                        }
                        else if(category.equals("Mobile"))
                        {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                modell model4 = singleSnapshot.getValue(modell.class);
                                mobilesDetails.add(model4);
                            }
                        }
                        else if(category.equals("Lifestyle"))
                        {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                modell model4 = singleSnapshot.getValue(modell.class);
                                lifestyleDetails.add(model4);
                            }
                        }
                        else if(category.equals("More"))
                        {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                modell model4 = singleSnapshot.getValue(modell.class);
                                moreDetails.add(model4);
                            }
                            pd.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"not able to retreive details of images",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static HomeActivity getInstanc()
    {
        return homeActivity;
    }
}
