package com.sanqian.d6.android;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Utils {
    public static void copyToClipBoard(Context context, String content){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
        //Toast.makeText(context,"已复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    public static String getMetaDataValue(Context ctx, String name) {
        Object value = null;
        PackageManager packageManager = ctx.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name);
            }
        } catch (Exception e) {
        }
        if (value == null) {
            value = new String();
        }
        return value.toString();
    }

    public static byte[] readInput(InputStream input) throws Exception
    {
        //存放数据的byte数组
        byte[] buffer=new byte[5000];
        //保存数据的输出流对象
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        int len=0;
        while((len=input.read(buffer))!=-1)
        {
            //写入数据
            output.write(buffer, 0, len);
        }
        //返回输入流中的数据
        return output.toByteArray();

    }
}
