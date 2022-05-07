package com.tencent.qcloud.tuikit.tuichat.bean;

import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;

/**
 * 自定义消息的bean实体，用来与json的相互转化
 */
public class CustomHelloMessage {
    public static final int CUSTOM_HELLO_ACTION_ID = 3;

    public String businessID = TUIChatConstants.BUSINESS_ID_CUSTOM_HELLO;
    public String text = TUIChatService.getAppContext().getString(R.string.welcome_tip);
//    一天接入SDK_IM
//    public String link = "https://cloud.tencent.com/document/product/269/3794";

//    github_自定义消息
    public String link = "https://github.com/TencentCloud/TIMSDK/wiki/TUIKit-Android%E8%87%AA%E5%AE%9A%E4%B9%89%E6%B6%88%E6%81%AF";

    public int version = TUIChatConstants.JSON_VERSION_UNKNOWN;
}
