package com.aly.mopub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

//import com.adcolony.sdk.AdColony;
//import com.bytedance.sdk.openadsdk.TTAdConfig;
//import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedAdListener;
import com.mopub.mobileads.MoPubRewardedAds;
import com.mopub.mobileads.MoPubView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MoPubView.BannerAdListener, MoPubInterstitial.InterstitialAdListener, View.OnClickListener {
    private MoPubView moPubView;
    private MoPubInterstitial mInterstitial;
    private Button btnRew,btnInter,btnActivity;
    private MoPubRewardedAdListener rewardedAdListener;
    private static String TAG = "mopub-demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoPub.onCreate(this);
        btnRew = findViewById(R.id.btn_rewardedAd);
        btnRew.setOnClickListener(this);
        btnInter = findViewById(R.id.btn_interAd);
        btnInter.setOnClickListener(this);
        btnActivity = findViewById(R.id.btn_activity);
        btnActivity.setOnClickListener(this);

//        Log.d(TAG,"mainActivity#onCreate   "+Thread.currentThread().getName());

//
        final SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder("43c70eb4d9fd4ac69f9c12e8c97c58eb");
        if (BuildConfig.DEBUG){
            configBuilder.withLogLevel(MoPubLog.LogLevel.DEBUG);
        }else {
            configBuilder.withLogLevel(MoPubLog.LogLevel.INFO);
        }

        MoPub.initializeSdk(this,configBuilder.build(),initializationListener());

        MoPubRewardedAds.loadRewardedAd("43c70eb4d9fd4ac69f9c12e8c97c58eb");
        rewardedAdListener = new MoPubRewardedAdListener() {
            @Override
            public void onRewardedAdLoadSuccess(String s) {

            }

            @Override
            public void onRewardedAdLoadFailure(String s, MoPubErrorCode moPubErrorCode) {
                Log.d(TAG, "onRewardedAdLoadFailure: "+moPubErrorCode+","+s);

            }

            @Override
            public void onRewardedAdStarted(String s) {

            }

            @Override
            public void onRewardedAdShowError(String s, MoPubErrorCode moPubErrorCode) {
                Log.d(TAG, "onRewardedAdShowError: "+moPubErrorCode + ","+s);

            }

            @Override
            public void onRewardedAdClicked(String s) {

            }

            @Override
            public void onRewardedAdClosed(String s) {

            }

            @Override
            public void onRewardedAdCompleted(Set<String> set, MoPubReward moPubReward) {

            }
        };
        MoPubRewardedAds.setRewardedAdListener(rewardedAdListener);



    }

    private SdkInitializationListener initializationListener(){
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                Log.d(TAG, "onInitializationFinished: ");

                }
            };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rewardedAd:
                MoPubRewardedAds.showRewardedAd("43c70eb4d9fd4ac69f9c12e8c97c58eb");
                break;
        }
    }

    /**
     * banner
     * @param banner
     */
    @Override
    public void onBannerLoaded(@NonNull MoPubView banner) {

    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

    }

    @Override
    public void onBannerClicked(MoPubView banner) {

    }

    @Override
    public void onBannerExpanded(MoPubView banner) {

    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {

    }

    /**
     * interstitial
     * @param interstitial
     */

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {

    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        MoPub.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MoPub.onStop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MoPub.onResume(this);
        Log.d(TAG, "onResume: " + MainActivity.this.getTaskId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        MoPub.onStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MoPub.onRestart(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MoPub.onBackPressed(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moPubView.destroy();
        mInterstitial.destroy();
        MoPub.onDestroy(this);
    }
}