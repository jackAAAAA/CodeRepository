package com.tencent.liteav.trtccalling.model;

/**
 * 结果回调
 */

//6.0.1992
//public class TRTCCallingCallback {
//    /**
//     * 通用回调
//     */
//    public interface ActionCallback {
//        void onCallback(int code, String msg);
//    }
//}

//6.1.2155
public interface TRTCCallingCallback {
    //用于返回IM 设置头像和昵称的结果
    void onCallback(int code, String msg);
}
