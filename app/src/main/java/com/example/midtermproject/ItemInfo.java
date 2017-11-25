package com.example.midtermproject;

/*
 * Created by qingming on 2017/10/26.
 */


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItemInfo extends AppCompatActivity {

    final String []name={
            "刘备","曹操",
            "孙权","关羽",
            "张飞","诸葛亮",
            "周瑜","司马懿",
            "张辽","甘宁",

            "大乔","孙尚香",
            "甄姬","小乔",
            "蔡文姬","貂蝉",
            "黄忠","赵云",
            "马超","吕布",

            "吕蒙","典韦",
            "黄月英","孙策",
            "曹植","刘禅",
            "孙鲁班","郭女王",
            "司马昭","华雄"
    };
    final int []imgId={
            R.mipmap.liubei,R.mipmap.caocao,
            R.mipmap.sunquan,R.mipmap.guanyu,
            R.mipmap.zhangfei,R.mipmap.zhugeliang,
            R.mipmap.zhouyu,R.mipmap.simayi,
            R.mipmap.zhangliao,R.mipmap.ganning,

            R.mipmap.daqiao,R.mipmap.sunshangxiang,
            R.mipmap.zhenji,R.mipmap.xiaoqiao,
            R.mipmap.caiwenji,R.mipmap.diaochan,
            R.mipmap.huangzhong,R.mipmap.zhaoyun,
            R.mipmap.machao,R.mipmap.lvbu,

            R.mipmap.lvmeng,R.mipmap.dianwei,
            R.mipmap.huangyueying,R.mipmap.sunce,
            R.mipmap.caozhi,R.mipmap.liushan,
            R.mipmap.sunluban,R.mipmap.guonvwang,
            R.mipmap.simazhao,R.mipmap.huaxiong
    };

    /*为了方便重写手机自带返回键返回这两个变量放在外面*/
    private Bundle bundle;
    private Role curR=new Role();
    private int whichView=0;
    private boolean isEditable=false;
    private static final String DYNAMICACTION = "com.example.midtermproject.DYNAMICACTION";
    DynamicBroadcastReceiver dynamicBroadcastReceiver=new DynamicBroadcastReceiver();


    //对话框声明
    private AlertDialog.Builder alertDialogRole;
    private AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);


        //注册动态广播关键代码:
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION);
        registerReceiver(dynamicBroadcastReceiver,dynamic_filter);

        //设定商品详情界面的信息
        bundle=getIntent().getExtras();
        whichView=bundle.getInt("whichView");
        isEditable=bundle.getBoolean("isEditable");
        curR=new Role(bundle);
        setUIInfo();
        setUIInfoEdit(isEditable);
        final ImageView favoriteImg=(ImageView) findViewById(R.id.StarInfo);
        if(!curR.getfavorite()){
            favoriteImg.setImageResource(R.mipmap.empty_star);
        }
        else{
            favoriteImg.setImageResource(R.mipmap.full_star);
        }


        /*最下方更改按键*/
        final TextView editBtnInfo = (TextView) findViewById(R.id.editBtnInfo);
        editBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editBtnInfo.getText().equals("更改")){
                    editBtnInfo.setText("确定");
                    setUIInfoEdit(true);
                }
                else{
                    editBtnInfo.setText("更改");
                    updateRole();
                    setUIInfoEdit(false);
                    Toast.makeText(getApplicationContext(),"人物"+curR.getname()+"已经更新", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*长按图片选择图片的图片选择框*/
        alertDialogRole = new AlertDialog.Builder(this);
        alertDialogRole.setTitle("请选择人物图片")
                .setItems(name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (name[i].equals(name[i])) {
                            ImageView imageView = (ImageView) findViewById(R.id.ItemImgInfo);
                            imageView.setImageResource(imgId[i]);
                            ScrollView sv=(ScrollView) findViewById(R.id.scrollViewInfo);
                            sv.setBackgroundResource(imgId[i]);
                            sv.getBackground().mutate().setAlpha(50);
                            curR.setimgId(imgId[i]);
                        }
                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplication(), "你点击了取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                .create();

        /*长按人物图片提示图片增加方法*/
//        alertDialog = new AlertDialog.Builder(this);
//        final String mitems[] = {"从内置数据中添加", "自行设置"};
//        alertDialog.setTitle("添加图片方式")
//                .setItems(mitems, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (mitems[i].equals("从内置数据中添加")) {
//                            alertDialogRole.show();
//
//                        } else if (mitems[i].equals("自行设置")) {
//                            Toast.makeText(getApplication(), "暂不支持", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .setNegativeButton("取消",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(getApplication(), "你点击了取消", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                .create();


        /*左上角返回按键返回主表，若加入购物车则带回数据*/
        ImageView backImg=(ImageView) findViewById(R.id.backBtnInfo);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ItemInfo.this,RoleLists.class);
                returnMain(intent);
            }
        });

        /*z点购物车将商品加入购物车列表*/
        ImageView shopListImg=(ImageView) findViewById(R.id.recruit);
        shopListImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"英雄已经收入麾下",Toast.LENGTH_SHORT).show();
                //点击购物车图标时候，传递商品信息
                EventBus.getDefault().post(new MessageEvent(curR));
                //点击购物车图标时候，发送动态广播
                BroadcastDynamic(DYNAMICACTION);
            }
        });
        /*z点星星收藏商品*/
        favoriteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!curR.getfavorite()){
                    favoriteImg.setImageResource(R.mipmap.full_star);
                    Toast.makeText(getApplicationContext(),"角色已收藏",Toast.LENGTH_SHORT).show();
                    curR.setfavorite(true);
                }
                else{
                    favoriteImg.setImageResource(R.mipmap.empty_star);
                    Toast.makeText(getApplicationContext(),"角色已取消收藏",Toast.LENGTH_SHORT).show();
                    curR.setfavorite(false);
                }
            }
        });
    }

    /*重写手机自带的返回键返回主表，若加入购物车则带回数据*/
    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent keyEvent){
        if(KeyCode== KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(ItemInfo.this,RoleLists.class);
            returnMain(intent);
        }
        return true;
    }

    public void returnMain(Intent intent){
        //注销动态广播关键代码
        unregisterReceiver(dynamicBroadcastReceiver);
        Bundle bun;
        Role curRole = updateRole();
        bun = curRole.putInBundle();
        bun.putInt("whichView",whichView);
        intent.putExtras(bun);
        //回到RoleLists
        setResult(1,intent);
        finish();
        //startActivity(intent);
    }

    //根据当前界面更新Role的数据
    public Role updateRole(){
        Role tmp=new Role(curR);
        TextView tv=(TextView) findViewById(R.id.nameInfo);
        tmp.setname(tv.getText().toString());
        tv=(TextView) findViewById(R.id.nicknameInfo);
        tmp.setnickname(tv.getText().toString());
        EditText et=(EditText) findViewById(R.id.periodInfo);
        tmp.setperiod(et.getText().toString());
        et=(EditText) findViewById(R.id.sexInfo);
        tmp.setsex(et.getText().toString());
        et=(EditText) findViewById(R.id.countryInfo);
        tmp.setcountry(et.getText().toString());
        et=(EditText) findViewById(R.id.hometownInfo);
        tmp.sethometown(et.getText().toString());
        et=(EditText) findViewById(R.id.introInfo);
        if(et.getText().toString().length()>8) tmp.setintro(et.getText().toString().substring(8));
        else tmp.setintro(et.getText().toString());
        return tmp;
    }

    public void setUIInfo(){
        //设定商品详情界面的信息
        EditText et=(EditText) findViewById(R.id.nameInfo);
        et.setText(curR.getname());
        et=(EditText) findViewById(R.id.nicknameInfo);
        et.setText(curR.getnickname());
        et=(EditText) findViewById(R.id.periodInfo);
        et.setText(curR.getperiod());
        et=(EditText) findViewById(R.id.sexInfo);
        et.setText(curR.getsex());
        et=(EditText) findViewById(R.id.countryInfo);
        et.setText(curR.getcountry());
        et=(EditText) findViewById(R.id.hometownInfo);
        et.setText(curR.gethometown());
        et=(EditText) findViewById(R.id.introInfo);
        et.setText("        "+curR.getintro());
        ImageView iv=(ImageView) findViewById(R.id.ItemImgInfo);
        if(curR.getimgId()>0)iv.setImageResource(curR.getimgId());
        else iv.setImageResource(R.mipmap.beijing1);
        ScrollView sv=(ScrollView) findViewById(R.id.scrollViewInfo);
        sv.setBackgroundResource(curR.getimgId());
        sv.getBackground().mutate().setAlpha(50);
        final ImageView favoriteImg=(ImageView) findViewById(R.id.StarInfo);
        if(!curR.getfavorite()){
            favoriteImg.setImageResource(R.mipmap.empty_star);
        }
        else{
            favoriteImg.setImageResource(R.mipmap.full_star);
        }
    }
    public void setUIInfoEdit(boolean bool){
        //设定商品详情界面的信息
        EditText et=(EditText) findViewById(R.id.nameInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        et=(EditText) findViewById(R.id.nicknameInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        et=(EditText) findViewById(R.id.periodInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        et=(EditText) findViewById(R.id.sexInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        et=(EditText) findViewById(R.id.countryInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        et=(EditText) findViewById(R.id.hometownInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        et=(EditText) findViewById(R.id.introInfo);
        et.setFocusable(bool);
        et.setFocusableInTouchMode(bool);
        et.setClickable(bool);
        et.setBackgroundResource(R.drawable.edittextbackgrd);
        TextView editBtnInfo = (TextView) findViewById(R.id.editBtnInfo);
        if(!bool) {
            editBtnInfo.setText("更改");
        }
        else{
            editBtnInfo.setText("确定");
        }
        ImageView iv=(ImageView) findViewById(R.id.ItemImgInfo);
        if(curR.getimgId()>0)iv.setImageResource(curR.getimgId());
        else iv.setImageResource(R.mipmap.beijing1);
        if(bool){
            //长按图片设置
            iv=(ImageView) findViewById(R.id.ItemImgInfo);
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    alertDialogRole.show();
                    return true;
                }
            });
        }
        else{
            //长按图片设置
            iv=(ImageView) findViewById(R.id.ItemImgInfo);
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }

    }
    public void BroadcastDynamic(String message){
        Bundle bundle = curR.putInBundle();
        Intent intentBroadcast = new Intent(message);
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);
    }
}
