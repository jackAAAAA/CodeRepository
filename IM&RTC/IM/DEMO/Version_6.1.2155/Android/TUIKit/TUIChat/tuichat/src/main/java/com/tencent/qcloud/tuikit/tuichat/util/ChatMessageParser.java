package com.tencent.qcloud.tuikit.tuichat.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.imsdk.message.Message;
import com.tencent.imsdk.message.MessageBaseElement;
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
import com.tencent.imsdk.v2.V2TIMSignalingInfo;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMVideoElem;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.CallModel;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageCustom;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageTyping;
import com.tencent.qcloud.tuikit.tuichat.bean.ReplyPreviewBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.CallingMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.CustomLinkMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.FaceMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.FileMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.ImageMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.LocationMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.MergeMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.ReplyMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.SoundMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TUIMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TextMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TipsMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.VideoMessageBean;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatMessageParser {
    private static final String TAG = ChatMessageParser.class.getSimpleName();

    //    ????????????V2TIMMessage
    public static TUIMessageBean parseMessage(V2TIMMessage v2TIMMessage) {
        return parseMessage(v2TIMMessage, false);
    }

    public static TUIMessageBean parseMessage(V2TIMMessage v2TIMMessage, boolean isIgnoreReply) {
        if (v2TIMMessage == null) {
            return null;
        }
        if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_HAS_DELETED || v2TIMMessage.getElemType() == V2TIMMessage.V2TIM_ELEM_TYPE_NONE) {
            return null;
        }
        TUIMessageBean message = null;
        if (!isIgnoreReply) {
            message = parseReplyMessage(v2TIMMessage);
        }
        if (message == null) {
            int msgType = v2TIMMessage.getElemType();
            switch (msgType) {
                case V2TIMMessage.V2TIM_ELEM_TYPE_TEXT:
                    message = new TextMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE:
                    message = new ImageMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_SOUND:
                    message = new SoundMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO:
                    message = new VideoMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_FILE:
                    message = new FileMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_LOCATION:
                    message = new LocationMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_FACE:
                    message = new FaceMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS:
                    message = new TipsMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_MERGER:
                    message = new MergeMessageBean();
                    break;
                case V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM:
                    message = parseCustomMessage(v2TIMMessage);
                    break;
                default:
                    break;
            }
        }
        setV2TIMMessageAttr(v2TIMMessage, message);
        return message;
    }

    //   ??????List<V2TIMMessage>
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<TUIMessageBean> parseMessageList(V2TIMMessage v2TIMMessage) {
        if (v2TIMMessage == null) {
            return null;
        }
        if (v2TIMMessage.getStatus() == V2TIMMessage.V2TIM_MSG_STATUS_HAS_DELETED || v2TIMMessage.getElemType() == V2TIMMessage.V2TIM_ELEM_TYPE_NONE) {
            return null;
        }
//        ?????????????????????????????????
//        if (!isIgnoreReply) {
//            message = parseReplyMessage(v2TIMMessage);
//       }
        List<TUIMessageBean> messageList = new ArrayList<>();
        TUIMessageBean message = null;
        V2TIMElem elem = null;

        /**
         * ???????????????switch/case????????? + do while{}:
         * ????????????????????????????????????Elem?????????????????????????????????Elem?????????onProcess????????????????????????????????????????????????
         *
         */
        int msgType = v2TIMMessage.getElemType();
        ToastUtil.toastLongMessage("???????????????????????????" + msgType);
        Log.e("???????????????????????????", "" + msgType);

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

//        Java?????? ????????????????????????????????????
//        ??????????????????Message -> List< MessageBaseElement > getMessageBaseElements -> ???TUIMessageBean???????????????????????????
//        try {
//            Class v2TIMElemClass = Class.forName("com.tencent.imsdk.v2");
//            Constructor v2TIMElemConstructor = v2TIMElemClass.getDeclaredConstructor();
//            v2TIMElemConstructor.setAccessible(true);
//            Object instance = v2TIMElemConstructor.newInstance();
//            Method getMessageMethod = v2TIMElemClass.getDeclaredMethod("getMessage");
//            getMessageMethod.setAccessible(true);
//            Log.e(TAG, "??????????????????" + (String) getMessageMethod.invoke(instance));
//        } catch (ClassNotFoundException | NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        int i = 0;
        do {
            if (elem instanceof V2TIMTextElem) {
                message = new TextMessageBean();
//                Android????????????msgid??????????????????message???
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                TextMessageBean text = (TextMessageBean) message;
                text.onProcessMessage((V2TIMTextElem) elem);
                messageList.add(text);
            }
            if (elem instanceof V2TIMImageElem) {
                message = new ImageMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                ImageMessageBean image = (ImageMessageBean) message;
                image.onProcessMessage((V2TIMImageElem) elem);
                messageList.add(image);
            }
            if (elem instanceof V2TIMSoundElem) {
                message = new SoundMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                SoundMessageBean sound = (SoundMessageBean) message;
                sound.onProcessMessage((V2TIMSoundElem) elem);
                messageList.add(sound);
            }
            if (elem instanceof V2TIMVideoElem) {
                message = new VideoMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                VideoMessageBean video = (VideoMessageBean) message;
                video.onProcessMessage((V2TIMVideoElem) elem);
                messageList.add(video);
            }
            if (elem instanceof V2TIMFileElem) {
                message = new FileMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                FileMessageBean file = (FileMessageBean) message;
                file.onProcessMessage((V2TIMFileElem) elem);
                messageList.add(file);
            }
            if (elem instanceof V2TIMLocationElem) {
                message = new LocationMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                LocationMessageBean location = (LocationMessageBean) message;
                location.onProcessMessage((V2TIMLocationElem) elem);
                messageList.add(location);
            }
            if (elem instanceof V2TIMFaceElem) {
                message = new FaceMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                FaceMessageBean face = (FaceMessageBean) message;
                face.onProcessMessage((V2TIMFaceElem) elem);
                messageList.add(face);
            }
            if (elem instanceof V2TIMGroupTipsElem) {
                message = new TipsMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                TipsMessageBean tips = (TipsMessageBean) message;
                tips.onProcessMessage((V2TIMGroupTipsElem) elem);
                messageList.add(tips);
            }
            if (elem instanceof V2TIMMergerElem) {
                message = new MergeMessageBean();
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
                MergeMessageBean merge = (MergeMessageBean) message;
                merge.onProcessMessage((V2TIMMergerElem) elem);
                messageList.add(merge);
            }
            if (elem instanceof V2TIMCustomElem) {
                message = parseCustomMessage(v2TIMMessage, (V2TIMCustomElem) elem);
                setV2TIMMessageAttr(v2TIMMessage, i++, message);
//                Android??????????????????????????????????????????????????????
                messageList.add(message);
            }
            elem = elem.getNextElem();
        } while (elem != null);

        return messageList;
    }

    //    ??????TUIMessageBean?????????
    private static void setV2TIMMessageAttr(V2TIMMessage v2TIMMessage, TUIMessageBean message) {
        if (message != null) {
            message.setCommonAttribute(v2TIMMessage);
            message.onProcessMessage(v2TIMMessage);
        }
    }

    private static void setV2TIMMessageAttr(V2TIMMessage v2TIMMessage, int i, TUIMessageBean message) {
        if (message != null) {
            message.setCommonAttribute(v2TIMMessage, i);
        }
    }

    private static TUIMessageBean parseCustomMessage(V2TIMMessage v2TIMMessage) {
        TUIMessageBean messageBean = parseCallingMessage(v2TIMMessage);
        if (messageBean == null) {
            messageBean = parseGroupCreateMessage(v2TIMMessage);
        }
        if (messageBean == null) {
            messageBean = parseCustomMessageFromMap(v2TIMMessage);
        }
        return messageBean;
    }

