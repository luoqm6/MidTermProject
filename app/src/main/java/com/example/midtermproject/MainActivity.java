package com.example.midtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //广播使用的filter
    String STATICACTION="com.example.midtermproject.STATICACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView1=(TextView) findViewById(R.id.tvInMain1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,RoleLists.class);
                startActivityForResult(intent,1);
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

