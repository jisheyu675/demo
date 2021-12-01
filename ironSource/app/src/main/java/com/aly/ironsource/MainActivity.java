package com.aly.ironsource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRew,btnInter;
    private static String TAG = "ironSource-demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRew = findViewById(R.id.btn_rewardedAd);
        btnRew.setOnClickListener(this);
        btnInter = findViewById(R.id.btn_interAd);
        btnInter.setOnClickListener(this);

        //设置监听器
        IronSource.setRewardedVideoListener(mRewLis);
        IronSource.setInterstitialListener(mInterLis);

        //初始化
        IronSource.init(this,"e1b69cb9",IronSource.AD_UNIT.REWARDED_VIDEO,
                IronSource.AD_UNIT.INTERSTITIAL,IronSource.AD_UNIT.BANNER);

        //确定和监控用户设备上的互联网连接。这使得SDK可以根据网络修改更改其可用性，即在没有网络连接的情况下，可用性将转向FALSE。
        IronSource.shouldTrackNetworkState(this,true);

        //加载插页式广告
        IronSource.loadInterstitial();


        final FrameLayout bannerContainer = findViewById(R.id.bannerContainer);
        //启动banner试图，设置banner大小
        IronSourceBannerLayout bannerLayout = IronSource.createBanner(this, ISBannerSize.BANNER);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        bannerContainer.addView(bannerLayout,0,layoutParams);
        bannerLayout.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {

            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError ironSourceError) {

            }

            @Override
            public void onBannerAdClicked() {

            }

            @Override
            public void onBannerAdScreenPresented() {
                //当横幅即将呈现全屏内容时的回调
            }

            @Override
            public void onBannerAdScreenDismissed() {
                //内容被取消
            }

            @Override
            public void onBannerAdLeftApplication() {
                //在将用户从应用程序上下文中取出时的
            }
        });

        IronSource.loadBanner(bannerLayout);

        //验证奖励视频调解集成是否成功
        IntegrationHelper.validateIntegration(this);

    }
    RewardedVideoListener mRewLis = new RewardedVideoListener() {
        @Override
        public void onRewardedVideoAdOpened() {

        }

        @Override
        public void onRewardedVideoAdClosed() {

        }

        @Override
        public void onRewardedVideoAvailabilityChanged(boolean b) {
            //广告可用性的回调，true就是视频广告 可用，就可以show，false就是不可用
        }

        @Override
        public void onRewardedVideoAdRewarded(Placement placement) {
            //当用户看完视频，并应得到奖励的回调
            placement.getRewardName();//奖励名称
            placement.getRewardAmount();//奖励金额
        }

        @Override
        public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {

        }

        @Override
        public void onRewardedVideoAdClicked(Placement placement) {

        }

        //开始和结束 不是适用于所有的广告联盟的
        @Override
        public void onRewardedVideoAdStarted() {

        }

        @Override
        public void onRewardedVideoAdEnded() {

        }
    };

    InterstitialListener mInterLis = new InterstitialListener() {
        @Override
        public void onInterstitialAdReady() {
            //在调用load函数后准备显示中间Ad时的回调
            Log.d(TAG, "onInterstitialAdReady: ");
        }

        @Override
        public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {

            Log.d(TAG, "onInterstitialAdLoadFailed: "+ironSourceError);
        }

        @Override
        public void onInterstitialAdOpened() {
            Log.d(TAG, "onInterstitialAdOpened: ");
        }

        @Override
        public void onInterstitialAdClosed() {
            Log.d(TAG, "onInterstitialAdClosed: ");
        }

        @Override
        public void onInterstitialAdShowSucceeded() {
            Log.d(TAG, "onInterstitialAdShowSucceeded: ");

        }

        @Override
        public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
            Log.d(TAG, "onInterstitialAdShowFailed: "+ironSourceError);

        }

        @Override
        public void onInterstitialAdClicked() {
            Log.d(TAG, "onInterstitialAdClicked: ");

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rewardedAd:
                if (IronSource.isRewardedVideoAvailable()){
                    IronSource.showRewardedVideo();
                }else{
                    Log.d(TAG, "onClick: isRewardedVideoAvailable is false");
                }

                break;
            case R.id.btn_interAd:
                if (IronSource.isInterstitialReady()){
                    IronSource.showInterstitial();
                    Log.d(TAG, "showInterstitial:  true");
                }else{
                    Log.d(TAG, "onClick: isInterstitialReady is false");
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }
}