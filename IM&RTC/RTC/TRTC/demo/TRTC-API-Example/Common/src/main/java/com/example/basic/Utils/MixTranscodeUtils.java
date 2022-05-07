package com.example.basic.Utils;

import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;

import java.util.ArrayList;
import java.util.List;

public class MixTranscodeUtils {
    //    混流转码之后，画布画面 & 主画面的分辨率大小
    private static int videoWidth = 480;
    private static int videoHeight = 640;

    private static int mixMode1 = TRTCCloudDef.TRTC_TranscodingConfigMode_Manual;
    private static int mixMode2 = TRTCCloudDef.TRTC_TranscodingConfigMode_Template_PureAudio;
    private static int mixMode3 = TRTCCloudDef.TRTC_TranscodingConfigMode_Template_PresetLayout;
    private static int mixMode4 = TRTCCloudDef.TRTC_TranscodingConfigMode_Template_ScreenSharing;

//    设置混流后的画面质量和混流模式
//    配置承载混流后的画布画面的属性（未配置CDN直播）
//    带配置：
//    1.backgroundColor
//    2.audioCodec: 音频编码输出格式
//    3.streamId
    private static TRTCCloudDef.TRTCTranscodingConfig setCanvasQuality(int mixMode) {
        TRTCCloudDef.TRTCTranscodingConfig config = new TRTCCloudDef.TRTCTranscodingConfig();
// 设置分辨率为720 × 1280, 码率为1500kbps，帧率为20FPS
        config.appId = 1307756651;
        config.bizId = 153663;
        config.videoWidth = videoWidth;
        config.videoHeight = videoHeight;
        config.videoBitrate = 1500;
        config.videoFramerate = 20;
        config.videoGOP = 2;
        config.audioSampleRate = 48000;
        config.audioBitrate = 64;
        config.audioChannels = 2;
        config.mode = mixMode;
//        背景图片——QQ
        config.backgroundImage = "19475";
        config.mixUsers = new ArrayList<>();
        return config;
    }

//    全手动模式
//    一路主画面 + 两路远端画面: 480P + 180P
    public static void mixTranscodeAuto1(TRTCCloud mTRTCCloud, String roomId, String localUid, List<String> remoteUidList) {
//        Local_Main
        int mainWidth = videoWidth;
        int mainHeight = videoHeight;
        int mainOffsetX = 0;
        int mainOffsetY = 0;

//        Remote_Sub
        int subWidth = 180;
        int subHeight = 240;
        int subOffsetX = 270;
        int subOffsetY = 360;

//        全手动模式
        TRTCCloudDef.TRTCTranscodingConfig config = setCanvasQuality(mixMode1);

// 主播摄像头的画面位置
        TRTCCloudDef.TRTCMixUser local = new TRTCCloudDef.TRTCMixUser();
        local.userId = localUid;
        local.zOrder = 0;   // zOrder 为0代表主播画面位于最底层
        local.x = mainOffsetX;
        local.y = mainOffsetY;
        local.width = mainWidth;
        local.height = mainHeight;
        local.roomId = null; // 本地用户不用填写 roomID，远程需要
        config.mixUsers.add(local);

//        远端画面的人数
        int size = remoteUidList.size();
        if (size == 1) {
            // 连麦者1的画面位置
            TRTCCloudDef.TRTCMixUser remote1 = new TRTCCloudDef.TRTCMixUser();
            remote1.userId = remoteUidList.get(0);
            remote1.zOrder = 1;
            remote1.x = subOffsetX; //仅供参考
            remote1.y = subOffsetY; //仅供参考
            remote1.width = subWidth; //仅供参考
            remote1.height = subHeight; //仅供参考
            remote1.roomId = roomId; // 本地用户不用填写 roomID，远程需要
            config.mixUsers.add(remote1);
        } else if (size == 2) {
            // 连麦者1的画面位置
            TRTCCloudDef.TRTCMixUser remote1 = new TRTCCloudDef.TRTCMixUser();
            remote1.userId = remoteUidList.get(0);
            remote1.zOrder = 1;
            remote1.x = subOffsetX; //仅供参考
            remote1.y = subOffsetY; //仅供参考
            remote1.width = subWidth; //仅供参考
            remote1.height = subHeight; //仅供参考
            remote1.roomId = roomId; // 本地用户不用填写 roomID，远程需要
            config.mixUsers.add(remote1);

            // 连麦者2的画面位置，官网代码存在remote2覆盖remote1位置的问题 https://cloud.tencent.com/document/product/647/16827#.E9.A2.84.E6.8E.92.E7.89.88.E6.A8.A1.E5.BC.8F.EF.BC.88presetlayout.EF.BC.89
            TRTCCloudDef.TRTCMixUser remote2 = new TRTCCloudDef.TRTCMixUser();
            remote2.userId = remoteUidList.get(1);
            remote2.zOrder = 2;
            remote2.x = subOffsetX; //仅供参考
            remote2.y = subOffsetY - 340; //仅供参考
            remote2.width = subWidth; //仅供参考
            remote2.height = subHeight; //仅供参考
            remote2.roomId = roomId; // 本地用户不用填写 roomID，远程需要
            config.mixUsers.add(remote2);
        }
// 发起云端混流
        mTRTCCloud.setMixTranscodingConfig(config);
    }

//    预排版模式
//    一路主画面 + 两路远端画面: 480P + 180P
    public static void mixTranscodeAuto3(TRTCCloud mTRTCCloud, String roomId) {
//        Local_Main
        int mainWidth = videoWidth;
        int mainHeight = videoHeight;
        int mainOffsetX = 0;
        int mainOffsetY = 0;

//        Remote_Sub
        int subWidth = 180;
        int subHeight = 240;
        int subOffsetX = 270;
        int subOffsetY = 360;

//        预排版模式
        TRTCCloudDef.TRTCTranscodingConfig config = setCanvasQuality(mixMode3);

// 主播摄像头的画面位置
        TRTCCloudDef.TRTCMixUser local = new TRTCCloudDef.TRTCMixUser();
        local.userId = "$PLACE_HOLDER_LOCAL_MAIN$";
        local.zOrder = 0;   // zOrder 为0代表主播画面位于最底层
        local.x = mainOffsetX;
        local.y = mainOffsetY;
        local.width = mainWidth;
        local.height = mainHeight;
        local.roomId = null; // 本地用户不用填写 roomID，远程需要
        config.mixUsers.add(local);

// 连麦者1的画面位置
        TRTCCloudDef.TRTCMixUser remote1 = new TRTCCloudDef.TRTCMixUser();
        remote1.userId = "$PLACE_HOLDER_REMOTE$";
        remote1.zOrder = 1;
        remote1.x = subOffsetX; //仅供参考
        remote1.y = subOffsetY; //仅供参考
        remote1.width = subWidth; //仅供参考
        remote1.height = subHeight; //仅供参考
//        混相同房间的音视频流
        remote1.roomId = roomId; // 本地用户不用填写 roomID，远程需要
//        混不同房间的音视频流，经验证无法实现
//        remote1.roomId = "321"; // 本地用户不用填写 roomID，远程需要
        config.mixUsers.add(remote1);

// 连麦者2的画面位置，官网代码存在remote2覆盖remote1位置的问题 https://cloud.tencent.com/document/product/647/16827#.E9.A2.84.E6.8E.92.E7.89.88.E6.A8.A1.E5.BC.8F.EF.BC.88presetlayout.EF.BC.89
        TRTCCloudDef.TRTCMixUser remote2 = new TRTCCloudDef.TRTCMixUser();
        remote2.userId = "$PLACE_HOLDER_REMOTE$";
        remote2.zOrder = 2;
        remote2.x = subOffsetX; //仅供参考
        remote2.y = subOffsetY - 340; //仅供参考
        remote2.width = subWidth; //仅供参考
        remote2.height = subHeight; //仅供参考
        remote2.roomId = roomId; // 本地用户不用填写 roomID，远程需要
        config.mixUsers.add(remote2);

//        设置混流输出的streamId/streamName
//        config.streamId = "MixedFlow_PresetLayout";

// 发起云端混流
        mTRTCCloud.setMixTranscodingConfig(config);
    }

