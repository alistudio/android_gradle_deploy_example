package com.sanqian.d6.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PrivacyActivity  extends Activity {

    private  static  PrivacyActivity instance;

    public PrivacyActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        String agreePrivacy = DataUtil.get(this,"privacy","0").toString();
        if(agreePrivacy.equals("1")){
//            this.checkPermition();
            this.startMain();
        } else {
            this.showPrivacy();
        }
    }

    public void showPrivacy()    {
        //加载当前要显示的隐私内容文本
        String str = initAssets("privacy.txt");

        //布局ui界面信息
        final View inflate = LayoutInflater.from(this).inflate(R.layout.privacy_policy, null);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        //设置隐私内容抬头
        tv_title.setText(R.string.privacy_title);
        //显示隐私内容，因为文本布局，需要美观，所以内容用需要使用换行符，但加载回来的内容用\n的话无法真正做到换行，只能在文本中用<br/>作为换行符，然后进行替换成\n
        TextView tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        tv_content.setText(str.replace("<br/>", "\n"));
        //获取同意和退出两个按钮并且添加事件
        TextView btn_exit = (TextView) inflate.findViewById(R.id.btn_exit);
        TextView btn_enter = (TextView) inflate.findViewById(R.id.btn_enter);

        //开始弹出隐私界面
        final Dialog dialog = new AlertDialog
                .Builder(this)
                .setView(inflate)
                .show();
        //对话框弹出后点击或按返回键不消失
        dialog.setCancelable(false);

        WindowManager m = getWindowManager();
        Display defaultDisplay = m.getDefaultDisplay();
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (defaultDisplay.getWidth() * 0.90);
        params.height = (int) (defaultDisplay.getHeight() * 0.8);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //退出按钮事件
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
                System.exit(0);
            }
        });
        //同意按钮事件
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DataUtil.put(instance,"privacy","1");
                instance.checkPermition();
            }
        });
    }
    /**
     * 从assets下的txt文件中读取数据
     */
    public String initAssets(String fileName) {
        String str = null;
        try {
            InputStream inputStream = getAssets().open(fileName);
            str = getString(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return str;
    }


    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    private void checkPermition(){
        LinearLayout ll_main = new LinearLayout(this);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-2, -2);
        ll_main.setBackgroundColor(-1);
        ll_main.setLayoutParams(llp);
        this.setContentView(ll_main);
        if (Build.VERSION.SDK_INT >= 23) {
            this.initPermittion();
        } else {
            this.startMain();
        }
    }

    String[] permissions = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    List<String> mPermissionList = new ArrayList();
    private final int mRequestCode = 100;

    private void initPermittion() {
        this.mPermissionList.clear();

        for(int i = 0; i < this.permissions.length; ++i) {
            if (ContextCompat.checkSelfPermission(this, this.permissions[i]) != 0) {
                this.mPermissionList.add(this.permissions[i]);
            }
        }

        if (this.mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, this.permissions, 100);
        } else {
            this.startMain();
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        if (100 == requestCode) {
            for(int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }

//            if (hasPermissionDismiss) {
//                this.initPermittion();
//            } else {
//                this.startMain();
//            }
            this.startMain();
        }

    }

    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }
}
