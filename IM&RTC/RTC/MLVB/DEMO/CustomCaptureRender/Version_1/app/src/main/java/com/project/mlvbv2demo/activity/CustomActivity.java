package com.project.mlvbv2demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.project.mlvbv2demo.R;
import com.project.mlvbv2demo.utils.CameraEglSurfaceView;
import com.project.mlvbv2demo.utils.CameraFboRender;
import com.project.mlvbv2demo.utils.TextureFrame;
import com.tencent.live2.V2TXLiveCode;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.V2TXLivePlayerObserver;
import com.tencent.live2.V2TXLivePusherObserver;
import com.tencent.live2.impl.V2TXLivePlayerImpl;
import com.tencent.live2.impl.V2TXLivePusherImpl;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tencent.live2.V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatTexture2D;
import static com.tencent.live2.V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1920x1080;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient mOkHttpClient;
    private String rtmpPushUrl;
    private V2TXLivePusherImpl v2TXLivePusher;
    private ImageView iv_bg;
    private ImageView iv_push;
    private ImageView camera_switch;
    private TXCloudVideoView tv_live_play_view;
    private CameraEglSurfaceView cameraEglSurfaceView;
    private V2TXLivePlayerImpl v2TXLivePlayer;
    private String rtmpPlayUrl;
    private int isPushing = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        setContentView(R.layout.activity_custom);
        iv_bg = findViewById(R.id.iv_bg);
        iv_push = findViewById(R.id.iv_push);
        camera_switch = findViewById(R.id.camera_switch);
        iv_push.setOnClickListener(this);
        camera_switch.setOnClickListener(this);
        intoURL();
        initPusher();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_push) {
            if (v2TXLivePusher.isPushing() == 0) {
                startPush();
            } else {
                stopPush();
            }

        } else if (id == R.id.camera_switch) {
//            if (cameraEglSurfaceView != null&&isPushing==1) {
//                cameraEglSurfaceView.switchCamera();
//            }
        }
    }

    /**
     * 初始化 SDK 推流器
     */
    private void initPusher() {
        v2TXLivePusher = new V2TXLivePusherImpl(this, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTMP);
//         设置默认美颜参数， 美颜样式为光滑，美颜等级 5，美白等级 3，红润等级 2
        v2TXLivePusher.getBeautyManager().setBeautyStyle(TXLiveConstants.BEAUTY_STYLE_SMOOTH);
        v2TXLivePusher.getBeautyManager().setBeautyLevel(5);
        v2TXLivePusher.getBeautyManager().setWhitenessLevel(3);
        v2TXLivePusher.getBeautyManager().setRuddyLevel(2);
    }

    private void stopPush() {
        Toast.makeText(this, "stopPush", Toast.LENGTH_SHORT).show();
        if (v2TXLivePusher != null) {
            iv_bg.setVisibility(View.VISIBLE);
            v2TXLivePusher.stopMicrophone();
            // 移除监听
            v2TXLivePusher.setObserver(null);
            // 停止推流
            v2TXLivePusher.stopPush();
            iv_push.setImageResource(R.mipmap.livepusher_start);
            isPushing = v2TXLivePusher.isPushing();
        }
        if (cameraEglSurfaceView != null) {
            cameraEglSurfaceView.onDestroy();
            cameraEglSurfaceView.setVideoFrameReadListener(null);
        }
        stopPlay();
    }

    private void startPush() {
        Toast.makeText(this, "startPush", Toast.LENGTH_SHORT).show();
        V2TXLiveDef.V2TXLiveVideoEncoderParam v2TXLiveVideoEncoderParam = new V2TXLiveDef.V2TXLiveVideoEncoderParam(V2TXLiveVideoResolution1920x1080);
        v2TXLiveVideoEncoderParam.videoFps = 20;
        v2TXLiveVideoEncoderParam.videoBitrate = 2500;
        v2TXLiveVideoEncoderParam.minVideoBitrate = 1000;
        v2TXLivePusher.setVideoQuality(v2TXLiveVideoEncoderParam);
//        v2TXLivePusher.setVideoQuality(V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1280x720, V2TXLiveDef.V2TXLiveVideoResolutionMode.V2TXLiveVideoResolutionModePortrait);
        v2TXLivePusher.enableCustomVideoCapture(true);
        v2TXLivePusher.startMicrophone();
        int i = v2TXLivePusher.startPush(rtmpPushUrl);
        if (i == V2TXLiveCode.V2TXLIVE_OK) {
            iv_bg.setVisibility(View.GONE);
            iv_push.setImageResource(R.mipmap.livepusher_pause);
            isPushing = v2TXLivePusher.isPushing();
            startPlay();
        } else if (i == V2TXLiveCode.V2TXLIVE_ERROR_INVALID_LICENSE) {
            Toast.makeText(this, "license 校验失败  ：" + i, Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "推流失败 ：" + i, Toast.LENGTH_SHORT).show();
            return;
        }
        v2TXLivePusher.setObserver(new V2TXLivePusherObserver() {
            @Override
            public void onError(int i, String s, Bundle bundle) {
                super.onError(i, s, bundle);
                Log.e("lin", "-----------------  onError i=" + i + "   s=" + s);
            }

            @Override
            public void onWarning(int i, String s, Bundle bundle) {
                super.onWarning(i, s, bundle);
                Log.e("lin", "-----------------  onWarning i=" + i + "   s=" + s);
            }

            @Override
            public void onCaptureFirstAudioFrame() {
                super.onCaptureFirstAudioFrame();
                Log.e("lin", "-----------------  onCaptureFirstAudioFrame ");
            }

            @Override
            public void onCaptureFirstVideoFrame() {
                super.onCaptureFirstVideoFrame();
                Log.e("lin", "-----------------  onCaptureFirstVideoFrame ");
            }

            @Override
            public void onMicrophoneVolumeUpdate(int i) {
                super.onMicrophoneVolumeUpdate(i);
                Log.e("lin", "-----------------  onMicrophoneVolumeUpdate i=" + i);
            }

            @Override
            public void onPushStatusUpdate(V2TXLiveDef.V2TXLivePushStatus v2TXLivePushStatus, String s, Bundle bundle) {
                super.onPushStatusUpdate(v2TXLivePushStatus, s, bundle);
                Log.e("lin", "-----------------onPushStatusUpdate      v2TXLivePushStatus=" + v2TXLivePushStatus + "   s=" + s + "   bundle=" + bundle.toString());
            }

            @Override
            public void onStatisticsUpdate(V2TXLiveDef.V2TXLivePusherStatistics v2TXLivePusherStatistics) {
                super.onStatisticsUpdate(v2TXLivePusherStatistics);
                Log.e("lin", "------- onStatisticsUpdate   appCpu=" + v2TXLivePusherStatistics.appCpu + "   systemCpu=" + v2TXLivePusherStatistics.systemCpu + "   width=" + v2TXLivePusherStatistics.width
                        + "   height=" + v2TXLivePusherStatistics.height + "   fps=" + v2TXLivePusherStatistics.fps + "   videoBitrate=" + v2TXLivePusherStatistics.videoBitrate + "   audioBitrate=" + v2TXLivePusherStatistics.audioBitrate + "");
            }

            @Override
            public void onSnapshotComplete(Bitmap bitmap) {
                super.onSnapshotComplete(bitmap);
                Log.e("lin", "-----------------  onSnapshotComplete ");
            }

            @Override
            public void onSetMixTranscodingConfig(int i, String s) {
                super.onSetMixTranscodingConfig(i, s);

                Log.e("lin", "-----------------  onSetMixTranscodingConfig i=" + i + "   s=" + s);
            }

            @Override
            public void onGLContextCreated() {
                super.onGLContextCreated();
                Log.e("lin", "-----------------  onSnapshotComplete ");
            }

            @Override
            public int onProcessVideoFrame(V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame, V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame1) {

                Log.e("lin", "-----------------  onProcessVideoFrame ");
                return super.onProcessVideoFrame(v2TXLiveVideoFrame, v2TXLiveVideoFrame1);
            }

            @Override
            public void onGLContextDestroyed() {
                super.onGLContextDestroyed();
                Log.e("lin", "-----------------  onGLContextDestroyed ");
            }
        });

        if (cameraEglSurfaceView == null) {
            cameraEglSurfaceView = findViewById(R.id.gl_surface_view);
        }
        cameraEglSurfaceView.start();
//        视频纹理采集监听
        cameraEglSurfaceView.setVideoFrameReadListener(new CameraFboRender.VideoFrameReadListener() {
            @Override
            public void onFrameAvailable(TextureFrame frame) {
                V2TXLiveDef.V2TXLiveTexture v2TXLiveTexture = new V2TXLiveDef.V2TXLiveTexture();
                v2TXLiveTexture.textureId = frame.textureId;
                V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame = new V2TXLiveDef.V2TXLiveVideoFrame();
                v2TXLiveVideoFrame.texture = v2TXLiveTexture;
                v2TXLiveVideoFrame.width = frame.width;
                v2TXLiveVideoFrame.height = frame.height;
                v2TXLiveVideoFrame.pixelFormat = V2TXLivePixelFormatTexture2D;
                v2TXLiveVideoFrame.bufferType = V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture;
                int i1 = v2TXLivePusher.sendCustomVideoFrame(v2TXLiveVideoFrame);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPush();
        stopPlay();
        if (cameraEglSurfaceView != null) {
            cameraEglSurfaceView.surfaceDestroyed(cameraEglSurfaceView.getHolder());
        }

    }

    private void intoURL() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        String reqUrl = "https://lvb.qcloud.com/weapp/utils/get_test_pushurl";
        Request request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
        Log.d("lin", "start fetch push url");
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("lin", "------- realtimePlayUrl=" + e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CustomActivity.this, "获取推流地址失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonRsp;
                    try {
                        jsonRsp = new JSONObject(response.body().string());
                        rtmpPushUrl = jsonRsp.optString("url_push");            // RTMP 推流地址
                        // RTMP 播放地址
                        rtmpPlayUrl = jsonRsp.optString("url_play_rtmp");
                        // FLA  播放地址
                        String flvPlayUrl = jsonRsp.optString("url_play_flv");
                        // HLS  播放地址
                        String hlsPlayUrl = jsonRsp.optString("url_play_hls");
                        // RTMP 加速流地址
                        String realtimePlayUrl = jsonRsp.optString("url_play_acc");
//                        bitmap = createQRCodeBitmap(rtmpPlayUrl, 800, 800, "UTF-8", "H", "1", Color.BLACK, Color.WHITE);
                        Log.e("lin", "------- rtmpPushUrl=" + rtmpPushUrl);
                        Log.e("lin", "------- rtmpPlayUrl=" + rtmpPlayUrl);
                        Log.e("lin", "------- flvPlayUrl=" + flvPlayUrl);
                        Log.e("lin", "------- hlsPlayUrl=" + hlsPlayUrl);
                        Log.e("lin", "------- realtimePlayUrl=" + realtimePlayUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void stopPlay() {
        if (v2TXLivePlayer != null) {
            v2TXLivePlayer.stopPlay();
            v2TXLivePlayer.setPlayoutVolume(0);
            v2TXLivePlayer.setObserver(null);
        }
    }

    public void startPlay() {
        if (TextUtils.isEmpty(rtmpPlayUrl)) {
            Toast.makeText(this, "拉流地址为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v2TXLivePlayer == null) {
            tv_live_play_view = findViewById(R.id.tv_live_play_view);
            v2TXLivePlayer = new V2TXLivePlayerImpl(this);
            v2TXLivePlayer.setRenderView(tv_live_play_view);
            v2TXLivePlayer.setRenderRotation(V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation0);
            v2TXLivePlayer.setRenderFillMode(V2TXLiveDef.V2TXLiveFillMode.V2TXLiveFillModeFill);
        }
        v2TXLivePlayer.setPlayoutVolume(100);
//        开启回调开关_onRenderVideoFrame: 自定义视频渲染回调
        v2TXLivePlayer.enableObserveVideoFrame(true, V2TXLivePixelFormatTexture2D, V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture);
        v2TXLivePlayer.setObserver(new V2TXLivePlayerObserver() {
            @Override
            public void onError(V2TXLivePlayer v2TXLivePlayer, int i, String msg, Bundle bundle) {
                super.onError(v2TXLivePlayer, i, msg, bundle);
                Log.e("lin", "------- onError   i=" + i);
                Log.e("lin", "------- onError   msg=" + msg);
            }

            @Override
            public void onWarning(V2TXLivePlayer v2TXLivePlayer, int i, String s, Bundle bundle) {
                super.onWarning(v2TXLivePlayer, i, s, bundle);
                Log.e("lin", "------- onWarning   i=" + i + "    s=" + s + "   bundle=" + bundle.toString());
            }

//            @Override
//            public void onVideoPlayStatusUpdate(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason, Bundle bundle) {
//                super.onVideoPlayStatusUpdate(v2TXLivePlayer, v2TXLivePlayStatus, v2TXLiveStatusChangeReason, bundle);
//
//                Log.e("lin", "------- onVideoPlayStatusUpdate   bundle=" + bundle.toString());
//            }
//
//            @Override
//            public void onAudioPlayStatusUpdate(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason, Bundle bundle) {
//                super.onAudioPlayStatusUpdate(v2TXLivePlayer, v2TXLivePlayStatus, v2TXLiveStatusChangeReason, bundle);
//                Log.e("lin", "------- onAudioPlayStatusUpdate   i=");
//            }

            @Override
            public void onPlayoutVolumeUpdate(V2TXLivePlayer v2TXLivePlayer, int i) {
                super.onPlayoutVolumeUpdate(v2TXLivePlayer, i);
                Log.e("lin", "------- onPlayoutVolumeUpdate   i=" + i);
            }

            @Override
            public void onStatisticsUpdate(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLivePlayerStatistics v2TXLivePlayerStatistics) {
                super.onStatisticsUpdate(v2TXLivePlayer, v2TXLivePlayerStatistics);
                Log.e("lin", "------- onStatisticsUpdate   appCpu=" + v2TXLivePlayerStatistics.appCpu + "   systemCpu=" + v2TXLivePlayerStatistics.systemCpu + "   width=" + v2TXLivePlayerStatistics.width
                        + "   height=" + v2TXLivePlayerStatistics.height + "   fps=" + v2TXLivePlayerStatistics.fps + "   videoBitrate=" + v2TXLivePlayerStatistics.videoBitrate + "   audioBitrate=" + v2TXLivePlayerStatistics.audioBitrate + "");
            }

            @Override
            public void onSnapshotComplete(V2TXLivePlayer v2TXLivePlayer, Bitmap bitmap) {
                super.onSnapshotComplete(v2TXLivePlayer, bitmap);
                Log.e("lin", "------- onSnapshotComplete   i=");
            }

            @Override
            public void onRenderVideoFrame(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame) {
                super.onRenderVideoFrame(v2TXLivePlayer, v2TXLiveVideoFrame);
                Log.e("lin", "------- onRenderVideoFrame   i=");
            }
        });
        int i = v2TXLivePlayer.startPlay(rtmpPlayUrl);
    }

}