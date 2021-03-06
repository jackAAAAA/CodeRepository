package com.tencent.liteav.demo.liveroom.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.liteav.debug.GenerateTestUserSig;
import com.tencent.liteav.demo.liveroom.R;
import android.support.v7.app.AppCompatActivity;
import com.tencent.liteav.demo.liveroom.IMLVBLiveRoomListener;
import com.tencent.liteav.demo.liveroom.MLVBLiveRoom;
import com.tencent.liteav.demo.liveroom.ui.fragment.LiveRoomChatFragment;
import com.tencent.liteav.demo.liveroom.ui.fragment.LiveRoomListFragment;
import com.tencent.liteav.demo.liveroom.roomutil.commondef.AnchorInfo;
import com.tencent.liteav.demo.liveroom.roomutil.commondef.AudienceInfo;
import com.tencent.liteav.demo.liveroom.roomutil.commondef.LoginInfo;
import com.tencent.liteav.demo.liveroom.roomutil.misc.NameGenerator;
import com.tencent.liteav.login.model.ProfileManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.logging.HttpLoggingInterceptor;


public class LiveRoomActivity extends AppCompatActivity implements LiveRoomActivityInterface {

    public final Handler mUiHandler = new Handler();

    private MLVBLiveRoom mMLVBLiveRoom;
    private Runnable     mRetryInitRoomRunnable;

    private String       mUserId;
    private String       mUserName;
    private String       mUserAvatar = "avatar";

