package com.tencent.trtc.videocall;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basic.TRTCBaseActivity;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.liteav.device.TXDeviceManager;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.debug.Constant;
import com.tencent.trtc.debug.GenerateTestUserSig;
import com.example.basic.Utils.MixTranscodeUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_RESOLUTION_1920_1080;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_SUB;

/**
 * TRTC视频通话的主页面
 *
 * 包含如下简单功能：
 * - 进入视频通话房间{@link VideoCallingActivity#enterRoom()}
 * - 退出视频通话房间{@link VideoCallingActivity#exitRoom()}
 * - 切换前置/后置摄像头{@link VideoCallingActivity#switchCamera()}
 * - 打开/关闭摄像头{@link VideoCallingActivity#muteVideo()}
 * - 打开/关闭麦克风{@link VideoCallingActivity#muteAudio()}
 * - 显示房间内其他用户的视频画面（当前示例最多可显示6个其他用户的视频画面）{@link TRTCCloudImplListener#refreshRemoteVideoViews()}
 *
 * - 详见接入文档{https://cloud.tencent.com/document/product/647/42045}
 */

/**
 * Video Call
 *
 * Features:
 * - Enter a video call room: {@link VideoCallingActivity#enterRoom()}
 * - Exit a video call room: {@link VideoCallingActivity#exitRoom()}
 * - Switch between the front and rear cameras: {@link VideoCallingActivity#switchCamera()}
 * - Turn on/off the camera: {@link VideoCallingActivity#muteVideo()}
 * - Turn on/off the mic: {@link VideoCallingActivity#muteAudio()}
 * - Display the video of other users (max. 6) in the room: {@link TRTCCloudImplListener#refreshRemoteVideoViews()}
 *
 * - For more information, please see the integration document {https://cloud.tencent.com/document/product/647/42045}.
 */
public class VideoCallingActivity extends TRTCBaseActivity implements View.OnClickListener {

    private static final String             TAG = "VideoCallingActivity";
    private static final int                OVERLAY_PERMISSION_REQ_CODE = 1234;

    private TextView mTextTitle;
    private TXCloudVideoView                mTXCVVLocalPreviewView;
    private ImageView                       mImageBack;
    private Button                          mButtonMuteVideo;
    private Button                          mButtonMuteAudio;
    private Button                          mButtonSwitchCamera;
    private Button                          mButtonAudioRoute;

    private Button                          mButtonSCR_open;
    private Button                          mButtonSCR_close;

