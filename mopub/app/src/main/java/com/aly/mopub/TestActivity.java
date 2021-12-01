package com.aly.mopub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends AppCompatActivity {
    private static String TAG = "mopub-demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d(TAG,"Activity#onCreate   "+Thread.currentThread().getName());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + TestActivity.this.getTaskId());
    }
}