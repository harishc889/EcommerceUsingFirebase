package com.allandroidprojects.buildsmart.miscellaneous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.allandroidprojects.buildsmart.R;

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }
}
