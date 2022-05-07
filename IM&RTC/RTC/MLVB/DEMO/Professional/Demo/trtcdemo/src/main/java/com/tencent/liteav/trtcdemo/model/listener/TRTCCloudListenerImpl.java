package com.tencent.liteav.trtcdemo.model.listener;

import android.os.Bundle;
import android.util.Log;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.TRTCStatistics;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * SDK 回调实现类，这里对TRTCCloudListener做了一个封装，让activity通过实现TRTCCloudListener直接使用相关的接口
 * 并且使用弱引用，可以放置activity被意外回收带来的内存泄漏问题
 *
 * @date : 2021/5/25
 * @author : xander
 */
public class TRTCCloudListenerImpl extends TRTCCloudListener {
    private static final String TAG = TRTCCloudListenerImpl.class.getName();

    private WeakReference<TRTCCloudManagerListener> mWefListener;

    public TRTCCloudListenerImpl(TRTCCloudManagerListener listener) {
        super();
        mWefListener = new WeakReference<>(listener);
    }

    @Override
    public void onEnterRoom(long elapsed) {
        Log.i(TAG, "onEnterRoom: elapsed = " + elapsed);
        final TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onEnterRoom(elapsed);
        }
    }

    @Override
    public void onExitRoom(int reason) {
        Log.i(TAG, "onExitRoom: reason = " + reason);
        final TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onExitRoom(reason);
        }
    }

    @Override
    public void onError(int errCode, String errMsg, Bundle extraInfo) {
        Log.i(TAG, "onError: errCode = " + errCode + " errMsg = " + errMsg);
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onError(errCode, errMsg, extraInfo);
        }
    }

    @Override
    public void onWarning(int warningCode, String warningMsg, Bundle extraInfo) {
        TXCLog.i(TAG, "onWarning: %d, warningMsg: %s", warningCode, warningMsg);
    }

    @Override
    public void onRemoteUserEnterRoom(String userId) {
        Log.i(TAG, "onRemoteUserEnterRoom: userId = " + userId);
    }

    @Override
    public void onRemoteUserLeaveRoom(String userId, int reason) {
        Log.i(TAG, "onRemoteUserLeaveRoom: userId = " + userId + " reason = " + reason);
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onRemoteUserLeaveRoom(userId, reason);
        }
    }

    @Override
    public void onUserVideoAvailable(final String userId, boolean available) {
        Log.i(TAG, "onUserVideoAvailable: userId = " + userId + " available = " + available);
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onUserVideoAvailable(userId, available);
        }
    }

    @Override
    public void onUserSubStreamAvailable(final String userId, boolean available) {
        Log.i(TAG, "onUserSubStreamAvailable: userId = " + userId + " available = " + available);
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onUserSubStreamAvailable(userId, available);
        }
    }

    @Override
    public void onUserAudioAvailable(String userId, boolean available) {
        Log.i(TAG, "onUserAudioAvailable: userId = " + userId + " available = " + available);
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onUserAudioAvailable(userId, available);
        }
    }

    @Override
    public void onCameraDidReady() {
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onCameraDidReady();
        }
    }

    @Override
    public void onFirstVideoFrame(String userId, int streamType, int width, int height) {
        Log.i(TAG, "onFirstVideoFrame: userId = " + userId + " streamType = " + streamType + " width = " + width + " height = " + height);
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onFirstVideoFrame(userId, streamType, width, height);
        }
    }

    @Override
    public void onUserVoiceVolume(ArrayList<TRTCCloudDef.TRTCVolumeInfo> userVolumes, int totalVolume) {
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onUserVoiceVolume(userVolumes, totalVolume);
        }
    }

    @Override
    public void onStatistics(TRTCStatistics statics) {

    }

    @Override
    public void onConnectOtherRoom(final String userID, final int err, final String errMsg) {
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onConnectOtherRoom(userID, err, errMsg);
        }
    }

    @Override
    public void onDisConnectOtherRoom(final int err, final String errMsg) {
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onDisConnectOtherRoom(err, errMsg);
        }
    }

    @Override
    public void onNetworkQuality(TRTCCloudDef.TRTCQuality localQuality, ArrayList<TRTCCloudDef.TRTCQuality> remoteQuality) {
        TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onNetworkQuality(localQuality, remoteQuality);
        }
    }

    @Override
    public void onAudioEffectFinished(int effectId, int code) {
        final TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onAudioEffectFinished(effectId, code);
        }
    }

    @Override
    public void onRecvCustomCmdMsg(String userId, int cmdID, int seq, byte[] message) {
        final TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onRecvCustomCmdMsg(userId, cmdID, seq, message);
        }
    }

    @Override
    public void onRecvSEIMsg(String userId, byte[] data) {
        final TRTCCloudManagerListener listener = mWefListener.get();
        if (listener != null) {
            listener.onRecvSEIMsg(userId, data);
        }
    }

    @Override
    public void onScreenCaptureStarted() {
        TXCLog.i(TAG, "onScreenCaptureStarted");
    }

    @Override
    public void onScreenCapturePaused() {
        TXCLog.i(TAG, "onScreenCapturePaused");
    }

    @Override
    public void onScreenCaptureResumed() {
        TXCLog.i(TAG, "onScreenCaptureResumed");
    }

    @Override
    public void onScreenCaptureStopped(int reason) {
        TXCLog.i(TAG, "onScreenCaptureStopped");
    }

    @Override
    public void onLocalRecordBegin(int errCode, String storagePath) {
        Log.d(TAG, "onLocalRecordBegin: " + errCode + " " + storagePath);
    }

    @Override
    public void onLocalRecordComplete(int errCode, String storagePath) {
        Log.d(TAG, "onLocalRecordComplete: " + errCode + " " + storagePath);
    }

    @Override
    public void onLocalRecording(long duration, String storagePath) {
        Log.d(TAG, "onLocalRecording: " + storagePath + " " + duration);
    }
}
