package com.tencent.qcloud.tim.demo.thirdpush;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSignalingInfo;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.bean.CallModel;
import com.tencent.qcloud.tim.demo.bean.OfflineMessageBean;
import com.tencent.qcloud.tim.demo.bean.OfflineMessageContainerBean;
import com.tencent.qcloud.tim.demo.main.MainActivity;
import com.tencent.qcloud.tim.demo.thirdpush.OEMPush.VIVOPushMessageReceiverImpl;
import com.tencent.qcloud.tim.demo.utils.BrandUtil;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.TUIUtils;
import com.tencent.qcloud.tuicore.util.ErrorMessageConverter;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

public class OfflineMessageDispatcher {

    private static final String TAG = OfflineMessageDispatcher.class.getSimpleName();
    private static final String OEMMessageKey = "ext";
    private static final String TPNSMessageKey = "customContent";

    public static OfflineMessageBean parseOfflineMessage(Intent intent) {
        DemoLog.i(TAG, "intent: " + intent);
        if (intent == null) {
            return null;
        }

        if (PushSetting.isTPNSChannel) {
            // TPNS 的透传参数数据格式为 customContent:{data}
            Uri uri = intent.getData();
            if (uri == null) {
                DemoLog.i(TAG, "intent.getData() uri is null");
                // intent 方式解析拿不到数据，试试 bundle 方式
                return parseOfflineMessageTPNS2(intent);
            } else {
                return parseOfflineMessageTPNS(intent);
            }
        } else {
            // OEM 厂商的透传参数数据格式为 ext:{data}
            return parseOfflineMessageOEM(intent);
        }
    }

    private static OfflineMessageBean parseOfflineMessageTPNS(Intent intent) {
        DemoLog.i(TAG, "parse TPNS push");
        //intent uri
        Uri uri = intent.getData();
//        TEST
        Log.i(TAG, "handleOfflinePush TPNS uri: " + uri);

        if (uri == null) {
            DemoLog.i(TAG, "intent.getData() uri is null");
        } else {
            DemoLog.i(TAG, "parseOfflineMessageTPNS get data uri: " + uri);
            String ext = uri.getQueryParameter(TPNSMessageKey);
            DemoLog.i(TAG, "push custom data ext: " + ext);
            if (!TextUtils.isEmpty(ext)) {
                return getOfflineMessageBeanFromContainer(ext);
            } else {
                DemoLog.i(TAG, "TextUtils.isEmpty(ext)");
            }
        }
        return null;
    }

    private static OfflineMessageBean parseOfflineMessageTPNS2(Intent intent) {
        DemoLog.i(TAG, "parse TPNS2 push");
        Bundle bundle = intent.getExtras();
        DemoLog.i(TAG, "bundle: " + bundle);

        //        TEST
        Log.i(TAG, "handleOfflinePush TPNS2 bundle: " + bundle);

        if (bundle == null) {
            String ext = VIVOPushMessageReceiverImpl.getParams();
            if (!TextUtils.isEmpty(ext)) {
                return getOfflineMessageBeanFromContainer(ext);
            }
            return null;
        } else {
            String ext = bundle.getString(TPNSMessageKey);

            Log.i(TAG, "handleOfflinePush bundle custom3 uri_decode: " + Uri.decode(bundle.getString("custom_content")));
//            如何只打印解析实体当中的url? —— TO DO
            ToastUtil.toastLongMessage(Uri.decode(bundle.getString("custom_content")));

//            经过检验：所有设备不支持华为设备默认获取到的ext为null，因为并没有在发消息的时候配上'ext'字段
//            Log.i(TAG, "handleOfflinePush ext bundle: " + ext);

            DemoLog.i(TAG, "push custom data ext: " + ext);
            if (TextUtils.isEmpty(ext)) {
                if (BrandUtil.isBrandXiaoMi()) {
                    ext = getXiaomiMessage(bundle);
                    return getOfflineMessageBeanFromContainer(ext);
                } else if (BrandUtil.isBrandOppo()) {
                    ext = getOPPOMessage(bundle);
                    return getOfflineMessageBean(ext);
                } else if (BrandUtil.isBrandHuawei()) {
                    ext = bundle.getString(OEMMessageKey);
                    Log.i(TAG, "handleOfflinePush Process ext bundle: " + ext);
                    return getOfflineMessageBeanFromContainer(ext);
//                    TEST
                }
            }
            return null;
        }
    }

    private static OfflineMessageBean parseOfflineMessageOEM(Intent intent) {
        DemoLog.i(TAG, "parse OEM push");
        Bundle bundle = intent.getExtras();
        DemoLog.i(TAG, "bundle: " + bundle);

        //        TEST
        Log.i(TAG, "handleOfflinePush oem: " + bundle);

        if (bundle == null) {
            String ext = VIVOPushMessageReceiverImpl.getParams();
            if (!TextUtils.isEmpty(ext)) {
                return getOfflineMessageBeanFromContainer(ext);
            }
            return null;
        } else {
            String ext = bundle.getString(OEMMessageKey);
            DemoLog.i(TAG, "push custom data ext: " + ext);
            if (TextUtils.isEmpty(ext)) {
                if (BrandUtil.isBrandXiaoMi()) {
                    ext = getXiaomiMessage(bundle);
                    return getOfflineMessageBeanFromContainer(ext);
                } else if (BrandUtil.isBrandOppo()) {
                    ext = getOPPOMessage(bundle);
                    return getOfflineMessageBean(ext);
                }
            } else {
                return getOfflineMessageBeanFromContainer(ext);
            }
            return null;
        }
    }

