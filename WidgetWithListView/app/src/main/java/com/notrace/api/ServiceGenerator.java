package com.notrace.api;

import android.text.TextUtils;

import com.notrace.model.BaseReqParam;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by notrace on 2016/9/13.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = "http://open.snssdk.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass,  final String authToken) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl.Builder oldbuild=original.url().newBuilder()
                            .scheme(original.url().scheme())
                            .host(original.url().host())
                            .addQueryParameter("partner",BaseReqParam.getInstance().getPartner())
                            .addQueryParameter("timestamp",BaseReqParam.getInstance().getTimestamp())
                            .addQueryParameter("nonce",BaseReqParam.getInstance().getNonce())
                            .addQueryParameter("signature", BaseReqParam.getInstance().createSignture());
                    if(!TextUtils.isEmpty(authToken))
                    {
                        oldbuild.addQueryParameter("access_token",authToken);
                    }

                    // 新的请求
                    Request newRequest = original.newBuilder()
                            .method(original.method(), original.body())
                            .url(oldbuild.build())
                            .build();
                    return chain.proceed(newRequest);
                }
            });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


}
