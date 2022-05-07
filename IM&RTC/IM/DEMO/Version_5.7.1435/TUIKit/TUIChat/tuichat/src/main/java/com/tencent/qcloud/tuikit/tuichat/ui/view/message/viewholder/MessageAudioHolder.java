package com.tencent.qcloud.tuikit.tuichat.ui.view.message.viewholder;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.qcloud.tuicore.TUIConfig;
import com.tencent.qcloud.tuicore.util.ScreenUtil;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.SoundElemBean;
import com.tencent.qcloud.tuikit.tuichat.component.AudioPlayer;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatLog;

import java.io.File;

public class MessageAudioHolder extends MessageContentHolder {

    private static final int AUDIO_MIN_WIDTH = ScreenUtil.getPxByDp(60);
    private static final int AUDIO_MAX_WIDTH = ScreenUtil.getPxByDp(250);

    private static final int UNREAD = 0;
    private static final int READ = 1;

    private TextView audioTimeText;
    private ImageView audioPlayImage;
    private LinearLayout audioContentView;

    public MessageAudioHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_adapter_content_audio;
    }

    @Override
    public void initVariableViews() {
        audioTimeText = rootView.findViewById(R.id.audio_time_tv);
        audioPlayImage = rootView.findViewById(R.id.audio_play_iv);
        audioContentView = rootView.findViewById(R.id.audio_content_ll);
    }

    @Override
    public void layoutVariableViews(final MessageInfo msg, final int position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        if (msg.isSelf()) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.rightMargin = 24;
            audioPlayImage.setImageResource(R.drawable.voice_msg_playing_3);
            audioPlayImage.setRotation(180f);
            audioContentView.removeView(audioPlayImage);
            audioContentView.addView(audioPlayImage);
            unreadAudioText.setVisibility(View.GONE);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.leftMargin = 24;
            // TODO 图标不对
            audioPlayImage.setImageResource(R.drawable.voice_msg_playing_3);
            audioContentView.removeView(audioPlayImage);
            audioContentView.addView(audioPlayImage, 0);
            if (msg.getCustomInt() == UNREAD) {
                LinearLayout.LayoutParams unreadParams = (LinearLayout.LayoutParams) isReadText.getLayoutParams();
                unreadParams.gravity = Gravity.CENTER_VERTICAL;
                unreadParams.leftMargin = 10;
                unreadAudioText.setVisibility(View.VISIBLE);
                unreadAudioText.setLayoutParams(unreadParams);
            } else {
                unreadAudioText.setVisibility(View.GONE);
            }
        }
        audioContentView.setLayoutParams(params);

        SoundElemBean soundElemBean = SoundElemBean.createSoundElemBean(msg);
        if (soundElemBean == null) {
            return;
        }
        int duration = (int) soundElemBean.getDuration();
        if (duration == 0) {
            duration = 1;
        }
        if (TextUtils.isEmpty(msg.getDataPath())) {
            getSound(msg, soundElemBean);
        }
        ViewGroup.LayoutParams audioParams = msgContentFrame.getLayoutParams();
        audioParams.width = AUDIO_MIN_WIDTH + ScreenUtil.getPxByDp(duration * 6);
        if (audioParams.width > AUDIO_MAX_WIDTH) {
            audioParams.width = AUDIO_MAX_WIDTH;
        }
        msgContentFrame.setLayoutParams(audioParams);
        audioTimeText.setText(duration + "''");
        msgContentFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AudioPlayer.getInstance().isPlaying()) {
                    AudioPlayer.getInstance().stopPlay();
                    return;
                }
                if (TextUtils.isEmpty(msg.getDataPath())) {
                    ToastUtil.toastLongMessage(TUIChatService.getAppContext().getString(R.string.voice_play_tip));
                    return;
                }
                audioPlayImage.setImageResource(R.drawable.play_voice_message);
                if (msg.isSelf()) {
                    audioPlayImage.setRotation(180f);
                }
                final AnimationDrawable animationDrawable = (AnimationDrawable) audioPlayImage.getDrawable();
                animationDrawable.start();
                msg.setCustomInt(READ);
                unreadAudioText.setVisibility(View.GONE);
                AudioPlayer.getInstance().startPlay(msg.getDataPath(), new AudioPlayer.Callback() {
                    @Override
                    public void onCompletion(Boolean success) {
                        audioPlayImage.post(new Runnable() {
                            @Override
                            public void run() {
                                animationDrawable.stop();
                                audioPlayImage.setImageResource(R.drawable.voice_msg_playing_3);
                                if (msg.isSelf()) {
                                    audioPlayImage.setRotation(180f);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void getSound(final MessageInfo msgInfo, SoundElemBean soundElemBean) {
        final String path = TUIConfig.getRecordDownloadDir() + soundElemBean.getUUID();
        File file = new File(path);
        if (!file.exists()) {
            soundElemBean.downloadSound(path, new SoundElemBean.SoundDownloadCallback() {
                @Override
                public void onProgress(long currentSize, long totalSize) {
                    TUIChatLog.i("downloadSound progress current:", currentSize + ", total:" + totalSize);
                }

                @Override
                public void onError(int code, String desc) {
                    TUIChatLog.e("getSoundToFile failed code = ", code + ", info = " + desc);
                }

                @Override
                public void onSuccess() {
                    msgInfo.setDataPath(path);
                }
            });
        } else {
            msgInfo.setDataPath(path);
        }
    }

}
