package com.tencent.qcloud.tuikit.tuiconversation.util;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMElem;
import com.tencent.imsdk.v2.V2TIMFaceElem;
import com.tencent.imsdk.v2.V2TIMFileElem;
import com.tencent.imsdk.v2.V2TIMGroupAtInfo;
import com.tencent.imsdk.v2.V2TIMGroupTipsElem;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMLocationElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMergerElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMVideoElem;
import com.tencent.qcloud.tuicore.TUIThemeManager;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuichat.bean.message.FaceMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.FileMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.ImageMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.LocationMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.MergeMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.SoundMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TUIMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TextMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TipsMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.VideoMessageBean;
import com.tencent.qcloud.tuikit.tuiconversation.R;
import com.tencent.qcloud.tuikit.tuiconversation.TUIConversationService;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;
import com.tencent.qcloud.tuikit.tuiconversation.bean.DraftInfo;

import java.util.ArrayList;
import java.util.List;

public class ConversationUtils {
    private static final String TAG = ConversationUtils.class.getSimpleName();

    public static List<ConversationInfo> convertV2TIMConversationList(List<V2TIMConversation> conversationList) {
        List<ConversationInfo> conversationInfoList = new ArrayList<>();
        if (conversationList != null) {
            for (V2TIMConversation conversation : conversationList) {
                conversationInfoList.add(convertV2TIMConversation(conversation));
            }
        }
        return conversationInfoList;
    }

