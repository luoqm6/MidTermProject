package com.example.midtermproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by qingming on 2017/10/26.
 */

public class StaticBroadcastReceiver extends BroadcastReceiver{
    private static String STATICACTION="com.example.midtermproject.STATICACTION";
    private static int num=0;
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(STATICACTION)){
            Bundle bundle=intent.getExtras();
            Role tmpR=new Role(bundle);
            //获取状态通知栏管理
            NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //实例化通知栏构造器 Notification.Builder
            Notification.Builder builder=new Notification.Builder(context);
            //为了版本兼容  选择V7包下的NotificationCompat进行构造
            //NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Bitmap bm = BitmapFactory.decodeResource(getResources(context), tmpR.getimgId());
            //对builder进行配置
            builder.setContentTitle("发现新角色"+tmpR.getname()) //设置通知栏标题:发件人
                    .setContentText(tmpR.getcountry()+"国豪杰"+tmpR.getname()+"出现!") //设置通知栏显示内容
                    .setTicker("发现新角色"+tmpR.getname()) //通知首次出现在通知栏,带上升动画效果的
                    .setPriority(Notification.PRIORITY_HIGH) //设置通知优先级
                    .setLargeIcon(bm)// 设置大ICON
                    .setWhen(System.currentTimeMillis()) //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setSmallIcon( tmpR.getimgId())
                    .setAutoCancel(true);//设置这个标志当用户单击面板就可以将通知取消
            //绑定intent，点击图标能够进入某activity
            Intent mInent=new Intent(context,ItemInfo.class);
            mInent.addCategory(Intent.CATEGORY_LAUNCHER);
            //mInent.addCategory(Intent.CATEGORY_DEFAULT);
            Bundle bundle1=tmpR.putinbundle();
            bundle.putInt("whichView",0);
            bundle.putBoolean("isEditable",false);
            mInent.putExtras(bundle1);
            PendingIntent mPendingIntent=PendingIntent.getActivity(context,0,mInent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);
            //绑定Notification，发送通知请求
            Notification notify=builder.build();
            manager.notify(num++,notify);
        }
    }
    public Resources getResources(Context context) {
        return context.getResources();
    }
}
