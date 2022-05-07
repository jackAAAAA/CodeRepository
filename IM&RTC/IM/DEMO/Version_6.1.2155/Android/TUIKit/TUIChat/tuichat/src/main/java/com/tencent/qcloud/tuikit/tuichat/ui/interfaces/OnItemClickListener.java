package com.tencent.qcloud.tuikit.tuichat.ui.interfaces;

import android.view.View;

import com.tencent.qcloud.tuikit.tuichat.bean.message.TUIMessageBean;

public interface OnItemClickListener {
//    接口当中用default声明的默认方法可以在子类实现时不重写
    void onMessageLongClick(View view, int position, TUIMessageBean messageInfo);

    default void onMessageClick(View view, int position, TUIMessageBean messageInfo) {};

    void onUserIconClick(View view, int position, TUIMessageBean messageInfo);

    void onUserIconLongClick(View view, int position, TUIMessageBean messageInfo);

    void onReEditRevokeMessage(View view, int position, TUIMessageBean messageInfo);

    void onRecallClick(View view, int position, TUIMessageBean messageInfo);

    default void onReplyMessageClick(View view, int position, String originMsgId) {}

    default void onSendFailBtnClick(View view, int position, TUIMessageBean messageInfo) {};

    default void onTextSelected(View view, int position, TUIMessageBean messageInfo) {};
}
