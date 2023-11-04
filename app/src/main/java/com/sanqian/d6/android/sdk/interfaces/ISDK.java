package com.sanqian.d6.android.sdk.interfaces;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import org.json.JSONObject;


public interface ISDK {
    //sdk 初始化
    void init();

    /**
     * 登陆
     *
     */
    void login();

    /**
     * 登录回调
     */
    void onLogin(JSONObject result);
    /**
     * 充值回调
     *
     * @param payInfo
     */
    void pay(String payInfo);
    /**
     * 充值回调
     *
     */
    void onPay(JSONObject result);

    /**
     * 登出
     */
    void logout();
    /**
     * 登录回调
     */
    void onLogout(JSONObject result);
    /**
     * 用户中心
     */
    void showUserCenter();

    /**
     * 绑定手机
     */
    void showBindPage();

    /**
     * 实名
     */
    void realNameRegister();

    /**
     * 客服中心
     */
    void showService();

    /**
     * 上传游戏角色
     *
     * @param userExtraData
     */
    void submitExtraData(String userExtraData);

    /**
     * 退出游戏
     */
    void exit();
    void onExit(JSONObject result);

    /**
     * apk 更新地址
     *
     * @return
     */
    String getApkUrl();

    /**
     * 获取用户生日
     *
     * @return
     */
    String getBirthday();

    /**
     * 生命周期部分
     */

    void onStart();

    void onResume();

    void onPause();
    void onReStart();
    void onDestroy();
    void onClose();

    boolean onKeyDown(int keyCode);

    void onStop();

    void onNewIntent(Intent intent);

    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onRestoreInstanceState(Bundle savedInstanceState);
    void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState);
    void onConfigurationChanged(Configuration newConfig);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
