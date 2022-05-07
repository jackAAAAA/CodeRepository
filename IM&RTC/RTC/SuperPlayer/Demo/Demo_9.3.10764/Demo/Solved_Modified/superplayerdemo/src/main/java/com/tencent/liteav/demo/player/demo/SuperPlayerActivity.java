package com.tencent.liteav.demo.player.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.demo.common.Utils.IntentUtils;
import com.tencent.liteav.demo.player.expand.model.entity.VideoListModel;
import com.tencent.liteav.demo.superplayer.SuperPlayerDef;
import com.tencent.liteav.demo.superplayer.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.superplayer.SuperPlayerModel;
import com.tencent.liteav.demo.superplayer.SuperPlayerVideoId;
import com.tencent.liteav.demo.superplayer.SuperPlayerView;
import com.tencent.liteav.demo.player.R;
import com.tencent.liteav.demo.player.expand.model.SuperPlayerConstants;
import com.tencent.liteav.demo.player.expand.model.GetVideoInfoListListener;
import com.tencent.liteav.demo.player.expand.model.VideoDataMgr;
import com.tencent.liteav.demo.player.expand.model.entity.VideoInfo;
import com.tencent.liteav.demo.player.expand.model.utils.SuperVodListLoader;
import com.tencent.liteav.demo.player.expand.ui.TCVodPlayerListAdapter;
import com.tencent.liteav.demo.player.expand.model.entity.VideoModel;
import com.tencent.liteav.demo.superplayer.model.VipWatchModel;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by liyuejiao on 2018/7/3.
 * 超级播放器主Activity：视图 & 功能管理
 */

