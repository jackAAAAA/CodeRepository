package com.tencent.qcloud.tuikit.tuichat.bean.message;

import android.text.TextUtils;

import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMElem;
import com.tencent.imsdk.v2.V2TIMFaceElem;
import com.tencent.imsdk.v2.V2TIMFileElem;
import com.tencent.imsdk.v2.V2TIMGroupTipsElem;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMLocationElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMergerElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMVideoElem;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.message.reply.TUIReplyQuoteBean;

import java.io.Serializable;
import java.util.List;

public abstract class TUIMessageBean implements Serializable {
     /**
      * 消息正常状态
      */
     public static final int MSG_STATUS_NORMAL = 0;
     /**
      * 消息发送中状态
      */
     public static final int MSG_STATUS_SENDING = 1;
     /**
      * 消息发送成功状态
      */
     public static final int MSG_STATUS_SEND_SUCCESS = 2;
     /**
      * 消息发送失败状态
      */
     public static final int MSG_STATUS_SEND_FAIL = 3;

     /**
      * 消息未读状态
      */
     public static final int MSG_STATUS_READ = 0x111;
     /**
      * 消息删除状态
      */
     public static final int MSG_STATUS_DELETE = 0x112;
     /**
      * 消息撤回状态
      */
     public static final int MSG_STATUS_REVOKE = 0x113;

     /**
      * 消息内容下载中状态
      */
     public static final int MSG_STATUS_DOWNLOADING = 4;
     /**
      * 消息内容未下载状态
      */
     public static final int MSG_STATUS_UN_DOWNLOAD = 5;
     /**
      * 消息内容已下载状态
      */
     public static final int MSG_STATUS_DOWNLOADED = 6;

     private V2TIMMessage v2TIMMessage;
     private long msgTime;
     private String extra;
     private boolean read;
     private boolean peerRead;
     private String id;
     private boolean isGroup;
     private int status;
     private int downloadStatus;

     public void setCommonAttribute(V2TIMMessage v2TIMMessage) {
          msgTime = System.currentTimeMillis() / 1000;
          this.v2TIMMessage = v2TIMMessage;

          if (v2TIMMessage == null) {
               return;
          }
          peerRead = v2TIMMessage.isPeerRead();
          read = v2TIMMessage.isRead();
          id = v2TIMMessage.getMsgID();
          isGroup = !TextUtils.isEmpty(v2TIMMessage.getGroupID());

          if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
               status = MSG_STATUS_REVOKE;
               if (isSelf()) {
                    extra = TUIChatService.getAppContext().getString(R.string.revoke_tips_you);
               } else if (isGroup) {
                    String message = TUIChatConstants.covert2HTMLString(getSender());
                    extra = message + TUIChatService.getAppContext().getString(R.string.revoke_tips);
               } else {
                    extra = TUIChatService.getAppContext().getString(R.string.revoke_tips_other);
               }
          } else {
               if (isSelf()) {
                    if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_SEND_FAIL) {
                         status = MSG_STATUS_SEND_FAIL;
                    } else if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC) {
                         status = MSG_STATUS_SEND_SUCCESS;
                    } else if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_SENDING) {
                         status = MSG_STATUS_SENDING;
                    }
               }
          }
     }

//     设置组合消息中的元素
     public void setCommonAttribute(V2TIMMessage v2TIMMessage, int i) {
          msgTime = System.currentTimeMillis() / 1000;
          this.v2TIMMessage = v2TIMMessage;

          if (v2TIMMessage == null) {
               return;
          }
          peerRead = v2TIMMessage.isPeerRead();
          read = v2TIMMessage.isRead();
//          i用来让组合消息的msgId变得不同
          id = v2TIMMessage.getMsgID() + i;
          isGroup = !TextUtils.isEmpty(v2TIMMessage.getGroupID());

          if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
               status = MSG_STATUS_REVOKE;
               if (isSelf()) {
                    extra = TUIChatService.getAppContext().getString(R.string.revoke_tips_you);
               } else if (isGroup) {
                    String message = TUIChatConstants.covert2HTMLString(getSender());
                    extra = message + TUIChatService.getAppContext().getString(R.string.revoke_tips);
               } else {
                    extra = TUIChatService.getAppContext().getString(R.string.revoke_tips_other);
               }
          } else {
               if (isSelf()) {
                    if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_SEND_FAIL) {
                         status = MSG_STATUS_SEND_FAIL;
                    } else if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC) {
                         status = MSG_STATUS_SEND_SUCCESS;
                    } else if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_SENDING) {
                         status = MSG_STATUS_SENDING;
                    }
               }
          }
     }

