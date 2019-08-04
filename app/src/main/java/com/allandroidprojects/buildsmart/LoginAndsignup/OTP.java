package com.allandroidprojects.buildsmart.LoginAndsignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allandroidprojects.buildsmart.DisplayHome.HomeActivity;
import com.allandroidprojects.buildsmart.R;
import com.allandroidprojects.buildsmart.startup.MainActivity;

import java.util.Random;

public class OTP extends AppCompatActivity {
Button send,check;
EditText otp;
SmsManager sm;
String s;
int rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        send=findViewById(R.id.button2);
        check=findViewById(R.id.button3);
        sm= SmsManager.getDefault();
        Intent i= getIntent();
        Random random= new Random();
        rand= random.nextInt(50000);
        otp=findViewById(R.id.editText);
       s=i.getStringExtra("no");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.sendTextMessage(s,null,"your OTP is : "+rand,null,null);

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp=Integer.parseInt(otp.getText().toString());
                if(temp==rand)
                {

                    Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                    i.putExtra("phnno",s);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"succesfully logged in",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"wrong otp",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
