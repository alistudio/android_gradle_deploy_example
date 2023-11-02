package com.sanqian.d6.android.sdk.common.utils;

import android.util.Log;

import org.egret.egretnativeandroid.EgretNativeAndroid;


public final class RunGameJsUtil {
//    public static void run(String method, String data) {
//    }
    public static EgretNativeAndroid nativeAndroid = null;
    public static void run(String method, String msg) {
        try{
            nativeAndroid.callExternalInterface(method, msg);
        }
        catch (Exception e){
            Log.e("RungGameJsUtil", e.getMessage());
        }
    }
}
