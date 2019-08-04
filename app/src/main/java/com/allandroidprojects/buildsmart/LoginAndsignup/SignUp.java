package com.allandroidprojects.buildsmart.LoginAndsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allandroidprojects.buildsmart.DisplayHome.HomeActivity;
import com.allandroidprojects.buildsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class SignUp extends AppCompatActivity {
EditText mobileno,password,name;
Button createAcc,login;
String sr;
DatabaseReference databaseReference;
ProgressDialog lodingBar,lb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mobileno=findViewById(R.id.mobileno1);
        password=findViewById(R.id.password1);

        createAcc=findViewById(R.id.createacc1);
        lodingBar=new ProgressDialog(this);
        lb=new ProgressDialog(this);
        login=findViewById(R.id.login2);

        Paper.init(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUp.this,Login.class);
                startActivity(i);
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mobileno.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"mobileno can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"password can't be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    lodingBar.setTitle("Create Account");
                    lodingBar.setMessage("Please wait checking your inputs");
                    lodingBar.setCanceledOnTouchOutside(false);
                    lodingBar.show();


                    createAccount(mobileno.getText().toString(),password.getText().toString());
                }


            }
        });


        // for remember me option code is here

        final String uno = Paper.book().read(PrevalentClass.Pusername);
        final String upass = Paper.book().read(PrevalentClass.Puserpassword);

        if (uno != "" && upass != "") {

            if (!TextUtils.isEmpty(uno) && !TextUtils.isEmpty(upass)) {
                lb.setTitle("Automatically");
                lb.setMessage("loging u in wait...");
                lb.setCanceledOnTouchOutside(false);
                lb.show();
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Users").child(uno).exists()) {

                            Model_Users userData = dataSnapshot.child("Users").child(uno).getValue(Model_Users.class);


                            if (uno.equals(userData.getMobileno())) {
                                if (upass.equals(userData.getPassword())) {
                                    Toast.makeText(getApplicationContext(),
                                            "Already Logged in wait..", Toast.LENGTH_SHORT).show();
                                    Intent i;


                                    i = new Intent(SignUp.this, HomeActivity.class);

                                    i.putExtra("no", uno);


                                    startActivity(i);
                                    lb.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Wrong password try again", Toast.LENGTH_SHORT).show();
                                    lb.dismiss();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "wrong number try again", Toast.LENGTH_SHORT).show();
                                lb.dismiss();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "user doesnot exist", Toast.LENGTH_SHORT).show();

                            lb.dismiss();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext()," "+databaseError,Toast.LENGTH_SHORT).show();
                        lb.dismiss();
                    }

                });

            }
        }

    }
    protected void createAccount(final String mobileno1, final String password1)
    {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final String ph=mobileno1;

        name=findViewById(R.id.editText3);
        sr=name.getText().toString();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(ph).exists()))
                {
                    HashMap<String,Object> usedataMap=new HashMap<>();
                    usedataMap.put("mobileno",mobileno1);
                    usedataMap.put("password",password1);
                    usedataMap.put("name",sr);

                    databaseReference.child("Users").child(mobileno1).updateChildren(usedataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {Toast.makeText(getApplicationContext(),"acc created successfully",Toast.LENGTH_SHORT).show();
                                        lodingBar.dismiss();

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext()," network error to create acc",Toast.LENGTH_SHORT).show();
                                        lodingBar.dismiss();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"user already exists with this number ",Toast.LENGTH_SHORT).show();
                    lodingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext()," "+databaseError,Toast.LENGTH_SHORT).show();
                lodingBar.dismiss();
            }
        });



    }
}
