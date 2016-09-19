package com.notrace.model;

import android.util.Log;

import com.notrace.BuildConfig;
import com.notrace.utils.SHA1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by notrace on 2016/9/13.
 */
public class BaseReqParam {
    protected  String secure_key= BuildConfig.SECURE_KEY;
    private String signture;
    private String timestamp;
    private String nonce;
    private String partner=BuildConfig.ID;
    private String access_token;


    private static BaseReqParam instance;
    private BaseReqParam(){

    }
    public static BaseReqParam  getInstance(){
        if(instance==null)
        {
            instance=new BaseReqParam();
            instance.setNonce(new Random().nextInt()+"");

        }
        instance.setTimestamp(System.currentTimeMillis()+"");
        return instance;
    }
    public String createSignture(){
        List<String> list=new ArrayList<>();
        list.add(secure_key);
        list.add(timestamp);
        list.add(nonce);

        Log.d("=============nonce:",nonce);
        Log.d("=============timestamp:",timestamp);

        String[] keyArray = new String[list.size()];
        list.toArray(keyArray);

        Arrays.sort(keyArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key);
        }
        String codes = stringBuilder.toString();
        Log.d("============secure_key:",SHA1.SHA_1(codes));
        return SHA1.SHA_1(codes);
    }

    public String getSignture() {
        return signture;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public String getPartner() {
        return partner;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }


    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
