package com.tencent.qcloud.tuikit.tuichat.bean;

import com.tencent.qcloud.tuikit.tuichat.bean.message.TUIMessageBean;

public class ReplyPreviewBean {
    private String messageID;
    private String messageAbstract;
    private String messageSender;
    private int messageType;

    private int version = 1;

    private transient TUIMessageBean originalMessageBean;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageAbstract() {
        return messageAbstract;
    }

    public void setMessageAbstract(String messageAbstract) {
        this.messageAbstract = messageAbstract;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public TUIMessageBean getOriginalMessageBean() {
        return originalMessageBean;
    }

    public void setOriginalMessageBean(TUIMessageBean originalMessageBean) {
        this.originalMessageBean = originalMessageBean;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}
