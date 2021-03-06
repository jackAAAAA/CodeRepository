package com.tencent.qcloud.ugckit;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;


import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.qcloud.ugckit.basic.ITitleBarLayout;
import com.tencent.qcloud.ugckit.basic.JumpActivityMgr;
import com.tencent.qcloud.ugckit.basic.OnUpdateUIListener;
import com.tencent.qcloud.ugckit.basic.UGCKitResult;
import com.tencent.qcloud.ugckit.module.PlayerManagerKit;
import com.tencent.qcloud.ugckit.module.VideoGenerateKit;
import com.tencent.qcloud.ugckit.module.editer.AbsVideoEditUI;
import com.tencent.qcloud.ugckit.module.editer.UGCKitEditConfig;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.qcloud.ugckit.utils.TelephonyUtil;
import com.tencent.qcloud.ugckit.component.dialog.ActionSheetDialog;
import com.tencent.qcloud.ugckit.component.dialogfragment.ProgressFragmentUtil;
import com.tencent.qcloud.ugckit.module.effect.Config;
import com.tencent.qcloud.ugckit.module.effect.VideoEditerSDK;
import com.tencent.rtmp.TXLog;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;

public class UGCKitVideoEdit extends AbsVideoEditUI {
    private static final String TAG = "UGCKitVideoEdit";

    private ProgressFragmentUtil mProgressFragmentUtil;
    @Nullable
    private OnEditListener       mOnEditListener;
    private boolean              mIsPublish;

    public UGCKitVideoEdit(Context context) {
        super(context);
        initDefault();
    }

