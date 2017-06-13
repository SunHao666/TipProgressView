package com.demo.tipprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TipProgressView1 tipview = (TipProgressView1) findViewById(R.id.tipview);
        tipview.doAnim(10);
    }
}