//    ?????????????????????????????????Tips????????????????????????
    private static TUIMessageBean parseCustomMessage(V2TIMMessage v2TIMMessage, V2TIMCustomElem v2TIMCustomElem) {
        TUIMessageBean messageBean = parseCallingMessage(v2TIMMessage, v2TIMCustomElem);
        if (messageBean == null) {
            messageBean = parseGroupCreateMessage(v2TIMMessage, v2TIMCustomElem);
        }
        if (messageBean == null) {
            messageBean = parseCustomMessageFromMap(v2TIMMessage);
        }
        return messageBean;
    }

    private static TUIMessageBean parseCustomMessageFromMap(V2TIMMessage v2TIMMessage) {
        String businessId = getCustomBusinessId(v2TIMMessage);
        Class<? extends TUIMessageBean> messageBeanClazz = TUIChatService.getInstance().getMessageBeanClass(businessId);
        if (messageBeanClazz != null) {
            try {
                return messageBeanClazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static TUIMessageBean parseReplyMessage(V2TIMMessage v2TIMMessage) {
        String cloudCustomData = v2TIMMessage.getCloudCustomData();
        if (TextUtils.isEmpty(cloudCustomData)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            HashMap replyHashMap = gson.fromJson(cloudCustomData, HashMap.class);
            if (replyHashMap != null) {
                Object replyContentObj = replyHashMap.get("messageReply");
                ReplyPreviewBean replyPreviewBean = null;
                if (replyContentObj instanceof Map) {
                    replyPreviewBean = gson.fromJson(gson.toJson(replyContentObj), ReplyPreviewBean.class);
                }
                if (replyPreviewBean != null) {
                    return new ReplyMessageBean(replyPreviewBean);
                }
            }
        } catch (JsonSyntaxException e) {
            TUIChatLog.e(TAG, " getCustomJsonMap error ");
        }
        return null;
    }

    public static boolean isTyping(byte[] data) {
        try {
            String str = new String(data, "UTF-8");
            MessageTyping typing = new Gson().fromJson(str, MessageTyping.class);
            if (typing != null
                    && typing.userAction == MessageTyping.TYPE_TYPING
                    && TextUtils.equals(typing.actionParam, MessageTyping.EDIT_START)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            TUIChatLog.e(TAG, "parse json error");
        }
        return false;
    }

    private static String getCustomBusinessId(V2TIMMessage v2TIMMessage) {
        byte[] customData = v2TIMMessage.getCustomElem().getData();
        String data = new String(customData);

        Gson gson = new Gson();
        HashMap customJsonMap = null;
        try {
            customJsonMap = gson.fromJson(data, HashMap.class);
        } catch (JsonSyntaxException e) {
            TUIChatLog.e(TAG, " getCustomJsonMap error ");
        }
        String businessId = null;
        Object businessIdObj = null;
        if (customJsonMap != null) {
            businessIdObj = customJsonMap.get(TUIConstants.Message.CUSTOM_BUSINESS_ID_KEY);
        }
        if (businessIdObj instanceof String) {
            businessId = (String) businessIdObj;
        }
        return businessId;
    }

    /**
     * ??? IMSDK ????????? bean ??????????????? TUIKit ?????????bean??????
     *
     * @param v2TIMMessageList IMSDK ????????? bean ??????
     * @return ???????????? TUIKit bean ??????
     */
    public static List<TUIMessageBean> parseMessageList(List<V2TIMMessage> v2TIMMessageList) {
        if (v2TIMMessageList == null) {
            return null;
        }
        List<TUIMessageBean> messageList = new ArrayList<>();
        for (int i = 0; i < v2TIMMessageList.size(); i++) {
            V2TIMMessage timMessage = v2TIMMessageList.get(i);
            TUIMessageBean message = parseMessage(timMessage);
            if (message != null) {
                messageList.add(message);
            }
        }
        return messageList;
    }

    private static TUIMessageBean parseCallingMessage(V2TIMMessage v2TIMMessage) {
        V2TIMCustomElem customElem = v2TIMMessage.getCustomElem();
        if (customElem == null || customElem.getData() == null) {
            return null;
        }
        String data = new String(customElem.getData());

        Gson gson = new Gson();
        HashMap customJsonMap = null;
        try {
            customJsonMap = gson.fromJson(data, HashMap.class);
        } catch (JsonSyntaxException e) {
            TUIChatLog.e(TAG, " getCustomJsonMap error ");
        }
        String businessId = null;
        Double businessIdForTimeout = 0.0;
        Object businessIdObj = null;
        Object dataInData = null;
        if (customJsonMap != null) {
            businessIdObj = customJsonMap.get(TUIConstants.Message.CUSTOM_BUSINESS_ID_KEY);
            //dataInData = customJsonMap.get(TUIConstants.Message.CALLING_MESSAGE_DATA_KEY);
        }
        if (businessIdObj instanceof String) {
            businessId = (String) businessIdObj;
        } else if (businessIdObj instanceof Double) {
            businessIdForTimeout = (Double) businessIdObj;
        }

        /*HashMap dataJsonMap = null;
        Object typeObject = null;
        int callType = 0;
        if (dataInData instanceof String) {
            String dataString = (String) dataInData;
            Gson gsonData = new Gson();
            try {
                dataJsonMap = gsonData.fromJson(dataString, HashMap.class);
            } catch (JsonSyntaxException e) {
                TUIChatLog.e(TAG, " getCustomJsonMap error ");
            }
        }
        if (dataJsonMap != null) {
            typeObject = dataJsonMap.get(TUIConstants.Message.CALLING_TYPE_KEY);
        }

        if (typeObject instanceof Integer) {
            callType = (Integer) typeObject;
        }*/

        V2TIMSignalingInfo signalingInfo = V2TIMManager.getSignalingManager().getSignalingInfo(v2TIMMessage);

        // ????????????
        if (signalingInfo != null) { // ????????????
            try {
                HashMap signalDataMap = gson.fromJson(signalingInfo.getData(), HashMap.class);
                if (signalDataMap != null) {
                    businessIdObj = signalDataMap.get(TUIConstants.Message.CUSTOM_BUSINESS_ID_KEY);
                }
            } catch (JsonSyntaxException e) {
                TUIChatLog.e(TAG, " get signalingInfoCustomJsonMap error ");
            }
            if (businessIdObj instanceof String) {
                businessId = (String) businessIdObj;
            } else if (businessIdObj instanceof Double) {
                businessIdForTimeout = (Double) businessIdObj;
            }
            // ????????????????????????
            if (TextUtils.equals(businessId, TUIConstants.TUICalling.CUSTOM_MESSAGE_BUSINESS_ID) ||
                    Math.abs(businessIdForTimeout - TUIConstants.TUICalling.CALL_TIMEOUT_BUSINESS_ID) < 0.000001) {
                return getCallingMessage(v2TIMMessage);
            } else {
                return null;
            }
        }
        return null;
    }

    private static TUIMessageBean parseCallingMessage(V2TIMMessage v2TIMMessage, V2TIMCustomElem customElem) {
        if (customElem == null || customElem.getData() == null) {
            return null;
        }
        String data = new String(customElem.getData());

        Gson gson = new Gson();
        HashMap customJsonMap = null;
        try {
            customJsonMap = gson.fromJson(data, HashMap.class);
        } catch (JsonSyntaxException e) {
            TUIChatLog.e(TAG, " getCustomJsonMap error ");
        }
        String businessId = null;
        Double businessIdForTimeout = 0.0;
        Object businessIdObj = null;
        Object dataInData = null;
        if (customJsonMap != null) {
            businessIdObj = customJsonMap.get(TUIConstants.Message.CUSTOM_BUSINESS_ID_KEY);
            //dataInData = customJsonMap.get(TUIConstants.Message.CALLING_MESSAGE_DATA_KEY);
        }
        if (businessIdObj instanceof String) {
            businessId = (String) businessIdObj;
        } else if (businessIdObj instanceof Double) {
            businessIdForTimeout = (Double) businessIdObj;
        }

        /*HashMap dataJsonMap = null;
        Object typeObject = null;
        int callType = 0;
        if (dataInData instanceof String) {
            String dataString = (String) dataInData;
            Gson gsonData = new Gson();
            try {
                dataJsonMap = gsonData.fromJson(dataString, HashMap.class);
            } catch (JsonSyntaxException e) {
                TUIChatLog.e(TAG, " getCustomJsonMap error ");
            }
        }
        if (dataJsonMap != null) {
            typeObject = dataJsonMap.get(TUIConstants.Message.CALLING_TYPE_KEY);
        }

        if (typeObject instanceof Integer) {
            callType = (Integer) typeObject;
        }*/

        V2TIMSignalingInfo signalingInfo = V2TIMManager.getSignalingManager().getSignalingInfo(v2TIMMessage);

        // ????????????
        if (signalingInfo != null) { // ????????????
            try {
                HashMap signalDataMap = gson.fromJson(signalingInfo.getData(), HashMap.class);
                if (signalDataMap != null) {
                    businessIdObj = signalDataMap.get(TUIConstants.Message.CUSTOM_BUSINESS_ID_KEY);
                }
            } catch (JsonSyntaxException e) {
                TUIChatLog.e(TAG, " get signalingInfoCustomJsonMap error ");
            }
            if (businessIdObj instanceof String) {
                businessId = (String) businessIdObj;
            } else if (businessIdObj instanceof Double) {
                businessIdForTimeout = (Double) businessIdObj;
            }
            // ????????????????????????
            if (TextUtils.equals(businessId, TUIConstants.TUICalling.CUSTOM_MESSAGE_BUSINESS_ID) ||
                    Math.abs(businessIdForTimeout - TUIConstants.TUICalling.CALL_TIMEOUT_BUSINESS_ID) < 0.000001) {
                return getCallingMessage(v2TIMMessage);
            } else {
                return null;
            }
        }
        return null;
    }

    private static TUIMessageBean getCallingMessage(V2TIMMessage timMessage) {
        TUIMessageBean message;
        boolean isGroup = !TextUtils.isEmpty(timMessage.getGroupID());

        CallModel callModel = CallModel.convert2VideoCallData(timMessage);
        if (callModel == null) {
            return null;
        }
        String senderShowName = getDisplayName(timMessage);
        String content = "";
        Context context = TUIChatService.getAppContext();
        switch (callModel.action) {
            case CallModel.VIDEO_CALL_ACTION_DIALING:
                content = isGroup ? ("\"" + senderShowName + "\"" +
                        context.getString(R.string.start_group_call)) : (context.getString(R.string.start_call));
                break;
            case CallModel.VIDEO_CALL_ACTION_SPONSOR_CANCEL:
                content = isGroup ? context.getString(R.string.cancle_group_call) : context.getString(R.string.cancle_call);
                break;
            case CallModel.VIDEO_CALL_ACTION_LINE_BUSY:
                content = isGroup ? ("\"" + senderShowName + "\"" +
                        context.getString(R.string.line_busy)) : context.getString(R.string.other_line_busy);
                break;
            case CallModel.VIDEO_CALL_ACTION_REJECT:
                content = isGroup ? ("\"" + senderShowName + "\"" +
                        context.getString(R.string.reject_group_calls)) : context.getString(R.string.reject_calls);
                break;
            case CallModel.VIDEO_CALL_ACTION_SPONSOR_TIMEOUT:
                if (isGroup && callModel.invitedList != null && callModel.invitedList.size() == 1
                        && callModel.invitedList.get(0).equals(timMessage.getSender())) {
                    content = "\"" + senderShowName + "\"" + context.getString(R.string.no_response_call);
                } else {
                    StringBuilder inviteeShowStringBuilder = new StringBuilder();
                    if (callModel.invitedList != null && callModel.invitedList.size() > 0) {
                        for (String invitee : callModel.invitedList) {
                            inviteeShowStringBuilder.append(invitee).append("???");
                        }
                        if (inviteeShowStringBuilder.length() > 0) {
                            inviteeShowStringBuilder.delete(inviteeShowStringBuilder.length() - 1, inviteeShowStringBuilder.length());
                        }
                    }
                    content = isGroup ? ("\"" + inviteeShowStringBuilder.toString() + "\""
                            + context.getString(R.string.no_response_call)) : context.getString(R.string.no_response_call);
                }
                break;
            case CallModel.VIDEO_CALL_ACTION_ACCEPT:
                content = isGroup ? ("\"" + senderShowName + "\"" +
                        context.getString(R.string.accept_call)) : context.getString(R.string.accept_call);
                break;
            case CallModel.VIDEO_CALL_ACTION_HANGUP:
                content = isGroup ? context.getString(R.string.stop_group_call) :
                        context.getString(R.string.stop_call_tip) + DateTimeUtil.formatSecondsTo00(callModel.duration);
                break;
            default:
                content = context.getString(R.string.invalid_command);
                break;
        }
        if (isGroup) {
            TipsMessageBean tipsMessageBean = new TipsMessageBean();
            tipsMessageBean.setCommonAttribute(timMessage);
            tipsMessageBean.setText(content);
            tipsMessageBean.setExtra(content);
            message = tipsMessageBean;
        } else {
            CallingMessageBean callingMessageBean = new CallingMessageBean();
            callingMessageBean.setCommonAttribute(timMessage);
            callingMessageBean.setText(content);
            callingMessageBean.setExtra(content);
            callingMessageBean.setCallType(callModel.callType);
            message = callingMessageBean;
        }
        return message;
    }

    private static TUIMessageBean parseGroupCreateMessage(V2TIMMessage v2TIMMessage) {
        V2TIMCustomElem customElem = v2TIMMessage.getCustomElem();
        String data = new String(customElem.getData());
        Gson gson = new Gson();

        if (data.equals(MessageCustom.BUSINESS_ID_GROUP_CREATE)) {
            // ??????4.7??????????????? tuikit
            TipsMessageBean messageBean = new TipsMessageBean();
            messageBean.setCommonAttribute(v2TIMMessage);
            messageBean.setTipType(TipsMessageBean.MSG_TYPE_GROUP_CREATE);
            String message = TUIChatConstants.covert2HTMLString(getDisplayName(v2TIMMessage)) + TUIChatService.getAppContext().getString(R.string.create_group);
            messageBean.setText(message);
            messageBean.setExtra(message);
            return messageBean;
        } else {
            if (isTyping(customElem.getData())) {
                // ?????????????????????????????????????????????????????????
                return null;
            }
            TUIChatLog.i(TAG, "custom data:" + data);
            MessageCustom messageCustom = null;
            try {
                messageCustom = gson.fromJson(data, MessageCustom.class);
                if (!TextUtils.isEmpty(messageCustom.businessID) && messageCustom.businessID.equals(MessageCustom.BUSINESS_ID_GROUP_CREATE)) {
                    TipsMessageBean messageBean = new TipsMessageBean();
                    messageBean.setCommonAttribute(v2TIMMessage);
                    messageBean.setTipType(TipsMessageBean.MSG_TYPE_GROUP_CREATE);
                    String message = TUIChatConstants.covert2HTMLString(getDisplayName(v2TIMMessage)) + messageCustom.content;
                    messageBean.setText(message);
                    messageBean.setExtra(message);
                    return messageBean;
                }
            } catch (Exception e) {
                TUIChatLog.e(TAG, "invalid json: " + data + ", exception:" + e);
            }
        }
        return null;
    }

    private static TUIMessageBean parseGroupCreateMessage(V2TIMMessage v2TIMMessage, V2TIMCustomElem customElem) {
        String data = new String(customElem.getData());
        Gson gson = new Gson();

        if (data.equals(MessageCustom.BUSINESS_ID_GROUP_CREATE)) {
            // ??????4.7??????????????? tuikit
            TipsMessageBean messageBean = new TipsMessageBean();
            messageBean.setCommonAttribute(v2TIMMessage);
            messageBean.setTipType(TipsMessageBean.MSG_TYPE_GROUP_CREATE);
            String message = TUIChatConstants.covert2HTMLString(getDisplayName(v2TIMMessage)) + TUIChatService.getAppContext().getString(R.string.create_group);
            messageBean.setText(message);
            messageBean.setExtra(message);
            return messageBean;
        } else {
            if (isTyping(customElem.getData())) {
                // ?????????????????????????????????????????????????????????
                return null;
            }
            TUIChatLog.i(TAG, "custom data:" + data);
            MessageCustom messageCustom = null;
            try {
                messageCustom = gson.fromJson(data, MessageCustom.class);
                if (!TextUtils.isEmpty(messageCustom.businessID) && messageCustom.businessID.equals(MessageCustom.BUSINESS_ID_GROUP_CREATE)) {
                    TipsMessageBean messageBean = new TipsMessageBean();
                    messageBean.setCommonAttribute(v2TIMMessage);
                    messageBean.setTipType(TipsMessageBean.MSG_TYPE_GROUP_CREATE);
                    String message = TUIChatConstants.covert2HTMLString(getDisplayName(v2TIMMessage)) + messageCustom.content;
                    messageBean.setText(message);
                    messageBean.setExtra(message);
                    return messageBean;
                }
            } catch (Exception e) {
                TUIChatLog.e(TAG, "invalid json: " + data + ", exception:" + e);
            }
        }
        return null;
    }

    public static String getDisplayName(V2TIMMessage timMessage) {
        String displayName;
        if (timMessage == null) {
            return null;
        }
        // ?????????->????????????->??????->ID
        if (!TextUtils.isEmpty(timMessage.getNameCard())) {
            displayName = timMessage.getNameCard();
        } else if (!TextUtils.isEmpty(timMessage.getFriendRemark())) {
            displayName = timMessage.getFriendRemark();
        } else if (!TextUtils.isEmpty(timMessage.getNickName())) {
            displayName = timMessage.getNickName();
        } else {
            displayName = timMessage.getSender();
        }
        return displayName;
    }

    /**
     * ???????????????????????????????????? (???????????????????????????)
     *
     * @param messageBean ??????????????????
     * @return ?????????????????????????????????????????? null
     */
    public static String getLocalImagePath(TUIMessageBean messageBean) {
        if (messageBean == null || !messageBean.isSelf()) {
            return null;
        }
        V2TIMMessage message = messageBean.getV2TIMMessage();
        if (message == null || message.getElemType() != V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE) {
            return null;
        }
        V2TIMImageElem imageElem = message.getImageElem();
        if (imageElem == null) {
            return null;
        }
        String path = imageElem.getPath();
        File file = new File(path);
        if (file.exists()) {
            return path;
        }
        return null;
    }

    public static String getDisplayString(V2TIMMessage v2TIMMessage) {
        if (v2TIMMessage == null) {
            return null;
        }
        TUIMessageBean messageBean = parseMessage(v2TIMMessage);
        if (messageBean == null) {
            return null;
        }
        String displayString;
        if (messageBean.getStatus() == TUIMessageBean.MSG_STATUS_REVOKE) {
            if (messageBean.isSelf()) {
                displayString = TUIChatService.getAppContext().getString(R.string.revoke_tips_you);
            } else if (messageBean.isGroup()) {
                String message = TUIChatConstants.covert2HTMLString(
                        TextUtils.isEmpty(messageBean.getNameCard())
                                ? messageBean.getSender()
                                : messageBean.getNameCard());
                displayString = message + TUIChatService.getAppContext().getString(R.string.revoke_tips);
            } else {
                displayString = TUIChatService.getAppContext().getString(R.string.revoke_tips_other);
            }
        } else {
            displayString = messageBean.onGetDisplayString();
        }
        displayString = FaceManager.emojiJudge(displayString);
        return displayString;
    }

    public static String getReplyMessageAbstract(TUIMessageBean messageBean) {
        String result = "";
        if (messageBean != null) {
            String extra = "";
            if (messageBean instanceof TextMessageBean) {
                extra = ((TextMessageBean) messageBean).getText();
            } else if (messageBean instanceof MergeMessageBean) {
                extra = ((MergeMessageBean) messageBean).getTitle();
            } else if (messageBean instanceof FileMessageBean) {
                extra = ((FileMessageBean) messageBean).getFileName();
            } else if (messageBean instanceof CustomLinkMessageBean) {
                extra = ((CustomLinkMessageBean) messageBean).getText();
            } else if (messageBean instanceof SoundMessageBean
                    || messageBean instanceof ImageMessageBean
                    || messageBean instanceof VideoMessageBean
                    || messageBean instanceof LocationMessageBean
                    || messageBean instanceof FaceMessageBean) {
                extra = "";
            } else {
                extra = messageBean.getExtra();
            }
            result = result + extra;
        }
        return result;
    }

    public static boolean isFileType(int msgType) {
        return msgType == V2TIMMessage.V2TIM_ELEM_TYPE_FILE;
    }

    public static String getMsgTypeStr(int msgType) {
        String typeStr;
        switch (msgType) {
            case V2TIMMessage.V2TIM_ELEM_TYPE_SOUND: {
                typeStr = TUIChatService.getAppContext().getString(R.string.audio_extra);
                break;
            }
            case V2TIMMessage.V2TIM_ELEM_TYPE_FILE: {
                typeStr = TUIChatService.getAppContext().getString(R.string.file_extra);
                break;
            }
            case V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE: {
                typeStr = TUIChatService.getAppContext().getString(R.string.picture_extra);
                break;
            }
            case V2TIMMessage.V2TIM_ELEM_TYPE_LOCATION: {
                typeStr = TUIChatService.getAppContext().getString(R.string.location_extra);
                break;
            }
            case V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO: {
                typeStr = TUIChatService.getAppContext().getString(R.string.video_extra);
                break;
            }
            case V2TIMMessage.V2TIM_ELEM_TYPE_FACE: {
                typeStr = TUIChatService.getAppContext().getString(R.string.custom_emoji);
                break;
            }
            default: {
                typeStr = "";
            }
        }
        return typeStr;
    }

}
