package com.example.administrator.broadcast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    //本地广播数据类型实例
    private LocalBroadcastManager localBroadcastManager;

    //静态广播的action字符串
    private static final String static_action = "com.bn.pp2.staticreceiver";
    //动态广播的action字符串
    private static final String dynamic_action = "com.bn.pp2.dynamicreceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取本地广播实例
        localBroadcastManager  = LocalBroadcastManager.getInstance(this);

        //新建intentFilter并给其action标签赋值
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.test.sunlin.LOCAL_BROADCAST");

        //创建广播接收器实例并注册，将其接收器与action标签进行绑定
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    //发送本地广播
    public void broadcast(View v){
        Intent intent = new Intent("com.test.sunlin.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(intent);
    }
    @Override
    public void onDestroy(){
        //在onDestroy方法中取消注册
        super.onDestroy();
        //取消注册调用的是unregisterReceiver()方法，并传入接收器实例
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            Toast.makeText(context, "本地广播", Toast.LENGTH_SHORT).show();
        }
    }

    //发送自定义静态注册广播消息
    public void static_broadcast(View v){
        Intent intent = new Intent();
        //设置action
        intent.setAction(static_action);
        intent.setComponent(new ComponentName("com.example.administrator.broadcast",
                "com.example.administrator.broadcast.StaticReceiver"));
        //添加附加信息
        intent.putExtra("msg","静态注册广播");
        //发送Intent
        sendBroadcast(intent);
    }

    //发送自定义动态注册广播消息
    public void dynamic_broadcast(View v){
        Intent intent = new Intent();
        intent.setAction(dynamic_action);
        intent.putExtra("msg","动态注册广播");
        sendBroadcast(intent);
    }

    //动态广播的Receiver
    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //动作检测
            if(intent.getAction().equals(dynamic_action)){
                String msg = intent.getStringExtra("msg");
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStart(){
        super.onStart();
        IntentFilter dynamic_filter = new IntentFilter();
        //添加动态广播的action
        dynamic_filter.addAction(dynamic_action);
        //注册自定义动态广播
        registerReceiver(dynamicReceiver,dynamic_filter);
    }

}
