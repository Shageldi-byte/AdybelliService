package com.android.adybelliservice.StoreApplication.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.StoreApplication.Model.Profile.CountResponse;
import com.android.adybelliservice.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WidgetProvider extends AppWidgetProvider {
    ArrayList<Integer> widgetIds=new ArrayList<>();
    RemoteViews views;
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            views = new RemoteViews(context.getPackageName(), R.layout.store_widget);
            views.setTextViewText(R.id.phone,Utils.getSharedPreference(context,"phone"));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.phone);

            ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
            Call<GBody<CountResponse>> call=apiInterface.getSellerProductCount("Bearer "+Utils.getSharedPreference(context,"token"),
                    "5c249d738fc4a4ae6f2844c0b744e06a");
            call.enqueue(new Callback<GBody<CountResponse>>() {
                @Override
                public void onResponse(Call<GBody<CountResponse>> call, Response<GBody<CountResponse>> response) {
                    if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                        views.setTextViewText(R.id.count_1,response.body().getBody().getAccepted_products()+"");
                        views.setTextViewText(R.id.count_2,response.body().getBody().getPending_products()+"");
                        views.setTextViewText(R.id.count_3,response.body().getBody().getCanceled_products()+"");
                        final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                        final ComponentName cn = new ComponentName(context, WidgetProvider.class);
                        mgr.updateAppWidget(cn, views);
                    }
                }

                @Override
                public void onFailure(Call<GBody<CountResponse>> call, Throwable t) {

                }
            });
            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }



    }
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}