    private TRTCCloud                       mTRTCCloud;
    private TXDeviceManager                 mTXDeviceManager;
    private boolean                         mIsFrontCamera = true;
    private List<String>                    mRemoteUidList;
    private List<TXCloudVideoView>          mRemoteViewList;
    private int                             mUserCount = 0;
    private String                          mRoomId;
    private String                          mUserId;
    private boolean                         mAudioRouteFlag = true;
    private FloatingView                    mFloatingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videocall_activity_calling);
        getSupportActionBar().hide();
        handleIntent();

        if (checkPermission()) {
            initView();
            enterRoom();
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            if (intent.getStringExtra(Constant.USER_ID) != null) {
                mUserId = intent.getStringExtra(Constant.USER_ID);
            }
            if (intent.getStringExtra(Constant.ROOM_ID) != null) {
                mRoomId = intent.getStringExtra(Constant.ROOM_ID);
            }
        }
    }

    private void initView() {
        mTextTitle = findViewById(R.id.tv_room_number);
        mImageBack = findViewById(R.id.iv_back);
        mTXCVVLocalPreviewView = findViewById(R.id.txcvv_main);
        mButtonMuteVideo = findViewById(R.id.btn_mute_video);
        mButtonMuteAudio = findViewById(R.id.btn_mute_audio);
        mButtonSwitchCamera = findViewById(R.id.btn_switch_camera);
        mButtonAudioRoute = findViewById(R.id.btn_audio_route);

        mButtonSCR_open = findViewById(R.id.btn_screen_capture_open);
        mButtonSCR_close = findViewById(R.id.btn_screen_capture_close);

        if (!TextUtils.isEmpty(mRoomId)) {
            mTextTitle.setText(getString(R.string.videocall_roomid) + mRoomId);
        }
        mImageBack.setOnClickListener(this);
        mButtonMuteVideo.setOnClickListener(this);
        mButtonMuteAudio.setOnClickListener(this);
        mButtonSwitchCamera.setOnClickListener(this);
        mButtonAudioRoute.setOnClickListener(this);

        mButtonSCR_open.setOnClickListener(this);
        mButtonSCR_close.setOnClickListener(this);

        mRemoteUidList = new ArrayList<>();
        mRemoteViewList = new ArrayList<>();
        mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_view_1));
        mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_view_2));
        mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_view_3));
        mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_view_4));
        mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_view_5));
        mRemoteViewList.add((TXCloudVideoView)findViewById(R.id.trtc_view_6));

        mFloatingView = new FloatingView(getApplicationContext(), R.layout.videocall_view_floating_default);
        mFloatingView.setPopupWindow(R.layout.videocall_popup_layout);
        mFloatingView.setOnPopupItemClickListener(this);
    }

    private void enterRoom() {
        mTRTCCloud = TRTCCloud.sharedInstance(getApplicationContext());
        mTRTCCloud.setListener(new TRTCCloudImplListener(VideoCallingActivity.this));
        mTXDeviceManager = mTRTCCloud.getDeviceManager();

        TRTCCloudDef.TRTCParams trtcParams = new TRTCCloudDef.TRTCParams();
        trtcParams.sdkAppId = GenerateTestUserSig.SDKAPPID;
        trtcParams.userId = mUserId;
//        整型房间号
        trtcParams.roomId = Integer.parseInt(mRoomId);
//        字符串房间号
//        trtcParams.strRoomId = mRoomId;
        trtcParams.userSig = GenerateTestUserSig.genTestUserSig(trtcParams.userId);
//        自定义设置单路流的streamId
//        trtcParams.streamId = "Stream_DIY";

//        云端录制_指定用户
//        trtcParams.userDefineRecordId = "0203";
//        trtcParams.userDefineRecordId = "0203_2";

//        推流（主路的摄像头画面）：设置编码器推出的画面质量——横竖屏、分辨率、帧率、码率等
        TRTCCloudDef.TRTCVideoEncParam encparam = new TRTCCloudDef.TRTCVideoEncParam();
        encparam.videoResolutionMode = TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE;
        encparam.videoResolution = TRTC_VIDEO_RESOLUTION_1920_1080;
        encparam.videoFps = 8;
        encparam.enableAdjustRes = false;
        encparam.videoBitrate = 2000;

        mTRTCCloud.setVideoEncoderParam(encparam);

//        打开摄像头的本地预览，也要在进房之前来设置
        mTRTCCloud.startLocalPreview(mIsFrontCamera, mTXCVVLocalPreviewView);

//        进房之前TRTC混流——预排版模式
        MixTranscodeUtils.mixTranscodeAuto3(mTRTCCloud, mRoomId);

//        进房之前TRTC混流——屏幕分享，经验证后为Android暂时官方未开通此功能而不支持实现
//        MixTranscodeUtils.mixTranscodeAuto4(mTRTCCloud, mRoomId, "0", new ArrayList<String>());

        //采集声音
//        采集声音startLocalAudio要在调用enterRoom之前来设置，同时根据quality分别为：
//        1.TRTC_AUDIO_QUALITY_SPEECH：只能采集通话通道的声音
//        2.TRTC_AUDIO_QUALITY_DEFAULT：麦上通话，麦下媒体：既能采集通话通道的声音，也能采集媒体通道的声音
//        1.TRTC_AUDIO_QUALITY_SPEECH：既能采集通话通道的声音，也能采集媒体通道的声音
//        mTRTCCloud.startLocalAudio(TRTCCloudDef.TRTC_AUDIO_QUALITY_SPEECH);
        mTRTCCloud.startLocalAudio(TRTCCloudDef.TRTC_AUDIO_QUALITY_DEFAULT);
//        mTRTCCloud.startLocalAudio(TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC);

        mTRTCCloud.enterRoom(trtcParams, TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestDrawOverLays();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null && mFloatingView.isShown()) {
            mFloatingView.dismiss();
        }
        exitRoom();
    }

    private void exitRoom() {
        if (mTRTCCloud != null) {
            mTRTCCloud.stopLocalAudio();
            mTRTCCloud.stopLocalPreview();
            mTRTCCloud.exitRoom();
            mTRTCCloud.setListener(null);
        }
        mTRTCCloud = null;
        TRTCCloud.destroySharedInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFloatingView != null && mFloatingView.isShown()) {
            mFloatingView.dismiss();
        }
    }

    @Override
    protected void onPermissionGranted() {
        initView();
        enterRoom();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.btn_mute_video) {
            muteVideo();
        } else if (id == R.id.btn_mute_audio) {
            muteAudio();
        } else if (id == R.id.btn_switch_camera) {
            switchCamera();
        } else if (id == R.id.btn_audio_route) {
            audioRoute();
        } else if (id == R.id.iv_return){
            floatViewClick();
        } else if (id == R.id.btn_screen_capture_open) {
            startScreenCapute();
        } else if (id == R.id.btn_screen_capture_close) {
            mTRTCCloud.stopScreenCapture();
            Toast.makeText(getApplicationContext(), "屏幕分享已关闭", Toast.LENGTH_LONG).show();
        }
    }

    private void startScreenCapute() {
//        推流（辅路的屏幕分享画面）：设置编码器推出的画面质量——横竖屏、分辨率、帧率、码率等
        TRTCCloudDef.TRTCVideoEncParam encparam = new TRTCCloudDef.TRTCVideoEncParam();
        encparam.videoResolutionMode = TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE;
        encparam.videoResolution = TRTC_VIDEO_RESOLUTION_1920_1080;

        TRTCCloudDef.TRTCScreenShareParams shareParams = new TRTCCloudDef.TRTCScreenShareParams();

        mTRTCCloud.startScreenCapture(TRTC_VIDEO_STREAM_TYPE_SUB, encparam, shareParams);
    }

    private void floatViewClick() {
        Intent intent = new Intent(this, VideoCallingActivity.class);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void muteVideo() {
        boolean isSelected = mButtonMuteVideo.isSelected();
        if (!isSelected) {
            mTRTCCloud.stopLocalPreview();
            mButtonMuteVideo.setText(getString(R.string.videocall_open_camera));
        } else {
            mTRTCCloud.startLocalPreview(mIsFrontCamera, mTXCVVLocalPreviewView);
            mButtonMuteVideo.setText(getString(R.string.videocall_close_camera));
        }
        mButtonMuteVideo.setSelected(!isSelected);
    }

    private void muteAudio() {
        boolean isSelected = mButtonMuteAudio.isSelected();
        if (!isSelected) {
            mTRTCCloud.muteLocalAudio(true);
            mButtonMuteAudio.setText(getString(R.string.videocall_mute_audio));
        } else {
            mTRTCCloud.muteLocalAudio(false);
            mButtonMuteAudio.setText(getString(R.string.videocall_close_mute_audio));
        }
        mButtonMuteAudio.setSelected(!isSelected);
    }

    private void switchCamera() {
        mIsFrontCamera = !mIsFrontCamera;
        mTXDeviceManager.switchCamera(mIsFrontCamera);
        if(mIsFrontCamera){
            mButtonSwitchCamera.setText( getString(R.string.videocall_user_back_camera));
        }else{
            mButtonSwitchCamera.setText( getString(R.string.videocall_user_front_camera));
        }
    }

    private void audioRoute() {
        if(mAudioRouteFlag){
            mAudioRouteFlag = false;
            mTXDeviceManager.setAudioRoute(TXDeviceManager.TXAudioRoute.TXAudioRouteEarpiece);
            mButtonAudioRoute.setText(getString(R.string.videocall_use_speaker));
        }else{
            mAudioRouteFlag = true;
            mTXDeviceManager.setAudioRoute(TXDeviceManager.TXAudioRoute.TXAudioRouteSpeakerphone);
            mButtonAudioRoute.setText(getString(R.string.videocall_use_receiver));
        }
    }

    private class TRTCCloudImplListener extends TRTCCloudListener {

        private WeakReference<VideoCallingActivity> mContext;

        public TRTCCloudImplListener(VideoCallingActivity activity) {
            super();
            mContext = new WeakReference<>(activity);
        }

        @Override
        public void onUserVideoAvailable(String userId, boolean available) {
            Log.d(TAG, "onUserVideoAvailable userId " + userId + ", mUserCount " + mUserCount + ",available " + available);
            int index = mRemoteUidList.indexOf(userId);
            if (available) {
//                index = -1，就是没在远端用户列表中找到该远端用户，常常是首次进房且画面可见的时候；
//                第二次开始index就≠-1了，那就直接返回，无需重复渲染画面到指定的View上
                if (index != -1) {
                    return;
                }
                mRemoteUidList.add(userId);
                refreshRemoteVideoViews();
//                available = true 时，应该在远端用户第一次进房（index = -1，远端用户列表不存在这个用户时）的时候才混流；
//                避免当用户在这个房间时而重复混流
//                MixTranscodeUtils.mixTranscodeAuto3(mTRTCCloud, mRoomId, mUserId, mRemoteUidList);
            } else {
                if (index == -1) {
                    return;
                }
                mTRTCCloud.stopRemoteView(userId, TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_BIG);
                mRemoteUidList.remove(index);
//                停止订阅某个远端用户的画面之后，需要重新渲染剩下的远端用户画面，比如：画面位置更新
                refreshRemoteVideoViews();
//                available = false 时，应该从远端用户列表中移除已经存在的远端用户（index ≠ -1）之后再发起混流；
//                将此远端用户从混流画面中移除
//                MixTranscodeUtils.mixTranscodeAuto3(mTRTCCloud, mRoomId, mUserId, mRemoteUidList);
            }
        }

        @Override
        public void onUserAudioAvailable(String userId, boolean available) {
//            TO DO
        }

        private void refreshRemoteVideoViews() {
            for (int i = 0; i < mRemoteViewList.size(); i++) {
                if (i < mRemoteUidList.size()) {
                    String remoteUid = mRemoteUidList.get(i);
                    mRemoteViewList.get(i).setVisibility(View.VISIBLE);
//                  方案一：李亚萍的方案——拉流端开启远端镜像，不行
//                    mTRTCCloud.setVideoEncoderMirror(true);

//                  方案二：我的方法——拉流端开启远端旋转180 + 镜像，可以
//                    TRTCCloudDef.TRTCRenderParams trtcRenderParams = new TRTCCloudDef.TRTCRenderParams();
//                    trtcRenderParams.rotation = TRTC_VIDEO_ROTATION_180;
//                    trtcRenderParams.mirrorType = TRTC_VIDEO_MIRROR_TYPE_ENABLE;
//                    mTRTCCloud.setRemoteRenderParams(remoteUid, TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_SMALL, trtcRenderParams);

//                    方案三：推流端旋转180° & 拉流端不动，可以
                    mTRTCCloud.startRemoteView(remoteUid, TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_BIG, mRemoteViewList.get(i));
                } else {
                    mRemoteViewList.get(i).setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.d(TAG, "sdk callback onError");
            VideoCallingActivity activity = mContext.get();
            if (activity != null) {
                Toast.makeText(activity, "onError: " + errMsg + "[" + errCode+ "]" , Toast.LENGTH_SHORT).show();
                if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL) {
                    activity.exitRoom();
                }
            }
        }
    }

    public void requestDrawOverLays() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N && !Settings.canDrawOverlays(VideoCallingActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + VideoCallingActivity.this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            showFloatingView();
        }
    }

    private void showFloatingView() {
        if (mFloatingView != null && !mFloatingView.isShown()) {
            if ((null != mTRTCCloud)) {
                mFloatingView.show();
                mFloatingView.setOnPopupItemClickListener(this);
            }
        }
    }
}
