package com.tencent.liteav.demo.livepusher.camerapush.ui;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.liteav.audiosettingkit.AudioEffectPanel;
import com.tencent.liteav.demo.beauty.view.BeautyPanel;
import com.tencent.liteav.demo.livepusher.R;
import com.tencent.liteav.demo.livepusher.camerapush.ui.util.Utils;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.LogInfoWindow;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.PusherPlayQRCodeFragment;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.PusherSettingFragment;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.PusherVideoQualityFragment;
import com.tencent.live2.V2TXLiveCode;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePusher;
import com.tencent.live2.V2TXLivePusherObserver;
import com.tencent.live2.impl.V2TXLivePusherImpl;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.tencent.live2.V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModeLandscape;
import static com.tencent.live2.V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait;

/**
 * ????????? {@link com.tencent.live2.V2TXLivePusher} ????????? V2 ???????????? Demo
 * <p>
 * ????????????????????? ???
 * <p>
 * - ????????????????????? ???????????? {@link #startPush()} ??? ???????????? {@link #stopPush()} ()}
 * <p>
 * - ??????????????????????????? {@link com.tencent.live2.V2TXLivePusherObserver#onStatisticsUpdate(V2TXLiveDef.V2TXLivePusherStatistics)}
 * <p>
 * - ?????? SDK ????????????????????? {@link com.tencent.live2.V2TXLivePusherObserver#onPushStatusUpdate(V2TXLiveDef.V2TXLivePushStatus, String, Bundle)}
 * <p>
 * - ???????????????{@link BeautyPanel}
 * <p>
 * - BGM ?????????{@link AudioEffectPanel}
 * <p>
 * - ???????????????{@link PusherVideoQualityFragment}
 * <p>
 * - ?????????????????????????????????????????????????????????????????? {@link PusherSettingFragment} ??? {@link PusherSettingFragment.OnSettingChangeListener}
 */
