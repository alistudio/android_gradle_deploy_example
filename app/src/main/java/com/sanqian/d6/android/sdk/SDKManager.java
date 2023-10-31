package com.sanqian.d6.android.sdk;

import com.sanqian.d6.android.MainActivity;
import com.sanqian.d6.android.sdk.issuers.NoneSDK;

import java.util.HashMap;
import java.util.Map;

public final class SDKManager {

    private volatile static SDKManager instance;


    private SDKManager() {
    }

    public static SDKManager getInstance() {
        if (instance == null) {
            synchronized (SDKManager.class) {
                if (instance == null) instance = new SDKManager();
            }
        }
        return instance;
    }


    public BaseSDK createSDK(String sdkName, MainActivity activity) {

        SDKType of = SDKType.of(sdkName);
        if (of == null) throw new RuntimeException("sdkName=[" + sdkName + "],暂未实现！");
        return of.create(activity);
    }

    enum SDKType {
        gmSdk("none") {//默认
            @Override
            protected BaseSDK create(MainActivity activity) {
                return new NoneSDK(activity);
            }
        },
//        p800("p800") {//默认
//            @Override
//            protected BaseSDK create(MainActivity activity) {
//                return new P800SDK(activity);
//            }
//        },
        ;
        private final String name;

        SDKType(String name) {
            this.name = name;
        }

        private static Map<String, SDKType> cache;

        public static SDKType of(String name) {
            if (cache == null) {
                cache = new HashMap<>();
                for (SDKType sdkType : values()) {
                    cache.put(sdkType.name, sdkType);
                }
            }
            return cache.get(name);
        }

        protected abstract BaseSDK create(MainActivity activity);
    }

}
