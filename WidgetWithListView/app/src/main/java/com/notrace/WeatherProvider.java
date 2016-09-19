package com.notrace;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by notrace on 2016/9/12.
 */
public class WeatherProvider extends AppWidgetProvider {

    public static String CLICK_ACTION = "com.notrace.android.weatherlistwidget.CLICK";
    public static String REFRESH_ACTION = "com.notrace.android.weatherlistwidget.REFRESH";
    public static String EXTRA_CITY_ID = "com.notrace.android.weatherlistwidget.city";

    @Override
    public void onEnabled(Context context) {

        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
         String action = intent.getAction();
        if (action.equals(CLICK_ACTION)) {
            final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            final String city = intent.getStringExtra(EXTRA_CITY_ID);
            final String formatStr = context.getResources().getString(R.string.toast_format_string);
            Toast.makeText(context, String.format(formatStr, city), Toast.LENGTH_SHORT).show();
        }
        else if(action.equals(REFRESH_ACTION))
        {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, WeatherProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.weather_list);

        }
        super.onReceive(context, intent);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {

            final Intent intent = new Intent(context, WeatherWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.weather_list, intent);

            rv.setEmptyView(R.id.weather_list, R.id.empty_view);

            final Intent onClickIntent = new Intent(context, WeatherProvider.class);
            onClickIntent.setAction(WeatherProvider.CLICK_ACTION);
            onClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            onClickIntent.setData(Uri.parse(onClickIntent.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0,
                    onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.weather_list, onClickPendingIntent);



            final Intent refreshIntent=new Intent(context,WeatherProvider.class);

            refreshIntent.setAction(WeatherProvider.REFRESH_ACTION);
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds[i]);
            refreshIntent.setData(Uri.parse(refreshIntent.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                    refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.tv_widget_refresh, refreshPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
