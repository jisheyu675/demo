package com.aly.mssdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ms.sdk.MsInterstitialAd;
import com.ms.sdk.MsRewardVideoAd;
import com.ms.sdk.MsSDK;
import com.ms.sdk.listener.MsSdkInitializationListener;
import com.ms.sdk.wrapper.banner.MsBannerAdListener;
import com.ms.sdk.wrapper.banner.MsGameEasyBannerWrapper;
import com.ms.sdk.wrapper.interstitial.MsInterstitialAdListener;
import com.ms.sdk.wrapper.interstitial.MsInterstitialLoadCallback;
import com.ms.sdk.wrapper.video.MsRewardVideoAdListener;
import com.ms.sdk.wrapper.video.MsRewardVideoLoadCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "MsSDK-demo";
    MsInterstitialAd mInterstitialAd;
    MsRewardVideoAd mVideoAd;
    private Button btnInter,btnRew,btnPro;
    private FirebaseAnalytics mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnInter = findViewById(R.id.btn_interAd);
        btnInter.setOnClickListener(this);
        btnRew = findViewById(R.id.btn_rewardedAd);
        btnRew.setOnClickListener(this);
        btnPro = findViewById(R.id.btn_properties);
        btnPro.setOnClickListener(this);

        //FirebaseAnalytics init
        mFirebase = FirebaseAnalytics.getInstance(this);

        //手动跟踪屏幕
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "index");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        mFirebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        MsSDK.setDebuggable(true);
        MsSDK.init(this, new MsSdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {
                        MsGameEasyBannerWrapper.getInstance().initGameBannerWithActivity(MainActivity.this);
                        MsGameEasyBannerWrapper.getInstance().addBannerCallbackAtADPlaceId("banner_aaa", new MsBannerAdListener() {
                            @Override
                            public void onClicked() {
                                Log.d(TAG, "onClicked: ");
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"12241");
                                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"testName");
                                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"banner");
                                mFirebase.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
                            }

                            @Override
                            public void onDisplayed() {
                                Log.d(TAG, "onDisplayed: ");


                            }
                        });
                        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MsGameEasyBannerWrapper.getInstance().showBottomBannerAtADPlaceId("banner_aaa");
                            }
                        },200);

                        mInterstitialAd = new MsInterstitialAd(MainActivity.this,"inter_aaa");
                        final MsInterstitialLoadCallback callback = new MsInterstitialLoadCallback() {
                            @Override
                            public void onLoadFailed(String s) {

                                if (s.equals("inter_aaa")) {

                                    // inter_aaa位的插屏广告加载失败
                                    Log.d(TAG, "onLoadFailed:MsInterstitialLoadCallback "+s);
                                }
                            }

                            @Override
                            public void onLoadSuccessed(String s) {
                                if (s.equals("inter_aaa")) {
                                    // inter_aaa位的插屏广告加载成功，可以展示
                                    Log.d(TAG, "onLoadSuccessed: MsInterstitialLoadCallback"+s);
                                }

                            }
                        };

                        mVideoAd = MsRewardVideoAd.getInstance(MainActivity.this);
                        mVideoAd.setLoadCallback(new MsRewardVideoLoadCallback() {
                            @Override
                            public void onLoadFailed() {
                                Log.d(TAG, "onLoadFailed: MsRewardVideoLoadCallback");
                            }

                            @Override
                            public void onLoadSuccessed() {
                                Log.d(TAG, "onLoadSuccessed: MsRewardVideoLoadCallback");

                            }
                        });

                    }
                });
        MsSDK.onCreate(this);





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_interAd:

                //回调
                mInterstitialAd.setInterstitialAdListener(new MsInterstitialAdListener() {
                    @Override
                    public void onClicked() {
                        Log.d(TAG, "onClicked: mInterstitialAd");
                    }

                    @Override
                    public void onClosed() {
                        Log.d(TAG, "onClosed: mInterstitialAd");

                    }

                    @Override
                    public void onDisplayed() {
                        Log.d(TAG, "onDisplayed: mInterstitialAd 展示");

                    }
                });

                //展示
                if(mInterstitialAd!=null && mInterstitialAd.isReady()){
                    mInterstitialAd.show();
                    Bundle params = new Bundle();
                    params.putString("placement", "inter_aaa");
                    params.putString("type", "inter");
                    mFirebase.logEvent("inter_show", params);
                }
                break;
            case R.id.btn_rewardedAd:
                mVideoAd.setVideoAdListener(new MsRewardVideoAdListener() {
                    @Override
                    public void onVideoAdClicked() {
                        Log.d(TAG, "onVideoAdClicked: MsRewardVideoAdListener");

                    }

                    @Override
                    public void onVideoAdClosed() {
                        Log.d(TAG, "onVideoAdClosed: MsRewardVideoAdListener");

                    }

                    @Override
                    public void onVideoAdDisplayed() {
                        Log.d(TAG, "onVideoAdDisplayed: MsRewardVideoAdListener");

                    }

                    @Override
                    public void onVideoAdReward() {
                        Log.d(TAG, "onVideoAdReward: MsRewardVideoAdListener");

                    }

                    @Override
                    public void onVideoAdDontReward(String s) {
                        Log.d(TAG, "onVideoAdDontReward: MsRewardVideoAdListener"+s);

                    }
                });
                if (mVideoAd!=null&&mVideoAd.isReady()){
                    mVideoAd.show("video_aaa");
                    Bundle params = new Bundle();
                    params.putString("id", "video_aaa");
                    params.putString("type", "rewardVideo");
                    mFirebase.logEvent("rewardVideo_show", params);
                }
                break;
            case R.id.btn_properties:
                mFirebase.setUserProperty("favorite_food","beef");
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        MsSDK.onStart(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        MsSDK.onStop(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MsSDK.onDestroy(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MsSDK.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MsSDK.onResume(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MsSDK.onRestart(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MsSDK.onBackPressed(this);
    }
}