package com.allandroidprojects.buildsmart.LoginAndsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.allandroidprojects.buildsmart.Category.AddCategory;
import com.allandroidprojects.buildsmart.DisplayHome.HomeActivity;
import com.allandroidprojects.buildsmart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    EditText mobileno,password;
    Button loginAcc;
    DatabaseReference databaseReference;
    ProgressDialog lodingBar;
    String user;
    CheckBox asAdmin,rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // databaseReference.initializeApp(this);
        mobileno=findViewById(R.id.mobileno2);
        password=findViewById(R.id.password2);
        loginAcc=findViewById(R.id.loginacc1);
        rememberMe=findViewById(R.id.rememberme);
        lodingBar=new ProgressDialog(this);
       asAdmin=findViewById(R.id.checkbox_admin);
        loginAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String no=mobileno.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(no))
                {
                    Toast.makeText(getApplicationContext(),"mobileno can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(getApplicationContext(),"password can't be empty",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    lodingBar.setTitle("Login to Account");
                    lodingBar.setMessage("Please wait checking your inputs");
                    lodingBar.setCanceledOnTouchOutside(false);
                    lodingBar.show();

                    validate(no,pass);
                }

            }
        });


    }
    public void validate(final String no, final String pass) {
        if(asAdmin.isChecked())
        { user="Admin";
        }
        else
        { user="Users";}
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).child(no).exists()) {
//                    if(dataSnapshot.child("Users").child(no).child(pass).exists())
//                    {
//                        Toast.makeText(getApplicationContext(),
//                                "Successfully logged in",Toast.LENGTH_SHORT).show();
//                        lodingBar.dismiss();
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),
//                                "Wrong password try again",Toast.LENGTH_SHORT).show();
//                            lodingBar.dismiss();
//                    }
//
                    Model_Users userData = dataSnapshot.child(user).child(no).getValue(Model_Users.class);


                    if (no.equals(userData.getMobileno())) {
                        if (pass.equals(userData.getPassword())) {
                            Toast.makeText(getApplicationContext(),
                                    "Successfully logged in", Toast.LENGTH_SHORT).show();
                            Intent i;
                            if(asAdmin.isChecked()) {
                                i = new Intent(Login.this, AddCategory.class);
                            }
                            else  {
                                i = new Intent(Login.this, OTP.class);
                            }
                            i.putExtra("no",no);
                            if(rememberMe.isChecked()){
                                Paper.book().write(PrevalentClass.Pusername,no);
                                Paper.book().write(PrevalentClass.Puserpassword,pass);
                            }


                            startActivity(i);
                            lodingBar.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong password try again", Toast.LENGTH_SHORT).show();
                            lodingBar.dismiss();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "wrong number try again", Toast.LENGTH_SHORT).show();
                        lodingBar.dismiss();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "user doesnot exist", Toast.LENGTH_SHORT).show();

                    lodingBar.dismiss();

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