//public class SuperPlayerActivity extends Activity implements View.OnClickListener,
//        SuperVodListLoader.OnVodInfoLoadListener, SuperPlayerView.OnSuperPlayerViewCallback,
//        TCVodPlayerListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
//
//    private static final String TAG                   = "SuperPlayerActivity";
//    private static final String SHARE_PREFERENCE_NAME = "tx_super_player_guide_setting";
//    private static final String KEY_GUIDE_ONE         = "is_guide_one_finish";
//    private static final String KEY_GUIDE_TWO         = "is_guide_two_finish";
//    private static final String DEFAULT_IMAGHOLDER    = "http://xiaozhibo-10055601.file.myqcloud.com/coverImg.jpg";
//    private static final int    LIST_TYPE_LIVE        = 0;
//    private static final int    LIST_TYPE_VOD         = 1;
//    private static final int    REQUEST_CODE_QR_SCAN  = 100;
//
//    private Context                  mContext;
//    private RelativeLayout           mLayoutTitle;
//    private RelativeLayout           mRelativeMaskOne;
//    private RelativeLayout           mRelativeMaskTwo;
//    private ImageView                mImageBack;
//    private ImageView                mBtnScan;
//    private ImageView                mImageAdd;
//    private ImageButton              mImageLink;
//    private View                     mTitleMask;
//    private View                     mListMask;
//    private TextView                 mTextOne;
//    private TextView                 mTextTwo;
//    private SwipeRefreshLayout       mSwipeRefreshLayout;
//    private SuperPlayerView          mSuperPlayerView;
//    private RecyclerView             mVodPlayerListView;
//    private int                      DEFAULT_APPID = 1252463788;
//    private int                      mDataType     = LIST_TYPE_LIVE;
//    private int                      mVideoCount;
//    private SuperVodListLoader       mSuperVodListLoader;
//    private TCVodPlayerListAdapter   mVodPlayerListAdapter;
//    private GetVideoInfoListListener mGetVideoInfoListListener;
//    private ArrayList<VideoModel>    mLiveList;
//    private ArrayList<VideoModel>    mVodList;
//    private ArrayList<ListTabItem>   mListTabs;
//    private boolean                  mVideoHasPlay;
//    private boolean                  mDefaultVideo;
//    private String                   mVideoId;
//
//    private static class ListTabItem {
//        public ListTabItem(int type, TextView textView, ImageView imageView, View.OnClickListener listener) {
//            this.type = type;
//            this.textView = textView;
//            this.imageView = imageView;
//            this.textView.setOnClickListener(listener);
//        }
//
//        public int       type;
//        public TextView  textView;
//        public ImageView imageView;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.superplayer_activity_supervod_player);
//
//        mContext = this;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        initView();
//        initData();
//
//        mDataType = mDefaultVideo ? LIST_TYPE_LIVE : LIST_TYPE_VOD;
//        updateList(mDataType);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        String from = intent.getStringExtra("from");
//        if (!TextUtils.isEmpty(from)) {
//            playExternalVideo();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
//    }
//
//    @Override
//    public void finish() {
//        if (getActivityCount() == 1) {
//            startMainActivity();
//        }
//        super.finish();
//    }
//
//    private int getActivityCount() {
//        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
//        return taskInfoList.size();
//    }
//
//    private void startMainActivity() {
//        Intent intent = new Intent();
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setAction("com.tencent.liteav.action.liteavapp");
//        startActivity(intent);
//    }
//
//    private void initView() {
//        mLayoutTitle = (RelativeLayout) findViewById(R.id.superplayer_rl_title);
//        mImageBack = (ImageView) findViewById(R.id.superplayer_iv_back);
//        mImageBack.setOnClickListener(this);
//        mBtnScan = (ImageView) findViewById(R.id.superplayer_btn_scan);
//        mBtnScan.setOnClickListener(this);
//        mImageLink = (ImageButton) findViewById(R.id.superplayer_ib_webrtc_link_button);
//        mImageLink.setOnClickListener(this);
//
//        mSuperPlayerView = (SuperPlayerView) findViewById(R.id.superVodPlayerView);
//        mSuperPlayerView.setPlayerViewCallback(this);
//
//        mVodPlayerListView = (RecyclerView) findViewById(R.id.superplayer_recycler_view);
//        mVodPlayerListView.setLayoutManager(new LinearLayoutManager(this));
//        mVodPlayerListAdapter = new TCVodPlayerListAdapter(this);
//        mVodPlayerListAdapter.setOnItemClickListener(this);
//        mVodPlayerListView.setAdapter(mVodPlayerListAdapter);
//
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.superplayer_swipe_refresh_layout_list);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mImageAdd = (ImageView) findViewById(R.id.superplayer_iv_add);
//        mImageAdd.setOnClickListener(this);
//
//        mListTabs = new ArrayList<>();
//        mListTabs.add(LIST_TYPE_LIVE, new ListTabItem(LIST_TYPE_LIVE, (TextView) findViewById(R.id.superplayer_tv_live), null, this));
//        mListTabs.add(LIST_TYPE_VOD, new ListTabItem(LIST_TYPE_VOD, (TextView) findViewById(R.id.superplayer_tv_vod), null, this));
//
//        initNewGuideLayout();
//        initMaskLayout();
//    }
//
//    /**
//     * 初始化新手引导布局
//     */
//    private void initNewGuideLayout() {
//        mRelativeMaskOne = (RelativeLayout) findViewById(R.id.superplayer_small_rl_mask_one);
//        mRelativeMaskOne.setOnTouchListener(new View.OnTouchListener() { // 拦截事件
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        mRelativeMaskTwo = (RelativeLayout) findViewById(R.id.superplayer_small_rl_mask_two);
//        mRelativeMaskTwo.setOnTouchListener(new View.OnTouchListener() { // 拦截事件
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        mTextOne = (TextView) findViewById(R.id.superplayer_small_tv_btn1);
//        mTextTwo = (TextView) findViewById(R.id.superplayer_small_tv_btn2);
//
//        boolean isFinishOne = getBoolean(KEY_GUIDE_ONE);
//        boolean isFinishTwo = getBoolean(KEY_GUIDE_TWO);
//
//        if (isFinishOne) {
//            mRelativeMaskOne.setVisibility(GONE);
//            if (isFinishTwo) {
//                //ignore
//            } else {
//                mRelativeMaskTwo.setVisibility(VISIBLE);
//            }
//        } else {
//            mRelativeMaskOne.setVisibility(VISIBLE);
//            mRelativeMaskTwo.setVisibility(GONE);
//        }
//
//        mTextOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRelativeMaskOne.setVisibility(GONE);
//                mRelativeMaskTwo.setVisibility(VISIBLE);
//                putBoolean(KEY_GUIDE_ONE, true);
//                putBoolean(KEY_GUIDE_TWO, false);
//            }
//        });
//        mTextTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRelativeMaskOne.setVisibility(GONE);
//                mRelativeMaskTwo.setVisibility(GONE);
//                mTitleMask.setVisibility(GONE);
//                mListMask.setVisibility(GONE);
//                putBoolean(KEY_GUIDE_ONE, true);
//                putBoolean(KEY_GUIDE_TWO, true);
//            }
//        });
//
//    }
//
//    private void initMaskLayout() {
//        mTitleMask = findViewById(R.id.superplayer_view_title_mask);
//        mTitleMask.setOnClickListener(new View.OnClickListener() {// 拦截所有事件
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        mListMask = findViewById(R.id.superplayer_view_list_mask);
//        mListMask.setOnClickListener(new View.OnClickListener() { // 拦截所有事件
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        boolean isFinishOne = getBoolean(KEY_GUIDE_ONE);
//        boolean isFinishTwo = getBoolean(KEY_GUIDE_TWO);
//        if (!isFinishOne || !isFinishTwo) {
//            mTitleMask.setVisibility(VISIBLE);
//            mListMask.setVisibility(VISIBLE);
//        } else {
//            mTitleMask.setVisibility(GONE);
//            mListMask.setVisibility(GONE);
//        }
//    }
//
//
//    private void initData() {
//        mLiveList = new ArrayList<>();
//        mVodList = new ArrayList<>();
//        mDefaultVideo = getIntent().getBooleanExtra(SuperPlayerConstants.PLAYER_DEFAULT_VIDEO, true);
//        mSuperVodListLoader = new SuperVodListLoader();
//        mSuperVodListLoader.setOnVodInfoLoadListener(this);
//
//        initSuperVodGlobalSetting();
//
//        mVideoHasPlay = false;
//
//        mVideoCount = 0;
//
//        TXLiveBase.setAppID("1253131631");
//    }
//
//    private void updateLiveList() {
//        mLiveList.clear();
//        mSuperVodListLoader.getLiveList(new SuperVodListLoader.OnListLoadListener() {
//            @Override
//            public void onSuccess(final ArrayList<VideoModel> superPlayerModelList) {
//
//                SuperPlayerActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mDataType != LIST_TYPE_LIVE) return;
//                        mVodPlayerListAdapter.clear();
//                        for (VideoModel videoModel : superPlayerModelList) {
//                            mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//                            mLiveList.add(videoModel);
//                        }
//                        if (!mVideoHasPlay && !mLiveList.isEmpty()) {
//                            if (mLiveList.get(0).appid > 0) {
//                                TXLiveBase.setAppID("" + mLiveList.get(0).appid);
//                            }
//                            String from = getIntent().getStringExtra("from");
//                            if (TextUtils.isEmpty(from)) {
//                                playVideoModel(mLiveList.get(0));
//                            } else {
//                                playExternalVideo();
//                            }
//                            mVideoHasPlay = true;
//                        }
//                        mVodPlayerListAdapter.notifyDataSetChanged();
//
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//
//            @Override
//            public void onFail(int errCode) {
//                TXCLog.e(TAG, "updateLiveList error");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        });
//        mVodPlayerListAdapter.notifyDataSetChanged();
//    }
//
//    private void updateVodList() {
//        if (mDefaultVideo) {
//            ArrayList<VideoModel> superPlayerModels = mSuperVodListLoader.loadDefaultVodList(this.getApplicationContext());
////            将默认的点播视频倒序展示，以让后面添加的视频item能够被看见
//            Collections.reverse(superPlayerModels);
//            mSuperVodListLoader.getVodInfoOneByOne(superPlayerModels);
//            mImageAdd.setVisibility(VISIBLE);
//        } else {
//            mVideoId = getIntent().getStringExtra(SuperPlayerConstants.PLAYER_VIDEO_ID);
//            if (!TextUtils.isEmpty(mVideoId)) {
//                playDefaultVideo(SuperPlayerConstants.VOD_APPID, mVideoId);
//                mVideoHasPlay = true;
//            }
//            mGetVideoInfoListListener = new GetVideoInfoListListener() {
//                @Override
//                public void onGetVideoInfoList(final List<VideoInfo> videoInfoList) {
//                    if (mDataType != LIST_TYPE_VOD) return;
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mVodPlayerListAdapter.clear();
//                            mVodPlayerListAdapter.notifyDataSetChanged();
//                            mSwipeRefreshLayout.setRefreshing(false);
//                            ArrayList<VideoModel> videoModels = VideoDataMgr.getInstance().loadVideoInfoList(videoInfoList);
//                            if (videoModels != null && videoModels.size() != 0) {
//                                mSuperVodListLoader.getVodInfoOneByOne(videoModels);
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void onFail(int errCode) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(mContext, getString(R.string.superplayer_fetch_upload_list_video_fail), Toast.LENGTH_SHORT).show();
//                            mSwipeRefreshLayout.setRefreshing(false);
//                        }
//                    });
//                }
//            };
//            VideoDataMgr.getInstance().setGetVideoInfoListListener(mGetVideoInfoListListener);
//            VideoDataMgr.getInstance().getVideoList();
//
//            mBtnScan.setVisibility(GONE);
//            mImageAdd.setVisibility(GONE);
//        }
//    }
//
//    private void playDefaultVideo(int appid, String fileid) {
//        VideoModel videoModel = new VideoModel();
//        videoModel.appid = appid;
//        videoModel.fileid = fileid;
//        videoModel.title = getString(R.string.superplayer_small_video_special_effects_editing);
//        if (videoModel.appid > 0) {
//            TXLiveBase.setAppID("" + videoModel.appid);
//        }
//        playVideoModel(videoModel);
//    }
//
//    /**
//     * 初始化超级播放器全局配置
//     */
//    private void initSuperVodGlobalSetting() {
//        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
//        // 开启悬浮窗播放
//        prefs.enableFloatWindow = true;
//        // 设置悬浮窗的初始位置和宽高
//        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
//        rect.x = 0;
//        rect.y = 0;
//        rect.width = 810;
//        rect.height = 540;
//        prefs.floatViewRect = rect;
//        // 播放器默认缓存个数
//        prefs.maxCacheItem = 5;
//        // 设置播放器渲染模式
//        prefs.enableHWAcceleration = true;
//        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
//        //需要修改为自己的时移域名
//        prefs.playShiftDomain = "liteavapp.timeshift.qcloud.com";
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mSuperPlayerView.getPlayerState() == SuperPlayerDef.PlayerState.PLAYING
//                || mSuperPlayerView.getPlayerState() == SuperPlayerDef.PlayerState.PAUSE) {
//            Log.i(TAG, "onResume state :" + mSuperPlayerView.getPlayerState());
//            if(!mSuperPlayerView.isShowingVipView()) {
//                mSuperPlayerView.onResume();
//            }
//            if (mSuperPlayerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FLOAT) {
//                mSuperPlayerView.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
//            }
//        }
//        if (mSuperPlayerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FULLSCREEN) {
//            //隐藏虚拟按键，并且全屏
//            View decorView = getWindow().getDecorView();
//            if (decorView == null) return;
//            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//                decorView.setSystemUiVisibility(View.GONE);
//            } else if (Build.VERSION.SDK_INT >= 19) {
//                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//                decorView.setSystemUiVisibility(uiOptions);
//            }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause state :" + mSuperPlayerView.getPlayerState());
//        if (mSuperPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
//            mSuperPlayerView.onPause();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSuperPlayerView.release();
//        if (mSuperPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
//            mSuperPlayerView.resetPlayer();
//        }
//        VideoDataMgr.getInstance().setGetVideoInfoListListener(null);
//    }
//
//    /**
//     * 获取点播信息成功
//     */
//    @Override
//    public void onSuccess(final VideoModel videoModel) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mDataType != LIST_TYPE_VOD) return;
//                mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//                mVodList.add(videoModel);
//            }
//        });
//    }
//
//    /**
//     * 获取点播信息失败
//     *
//     * @param errCode
//     */
//    @Override
//    public void onFail(int errCode) {
//        TXCLog.i(TAG, "onFail errCode:" + errCode);
//    }
//
//    /**
//     * 点击直播/点播列表中的某个视频item事件
//     * @param position
//     * @param videoModel
//     */
//    @Override
//    public void onItemClick(int position, final VideoModel videoModel) {
//        if (videoModel.appid > 0) {
//            TXLiveBase.setAppID("" + videoModel.appid);
//        }
//        playVideoModel(videoModel);
//    }
//
//    private void playVideoModel(VideoModel videoModel) {
//        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();
//        superPlayerModelV3.appId = videoModel.appid;
//        superPlayerModelV3.vipWatchMode=videoModel.vipWatchModel;
//        if (!TextUtils.isEmpty(videoModel.videoURL)) {
//            if (isSuperPlayerVideo(videoModel)) {
//                playSuperPlayerVideo(videoModel);
//                return;
//            } else {
//                superPlayerModelV3.title = videoModel.title;
//                superPlayerModelV3.url = videoModel.videoURL;
//
//                superPlayerModelV3.multiURLs = new ArrayList<>();
//                if (videoModel.multiVideoURLs != null) {
//                    for (VideoModel.VideoPlayerURL modelURL : videoModel.multiVideoURLs) {
//                        superPlayerModelV3.multiURLs.add(new SuperPlayerModel.SuperPlayerURL(modelURL.url, modelURL.title));
//                    }
//                }
//            }
//        } else if (!TextUtils.isEmpty(videoModel.fileid)) {
//            superPlayerModelV3.videoId = new SuperPlayerVideoId();
//            superPlayerModelV3.videoId.fileId = videoModel.fileid;
//            superPlayerModelV3.videoId.pSign = videoModel.pSign;
//        }
//        mSuperPlayerView.playWithModel(superPlayerModelV3);
//    }
//
//    private boolean playSuperPlayerVideo(VideoModel videoModel) {
//        final SuperPlayerModel model = new SuperPlayerModel();
//        String videoUrl = videoModel.videoURL;
//        String appIdStr = getValueByName(videoUrl, "appId");
//        boolean rst = true;
//        try {
//            model.appId = appIdStr.equals("") ? 0 : Integer.valueOf(appIdStr);
//            SuperPlayerVideoId videoId = new SuperPlayerVideoId();
//            videoId.fileId = getValueByName(videoUrl, "fileId");
//            videoId.pSign = getValueByName(videoUrl, "psign");
//            model.videoId = videoId;
//            mSuperPlayerView.playWithModel(model);
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), R.string.superplayer_scancode_tip, Toast.LENGTH_SHORT).show();
//            rst = false;
//        }
//        return rst;
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.superplayer_iv_add) {            // 点播视频列表：点击+ —— 添加一个点播列表项
//            showAddVideoDialog();
//        } else if (id == R.id.superplayer_btn_scan) {   // 扫描二维码播放一个视频
//            scanQRCode();
//        } else if (id == R.id.superplayer_iv_back) {    // 悬浮窗播放
//            showFloatWindow();
//        } else if (id == R.id.superplayer_tv_live) {    // 点击“直播列表”
//            mDataType = LIST_TYPE_LIVE;
//            updateList(mDataType);
//        } else if (id == R.id.superplayer_tv_vod) {     // 点击“点播列表”
//            mDataType = LIST_TYPE_VOD;
//            updateList(mDataType);
//        } else if (id == R.id.superplayer_ib_webrtc_link_button) {   // 点击右上角的“问号”
//            showCloudLink();
//        }
//    }
//
//    private void showCloudLink() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("https://cloud.tencent.com/document/product/454/18872"));
//        startActivity(intent);
//    }
//
//    private void updateList(int dataType) {
//        for (ListTabItem item : mListTabs) {
//            if (item.type == dataType) {
//                item.textView.setTextColor(Color.rgb(255, 255, 255));
//            } else {
//                item.textView.setTextColor(Color.rgb(119, 119, 119));
//            }
//        }
//
//        mVodPlayerListAdapter.clear();
//        switch (mDataType) {
//            case LIST_TYPE_LIVE:
//                if (mLiveList.isEmpty()) {
//                    updateLiveList();
//                } else {
//                    for (VideoModel videoModel : mLiveList) {
//                        mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//                    }
//                }
//                break;
//            case LIST_TYPE_VOD:
//                if (isNeedUpdateVodList()) {
//                    if (mVodList != null && !mVodList.isEmpty()) {
//                        for (VideoModel videoModel : mVodList) {
//                            mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//                        }
//                    }
//                    updateVodList();
//                } else {
//                    for (VideoModel videoModel : mVodList) {
//                        mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//                    }
//                }
//                break;
//        }
//
//        mVodPlayerListAdapter.notifyDataSetChanged();
//    }
//
//    private boolean isNeedUpdateVodList() {
//        if (mVodList == null || mVodList.isEmpty()) {
//            return true;
//        }
//        for (VideoModel videoModel : mVodList) {
//            if (DEFAULT_IMAGHOLDER != videoModel.placeholderImage) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 悬浮窗播放
//     */
//    private void showFloatWindow() {
//        if (mSuperPlayerView.getPlayerState() == SuperPlayerDef.PlayerState.PLAYING) {
//            mSuperPlayerView.switchPlayMode(SuperPlayerDef.PlayerMode.FLOAT);
//        } else {
//            mSuperPlayerView.resetPlayer();
//            finish();
//        }
//    }
//
//    /**
//     * 扫描二维码
//     */
//    private void scanQRCode() {
//        Intent intent = new Intent(this, QRCodeScanActivity.class);
//        startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null || TextUtils.isEmpty(data.getStringExtra("result"))) {
//            return;
//        }
//        String result = data.getStringExtra("result");
//        if (REQUEST_CODE_QR_SCAN == requestCode) {
//            // 二维码播放视频
//            playExternalVideo(result);
//        }
//    }
//
//    private boolean isLivePlay(VideoModel videoModel) {
//        String videoURL = videoModel.videoURL;
//        if (TextUtils.isEmpty(videoModel.videoURL)) {
//            return false;
//        }
//        if (videoURL.startsWith("rtmp://")) {
//            return true;
//        } else if ((videoURL.startsWith("http://") || videoURL.startsWith("https://")) ) { //
//            // 此处修改了直播链接的判断规则：去掉 && videoURL.contains(".flv")
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isSuperPlayerVideo(VideoModel videoModel) {
//        return videoModel.videoURL.startsWith("txsuperplayer://play_vod");
//    }
//
//    private String getValueByName(String url, String name) { //txsuperplayer://play_vod?v=4&appId=1400295357&fileId=5285890796599775084&pcfg=Default
//        String result = "";
//        int index = url.indexOf("?");
//        String temp = url.substring(index + 1);
//        String[] keyValue = temp.split("&");
//        for (String str : keyValue) {
//            if (str.startsWith(name + "=")) {
//                result = str.replace(name + "=", "");
//                break;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 点击+：添加一个点播列表项——可以通过url/appid & fileid & psign来播放视频
//     */
//    private void showAddVideoDialog() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        final View dialogView = LayoutInflater.from(this).inflate(R.layout.superplayer_dialog_new_vod_player_fileid, null);
//
//        dialog.setView(dialogView);
//
//        final EditText etAppId = (EditText) dialogView.findViewById(R.id.superplayer_et_appid);
//        final EditText etFileId = (EditText) dialogView.findViewById(R.id.superplayer_et_fileid);
//        final EditText etPSign = (EditText) dialogView.findViewById(R.id.superplayer_et_psign);
//
//        if (mDataType == LIST_TYPE_VOD) {
//            dialog.setTitle(getString(R.string.superplayer_set_appid_fileid));
//        } else {
//            dialog.setTitle(getString(R.string.superplayer_set_play_url));
//            dialogView.findViewById(R.id.superplayer_tv_appid_text).setVisibility(GONE);
//            dialogView.findViewById(R.id.superplayer_tv_fileid_text).setVisibility(GONE);
//            dialogView.findViewById(R.id.superplayer_tv_psign_text).setVisibility(GONE);
//            etFileId.setVisibility(GONE);
//            etPSign.setVisibility(GONE);
//        }
//
//        dialog.setNegativeButton(getString(R.string.superplayer_cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setPositiveButton(getString(R.string.superplayer_ok),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        if (mDataType == LIST_TYPE_VOD) { //通过点播资源的信息来播放
//                            String appId = etAppId.getText().toString();
//                            String fileId = etFileId.getText().toString();
//                            String pSign = etPSign.getText().toString();
//
//                            if (TextUtils.isEmpty(appId)) {
//                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_appid), Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            if (TextUtils.isEmpty(fileId)) {
//                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_fileid), Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            int appid;
//                            try {
//                                appid = Integer.parseInt(appId);
//                            } catch (NumberFormatException e) {
//                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_appid), Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                            VideoModel videoModel = new VideoModel();
//                            videoModel.appid = appid;
//                            videoModel.fileid = fileId;
//                            videoModel.pSign = pSign;
//
////                            清空UI列表的操作要放到UI主线程中来运行，否则除了鸿蒙系统以外的其他系统可能报错
//                            // 尝试请求fileid信息
//                            SuperVodListLoader loader = new SuperVodListLoader();
//                            loader.setOnVodInfoLoadListener(new SuperVodListLoader.OnVodInfoLoadListener() {
//                                @Override
//                                public void onSuccess(VideoModel videoModel) {
////                                    在UI主线程中运行的其中两种写法——1.匿名内部类；2.Lambda
////                                    1.匿名内部类
////                                    runOnUiThread(new Runnable() {
////                                        @Override
////                                        public void run() {
////
////                                        }});
//
////                                    2.Lambda
////                                    runOnUiThread(() -> {});
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
////                                            实现点播视频的item添加后便立即播放
//                                            Log.i(TAG, "run: " + videoModel.toString());
//                                            playVideoModel(videoModel);
//
////                                            视频数据列表——mVodList；UI视频列表——mVodPlayerListAdapter
////                                            实现点播视频列表数据项item的添加
//                                            mVodList.add(0, videoModel);
////                                            直播/点播列表的UI展示刷新，需要重新渲染，因此第一步是clear()，即先清除原有列表中的所有元素，
////                                            否则只能在再次进入此列表才能被重新渲染而被刷新
//                                            mVodPlayerListAdapter.clear();
////                                            第二步是将每一个视频数据列表项item添加进UI视图列表中
//                                            for (VideoModel videoModel1 : mVodList) {
//                                                mVodPlayerListAdapter.addSuperPlayerModel(videoModel1);
//                                            }
////                                            第三步是mVodPlayerListAdapter.notifyDataSetChanged()，
////                                            而不是mSwipeRefreshLayout.setRefreshing(false)
//                                            mVodPlayerListAdapter.notifyDataSetChanged();
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onFail(int errCode) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(mContext, getString(R.string.superplayer_request_fail), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            });
////                            loader.getVodByFileId的执行在loader.setOnVodInfoLoadListener的前面，及时前者的代码摆放顺序在后面，
////                            否则后者的代码位置即使出现在前者之前，其也不会被执行
//                            loader.getVodByFileId(videoModel);
////                            通过线程的方案不行：请求新的点播视频之前先将默认列表清空，避免误判
////                            Thread t = new Thread(() -> {
////                                loader.getVodByFileId(videoModel);
////                            });
////                            t.start();
////                            try {
////                                t.join();
////                            } catch (InterruptedException e) {
////                                e.printStackTrace();
////                            }
////                            mVodPlayerListAdapter.reverse();
//                        } else { //通过URL来播放
//                            String playUrl = etAppId.getText().toString();
//                            if (TextUtils.isEmpty(playUrl)) {
//                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_play_url), Toast.LENGTH_SHORT).show();
//                            } else {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        playNewVideo(playUrl);
////                                      重新渲染显示直播列表
////                                        mVodPlayerListAdapter.clear();
////                                        mLiveList.add(0, videoModel);
////                                        for (VideoModel videoModel : mLiveList) {
////                                            Log.i(TAG, "run: " + videoModel.toString());
////                                            mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
////                                        }
////                                        mVodPlayerListAdapter.notifyDataSetChanged();
////                                        设置是否有加载圆圈，不用加这行语句
////                                        mSwipeRefreshLayout.setRefreshing(true);
//                                    }
//                                });
//                            }
//                        }
//
//                    }
//                });
//        dialog.show();
//    }
//
//    private void playNewVideo(String result) {
//        mVideoCount++;
//        VideoModel videoModel = new VideoModel();
//        videoModel.title = getString(R.string.superplayer_test_video) + mVideoCount;
//        videoModel.videoURL = result;
//        videoModel.placeholderImage = DEFAULT_IMAGHOLDER;
//        videoModel.appid = DEFAULT_APPID;
//        if (!TextUtils.isEmpty(videoModel.videoURL) && videoModel.videoURL.contains("liteavapp.qcloud.com")) {
//            videoModel.appid = 1253131631;
//            TXLiveBase.setAppID("1253131631");
//            videoModel.multiVideoURLs = new ArrayList<>(3);
//            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL(getString(R.string.superplayer_definition_super), videoModel.videoURL));
//            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL(getString(R.string.superplayer_definition_high), videoModel.videoURL.replace(".flv", "_900.flv")));
//            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL(getString(R.string.superplayer_definition_standard), videoModel.videoURL.replace(".flv", "_550.flv")));
//        }
//        if (!TextUtils.isEmpty(videoModel.videoURL) && videoModel.videoURL.contains("3891.liveplay.myqcloud.com")) {
//            videoModel.appid = 1252463788;
//            TXLiveBase.setAppID("1252463788");
//        }
//
//        boolean needRefreshList = false;
//        if (isSuperPlayerVideo(videoModel)) {
//            boolean rst = playSuperPlayerVideo(videoModel);
//            if (rst) {
//                mVodList.add(videoModel);
//                needRefreshList = mDataType == LIST_TYPE_VOD;
//            }
//        } else if (isLivePlay(videoModel)) {
//            mLiveList.add(0, videoModel);
//            needRefreshList = mDataType == LIST_TYPE_LIVE;
//            Log.i(TAG, "playNewVideo0: " + mLiveList.get(0).toString());
//            playVideoModel(videoModel);
//        } else {
//            mVodList.add(videoModel);
//            needRefreshList = mDataType == LIST_TYPE_VOD;
//            playVideoModel(videoModel);
//        }
//        if (needRefreshList) {
//            mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//            mVodPlayerListAdapter.notifyDataSetChanged();
////            mVodPlayerListAdapter.clear();
////            for (VideoModel videoModel1 : mLiveList) {
////                Log.i(TAG, "playNewVideo1: " + videoModel1.toString());
////                mVodPlayerListAdapter.addSuperPlayerModel(videoModel1);
////            }
////            mVodPlayerListAdapter.notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * 播放外部传入的视频
//     * 通过Intent传入的数据
//     */
//    private void playExternalVideo() {
//        Intent intent = getIntent();
//        String appId = intent.getStringExtra("appId");
//        String fileId = intent.getStringExtra("fileId");
//        String psign = intent.getStringExtra("psign");
//        playExternalVideo(appId, fileId, psign);
//    }
//
//    /**
//     * 播放外部传入的数据
//     * 通过扫码返回的url数据
//     *
//     * @param result
//     */
//    private void playExternalVideo(String result) {
//        if (result.contains("protocol=v4vodplay")) { // 优先解析包含v4协议字段的特殊食品
//            Uri uri = Uri.parse(result);
//            String appId = uri.getQueryParameter("appId");
//            String fileId = uri.getQueryParameter("fileId");
//            String psign = uri.getQueryParameter("psign");
//            playExternalVideo(appId, fileId, psign);
//        } else {
//            playNewVideo(result);
//        }
//    }
//
//    private void playExternalVideo(String appId, String fileId, String psign) {
////        txsuperplayer://play_vod?v=4&appId=1400295357&fileId=5285890796599775084&pcfg=Default
//        String videoURL = "txsuperplayer://play_vod?appId=" + appId + "&fileId=" + fileId + "&psign=" + psign;
//        Log.d(TAG, "playExternalVideo: videoURL -> " + videoURL);
//        playNewVideo(videoURL);
//    }
//
//    @Override
//    public void onStartFullScreenPlay() {
//        // 隐藏其他元素实现全屏
//        mLayoutTitle.setVisibility(GONE);
//        if (mImageAdd != null) {
//            mImageAdd.setVisibility(GONE);
//        }
//    }
//
//    @Override
//    public void onStopFullScreenPlay() {
//        // 恢复原有元素
//        mLayoutTitle.setVisibility(VISIBLE);
//        if (mImageAdd != null) {
//            mImageAdd.setVisibility(VISIBLE);
//        }
//    }
//
//    @Override
//    public void onClickFloatCloseBtn() {
//        // 点击悬浮窗关闭按钮，那么结束整个播放
//        mSuperPlayerView.resetPlayer();
//        finish();
//    }
//
//    @Override
//    public void onClickSmallReturnBtn() {
//        // 点击小窗模式下返回按钮，开始悬浮播放
//        showFloatWindow();
//    }
//
//    @Override
//    public void onStartFloatWindowPlay() {
//        // 开始悬浮播放后，直接返回到桌面，进行悬浮播放
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onRefresh() {
//        if (mDefaultVideo) {
//            mSwipeRefreshLayout.setRefreshing(false);
//            return;
//        }
//        if (mDataType == LIST_TYPE_VOD) {
//            mVodList.clear();
//            VideoDataMgr.getInstance().getVideoList();
//        } else {
//            updateLiveList();
//        }
//    }
//
//    private void putBoolean(String key, boolean value) {
//        getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
//    }
//
//    private boolean getBoolean(String key) {
//        return getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(key, false);
//    }
//
//}

public class SuperPlayerActivity extends Activity implements View.OnClickListener,
        SuperPlayerView.OnSuperPlayerViewCallback,
        TCVodPlayerListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG                   = "SuperPlayerActivity";
    private static final String SHARE_PREFERENCE_NAME = "tx_super_player_guide_setting";
    private static final String KEY_GUIDE_ONE         = "is_guide_one_finish";
    private static final String KEY_GUIDE_TWO         = "is_guide_two_finish";
    private static final String DEFAULT_IMAGHOLDER    = "http://xiaozhibo-10055601.file.myqcloud.com/coverImg.jpg";
    private static final float sPlayerViewDisplayRatio = (float) 720 / 1280;   //当前界面播放器view展示的宽高比，用主流的16：9
    private static final int    LIST_TYPE_LIVE        = 0;
    private static final int    LIST_TYPE_VOD         = 1;
    private static final int    REQUEST_CODE_QR_SCAN  = 100;
    private static final int    APP_ID_INDEX          = 5;
    private static final int    FILE_ID_INDEX         = 6;
    private static final int    MIN_VALUE_ARRAY_SIZE  = 7;


    private Context                  mContext;
    private RelativeLayout           mLayoutTitle;
    private RelativeLayout           mRelativeMaskOne;
    private RelativeLayout           mRelativeMaskTwo;
    private ImageView                mImageBack;
    private ImageView                mBtnScan;
    private ImageView                mImageAdd;
    private ImageButton              mImageLink;
    private View                     mTitleMask;
    private View                     mListMask;
    private TextView                 mTextOne;
    private TextView                 mTextTwo;
    private SwipeRefreshLayout       mSwipeRefreshLayout;
    private SuperPlayerView          mSuperPlayerView;
    private RecyclerView             mVodPlayerListView;
    private int                      DEFAULT_APPID = 1252463788;
    private int                      mDataType     = LIST_TYPE_LIVE;
    private int                      mVideoCount;
    private SuperVodListLoader       mSuperVodListLoader;
    private TCVodPlayerListAdapter   mVodPlayerListAdapter;
    private GetVideoInfoListListener mGetVideoInfoListListener;
    private ArrayList<VideoModel>    mLiveList;
    private ArrayList<VideoListModel>    mVodList;
    private ArrayList<ListTabItem>   mListTabs;
    private boolean                  mVideoHasPlay;
    private boolean                  mDefaultVideo;
    private String                   mVideoId;

    private static class ListTabItem {
        public ListTabItem(int type, TextView textView, ImageView imageView, View.OnClickListener listener) {
            this.type = type;
            this.textView = textView;
            this.imageView = imageView;
            this.textView.setOnClickListener(listener);
        }

        public int       type;
        public TextView  textView;
        public ImageView imageView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.superplayer_activity_supervod_player);

        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
        initData();

        mDataType = mDefaultVideo ? LIST_TYPE_LIVE : LIST_TYPE_VOD;
        updateList(mDataType);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String from = intent.getStringExtra("from");
        if (!TextUtils.isEmpty(from)) {
            playExternalVideo();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void finish() {
        if (getActivityCount() == 1) {
            startMainActivity();
        }
        super.finish();
    }

    /**
     * 以16：9 比例显示播放器view，优先保证宽度完全填充
     */
    private void adjustSuperPlayerViewAndMaskHeight() {
        final int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = mSuperPlayerView.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = (int) (screenWidth * sPlayerViewDisplayRatio);
        mSuperPlayerView.setLayoutParams(layoutParams);
        mRelativeMaskOne.setLayoutParams(layoutParams);
        mRelativeMaskTwo.setLayoutParams(layoutParams);
    }

    private int getActivityCount() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
        return taskInfoList.size();
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setAction("com.tencent.liteav.action.liteavapp");
        IntentUtils.safeStartActivity(this, intent);
    }

    private void initView() {
        mLayoutTitle = (RelativeLayout) findViewById(R.id.superplayer_rl_title);
        mImageBack = (ImageView) findViewById(R.id.superplayer_iv_back);
        mImageBack.setOnClickListener(this);
        mBtnScan = (ImageView) findViewById(R.id.superplayer_btn_scan);
        mBtnScan.setOnClickListener(this);
        mImageLink = (ImageButton) findViewById(R.id.superplayer_ib_webrtc_link_button);
        mImageLink.setOnClickListener(this);

        mSuperPlayerView = (SuperPlayerView) findViewById(R.id.superVodPlayerView);
        mSuperPlayerView.setPlayerViewCallback(this);

        mVodPlayerListView = (RecyclerView) findViewById(R.id.superplayer_recycler_view);
        mVodPlayerListView.setLayoutManager(new LinearLayoutManager(this));
        mVodPlayerListAdapter = new TCVodPlayerListAdapter(this);
        mVodPlayerListAdapter.setOnItemClickListener(this);
        mVodPlayerListView.setAdapter(mVodPlayerListAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.superplayer_swipe_refresh_layout_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mImageAdd = (ImageView) findViewById(R.id.superplayer_iv_add);
        mImageAdd.setOnClickListener(this);

        mListTabs = new ArrayList<>();
        mListTabs.add(LIST_TYPE_LIVE, new ListTabItem(LIST_TYPE_LIVE, (TextView) findViewById(R.id.superplayer_tv_live), null, this));
        mListTabs.add(LIST_TYPE_VOD, new ListTabItem(LIST_TYPE_VOD, (TextView) findViewById(R.id.superplayer_tv_vod), null, this));

        initNewGuideLayout();
        initMaskLayout();
    }

    /**
     * 初始化新手引导布局
     */
    private void initNewGuideLayout() {
        mRelativeMaskOne = (RelativeLayout) findViewById(R.id.superplayer_small_rl_mask_one);
        mRelativeMaskOne.setOnTouchListener(new View.OnTouchListener() { // 拦截事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mRelativeMaskTwo = (RelativeLayout) findViewById(R.id.superplayer_small_rl_mask_two);
        mRelativeMaskTwo.setOnTouchListener(new View.OnTouchListener() { // 拦截事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        adjustSuperPlayerViewAndMaskHeight();
        mTextOne = (TextView) findViewById(R.id.superplayer_small_tv_btn1);
        mTextTwo = (TextView) findViewById(R.id.superplayer_small_tv_btn2);

        boolean isFinishOne = getBoolean(KEY_GUIDE_ONE);
        boolean isFinishTwo = getBoolean(KEY_GUIDE_TWO);

        if (isFinishOne) {
            mRelativeMaskOne.setVisibility(GONE);
            if (isFinishTwo) {
                //ignore
            } else {
                mRelativeMaskTwo.setVisibility(VISIBLE);
            }
        } else {
            mRelativeMaskOne.setVisibility(VISIBLE);
            mRelativeMaskTwo.setVisibility(GONE);
        }

        mTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeMaskOne.setVisibility(GONE);
                mRelativeMaskTwo.setVisibility(VISIBLE);
                putBoolean(KEY_GUIDE_ONE, true);
                putBoolean(KEY_GUIDE_TWO, false);
            }
        });
        mTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeMaskOne.setVisibility(GONE);
                mRelativeMaskTwo.setVisibility(GONE);
                mTitleMask.setVisibility(GONE);
                mListMask.setVisibility(GONE);
                putBoolean(KEY_GUIDE_ONE, true);
                putBoolean(KEY_GUIDE_TWO, true);
            }
        });

    }

    private void initMaskLayout() {
        mTitleMask = findViewById(R.id.superplayer_view_title_mask);
        mTitleMask.setOnClickListener(new View.OnClickListener() {// 拦截所有事件
            @Override
            public void onClick(View v) {

            }
        });
        mListMask = findViewById(R.id.superplayer_view_list_mask);
        mListMask.setOnClickListener(new View.OnClickListener() { // 拦截所有事件
            @Override
            public void onClick(View v) {

            }
        });
        boolean isFinishOne = getBoolean(KEY_GUIDE_ONE);
        boolean isFinishTwo = getBoolean(KEY_GUIDE_TWO);
        if (!isFinishOne || !isFinishTwo) {
            mTitleMask.setVisibility(VISIBLE);
            mListMask.setVisibility(VISIBLE);
        } else {
            mTitleMask.setVisibility(GONE);
            mListMask.setVisibility(GONE);
        }
    }


    private void initData() {
        mLiveList = new ArrayList<>();
        mVodList = new ArrayList<>();
        mDefaultVideo = getIntent().getBooleanExtra(SuperPlayerConstants.PLAYER_DEFAULT_VIDEO, true);
        mSuperVodListLoader = new SuperVodListLoader();

        initSuperVodGlobalSetting();

        mVideoHasPlay = false;

        mVideoCount = 0;

        TXLiveBase.setAppID("1253131631");
    }

    private void addVideoModelIntoVodPlayerListAdapter(VideoModel videoModel) {
        VideoListModel videoListModel = new VideoListModel();
        videoListModel.addVideoModel(videoModel);
        mVodPlayerListAdapter.addSuperPlayerModel(videoListModel);
    }

    private void updateLiveList() {
        mLiveList.clear();
        mSuperVodListLoader.getLiveList(new SuperVodListLoader.OnListLoadListener() {
            @Override
            public void onSuccess(final ArrayList<VideoModel> superPlayerModelList) {
                SuperPlayerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mDataType != LIST_TYPE_LIVE) return;
                        mVodPlayerListAdapter.clear();
                        for (VideoModel videoModel :
                                superPlayerModelList) {
                            addVideoModelIntoVodPlayerListAdapter(videoModel);
                            mLiveList.add(videoModel);
                        }
                        if (!mVideoHasPlay && !mLiveList.isEmpty()) {
                            if (mLiveList.get(0).appid > 0) {
                                TXLiveBase.setAppID("" + mLiveList.get(0).appid);
                            }
                            String from = getIntent().getStringExtra("from");
                            if (TextUtils.isEmpty(from)) {
                                playVideoModel(mLiveList.get(0));
                            } else {
                                playExternalVideo();
                            }
                            mVideoHasPlay = true;
                        }
                        mVodPlayerListAdapter.notifyDataSetChanged();

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            public void onFail(int errCode) {
                Log.e(TAG, "updateLiveList error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        mVodPlayerListAdapter.notifyDataSetChanged();
    }

    private void onGetVodInfoOnebyOneOnSuccess(final VideoModel videoModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDataType != LIST_TYPE_VOD) {
                    return;
                }
                if (TextUtils.equals("8602268011437356984", videoModel.fileid)) {
                    videoModel.title = getString(R.string.superplayer_cover_video_name);
                }
                addVideoModelIntoVodPlayerListAdapter(videoModel);
                addVideoModelIntoVodList(videoModel);
            }
        });
    }

    private void updateVodList() {
        if (mDefaultVideo) {
            ArrayList<VideoModel> superPlayerModels = mSuperVodListLoader.loadDefaultVodList(this.getApplicationContext());
            mSuperVodListLoader.getVodInfoOneByOne(superPlayerModels, new SuperVodListLoader.OnVodInfoLoadListener() {
                @Override
                public void onSuccess(VideoModel videoModel) {
                    onGetVodInfoOnebyOneOnSuccess(videoModel);
                }

                @Override
                public void onFail(int errCode) {

                }
            });
            ArrayList<VideoModel> circleModels = mSuperVodListLoader.loadCircleVodList();
            mSuperVodListLoader.getBatchVodList(circleModels, new SuperVodListLoader.OnListLoadListener() {
                @Override
                public void onSuccess(ArrayList<VideoModel> videoModels) {
                    final VideoListModel videoListModel = new VideoListModel();
                    videoListModel.videoModelList = videoModels;
                    videoListModel.title = "视频列表轮播演示";
                    videoListModel.icon = "http://1500005830.vod2.myqcloud.com/6c9a5118vodcq1500005830/f817e7c8387702291186401215/gk5EbAYcy10A.png";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVodPlayerListAdapter.addSuperPlayerModel(videoListModel);
                        }
                    });
                    mVodList.add(videoListModel);
                }

                @Override
                public void onFail(int errCode) {

                }
            });
            mImageAdd.setVisibility(VISIBLE);
        } else {
            mVideoId = getIntent().getStringExtra(SuperPlayerConstants.PLAYER_VIDEO_ID);
            if (!TextUtils.isEmpty(mVideoId)) {
                playDefaultVideo(SuperPlayerConstants.VOD_APPID, mVideoId);
                mVideoHasPlay = true;
            }
            mGetVideoInfoListListener = new GetVideoInfoListListener() {
                @Override
                public void onGetVideoInfoList(final List<VideoInfo> videoInfoList) {
                    if (mDataType != LIST_TYPE_VOD) return;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVodPlayerListAdapter.clear();
                            mVodPlayerListAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                            ArrayList<VideoModel> videoModels = VideoDataMgr.getInstance().loadVideoInfoList(videoInfoList);
                            if (videoModels != null && videoModels.size() != 0) {
                                mSuperVodListLoader.getVodInfoOneByOne(videoModels,
                                        new SuperVodListLoader.OnVodInfoLoadListener() {
                                            @Override
                                            public void onSuccess(VideoModel videoModel) {
                                                onGetVodInfoOnebyOneOnSuccess(videoModel);
                                            }

                                            @Override
                                            public void onFail(int errCode) {

                                            }
                                        });
                            }
                        }
                    });
                }

                @Override
                public void onFail(int errCode) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, getString(R.string.superplayer_fetch_upload_list_video_fail), Toast.LENGTH_SHORT).show();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            };
            VideoDataMgr.getInstance().setGetVideoInfoListListener(mGetVideoInfoListListener);
            VideoDataMgr.getInstance().getVideoList();

            mBtnScan.setVisibility(GONE);
            mImageAdd.setVisibility(GONE);
        }
    }

    private void playDefaultVideo(int appid, String fileid) {
        VideoModel videoModel = new VideoModel();
        videoModel.appid = appid;
        videoModel.fileid = fileid;
        videoModel.title = getString(R.string.superplayer_small_video_special_effects_editing);
        if (videoModel.appid > 0) {
            TXLiveBase.setAppID("" + videoModel.appid);
        }
        playVideoModel(videoModel);
    }

    /**
     * 初始化超级播放器全局配置
     */
    private void initSuperVodGlobalSetting() {
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = true;
        // 设置悬浮窗的初始位置和宽高
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = 810;
        rect.height = 540;
        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        //需要修改为自己的时移域名
        prefs.playShiftDomain = "liteavapp.timeshift.qcloud.com";
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView.getPlayerState() == SuperPlayerDef.PlayerState.PLAYING
                || mSuperPlayerView.getPlayerState() == SuperPlayerDef.PlayerState.PAUSE) {
            Log.i(TAG, "onResume state :" + mSuperPlayerView.getPlayerState());
            if (!mSuperPlayerView.isShowingVipView()) {
                mSuperPlayerView.onResume();
            }
            if (mSuperPlayerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FLOAT) {
                mSuperPlayerView.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
            }
        }
        if (mSuperPlayerView.getPlayerMode() == SuperPlayerDef.PlayerMode.FULLSCREEN) {
            //隐藏虚拟按键，并且全屏
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                decorView.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause state :" + mSuperPlayerView.getPlayerState());
        if (mSuperPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mSuperPlayerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuperPlayerView.release();
        if (mSuperPlayerView.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mSuperPlayerView.resetPlayer();
        }
        VideoDataMgr.getInstance().setGetVideoInfoListListener(null);
    }

    @Override
    public void onItemClick(List<VideoModel> videoModelArrayList) {
        if (videoModelArrayList.size() == 1) {
            if (videoModelArrayList.get(0).appid > 0) {
                TXLiveBase.setAppID("" + videoModelArrayList.get(0).appid);
            }
            playVideoModel(videoModelArrayList.get(0));
        } else {
            playWithModelList(videoModelArrayList);
        }
    }

    private void playWithModelList(List<VideoModel> videoModelArrayList) {
        List<SuperPlayerModel> superPlayerModelList = new ArrayList<>();
        for (VideoModel videoModel : videoModelArrayList) {
            superPlayerModelList.add(videoModel.convertToSuperPlayerModel());
        }
        mSuperPlayerView.playWithModelList(superPlayerModelList, true, 0);
    }

    private void playVideoModel(VideoModel videoModel) {
        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();
        superPlayerModelV3.appId = videoModel.appid;
        superPlayerModelV3.vipWatchMode = videoModel.vipWatchModel;
        if (videoModel.dynamicWaterConfig != null) {
            superPlayerModelV3.dynamicWaterConfig = videoModel.dynamicWaterConfig;
        }
        if (!TextUtils.isEmpty(videoModel.videoURL)) {
            if (isSuperPlayerVideo(videoModel)) {
                playSuperPlayerVideo(videoModel);
                return;
            } else {
                superPlayerModelV3.title = videoModel.title;
                superPlayerModelV3.url = videoModel.videoURL;

                superPlayerModelV3.multiURLs = new ArrayList<>();
                if (videoModel.multiVideoURLs != null) {
                    for (VideoModel.VideoPlayerURL modelURL : videoModel.multiVideoURLs) {
                        superPlayerModelV3.multiURLs.add(new SuperPlayerModel.SuperPlayerURL(modelURL.url, modelURL.title));
                    }
                }
            }
        } else if (!TextUtils.isEmpty(videoModel.fileid)) {
            superPlayerModelV3.videoId = new SuperPlayerVideoId();
            superPlayerModelV3.videoId.fileId = videoModel.fileid;
            superPlayerModelV3.videoId.pSign = videoModel.pSign;
        }
        superPlayerModelV3.title = videoModel.title;
        superPlayerModelV3.playAction = videoModel.playAction;
        superPlayerModelV3.placeholderImage = videoModel.placeholderImage;
        superPlayerModelV3.coverPictureUrl = videoModel.coverPictureUrl;
        superPlayerModelV3.duration = videoModel.duration;
        mSuperPlayerView.playWithModel(superPlayerModelV3);
    }

    private boolean playSuperPlayerVideo(VideoModel videoModel) {
        final SuperPlayerModel model = new SuperPlayerModel();
        String videoUrl = videoModel.videoURL;
        String appIdStr = getValueByName(videoUrl, "appId");
        boolean rst = true;
        try {
            model.appId = appIdStr.equals("") ? 0 : Integer.valueOf(appIdStr);
            SuperPlayerVideoId videoId = new SuperPlayerVideoId();
            videoId.fileId = getValueByName(videoUrl, "fileId");
            videoId.pSign = getValueByName(videoUrl, "psign");
            model.videoId = videoId;
            mSuperPlayerView.playWithModel(model);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.superplayer_scancode_tip, Toast.LENGTH_SHORT).show();
            rst = false;
        }
        return rst;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.superplayer_iv_add) {            // 点击+添加一个点播列表项
            showAddVideoDialog();
        } else if (id == R.id.superplayer_btn_scan) {   // 扫描二维码播放一个视频
            scanQRCode();
        } else if (id == R.id.superplayer_iv_back) {    // 悬浮窗播放
            showFloatWindow();
        } else if (id == R.id.superplayer_tv_live) {
            mDataType = LIST_TYPE_LIVE;
            updateList(mDataType);
        } else if (id == R.id.superplayer_tv_vod) {
            mDataType = LIST_TYPE_VOD;
            updateList(mDataType);
        } else if (id == R.id.superplayer_ib_webrtc_link_button) {
            showCloudLink();
        }
    }

    private void showCloudLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://cloud.tencent.com/document/product/454/18872"));
        IntentUtils.safeStartActivity(this, intent);
    }

    private void updateList(int dataType) {
        for (ListTabItem item : mListTabs) {
            if (item.type == dataType) {
                item.textView.setTextColor(Color.rgb(255, 255, 255));
            } else {
                item.textView.setTextColor(Color.rgb(119, 119, 119));
            }
        }

        mVodPlayerListAdapter.clear();
        switch (mDataType) {
            case LIST_TYPE_LIVE:
                if (mLiveList.isEmpty()) {
                    updateLiveList();
                } else {
                    for (VideoModel videoModel : mLiveList) {
                        addVideoModelIntoVodPlayerListAdapter(videoModel);
                    }
                }
                break;
            case LIST_TYPE_VOD:
                if (isNeedUpdateVodList()) {
                    if (mVodList != null && !mVodList.isEmpty()) {
                        for (VideoListModel videoListModel : mVodList) {
                            mVodPlayerListAdapter.addSuperPlayerModel(videoListModel);
                        }
                    }
                    updateVodList();
                } else {
                    for (VideoListModel videoListModel : mVodList) {
                        mVodPlayerListAdapter.addSuperPlayerModel(videoListModel);
                    }
                }
                break;
        }

        mVodPlayerListAdapter.notifyDataSetChanged();
    }

    private boolean isNeedUpdateVodList() {
        if (mVodList == null || mVodList.isEmpty()) {
            return true;
        }
        for (VideoListModel videoListModel : mVodList) {
            if (videoListModel.videoModelList.size() > 1) {
                if (DEFAULT_IMAGHOLDER != videoListModel.icon) {
                    return false;
                }
            } else if (videoListModel.videoModelList.size() == 1) {
                if (DEFAULT_IMAGHOLDER != videoListModel.videoModelList.get(0).placeholderImage) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 悬浮窗播放
     */
    private void showFloatWindow() {
        if (mSuperPlayerView.getPlayerState() == SuperPlayerDef.PlayerState.PLAYING) {
            mSuperPlayerView.switchPlayMode(SuperPlayerDef.PlayerMode.FLOAT);
        } else {
            mSuperPlayerView.resetPlayer();
            finish();
        }
    }

    /**
     * 扫描二维码
     */
    private void scanQRCode() {
        Intent intent = new Intent(this, QRCodeScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || TextUtils.isEmpty(data.getStringExtra("result"))) {
            return;
        }
        String result = data.getStringExtra("result");
        if (REQUEST_CODE_QR_SCAN == requestCode) {
            // 二维码播放视频
            playExternalVideo(result);
        }
    }

    private boolean isLivePlay(VideoModel videoModel) {
        String videoURL = videoModel.videoURL;
        if (TextUtils.isEmpty(videoModel.videoURL)) {
            return false;
        }
        if (videoURL.startsWith("rtmp://")) {
            return true;
        } else if ((videoURL.startsWith("http://") || videoURL.startsWith("https://")) ) {
            // 去掉 && videoURL.contains(".flv")
            return true;
        } else {
            return false;
        }
    }

    private boolean isSuperPlayerVideo(VideoModel videoModel) {
        return videoModel.videoURL.startsWith("txsuperplayer://play_vod");
    }

    private String getValueByName(String url, String name) { //txsuperplayer://play_vod?v=4&appId=1400295357&fileId=5285890796599775084&pcfg=Default
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.startsWith(name + "=")) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    /**
     * 点击+添加一个点播列表项
     */
    private void showAddVideoDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.superplayer_dialog_new_vod_player_fileid, null);

        dialog.setView(dialogView);

        final EditText etAppId = (EditText) dialogView.findViewById(R.id.superplayer_et_appid);
        final EditText etFileId = (EditText) dialogView.findViewById(R.id.superplayer_et_fileid);
        final EditText etPSign = (EditText) dialogView.findViewById(R.id.superplayer_et_psign);

        if (mDataType == LIST_TYPE_VOD) {
            dialog.setTitle(getString(R.string.superplayer_set_appid_fileid));
        } else {
            dialog.setTitle(getString(R.string.superplayer_set_play_url));
            dialogView.findViewById(R.id.superplayer_tv_appid_text).setVisibility(GONE);
            dialogView.findViewById(R.id.superplayer_tv_fileid_text).setVisibility(GONE);
            etFileId.setVisibility(GONE);
            etPSign.setVisibility(GONE);
        }

        dialog.setNegativeButton(getString(R.string.superplayer_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(getString(R.string.superplayer_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mDataType == LIST_TYPE_VOD) {
                            String appId = etAppId.getText().toString();
                            String fileId = etFileId.getText().toString();
                            String pSign = etPSign.getText().toString();

                            if (TextUtils.isEmpty(appId)) {
                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_appid), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(fileId)) {
                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_fileid), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            int appid;
                            try {
                                appid = Integer.parseInt(appId);
                            } catch (NumberFormatException e) {
                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_appid), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            VideoModel videoModel = new VideoModel();
                            videoModel.appid = appid;
                            videoModel.fileid = fileId;
                            videoModel.pSign = pSign;

                            // 尝试请求fileid信息
                            SuperVodListLoader loader = new SuperVodListLoader();
//                            第849~870行开始的代码块无用
                            loader.setOnVodInfoLoadListener(new SuperVodListLoader.OnVodInfoLoadListener() {
                                @Override
                                public void onSuccess(final VideoModel videoModel) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                            addVideoModelIntoVodPlayerListAdapter(videoModel);
                                            Log.i(TAG, "run: 111111111111");
                                        }
                                    });
                                }

                                @Override
                                public void onFail(int errCode) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, getString(R.string.superplayer_request_fail), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            loader.getVodByFileId(videoModel, new SuperVodListLoader.OnVodInfoLoadListener() {
                                @Override
                                public void onSuccess(final VideoModel videoModel) {
//                                    onGetVodInfoOnebyOneOnSuccess(videoModel);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 以下操作必须在UI主线程中进行
//                                            实现点播视频的item添加后便立即播放
                                            playVideoModel(videoModel);
//                                            视频数据列表——mVodList；UI视频列表——mVodPlayerListAdapter（四步）
//                                            1.实现点播视频列表数据项item的添加
                                            VideoListModel videoListModel = new VideoListModel();
                                            videoListModel.addVideoModel(videoModel);
                                            mVodList.add(0, videoListModel);
//                                            直播/点播列表的UI展示刷新，需要重新渲染
//                                            2.clear()，即先清除原有列表中的所有元素，
//                                            否则只能在再次进入此列表才能被重新渲染而被刷新
                                            mVodPlayerListAdapter.clear();
//                                            3.将每一个视频数据列表项item添加进UI视图列表中
                                            for (int i = 0; i < mVodList.size(); i++) {
                                                for (int j = 0; j < mVodList.get(i).size(); j++) {
                                                    addVideoModelIntoVodPlayerListAdapter(mVodList.get(i).get(j));
                                                }
                                            }
                                            //                                    4.mVodPlayerListAdapter.notifyDataSetChanged()，
                                            //                                    而不是mSwipeRefreshLayout.setRefreshing(false)
                                            mVodPlayerListAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }

                                @Override
                                public void onFail(int errCode) {

                                }
                            });
                        } else {
                            String playUrl = etAppId.getText().toString();
                            if (TextUtils.isEmpty(playUrl)) {
                                Toast.makeText(mContext, getString(R.string.superplayer_input_correct_play_url), Toast.LENGTH_SHORT).show();
                            } else {
                                playNewVideo(playUrl);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                      直播/点播列表的UI展示刷新，需要重新渲染，因此第一步是clear()，即先清除原有列表中的所有元素，
//                                      否则只能在再次进入此列表才能被重新渲染而被刷新
                                        mVodPlayerListAdapter.clear();
//                                      第二步是将每一个视频数据列表项item添加进UI视图列表中
                                        for (int i = 0; i < mLiveList.size(); ++i) {
                                            addVideoModelIntoVodPlayerListAdapter(mLiveList.get(i));
                                        }
//                                      第三步是mVodPlayerListAdapter.notifyDataSetChanged()，
//                                      而不是mSwipeRefreshLayout.setRefreshing(false)
                                        mVodPlayerListAdapter.notifyDataSetChanged();
//                                      mSwipeRefreshLayout.setRefreshing(false);
                                    }
                                });
                            }
                        }

                    }
                });
        dialog.show();
    }

    private void addVideoModelIntoVodList(VideoModel videoModel) {
        VideoListModel videoListModel = new VideoListModel();
        videoListModel.addVideoModel(videoModel);
        mVodList.add(videoListModel);
//        mVodList.add(0, videoListModel);
    }

    private void playNewVideo(String result) {
        mVideoCount++;
        VideoModel videoModel = new VideoModel();
        videoModel.title = getString(R.string.superplayer_test_video) + mVideoCount;
        videoModel.videoURL = result;
        videoModel.placeholderImage = DEFAULT_IMAGHOLDER;
        videoModel.appid = DEFAULT_APPID;
        if (!TextUtils.isEmpty(videoModel.videoURL) && videoModel.videoURL.contains("liteavapp.qcloud.com")) {
            videoModel.appid = 1253131631;
            TXLiveBase.setAppID("1253131631");
            videoModel.multiVideoURLs = new ArrayList<>(3);
            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL(getString(R.string.superplayer_definition_super), videoModel.videoURL));
            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL(getString(R.string.superplayer_definition_high), videoModel.videoURL.replace(".flv", "_900.flv")));
            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL(getString(R.string.superplayer_definition_standard), videoModel.videoURL.replace(".flv", "_550.flv")));
        }
        if (!TextUtils.isEmpty(videoModel.videoURL) && videoModel.videoURL.contains("3891.liveplay.myqcloud.com")) {
            videoModel.appid = 1252463788;
            TXLiveBase.setAppID("1252463788");
        }

        boolean needRefreshList = false;
        if (isSuperPlayerVideo(videoModel)) {
            boolean rst = playSuperPlayerVideo(videoModel);
            if (rst) {
                addVideoModelIntoVodList(videoModel);
                needRefreshList = mDataType == LIST_TYPE_VOD;
            }
        } else if (isLivePlay(videoModel)) {
            mLiveList.add(0, videoModel);
            needRefreshList = mDataType == LIST_TYPE_LIVE;
            playVideoModel(videoModel);
        } else {
            addVideoModelIntoVodList(videoModel);
            needRefreshList = mDataType == LIST_TYPE_VOD;
            playVideoModel(videoModel);
        }
