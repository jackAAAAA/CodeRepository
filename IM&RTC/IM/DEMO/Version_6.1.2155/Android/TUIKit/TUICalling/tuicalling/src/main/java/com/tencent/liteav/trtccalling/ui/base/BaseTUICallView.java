package com.tencent.liteav.trtccalling.ui.base;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tencent.liteav.trtccalling.R;
import com.tencent.liteav.trtccalling.TUICalling;
import com.tencent.liteav.trtccalling.model.TRTCCalling;
import com.tencent.liteav.trtccalling.model.TRTCCallingDelegate;
import com.tencent.liteav.trtccalling.model.impl.UserModel;
import com.tencent.liteav.trtccalling.model.impl.base.CallingInfoManager;
import com.tencent.liteav.trtccalling.model.impl.base.OfflineMessageBean;
import com.tencent.liteav.trtccalling.model.impl.base.OfflineMessageContainerBean;
import com.tencent.liteav.trtccalling.model.impl.base.TRTCLogger;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.ITUINotification;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.trtc.TRTCCloudDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseTUICallView extends FrameLayout implements TRTCCallingDelegate {
    private static final String TAG = "BaseTUICallView";

    protected final Context         mContext;
    protected final TRTCCalling     mTRTCCalling;
    protected final UserModel       mSelfModel;
    protected final TUICalling.Role mRole;
    protected final String[]        mUserIDs;
    protected final String          mSponsorID;
    protected final String          mGroupID;
    protected final boolean         mIsFromGroup;
    protected final Handler         mMainHandler = new Handler(Looper.getMainLooper());

    private long mSelfLowQualityTime;
    private long mOtherPartyLowQualityTime;

    private static final int MIN_DURATION_SHOW_LOW_QUALITY = 5000; //????????????????????????????????????

    public BaseTUICallView(Context context, TUICalling.Role role, String[] userIDs,
                           String sponsorID, String groupID, boolean isFromGroup) {
        super(context);
        mContext = context;
        mTRTCCalling = TRTCCalling.sharedInstance(context);
        mSelfModel = new UserModel();
        mSelfModel.userId = TUILogin.getUserId();
        mSelfModel.userName = TUILogin.getLoginUser();
        mRole = role;
        mUserIDs = userIDs;
        mSponsorID = sponsorID;
        mGroupID = groupID;
        mIsFromGroup = isFromGroup;
        initView();
        initListener();
    }

    protected void runOnUiThread(Runnable task) {
        if (null != task) {
            mMainHandler.post(task);
        }
    }

    protected abstract void initView();

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTRTCCalling.addDelegate(this);
        if (TUICalling.Role.CALLED == mRole && !mTRTCCalling.isValidInvite()) {
            TRTCLogger.w(TAG, "this invitation is invalid");
            onCallingCancel();
        }

        //???????????????,?????????????????????
        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static OfflineMessageBean parseOfflineMessage(Intent intent) {
        Log.i(TAG, "parse TPNS2 push");
        Bundle bundle = intent.getExtras();
        Log.i(TAG, "bundle: " + bundle);
        if (bundle == null) {
            return null;
        } else {
            String ext = bundle.getString("custom_content");
            Log.i(TAG, "push custom data ext: " + ext);
            if (TextUtils.isEmpty(ext)) {
                return null;
            } else {
                return getOfflineMessageBeanFromContainer(ext);
            }
        }
    }

    private static OfflineMessageBean getOfflineMessageBeanFromContainer(String ext) {
        if (TextUtils.isEmpty(ext)) {
            return null;
        }
        ext = Uri.decode(ext);
        OfflineMessageContainerBean bean = null;
        try {
            bean = new Gson().fromJson(ext, OfflineMessageContainerBean.class);
        } catch (Exception e) {
            Log.e(TAG, "getOfflineMessageBeanFromContainer: " + e.getMessage());
        }
        if (bean == null) {
            return null;
        }
        return bean.entity;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected void finish() {
        mTRTCCalling.removeDelegate(this);
    }

    @Override
    public void onGroupCallInviteeListUpdate(List<String> userIdList) {

    }

    @Override
    public void onInvited(String sponsor, List<String> userIdList, boolean isFromGroup, int callType) {

    }

    @Override
    public void onError(int code, String msg) {

    }

    @Override
    public void onUserEnter(final String userId) {

    }

    @Override
    public void onUserLeave(final String userId) {

    }

    @Override
    public void onReject(final String userId) {

    }

    @Override
    public void onNoResp(final String userId) {

    }

    @Override
    public void onLineBusy(String userId) {

    }

    @Override
    public void onCallingCancel() {

    }

    @Override
    public void onCallingTimeout() {

    }

    @Override
    public void onCallEnd() {

    }

    @Override
    public void onUserVoiceVolume(Map<String, Integer> volumeMap) {

    }

    @Override
    public void onNetworkQuality(TRTCCloudDef.TRTCQuality localQuality,
                                 ArrayList<TRTCCloudDef.TRTCQuality> remoteQuality) {

    }

    @Override
    public void onUserVideoAvailable(String userId, boolean isVideoAvailable) {

    }

    @Override
    public void onUserAudioAvailable(String userId, boolean isAudioAvailable) {

    }

    @Override
    public void onSwitchToAudio(boolean success, String message) {

    }

    //localQuality ????????????????????? remoteQualityList??????????????????????????????????????????1v1?????????????????????
    protected void updateNetworkQuality(TRTCCloudDef.TRTCQuality localQuality,
                                        List<TRTCCloudDef.TRTCQuality> remoteQualityList) {
        //????????????????????????????????????????????????????????????????????????
        boolean isLocalLowQuality = isLowQuality(localQuality);
        if (isLocalLowQuality) {
            updateLowQualityTip(true);
        } else {
            if (!remoteQualityList.isEmpty()) {
                TRTCCloudDef.TRTCQuality remoteQuality = remoteQualityList.get(0);
                if (isLowQuality(remoteQuality)) {
                    updateLowQualityTip(false);
                }
            }
        }
    }

    private boolean isLowQuality(TRTCCloudDef.TRTCQuality qualityInfo) {
        if (qualityInfo == null) {
            return false;
        }
        int quality = qualityInfo.quality;
        boolean lowQuality;
        switch (quality) {
            case TRTCCloudDef.TRTC_QUALITY_Vbad:
            case TRTCCloudDef.TRTC_QUALITY_Down:
                lowQuality = true;
                break;
            default:
                lowQuality = false;
        }
        return lowQuality;
    }

    private void updateLowQualityTip(boolean isSelf) {
        long currentTime = System.currentTimeMillis();
        if (isSelf) {
            if (currentTime - mSelfLowQualityTime > MIN_DURATION_SHOW_LOW_QUALITY) {
                Toast.makeText(mContext, R.string.trtccalling_self_network_low_quality, Toast.LENGTH_SHORT).show();
                mSelfLowQualityTime = currentTime;
            }
        } else {
            if (currentTime - mOtherPartyLowQualityTime > MIN_DURATION_SHOW_LOW_QUALITY) {
                Toast.makeText(mContext, R.string.trtccalling_other_party_network_low_quality,
                        Toast.LENGTH_SHORT).show();
                mOtherPartyLowQualityTime = currentTime;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected boolean isDestroyed() {
        boolean isDestroyed = false;
        if (mContext instanceof Activity && ((Activity) mContext).isDestroyed()) {
            isDestroyed = true;
        }
        return isDestroyed;
    }

    private void initListener() {
        ITUINotification notification = new ITUINotification() {
            @Override
            public void onNotifyEvent(String key, String subKey, Map<String, Object> param) {
                if (TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED.equals(key)
                        && TUIConstants.TUILogin.EVENT_SUB_KEY_USER_KICKED_OFFLINE.equals(subKey)) {
                    ToastUtils.showShort(mContext.getString(R.string.trtccalling_user_kicked_offline));
                    mTRTCCalling.hangup();
                    finish();
                }
                if (TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED.equals(key)
                        && TUIConstants.TUILogin.EVENT_SUB_KEY_USER_SIG_EXPIRED.equals(subKey)) {
                    ToastUtils.showShort(mContext.getString(R.string.trtccalling_user_sig_expired));
                    mTRTCCalling.hangup();
                    finish();
                }
            }
        };
        TUICore.registerEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED,
                TUIConstants.TUILogin.EVENT_SUB_KEY_USER_KICKED_OFFLINE,
                notification);
        TUICore.registerEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED,
                TUIConstants.TUILogin.EVENT_SUB_KEY_USER_SIG_EXPIRED,
                notification);
    }

    public void showUserToast(String userId, int msgId) {
        if (TextUtils.isEmpty(userId)) {
            TRTCLogger.d(TAG, "showUserToast userId is empty");
            return;
        }
        CallingInfoManager.getInstance().getUserInfoByUserId(userId,
                new CallingInfoManager.UserCallback() {
                    @Override
                    public void onSuccess(UserModel model) {
                        if (null == model || TextUtils.isEmpty(model.userName)) {
                            ToastUtils.showLong(mContext.getString(msgId, userId));
                        } else {
                            ToastUtils.showLong(mContext.getString(msgId, model.userName));
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showLong(mContext.getString(msgId, userId));
                    }
                });
    }
}
