package com.tencent.qcloud.tuikit.tuiconversation.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.component.TitleBarLayout;
import com.tencent.qcloud.tuicore.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuicore.component.menu.Menu;
import com.tencent.qcloud.tuicore.util.BackgroundTasks;
import com.tencent.qcloud.tuicore.util.PopWindowUtil;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuiconversation.R;
import com.tencent.qcloud.tuikit.tuiconversation.TUIConversationConstants;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;
import com.tencent.qcloud.tuicore.component.fragments.BaseFragment;
import com.tencent.qcloud.tuicore.component.action.PopActionClickListener;
import com.tencent.qcloud.tuicore.component.action.PopDialogAdapter;
import com.tencent.qcloud.tuicore.component.action.PopMenuAction;
import com.tencent.qcloud.tuikit.tuiconversation.presenter.ConversationPresenter;
import com.tencent.qcloud.tuikit.tuiconversation.setting.ConversationLayoutSetting;
import com.tencent.qcloud.tuikit.tuiconversation.ui.view.ConversationLayout;
import com.tencent.qcloud.tuikit.tuiconversation.ui.view.ConversationListLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TUIConversationFragment extends BaseFragment {

    private View mBaseView;
    private ConversationLayout mConversationLayout;
    private ListView mConversationPopList;
    private PopDialogAdapter mConversationPopAdapter;
    private PopupWindow mConversationPopWindow;
    private List<PopMenuAction> mConversationPopActions = new ArrayList<>();
    private Menu mMenu;

    private ConversationPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.conversation_fragment, container, false);
        initView();
        return mBaseView;
    }

    private void initView() {
        // 从布局文件中获取会话列表面板
        mConversationLayout = mBaseView.findViewById(R.id.conversation_layout);
        initMenu();
        
        presenter = new ConversationPresenter();
        presenter.setConversationListener();
        mConversationLayout.setPresenter(presenter);

        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        // 通过API设置ConversationLayout各种属性的样例，开发者可以打开注释，体验效果
//        ConversationLayoutSetting.customizeConversation(mConversationLayout);
        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConversationInfo conversationInfo) {
                //此处为demo的实现逻辑，根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                startChatActivity(conversationInfo);
            }
        });

        mConversationLayout.getConversationList().setOnItemLongClickListener(new ConversationListLayout.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position, ConversationInfo conversationInfo) {
                startPopShow(view, conversationInfo);
            }
        });
        initTitleAction();
        initPopMenuAction();
    }

    private void initMenu() {
        mMenu = new Menu(getActivity(), (TitleBarLayout) mConversationLayout.getTitleBar());

        PopActionClickListener popActionClickListener = new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                PopMenuAction action = (PopMenuAction) data;
                if (TextUtils.equals(action.getActionName(), getResources().getString(R.string.start_conversation))) {
                    TUICore.startActivity("StartC2CChatActivity", null);
                }

                if (TextUtils.equals(action.getActionName(), getResources().getString(R.string.create_private_group))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(TUIConversationConstants.GroupType.TYPE, TUIConversationConstants.GroupType.PRIVATE);
                    TUICore.startActivity("StartGroupChatActivity", bundle);
                }
                if (TextUtils.equals(action.getActionName(), getResources().getString(R.string.create_group_chat))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(TUIConversationConstants.GroupType.TYPE, TUIConversationConstants.GroupType.PUBLIC);
                    TUICore.startActivity("StartGroupChatActivity", bundle);
                }
                if (TextUtils.equals(action.getActionName(), getResources().getString(R.string.create_chat_room))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(TUIConversationConstants.GroupType.TYPE, TUIConversationConstants.GroupType.CHAT_ROOM);
                    TUICore.startActivity("StartGroupChatActivity", bundle);
                }
                mMenu.hide();
            }
        };

        // 设置右上角+号显示PopAction
        List<PopMenuAction> menuActions = new ArrayList<PopMenuAction>();

        PopMenuAction action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.start_conversation));
        action.setActionClickListener(popActionClickListener);
        action.setIconResId(R.drawable.create_c2c);
        menuActions.add(action);

        action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.create_private_group));
        action.setIconResId(R.drawable.group_icon);
        action.setActionClickListener(popActionClickListener);
        menuActions.add(action);

        action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.create_group_chat));
        action.setIconResId(R.drawable.group_icon);
        action.setActionClickListener(popActionClickListener);
        menuActions.add(action);

        action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.create_chat_room));
        action.setIconResId(R.drawable.group_icon);
        action.setActionClickListener(popActionClickListener);
        menuActions.add(action);
        mMenu.setMenuAction(menuActions);
    }

    private void initTitleAction() {
        mConversationLayout.getTitleBar().setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMenu.isShowing()) {
                    mMenu.hide();
                } else {
                    mMenu.show();
                }
            }
        });
    }

    private void initPopMenuAction() {
        // 设置长按conversation显示PopAction
        List<PopMenuAction> conversationPopActions = new ArrayList<PopMenuAction>();
        PopMenuAction action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.chat_top));
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.setConversationTop((ConversationInfo) data, new IUIKitCallback() {
                    @Override
                    public void onSuccess(Object data) {

                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtil.toastLongMessage(module + ", Error code = " + errCode + ", desc = " + errMsg);
                    }
                });
            }
        });
        conversationPopActions.add(action);
        action = new PopMenuAction();
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.deleteConversation((ConversationInfo) data);
            }
        });
        action.setActionName(getResources().getString(R.string.chat_delete));
        conversationPopActions.add(action);

        action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.clear_conversation_message));
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.clearConversationMessage((ConversationInfo) data);
            }
        });
        conversationPopActions.add(action);

        mConversationPopActions.clear();
        mConversationPopActions.addAll(conversationPopActions);
    }

    /**
     * 长按会话item弹框
     *
     * @param conversationInfo 会话数据对象
     * @param locationX        长按时X坐标
     * @param locationY        长按时Y坐标
     */
    private void showItemPopMenu(final ConversationInfo conversationInfo, float locationX, float locationY) {
        if (mConversationPopActions == null || mConversationPopActions.size() == 0)
            return;
        View itemPop = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu_layout, null);
        mConversationPopList = itemPop.findViewById(R.id.pop_menu_list);
        mConversationPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuAction action = mConversationPopActions.get(position);
                if (action.getActionClickListener() != null) {
                    action.getActionClickListener().onActionClick(position, conversationInfo);
                }
                mConversationPopWindow.dismiss();
            }
        });

        for (int i = 0; i < mConversationPopActions.size(); i++) {
            PopMenuAction action = mConversationPopActions.get(i);
            if (conversationInfo.isTop()) {
                if (action.getActionName().equals(getResources().getString(R.string.chat_top))) {
                    action.setActionName(getResources().getString(R.string.quit_chat_top));
                }
            } else {
                if (action.getActionName().equals(getResources().getString(R.string.quit_chat_top))) {
                    action.setActionName(getResources().getString(R.string.chat_top));
                }

            }
        }
        mConversationPopAdapter = new PopDialogAdapter();
        mConversationPopList.setAdapter(mConversationPopAdapter);
        mConversationPopAdapter.setDataSource(mConversationPopActions);
        mConversationPopWindow = PopWindowUtil.popupWindow(itemPop, mBaseView, (int) locationX, (int) locationY);
        BackgroundTasks.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                mConversationPopWindow.dismiss();
            }
        }, 10000); // 10s后无操作自动消失
    }

    private void startPopShow(View view, ConversationInfo info) {
        showItemPopMenu(info, view.getX(), view.getY() + view.getHeight() / 2);
    }

    private void startChatActivity(ConversationInfo conversationInfo) {
        Bundle param = new Bundle();
        param.putInt(TUIConstants.TUIChat.CHAT_TYPE, conversationInfo.isGroup() ? V2TIMConversation.V2TIM_GROUP : V2TIMConversation.V2TIM_C2C);
        param.putString(TUIConstants.TUIChat.CHAT_ID, conversationInfo.getId());
        param.putString(TUIConstants.TUIChat.CHAT_NAME, conversationInfo.getTitle());
        if (conversationInfo.getDraft() != null) {
            param.putString(TUIConstants.TUIChat.DRAFT_TEXT, conversationInfo.getDraft().getDraftText());
            param.putLong(TUIConstants.TUIChat.DRAFT_TIME, conversationInfo.getDraft().getDraftTime());
        }
        param.putBoolean(TUIConstants.TUIChat.IS_TOP_CHAT, conversationInfo.isTop());

        if (conversationInfo.isGroup()) {
            param.putString(TUIConstants.TUIChat.FACE_URL, conversationInfo.getIconPath());
            param.putString(TUIConstants.TUIChat.GROUP_TYPE, conversationInfo.getGroupType());
        }
        if (conversationInfo.isGroup()) {
            TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
        } else {
            TUICore.startActivity(TUIConstants.TUIChat.C2C_CHAT_ACTIVITY_NAME, param);
        }
    }

}
