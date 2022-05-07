package com.tencent.liteav.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.liteav.demo.common.widget.expandableadapter.BaseExpandableRecyclerViewAdapter;
import com.tencent.liteav.demo.lebplayer.ui.LebPlayerLauncherActivity;
import com.tencent.liteav.demo.livelinkmicnew.V2MainActivity;
import com.tencent.liteav.demo.liveplayer.ui.LivePlayerEntranceActivity;
import com.tencent.liteav.demo.livepusher.camerapush.ui.CameraPushEntranceActivity;
import com.tencent.liteav.demo.livepusher.screenpush.ScreenPushEntranceActivity;
import com.tencent.liteav.demo.liveroom.ui.LiveRoomActivity;
import com.tencent.liteav.demo.player.demo.SuperPlayerActivity;
import com.tencent.liteav.demo.videoediter.TCVideoPickerActivity;
import com.tencent.liteav.demo.videojoiner.ui.TCVideoJoinChooseActivity;
import com.tencent.liteav.demo.videorecord.TCVideoSettingActivity;
import com.tencent.liteav.login.model.ProfileManager;
import com.tencent.liteav.login.ui.LoginActivity;
import com.tencent.liteav.trtcdemo.ui.TRTCCallEnterActivity;
import com.tencent.liteav.trtcdemo.ui.TRTCLiveEnterActivity;
import com.tencent.liteav.trtcdemo.ui.TRTCSpeedTestActivity;
import com.tencent.rtmp.TXLiveBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MainActivity extends Activity {

    private static final String     TAG = MainActivity.class.getName();
    private static final String     TRTC_APP_PACKAGE_NAME = "com.tencent.trtc";
    private static final String     TRTC_APP_MAIN_CLASS_NAME = "com.tencent.liteav.demo.SplashActivity";

    private TextView                mMainTitle, mTvVersion;
    private RecyclerView            mRvList;
    private MainExpandableAdapter   mAdapter;
    private ImageView               mLogoutImg;
    private AlertDialog             mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            Log.d(TAG, "brought to front");
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        mTvVersion = (TextView) findViewById(R.id.main_tv_version);
        mTvVersion.setText(getString(R.string.app_tv_video_cloud_tools_version, TXLiveBase.getSDKVersionStr()+"(8.9.1015)"));

        mMainTitle = (TextView) findViewById(R.id.main_title);
        mMainTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        File logFile = getLogFile();
                        if (logFile != null) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("application/octet-stream");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(logFile));
                            startActivity(Intent.createChooser(intent, getString(R.string.app_title_share_log)));
                        }
                    }
                });
                return false;
            }
        });
        mLogoutImg = (ImageView) findViewById(R.id.img_logout);
        mLogoutImg.setVisibility(View.VISIBLE);
        mLogoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        mRvList = (RecyclerView) findViewById(R.id.main_recycler_view);
        List<GroupBean> groupBeans = initGroupData();
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainExpandableAdapter(groupBeans);
        mAdapter.setListener(new BaseExpandableRecyclerViewAdapter.ExpandableRecyclerViewOnClickListener<GroupBean, ChildBean>() {
            @Override
            public boolean onGroupLongClicked(GroupBean groupItem) {
                return false;
            }

            @Override
            public boolean onInterceptGroupExpandEvent(GroupBean groupItem, boolean isExpand) {
                return false;
            }

            @Override
            public void onGroupClicked(GroupBean groupItem) {
                mAdapter.setSelectedChildBean(groupItem);
            }

            @Override
            public void onChildClicked(GroupBean groupItem, ChildBean childItem) {
                if (childItem.mIconId == R.drawable.xiaoshipin) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://dldir1.qq.com/hudongzhibo/liteav/XiaoShiPin.apk"));
                    startActivity(intent);
                    return;
                } else if (childItem.mIconId == R.drawable.xiaozhibo) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://dldir1.qq.com/hudongzhibo/liteav/xiaozhibo.apk"));
                    startActivity(intent);
                    return;
                } else if (childItem.mIconId == R.drawable.trtcdemo_fill_adjust){
                    if(checkAPP()){
                        jumpTRTCAPP();
                    }else{
                        jumpDownloadPage();
                    }
                    return;
                }
                Intent intent = new Intent(MainActivity.this, childItem.getTargetClass());
                intent.putExtra("TITLE", childItem.mName);
                intent.putExtra("TYPE", childItem.mType);
                MainActivity.this.startActivity(intent);
            }
        });
        mRvList.setAdapter(mAdapter);
    }

    private void showLogoutDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this, R.style.common_alert_dialog)
                    .setMessage(getString(R.string.app_dialog_log_out))
                    .setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 执行退出登录操作
                            ProfileManager.getInstance().logout(new ProfileManager.ActionCallback() {
                                @Override
                                public void onSuccess() {
                                    // 退出登录
                                    startLoginActivity();
                                }

                                @Override
                                public void onFailed(int code, String msg) {
                                }
                            });
                        }
                    })
                    .setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        //退出登录
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.common_alert_dialog)
                .setMessage(getString(R.string.app_dialog_exit_app))
                .setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private List<GroupBean> initGroupData() {
        List<GroupBean> groupList = new ArrayList<>();


        // 直播
        List<ChildBean> pusherChildList = new ArrayList<>();
        pusherChildList.add(new ChildBean(getString(R.string.app_item_live_pusher), R.drawable.push, 0, CameraPushEntranceActivity.class));
        pusherChildList.add(new ChildBean(getString(R.string.app_item_live_pusher_screen), R.drawable.push, 0, ScreenPushEntranceActivity.class));
        pusherChildList.add(new ChildBean(getString(R.string.app_item_live_player), R.drawable.live, 0, LivePlayerEntranceActivity.class));
        pusherChildList.add(new ChildBean(getString(R.string.app_item_leb_player), R.drawable.live, 0, LebPlayerLauncherActivity.class));
        pusherChildList.add(new ChildBean(getString(R.string.app_item_link_mic_new), R.drawable.room_live, 0, V2MainActivity.class));
        pusherChildList.add(new ChildBean(getString(R.string.app_item_link_mic_old), R.drawable.room_live, 0, LiveRoomActivity.class));
        if (pusherChildList.size() != 0) {
            // 这个是网页链接，配合build.sh避免在如ugc_smart版中出现
            pusherChildList.add(new ChildBean(getString(R.string.app_item_xiao_zhi_bo), R.drawable.xiaozhibo, 0, null));
            GroupBean pusherGroupBean = new GroupBean(getString(R.string.app_item_mlvb), R.drawable.room_live, pusherChildList);
            groupList.add(pusherGroupBean);
        }

        // 初始化播放器
        List<ChildBean> playerChildList = new ArrayList<>();
        playerChildList.add(new ChildBean(getString(R.string.app_item_super_player), R.drawable.play, 3, SuperPlayerActivity.class));
        if (playerChildList.size() != 0) {
            GroupBean playerGroupBean = new GroupBean(getString(R.string.app_item_player), R.drawable.composite, playerChildList);
            groupList.add(playerGroupBean);
        }

        // 短视频
        List<ChildBean> shortVideoChildList = new ArrayList<>();
        shortVideoChildList.add(new ChildBean(getString(R.string.app_item_video_recording), R.drawable.video, 0, TCVideoSettingActivity.class));
        shortVideoChildList.add(new ChildBean(getString(R.string.app_item_effects_editor), R.drawable.cut, 0, TCVideoPickerActivity.class));
        shortVideoChildList.add(new ChildBean(getString(R.string.app_item_video_stitching), R.drawable.composite, TCVideoJoinChooseActivity.TYPE_MULTI_CHOOSE, TCVideoJoinChooseActivity.class));
        shortVideoChildList.add(new ChildBean(getString(R.string.app_item_picture_transition), R.drawable.short_video_picture, TCVideoJoinChooseActivity.TYPE_MULTI_CHOOSE_PICTURE, TCVideoJoinChooseActivity.class));
        shortVideoChildList.add(new ChildBean(getString(R.string.app_item_video_upload), R.drawable.update, TCVideoJoinChooseActivity.TYPE_PUBLISH_CHOOSE, TCVideoJoinChooseActivity.class));

        if (shortVideoChildList.size() != 0) {
            // 这个是网页链接，配合build.sh避免在其他版本中出现
            shortVideoChildList.add(new ChildBean(getString(R.string.app_item_xiao_shi_pin), R.drawable.xiaoshipin, 0, null));
            GroupBean shortVideoGroupBean = new GroupBean(getString(R.string.app_item_ugsv), R.drawable.video, shortVideoChildList);
            groupList.add(shortVideoGroupBean);
        }

        // TRTC
        List<ChildBean> videoConnectChildList = new ArrayList<>();
        videoConnectChildList.add(new ChildBean(getString(R.string.item_trtc_speed_test), R.drawable.room_multi, 1, TRTCSpeedTestActivity.class));
        videoConnectChildList.add(new ChildBean(getString(R.string.item_trtc_live), R.drawable.room_multi, 1, TRTCLiveEnterActivity.class));
        videoConnectChildList.add(new ChildBean(getString(R.string.item_trtc_call), R.drawable.room_multi, 1, TRTCCallEnterActivity.class));
        videoConnectChildList.add(new ChildBean(getString(R.string.item_trtc_app), R.drawable.trtcdemo_fill_adjust, 0, null));

        if (videoConnectChildList.size() != 0) {
            GroupBean videoConnectGroupBean = new GroupBean(getString(R.string.app_item_trtc), R.drawable.room_multi, videoConnectChildList);
            groupList.add(videoConnectGroupBean);
        }

        return groupList;
    }


    private static class MainExpandableAdapter extends BaseExpandableRecyclerViewAdapter<GroupBean, ChildBean, GroupVH, ChildVH> {
        private List<GroupBean> mListGroupBean;
        private GroupBean       mGroupBean;

        public void setSelectedChildBean(GroupBean groupBean) {
            boolean isExpand = isExpand(groupBean);
            if (mGroupBean != null) {
                GroupVH lastVH = getGroupViewHolder(mGroupBean);
                if (!isExpand)
                    mGroupBean = groupBean;
                else
                    mGroupBean = null;
                notifyItemChanged(lastVH.getAdapterPosition());
            } else {
                if (!isExpand)
                    mGroupBean = groupBean;
                else
                    mGroupBean = null;
            }
            if (mGroupBean != null) {
                GroupVH currentVH = getGroupViewHolder(mGroupBean);
                notifyItemChanged(currentVH.getAdapterPosition());
            }
        }

        public MainExpandableAdapter(List<GroupBean> list) {
            mListGroupBean = list;
        }

        @Override
        public int getGroupCount() {
            return mListGroupBean.size();
        }

        @Override
        public GroupBean getGroupItem(int groupIndex) {
            return mListGroupBean.get(groupIndex);
        }

        @Override
        public GroupVH onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
            return new GroupVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_entry_item, parent, false));
        }

        @Override
        public void onBindGroupViewHolder(GroupVH holder, GroupBean groupBean, boolean isExpand) {
            holder.textView.setText(groupBean.mName);
            holder.ivLogo.setImageResource(groupBean.mIconId);
            if (mGroupBean == groupBean) {
                holder.itemView.setBackgroundResource(R.color.main_item_selected_color);
            } else {
                holder.itemView.setBackgroundResource(R.color.main_item_unselected_color);
            }
        }

        @Override
        public ChildVH onCreateChildViewHolder(ViewGroup parent, int childViewType) {
            return new ChildVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_entry_child_item, parent, false));
        }

        @Override
        public void onBindChildViewHolder(ChildVH holder, GroupBean groupBean, ChildBean childBean) {
            holder.textView.setText(childBean.getName());

            if (groupBean.mChildList.indexOf(childBean) == groupBean.mChildList.size() - 1) {//说明是最后一个
                holder.divideView.setVisibility(View.GONE);
            } else {
                holder.divideView.setVisibility(View.VISIBLE);
            }

        }
    }


    public static class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView ivLogo;
        TextView  textView;

        GroupVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_tv);
            ivLogo = (ImageView) itemView.findViewById(R.id.icon_iv);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
        }

    }

    public static class ChildVH extends RecyclerView.ViewHolder {
        TextView textView;
        View     divideView;

        ChildVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_tv);
            divideView = itemView.findViewById(R.id.item_view_divide);
        }

    }

    private class GroupBean implements BaseExpandableRecyclerViewAdapter.BaseGroupBean<ChildBean> {
        private String          mName;
        private List<ChildBean> mChildList;
        private int             mIconId;

        public GroupBean(String name, int iconId, List<ChildBean> list) {
            mName = name;
            mChildList = list;
            mIconId = iconId;
        }

        @Override
        public int getChildCount() {
            return mChildList.size();
        }

        @Override
        public ChildBean getChildAt(int index) {
            return mChildList.size() <= index ? null : mChildList.get(index);
        }

        @Override
        public boolean isExpandable() {
            return getChildCount() > 0;
        }

        public String getName() {
            return mName;
        }

        public List<ChildBean> getChildList() {
            return mChildList;
        }

        public int getIconId() {
            return mIconId;
        }
    }

    private class ChildBean {
        public String mName;
        public int    mIconId;
        public Class  mTargetClass;
        public int    mType;

        public ChildBean(String name, int iconId, int type, Class targetActivityClass) {
            this.mName = name;
            this.mIconId = iconId;
            this.mTargetClass = targetActivityClass;
            this.mType = type;
        }

        public String getName() {
            return mName;
        }


        public int getIconId() {
            return mIconId;
        }


        public Class getTargetClass() {
            return mTargetClass;
        }
    }


    private File getLogFile() {
        File sdcardDir = getExternalFilesDir(null);
        if (sdcardDir == null) {
            return null;
        }

        String       path      = sdcardDir.getAbsolutePath() + "/log/liteav";
        List<String> logs      = new ArrayList<>();
        File         directory = new File(path);
        if (directory != null && directory.exists() && directory.isDirectory()) {
            long lastModify = 0;
            File files[]    = directory.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.getName().endsWith("xlog")) {
                        logs.add(file.getAbsolutePath());
                    }
                }
            }
        }


        String zipPath = path + "/liteavLog.zip";
        return zip(logs, zipPath);
    }

    private File zip(List<String> files, String zipFileName) {
        File zipFile = new File(zipFileName);
        zipFile.deleteOnExit();
        InputStream     is  = null;
        ZipOutputStream zos = null;

        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            zos.setComment("LiteAV log");
            for (String path : files) {
                File file = new File(path);
                try {
                    if (file.length() == 0 || file.length() > 8 * 1024 * 1024) continue;

                    is = new FileInputStream(file);
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    byte[] buffer = new byte[8 * 1024];
                    int    length = 0;
                    while ((length = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.w(TAG, "zip log error");
            zipFile = null;
        } finally {
            try {
                zos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return zipFile;
    }


    private void jumpDownloadPage() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://dldir1.qq.com/hudongzhibo/liteav/TRTCDemo.apk"));
        startActivity(intent);
    }

    private void jumpTRTCAPP() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName componentName = new ComponentName(TRTC_APP_PACKAGE_NAME, TRTC_APP_MAIN_CLASS_NAME);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    private boolean checkAPP() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if(packageInfo != null && !TextUtils.isEmpty(packageInfo.packageName)){
                if(TRTC_APP_PACKAGE_NAME.equals(packageInfo.packageName)){
                    return true;
                }
            }
        }
        return false;
    }

}