package com.tencent.qcloud.tim.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.tencent.android.tpush.XGPushManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.qcloud.tim.demo.bean.OfflineMessageBean;
import com.tencent.qcloud.tim.demo.bean.UserInfo;
import com.tencent.qcloud.tim.demo.login.LoginForDevActivity;
import com.tencent.qcloud.tim.demo.main.MainActivity;
import com.tencent.qcloud.tim.demo.thirdpush.OfflineMessageDispatcher;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.TUIUtils;
import com.tencent.qcloud.tuicore.component.activities.BaseLightActivity;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import java.util.Arrays;

public class SplashActivity extends BaseLightActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

        mUserInfo = UserInfo.getInstance();
        handleData();
    }

    private void handleData() {
        if (mUserInfo != null && mUserInfo.isAutoLogin()) {
            DemoApplication.instance().init();
            login();
        } else {
            startLogin();
        }
    }

    private void login() {
        TUIUtils.login(mUserInfo.getUserId(), mUserInfo.getUserSig(), new V2TIMCallback() {
            @Override
            public void onError(final int code, final String desc) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.toastLongMessage(getString(R.string.failed_login_tip) + ", errCode = " + code + ", errInfo = " + desc);
                        startLogin();
                    }
                });
                DemoLog.i(TAG, "imLogin errorCode = " + code + ", errorInfo = " + desc);
            }

            @Override
            public void onSuccess() {
                startMain();
            }
        });
    }

    private void startLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginForDevActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMain() {
        DemoLog.i(TAG, "MainActivity" );

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtras(getIntent());
        intent.setData(getIntent().getData());
        startActivity(intent);


//        TEST
//        此处可以解析TPNS的推送内容，因为控制台默认配置的跳转为此Activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            tag.tpush.NOTIFIC, activity, notificationActionType, busiMsgId, PUSH.CHANNEL, traceId, action_confirm, source, protect,
//            accId, msgId, title, groupId, custom_content, targetType, group_id, pushChannel, content, templateId, action_type, tag.tpush.MSG,
//            timestamps, notifaction_id, pushTime
            Log.i(TAG, "handleOfflinePush SplashActivity decode: " + OfflineMessageDispatcher.decode(bundle.getString("custom_content")));
//            Log.i(TAG, "handleOfflinePush SplashActivity: " + bundle.get("action_type"));
//            Log.i(TAG, "handleOfflinePush SplashActivity: " + bundle.get("timestamps"));
        }

        // SDK 1.3.2.0 起新增
        // 当创建推送任务时有填自定义参数（custom_content），可以通过此接口获取 custom_content 字符串内容。
//        String customContent1 = XGPushManager.getCustomContentFromIntent(XGPushManager.getContext(), getIntent());
//        String customContent = XGPushManager.getCustomContentFromIntent(getApplicationContext(), getIntent());
//        Log.i(TAG, "handleOfflinePush custom_xg: " + customContent + "\n" + customContent1);

//        此处才能拿到下发的自定义内容
        OfflineMessageBean bean = OfflineMessageDispatcher.parseOfflineMessage(getIntent());
//        if (bean != null) {
//            DemoLog.i(TAG, "startMain offlinePush bean is " + bean);
//
////            TEST
//            Log.i(TAG, "startMain: " + bean.toString());
//
//            OfflineMessageDispatcher.redirect(bean);
//
//            DemoApplication.instance().initPush();
//            DemoApplication.instance().bindUserID(UserInfo.getInstance().getUserId());
//
//            finish();
//            return;
//        } else {
//            TEST
//            Log.i(TAG, "handleOfflinePush: bean is null");
//        }

        finish();

    }
}
