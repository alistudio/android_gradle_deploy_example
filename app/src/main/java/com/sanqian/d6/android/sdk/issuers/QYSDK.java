package com.sanqian.d6.android.sdk.issuers;

import android.content.Intent;
import android.util.Log;

import com.sanqian.d6.android.MainActivity;
import com.sanqian.d6.android.sdk.BaseSDK;

import org.json.JSONException;
import org.json.JSONObject;

import com.pgame.sdkall.QYManager;
import com.pgame.sdkall.entity.CallbackInfo;
import com.pgame.sdkall.entity.QYPayInfo;
import com.pgame.sdkall.entity.RoleInfos;
import com.pgame.sdkall.entity.SdkInitInfo;
import com.pgame.sdkall.listener.SDKListener;

public class QYSDK extends BaseSDK {
    SDKListener listener;
    public QYSDK(MainActivity activity){
        super(activity);
        listener = null;
    }

    @Override
    public void init() {
        QYSDK THIS = this;
        if (this.initResult != null){
            this.onInit(this.initResult);
            return;
        }
        super.init();

        listener = new SDKListener() {
            @Override
            public void onInit(int i) {
                if (0 == i){
                    JSONObject result = new JSONObject();
                    try {
                        result.put("status", i);
                        result.put("msg", "SDK初始化成功");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    THIS.onInit(result);
                }
                else {
                    Log.e("MainActivity", String.format("sdk初始化失败,错误码%d", i));
                }
            }

            @Override
            public void onLogin(Object o, int i) {
                if (0 == i){
                    CallbackInfo callbackInfo = (CallbackInfo) o;
//                    String resultStr = "{" + "'status'" + ":" + callbackInfo.statusCode + "," + "'zid'" + ":" + "%" + callbackInfo.userId + "%" + "," + "'platformUserId'" + ":" + "%" + callbackInfo.platformUserId + "%"
//                            + "," + "'channel'" + ":" + "%" + "sy" + "%" + "," + "'timeStamp'" + ":" + "%" + callbackInfo.timestamp + "%" + "," + "'dToken'" + ":" + "%" + callbackInfo.sign + "%" + "," + "'desc'" + ":" + "%" + callbackInfo.desc + "%" + ","
//                            + "'channelId'" + ":" + "%" + callbackInfo.platformId + "%" + "}";

                    JSONObject result = new JSONObject();
                    try {
                        result.put("status", callbackInfo.statusCode);
                        result.put("zid", callbackInfo.userId);
                        result.put("platformUserId", callbackInfo.platformUserId);
                        result.put("channel", "sy");
                        result.put("timeStamp", callbackInfo.timestamp);
                        result.put("dToken", callbackInfo.sign);
                        result.put("desc", callbackInfo.desc);
                        result.put("channelId", callbackInfo.platformId);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    THIS.onLogin(result);
                }
                else {
                    Log.e("MainActivity", String.format("onInit: 初始化失败,错误码%s", o.toString()));
                }
            }

            @Override
            public void onLogout(int i) {
                if (0 == i){
                    JSONObject result = new JSONObject();
                    try {
                        result.put("status", i);
                        result.put("msg", "SDK注销成功");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    THIS.onLogout(result);
                }
                else {
                    Log.e("MainActivity", String.format("sdk注销失败,错误码%d", i));
                }
            }

            @Override
            public void onExit(int i) {
                if (0 == i){
                    QYManager.getInstace().sdkDestroy();
                    THIS.activity.finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }

            @Override
            public void onPay(int i) {
                if (0 == i){
                    Log.e("MainActivity", "支付成功" );
                    JSONObject result = new JSONObject();
                    try {
                        result.put("status", i);
                        result.put("msg", "支付成功");
                        THIS.onPay(result);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Log.e("MainActivity", "支付成功");
                }
            }
        };

        SdkInitInfo initInfo = new SdkInitInfo();
        initInfo.setmCtx(this.activity);
        QYManager.getInstace().init(initInfo, listener);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", "");
            jsonObject.put("result", "success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onInit(jsonObject);
    }

    @Override
    public void login() {
        super.login();
        QYManager.getInstace().login();
    }
    @Override
    public void pay(String payInfo) {
        super.pay(payInfo);
        try {
            QYPayInfo info = new QYPayInfo();
            JSONObject jsonObject = new JSONObject(payInfo);
            info.setCpOrderId(jsonObject.getString("outOrderid"));
            info.setRoleId(jsonObject.getString("roleId"));
            info.setRoleName(jsonObject.getString("roleName"));
            info.setCallBackStr(jsonObject.getString("callBackStr"));
            info.setProductId(jsonObject.getString("productId"));
            info.setMoney(jsonObject.getInt("money"));
            info.setNoticeUrl("http://www.baidu.com");
            info.setPayType(1);
            info.setMoreCharge(0);
            info.setProductName(jsonObject.getString("productName"));
            info.setRate(jsonObject.getInt("rate"));
            info.setGameGold(jsonObject.getString("gameGold"));
            info.setExStr(jsonObject.getString("exStr"));
            QYManager.getInstace().pay(info);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exit() {
        super.exit();
        QYManager.getInstace().sdkExit();
    }

    @Override
    public void logout() {
        super.logout();
        QYManager.getInstace().logout();
    }

    @Override
    public void submitExtraData(String userExtraData) {
        super.submitExtraData(userExtraData);
        try {
            JSONObject jsonObject = new JSONObject(userExtraData);
            RoleInfos roleInfo = new RoleInfos();
            roleInfo.setInfoType(jsonObject.getInt("type"));// 这里为进入游戏是调用
            roleInfo.setRoleId(jsonObject.getString("roleId"));
            roleInfo.setRoleLevel(jsonObject.getString("roleLevel"));
            roleInfo.setServerId(jsonObject.getString("serverId"));
            roleInfo.setRoleName(jsonObject.getString("roleName"));
            roleInfo.setServerName(jsonObject.getString("serverName"));
            roleInfo.setCreateRoleTime(jsonObject.getString("createRoleTime"));
            roleInfo.setBalance(jsonObject.getString("balance"));
            roleInfo.setPartyName(jsonObject.getString("partyName"));
            roleInfo.setRoleUpLevelTime(jsonObject.getString("roleUpLevelTime"));
            QYManager.getInstace().sdkRoleInfo(roleInfo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        QYManager.getInstace().onResume(this.activity);
    }

    @Override
    public void onPause() {
        super.onPause();
        QYManager.getInstace().onPause(this.activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        QYManager.getInstace().onStart(this.activity);
    }

    @Override
    public void onReStart() {
        super.onReStart();
        QYManager.getInstace().onReStart(this.activity);
    }

    @Override
    public void onStop() {
        super.onStop();
        QYManager.getInstace().onStop(this.activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        QYManager.getInstace().onActivityResult(this.activity, requestCode, resultCode, data);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        QYManager.getInstace().onNewIntent(this.activity,intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QYManager.getInstace().sdkDestroy();
    }
}
