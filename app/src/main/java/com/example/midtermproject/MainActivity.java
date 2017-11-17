package com.example.midtermproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //广播使用的filter
    String STATICACTION="com.example.midtermproject.STATICACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton imageButton1 = (ImageButton) findViewById(R.id.img_people);
        imageButton1.setAlpha(98);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RoleLists.class);
                startActivityForResult(intent, 1);
            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.img_web);
        imageButton2.setAlpha(98);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://cd.e3ol.com/book.asp");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final String mitems[] = {"点击 开/关 背景音乐", "点击 开/关 音效"};
        final boolean set_act[] = {false, false};//设置两个bool变量控制音乐、音效
        alertDialog.setTitle("词典设置")
                .setItems(mitems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mitems[i].equals("背景音乐")) {
                            if (set_act[i] == false) {
                                set_act[i] = true;
                                Toast.makeText(getApplicationContext(), "开启背景音乐", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MusicServer.class);
                                startService(intent);
                            } else if (set_act[i] == true) {
                                set_act[i] = false;
                                Toast.makeText(getApplicationContext(), "关闭背景音乐", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MusicServer.class);
                                stopService(intent);
                            }
                        } else if (mitems[i].equals("音效")) {
                            if (set_act[i] == false) {
                                set_act[i] = true;
                                Toast.makeText(getApplicationContext(), "开启音效", Toast.LENGTH_SHORT).show();
                            } else if (set_act[i] == true) {
                                set_act[i] = false;
                                Toast.makeText(getApplicationContext(), "关闭音效", Toast.LENGTH_SHORT).show();
                            }
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

        ImageView imageButton3 = (ImageView) findViewById(R.id.img_set);
        imageButton3.setAlpha(98);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }
//    public void BroadcastStatic(String message){
//        Random random=new Random();
//        int index=random.nextInt(10);
//        Role tmpR=listItems1.get(index).get("Role");
//        Bundle bundle = tmpR.putInBundle();
//        Intent intentBroadcast = new Intent(message);
//        intentBroadcast.putExtras(bundle);
//        sendBroadcast(intentBroadcast);
//    }
}

