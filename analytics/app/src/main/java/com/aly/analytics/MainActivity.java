package com.aly.analytics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAnalytics mFirebase;
    private Button btnLogin,btnCreate,btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mFirebase = FirebaseAnalytics.getInstance(this);

        //手动跟踪屏幕
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "index");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        mFirebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

    }
    private void initView(){
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(this);
        btnPay = findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_create:
                mFirebase.setUserProperty("favorite_food","beef");
                break;
            case R.id.btn_pay:
                pay();
                break;
        }

    }
    private void login(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"12241");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"testName");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"image");
        mFirebase.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
    }
    private void pay(){
        Bundle params = new Bundle();
        params.putString("roleName", "name");
        params.putString("type", "pay");
        mFirebase.logEvent("pay_content", params);
    }
}