//     抽象类没有构造方法，无法实现返回TUIMessageBean的集合List
//     public List<TUIMessageBean> setCommonProcess(V2TIMElem elem) {
//          if (elem instanceof V2TIMTextElem) {
//               message = new TextMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getTextElem().getNextElem();
//          }
//          if (elem instanceof V2TIMImageElem) {
//               message = new ImageMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getImageElem().getNextElem();
//          }
//          if (elem instanceof V2TIMSoundElem) {
//               message = new SoundMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getSoundElem().getNextElem();
//          }
//          if (elem instanceof V2TIMVideoElem) {
//               message = new VideoMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getVideoElem().getNextElem();
//          }
//          if (elem instanceof V2TIMFileElem) {
//               message = new FileMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getFileElem().getNextElem();
//          }
//          if (elem instanceof V2TIMLocationElem) {
//               message = new LocationMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getLocationElem().getNextElem();
//          }
//          if (elem instanceof V2TIMFaceElem) {
//               message = new FaceMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getFaceElem().getNextElem();
//          }
//          if (elem instanceof V2TIMGroupTipsElem) {
//               message = new TipsMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getGroupTipsElem().getNextElem();
//          }
//          if (elem instanceof V2TIMMergerElem) {
//               message = new MergeMessageBean();
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getMergerElem().getNextElem();
//          }
//          if (elem instanceof V2TIMCustomElem) {
//               message = parseCustomMessage(v2TIMMessage);
////                setV2TIMMessageAttr(v2TIMMessage, message);
////                messageList.add(message);
//               elem = v2TIMMessage.getCustomElem().getNextElem();
//          }
//     }

     /**
      * 获取要显示在会话列表的消息摘要
      * @return 消息摘要
      */
     public abstract String onGetDisplayString();

     /**
      * V2TIMMessage 解析为 TUIMessageBean
      *
      * @param v2TIMMessage IMSDK 消息类
      */
     public abstract void onProcessMessage(V2TIMMessage v2TIMMessage);

     /**
      * 此处为组合消息的解析尝试，由于无法从V2TIMElem分别获取到V2TIMTextElem、V2TIMImageElem等子类Elem
      * 导致决定在父类TUIMessageBean当中定义10个指定的抽象方法，但是自认为太丑，决定不实现
      * V2TIMElem 解析为 TUIMessageBean
      * @param
      */
//     public abstract void onProcessMessage(V2TIMTextElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMImageElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);
//     public abstract void onProcessMessage(V2TIMElem v2TIMElem);

     public final long getMessageTime() {
          if (v2TIMMessage != null) {
               long timestamp = v2TIMMessage.getTimestamp();
               // 老版本 IMSDK 创建消息时间为0，返回客户端时间
               if (timestamp != 0) {
                    return timestamp;
               }
          }
          return msgTime;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getId() {
          return id;
     }


     public String getUserId() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getUserID();
          }
          return "";
     }

     public boolean isSelf() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.isSelf();
          }
          return true;
     }

     public String getSender() {
          String sender = null;
          if (v2TIMMessage != null) {
               sender = v2TIMMessage.getSender();
          }
          if (TextUtils.isEmpty(sender)) {
               sender = V2TIMManager.getInstance().getLoginUser();
          }
          return sender;
     }

     public V2TIMMessage getV2TIMMessage() {
          return v2TIMMessage;
     }

     public boolean isGroup() {
          return isGroup;
     }

     public void setGroup(boolean group) {
          isGroup = group;
     }

     public String getGroupId() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getGroupID();
          }
          return "";
     }

     public boolean isPeerRead() {
          return peerRead;
     }

     public boolean isRead() {
          return read;
     }

     public String getNameCard() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getNameCard();
          }
          return "";
     }

     public String getNickName() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getNickName();
          }
          return "";
     }

     public String getFriendRemark() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getFriendRemark();
          }
          return "";
     }

     public String getFaceUrl() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getFaceUrl();
          }
          return "";
     }

     public void setStatus(int status) {
          this.status = status;
     }

     public int getStatus() {
          return status;
     }

     public void setExtra(String extra) {
          this.extra = extra;
     }

     public String getExtra() {
          return extra;
     }

     public void setRead(boolean read) {
          this.read = read;
     }

     public void setPeerRead(boolean peerRead) {
          this.peerRead = peerRead;
     }


     public void setDownloadStatus(int downloadStatus) {
          this.downloadStatus = downloadStatus;
     }

     public int getDownloadStatus() {
          return downloadStatus;
     }

     public int getMsgType() {
          if (v2TIMMessage != null) {
               return v2TIMMessage.getElemType();
          } else {
               return V2TIMMessage.V2TIM_ELEM_TYPE_NONE;
          }
     }


     public void setV2TIMMessage(V2TIMMessage v2TIMMessage) {
          this.v2TIMMessage = v2TIMMessage;
          setCommonAttribute(v2TIMMessage);
          onProcessMessage(v2TIMMessage);
     }

     public Class<? extends TUIReplyQuoteBean> getReplyQuoteBeanClass() {
          return null;
     }
}
