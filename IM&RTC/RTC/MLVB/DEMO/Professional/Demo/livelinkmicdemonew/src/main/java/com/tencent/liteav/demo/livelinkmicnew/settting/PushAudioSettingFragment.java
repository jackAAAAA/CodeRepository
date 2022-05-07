package com.tencent.liteav.demo.livelinkmicnew.settting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.tencent.liteav.demo.livelinkmicnew.R;
import com.tencent.liteav.demo.livelinkmicnew.settting.customitem.BaseSettingItem;
import com.tencent.liteav.demo.livelinkmicnew.settting.customitem.CheckBoxSettingItem;
import com.tencent.liteav.demo.livelinkmicnew.settting.customitem.RadioButtonSettingItem;
import com.tencent.liteav.demo.livelinkmicnew.settting.customitem.SeekBarSettingItem;
import com.tencent.liteav.device.TXDeviceManager;
import com.tencent.live2.V2TXLivePusher;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地音频相关设置
 */
public class PushAudioSettingFragment extends Fragment {
    private LinearLayout           mContentItem;
    private List<BaseSettingItem>  mSettingItemList;
    private RadioButtonSettingItem mAudioVolumeTypeItem;
    private CheckBoxSettingItem    mAudioEarMonitoringItem;
    private CheckBoxSettingItem    mAudioVolumeEvaluationItem;
    private CheckBoxSettingItem    mAudioMuteItem;
    private AVSettingConfig.AudioConfig mAudioConfig;
    private V2TXLivePusher         mLivePusher;

    public void setLivePusher(V2TXLivePusher pusher) {
        mLivePusher = pusher;
    }

    @Override
    public void onViewCreated(View itemView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(itemView, savedInstanceState);
        mContentItem = (LinearLayout) itemView.findViewById(R.id.item_content);
        mSettingItemList = new ArrayList<>();
        mAudioConfig = AVSettingConfig.getInstance().getAudioConfig();
        BaseSettingItem.ItemText itemText =
                new BaseSettingItem.ItemText(getString(R.string.livelinkmicnew_tv_volume_type),
                        getString(R.string.livelinkmicnew_tv_volume_type_auto),
                        getString(R.string.livelinkmicnew_tv_volume_type_media),
                        getString(R.string.livelinkmicnew_tv_volume_type_voip));
        mAudioVolumeTypeItem = new RadioButtonSettingItem(getContext(), itemText,
                new RadioButtonSettingItem.SelectedListener() {
                    @Override
                    public void onSelected(int index) {
                        int vIndex = mAudioVolumeTypeItem.getSelected();
                        TXDeviceManager.TXSystemVolumeType volumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeAuto;
                        if (0 == vIndex) {
                            volumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeAuto;
                        } else if (1 == vIndex) {
                            volumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeMedia;
                        } else if (2 == vIndex) {
                            volumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeVOIP;
                        }
                        mAudioConfig.setAudioVolumeType(vIndex);
                        if (mLivePusher != null) {
                            mLivePusher.getDeviceManager().setSystemVolumeType(volumeType);
                        }
                    }
                });
        mSettingItemList.add(mAudioVolumeTypeItem);

        itemText = new BaseSettingItem.ItemText(getString(R.string.livelinkmicnew_tv_voice_capture_volume), "");
        mSettingItemList.add(new SeekBarSettingItem(getContext(), itemText, new SeekBarSettingItem.Listener() {
            @Override
            public void onSeekBarChange(int progress, boolean fromUser) {
                mAudioConfig.setRecordVolume(progress);
                if (mLivePusher != null) {
                    mLivePusher.getAudioEffectManager().setVoiceCaptureVolume(progress);
                }
            }
        }).setProgress(mAudioConfig.getRecordVolume()));

        //耳返设置入口
        itemText = new BaseSettingItem.ItemText(getString(R.string.livelinkmicnew_tv_enable_voice_ear_monitor), "");
        mAudioEarMonitoringItem = new CheckBoxSettingItem(getContext(), itemText,
                new CheckBoxSettingItem.ClickListener() {
                    @Override
                    public void onClick() {
                        mAudioConfig.setEnableEarMonitoring(mAudioEarMonitoringItem.getChecked());
                        if (mLivePusher != null) {
                            mLivePusher.getAudioEffectManager().enableVoiceEarMonitor(mAudioEarMonitoringItem.getChecked());
                        }
                    }
                });
        mSettingItemList.add(mAudioEarMonitoringItem);

        itemText =
                new BaseSettingItem.ItemText(getString(R.string.livelinkmicnew_tv_volume_tips), "");
        mAudioVolumeEvaluationItem = new CheckBoxSettingItem(getContext(), itemText,
                new CheckBoxSettingItem.ClickListener() {
                    @Override
                    public void onClick() {
                        mAudioConfig.setAudioVolumeEvaluation(mAudioVolumeEvaluationItem.getChecked());
                        if (mLivePusher != null) {
                            mLivePusher.enableVolumeEvaluation(mAudioConfig.isAudioVolumeEvaluation() ? 300 : 0);
                        }
                    }
                });
        mSettingItemList.add(mAudioVolumeEvaluationItem);

        itemText =
                new BaseSettingItem.ItemText(getString(R.string.livelinkmicnew_tv_mute_audio), "");
        mAudioMuteItem = new CheckBoxSettingItem(getContext(), itemText,
                new CheckBoxSettingItem.ClickListener() {
                    @Override
                    public void onClick() {
                        mAudioConfig.setEnableAudio(mAudioMuteItem.getChecked());
                        if (mLivePusher != null) {
                            boolean isChecked = mAudioMuteItem.getChecked();
                            if (isChecked) {
                                mLivePusher.resumeAudio();
                            } else {
                                mLivePusher.pauseAudio();
                            }
                        }
                    }
                });
        mSettingItemList.add(mAudioMuteItem);

        updateItem();

        // 将这些view添加到对应的容器中
        for (BaseSettingItem item : mSettingItemList) {
            View view = item.getView();
            view.setPadding(0, SizeUtils.dp2px(8), 0, 0);
            mContentItem.addView(view);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mAudioConfig.saveCache();
    }

    private void updateItem() {
        mAudioVolumeTypeItem.setSelect(mAudioConfig.getAudioVolumeType());
        mAudioEarMonitoringItem.setCheck(mAudioConfig.isEnableEarMonitoring());
        mAudioVolumeEvaluationItem.setCheck(mAudioConfig.isAudioVolumeEvaluation());
        mAudioMuteItem.setCheck(mAudioConfig.isEnableAudio());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.live_link_mic_new_setting_common, container, false);
    }
}
