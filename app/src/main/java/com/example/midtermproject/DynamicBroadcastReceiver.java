package com.example.midtermproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

/*
 * Created by qingming on 2017/10/28.
 */

public class DynamicBroadcastReceiver extends BroadcastReceiver{
    private static final String DYNAMICACTION = "com.example.midtermproject.DYNAMICACTION";
    private static int num;
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(DYNAMICACTION)){
            Bundle bundle = intent.getExtras();
            Role tmpR=new Role(bundle);
            //获取状态通知栏管理
            NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //实例化通知栏构造器 Notification.Builder
            Notification.Builder builder=new Notification.Builder(context);
            //为了版本兼容  选择V7包下的NotificationCompat进行构造
            //NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),tmpR.getimgId());
            //对builder进行配置
            builder.setContentTitle("查看麾下英雄！") //设置通知栏标题:发件人
                    .setContentText(tmpR.getname()+"已收入麾下！") //设置通知栏显示内容
                    .setTicker("查看麾下英雄！") //通知首次出现在通知栏,带上升动画效果的
                    .setPriority(Notification.PRIORITY_HIGH) //设置通知优先级
                    .setLargeIcon(bm)// 设置大ICON
                    .setWhen(System.currentTimeMillis()) //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setSmallIcon(tmpR.getimgId())
                    .setAutoCancel(true);//设置这个标志当用户单击面板就可以将通知取消
            //绑定intent，点击图标能够进入某activity
            Intent mInent=new Intent(context,RoleLists.class);
            //mInent.addCategory(Intent.CATEGORY_LAUNCHER);
            mInent.addCategory(Intent.CATEGORY_DEFAULT);
            bundle=tmpR.putinbundle();
            bundle.putInt("whichView",1);
            mInent.putExtras(bundle);
            mInent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent mPendingIntent=PendingIntent.getActivity(context,0,mInent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);
            //绑定Notification，发送通知请求
            Notification notify=builder.build();
            manager.notify(num++,notify);

            //动态广播设置widget根据加入购物车商品催下单
            //绑定intent，点击图标能够进入某activity
            mPendingIntent=PendingIntent.getActivity(context,0,mInent,PendingIntent.FLAG_UPDATE_CURRENT);
            //实例化RemoteView,其对应相应的Widget布局
            RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.example_app_widget_provider);
            //获取AppWidgetManager实例
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName me=new ComponentName(context,ExampleAppWidgetProvider.class);
            //设置widget的文字为传入商品相关信息
            updateViews.setTextViewText(R.id.appwidget_text,"查看麾下英雄！\n"+tmpR.getname()+"已收入麾下！");
            //设置widget的图片为传入商品图片
            updateViews.setImageViewResource(R.id.widgetImg,tmpR.getimgId());
            updateViews.setOnClickPendingIntent(R.id.widgetView,mPendingIntent);

            //更新widget显示信息
            appWidgetManager.updateAppWidget(me,updateViews);

        }
    }
}
