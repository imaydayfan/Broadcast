package com.example.administrator.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StaticReceiver extends BroadcastReceiver{
    //静态广播接收器执行的方法
    @Override
    public void onReceive(Context context, Intent intent){
        String msg = intent.getStringExtra("msg");
        Log.e("static_broadcast","静态注册广播2");
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