    private static String getXiaomiMessage(Bundle bundle) {
        MiPushMessage miPushMessage = (MiPushMessage) bundle.getSerializable(PushMessageHelper.KEY_MESSAGE);
        if (miPushMessage == null) {
            return null;
        }
        Map extra = miPushMessage.getExtra();
        return extra.get(OEMMessageKey).toString();
    }

    private static String getOPPOMessage(Bundle bundle) {
        Set<String> set = bundle.keySet();
        if (set != null) {
            for (String key : set) {
                Object value = bundle.get(key);
                DemoLog.i(TAG, "push custom data key: " + key + " value: " + value);
                if (TextUtils.equals("entity", key)) {
                    return value.toString();
                }
            }
        }
        return null;
    }

    private static OfflineMessageBean getOfflineMessageBeanFromContainer(String ext) {
        if (TextUtils.isEmpty(ext)) {
            return null;
        }
        OfflineMessageContainerBean bean = null;
        try {
            bean = new Gson().fromJson(ext, OfflineMessageContainerBean.class);
        } catch (Exception e) {
            DemoLog.w(TAG, "getOfflineMessageBeanFromContainer: " + e.getMessage());
        }
        if (bean == null) {
            return null;
        }
        return offlineMessageBeanValidCheck(bean.entity);
    }

    private static OfflineMessageBean getOfflineMessageBean(String ext) {
        if (TextUtils.isEmpty(ext)) {
            return null;
        }
        OfflineMessageBean bean = new Gson().fromJson(ext, OfflineMessageBean.class);
        return offlineMessageBeanValidCheck(bean);
    }

    private static OfflineMessageBean offlineMessageBeanValidCheck(OfflineMessageBean bean) {
        if (bean == null) {
            return null;
        } else if (bean.version != 1
                || (bean.action != OfflineMessageBean.REDIRECT_ACTION_CHAT
                && bean.action != OfflineMessageBean.REDIRECT_ACTION_CALL)) {
            PackageManager packageManager = DemoApplication.instance().getPackageManager();
            String label = String.valueOf(packageManager.getApplicationLabel(DemoApplication.instance().getApplicationInfo()));
            ToastUtil.toastLongMessage(DemoApplication.instance().getString(R.string.you_app) + label + DemoApplication.instance().getString(R.string.low_version));
            DemoLog.e(TAG, "unknown version: " + bean.version + " or action: " + bean.action);
            return null;
        }
        return bean;
    }

    public static boolean redirect(final OfflineMessageBean bean) {
        if (bean.action == OfflineMessageBean.REDIRECT_ACTION_CHAT) {
            if (TextUtils.isEmpty(bean.sender)) {
                return true;
            }
            TUIUtils.startChat(bean.sender, bean.nickname, bean.chatType);
            return true;
        } else if (bean.action == OfflineMessageBean.REDIRECT_ACTION_CALL) {
            redirectCall(bean);
        }
        return true;
    }

    static void redirectCall(OfflineMessageBean bean) {
        if (bean == null || bean.content == null) {
            return;
        }
        final CallModel model = new Gson().fromJson(bean.content, CallModel.class);
        DemoLog.i(TAG, "bean: " + bean + " model: " + model);
        if (model != null) {
            model.sender = bean.sender;
            model.data = bean.content;
            long timeout = V2TIMManager.getInstance().getServerTime() - bean.sendTime;
            if (timeout >= model.timeout) {
                ToastUtil.toastLongMessage(DemoApplication.instance().getString(R.string.call_time_out));
            } else {
                if (TextUtils.isEmpty(model.groupId)) {
                    Intent mainIntent = new Intent(DemoApplication.instance(), MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    DemoApplication.instance().startActivity(mainIntent);
                } else {
                    V2TIMSignalingInfo info = new V2TIMSignalingInfo();
                    info.setInviteID(model.callId);
                    info.setInviteeList(model.invitedList);
                    info.setGroupID(model.groupId);
                    info.setInviter(bean.sender);
                    V2TIMManager.getSignalingManager().addInvitedSignaling(info, new V2TIMCallback() {

                        @Override
                        public void onError(int code, String desc) {
                            DemoLog.e(TAG, "addInvitedSignaling code: " + code + " desc: " + ErrorMessageConverter.convertIMError(code, desc));
                        }

                        @Override
                        public void onSuccess() {
                            Intent mainIntent = new Intent(DemoApplication.instance(), MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            DemoApplication.instance().startActivity(mainIntent);
                            TUIUtils.startCall(bean.sender, model.data);
                        }
                    });
                }
            }
        }
    }

    public static void updateBadge(final Context context, final int number) {
        if (!BrandUtil.isBrandHuawei()) {
            return;
        }
        DemoLog.i(TAG, "huawei badge = " + number);
        try {
            Bundle extra = new Bundle();
            extra.putString("package", "com.tencent.qcloud.tim.tuijack");
            extra.putString("class", "com.tencent.qcloud.tim.demo.SplashActivity");
            extra.putInt("badgenumber", number);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, extra);
        } catch (Exception e) {
            DemoLog.w(TAG, "huawei badge exception: " + e.getLocalizedMessage());
        }
    }

//    URL解码
    public static String decode(String url) {
        try {
            String prevURL = "";
            String decodeURL = url;
            while (!prevURL.equals(decodeURL)) {
                prevURL = decodeURL;
                decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" + e.getMessage();
        }
    }

}
