package com.tencent.qcloud.tim.demo.thirdpush.OEMPush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.HmsMessaging;
import com.huawei.hms.push.RemoteMessage;
import com.tencent.qcloud.tim.demo.SplashActivity;
import com.tencent.qcloud.tim.demo.main.MainActivity;
import com.tencent.qcloud.tim.demo.profile.AboutIMActivity;
import com.tencent.qcloud.tim.demo.thirdpush.ThirdPushTokenMgr;
import com.tencent.qcloud.tim.demo.utils.BrandUtil;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.TUIUtils;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;

public class HUAWEIHmsMessageService extends HmsMessageService {

    private static final String TAG = HUAWEIHmsMessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage message) {
        DemoLog.i(TAG, "onMessageReceived message=" + message);

        Log.i(TAG, "onMessageReceived: " + "推送消息发送者："+ message.getFrom() + " 推送消息内容：" + message.getData());

//        收到华为控制台发送的透传字段，就打开IM的介绍页面
//        31行的代码可以实现由华为厂商通道下发的透传字段而触发跳转到Activity_IM的介绍页面
//        TUIUtils.startActivity("AboutIMActivity", null);

        //34~35行代码无法打开想要的页面
//        Intent intent = new Intent(getApplicationContext(), AboutIMActivity.class);
//        startActivity(intent);

        //39~44行代码能够实现由华为厂商通道下发的透传字段而触发跳转到由Android系统自带浏览器打开的指定网页
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("https://cloud.tencent.com/document/product/269/3794");
        intent.setData(content_url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TUIChatService.getAppContext().startActivity(intent);

        /**
         * {@link OEMPushSetting#init}
         */
//        拒绝接收华为通知消息 https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/configuring-notification-messages-0000001134381639
//        HmsMessaging.getInstance(getApplicationContext()).turnOffPush().addOnCompleteListener(task -> {
//            // 获取结果
//            if (task.isSuccessful()) {
//                Log.i(TAG, "turnOffPush Complete");
//            } else {
//                Log.e(TAG, "turnOffPush failed: ret=" + task.getException().getMessage());
//            }
//        });

    }

    @Override
    public void onMessageSent(String msgId) {
        DemoLog.i(TAG, "onMessageSent msgId=" + msgId);
    }

    @Override
    public void onSendError(String msgId, Exception exception) {
        DemoLog.i(TAG, "onSendError msgId=" + msgId);
    }

    @Override
    public void onNewToken(String token) {
        DemoLog.i(TAG, "onNewToken token=" + token);
        ThirdPushTokenMgr.getInstance().setThirdPushToken(token);
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
    }

    @Override
    public void onTokenError(Exception exception) {
        DemoLog.i(TAG, "onTokenError exception=" + exception);
    }

    @Override
    public void onMessageDelivered(String msgId, Exception exception) {
        DemoLog.i(TAG, "onMessageDelivered msgId=" + msgId);
    }


    public static void updateBadge(final Context context, final int number) {
        if (!BrandUtil.isBrandHuawei()) {
            return;
        }
        DemoLog.i(TAG, "huawei badge = " + number);
        try {
            Bundle extra = new Bundle();
            extra.putString("package", "com.tencent.qcloud.tim.tuikit");
            extra.putString("class", "com.tencent.qcloud.tim.demo.SplashActivity");
            extra.putInt("badgenumber", number);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, extra);
        } catch (Exception e) {
            DemoLog.w(TAG, "huawei badge exception: " + e.getLocalizedMessage());
        }
    }
}
