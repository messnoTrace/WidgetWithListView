package com.notrace;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.notrace.api.ServiceGenerator;
import com.notrace.api.TouTiaoApi;
import com.notrace.model.News;
import com.notrace.model.NewsResponse;
import com.notrace.model.PicResponse;
import com.notrace.model.TokenResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by notrace on 2016/9/12.
 */
public class WeatherWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(),intent);
    }

}

/**
 * This is the factory that will provide data to the collection widget.
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;
    private List<News>list=new ArrayList<>();
    TouTiaoApi api;
    Subscription subscription;
    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        loadData(context);
        Log.d("======","StackRemoteViewsFactory");

    }

    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        loadData(mContext);
        Log.d("=========","onDataSetChanged");
    }

    public void onDestroy() {

        if(subscription!=null)
        {
            subscription.unsubscribe();
        }
    }

    public int getCount() {
        Log.d("=========","getCount");
        return list.size();
    }

    public RemoteViews getViewAt(int position) {
        Log.d("=========","getViewAt");

        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.tv_item_title,list.get(position).getTitle());
        rv.setTextViewText(R.id.tv_item_descirption,list.get(position).getDescription());
        rv.setTextViewText(R.id.tv_item_time,list.get(position).getBehot_time());
        rv.setTextViewText(R.id.tv_item_comment,list.get(position).getComment_count());
        rv.setTextViewText(R.id.tv_item_source,list.get(position).getSource());

        PicResponse response=list.get(position).getMiddle_image();
        if(response!=null)
        {
            if(response.getUrl_list()!=null)
            {
                switch (response.getUrl_list().size())
                {
                    case 3:
                        rv.setImageViewResource(R.id.iv_item_src3,R.drawable.ic_launcher);
                    case 2:
                        rv.setImageViewResource(R.id.iv_item_src2,R.drawable.ic_launcher);
                    case 1:
                        rv.setImageViewResource(R.id.iv_item_src1,R.drawable.ic_launcher);
                        break;
                }
            }
        }
        final Intent fillInIntent = new Intent();
        final Bundle extras = new Bundle();
        extras.putString(WeatherProvider.EXTRA_CITY_ID, list.get(position).getArticle_url());
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.ll_item_content, fillInIntent);
        return rv;
    }

    public RemoteViews getLoadingView() {
        Log.d("=========","getLoadingView");
        return null;
    }

    public int getViewTypeCount() {
        Log.d("=========","getViewTypeCount");
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        Log.d("=========","hasStableIds");
        return true;
    }

    private void loadData(final Context context) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", "233");
        params.put("openudid", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
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

                        if(newsResponse!=null&&newsResponse.getData()!=null)
                        {
                            list=newsResponse.getData();
                            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                            final ComponentName cn = new ComponentName(context, WeatherProvider.class);
                            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.weather_list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("========Throwable", "==" + throwable.toString());
                    }
                });

    }

}


