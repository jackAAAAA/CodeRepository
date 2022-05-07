package com.project.mlvbv2demo;

import android.app.Application;
import android.util.Log;

import com.tencent.rtmp.TXLiveBase;

public class MyApplication extends Application {

//    String  licenceUrl = "http://download-1252463788.cossh.myqcloud.com/xiaoshipin/licence_android/RDM_Enterprise.license";
//    String  licenseKey = "9bc74ac7bfd07ea392e8fdff2ba5678a";
//    朱金武_2021/12/14
    String  licenceUrl = "https://license.vod2.myqcloud.com/license/v1/6dc44ae834be1e499d6080133992e10c/TXLiveSDK.licence";
    String  licenseKey = "f8a3c0e96690539888988947072251c7";
//    String  licenceUrl = "https://license.vod2.myqcloud.com/license/v2/1251316161_1/v_cube.license";
//    String  licenseKey = "f8a3c0e96690539888988947072251c7";

    @Override
    public void onCreate() {
        super.onCreate();
        TXLiveBase.getInstance().setLicence(this, licenceUrl, licenseKey);
        String sdkVersionStr = TXLiveBase.getSDKVersionStr();
        Log.e("lin", "----------   sdkVersionStr="+sdkVersionStr);
        TXLiveBase.getInstance().getLicenceInfo(getApplicationContext());

    }
}