    private TextView     mTextTitle;
    private TextView     mTextGlobalLog;
    private ScrollView   mTextGlobalLogContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MlvbBeautyTheme);
        setContentView(R.layout.mlvb_activity_live_room);

        findViewById(R.id.mlvb_liveroom_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTextTitle = ((TextView) findViewById(R.id.mlvb_liveroom_title_textview));
        mTextTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRetryInitRoomRunnable != null) {
                    synchronized (LiveRoomActivity.this) {
                        mRetryInitRoomRunnable.run();
                        mRetryInitRoomRunnable = null;
                    }
                }
            }
        });

        mTextGlobalLog = ((TextView) findViewById(R.id.mlvb_liveroom_global_log_textview));
        mTextGlobalLog.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(LiveRoomActivity.this, R.style.MlvbRtmpRoomDialogTheme)
                        .setTitle(getString(R.string.mlvb_global_log))
                        .setMessage(getString(R.string.mlvb_clear_log))
                        .setNegativeButton(getString(R.string.mlvb_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton(getString(R.string.mlvb_clear), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTextGlobalLog.setText("");
                        dialog.dismiss();
                    }
                }).show();

                return true;
            }
        });
        findViewById(R.id.mlvb_liveroom_link_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://cloud.tencent.com/document/product/454/14606"));
                startActivity(intent);
            }
        });

        mTextGlobalLogContainer = ((ScrollView) findViewById(R.id.mlvb_liveroom_global_log_container));

        mMLVBLiveRoom = MLVBLiveRoom.sharedInstance(this.getApplicationContext());
        mMLVBLiveRoom.setListener(new MLVBLiveRoomListener());

        initializeLiveRoom();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
        if (fragment instanceof LiveRoomChatFragment) {
            ((LiveRoomChatFragment) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class LoginInfoResponse {
        public int    code;
        public int    sdkAppID;
        public String message;
        public String accType;
        public String userID;
        public String userSig;
    }

    private static class HttpInterceptorLog implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.i("HttpRequest", message + "\n");
        }
    }

    private void initializeLiveRoom() {
        setTitle(getString(R.string.mlvb_connecting));

        final SharedPreferences sp = getSharedPreferences("com.tencent.demo", Context.MODE_PRIVATE);
        String userIdFromSp = sp.getString("userID", "");
        String userNameFromSp = sp.getString("userName", "");
        if (!TextUtils.isEmpty(userNameFromSp)) {
            mUserName = userNameFromSp;
        } else {
            mUserName = NameGenerator.getRandomName(this);
            sp.edit().putString("userName", mUserName).commit();
        }
        SharedPreferences spf = getSharedPreferences("com.tencent.demo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("userID", mUserName);
        editor.commit();
        internalInitializeLiveRoom();
    }

    private void internalInitializeLiveRoom() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.sdkAppID = GenerateTestUserSig.SDKAPPID;
        loginInfo.userID = ProfileManager.getInstance().getUserModel().userId;
        loginInfo.userSig = ProfileManager.getInstance().getUserModel().userSig;
        loginInfo.userName = mUserName;
        loginInfo.userAvatar = mUserAvatar;
        LiveRoomActivity.this.mUserId = ProfileManager.getInstance().getUserModel().userId;

        mMLVBLiveRoom.login(loginInfo, new IMLVBLiveRoomListener.LoginCallback() {
            @Override
            public void onError(int errCode, String errInfo) {
                setTitle(errInfo);
                printGlobalLog(getString(R.string.mlvb_live_room_init_fail, errInfo));
                mRetryInitRoomRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LiveRoomActivity.this, getString(R.string.mlvb_retry), Toast.LENGTH_SHORT).show();
                        initializeLiveRoom();
                    }
                };
            }

            @Override
            public void onSuccess() {
                setTitle(getString(R.string.mlvb_phone_live));
                printGlobalLog(getString(R.string.mlvb_live_room_init_success), mUserId);
                Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
                if (!(fragment instanceof LiveRoomChatFragment)) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    fragment = LiveRoomListFragment.newInstance(mUserId);
                    ft.replace(R.id.mlvb_rtmproom_fragment_container, fragment);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public MLVBLiveRoom getLiveRoom() {
        return mMLVBLiveRoom;
    }

    @Override
    public String getSelfUserID() {
        return mUserId;
    }

    @Override
    public String getSelfUserName() {
        return mUserName;
    }

    @Override
    public void showGlobalLog(final boolean enable) {
        if (mUiHandler != null)
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTextGlobalLogContainer.setVisibility(enable ? View.VISIBLE : View.GONE);
                }
            });
    }

    @Override
    public void printGlobalLog(final String format, final Object... args) {
        if (mUiHandler != null) {
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");
                    String line = String.format("[%s] %s\n", dataFormat.format(new Date()), String.format(format, args));
                    mTextGlobalLog.append(line);
                    if (mTextGlobalLogContainer.getVisibility() != View.GONE) {
                        mTextGlobalLogContainer.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }
            });
        }
    }

    @Override
    public void setTitle(final String s) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                String ss = NameGenerator.replaceNonPrintChar(s, 20, "...", false);
                mTextTitle.setLinksClickable(false);
                mTextTitle.setText(ss);
            }
        });
    }

    private final class MLVBLiveRoomListener implements IMLVBLiveRoomListener {

        /**
         * ????????????
         * <p>
         * SDK ????????????????????????????????????????????????????????????????????????????????????
         *
         * @param errCode   ????????? TRTCErrorCode
         * @param errMsg    ????????????
         * @param extraInfo ???????????????????????????????????????????????????????????????????????????????????????
         */
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onError(errCode, errMsg, extraInfo);
            }
        }

        /**
         * ????????????
         *
         * @param warningCode ????????? TRTCWarningCode
         * @param warningMsg  ????????????
         * @param extraInfo   ???????????????????????????????????????????????????????????????????????????????????????
         */
        @Override
        public void onWarning(int warningCode, String warningMsg, Bundle extraInfo) {

        }

        @Override
        public void onDebugLog(String log) {
            printGlobalLog(log);
        }

        /**
         * ????????????????????????
         * <p>
         * ???????????????????????????????????????????????????????????????
         *
         * @param roomID ??????ID
         */
        @Override
        public void onRoomDestroy(String roomID) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onRoomDestroy(roomID);
            }
        }

        /**
         * ???????????????????????????
         * <p>
         * ???????????????????????????????????????????????????????????????????????????????????????????????? {@link MLVBLiveRoom#startRemoteView(AnchorInfo, TXCloudVideoView)} ?????????????????????????????????
         *
         * @param anchorInfo ?????????????????????
         * @note ????????????????????????????????????????????????????????????????????????
         */
        @Override
        public void onAnchorEnter(AnchorInfo anchorInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onAnchorEnter(anchorInfo);
            }
        }

        /**
         * ????????????????????????
         * <p>
         * ???????????????????????????????????????????????????????????????????????????????????????????????? {@link MLVBLiveRoom#stopRemoteView(AnchorInfo)} ?????????????????????????????????
         *
         * @param anchorInfo ??????????????????
         * @note ????????????????????????????????????????????????????????????????????????
         */
        @Override
        public void onAnchorExit(AnchorInfo anchorInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onAnchorExit(anchorInfo);
            }
        }

        /**
         * ????????????????????????
         *
         * @param audienceInfo ??????????????????
         */
        @Override
        public void onAudienceEnter(AudienceInfo audienceInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()){
                ((LiveRoomChatFragment) fragment).onAudienceEnter(audienceInfo);
            }
        }

        /**
         * ????????????????????????
         *
         * @param audienceInfo ??????????????????
         */
        @Override
        public void onAudienceExit(AudienceInfo audienceInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()){
                ((LiveRoomChatFragment) fragment).onAudienceExit(audienceInfo);
            }
        }

        /**
         * ??????????????????????????????????????????
         *
         * @param anchorInfo ????????????
         * @param reason     ??????????????????
         */
        @Override
        public void onRequestJoinAnchor(AnchorInfo anchorInfo, String reason) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onRequestJoinAnchor(anchorInfo, reason);
            }
        }

        /**
         * ??????????????????????????????????????????
         * <p>
         * ?????????????????????????????????????????????????????????????????? {@link MLVBLiveRoom#kickoutJoinAnchor(String)} ???????????????
         */
        @Override
        public void onKickoutJoinAnchor() {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onKickoutJoinAnchor();
            }
        }

        /**
         * ?????????????????? PK ??????
         * <p>
         * ????????????????????????????????? PK ??????
         * ???????????? PK ?????????????????? {@link MLVBLiveRoom#startRemoteView(AnchorInfo, TXCloudVideoView, PlayCallback)}  ??????????????????????????????
         *
         * @param anchorInfo ?????????????????????????????????
         */
        @Override
        public void onRequestRoomPK(AnchorInfo anchorInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onRequestRoomPK(anchorInfo);
            }
        }

        /**
         * ?????????????????? PK ??????
         */
        @Override
        public void onQuitRoomPK(AnchorInfo anchorInfo) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onQuitRoomPK(anchorInfo);
            }
        }

        /**
         * ??????????????????
         *
         * @param roomID     ??????ID
         * @param userID     ?????????ID
         * @param userName   ???????????????
         * @param userAvatar ???????????????
         * @param message    ????????????
         */
        @Override
        public void onRecvRoomTextMsg(String roomID, String userID, String userName, String userAvatar, String message) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.mlvb_rtmproom_fragment_container);
            if (fragment instanceof LiveRoomChatFragment && fragment.isVisible()) {
                ((LiveRoomChatFragment) fragment).onRecvRoomTextMsg(roomID, userID, userName, userAvatar, message);
            }
        }

        /**
         * ?????????????????????
         *
         * @param roomID     ??????ID
         * @param userID     ?????????ID
         * @param userName   ???????????????
         * @param userAvatar ???????????????
         * @param cmd        ?????????cmd
         * @param message    ?????????????????????
         */
        @Override
        public void onRecvRoomCustomMsg(String roomID, String userID, String userName, String userAvatar, String cmd, String message) {
            //do nothing
        }
    }
}