public class CameraPushMainActivity extends FragmentActivity implements
        PusherVideoQualityFragment.OnVideoQualityChangeListener, PusherSettingFragment.OnSettingChangeListener {

    private static final String TAG                           = "CameraPushMainActivity";
    private static final String PUSHER_SETTING_FRAGMENT       = "push_setting_fragment";
    private static final String PUSHER_PLAY_QR_CODE_FRAGMENT  = "push_play_qr_code_fragment";
    private static final String PUSHER_VIDEO_QUALITY_FRAGMENT = "push_video_quality_fragment";

    private TXPhoneStateListener                  mPhoneListener;
    private TextView                              mTextNetBusyTips;              // ????????????Tips
    private BeautyPanel                           mBeautyPanelView;              // ????????????pannel
    private Button                                mBtnStartPush;                 // ?????????????????????
    private LinearLayout                          mLinearBottomBar;              // ?????????????????????
    private AudioEffectPanel                      mAudioEffectPanel;             // ????????????
    private PusherPlayQRCodeFragment              mPusherPlayQRCodeFragment;   // ??????????????????
    private PusherSettingFragment                 mPusherSettingFragment;      // ????????????
    private PusherVideoQualityFragment            mPusherVideoQualityFragment; // ????????????
    private LogInfoWindow                         mLogInfoWindow;              // Log ????????????
    private String                                mPusherURL             = "";   // ????????????
    private String                                mRTMPPlayURL           = "";   // RTMP ????????????
    private String                                mFlvPlayURL            = "";   // flv ????????????
    private String                                mHlsPlayURL            = "";   // hls ????????????
    private String                                mRTCPlayURL            = "";   // RTC????????????
    private int                                   mLogClickCount         = 0;
    private V2TXLivePusher                        mLivePusher;
    private TXCloudVideoView                      mPusherView;
    private Bitmap                                mWaterMarkBitmap;
    private boolean                               mIsPushing             = false;
    private boolean                               mIsResume              = false;
    private boolean                               mIsWaterMarkEnable     = true;
    private boolean                               mIsDebugInfo           = false;
    private boolean                               mIsMuteAudio           = false;
    private boolean                               mIsLandscape           = false;
    private boolean                               mIsMirrorEnable        = false;
    private boolean                               mIsFocusEnable         = false;
    private boolean                               mIsEarMonitoringEnable = false;
    private boolean                               mFrontCamera           = true;
    private boolean                               mIsEnableAdjustBitrate = false;
    private V2TXLiveDef.V2TXLiveVideoResolution   mVideoResolution       = V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution960x540;
    private V2TXLiveDef.V2TXLiveAudioQuality      mAudioQuality          = V2TXLiveDef.V2TXLiveAudioQuality.V2TXLiveAudioQualityDefault;
    private V2TXLiveDef.V2TXLiveVideoEncoderParam mVideoEncoderParam     = new V2TXLiveDef.V2TXLiveVideoEncoderParam(mVideoResolution);
    private int                                   mQualityType;
    private boolean                               mIsPrivacyModeFlag     = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTheme(R.style.LivePusherBeautyTheme);
        setContentView(R.layout.livepusher_activity_live_pusher_main);
        initData();                // ???????????????
        initFragment();            // ?????????Fragment
        initPusher();              // ????????? SDK ?????????
        initMainView();            // ???????????????????????? View

        // ???????????????????????????????????????????????????????????????????????????
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.MICROPHONE).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                // ?????????????????????????????????
                startPush();
                mPusherPlayQRCodeFragment.toggle(getFragmentManager(), PUSHER_PLAY_QR_CODE_FRAGMENT);
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                ToastUtils.showShort(R.string.livepusher_app_camera_mic);
                finish();
            }
        }).request();

    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPush();
        mPusherView.onDestroy();
        unInitPhoneListener();
        if (mAudioEffectPanel != null) {
            mAudioEffectPanel.unInit();
            mAudioEffectPanel = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != mAudioEffectPanel && mAudioEffectPanel.getVisibility() != View.GONE && ev.getRawY() < mAudioEffectPanel.getTop()) {
            mAudioEffectPanel.setVisibility(View.GONE);
            mAudioEffectPanel.hideAudioPanel();
            mLinearBottomBar.setVisibility(View.VISIBLE);
        }
        if (null != mBeautyPanelView && mBeautyPanelView.getVisibility() != View.GONE && ev.getRawY() < mBeautyPanelView.getTop()) {
            mBeautyPanelView.setVisibility(View.GONE);
            mLinearBottomBar.setVisibility(View.VISIBLE);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.livepusher_ibtn_back) {
            stopPush();
            finish();
        } else if (id == R.id.livepusher_ibtn_show_log) {
            if (mLogInfoWindow.isShowing()) {
                mLogInfoWindow.dismiss();
            }
            int count = mLogClickCount % 3;
            if (count == 0) {
                mLogInfoWindow.show(view);
                showLog(false);
            } else if (count == 1) {
                showLog(true);
            } else if (count == 2) {
                showLog(false);
            }
            mLogClickCount++;
        } else if (id == R.id.livepusher_ibtn_qrcode) {
            if (mLogInfoWindow.isShowing()) {
                mLogInfoWindow.dismiss();
            }
            mPusherPlayQRCodeFragment.toggle(getFragmentManager(), PUSHER_PLAY_QR_CODE_FRAGMENT);
        } else if (id == R.id.livepusher_btn_start) {
            togglePush();
        } else if (id == R.id.livepusher_btn_switch_camera) {
            // ???????????????????????????
            if (view.getTag() == null || (Boolean) view.getTag()) {
                view.setTag(false);
                view.setBackgroundResource(R.drawable.livepusher_camera_back_btn);
            } else {
                view.setTag(true);
                view.setBackgroundResource(R.drawable.livepusher_camera_front);
            }
            switchCamera();
        } else if (id == R.id.livepusher_btn_beauty) {
            if (mLogInfoWindow.isShowing()) {
                mLogInfoWindow.dismiss();
            }
            if (mBeautyPanelView.isShown()) {
                mBeautyPanelView.setVisibility(View.GONE);
                mLinearBottomBar.setVisibility(View.VISIBLE);
            } else {
                mBeautyPanelView.setVisibility(View.VISIBLE);
                mLinearBottomBar.setVisibility(View.GONE);
            }
        } else if (id == R.id.livepusher_btn_bgm) {
            if (mLogInfoWindow.isShowing()) {
                mLogInfoWindow.dismiss();
            }
            if (mAudioEffectPanel.isShown()) {
                mAudioEffectPanel.setVisibility(View.GONE);
                mAudioEffectPanel.hideAudioPanel();
                mLinearBottomBar.setVisibility(View.VISIBLE);
            } else {
                mAudioEffectPanel.setVisibility(View.VISIBLE);
                mAudioEffectPanel.showAudioPanel();
                mLinearBottomBar.setVisibility(View.GONE);
            }
        } else if (id == R.id.livepusher_btn_video_quality) {
            if (mLogInfoWindow.isShowing()) {
                mLogInfoWindow.dismiss();
            }
            mPusherVideoQualityFragment.toggle(getSupportFragmentManager(), PUSHER_VIDEO_QUALITY_FRAGMENT);
        } else if (id == R.id.livepusher_btn_setting) {
            if (mLogInfoWindow.isShowing()) {
                mLogInfoWindow.dismiss();
            }
            mPusherSettingFragment.toggle(getSupportFragmentManager(), PUSHER_SETTING_FRAGMENT);
        }
    }

    @Override
    public void onMuteChange(boolean enable) {
        setMute(enable);
    }

    @Override
    public void onHomeOrientationChange(boolean isLandscape) {
        mIsLandscape = isLandscape;
        // ???????????????
        V2TXLiveDef.V2TXLiveVideoEncoderParam param = new V2TXLiveDef.V2TXLiveVideoEncoderParam(mVideoResolution);
        param.videoResolutionMode = isLandscape ? V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait;
        mLivePusher.setVideoQuality(param); }

    @Override
    public void onMirrorChange(boolean enable) {
        setMirror(enable);
    }

    @Override
    public void onFlashLightChange(boolean enable) {
        turnOnFlashLight(enable);
    }

    @Override
    public void onWatermarkChange(boolean enable) {
        setWatermark(enable);
    }

    @Override
    public void onTouchFocusChange(boolean enable) {
        setTouchFocus(enable);
        if (mIsPushing) {
            showToast(R.string.livepusher_pushing_start_stop_retry_push_by_focus);
        }
    }

    @Override
    public void onClickSnapshot() {
        snapshot();
    }

    @Override
    public void onAdjustBitrateChange(boolean enable) {
        setAdjustBitrate(enable, mPusherVideoQualityFragment.getQualityType());
    }

    @Override
    public void onQualityChange(int type) {
        setQuality(mPusherSettingFragment.isAdjustBitrate(), type);
    }

    @Override
    public void onEnableAudioEarMonitoringChange(boolean enable) {
        enableAudioEarMonitoring(enable);
    }

    @Override
    public void onAudioQualityChange(V2TXLiveDef.V2TXLiveAudioQuality audioQuality) {
        setAudioQuality(audioQuality);
    }

    private void initData() {
        Intent intent = getIntent();
        mPusherURL = intent.getStringExtra(Constants.INTENT_URL_PUSH);
        mRTMPPlayURL = intent.getStringExtra(Constants.INTENT_URL_PLAY_RTMP);
        mFlvPlayURL = intent.getStringExtra(Constants.INTENT_URL_PLAY_FLV);
        mHlsPlayURL = intent.getStringExtra(Constants.INTENT_URL_PLAY_HLS);
        mRTCPlayURL = intent.getStringExtra(Constants.INTENT_URL_PLAY_RTC);
    }

    /**
     * ????????? SDK ?????????
     */
    private void initPusher() {
        mPusherView = findViewById(R.id.livepusher_tx_cloud_view);

        if (mPusherURL.startsWith("trtc")) {
            mLivePusher = new V2TXLivePusherImpl(this, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTC);
        } else {
            mLivePusher = new V2TXLivePusherImpl(this, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTMP);
        }

        // ??????????????????????????? ???????????????????????????????????? 5??????????????? 3??????????????? 2
        mLivePusher.getBeautyManager().setBeautyStyle(TXLiveConstants.BEAUTY_STYLE_SMOOTH);
        mLivePusher.getBeautyManager().setBeautyLevel(5);
        mLivePusher.getBeautyManager().setWhitenessLevel(3);
        mLivePusher.getBeautyManager().setRuddyLevel(2);

        mWaterMarkBitmap = decodeResource(getResources(), R.drawable.livepusher_watermark);
        initListener();

        setMirror(mPusherSettingFragment.isMirror());
        setWatermark(mPusherSettingFragment.isWatermark());
        setTouchFocus(mPusherSettingFragment.isTouchFocus());
        enableAudioEarMonitoring(mPusherSettingFragment.enableAudioEarMonitoring());
        setQuality(mPusherSettingFragment.isAdjustBitrate(), mPusherVideoQualityFragment.getQualityType());
        setAudioQuality(mPusherSettingFragment.getAudioQuality());
        turnOnFlashLight(mPusherSettingFragment.isFlashEnable());
        mIsLandscape = mPusherSettingFragment.isLandscape();
    }

    /**
     * ???????????????????????? Fragment
     */
    private void initFragment() {
        if (mPusherSettingFragment == null) {
            mPusherSettingFragment = new PusherSettingFragment();
            mPusherSettingFragment.loadConfig(this);
            mPusherSettingFragment.setOnSettingChangeListener(this);
        }
        if (mPusherPlayQRCodeFragment == null) {
            mPusherPlayQRCodeFragment = new PusherPlayQRCodeFragment();
            mPusherPlayQRCodeFragment.setQRCodeURL(mFlvPlayURL, mRTMPPlayURL, mHlsPlayURL,  mRTCPlayURL);
        }
        if (mPusherVideoQualityFragment == null) {
            mPusherVideoQualityFragment = new PusherVideoQualityFragment();
            mPusherVideoQualityFragment.loadConfig(this);
            mPusherVideoQualityFragment.setOnVideoQualityChangeListener(this);
        }
        if (mLogInfoWindow == null) {
            mLogInfoWindow = new LogInfoWindow(this);
        }
    }

    /**
     * ????????? ?????????log???????????? ??? view
     */
    private void initMainView() {
        mBtnStartPush = findViewById(R.id.livepusher_btn_start);
        mBeautyPanelView = findViewById(R.id.livepusher_bp_beauty_pannel);
        mTextNetBusyTips = findViewById(R.id.livepusher_tv_net_error_warning);
        mLinearBottomBar = findViewById(R.id.livepusher_ll_bottom_bar);

        mAudioEffectPanel = findViewById(R.id.livepusher_audio_panel);
        mAudioEffectPanel.setAudioEffectManager(mLivePusher.getAudioEffectManager());
        mAudioEffectPanel.setBackgroundColor(0xff13233F);
        mAudioEffectPanel.setOnAudioEffectPanelHideListener(new AudioEffectPanel.OnAudioEffectPanelHideListener() {
            @Override
            public void onClosePanel() {
                mAudioEffectPanel.setVisibility(View.GONE);
                mLinearBottomBar.setVisibility(View.VISIBLE);
            }
        });

        mBeautyPanelView.setBeautyManager(mLivePusher.getBeautyManager());
        mBeautyPanelView.setOnBeautyListener(new BeautyPanel.OnBeautyListener() {
            @Override
            public boolean onClose() {
                mBeautyPanelView.setVisibility(View.GONE);
                mLinearBottomBar.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    /**
     * ???????????????????????????
     */
    private void showNetBusyTips() {
        if (mTextNetBusyTips.isShown()) {
            return;
        }
        mTextNetBusyTips.setVisibility(View.VISIBLE);
        mTextNetBusyTips.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextNetBusyTips.setVisibility(View.GONE);
            }
        }, 5000);
    }

    private Uri getUri(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.tencent.liteav.demo", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    /**
     * ???????????? "????????????" ????????????????????????
     *
     * @return false---Activity?????????????????????????????????
     */
    private boolean isActivityCanRotation() {
        int flag = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        return flag != 0;
    }

    private void showToast(final @StringRes int resId) {
        showToast(getString(resId));
    }

    private void showToast(final String text) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CameraPushMainActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onPushStart(int code) {
        TXLog.d(TAG, "onPusherStart: code -> " + code);
        switch (code) {
            case Constants.PLAY_STATUS_SUCCESS:
                mBtnStartPush.setBackgroundResource(R.drawable.livepusher_pause);
                break;
            case Constants.PLAY_STATUS_INVALID_URL:
                showToast(R.string.livepusher_url_illegal);
                // ????????????log
                Bundle params = new Bundle();
                params.putString(TXLiveConstants.EVT_DESCRIPTION, getString(R.string.livepusher_check_url));
                mLogInfoWindow.setLogText(null, params, LogInfoWindow.CHECK_RTMP_URL_FAIL);
                break;
            case Constants.PLAY_STATUS_LICENSE_ERROR:
                String errInfo = getString(R.string.livepusher_license_check_fail);
                int start = (errInfo + getString(R.string.livepusher_license_click_info)).length();
                int end = (errInfo + getString(R.string.livepusher_license_click_use_info)).length();
                SpannableStringBuilder spannableStrBuidler = new SpannableStringBuilder(errInfo + getString(R.string.livepusher_license_click_use_info));
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("https://cloud.tencent.com/document/product/454/34750");
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                };
                spannableStrBuidler.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStrBuidler.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                TextView tv = new TextView(this);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(spannableStrBuidler);
                tv.setPadding(20, 0, 20, 0);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle(getString(R.string.livepusher_push_fail)).setView(tv).setPositiveButton(getString(R.string.livepusher_comfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopPush();
                    }
                });
                dialogBuilder.show();
            default:
                break;
        }
        if (code != Constants.PLAY_STATUS_INVALID_URL) {
            // ????????????log
            Bundle bundle = new Bundle();
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, getString(R.string.livepusher_check_url));
            mLogInfoWindow.setLogText(null, bundle, LogInfoWindow.CHECK_RTMP_URL_OK);
        }
    }


    private void startPush() {
        int resultCode = Constants.PLAY_STATUS_SUCCESS;
        String tRTMPURL = "";
        if (!TextUtils.isEmpty(mPusherURL)) {
            String url[] = mPusherURL.split("###");
            if (url.length > 0) {
                tRTMPURL = url[0];
            }
        }

        if (Utils.checkLegalForPushUrl(tRTMPURL)) {
            // ?????????????????????View
            mPusherView.setVisibility(View.VISIBLE);
            // ??????????????????
            mLivePusher.setObserver(new MyPusherObserver());
            // ?????????????????????
            mVideoEncoderParam.videoResolution = mVideoResolution;
            mVideoEncoderParam.videoResolutionMode = mIsLandscape ? V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait;
            mLivePusher.setVideoQuality(mVideoEncoderParam);

            // ?????????????????????????????????
            mLivePusher.setEncoderMirror(mIsMirrorEnable);
            // ????????????????????????
            mPusherView.showLog(mIsDebugInfo);

            // ??????????????????
            if (mIsWaterMarkEnable) {
                mLivePusher.setWatermark(mWaterMarkBitmap, 0.02f, 0.05f, 0.2f);
            } else {
                mLivePusher.setWatermark(null, 0, 0, 0);
            }

            // ????????????????????????
            mLivePusher.getDeviceManager().enableCameraAutoFocus(mIsFocusEnable);

            mLivePusher.getAudioEffectManager().enableVoiceEarMonitor(mIsEarMonitoringEnable);
            // ????????????
            setPushScene(mQualityType, mIsEnableAdjustBitrate);

            // ???????????????????????????????????????????????? TXLivePusher.setVideoQuality ?????????TXLivePusher.startPusher????????????????????????
            setAudioQuality(mAudioQuality);

            // ??????????????????View
            mLivePusher.setRenderView(mPusherView);
            mLivePusher.startCamera(mFrontCamera);
            mLivePusher.startMicrophone();
            if (!mFrontCamera) mLivePusher.getDeviceManager().switchCamera(mFrontCamera);
            // ????????????
            resultCode = mLivePusher.startPush(tRTMPURL.trim());

            mIsPushing = true;
        } else {
            resultCode = Constants.PLAY_STATUS_INVALID_URL;
        }
        TXLog.i(TAG, "start: mIsResume -> " + mIsResume);
        onPushStart(resultCode);
    }

    private void stopPush() {
        if (!mIsPushing) {
            return;
        }
        if (mIsPrivacyModeFlag) {
            mLivePusher.stopVirtualCamera();
        }
        // ??????????????????
        mLivePusher.stopCamera();
        // ????????????
        mLivePusher.setObserver(null);
        // ????????????
        mLivePusher.stopPush();
        // ?????????????????????View
        mPusherView.setVisibility(View.GONE);
        mIsPushing = false;
        mBtnStartPush.setBackgroundResource(R.drawable.livepusher_start);
        mLogInfoWindow.reset();
        mAudioEffectPanel.reset();
    }

    private class MyPusherObserver extends V2TXLivePusherObserver {
        @Override
        public void onWarning(int code, String msg, Bundle extraInfo) {
            Log.w(TAG, "[Pusher] onWarning errorCode: " + code + ", msg " + msg);
            if (code == V2TXLiveCode.V2TXLIVE_WARNING_NETWORK_BUSY) {
                showNetBusyTips();
            }
        }

        @Override
        public void onError(int code, String msg, Bundle extraInfo) {
            Log.e(TAG, "[Pusher] onError: " + msg + ", extraInfo " + extraInfo);
        }

        @Override
        public void onCaptureFirstAudioFrame() {
            Log.i(TAG, "[Pusher] onCaptureFirstAudioFrame");
        }

        @Override
        public void onCaptureFirstVideoFrame() {
            Log.i(TAG, "[Pusher] onCaptureFirstVideoFrame");
        }

        @Override
        public void onMicrophoneVolumeUpdate(int volume) {
        }

        @Override
        public void onPushStatusUpdate(V2TXLiveDef.V2TXLivePushStatus status, String msg, Bundle bundle) {
        }

        @Override
        public void onSnapshotComplete(Bitmap bitmap) {
            if (mLivePusher.isPushing() == 1) {
                if (bitmap != null) {
                    saveSnapshotBitmap(bitmap);
                } else {
                    showToast(R.string.livepusher_screenshot_fail);
                }
            } else {
                showToast(R.string.livepusher_screenshot_fail_push);
            }
        }

        @Override
        public void onStatisticsUpdate(V2TXLiveDef.V2TXLivePusherStatistics statistics) {
            Bundle netStatus = new Bundle();
            netStatus.putInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH, statistics.width);
            netStatus.putInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT, statistics.height);
            int appCpu = statistics.appCpu / 10;
            int totalCpu = statistics.systemCpu / 10;
            String strCpu = appCpu + "/" + totalCpu + "%";
            netStatus.putCharSequence(TXLiveConstants.NET_STATUS_CPU_USAGE, strCpu);
            netStatus.putInt(TXLiveConstants.NET_STATUS_NET_SPEED, statistics.videoBitrate + statistics.audioBitrate);
            netStatus.putInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE, statistics.audioBitrate);
            netStatus.putInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE, statistics.videoBitrate);
            netStatus.putInt(TXLiveConstants.NET_STATUS_VIDEO_FPS, statistics.fps);
            netStatus.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, 5);
            Log.d(TAG, "Current status, CPU:" + netStatus.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                    ", RES:" + netStatus.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + netStatus.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                    ", SPD:" + netStatus.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                    ", FPS:" + netStatus.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                    ", ARA:" + netStatus.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                    ", VRA:" + netStatus.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
            mLogInfoWindow.setLogText(netStatus, null, 0);
        }
    }

    private void togglePush() {
        if (mIsPushing) {
            stopPush();
        } else {
            startPush();
        }
    }

    private void resume() {
        TXLog.i(TAG, "resume: mIsResume -> " + mIsResume);
        if (mIsResume) {
            return;
        }
        if (mPusherView != null) {
            mPusherView.onResume();
        }
        mLivePusher.stopVirtualCamera();
        if (mIsMuteAudio) {// audio?????????????????????????????? MuteAudio ??? PausePusher ??????????????????????????????
            mLivePusher.pauseAudio();
        } else {
            mLivePusher.resumeAudio();
        }
        mLivePusher.resumeVideo();
        mIsResume = true;
        mAudioEffectPanel.resumeBGM();
    }

    private void pause() {
        TXLog.i(TAG, "pause: mIsResume -> " + mIsResume);
        if (mPusherView != null) {
            mPusherView.onPause();
        }
        mLivePusher.startVirtualCamera(decodeResource(getResources(), R.drawable.livepusher_pause_publish));
        mLivePusher.pauseAudio();
        mIsResume = false;
        mAudioEffectPanel.pauseBGM();
    }

    private void setMute(boolean enable) {
        mIsMuteAudio = enable;
        if (enable) {
            mLivePusher.pauseAudio();
        } else {
            mLivePusher.resumeAudio();
        }
    }

    private void switchCamera() {
        mFrontCamera = !mFrontCamera;
        mLivePusher.getDeviceManager().switchCamera(mFrontCamera);
    }

    private void setMirror(boolean enable) {
        mIsMirrorEnable = enable;
        mLivePusher.setEncoderMirror(enable);
    }

    private void turnOnFlashLight(boolean enable) {
        mLivePusher.getDeviceManager().enableCameraTorch(enable);
    }

    private void showLog(boolean enable) {
        mIsDebugInfo = enable;
        mPusherView.showLog(enable);
    }

    private void setWatermark(boolean enable) {
        mIsWaterMarkEnable = enable;
        if (enable) {
            mLivePusher.setWatermark(mWaterMarkBitmap, 0.02f, 0.05f, 0.2f);
        } else {
            mLivePusher.setWatermark(null, 0, 0, 0);
        }
    }

    private void setTouchFocus(boolean enable) {
        mIsFocusEnable = !enable;
        mLivePusher.getDeviceManager().enableCameraAutoFocus(mIsFocusEnable);
        if (mLivePusher.isPushing() == 1) {
            stopPush();
            startPush();
        }
    }

    private void snapshot() {
        mLivePusher.snapshot();
    }

    private void setAdjustBitrate(boolean enable, int qualityType) {
        mIsEnableAdjustBitrate = enable;
        setPushScene(qualityType, enable);
    }

    private void setQuality(boolean enable, int type) {
        setPushScene(type, enable);
    }

    private void enableAudioEarMonitoring(boolean enable) {
        mIsEarMonitoringEnable = enable;
        if (mLivePusher != null) {
            mLivePusher.getAudioEffectManager().enableVoiceEarMonitor(enable);
        }
    }

    private void setAudioQuality(V2TXLiveDef.V2TXLiveAudioQuality audioQuality) {
        mAudioQuality = audioQuality;
        if (mLivePusher != null) {
            mLivePusher.setAudioQuality(audioQuality);
        }
    }

    /**
     * ??????????????????
     * <p>
     * SDK ?????????????????????????????????????????? ?????????????????????FPS?????????????????????????????????????????????????????? ???????????????
     * <p>
     * ????????????????????????????????????????????????
     * <p>
     */
    private void setPushScene(int type, boolean enableAdjustBitrate) {
        TXLog.i(TAG, "setPushScene: type = " + type + " enableAdjustBitrate = " + enableAdjustBitrate);
        mQualityType = type;
        mIsEnableAdjustBitrate = enableAdjustBitrate;
        switch (type) {
            case TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION:     // 360P
                if (mLivePusher != null) {
                    mVideoResolution = V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution640x360;
                    mVideoEncoderParam.videoResolution = mVideoResolution;
                    mVideoEncoderParam.videoResolutionMode = mIsLandscape ? V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait;
                    mLivePusher.setVideoQuality(mVideoEncoderParam);
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION:         // 540P
                if (mLivePusher != null) {
                    mVideoResolution = V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution960x540;
                    mVideoEncoderParam.videoResolution = mVideoResolution;
                    mVideoEncoderParam.videoResolutionMode = mIsLandscape ? V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait;
                    mLivePusher.setVideoQuality(mVideoEncoderParam);
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION:        // 720p
                if (mLivePusher != null) {
                    mVideoResolution = V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1280x720;
                    mVideoEncoderParam.videoResolution = mVideoResolution;
                    mVideoEncoderParam.videoResolutionMode = mIsLandscape ? V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait;
                    mLivePusher.setVideoQuality(mVideoEncoderParam);
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_ULTRA_DEFINITION:        // 1080p
                if (mLivePusher != null) {
                    mVideoResolution = V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1920x1080;
                    mVideoEncoderParam.videoResolution = mVideoResolution;
                    mVideoEncoderParam.videoResolutionMode = mIsLandscape ? V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait;
                    mLivePusher.setVideoQuality(mVideoEncoderParam);
                }
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    private void initListener() {
        mPhoneListener = new TXPhoneStateListener();
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * ??????
     */
    private void unInitPhoneListener() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
    }

    /**
     * ??????????????????
     *
     * @param resources
     * @param id
     * @return
     */
    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    /**
     * ?????????????????????
     *
     * @param bmp
     */
    private void saveSnapshotBitmap(final Bitmap bmp) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String bitmapFileName = UUID.randomUUID().toString();//??????UUID????????????????????????
                FileOutputStream out = null;
                File sdcardDir = getExternalFilesDir(null);
                if (sdcardDir == null) {
                    TXLog.e(TAG, "sdcardDir is null");
                    return;
                }
                final String path = sdcardDir + File.separator + bitmapFileName + ".png";
                final File file = new File(path);
                try {
                    file.getParentFile().mkdirs();
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    out = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.exists() && file.length() > 0) {
                    showToast(R.string.livepusher_screenshot_success);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);//??????????????????
                    Uri uri = getUri(file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, getString(R.string.livepusher_share_pic)));
                } else {
                    showToast(R.string.livepusher_screenshot_fail);
                }
            }
        });
    }

    /**
     * ????????????
     */
    private class TXPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXLog.i(TAG, "onCallStateChanged: state -> " + state);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:   //??????????????????
                case TelephonyManager.CALL_STATE_OFFHOOK:   //????????????
                    pause();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:      //????????????
                    resume();
                    break;
            }
        }
    }

}
