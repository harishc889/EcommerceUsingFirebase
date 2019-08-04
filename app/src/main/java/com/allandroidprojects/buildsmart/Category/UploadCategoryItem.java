package com.allandroidprojects.buildsmart.Category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.allandroidprojects.buildsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UploadCategoryItem extends AppCompatActivity {
private ImageView selectImage;
private EditText pname,pprice,pdescription;
private static final int requestCode=1;
private Uri imageUri;
private Button upload;
private String productName,productPrice,productDescription;
private int flag=0;
private StorageReference storageRef;
private DatabaseReference myref;

private String saveCurrentTime,downloadUrl,category;
private String saveCurrentDate,productRandomTitle;
private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_category_item);
        selectImage=findViewById(R.id.select_image);
        category=getIntent().getStringExtra("name");
         upload=findViewById(R.id.upload);
         progressDialog=new ProgressDialog(this);

        pname=findViewById(R.id.productName);
        pprice=findViewById(R.id.productPrice);
        pdescription=findViewById(R.id.ProductDescription);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              selectImage();

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
            }
        });
    }

    private void validateInputs() {
       productName=pname.getText().toString();
       productPrice=pprice.getText().toString();
       productDescription=pdescription.getText().toString();

       if(TextUtils.isEmpty(productName))
       {
           flag=1;
       }
       else if(imageUri==null)
       {
           flag=1;
       }
       else if(TextUtils.isEmpty(productPrice))
       {
           flag=1;
       }
       else if(TextUtils.isEmpty(productDescription))
       {
           flag=1;
       }

       else
           {
           flag=0;
       }
       if(flag==1)
       {
           Toast.makeText(getApplicationContext(),"Fill all the fields ",Toast.LENGTH_SHORT).show();}
       else
       {
           storeProductInformation();
       }
    }

    private void storeProductInformation() {
        storageRef = FirebaseStorage.getInstance().getReference().child("ProductImage");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomTitle=saveCurrentDate+saveCurrentTime;

        final StorageReference filePath= storageRef.child(imageUri.getLastPathSegment()+productRandomTitle+".jpg");
        final UploadTask uploadTask=filePath.putFile(imageUri);
        progressDialog.setTitle("Uploading..");
        progressDialog.setMessage("uploading your selected image wait..");
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"error : "+e,Toast.LENGTH_LONG).show();
           progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getApplicationContext(),"successfully inserted your image into database ",Toast.LENGTH_LONG).show();
                        downloadUrl=uri.toString();
                        productDetailsIntoDatabase();
                    }
                });


//               downloadUrl = taskSnapshot.getDownloadUrl().toString();

            }
        });
    }

    private void productDetailsIntoDatabase() {
        myref = FirebaseDatabase.getInstance().getReference("products");

        HashMap<String,Object> productDetails=new HashMap<>();
        productDetails.put("pid",productRandomTitle);
        productDetails.put("date",saveCurrentDate);
        productDetails.put("time",saveCurrentTime);
        productDetails.put("name",productName);
        productDetails.put("Description",productDescription);
        productDetails.put("price",productPrice);
        productDetails.put("downloadurl",downloadUrl);
        productDetails.put("category",category);
        myref.child(category).child(productRandomTitle).updateChildren(productDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"successfully inserted product details into database ",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),AddCategory.class);
                    startActivity(i);
                    display_home();
                }
                else
                {   String e=task.getException().toString();
                    Toast.makeText(getApplicationContext(),"error:  "+e,Toast.LENGTH_LONG).show();
                }
            }
        });
        progressDialog.dismiss();

    }

    private void display_home() {

    }

    private void selectImage()
    {
        Intent i=new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==this.requestCode && resultCode==RESULT_OK)
        {
            imageUri=data.getData();
            selectImage.setImageURI(imageUri);

        }
    }
}
