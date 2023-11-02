package com.sanqian.d6.android.sdk;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.widget.Toast;

import com.sanqian.d6.android.MainActivity;
import com.sanqian.d6.android.sdk.common.utils.RunGameJsUtil;
import com.sanqian.d6.android.sdk.interfaces.ISDK;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class BaseSDK implements ISDK {
    private static String deviceId;
    protected MainActivity activity;
    protected  JSONObject initResult;

    public BaseSDK(MainActivity activity){
        this.initResult = null;
        this.activity = activity;
    }
    @Override
    public void init() {

    }


    public void onInit(JSONObject result){
        this.initResult = result;
        deviceId = Settings.Secure.getString(this.activity.getContentResolver(),Settings.Secure.ANDROID_ID);
        RunGameJsUtil.run("_initSdk_success",result.toString());
        Toast.makeText(this.activity,"初始化成功", Toast.LENGTH_SHORT).show();
    }


    //防沉迷回调
    public void onAntiAddiction(){
        onClose();
    }

    @Override
    public void login() {

    }

    @Override
    public void onLogin(JSONObject result) {
        RunGameJsUtil.run("_onLogin_",result.toString());
    }

    @Override
    public void pay(String payInfo) {
    }

    @Override
    public void onPay(JSONObject result) {
        RunGameJsUtil.run("_onPay_",result.toString());
    }

    @Override
    public void logout() {

    }

    @Override
    public void onLogout(JSONObject result) {
        RunGameJsUtil.run("_onLogout_success",result.toString());
    }

    @Override
    public void showUserCenter() {

    }

    @Override
    public void showBindPage() {

    }

    @Override
    public void realNameRegister() {

    }

    @Override
    public void showService() {

    }

    @Override
    public void submitExtraData(String userExtraData) {

    }

    @Override
    public void exit() {
        onClose();
    }

    public void onExit(JSONObject result){

//        RunGameJsUtil.run("onSDKExit",result.toString());
    }

    @Override
    public String getApkUrl() {
        return null;
    }

    @Override
    public String getBirthday() {
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
//        if (this.jsEveInit)
//            RunGameJsUtil.run("onAppResume","");
    }

    @Override
    public void onPause() {
//        if (this.jsEveInit)
//            RunGameJsUtil.run("onAppPause","");
    }

    @Override
    public void onReStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onKeyDown(int keyCode) {

//        JSBridge.onKeyDown(keyCode);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    }
    @Override
    public void onClose() {
        System.out.println("关闭界面");
//        GameEngine.getInstance().ExitGame();
    }
}
