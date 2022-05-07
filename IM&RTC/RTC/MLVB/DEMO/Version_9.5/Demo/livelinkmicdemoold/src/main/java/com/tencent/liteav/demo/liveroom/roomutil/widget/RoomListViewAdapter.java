package com.tencent.liteav.demo.liveroom.roomutil.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tencent.liteav.demo.liveroom.R;
import com.tencent.liteav.demo.liveroom.roomutil.commondef.RoomInfo;
import com.tencent.liteav.demo.liveroom.roomutil.misc.NameGenerator;

import java.util.List;

/**
 * Created by jac on 2017/11/7.
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 */

public class RoomListViewAdapter extends BaseAdapter {

    public static final int ROOM_TYPE_LIVE      = 1;
    public static final int ROOM_TYPE_MULTI     = 2;
    public static final int ROOM_TYPE_DOUBLE    = 3;
    public static final int ROOM_TYPE_ANSWER    = 4;

    private List<RoomInfo> dataList;
    private int roomType = ROOM_TYPE_LIVE;

    private class ConvertView extends FrameLayout {
        private TextView nameView;
        private TextView nbView;
        private TextView idView;

        public ConvertView(Context context, int type) {
            this(context, null, type);
        }

        public ConvertView(@NonNull Context context, @Nullable AttributeSet attrs, int type) {
            super(context, attrs);

            inflate(context, R.layout.mlvb_rtmproom_roominfo_item, this);
            setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

            nameView = ((TextView) findViewById(R.id.mlvb_room_name_textview));
            nbView = ((TextView) findViewById(R.id.room_online_nb_textview));
            idView = ((TextView) findViewById(R.id.room_id_textview));

            if (type == ROOM_TYPE_ANSWER) {
                ((TextView) findViewById(R.id.mlvb_tv_room_name_title)).setText(getResources().getString(R.string.mlvb_answer_room_name));
                ((TextView) findViewById(R.id.room_id_title)).setText(getResources().getString(R.string.mlvb_answer_room_id));
            } else if (type == ROOM_TYPE_LIVE) {
                ((TextView) findViewById(R.id.mlvb_tv_room_name_title)).setText(getResources().getString(R.string.mlvb_live_room_name));
                ((TextView) findViewById(R.id.room_id_title)).setText(getResources().getString(R.string.mlvb_live_room_id));
            } else {
                ((TextView) findViewById(R.id.mlvb_tv_room_name_title)).setText(getResources().getString(R.string.mlvb_chat_name));
                ((TextView) findViewById(R.id.room_id_title)).setText(getResources().getString(R.string.mlvb_chat_id));
            }
        }

        public void setRoomName(String name) {
            name = NameGenerator.replaceNonPrintChar(name, 16, "...", false);
            nameView.setText(name);
        }

        public void setMemberCount(int count) {
            String format = getResources().getString(R.string.mlvb_number_of_people, count);
            nbView.setText(format);
        }

        public void setId(String id) {
            id = NameGenerator.replaceNonPrintChar(id, 30, "...", true);
            this.idView.setText(id);
        }
    }

    public void setDataList(List<RoomInfo> dataList) {
        this.dataList = dataList;
    }

    public void setRoomType(int type) {
        roomType = type;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConvertView view = (ConvertView) convertView;
        if (view == null) {
            view = new ConvertView(parent.getContext(), roomType);
        }
        RoomInfo roomInfo = dataList.get(position);
        view.setMemberCount(roomInfo.pushers == null ? 0 : roomInfo.pushers.size());
        view.setRoomName(roomInfo.roomInfo);
        view.setId(roomInfo.roomID);
        return view;
    }


    /**
     * Created by jac on 2017/11/16.
     * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
     */

    public static class ChatMessageAdapter extends BaseAdapter {

        private List<TextChatMsg> mTextChatMsgList;
        private Context mContext;

        public ChatMessageAdapter(Context mContext, List<TextChatMsg> textChatMsgList) {
            this.mTextChatMsgList = textChatMsgList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mTextChatMsgList.size();
        }

        @Override
        public Object getItem(int position) {
            return mTextChatMsgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            TextChatMsg textChatMsg = mTextChatMsgList.get(position);

            if (textChatMsg.alignment() == TextChatMsg.Alignment.LEFT) {
                if (view == null || view.getTag() != TextChatMsg.Alignment.LEFT)
                    view = LayoutInflater.from(mContext).inflate(R.layout.mlvb_rtcroom_chatview_item_left, null);
                view.setTag(TextChatMsg.Alignment.LEFT);
            } else {
                if (view == null || view.getTag() != TextChatMsg.Alignment.RIGHT)
                    view = LayoutInflater.from(mContext).inflate(R.layout.mlvb_rtcroom_chatview_item_right, null);
                view.setTag(TextChatMsg.Alignment.RIGHT);
            }

            ((TextView) view.findViewById(R.id.mlvb_rtcroom_chat_msg_user_and_time_tv))
                    .setText(String.format("%s %s", textChatMsg.getName(), textChatMsg.getTime()));
            ((TextView) view.findViewById(R.id.mlvb_tv_rtc_room_chat_msg_msg))
                    .setText(textChatMsg.getMsg());

            return view;
        }
    }

    /**
     * Created by jac on 2017/11/17.
     * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
     */

    public static class TextChatMsg {

        public static enum Alignment {
            LEFT    (0),
            RIGHT   (1),
            CENTER  (2);
            final int alignment;

            Alignment(int alignment) {
                this.alignment = alignment;
            }
        }

        private String name;
        private String time;
        private String msg;
        private Alignment alignment;

        public TextChatMsg() {
        }

        public TextChatMsg(String name, String time, String msg, Alignment alignment) {
            this.name = name;
            this.time = time;
            this.msg = msg;
            this.alignment = alignment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Alignment alignment() {
            return alignment;
        }

        public void setAlignment(Alignment alignment) {
            this.alignment = alignment;
        }
    }
}
