package com.tencent.qcloud.tuikit.tuichat.bean.message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.message.reply.CustomLinkReplyQuoteBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.reply.TUIReplyQuoteBean;

import java.util.HashMap;

/**
 * 用于存储显示的文字和要跳转的链接。
 * {@link com.tencent.qcloud.tuikit.tuichat.ui.view.message.viewholder.CustomLinkMessageHolder}
 */
public class CustomLinkMessageBean extends TUIMessageBean {

    private String text;
    private String link;

    /**
     * 用于生成在会话列表中的文字摘要
     */
    @Override
    public String onGetDisplayString() {
        return text;
    }

    /**
     * @param v2TIMMessage IMSDK 消息类
     * 用于实现对自定义消息的解析
     */
    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
        text = TUIChatService.getAppContext().getString(R.string.no_support_msg);
        link = "";
        String data = new String(v2TIMMessage.getCustomElem().getData());
        try {
            // new Gson().fromJson()：表示的是将指定的String类型的Json数据转换成指定类的对象
            // new Gson().toJson(Object src)：表示的是将指定的对象序列化为其等效的Json数据格式
            HashMap map = new Gson().fromJson(data, HashMap.class);
            if (map != null) {
                text = (String) map.get("text");
                link = (String) map.get("link");
            }
        } catch (JsonSyntaxException e) {

        }
        setExtra(text);
    }

    public void onProcessMessage(V2TIMCustomElem v2TIMCustomElem) {
        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
        text = TUIChatService.getAppContext().getString(R.string.no_support_msg);
        link = "";
        String data = new String(v2TIMCustomElem.getData());
        try {
            // new Gson().fromJson()：表示的是将指定的String类型的Json数据转换成指定类的对象
            // new Gson().toJson(Object src)：表示的是将指定的对象序列化为其等效的Json数据格式
            HashMap map = new Gson().fromJson(data, HashMap.class);
            if (map != null) {
                text = (String) map.get("text");
                link = (String) map.get("link");
            }
        } catch (JsonSyntaxException e) {

        }
        setExtra(text);
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    @Override
    public Class<? extends TUIReplyQuoteBean> getReplyQuoteBeanClass() {
        return CustomLinkReplyQuoteBean.class;
    }
}