    /**
     * V2TIMConversation 转换为 ConversationInfo
     *
     * @param conversation V2TIMConversation
     * @return ConversationInfo
     */
    public static ConversationInfo convertV2TIMConversation(final V2TIMConversation conversation) {
        if (conversation == null) {
            return null;
        }
        TUIConversationLog.i(TAG, "TIMConversation2ConversationInfo id:" + conversation.getConversationID()
                + "|name:" + conversation.getShowName()
                + "|unreadNum:" + conversation.getUnreadCount());
        final ConversationInfo info = new ConversationInfo();
        info.setConversation(conversation);
        int type = conversation.getType();
        if (type != V2TIMConversation.V2TIM_C2C && type != V2TIMConversation.V2TIM_GROUP) {
            return null;
        }

        boolean isGroup = type == V2TIMConversation.V2TIM_GROUP;

        String draftText = conversation.getDraftText();
        if (!TextUtils.isEmpty(draftText)) {
            DraftInfo draftInfo = new DraftInfo();
            draftInfo.setDraftText(draftText);
            draftInfo.setDraftTime(conversation.getDraftTimestamp());
            info.setDraft(draftInfo);
        }

        V2TIMMessage v2TIMMessage = conversation.getLastMessage();

        if (v2TIMMessage == null) {
            long time = DateTimeUtil.getStringToDate("0001-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
            info.setLastMessageTime(time);
            Log.e(TAG, "convertV2TIMConversation: null");
        } else {
//            会话解析组合消息
            V2TIMElem elem = null;
            int msgType = v2TIMMessage.getElemType();

            switch (msgType) {
                case V2TIMMessage.V2TIM_ELEM_TYPE_TEXT:
                    elem = v2TIMMessage.getTextElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE:
                    elem = v2TIMMessage.getImageElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_SOUND:
                    elem = v2TIMMessage.getSoundElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO:
                    elem = v2TIMMessage.getVideoElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_FILE:
                    elem = v2TIMMessage.getFileElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_LOCATION:
                    elem = v2TIMMessage.getLocationElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_FACE:
                    elem = v2TIMMessage.getFaceElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS:
                    elem = v2TIMMessage.getGroupTipsElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_MERGER:
                    elem = v2TIMMessage.getMergerElem();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM:
                    elem = v2TIMMessage.getCustomElem();
                    break;
                default:
                    break;
            }

            if (elem != null) {
                while (elem.getNextElem() != null) {
                    elem = elem.getNextElem();
                }
            }

            TUIMessageBean message = null;
            if (elem instanceof V2TIMTextElem) {
                message = new TextMessageBean();
                TextMessageBean text = (TextMessageBean) message;
                text.onProcessMessage((V2TIMTextElem) elem);
//            创建V2TIMETextMessage的标准写法 V2TIMManager.getMessageManager().createTextMessage(text.getText())
                v2TIMMessage = V2TIMManager.getMessageManager().createTextMessage(text.getText());
            }
            if (elem instanceof V2TIMImageElem) {
                message = new ImageMessageBean();
                ImageMessageBean image = (ImageMessageBean) message;
                image.onProcessMessage((V2TIMImageElem) elem);
                v2TIMMessage = V2TIMManager.getMessageManager().createImageMessage(image.getPath());
            }
            if (elem instanceof V2TIMSoundElem) {
                message = new SoundMessageBean();
                SoundMessageBean sound = (SoundMessageBean) message;
                sound.onProcessMessage((V2TIMSoundElem) elem);
                v2TIMMessage = V2TIMManager.getMessageManager().createSoundMessage(sound.getDataPath(), sound.getDuration());
            }
            if (elem instanceof V2TIMVideoElem) {
                message = new VideoMessageBean();
                VideoMessageBean video = (VideoMessageBean) message;
                video.onProcessMessage((V2TIMVideoElem) elem);
                v2TIMMessage = V2TIMManager.getMessageManager().createVideoMessage(video.getVideoUUID(), "mp4",
                        video.getDuration() / 1000, video.getSnapshotUUID());
            }
            if (elem instanceof V2TIMFileElem) {
                message = new FileMessageBean();
                FileMessageBean file = (FileMessageBean) message;
                file.onProcessMessage((V2TIMFileElem) elem);
                v2TIMMessage = V2TIMManager.getMessageManager().createFileMessage(file.getPath(), file.getFileName());
            }
            if (elem instanceof V2TIMFaceElem) {
                message = new FaceMessageBean();
                FaceMessageBean face = (FaceMessageBean) message;
                face.onProcessMessage((V2TIMFaceElem) elem);
                v2TIMMessage = V2TIMManager.getMessageManager().createFaceMessage(face.getIndex(), face.getData());
            }

            info.setLastMessageTime(v2TIMMessage.getTimestamp());
            info.setLastMessage(v2TIMMessage);
            Log.e(TAG, "convertV2TIMConversation: " + v2TIMMessage.getTimestamp());
        }

        int atInfoType = getAtInfoType(conversation);
        switch (atInfoType) {
            case V2TIMGroupAtInfo.TIM_AT_ME:
                info.setAtInfoText(TUIConversationService.getAppContext().getString(R.string.ui_at_me));
                break;
            case V2TIMGroupAtInfo.TIM_AT_ALL:
                info.setAtInfoText(TUIConversationService.getAppContext().getString(R.string.ui_at_all));
                break;
            case V2TIMGroupAtInfo.TIM_AT_ALL_AT_ME:
                info.setAtInfoText(TUIConversationService.getAppContext().getString(R.string.ui_at_all_me));
                break;
            default:
                info.setAtInfoText("");
                break;

        }

        info.setTitle(conversation.getShowName());
        List<Object> faceList = new ArrayList<>();
        if (isGroup) {
            if (!TextUtils.isEmpty(conversation.getFaceUrl())) {
                faceList.add(conversation.getFaceUrl());
            }
        } else {
            if (TextUtils.isEmpty(conversation.getFaceUrl())) {
                faceList.add(TUIThemeManager.getAttrResId(TUIConversationService.getAppContext(), R.attr.core_default_user_icon));
            } else {
                faceList.add(conversation.getFaceUrl());
            }
        }
        info.setIconUrlList(faceList);

        if (isGroup) {
            info.setId(conversation.getGroupID());
            info.setGroupType(conversation.getGroupType());
        } else {
            info.setId(conversation.getUserID());
        }

        if (V2TIMManager.GROUP_TYPE_MEETING.equals(conversation.getGroupType())) {
            info.setShowDisturbIcon(false);
        } else {
            info.setShowDisturbIcon(conversation.getRecvOpt() == V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE);
        }
        info.setConversationId(conversation.getConversationID());
        info.setGroup(isGroup);
        // AVChatRoom 不支持未读数。
        if (!V2TIMManager.GROUP_TYPE_AVCHATROOM.equals(conversation.getGroupType())) {
            info.setUnRead(conversation.getUnreadCount());
        }
        info.setTop(conversation.isPinned());
        info.setOrderKey(conversation.getOrderKey());
        return info;
    }

    private static int getAtInfoType(V2TIMConversation conversation) {
        int atInfoType = 0;
        boolean atMe = false;
        boolean atAll = false;

        List<V2TIMGroupAtInfo> atInfoList = conversation.getGroupAtInfoList();

        if (atInfoList == null || atInfoList.isEmpty()) {
            return V2TIMGroupAtInfo.TIM_AT_UNKNOWN;
        }

        for (V2TIMGroupAtInfo atInfo : atInfoList) {
            if (atInfo.getAtType() == V2TIMGroupAtInfo.TIM_AT_ME) {
                atMe = true;
                continue;
            }
            if (atInfo.getAtType() == V2TIMGroupAtInfo.TIM_AT_ALL) {
                atAll = true;
                continue;
            }
            if (atInfo.getAtType() == V2TIMGroupAtInfo.TIM_AT_ALL_AT_ME) {
                atMe = true;
                atAll = true;
                continue;
            }
        }

        if (atAll && atMe) {
            atInfoType = V2TIMGroupAtInfo.TIM_AT_ALL_AT_ME;
        } else if (atAll) {
            atInfoType = V2TIMGroupAtInfo.TIM_AT_ALL;
        } else if (atMe) {
            atInfoType = V2TIMGroupAtInfo.TIM_AT_ME;
        } else {
            atInfoType = V2TIMGroupAtInfo.TIM_AT_UNKNOWN;
        }

        return atInfoType;
    }

    public static boolean isNeedUpdate(ConversationInfo conversationInfo) {
        return V2TIMManager.GROUP_TYPE_AVCHATROOM.equals(conversationInfo.getGroupType());
    }

}
