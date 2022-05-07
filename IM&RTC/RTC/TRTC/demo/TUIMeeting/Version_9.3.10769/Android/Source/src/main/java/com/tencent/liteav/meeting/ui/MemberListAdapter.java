package com.tencent.liteav.meeting.ui;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.liteav.basic.ImageLoader;
import com.tencent.liteav.demo.trtc.R;
import com.tencent.liteav.meeting.model.TRTCMeeting;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MemberListAdapter.class.getSimpleName();

    private static final int         TYPE_SELF    = 0;
    private static final int         TYPE_OTHER   = 1;
    public static final  String      VOLUME_SHOW  = "volume_show";
    public static final  String      VOLUME       = "volume";
    public static final  String      QUALITY      = "quality";
    public static final  String      VIDEO_CHANGE = "video_change";
    private final        TRTCMeeting mTRTCMeeting;

    private Context            context;
    private List<MemberEntity> list;
    private ListCallback       mListCallback;

    public MemberListAdapter(Context context,
                             TRTCMeeting meeting,
                             List<MemberEntity> list,
                             ListCallback listCallback) {
        this.mTRTCMeeting = meeting;
        this.context = context;
        this.list = list;
        this.mListCallback = listCallback;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_SELF) {
            View view = inflater.inflate(R.layout.trtcmeeting_item_meeting_member, parent, false);
            return new SelfViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.trtcmeeting_item_meeting_member, parent, false);
            return new OtherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        if (holder instanceof OtherViewHolder) {
            MemberEntity item = list.get(position);
            ((OtherViewHolder) holder).bind(item, mListCallback);
        } else if (holder instanceof SelfViewHolder) {
            MemberEntity item = list.get(position);
            ((SelfViewHolder) holder).bind(item, mListCallback);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads == null || payloads.size() == 0) {
            onBindViewHolder(holder, position);
        } else {
            if (QUALITY.equals(payloads.get(0))) {
                if (holder instanceof OtherViewHolder) {
                    MemberEntity item = list.get(position);
                    ((OtherViewHolder) holder).setQuality(item.getQuality());
                } else if (holder instanceof SelfViewHolder) {
                    MemberEntity item = list.get(position);
                    ((SelfViewHolder) holder).setQuality(item.getQuality());
                }
            } else if (VOLUME.equals(payloads.get(0))) {
                if (holder instanceof OtherViewHolder) {
                    MemberEntity item = list.get(position);
                    ((OtherViewHolder) holder).setVolume(item.isTalk());
                } else if (holder instanceof SelfViewHolder) {
                    MemberEntity item = list.get(position);
                    ((SelfViewHolder) holder).setVolume(item.isTalk());
                }
            } else if (VOLUME_SHOW.equals(payloads.get(0))) {
                if (holder instanceof OtherViewHolder) {
                    MemberEntity item = list.get(position);
                    ((OtherViewHolder) holder).showVolume(item.isShowAudioEvaluation());
                } else if (holder instanceof SelfViewHolder) {
                    MemberEntity item = list.get(position);
                    ((SelfViewHolder) holder).showVolume(item.isShowAudioEvaluation());
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_SELF : TYPE_OTHER;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SelfViewHolder extends RecyclerView.ViewHolder {
        private TextView         mUserNameTv;
        private MeetingVideoView mViewVideo;
        private CircleImageView  mUserHeadImg;
        private MemberEntity     mMemberEntity;
        private FrameLayout      mVideoContainer;
        private ImageView        mUserSignal;
        private ImageView        mUserSpeak;
        private ProgressBar      mVolumePb;

        private Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                mUserSpeak.setImageResource(R.drawable.trtcmeeting_ic_not_speak);
            }
        };

        public SelfViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        public MemberEntity getMemberEntity() {
            return mMemberEntity;
        }

        public FrameLayout getVideoContainer() {
            return mVideoContainer;
        }

        public MeetingVideoView getViewVideo() {
            return mViewVideo;
        }

        public void setQuality(int quality) {
            if (quality == MemberEntity.QUALITY_GOOD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_3);
            } else if (quality == MemberEntity.QUALITY_NORMAL) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_2);
            } else if (quality == MemberEntity.QUALITY_BAD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_1);
            } else {
                mUserSignal.setVisibility(View.GONE);
            }
        }

        public void setVolume(boolean isTalk) {
            mUserSpeak.setImageResource(isTalk ? R.drawable.trtcmeeting_ic_speak : R.drawable.trtcmeeting_ic_not_speak);
            if (isTalk) {
                mUserSpeak.removeCallbacks(mRunnable);
                mUserSpeak.postDelayed(mRunnable, 2000);
            }
        }

        public void showVolume(boolean isShow) {
            mVolumePb.setVisibility(View.GONE);
        }

        //        public void addVideoView() {
        //            if (mMemberEntity == null) {
        //                return;
        //            }
        //            mViewVideo = mMemberEntity.getMeetingVideoView();
        //            ViewGroup viewGroup = (ViewGroup) mViewVideo.getParent();
        //            if (viewGroup != null && viewGroup != mVideoContainer) {
        //                viewGroup.removeView(mViewVideo);
        //            }
        //            if (viewGroup != mVideoContainer) {
        //                mVideoContainer.addView(mViewVideo);
        //                mViewVideo.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        //            }
        //        }

        public void removeVideoView() {
            mVideoContainer.removeAllViews();
        }

        public void bind(final MemberEntity model,
                         final ListCallback listener) {
            mMemberEntity = model;
            mUserNameTv.setText(model.getUserName());
            ImageLoader.loadImage(context, mUserHeadImg, model.getUserAvatar(), R.drawable.trtcmeeting_meeting_head);
            mMemberEntity.getMeetingVideoView().setWaitBindGroup(mVideoContainer);
            mVideoContainer.removeAllViews();
            if (model.isVideoAvailable() && !model.isMuteVideo()) {
                mVideoContainer.setVisibility(View.VISIBLE);
                mUserHeadImg.setVisibility(View.GONE);
            } else {
                mVideoContainer.setVisibility(View.GONE);
                mUserHeadImg.setVisibility(View.VISIBLE);
            }
            if (model.getQuality() == MemberEntity.QUALITY_GOOD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_3);
            } else if (model.getQuality() == MemberEntity.QUALITY_NORMAL) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_2);
            } else if (model.getQuality() == MemberEntity.QUALITY_BAD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_1);
            } else {
                mUserSignal.setVisibility(View.GONE);
            }

            showVolume(model.isShowAudioEvaluation());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());
                }
            });
        }

        private void initView(final View itemView) {
            mUserNameTv = (TextView) itemView.findViewById(R.id.tv_user_name);
            mUserHeadImg = (CircleImageView) itemView.findViewById(R.id.img_user_head);
            mVideoContainer = (FrameLayout) itemView.findViewById(R.id.fl_container);
            mUserSignal = (ImageView) itemView.findViewById(R.id.img_signal);
            mUserSpeak = (ImageView) itemView.findViewById(R.id.img_speak);
            mVolumePb = (ProgressBar) itemView.findViewById(R.id.pb_volume);
            //            itemView.findViewById(R.id.rl_content).setLayoutParams(new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        private final GestureDetector mSimpleOnGestureListener = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mListCallback != null) {
                    mListCallback.onItemClick(getLayoutPosition());
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mListCallback != null) {
                    mListCallback.onItemDoubleClick(getLayoutPosition());
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
        private       TextView        mUserNameTv;
        private       CircleImageView mUserHeadImg;
        private       MemberEntity    mMemberEntity;
        private       FrameLayout     mVideoContainer;
        private       ImageView       mUserSignal;
        private       ImageView       mUserSpeak;
        private       ProgressBar     mVolumePb;
        private       boolean         isPlaying                = false;
        private       Runnable        mRunnable = new Runnable() {
            @Override
            public void run() {
                mUserSpeak.setImageResource(R.drawable.trtcmeeting_ic_not_speak);
            }
        };

        public OtherViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void setQuality(int quality) {
            if (quality == MemberEntity.QUALITY_GOOD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_3);
            } else if (quality == MemberEntity.QUALITY_NORMAL) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_2);
            } else if (quality == MemberEntity.QUALITY_BAD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_1);
            } else {
                mUserSignal.setVisibility(View.GONE);
            }
        }

        public void setVolume(boolean isTalk) {
            mUserSpeak.setImageResource(isTalk ? R.drawable.trtcmeeting_ic_speak : R.drawable.trtcmeeting_ic_not_speak);
            if (isTalk) {
                mUserSpeak.removeCallbacks(mRunnable);
                mUserSpeak.postDelayed(mRunnable, 2000);
            }
        }

        public void showVolume(boolean isShow) {
            mVolumePb.setVisibility(View.GONE);
        }

        public void bind(final MemberEntity model,
                         final ListCallback listener) {
            mMemberEntity = model;
            Log.d(TAG, "bind: " + mMemberEntity.getUserId() + " mVideoContainer " + mVideoContainer);
            MeetingVideoView videoView = mMemberEntity.getMeetingVideoView();
            if (videoView != null) {
                videoView.setWaitBindGroup(mVideoContainer);
            }
            mVideoContainer.removeAllViews();
            //展示其他数据
            ImageLoader.loadImage(context, mUserHeadImg, model.getUserAvatar(), R.drawable.trtcmeeting_meeting_head);
            mUserNameTv.setText(model.getUserName());
            if (model.getQuality() == MemberEntity.QUALITY_GOOD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_3);
            } else if (model.getQuality() == MemberEntity.QUALITY_NORMAL) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_2);
            } else if (model.getQuality() == MemberEntity.QUALITY_BAD) {
                mUserSignal.setVisibility(View.VISIBLE);
                mUserSignal.setImageResource(R.drawable.trtcmeeting_ic_signal_1);
            } else {
                mUserSignal.setVisibility(View.GONE);
            }
            showVolume(model.isShowAudioEvaluation());
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mSimpleOnGestureListener.onTouchEvent(event);
                }
            });
            mVolumePb.setProgress(0);
        }

        private void initView(final View itemView) {
            mUserNameTv = (TextView) itemView.findViewById(R.id.tv_user_name);
            mVideoContainer = (FrameLayout) itemView.findViewById(R.id.fl_container);
            mUserHeadImg = (CircleImageView) itemView.findViewById(R.id.img_user_head);
            mUserSignal = (ImageView) itemView.findViewById(R.id.img_signal);
            mVolumePb = (ProgressBar) itemView.findViewById(R.id.pb_volume);
            mUserSpeak = (ImageView) itemView.findViewById(R.id.img_speak);
        }
    }

    public interface ListCallback {
        void onItemClick(int position);

        void onItemDoubleClick(int position);
    }
}