    public UGCKitVideoEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault();
    }

    public UGCKitVideoEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault();
    }

    private void initDefault() {
        mProgressFragmentUtil = new ProgressFragmentUtil((FragmentActivity) getContext());

        // ??????"??????"
        getTitleBar().setTitle(getResources().getString(R.string.ugckit_complete), ITitleBarLayout.POSITION.RIGHT);
        getTitleBar().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPressed();
            }
        });
        getTitleBar().setOnRightClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPublishDialog();
            }
        });
        // ????????????
        TelephonyUtil.getInstance().setOnTelephoneListener(new TelephonyUtil.OnTelephoneListener() {
            @Override
            public void onRinging() {
                // ???????????? ????????????
                stopGenerate();
                // ??????????????????
                PlayerManagerKit.getInstance().stopPlay();
            }

            @Override
            public void onOffhook() {
                stopGenerate();
                // ??????????????????
                PlayerManagerKit.getInstance().stopPlay();
            }

            @Override
            public void onIdle() {
                // ??????????????????
                PlayerManagerKit.getInstance().restartPlay();
            }
        });
        TelephonyUtil.getInstance().initPhoneListener();

        // ??????????????????????????????????????????
        JumpActivityMgr.getInstance().setQuickImport(false);
    }

    @Override
    public void backPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = builder.setTitle(getContext().getString(R.string.ugckit_tips)).setCancelable(false).setMessage(R.string.ugckit_confirm_cancel_edit_content)
                .setPositiveButton(R.string.ugckit_btn_back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();
                        TXCLog.i(TAG,"[UGCKit][VideoEdit]backPressed call stopPlay");
                        PlayerManagerKit.getInstance().stopPlay();
                        // ?????????????????????
                        VideoEditerSDK.getInstance().releaseSDK();
                        VideoEditerSDK.getInstance().clear();
                        if (mOnEditListener != null) {
                            mOnEditListener.onEditCanceled();
                        }
                    }
                })
                .setNegativeButton(getContext().getString(R.string.ugckit_wrong_click), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * ?????????????????? {@code setVideoPath}
     * ?????????????????????????????????
     *
     * @param videoPath ????????????????????????
     */
    @Override
    public void setVideoPath(String videoPath) {
        // ??????TXVideoEditer?????????"????????????"?????????????????????TXVideoEditer???"???????????????"?????????????????????????????????????????????????????????TXVideoEditer
        TXVideoEditer editer = VideoEditerSDK.getInstance().getEditer();
        if (editer == null) {
            VideoEditerSDK.getInstance().initSDK();
        }
        TXCLog.i(TAG, "[UGCKit][VideoEdit][QuickImport]setVideoPath:" + videoPath);
        VideoEditerSDK.getInstance().setVideoPath(videoPath);

        // ??????TXVideoInfo?????????"????????????"?????????videoPath?????????????????????????????????;"???????????????"?????????????????????????????????????????????
        TXVideoEditConstants.TXVideoInfo info = VideoEditerSDK.getInstance().getTXVideoInfo();
        if (info == null) {
            // ???"??????"????????????????????????"????????????"???info???????????????????????????????????????null
            // ???"??????"????????????????????????"????????????"???info???null?????????????????????
            info = TXVideoInfoReader.getInstance(UGCKit.getAppContext()).getVideoFileInfo(videoPath);
            VideoEditerSDK.getInstance().setTXVideoInfo(info);
        }

        // ??????????????????????????????????????????1????????????(????????????????????????)
        VideoEditerSDK.getInstance().clearThumbnails();

        long startTime = VideoEditerSDK.getInstance().getCutterStartTime();
        long endTime = VideoEditerSDK.getInstance().getCutterEndTime();
        if (endTime > startTime) {
            TXCLog.i(TAG, "[UGCKit][VideoEdit][QuickImport]load thumbnail start time:" + startTime + ",end time:" + endTime);
        }

        VideoEditerSDK.getInstance().setCutterStartTime(0, info.duration);
        VideoEditerSDK.getInstance().setVideoDuration(info.duration);
        VideoEditerSDK.getInstance().initThumbnailList(new TXVideoEditer.TXThumbnailListener() {
            @Override
            public void onThumbnail(final int index, long timeMs, final Bitmap bitmap) {
                TXLog.d(TAG, "onThumbnail index:" + index + ",timeMs:" + timeMs);
                VideoEditerSDK.getInstance().addThumbnailBitmap(timeMs, bitmap);
            }
        }, 1000);

        JumpActivityMgr.getInstance().setQuickImport(true);
    }

    /**
     * ?????????????????????
     */
    private void showPublishDialog() {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(getContext());
        actionSheetDialog.builder();
        actionSheetDialog.setCancelable(false);
        actionSheetDialog.setCancelable(false);
        actionSheetDialog.addSheetItem(getResources().getString(R.string.ugckit_video_editer_activity_show_publish_dialog_save),
                ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        VideoEditerSDK.getInstance().setPublishFlag(false);
                        startGenerate();
                    }
                });

        if (mIsPublish) {
            actionSheetDialog.addSheetItem(getResources().getString(R.string.ugckit_video_editer_activity_show_publish_dialog_publish),
                    ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            VideoEditerSDK.getInstance().setPublishFlag(true);
                            startGenerate();
                        }

                    });
        }
        actionSheetDialog.show();
    }

    @Override
    public void initPlayer() {
        getVideoPlayLayout().initPlayerLayout();

        VideoEditerSDK.getInstance().resetDuration();
    }

    @Override
    public void setConfig(UGCKitEditConfig config) {
        VideoGenerateKit.getInstance().setCustomVideoBitrate(config.videoBitrate);
        VideoGenerateKit.getInstance().setVideoResolution(config.resolution);
        VideoGenerateKit.getInstance().setCoverGenerate(config.isCoverGenerate);
        VideoGenerateKit.getInstance().saveVideoToDCIM(config.isSaveToDCIM);
        VideoGenerateKit.getInstance().setWaterMark(config.mWaterMarkConfig);
        VideoGenerateKit.getInstance().setTailWaterMark(config.mTailWaterMarkConfig);
        mIsPublish = config.isPublish;
    }

    @Override
    public void start() {
        KeyguardManager manager = (KeyguardManager) UGCKit.getAppContext().getSystemService(Context.KEYGUARD_SERVICE);
        if (!manager.inKeyguardRestrictedInputMode()) {
            PlayerManagerKit.getInstance().restartPlay();
        }
    }

    @Override
    public void stop() {
        TXCLog.i(TAG,"[UGCKit][VideoEdit]onStop call stopPlay");
        PlayerManagerKit.getInstance().stopPlay();

        stopGenerate();
    }

    @Override
    public void release() {
        Config.getInstance().clearConfig();
        TelephonyUtil.getInstance().uninitPhoneListener();
    }

    @Override
    public void setOnVideoEditListener(@Nullable final OnEditListener listener) {
        if (listener == null) {
            mOnEditListener = null;
            VideoGenerateKit.getInstance().setOnUpdateUIListener(null);
            return;
        }
        mOnEditListener = listener;

        VideoGenerateKit.getInstance().setOnUpdateUIListener(new OnUpdateUIListener() {
            @Override
            public void onUIProgress(float progress) {
                mProgressFragmentUtil.updateGenerateProgress((int) (progress * 100));
            }

            @Override
            public void onUIComplete(int retCode, String descMsg) {
                mProgressFragmentUtil.dismissLoadingProgress();

                LogReport.getInstance().reportVideoEdit(retCode);

                final UGCKitResult ugcKitResult = new UGCKitResult();
                ugcKitResult.outputPath = VideoGenerateKit.getInstance().getVideoOutputPath();
                ugcKitResult.coverPath = VideoGenerateKit.getInstance().getCoverPath();
                ugcKitResult.errorCode = retCode;
                ugcKitResult.descMsg = descMsg;
                ugcKitResult.isPublish = VideoEditerSDK.getInstance().isPublish();
                if (listener != null) {
                    listener.onEditCompleted(ugcKitResult);
                }
            }

            @Override
            public void onUICancel() {
                // ???????????????????????????UI??????????????????
            }
        });
    }

    private void startGenerate() {
        mProgressFragmentUtil.showLoadingProgress(new ProgressFragmentUtil.IProgressListener() {
            @Override
            public void onStop() {
                PlayerManagerKit.getInstance().restartPlay();

                stopGenerate();
            }
        });
        PlayerManagerKit.getInstance().stopPlay();

        VideoGenerateKit.getInstance().addTailWaterMark();
        VideoGenerateKit.getInstance().startGenerate();
    }

    private void stopGenerate() {
        VideoGenerateKit.getInstance().stopGenerate();
        mProgressFragmentUtil.dismissLoadingProgress();
    }

}
