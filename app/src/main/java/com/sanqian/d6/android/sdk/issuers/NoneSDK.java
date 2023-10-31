package com.sanqian.d6.android.sdk.issuers;

import com.sanqian.d6.android.MainActivity;
import com.sanqian.d6.android.sdk.BaseSDK;

import org.json.JSONException;
import org.json.JSONObject;

public class NoneSDK extends BaseSDK {
    public NoneSDK(MainActivity activity){
        super(activity);
    }

    @Override
    public void init() {
        super.init();
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", "");
            jsonObject.put("result", "success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onLogin(jsonObject);
    }
}
