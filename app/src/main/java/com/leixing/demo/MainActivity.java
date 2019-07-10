package com.leixing.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author leixing
 */
public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void close() {
        finish();
    }
}
