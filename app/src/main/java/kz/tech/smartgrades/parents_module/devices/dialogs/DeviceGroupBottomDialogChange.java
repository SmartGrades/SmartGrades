package kz.tech.smartgrades.parents_module.devices.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.models.ModelDevice;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class DeviceGroupBottomDialogChange extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onLockClick(ModelDevice modelDevice, String lockStatus);
        void onUserClick(String idChild, ModelDevice modelDevice, String lockStatus);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_MODEL_ICON = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parH:60",
            "orn:hor", "WeiSum:2", "GRAV:CORE"};
    private static final String[] PARAM_ICON = {"prt:LinLay", "layW:0", "layH:mPrt",
            "wei:1", "GRAV:CORE"};
    private static final String[] PARAM_MODEL = {"prt:LinLay", "layW:0", "layH:mPrt",
            "wei:1", "GRAV:CORE",  "grv:LCV", "txtC:GRAY_ONE"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,10,0,20",
            "grv:CHCV", "txtC:BLACK", "TyFa:BOLD"};
    private static final String[] PARAM_LOCK = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,10,0,20",
            "grv:CHCV", "txtC:RED_ONE"};
    private static final String[] PARAM_UNLOCK = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,10,0,20",
            "grv:CHCV", "txtC:GREEN_ONE"};
    private BottomSheetDialog dialog;
    private LinearLayout linearLayout;
    private Context context;
    private Resources res;
    private ModelDevice modelDevice;
    public DeviceGroupBottomDialogChange(Context context, Resources res, ModelDevice modelDevice, List<ModelChild> childList) {
        super(context);
        dialog = this;
        this.context = context;
        this.res = res;
        this.modelDevice = modelDevice;
        int typeDevice = 0;
        switch (modelDevice.getTypeDevice()) {
            case "SmartPhone": typeDevice = R.mipmap.smartphone; break;
            case "Tablet": typeDevice = R.mipmap.planshet; break;
            case "iPad": typeDevice = R.mipmap.ipod; break;
            case "iPhone": typeDevice = R.mipmap.iphone; break;
        }
        String nameDevice = modelDevice.getNameDevice();

        linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v1);

        LinearLayout llModelIcon = new CustomLinearLayout().onCustom(context, PARAM_MODEL_ICON);
        linearLayout.addView(llModelIcon);

        ImageView ivIcon = new CustomImageView().onCustom(context, PARAM_ICON, typeDevice);
        llModelIcon.addView(ivIcon);

        TextView tvModel = new CustomTextView().onCustom(context, PARAM_MODEL, nameDevice);
        llModelIcon.addView(tvModel);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, null);
        linearLayout.addView(tvTitle);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v2);

        switch (modelDevice.getLockStatus()) {
            case "user":
                for (int i = 0; i < childList.size(); i++) {
                    if (!modelDevice.getIdUser().equals(childList.get(i).getIdChild())) {
                        addChild(childList.get(i));
                    }
                }
                tvTitle.setText(res.getString(R.string.change_user_to));
                addLock();
                addUnlock();
                break;
            case "lock":
                for (int i = 0; i < childList.size(); i++) {
                    addChild(childList.get(i));
                }
                tvTitle.setText(res.getString(R.string.open_access_for));
                addUnlock();
                break;
            case "unlock":
                for (int i = 0; i < childList.size(); i++) {
                    addChild(childList.get(i));
                }
                tvTitle.setText(res.getString(R.string.grant_access));
                addLock();
                break;
        }
    }
    private void addChild(ModelChild modelChild) {
        String avatar = modelChild.getAvatar();
        String name = modelChild.getName();
        String idChild = modelChild.getIdChild();

        LinearLayout llModelIcon = new CustomLinearLayout().onCustom(context, PARAM_MODEL_ICON);
        linearLayout.addView(llModelIcon);
        llModelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserClick(idChild, modelDevice, "user");
                }
                if (dialog != null) {
                    dialog.cancel();
                    dialog = null;
                }
            }
        });

        ImageView ivIcon = new CustomImageView().onCustom(context, PARAM_ICON, 0);
        llModelIcon.addView(ivIcon);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivIcon);

        TextView tvModel = new CustomTextView().onCustom(context, PARAM_MODEL, name);
        llModelIcon.addView(tvModel);

        View v = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v);
    }

    private void addLock() {
        TextView tvLock = new CustomTextView().onCustom(context, PARAM_LOCK, res.getString(R.string.close_access_for_all_children));
        linearLayout.addView(tvLock);
        tvLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onLockClick(modelDevice, "lock");
                }
                if (dialog != null) {
                    dialog.cancel();
                    dialog = null;
                }
            }
        });

        View v = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v);

    }

    private void addUnlock() {
        TextView tvUnlock = new CustomTextView().onCustom(context, PARAM_UNLOCK, res.getString(R.string.open_access_for_all_children));
        linearLayout.addView(tvUnlock);
        tvUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onLockClick(modelDevice, "unlock");
                }
                if (dialog != null) {
                    dialog.cancel();
                    dialog = null;
                }
            }
        });

        View v = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v);
    }

    public DeviceGroupBottomDialogChange(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected DeviceGroupBottomDialogChange(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
