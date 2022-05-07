package com.tencent.liteav.meeting.ui.widget.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.fragment.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.liteav.demo.trtc.R;

/**
 * 用户设置页的基类
 *
 * @author guanyifeng
 */
public abstract class BaseSettingFragmentDialog extends BottomSheetDialogFragment {
    public static final String DATA = "data";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TRTCMeetingBaseFragmentDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    /**
     * @return layout的resId
     */
    protected abstract int getLayoutId();

    /**
     * 可以通过覆盖这个函数达到改变弹窗大小的效果
     *
     * @param dm DisplayMetrics
     * @return 界面宽度
     */
    protected int getWidth(DisplayMetrics dm) {
        return (int) (dm.widthPixels * 0.9);
    }

    /**
     * 可以通过覆盖这个函数达到改变弹窗大小的效果
     *
     * @param dm DisplayMetrics
     * @return 界面高度
     */
    protected int getHeight(DisplayMetrics dm) {
        return (int) (dm.heightPixels * 0.8);
    }

    protected boolean isShowBottom() {
        return true;
    }
}
