package com.gamehaus.inmobi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "roy_inmobi";
    private InMobiInterstitial interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInmobi();
    }

    private void initInmobi() {
        JSONObject consentObject = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            // Provide 0 if GDPR is not applicable and 1 if applicable
            consentObject.put("gdpr", "0");
            // Provide user consent in IAB format
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_IAB, " << consent in IAB format >> ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(this, "0daf152d48754edcab5e9cfdc70166cf", consentObject, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (null != error) {
                    Log.e(TAG, "InMobi Init failed -" + error.getMessage());
                } else {
                    Log.d(TAG, "InMobi Init Successful");
                    initRwd();
                }
            }
        });
    }

    private void initRwd() {
        interstitialAd = new InMobiInterstitial(MainActivity.this, 1631033716358L,
                new InterstitialAdEventListener() {

                    @Override
                    public void onAdFetchFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        super.onAdFetchFailed(inMobiInterstitial, inMobiAdRequestStatus);
                    }

                    @Override
                    public void onAdWillDisplay(@NonNull InMobiInterstitial inMobiInterstitial) {
                        super.onAdWillDisplay(inMobiInterstitial);
                    }

                    @Override
                    public void onAdDisplayed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                        super.onAdDisplayed(inMobiInterstitial, adMetaInfo);
                    }

                    @Override
                    public void onAdDisplayFailed(@NonNull InMobiInterstitial inMobiInterstitial) {
                        super.onAdDisplayFailed(inMobiInterstitial);
                    }

                    @Override
                    public void onAdDismissed(@NonNull InMobiInterstitial inMobiInterstitial) {
                        super.onAdDismissed(inMobiInterstitial);
                    }

                    @Override
                    public void onUserLeftApplication(@NonNull InMobiInterstitial inMobiInterstitial) {
                        super.onUserLeftApplication(inMobiInterstitial);
                    }

                    @Override
                    public void onRewardsUnlocked(@NonNull InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        super.onRewardsUnlocked(inMobiInterstitial, map);
                    }

                    @Override
                    public void onAdFetchSuccessful(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                        super.onAdFetchSuccessful(inMobiInterstitial, adMetaInfo);
                    }


                    @Override
                    public void onAdLoadSucceeded(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                        super.onAdLoadSucceeded(inMobiInterstitial, adMetaInfo);
                        Log.i(TAG, "onAdLoadSucceeded: ");
                    }

                    @Override
                    public void onAdLoadFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        super.onAdLoadFailed(inMobiInterstitial, inMobiAdRequestStatus);
                        Log.i(TAG, "onAdLoadFailed: "+inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdClicked(@NonNull InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        super.onAdClicked(inMobiInterstitial, map);
                    }

                    @Override
                    public void onRequestPayloadCreated(byte[] bytes) {
                        super.onRequestPayloadCreated(bytes);
                    }

                    @Override
                    public void onRequestPayloadCreationFailed(@NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        super.onRequestPayloadCreationFailed(inMobiAdRequestStatus);
                    }
                });
        interstitialAd.load();
    }

    public void showRwd(View view) {
        if (interstitialAd == null) return;
        if (interstitialAd.isReady()) {
            interstitialAd.show();
        } else {
            Log.i(TAG, "ads not ready ");
        }
    }
}