package com.sanqian.d6.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.sanqian.d6.android.sdk.BaseSDK;
import com.sanqian.d6.android.sdk.SDKManager;
import com.sanqian.d6.android.sdk.common.utils.RunGameJsUtil;

import org.egret.runtime.launcherInterface.INativePlayer;
import org.egret.egretnativeandroid.EgretNativeAndroid;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

//Android项目发布设置详见doc目录下的README_ANDROID.md

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    private EgretNativeAndroid nativeAndroid;

    public static BaseSDK curSDK;
    public static String sdkName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nativeAndroid = new EgretNativeAndroid(this);
        RunGameJsUtil.nativeAndroid = nativeAndroid;
        if (!nativeAndroid.checkGlEsVersion()) {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        nativeAndroid.config.showFPS = false;
        nativeAndroid.config.fpsLogTime = 30;
        nativeAndroid.config.disableNativeRender = false;
        nativeAndroid.config.clearCache = false;
        nativeAndroid.config.loadingTimeout = 0;
        nativeAndroid.config.immersiveMode = true;
        nativeAndroid.config.useCutout = true;


       /* if (!nativeAndroid.initialize("http://192.168.1.204/index.html")) {
            Toast.makeText(this, "Initialize native failed.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        setContentView(nativeAndroid.getRootFrameLayout());*/

        MainActivity.sdkName = Utils.getMetaDataValue(this, "sdk_name");
        curSDK = SDKManager.getInstance().createSDK(MainActivity.sdkName, this);
        curSDK.init();
        Date currentDate = new Date();
        String url = Utils.getMetaDataValue(this, "index_version")+"?v=" + currentDate.getTime();
        this.initEngine(url);
//        this.fetchIndexVersion();
    }
//    public void fetchIndexVersion(){
//        Activity THIS = this;
//        new Thread(new Runnable(){
//            @Override
//            public void run(){
//                try{
//                    String urlStr = Utils.getMetaDataValue(THIS, "index_version");
//                    URL url = new URL(urlStr);
//                    HttpURLConnection http=(HttpURLConnection) url.openConnection();
//                    //设置请求方式
//                    http.setRequestMethod("GET");
//                    //设置连接超时时间
//                    http.setConnectTimeout(5000);
//                    //如果连接成功读取网络数据
//                    if(http.getResponseCode()==200)
//                    {
//                        System.out.println("下载成功");
//                        InputStream inputstream=http.getInputStream();
//                        //调用WebTools中的getData方法并得到数据
//                        byte[] data=Utils.readInput(inputstream);
//                        //把数据写入到文件中
//                        String jsonStr = new String(data);
//                        onFetchIndexVersion(jsonStr);
//                    }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

//    private void onFetchIndexVersion(final String response){
//        Activity THIS = this;
//        runOnUiThread(new Runnable(){
//            @Override
//            public void run() {
//                String url = "";
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.has("url"))
//                        url = jsonObject.getString("url");
//                    initEngine(url);
//                } catch (JSONException e) {
//                    Log.e("initEngine", response);
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    private void initEngine(String url){
        if (!nativeAndroid.initialize(url)) {
            Toast.makeText(this, "Initialize native failed.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(nativeAndroid.getRootFrameLayout());
        setExternalInterfaces();

    }
    @Override
    protected void onPause() {
        super.onPause();
        nativeAndroid.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nativeAndroid.resume();
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            nativeAndroid.exitGame();
        }

        return super.onKeyDown(keyCode, keyEvent);
    }

    private void setExternalInterfaces() {
        nativeAndroid.setExternalInterface("sendToNative", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message: " + message);
                nativeAndroid.callExternalInterface("sendToJS", "Get message: " + message);
            }
        });
        nativeAndroid.setExternalInterface("@onState", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.e(TAG, "Get @onState: " + message);
            }
        });
        nativeAndroid.setExternalInterface("@onError", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.e(TAG, "Get @onError: " + message);
            }
        });
        nativeAndroid.setExternalInterface("@onJSError", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.e(TAG, "Get @onJSError: " + message);
            }
        });

        /*初始化sdk*/
        nativeAndroid.setExternalInterface("_initSdk", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_initSdk: " + message);
                curSDK.init();
            }
        });

        /*登录*/
        nativeAndroid.setExternalInterface("_onLogin", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_onLogin: " + message);
                curSDK.login();
            }
        });

        /*上报*/
        nativeAndroid.setExternalInterface("_onReport", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_onReport1: " + message);
                curSDK.submitExtraData(message);
            }
        });

        /*支付*/
        nativeAndroid.setExternalInterface("_pay", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_pay: " + message);
                curSDK.pay(message);
            }
        });

        /*注销账户*/
        nativeAndroid.setExternalInterface("_onLogout", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_onLogout: " + message);
                curSDK.logout();
            }
        });

        nativeAndroid.setExternalInterface("_logoutGame", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_logoutGame: " + message);
                curSDK.exit();
                finish();
                System.exit(0);
            }
        });


        /*切换账户*/
        nativeAndroid.setExternalInterface("_onSwitchUser", new INativePlayer.INativeInterface() {
            @Override
            public void callback(String message) {
                Log.d(TAG, "Get message_onSwitchUser: " + message);
            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