//        if (needRefreshList) {
//            addVideoModelIntoVodPlayerListAdapter(videoModel);
//            mVodPlayerListAdapter.notifyDataSetChanged();
//        }
    }

    /**
     * 播放外部传入的视频
     * 通过Intent传入的数据
     */
    private void playExternalVideo() {
        Intent intent = getIntent();
        String appId = intent.getStringExtra("appId");
        String fileId = intent.getStringExtra("fileId");
        String psign = intent.getStringExtra("psign");
        playExternalVideo(appId, fileId, psign);
    }

    /**
     * 播放外部传入的数据
     * 通过扫码返回的url数据
     *
     * @param result
     */
    private void playExternalVideo(String result) {
        if (result.contains("protocol=v4vodplay")) { // 优先解析包含v4协议字段的特殊食品
            Uri uri = Uri.parse(result);
            String appId = uri.getQueryParameter("appId");
            String fileId = uri.getQueryParameter("fileId");
            String psign = uri.getQueryParameter("psign");
            playExternalVideo(appId, fileId, psign);
        } else if (result.contains("https://playvideo.qcloud.com/getplayinfo/v4/")) {
            String[] valueArray = result.split("/");
            String appId = "";
            String fileId = "";
            if (valueArray.length >= MIN_VALUE_ARRAY_SIZE) {
                appId = valueArray[APP_ID_INDEX];
                int positionOfQuestionMark = valueArray[FILE_ID_INDEX].indexOf("?");
                fileId = positionOfQuestionMark > 0 ? valueArray[FILE_ID_INDEX].
                        substring(0, positionOfQuestionMark) : valueArray[FILE_ID_INDEX];
            }
            Uri uri = Uri.parse(result);
            String psign = uri.getQueryParameter("psign");
            playExternalVideo(appId, fileId, psign);
        } else {
            playNewVideo(result);
        }
    }

    private void playExternalVideo(String appId, String fileId, String psign) {
//        txsuperplayer://play_vod?v=4&appId=1400295357&fileId=5285890796599775084&pcfg=Default
        String videoURL = "txsuperplayer://play_vod?appId=" + appId + "&fileId=" + fileId + "&psign=" + psign;
        Log.d(TAG, "playExternalVideo: videoURL -> " + videoURL);
        playNewVideo(videoURL);
    }

    @Override
    public void onStartFullScreenPlay() {
        // 隐藏其他元素实现全屏
        mLayoutTitle.setVisibility(GONE);
        if (mImageAdd != null) {
            mImageAdd.setVisibility(GONE);
        }
    }

    @Override
    public void onStopFullScreenPlay() {
        // 恢复原有元素
        mLayoutTitle.setVisibility(VISIBLE);
        if (mDefaultVideo && mImageAdd != null) {
            mImageAdd.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClickFloatCloseBtn() {
        // 点击悬浮窗关闭按钮，那么结束整个播放
        mSuperPlayerView.resetPlayer();
        finish();
    }

    @Override
    public void onClickSmallReturnBtn() {
        // 点击小窗模式下返回按钮，开始悬浮播放
        showFloatWindow();
    }

    @Override
    public void onStartFloatWindowPlay() {
        // 开始悬浮播放后，直接返回到桌面，进行悬浮播放
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        IntentUtils.safeStartActivity(this, intent);
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPlayEnd() {

    }

    @Override
    public void onError(int code) {

    }

    @Override
    public void onRefresh() {
        if (mDefaultVideo) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        if (mDataType == LIST_TYPE_VOD) {
            mVodList.clear();
            VideoDataMgr.getInstance().getVideoList();
        } else {
            updateLiveList();
        }
    }

    private void putBoolean(String key, boolean value) {
        getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    private boolean getBoolean(String key) {
        return getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(key, false);
    }
}