    //    一路主画面 + 六路远端画面
    public static void mixTransCodeAuto3_1(TRTCCloud mTRTCCloud, String roomId) {
//        Local_Main
        int mainWidth = videoWidth;
        int mainHeight = videoHeight;
        int mainOffsetX = 0;
        int mainOffsetY = 0;

//        Remote_Sub
        int subWidth = 120;
        int subHeight = 160;
        int subOffsetX = 32;
        int subOffsetY = 120;

        //预排版模式
        TRTCCloudDef.TRTCTranscodingConfig config = setCanvasQuality(mixMode3);

        // 设置混流后主播的画面位置
        TRTCCloudDef.TRTCMixUser mainMixUser = new TRTCCloudDef.TRTCMixUser();
        mainMixUser.userId = "$PLACE_HOLDER_LOCAL_MAIN$";
        mainMixUser.zOrder = 0;   // zOrder 为0代表主播画面位于最底层
        mainMixUser.x = mainOffsetX;
        mainMixUser.y = mainOffsetY;
        mainMixUser.width = mainWidth;
        mainMixUser.height = mainHeight;
        mainMixUser.roomId = null; // 本地用户不用填写 roomID，远程需要
        config.mixUsers.add(mainMixUser);

        List<TRTCCloudDef.TRTCMixUser> subMixList = initSubMixUser(roomId, subWidth, subHeight, 1, 6);
        for (int i = 0; i < subMixList.size(); i++) {
            TRTCCloudDef.TRTCMixUser _mixUser = subMixList.get(i);
            _mixUser.userId = "$PLACE_HOLDER_REMOTE$";
            if (i < 3) {
                // 前三个小画面靠右从下往上铺
                _mixUser.x = videoWidth - subOffsetX - subWidth;
                _mixUser.y = (subOffsetY + subHeight) * (2 - i) + subOffsetY;
                _mixUser.width = subWidth;
                _mixUser.height = subHeight;
            } else {
                // 后三个小画面靠左从下往上铺
                _mixUser.x = subOffsetX;
                _mixUser.y = (subOffsetY + subHeight) * (5 - i) + subOffsetY;
                _mixUser.width = subWidth;
                _mixUser.height = subHeight;
            }
            config.mixUsers.add(_mixUser);
        }
        mTRTCCloud.setMixTranscodingConfig(config);
    }

