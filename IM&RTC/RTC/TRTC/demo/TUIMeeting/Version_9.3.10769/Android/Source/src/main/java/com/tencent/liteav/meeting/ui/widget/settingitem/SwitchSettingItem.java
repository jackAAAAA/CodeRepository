package com.tencent.liteav.meeting.ui.widget.settingitem;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tencent.liteav.demo.trtc.R;

/**
 * 带seekbar的item
 *
 * @author guanyifeng
 */
public class SwitchSettingItem extends BaseSettingItem {
    private static final String TAG = SwitchSettingItem.class.getName();

    private final Listener       mListener;
    private       ItemViewHolder mItemViewHolder;

    public SwitchSettingItem(Context context,
                             @NonNull ItemText itemText,
                             Listener listener) {
        super(context, itemText);
        mItemViewHolder = new ItemViewHolder(
                mInflater.inflate(R.layout.trtcmeeting_item_setting_switch, null)
        );
        mListener = listener;
    }

    public SwitchSettingItem setCheck(final boolean isCheck) {
        mItemViewHolder.mItemSwitch.post(new Runnable() {
            @Override
            public void run() {
                mItemViewHolder.mItemSwitch.setChecked(isCheck);
            }
        });
        return this;
    }

    @Override
    public View getView() {
        if (mItemViewHolder != null) {
            return mItemViewHolder.rootView;
        }
        return null;
    }

    public interface Listener {
        void onSwitchChecked(boolean isChecked);
    }

    public class ItemViewHolder {
        public  View         rootView;
        private TextView     mTitle;
        private SwitchCompat mItemSwitch;


        public ItemViewHolder(@NonNull final View itemView) {
            rootView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mTitle.setText(mItemText.title);
            mItemSwitch = (SwitchCompat) itemView.findViewById(R.id.switch_item);
            mItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mListener != null) {
                        mListener.onSwitchChecked(isChecked);
                    }
                }
            });
        }
    }
}
