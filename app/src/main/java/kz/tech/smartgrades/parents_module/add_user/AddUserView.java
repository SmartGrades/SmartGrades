package kz.tech.smartgrades.parents_module.add_user;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.tools.GetAvatarByte;
import kz.tech.smartgrades.root.dialogs.BottomDialogSelectAvatar;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCardView;
import kz.tech.smartgrades.root.ui.CustomView.CustomCheckBox;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.parents_module.add_user.models.ModelUserReg;

public class AddUserView extends RelativeLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onAddUserClick(ModelUserReg modelUserReg);
        void onMessageClick(String msg);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_CONTAINER_SV = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:0,5,0,56"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};

    private static final String[] PARAM_CONTAINER_TOP_CV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:8,8,8,0", "Radius:8"};
    private static final String[] PARAM_CONTAINER_TOP_LL = {"prt:CardView", "layW:mPrt", "layH:wCnt", "orn:hor", "WeiSum:4"};
    private static final String[] PARAM_USER_PHOTO = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "parH:100", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:add_photo"};
    private static final String[] PARAM_CONTAINER_NAME_AND_PIN = {"prt:LinLay", "layW:0", "orn:ver", "layH:mPrt", "wei:3"};
    private static final String[] PARAM_USER_NAME = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40","mrg:5,5,5,5", "grv:TCH"};
    private static final String[] PARAM_USER_PIN = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:5,5,5,5", "grv:TCH",
            "InTy:NUMB", "Filter:PIN"};

    private static final String[] PARAM_PARENTS_CHILD_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "mrg:20,5,20,5", "orn:hor", "WeiSum:2"};
    private static final String[] PARAM_PARENTS_CHILD_CB = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5"};


    private static final String[] PARAM_CREATE_MEMBER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "Rule:BOTTOM", "mrg:20,0,20,20",
            "parH:50", "grv:CHCV", "txtC:WHITE", "backR:RED_ONE"};
    private CheckBox cbParent, cbFamilyMember, cbSon, cbDaughter;
    private String parent;
    public AddUserView(Context context, Resources res, String idStatus) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        switch (idStatus) {
            case "Father": parent = "Mother"; break;
            case "Mother": parent = "Father"; break;
        }

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(scrollView);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);

        CardView cvTop = new CustomCardView().onCustom(context, PARAM_CONTAINER_TOP_CV);
        linearLayout.addView(cvTop);

        LinearLayout llTop = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_TOP_LL);
        cvTop.addView(llTop);

        CircleImageView civSelectPhoto = new CustomCircleImageView().onCustom(context, PARAM_USER_PHOTO);
        llTop.addView(civSelectPhoto);
        civSelectPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbParent.isChecked() && !cbFamilyMember.isChecked() && !cbSon.isChecked() && !cbDaughter.isChecked()) {
                    onItemClickListener.onMessageClick(res.getString(R.string.select_status_family_member));
                    return;
                } else {
                    String isParents = "";
                    if (cbParent.isChecked()) {
                        isParents = parent;
                    } else if (cbFamilyMember.isChecked()) {
                        isParents = "FamilyMember";
                    } else if (cbSon.isChecked()) {
                        isParents = "Son";
                    } else if (cbDaughter.isChecked()) {
                        isParents = "Daughter";
                    }
                    BottomDialogSelectAvatar bottomDialogSelectAvatar =
                            new BottomDialogSelectAvatar(context, civSelectPhoto, isParents);
                    bottomDialogSelectAvatar.show();
                }
            }
        });

        LinearLayout llNameAndPin = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_NAME_AND_PIN);
        llTop.addView(llNameAndPin);

        EditText etName = new CustomEditText().onCustom(context, PARAM_USER_NAME, res.getString(R.string.name));
        llNameAndPin.addView(etName);

        EditText etPin = new CustomEditText().onCustom(context, PARAM_USER_PIN, res.getString(R.string.quiack_access_code));
        llNameAndPin.addView(etPin);

        LinearLayout llParents = new CustomLinearLayout().onCustom(context, PARAM_PARENTS_CHILD_LL);
        linearLayout.addView(llParents);

        cbParent = new CustomCheckBox().onCustom(context, PARAM_PARENTS_CHILD_CB, res.getString(R.string.parent));
        llParents.addView(cbParent);
        cbFamilyMember = new CustomCheckBox().onCustom(context, PARAM_PARENTS_CHILD_CB, res.getString(R.string.family_member));
        llParents.addView(cbFamilyMember);

        LinearLayout llChild = new CustomLinearLayout().onCustom(context, PARAM_PARENTS_CHILD_LL);
        linearLayout.addView(llChild);

        cbSon = new CustomCheckBox().onCustom(context, PARAM_PARENTS_CHILD_CB, res.getString(R.string.son));
        llChild.addView(cbSon);
        cbDaughter = new CustomCheckBox().onCustom(context, PARAM_PARENTS_CHILD_CB, res.getString(R.string.daughter));
        llChild.addView(cbDaughter);

        cbParent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbParent.isChecked()) { cbParent.setChecked(true); }
                if (cbFamilyMember.isChecked()) { cbFamilyMember.setChecked(false); }
                if (cbSon.isChecked()) { cbSon.setChecked(false); }
                if (cbDaughter.isChecked()) { cbDaughter.setChecked(false); }
            }
        });
        cbFamilyMember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbParent.isChecked()) { cbParent.setChecked(false); }
                if (cbFamilyMember.isChecked()) { cbFamilyMember.setChecked(true); }
                if (cbSon.isChecked()) { cbSon.setChecked(false); }
                if (cbDaughter.isChecked()) { cbDaughter.setChecked(false); }
            }
        });
        cbSon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbParent.isChecked()) { cbParent.setChecked(false); }
                if (cbFamilyMember.isChecked()) { cbFamilyMember.setChecked(false); }
                if (cbSon.isChecked()) { cbSon.setChecked(true); }
                if (cbDaughter.isChecked()) { cbDaughter.setChecked(false); }
            }
        });
        cbDaughter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbParent.isChecked()) { cbParent.setChecked(false); }
                if (cbFamilyMember.isChecked()) { cbFamilyMember.setChecked(false); }
                if (cbSon.isChecked()) { cbSon.setChecked(false); }
                if (cbDaughter.isChecked()) { cbDaughter.setChecked(true); }
            }
        });

        //  BOTTOM
        TextView tvCreateMember = new CustomTextView().onCustom(context, PARAM_CREATE_MEMBER, res.getString(R.string.create));
        this.addView(tvCreateMember);
        tvCreateMember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String isParents = "";
                if(TextUtils.isEmpty(etName.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_name));
                    return;
                }
                if(TextUtils.isEmpty(etPin.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_quick_access_code));
                    return;
                } else if (etPin.length() < 4){
                    onItemClickListener.onMessageClick(res.getString(R.string.quick_access_code_must_be_with_six_numbers));
                    return;
                }
                if (!cbParent.isChecked() && !cbFamilyMember.isChecked() && !cbSon.isChecked() && !cbDaughter.isChecked()) {
                    onItemClickListener.onMessageClick(res.getString(R.string.select_status_family_member));
                    return;
                } else {
                    if (cbParent.isChecked()) {
                        isParents = parent;
                    } else if (cbFamilyMember.isChecked()) {
                        isParents = "FamilyMember";
                    } else if (cbSon.isChecked()) {
                        isParents = "Son";
                    } else if (cbDaughter.isChecked()) {
                        isParents = "Daughter";
                    }
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onAddUserClick(
                            new ModelUserReg(
                                    etName.getText().toString(),
                                    etPin.getText().toString(),
                                    isParents,
                                    GetAvatarByte.getAvatar(civSelectPhoto)
                            ));
                }
            }
        });

    }

    public AddUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddUserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddUserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