    //    屏幕分享模式
    //    一路主画面 + 两路远端画面: 480P + 180P
    public static void mixTranscodeAuto4(TRTCCloud mTRTCCloud, String roomId, String localUid, List<String> remoteUidList) {
//        Local_Main
        int mainWidth = videoWidth;
        int mainHeight = videoHeight;
        int mainOffsetX = 0;
        int mainOffsetY = 0;

//        Remote_Sub
        int subWidth = 180;
        int subHeight = 240;
        int subOffsetX = 270;
        int subOffsetY = 360;

//        全手动模式
        TRTCCloudDef.TRTCTranscodingConfig config = setCanvasQuality(mixMode4);

// 主播摄像头的画面位置
//        TRTCCloudDef.TRTCMixUser local = new TRTCCloudDef.TRTCMixUser();
//        local.userId = localUid;
//        local.zOrder = 0;   // zOrder 为0代表主播画面位于最底层
//        local.x = mainOffsetX;
//        local.y = mainOffsetY;
//        local.width = mainWidth;
//        local.height = mainHeight;
//        local.roomId = null; // 本地用户不用填写 roomID，远程需要
//        config.mixUsers.add(local);

//        辅路屏幕画面
        config.videoWidth = 0;
        config.videoHeight = 0;
        config.videoBitrate = 0;

// 发起云端混流
        mTRTCCloud.setMixTranscodingConfig(config);
    }

    private static List<TRTCCloudDef.TRTCMixUser> initSubMixUser(String roomId, int subWidth, int subHeight, int mainNum, int subNum) {
        List<TRTCCloudDef.TRTCMixUser> subUserList = new ArrayList<>();
        for (int i = 0; i < subNum; i++) {
            TRTCCloudDef.TRTCMixUser mixUser = new TRTCCloudDef.TRTCMixUser();
            mixUser.zOrder = mainNum + i;
            mixUser.streamType = TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_BIG;
            mixUser.roomId = roomId;
            subUserList.add(mixUser);
        }
        return subUserList;
    }
}
