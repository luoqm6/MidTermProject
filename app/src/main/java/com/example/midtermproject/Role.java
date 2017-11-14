package com.example.midtermproject;

import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by qingming on 2017/10/26.
 */

public class Role implements Serializable{
    private String country;//角色所属的势力
    private String name;//角色名字
    private String nickname;//角色的字
    private String period;//生卒年月
    private String sex;//性别
    private String hometown;//籍贯
    private String intro;//简介
    private int imgId;//商品对应的图片id
    private boolean favorite;//标记是否被收藏
    public Role(){
        this.name="";
        this.nickname="";
        this.period="";
        this.sex="";
        this.country="";
        this.hometown="";
        this.intro="";
        this.imgId=0;
        this.favorite=false;
    }
    public Role(Bundle bundle){
        setByBundle(bundle);
    }
    public Role(String country,String name,String nickname,String period,String sex,String hometown,String intro,int imgId,boolean favorite){
        this.country=country;
        this.name=name;
        this.nickname=nickname;
        this.period=period;
        this.sex=sex;
        this.hometown=hometown;
        this.intro=intro;
        this.imgId=imgId;
        this.favorite=favorite;
    }
    public Role(Role role){
        this.country=role.getcountry();
        this.name=role.getname();
        this.period=role.getperiod();
        this.sex=role.getsex();
        this.hometown=role.gethometown();
        this.imgId=role.getimgId();
        this.favorite=role.getfavorite();
    }
    public String getcountry(){
        return country;
    }
    public String getname(){
        return name;
    }
    public String getnickname(){
        return nickname;
    }
    public String getperiod(){
        return period;
    }
    public String getsex(){
        return sex;
    }
    public String gethometown(){
        return hometown;
    }
    public String getintro(){
        return intro;
    }
    public int getimgId(){
        return imgId;
    }
    public boolean getfavorite(){
        return favorite;
    }
    //////////////////////////
    public void setcountry(String country){
        this.country=country;
    }
    public void setname(String name){
        this.name=name;
    }
    public void setnickname(String nickname){
        this.nickname=nickname;
    }
    public void setperiod(String period){
        this.period=period;
    }
    public void setsex(String sex){
        this.sex=sex;
    }
    public void sethometown(String hometown){
        this.hometown=hometown;
    }
    public void setintro(String intro){
        this.intro=intro;
    }
    public void setimgId(int imgId){
        this.imgId=imgId;
    }
    public void  setfavorite(boolean favorite){
        this.favorite=favorite;
    }
    public Bundle putinbundle(){
        Bundle bundle = new Bundle();
        bundle.putString("country",getcountry());
        bundle.putString("name",getname());
        bundle.putString("nickname",getnickname());
        bundle.putString("period",getperiod());
        bundle.putString("sex",getsex());
        bundle.putString("hometown",gethometown());
        bundle.putString("intro",getintro());
        bundle.putInt("imgId",getimgId());
        bundle.putBoolean("favorite",getfavorite());
        return bundle;
    }
    public Bundle putInBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("country",getcountry());
        bundle.putString("name",getname());
        bundle.putString("nickname",getnickname());
        bundle.putString("period",getperiod());
        bundle.putString("sex",getsex());
        bundle.putString("hometown",gethometown());
        bundle.putString("intro",getintro());
        bundle.putInt("imgId",getimgId());
        bundle.putBoolean("favorite",getfavorite());
        return bundle;
    }
    public void setByBundle(Bundle bundle){
        this.country = bundle.getString("country");
        this.name = bundle.getString("name");
        this.nickname=bundle.getString("nickname");
        this.period=bundle.getString("period");
        this.sex=bundle.getString("sex");
        this.hometown=bundle.getString("hometown");
        this.intro=bundle.getString("intro");
        this.imgId=bundle.getInt("imgId");
        this.favorite=bundle.getBoolean("favorite");
    }
    public static List<Map<String,Object>> getSimpleList(List<Map<String,Role>> listItems2){
        List<Map<String,Object>> simpleListItems2=new ArrayList<>();
        for(int i=0;i<listItems2.size();i++){
            Map<String,Object> tmp=new LinkedHashMap<>();
            tmp.put("country",listItems2.get(i).get("Role").getcountry());
            tmp.put("name",listItems2.get(i).get("Role").getname());
            tmp.put("imgId",listItems2.get(i).get("Role").getimgId());
            simpleListItems2.add(tmp);
        }
        return simpleListItems2;
    }
}