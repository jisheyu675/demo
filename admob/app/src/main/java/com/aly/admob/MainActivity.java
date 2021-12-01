package com.aly.admob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.ads.mediationtestsuite.MediationTestSuite;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ironsource.mediationsdk.IronSource;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private static String TAG = "admob-demo";
    private Button btnInter,btnReward,btnTest;
    private RewardedAd mRewardedAd;
    private TextView txtCon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInter = findViewById(R.id.btn_interAd);
        btnInter.setOnClickListener(this);
        btnReward = findViewById(R.id.btn_rewardedAd);
        btnReward.setOnClickListener(this);
        txtCon = findViewById(R.id.txt_con);
        btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);


        //初始化
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Map<String,AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                for (String adapterClass:statusMap.keySet()){
                    AdapterStatus status = statusMap.get(adapterClass);
                    Log.d(TAG, String.format("Adapter name: %s, Description: %s, Latency: %d",
                            adapterClass, status.getDescription(), status.getLatency()));
                }
            }
        });


        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //插页式
        InterstitialAd.load(this, "ca-app-pub-2152232918216707/4730706409", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: "+loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

        //激励视频
        RewardedAd.load(this, "ca-app-pub-2152232918216707/3343626633", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                mRewardedAd = rewardedAd;
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: "+loadAdError.getMessage());
                mRewardedAd = null;
            }
        });


        //banner
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d(TAG, "onAdClosed: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                txtCon.setText(loadAdError.toString());

                Log.d(TAG, "onAdFailedToLoad: "+loadAdError);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.d(TAG, "onAdClicked: ");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.d(TAG, "onAdImpression: ");
            }
        });
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_interAd:
                if (mInterstitialAd != null){
                    mInterstitialAd.show(this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }else{
                    Log.d(TAG, "onClick: The interstitial ad wasn't ready yet.");
                }
                break;
            case R.id.btn_rewardedAd:
                if (mRewardedAd != null){
                    mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                            Log.d(TAG, "onUserEarnedReward: The user earned the reward.\n rewardAmount:"+rewardAmount+
                                    "rewardType: "+rewardType);

                        }
                    });
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }else {
                    Log.d(TAG, "onClick: The rewarded ad wasn't ready yet.");
                }
                break;
            case R.id.btn_test:
                MediationTestSuite.launch(this);
                break;
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