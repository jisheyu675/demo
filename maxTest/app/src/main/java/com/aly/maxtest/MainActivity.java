package com.aly.maxtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MaxAdListener, View.OnClickListener {

    private static String TAG = "max-demo";
    private MaxInterstitialAd interstitialAd;
    private MaxRewardedAd rewardedAd;
    private MaxAdView adView;

    private Button btnInter,btnReward,btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInter = findViewById(R.id.btn_interAd);
        btnInter.setOnClickListener(this);
        btnReward = findViewById(R.id.btn_rewardedAd);
        btnReward.setOnClickListener(this);
        btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);

        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(AppLovinSdkConfiguration config) {
                Log.d(TAG, "onSdkInitialized: "+config);

                interstitialAd = new MaxInterstitialAd("07eff2e0c5f45099",MainActivity.this);
                interstitialAd.setListener(MainActivity.this);
                interstitialAd.loadAd();

                rewardedAd = MaxRewardedAd.getInstance("3f8135de853b7f6a",MainActivity.this);
                rewardedAd.setListener(maxRewardedAdListener);
                rewardedAd.loadAd();

                adView = new MaxAdView("e485af0cb1172ccb",MainActivity.this);
                adView.setListener(maxAdViewAdListener);
                adView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.banner_height)));
                ViewGroup rootView = findViewById(R.id.content);
                rootView.addView(adView);
                adView.loadAd();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_interAd:
                if (interstitialAd.isReady()){
                    interstitialAd.showAd();
                }else{
                    Log.d(TAG, "onClick: interstitialAd is not ready");
                }
                break;
            case R.id.btn_rewardedAd:
                if (rewardedAd.isReady()){
                    rewardedAd.showAd();
                }else{
                    Log.d(TAG, "onClick: rewardedAd is not ready");
                }

                break;
            case R.id.btn_test:
                AppLovinSdk.getInstance(this).showMediationDebugger();
                break;

        }
    }

    @Override
    public void onAdLoadFailed(String adUnitId, int errorCode) {
        Log.d(TAG, "onAdLoadFailed: "+adUnitId+"code : "+errorCode);

    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        Log.d(TAG, "onAdLoaded: "+ad);

    }

    @Override
    public void onAdHidden(MaxAd ad) {
        Log.d(TAG, "onAdHidden: "+ad);

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, int errorCode) {
        Log.d(TAG, "onAdDisplayFailed: "+ad+ "code : "+errorCode);

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {
        Log.d(TAG, "onAdDisplayed: "+ad);

    }

    @Override
    public void onAdClicked(MaxAd ad) {
        Log.d(TAG, "onAdClicked: "+ad);

    }

    MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
        @Override
        public void onRewardedVideoStarted(MaxAd ad) {
            Log.d(TAG, "onRewardedVideoStarted: "+ad);
        }

        @Override
        public void onRewardedVideoCompleted(MaxAd ad) {
            Log.d(TAG, "onRewardedVideoCompleted: "+ad);

        }

        @Override
        public void onUserRewarded(MaxAd ad, MaxReward reward) {
            Log.d(TAG, "onUserRewarded: "+ad+"maxReward : "+reward);

        }

        @Override
        public void onAdLoaded(MaxAd ad) {
            Log.d(TAG, "onAdLoaded: "+ad);

        }

        @Override
        public void onAdLoadFailed(String adUnitId, int errorCode) {
            Log.d(TAG, "onAdLoadFailed: "+adUnitId+"code : "+errorCode);

        }

        @Override
        public void onAdDisplayed(MaxAd ad) {
            Log.d(TAG, "onAdDisplayed: "+ad);

        }

        @Override
        public void onAdHidden(MaxAd ad) {
            Log.d(TAG, "onAdHidden: "+ad);

        }

        @Override
        public void onAdClicked(MaxAd ad) {
            Log.d(TAG, "onAdClicked: "+ad);

        }

        @Override
        public void onAdDisplayFailed(MaxAd ad, int errorCode) {
            Log.d(TAG, "onAdDisplayFailed: "+ad+"code : "+errorCode);

        }
    };

    MaxAdViewAdListener maxAdViewAdListener = new MaxAdViewAdListener() {
        @Override
        public void onAdExpanded(MaxAd ad) {
            Log.d(TAG, "onAdExpanded: "+ad);
        }

        @Override
        public void onAdCollapsed(MaxAd ad) {
            Log.d(TAG, "onAdCollapsed: "+ad);

        }

        @Override
        public void onAdLoaded(MaxAd ad) {
            Log.d(TAG, "onAdLoaded: success");

        }

        @Override
        public void onAdLoadFailed(String adUnitId, int errorCode) {
            Log.d(TAG, "onAdLoadFailed: "+errorCode);

            //204 ?????????????????????????????????????????????
            //-1  ??????????????????????????????sdk?????????????????????
            //-102 ??????ad?????????????????????????????????????????????
            //-103 ????????????????????????internet????????????????????????
            //-2051 ????????????????????????VPN???VPN?????????????????????????????????????????????
            //-50001 ??????????????????????????????????????????????????????????????????ad????????????
            //-5201 ??????AppLovin MAX SDK?????????????????????
            //-5601 ?????????AppLovin MAX SDK????????????????????????ad???????????????????????????????????????????????????

        }

        @Override
        public void onAdDisplayed(MaxAd ad) {
            Log.d(TAG, "onAdDisplayed: "+ad);

        }

        @Override
        public void onAdHidden(MaxAd ad) {
            Log.d(TAG, "onAdHidden: "+ad);

        }

        @Override
        public void onAdClicked(MaxAd ad) {
            Log.d(TAG, "onAdClicked: "+ad);

        }

        @Override
        public void onAdDisplayFailed(MaxAd ad, int errorCode) {
            Log.d(TAG, "onAdDisplayFailed: "+ad+"code : "+errorCode);

        }
    };


}