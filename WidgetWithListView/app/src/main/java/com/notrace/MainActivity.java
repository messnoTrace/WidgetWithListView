package com.notrace;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.notrace.api.ServiceGenerator;
import com.notrace.api.TouTiaoApi;
import com.notrace.model.NewsResponse;
import com.notrace.model.TokenResponse;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    Subscription subscription;
    TouTiaoApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                requestToken();
                loadData();
            }
        });
    }

    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", "233");
        params.put("openudid", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        params.put("os", "Android");
        params.put("os_version", android.os.Build.VERSION.SDK_INT + "");
        params.put("device_model", android.os.Build.MODEL + "");
        api = ServiceGenerator.createService(TouTiaoApi.class);
        subscription = api.getToken(params)
                .flatMap(new Func1<TokenResponse, Observable<NewsResponse>>() {
                    @Override
                    public Observable<NewsResponse> call(TokenResponse tokenResponse) {
                        Log.d("=========", "Observable");
                        if (!("0".equals(tokenResponse.getRet())))
                            return null;
                        HashMap<String, String> newsPrams = new HashMap<>();
                        newsPrams.put("category", "");
                        newsPrams.put("min_behot_time", System.currentTimeMillis() - 10 + "");
                        newsPrams.put("max_behot_time", System.currentTimeMillis() + "");
                        return api.getNews(tokenResponse.getData().getAccess_token(), newsPrams);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsResponse>() {
                    @Override
                    public void call(NewsResponse newsResponse) {

                        Log.d("========newsResponse", newsResponse.getMsg());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("========Throwable", "==" + throwable.toString());
                    }
                });

    }

    private void requestToken() {


        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid=tm.getDeviceId();
        api = ServiceGenerator.createService(TouTiaoApi.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", "233");
        params.put("openudid", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        params.put("os", "Android");
        params.put("os_version", android.os.Build.VERSION.SDK_INT + "");
        params.put("device_model", android.os.Build.MODEL + "");
        api.getToken(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TokenResponse>() {
                    @Override
                    public void call(TokenResponse tokenResponse) {

                        Log.d("=====tokenResponsetoken","");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("=====throwable ,token","");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
