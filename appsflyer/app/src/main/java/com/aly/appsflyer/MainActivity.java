package com.aly.appsflyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final  String AF_DEV_KEY = "fZvuk792H9hJQKmaTwuXxA";
    private static String TAG = "appsflyer-Demo-";
    private Button btnLogin;
    private TextView infoTv;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                for (String attrName : map.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + map.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String s) {
                Log.d(TAG, "onConversionDataFail: "+s);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
                for (String attrName : map.keySet()) {
                    Log.d(TAG, "attribute: " + attrName + " = " + map.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d(TAG, "onAttributionFailure: "+s);

            }
        };
        AppsFlyerLib.getInstance().init(AF_DEV_KEY,conversionListener,getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
    }
    private void initView() {

//        callbackManager = CallbackManager.Factory.create();
        btnLogin =  findViewById(R.id.btn_login);
//        btnLogin.setReadPermissions(Arrays.asList("email"));
        btnLogin.setOnClickListener(this);
        frameLayout = (FrameLayout)findViewById(R.id.frameLay);
        infoTv = (TextView)findViewById(R.id.tv_Info);
        findViewById(R.id.btn_pay).setOnClickListener(this);
        frameLayout.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.LOGIN,null);
                break;
            case R.id.btn_pay:
                pay();
                break;
        }

    }
    private void pay(){


//        Log.d(TAG, "Purchase successful!");
//        Map<String, String> additional_event_values = new HashMap<>();
//        additional_event_values.put("some_parameter", "some_value");
//        String price= "10";
//        String currency = "USD";
//        AppsFlyerLib.getInstance().validateAndTrackInAppPurchase(getApplicationContext(), PUBLIC_KEY, purchase.getSignature(), purchase.getOriginalJson(), revenue, currency, additional_event_values);


        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.REVENUE, 200);
        eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
        eventValue.put(AFInAppEventParameterName.QUANTITY, 2);
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, "092");
        eventValue.put("order_id", "9277");
        eventValue.put(AFInAppEventParameterName.RECEIPT_ID, "9277");
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(),AFInAppEventType.PURCHASE, eventValue);


    }
}