package com.example.midtermproject;

/**
 * Created by qingming on 2017/11/25.
 */
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.logging.LogRecord;
public class Game extends AppCompatActivity {
    private int score = 0;
    //需要寻找的人物的图片id
    private int right_id = 100;
    //点击的imageView的图片id
    private int current_id=0;
    private double start_time;
    //储存每个imageView的图片id；
    private int[] img_index = new int[16];

    private Spinner mspinner;
    private ArrayAdapter<String> adapter;
    private int message = -1;
    private double used_time = 0;
    private SoundPool pool;
    private Map<String,Integer> poolMap;
    private boolean fisrt_in = true;
    private boolean hasSound = false;
    private int show_number = 0;
    private int dinaji_number = 0;
    private int sleep_time = 2000;
    private int[] godlike_tishi = {0,0,0};
    private int[] godlike_tishi1 = {1,0,0};
    private int dianji_show = 0;
    private double pingfen = 0;
    private double jinyanzhi = 0;
    private int Lv = 0;
    private ProgressBar progressbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置为全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        //定义DisplayMetrics对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Bundle bundle;
        bundle=getIntent().getExtras();
        hasSound = bundle.getBoolean("hasSound");


        final  String[] name = {"蔡文姬","曹植","大乔" ,"典韦" ,"貂蝉" ,"郭女王" ,"黄月英","黄忠","华雄","刘禅", "吕布","吕蒙" ,"马超", "司马昭","孙策","孙鲁班", "孙尚香" ,"小乔" ,"赵云", "甄姬"};
        final ImageView []imag_group={(ImageView)findViewById(R.id.r11),(ImageView)findViewById(R.id.r12),(ImageView)findViewById(R.id.r13),(ImageView)findViewById(R.id.r14),
                (ImageView)findViewById(R.id.r21),(ImageView)findViewById(R.id.r22),(ImageView)findViewById(R.id.r23),(ImageView)findViewById(R.id.r24),
                (ImageView)findViewById(R.id.r31),(ImageView)findViewById(R.id.r32),(ImageView)findViewById(R.id.r33),(ImageView)findViewById(R.id.r34),
                (ImageView)findViewById(R.id.r41),(ImageView)findViewById(R.id.r42),(ImageView)findViewById(R.id.r43),(ImageView)findViewById(R.id.r44)};
        final TextView score_id =(TextView)findViewById(R.id.score);
        final Button button = (Button) findViewById(R.id.queren);
        final TextView m_text = (TextView) findViewById(R.id.people);
        final TextView text_time = (TextView) findViewById(R.id.time);
        final  Button button_stop = (Button) findViewById(R.id.stop);
        final TextView show_T_rp = (TextView) findViewById(R.id.show_text_rp);
        final TextView show_lv = (TextView) findViewById(R.id.show_Lvmsg);
        final int []imag_mainip={R.mipmap.caiwenji,R.mipmap.caozhi,R.mipmap.daqiao,R.mipmap.dianwei,R.mipmap.diaochan,R.mipmap.guonvwang,
                R.mipmap.huangyueying,R.mipmap.huangzhong,R.mipmap.huaxiong,R.mipmap.liushan,R.mipmap.lvbu,
                R.mipmap.lvmeng,R.mipmap.machao,R.mipmap.simazhao,R.mipmap.sunce,R.mipmap.sunluban,R.mipmap.sunshangxiang,
                R.mipmap.xiaoqiao,R.mipmap.zhaoyun,R.mipmap.zhenji};
        final int []mp3 = {R.raw.caiwenji,R.raw.caozhi,R.raw.daqiao,R.raw.dianwei,R.raw.diaochan,R.raw.guonvwang,R.raw.huangyueying,R.raw.huangzhong,
                R.raw.huaxiong,R.raw.liushan,R.raw.lvbu,R.raw.lvmeng,R.raw.machao,R.raw.simazhao,R.raw.sunce,R.raw.sunluban,R.raw.sunshangxiang,
                R.raw.xiaoqiao,R.raw.zhaoyun,R.raw.zhenji
        };
        final Random random=new Random();
        //设置音效
        pool = new SoundPool( 4 , AudioManager.STREAM_MUSIC,0);
        poolMap = new HashMap<String, Integer>();
        poolMap.put("start",pool.load(this,R.raw.gamestart,1) );
        poolMap.put("end",pool.load(this,R.raw.gameend,1) );
        poolMap.put("action1",pool.load(this,R.raw.raw_hit_huo2,1) );
        poolMap.put("action2",pool.load(this,R.raw.raw_hit_lei,1));
        poolMap.put("comp",pool.load(this,R.raw.raw_addhp,1));
        poolMap.put("tishi1",pool.load(this,R.raw.unstoppable,1));
        poolMap.put("tishi2",pool.load(this,R.raw.godlike,1));
        poolMap.put("tishi3",pool.load(this,R.raw.legendary,1));
        for (int i = 0;i<20;i++){
            poolMap.put(name[i],pool.load(this,mp3[i],1));
        }
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (sampleId == poolMap.size()) {
                    //Toast.makeText(MainActivity.this, "加载声音池完成!", Toast.LENGTH_SHORT).show();
                    if(hasSound) pool.play(poolMap.get("comp"), 1.0f, 1.0f, 0, 1, 1.0f);
                }
            }
        });

        //下拉框
        mspinner = (Spinner) findViewById(R.id.Spinner1);
        //将可选内容与ArraAdapter连接
        adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,name);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        mspinner.setAdapter(adapter);
        mspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent .setVisibility(View.VISIBLE);
                right_id = position;
                LinearLayout linearLayout =(LinearLayout) findViewById(R.id.Game_Linear);
                linearLayout.setBackgroundResource(imag_mainip[right_id]);
                linearLayout.getBackground().mutate().setAlpha(200);
                m_text.setText("寻找："+name[right_id].toString());

                for(int i=0;i<imag_group.length;i++){
                    imag_group[i].setVisibility(View.INVISIBLE);
                }
                if (fisrt_in){
                    fisrt_in = false;
                }
                else if(hasSound) pool.play(poolMap.get(name[right_id]), 1.0f, 1.0f, 0, 1, 1.0f);
                //Toast.makeText(Game.this, "fisrt_in："+fisrt_in, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                right_id = 100;
            }
        });

        progressbar = (ProgressBar)findViewById(R.id.progressbar);
       // progressbar.setIndeterminate(true);
        progressbar.setProgress(0);
        progressbar.setMax(1000);

        //线程1，刷新图片
        final Handler handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    if(dianji_show!=0){
                        godlike_tishi[0]=0;
                        godlike_tishi[1]=0;
                        godlike_tishi[2]=0;
                        dianji_show=0;
                    }
                    //初始化都可见
                    for(int i=0;i<imag_group.length;i++){
                        imag_group[i].setVisibility(View.VISIBLE);
                    }
                    //随机选择不可见
                    for(int i=0;i<imag_group.length;i++){
                        int po = random.nextInt(20);
                        imag_group[i].setImageResource(imag_mainip[po]);
                        //储存每个imageView的图片id；
                        img_index[i] = po;
                        //获取答案出现次数
                        if (po == right_id) {
                            show_number++;
                            dianji_show++;
                            //Toast.makeText(Game.this, "show_number："+show_number, Toast.LENGTH_SHORT).show();
                        }

                        imag_group[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jinyanzhi += ((Lv+1) * (pingfen/100) );
                                for (int j =0 ;j<imag_group.length;j++){
                                    //判断当前点击的imageView的图片id
                                    if (v == imag_group[j]) current_id = img_index[j];
                                }
                                if(current_id == right_id  ){
                                    score += 1 ;
                                    dianji_show--;
                                    if (used_time>=20){
                                        if(godlike_tishi[0]<5) godlike_tishi[0]++;
                                        if (godlike_tishi[1]<6) godlike_tishi[1]++;
                                        if (godlike_tishi[2]<7) godlike_tishi[2]++;
                                    }

                                    if(show_number>0 && hasSound) {
                                        double tt = (double) (score * 10 / show_number) * 0.1;
                                        if (godlike_tishi[0] == 5 &&used_time>20 && godlike_tishi1[0]==1 && godlike_tishi[2]!=1 && pingfen>=80){
                                            pool.play(poolMap.get("tishi1"),1.0f, 1.0f, 0, 0, 1.0f);
                                            jinyanzhi += (5*(Lv+1));
                                            godlike_tishi[0]=0;

                                            godlike_tishi1[0]=0;
                                            godlike_tishi1[1]=1;
                                        }
                                        else if(godlike_tishi[1] == 6 &&used_time>20 && godlike_tishi1[1]==1 && godlike_tishi[0] != 1 && pingfen>=85) {
                                            pool.play(poolMap.get("tishi2"),1.0f, 1.0f, 0, 0, 1.0f);
                                            jinyanzhi += (6*(Lv+1));
                                            godlike_tishi[1]=0;
                                            godlike_tishi1[1]=0;
                                            godlike_tishi1[2]=1;
                                        }
                                        else if (godlike_tishi[2] == 7 &&used_time>20 && godlike_tishi1[2]==1 && godlike_tishi[1] !=1 &&pingfen>=90) {
                                            pool.play(poolMap.get("tishi3"), 1.0f, 1.0f, 0, 0, 1.0f);
                                            jinyanzhi += (7*(Lv+1));
                                            godlike_tishi[2]=0;

                                            godlike_tishi1[2]=0;
                                            godlike_tishi1[0]=1;
                                        }
                                        else pool.play(poolMap.get("action1"),1.0f, 1.0f, 0, 0, 1.0f);
                                    }

                                }
                                else if(hasSound) {
                                    pool.play(poolMap.get("action2"),1.0f, 1.0f, 0, 0, 1.0f);
                                    godlike_tishi[0]=0;
                                    godlike_tishi[1]=0;
                                    godlike_tishi[2]=0;

                                    godlike_tishi1[0]=1;
                                    godlike_tishi1[1]=0;
                                    godlike_tishi1[2]=0;
                                }
                                v.setVisibility(v.INVISIBLE);
                                dinaji_number++;

                                //Toast.makeText(Game.this, "jinyanzhi："+jinyanzhi, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    int po1 =random.nextInt(16);
                    for (int j=0;j<po1;j++){
                        int po2 =random.nextInt(16);
                        //找到一个不是正确答案的图片隐藏
                        while (img_index[po2] == right_id ){
                            po2 =random.nextInt(16);
                        }
                        imag_group[po2].setVisibility(imag_group[po2].INVISIBLE);
                    }
                    //控制图片刷新速度
                    if(show_number>0) {
                        double tt = (double) (score * 10 / show_number) * 0.1;
                        if (tt > 0.8 && used_time > 20) sleep_time *= java.lang.Math.sqrt(0.8 / tt);
                        else if (tt < 0.3 && used_time > 20) sleep_time *= java.lang.Math.sqrt(0.3 / (tt > 0.3 ? tt : 0.3));
                        else if (tt<0.8 && tt>0.3) sleep_time=2000;
                    }
                }
                else if (msg.what == -1){
                    //初始化都可见
                    //for(int i=0;i<imag_group.length;i++){
                    //   imag_group[i].setVisibility(View.VISIBLE);
                    //}
                    //放置图片
                    //for(int i=0;i<imag_group.length;i++){
                    //    imag_group[i].setImageResource(imag_mainip[i]);
                    // }
                }
            }
        };
        final Thread mthread=new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    handler.obtainMessage(message).sendToTarget();
                    try {
                        Thread.sleep(sleep_time);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        //线程2，计时
        final Handler handler_time = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == -1 ){
                    text_time.setText("已用时间："+used_time +"s");
                    //show_lv.setText("Lv"+Lv+" ");

                    show_T_rp.setText("命中率："+(show_number>0 ?(double)(score*100/show_number) : 0 )+"%  正确率："+
                    (dinaji_number>0 ?(double)(score*100/dinaji_number) : 0 )+"% 评分："+(int)pingfen);
                }
                else if (msg.what == 1 ){
                    double current_time = System.currentTimeMillis();
                    used_time = (current_time-start_time)/1000;
                    text_time.setText("已用时间："+used_time +"s");

                    double ttt = ( show_number>0 ? (double) (score * 10 / show_number) * 0.1 : 0 );
                    double ttt1 = (dinaji_number>0 ?(double)(score * 10 /dinaji_number)*0.1 : 0 );
                    pingfen = 30*ttt + ttt1 * 30 + 40;
                            //40*(  java.lang.Math.sqrt( used_time)/used_time ) ;

                    show_T_rp.setText("命中率："+(show_number>0 ?(double)(score*100/show_number) : 0 )+"%  正确率："+
                            (dinaji_number>0 ?(double)(score*100/dinaji_number) : 0 )+"% 评分："+(int)pingfen);
                    score_id.setText("分数："+score);

                    double lv_temp=0;
                    if (jinyanzhi>10) lv_temp = java.lang.Math.log(jinyanzhi/10)/java.lang.Math.log(2);
                    Lv = (int )lv_temp;
                    int tmp_max = (int)(10*java.lang.Math.pow(2,Lv+1));
                    if (tmp_max>10) progressbar.setMax(tmp_max);
                    progressbar.setProgress((int)jinyanzhi);

                    show_lv.setText("Lv"+Lv+"  "+(int)jinyanzhi+"/"+tmp_max);

                }
            }
        };
        final Thread time_thread = new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    handler_time.obtainMessage(message).sendToTarget();
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        mthread.start();
        time_thread.start();
        //Lv=0;jinyanzhi=0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message == -1 ){
                    message = 1;
                    //Toast.makeText(MainActivity.this, "寻找："+name[right_id].toString(), Toast.LENGTH_SHORT).show();
                    start_time = System.currentTimeMillis();
                    used_time = 0;
                    show_number = 0;
                    score = 0 ;
                    dinaji_number = 0;
                    pingfen = 0;
                }
                else if (message == 1){
                    Toast.makeText(Game.this, "请先点击停止按钮！", Toast.LENGTH_SHORT).show();
                };
                if(hasSound) pool.play(poolMap.get("start"),1.0f, 1.0f, 0, 0, 1.0f);
            }
        });
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = -1;
                if(hasSound) pool.play(poolMap.get("end"),1.0f, 1.0f, 0, 0, 1.0f);
            }
        });

        Button buttonQuit = (Button) findViewById(R.id.quit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.release();
                pool=null;
                Intent intent=new Intent();
                intent.setClass(Game.this,MainActivity.class);
                //回到RoleLists
                setResult(1,intent);
                finish();
            }
        });
    }
}
