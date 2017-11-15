package com.example.midtermproject;

/*
 * Created by qingming on 2017/11/13.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class RoleLists extends AppCompatActivity {
    /*所有的人物信息*/
    final String []name={"刘备","曹操",
            "孙权","关羽",
            "张飞","诸葛亮",
            "周瑜","司马懿",
            "张辽","甘宁"};
    final String []nickname={"字玄德","字孟德",
            "字仲谋","字云长",
            "字益德","字孔明",
            "字公瑾","字仲达",
            "字文远","字兴霸"};
    final String []country={"蜀","魏","吴","蜀","蜀","蜀","吴","魏","魏","吴"};
    final String []period={"161年－223年6月10日","155年－220年3月15日",
            "182年－252年5月21日","？－220年",
            "？－221年","181年-234年10月8日",
            "175年-210年","179年—251年9月7日",
            "169年－222年","？—220年"};
    final String []sex={"男","男","男","男","男","男","男","男","男","男"};
    final String []hometown={"幽州涿郡涿县（今河北省涿州市）人", "沛国谯县（今安徽亳州）人",
            "吴郡富春（今浙江杭州富阳区）人", "河东郡解县（今山西运城）人",
            "幽州涿郡（今河北省保定市涿州市）人", "徐州琅琊阳都（今山东临沂市沂南县）人",
            "庐江舒（今合肥庐江舒县）人", "河内郡温县孝敬里（今河南省焦作市温县）人",
            "雁门马邑（今山西朔州）人", "巴郡临江（今重庆忠县）人"};
    final String []intro={"幽州涿郡涿县（今河北省涿州市）人", "沛国谯县（今安徽亳州）人",
            "吴郡富春（今浙江杭州富阳区）人", "河东郡解县（今山西运城）人",
            "幽州涿郡（今河北省保定市涿州市）人", "徐州琅琊阳都（今山东临沂市沂南县）人",
            "庐江舒（今合肥庐江舒县）人", "河内郡温县孝敬里（今河南省焦作市温县）人",
            "雁门马邑（今山西朔州）人", "巴郡临江（今重庆忠县）人"};
    final int []imgId={R.mipmap.liubei,R.mipmap.caocao,
            R.mipmap.sunquan,R.mipmap.guanyu,
            R.mipmap.zhangfei,R.mipmap.zhugeliang,
            R.mipmap.zhouyu,R.mipmap.simayi,
            R.mipmap.zhangliao,R.mipmap.ganning};

    /*为了回调函数使用这些变量向购物车列表添加商品，声明放在外面*/
    final List<Map<String,Role>> listItems1 = new ArrayList<>();
    final List<Map<String,Role>> listItems2 = new ArrayList<>();
    private List<Map<String,Object>> simpleListItems2 = Role.getSimpleList(listItems2);
    Map<String,Role> s;
    SimpleAdapter simpleAdapter;

    //这两个声明放在onCreate外面不能初始化byid！！！！！！！！！！！！！！！！！！！！！！！！！！
    private FloatingActionButton fab1 ;
    private FloatingActionButton fab2 ;
    //这两个声明放在onCreate外面不能初始化byid！！！！！！！！！！！！！！！！！！！！！！！！！！

    //广播使用的filter
    String STATICACTION="com.example.midtermproject.STATICACTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolelists);


        //商品列表的初始化
        //final List<Map<String,Object>> listItems1 = new ArrayList<>();
        for(int i=0;i<name.length;i++){
            Map<String,Role> tmp = new LinkedHashMap<>();
            Role tmpR = new Role(country[i],name[i],nickname[i],period[i],sex[i],hometown[i],hometown[i],imgId[i],false);
            tmp.put("Role",tmpR);
            listItems1.add(tmp);
        }



        //eventbus的注册
        EventBus.getDefault().register(this);



        //两个右下角按钮的初始化以及将进入页面初始化为商品列表
        fab1 = (FloatingActionButton) findViewById(R.id.fabSL1);
        fab2 = (FloatingActionButton) findViewById(R.id.fabSL2);
        changeToRecyclerView();


        //发送静态广播推送推荐商品
        BroadcastStatic(STATICACTION);


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToListView();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToRecyclerView();
            }
        });


        //RecyclerView实现物品清单begin/////////////////
        final RecyclerView mRecyclerView;
        final CommonAdapter recycleAdapter;
        final RecyclerView.LayoutManager mLayoutManager;



        mLayoutManager = new LinearLayoutManager(this);
        recycleAdapter = new CommonAdapter(this,R.layout.recyclerviewitem,listItems1){
            @Override
            public void convert(ViewHolder holder,Map<String,Role> s){
                TextView itemCountryInR = holder.getView(R.id.itemCountryInR);
                itemCountryInR.setText(s.get("Role").getcountry());
                TextView itemNameInR = holder.getView(R.id.itemNameInR);
                itemNameInR.setText(s.get("Role").getname());
//                ImageView itemImgInR = holder.getView(R.id.itemImgInR);
//                itemImgInR.setImageResource(s.get("Role").getimgId());
            }
        };

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recycleAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view,int position) {
                s=listItems1.get(position);
                Role tmpR=s.get("Role");
                //跳转至商品详情界面
                Bundle bundle = tmpR.putInBundle();
                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(RoleLists.this,ItemInfo.class);
                startActivityForResult(intent,1);
            }
            @Override
            public boolean onLongClick(View view,int position) {
                s=listItems1.get(position);
                Snackbar.make( view,"英雄 " + s.get("Role").getname() + "从列表移除", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                listItems1.remove(position);
                recycleAdapter.notifyItemRemoved(position);
                recycleAdapter.notifyItemChanged(position);
                return true;
            }
        });



        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        ScaleInAnimationAdapter animationAdapter=new ScaleInAnimationAdapter(recycleAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
        //mRecyclerView.setAdapter(recycleAdapter);/////
        //RecyclerView实现物品清单end/////////////////////////////



        //ListView实现购物车清单begin/////////////////////////////


        final ListView listview = (ListView) findViewById(R.id.ListView);
        simpleListItems2 = Role.getSimpleList(listItems2);
        simpleAdapter = new SimpleAdapter(
                this,simpleListItems2,R.layout.listviewitem,
                new String[]{"country","name"},new int []{R.id.itemCountryInL,R.id.itemNameInL});
        listview.setAdapter(simpleAdapter);

        final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if(position==0) return ;
                s=listItems2.get(position);
                Role tmpR=listItems2.get(position).get("Role");
                Bundle bundle = tmpR.putInBundle();
                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(RoleLists.this,ItemInfo.class);
                startActivityForResult(intent,1);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                //if(position==0) return true;
                s=listItems2.get(position);
                //购物车界面长按删除提示信息
                alterDialog.setTitle("逐出英雄").setMessage("从麾下逐出"+s.get("Role").getname()+"?").setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                ).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make( view,"英雄 " + s.get("Role").getname() + "从麾下逐出", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                listItems2.remove(position);
                                simpleListItems2.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        }
                ).create();
                alterDialog.show();
                return true;
            }
        });
        //ListView实现购物车清单end//////////////////////////////////
    }

    /*回调函数，当从子界面回到这个父界面时调用，如果回传数据不为空也即是按下加入购物车则更新购物车列表*/
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==1){
            if(resultCode==1){
                Bundle bundle=getIntent().getExtras();
                if(bundle!=null){
                    if(bundle.getInt("whichView")==0){
                        changeToRecyclerView();
                    }
                    else{
                        changeToListView();
                    }
                }
            }
        }
    }
    public void BroadcastStatic(String message){
        Random random=new Random();
        int index=random.nextInt(10);
        Role tmpR=listItems1.get(index).get("Role");
        Bundle bundle = tmpR.putInBundle();
        Intent intentBroadcast = new Intent(message);
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);
    }


    //声明并重写自己的订阅函数
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        Role tmpR=messageEvent.getRole();
        Map<String,Role> tmp = new LinkedHashMap<>();
        tmp.put("Role",tmpR);
        listItems2.add(tmp);
        Map<String,Object> tmp1 = new LinkedHashMap<>();
        tmp1.put("country",tmpR.getcountry());
        tmp1.put("name",tmpR.getname());
        tmp1.put("imgId",tmpR.getimgId());
        simpleListItems2.add(tmp1);
        simpleAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            if(bundle.getInt("whichView")==0){
                changeToRecyclerView();
            }
            else{
                changeToListView();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void changeToRecyclerView(){
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.VISIBLE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.GONE);
        TextView tv1 = (TextView) findViewById(R.id.head1) ;
        tv1.setVisibility(View.VISIBLE);
        TextView tv2 = (TextView) findViewById(R.id.head2) ;
        tv2.setVisibility(View.GONE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.GONE);
    }

    public void changeToListView(){
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.GONE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.VISIBLE);
        TextView tv1 = (TextView) findViewById(R.id.head1) ;
        tv1.setVisibility(View.GONE);
        TextView tv2 = (TextView) findViewById(R.id.head2) ;
        tv2.setVisibility(View.VISIBLE);
        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.VISIBLE);
    }
}
