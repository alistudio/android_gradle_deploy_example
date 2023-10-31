package com.sanqian.d6.android.sdk.common.utils;

import android.util.Log;

import org.egret.egretnativeandroid.EgretNativeAndroid;


public final class RunGameJsUtil {
//    public static void run(String method, String data) {
//    }
    public static EgretNativeAndroid nativeAndroid = null;
    public static void run(String method, String msg) {
        nativeAndroid.callExternalInterface(method, msg);
    }
}
