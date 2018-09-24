package com.example.admin.myapplication.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public  class InternetBroadCastReceiver extends BroadcastReceiver{

    public static ConnectivityReceiverListener receiverListener;

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if(info!=null && info.isConnected())
        {
            receiverListener.onNetworkConnectionChanged(true);
        }
        else
        {
            receiverListener.onNetworkConnectionChanged(false);
        }
    }

}
