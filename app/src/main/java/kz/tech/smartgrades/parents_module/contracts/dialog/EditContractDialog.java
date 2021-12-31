package kz.tech.smartgrades.parents_module.contracts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.models.dialogs.AvatarDialog;
import kz.tech.smartgrades.parents_module.coins.models.dialogs.ContractsDialog;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.root.tools.ImageViewToByteArray;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class EditContractDialog extends Dialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onCreateContractClick(String title, String avatar, String purpose, String description, String countSteps);
        void onMessageClick(String msg);
        void onSaveChangeClick(ModelContract modelContract);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:50,0,50,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};
    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,55,0,0"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_NUMB_CONTRACT_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "orn:hor"
            ,"WeiSum:8", "backC:WHITE"};
    private static final String[] PARAM_VIEW_EMPTY = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};
    private static final String[] PARAM_NUMB_CONTRACT_TV = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:6",
            "grv:CHCV", "pad:5,5,5,5", "txtC:BLACK", "TyFa:BOLD"};
    private static final String[] PARAM_IMAGE_MOVE = {"prt:LinLay", "layW:0", "layH:mPrt", "parH:40", "wei:1", "pad:5,5,5,5", "GRAV:VER"};
    private static final String[] PARAM_ALL_CONTRACT = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:100", "orn:hor"
            ,"WeiSum:8", "backC:WHITE"};
    private static final String[] PARAM_IMAGE_ICON = {"prt:LinLay", "layW:0", "layH:mPrt", "parH:60", "mrg:10,0,0,0",
            "wei:1", "pad:5,5,5,5", "GRAV:VER"};
    private static final String[] PARAM_TEXTS_LL = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:6", "orn:ver"};
    private static final String[] PARAM_TEXTS_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "grv:CHCV", "pad:5,5,5,5", "txtC:BLACK", "TyFa:BOLD"};
    private static final String[] PARAM_TEXTS_DESCRIPTION = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:70",
            "grv:TCH", "pad:5,5,5,5", "txtC:GRAY_THREE"};
    private static final String[] PARAM_CONTAINER_BTN = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:100", "backC:WHITE"};
    private static final String[] PARAM_BTN = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "GRAV:CORE", "mrg:56,0,56,0",
            "grv:CHCV", "pad:5,5,5,5", "txtC:WHITE", "backR:RED_ONE"};
    private static final String[] PARAM_IMAGE_PHOTO = {"prt:LinLay", "layW:0", "layH:mPrt", "parH:40",
            "wei:1", "pad:5,5,5,5", "GRAV:VER", "BordC:GRAY_THREE", "BordW:2", "img:red_add_photo"};
    private Resources res;
    private Dialog dialog;
    private String title;
    private String avatarIcon;
    private String purpose;
    private String description;
    private String countSteps;
    private ImageView ivAvatarIcon;
    private byte[] dataImage;
    private boolean isEditContract = false;
    public EditContractDialog(@NonNull Context context) {
        super(context);
    }
    public EditContractDialog(Context con, int themeResId, Resources res, ModelContract m, String avatar, String name) {
        super(con, themeResId);
        this.res = res;
        dialog = this;
        if (m != null) {
            isEditContract = true;
        }
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(con, PARAM);
        this.setContentView(frameLayout);

        String[] arrayTitle = res.getStringArray(R.array.title_contract);
        String[] arrayDescription = res.getStringArray(R.array.description_contract);

        Toolbar toolbar = new CustomToolbar().onCustom(con, PARAM_TOOLBAR);
        frameLayout.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(con, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ImageView ivLeftButton = new CustomImageView().onCustom(con, PARAM_LEFT_BUTTON, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        String logoTitle = "";
        if (isEditContract) {
            logoTitle = res.getString(R.string.editing_a_contract);
        } else {
            logoTitle = res.getString(R.string.preparation_of_contract);
        }

        TextView tvTitle = new CustomTextView().onCustom(con, PARAM_TITLE, logoTitle);
        rlToolbar.addView(tvTitle);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(con, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);

        ScrollView svEditContract = new CustomScrollView().onCustom(con, PARAM_CONTAINER_SV);
        frameLayout.addView(svEditContract);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(con, PARAM_CONTAINER_LL);
        svEditContract.addView(linearLayout);

        View v1 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v1);

        LinearLayout llNumbContract = new CustomLinearLayout().onCustom(con, PARAM_NUMB_CONTRACT_LL);
        linearLayout.addView(llNumbContract);

        View vEmpty = new CustomView().onCustom(con, PARAM_VIEW_EMPTY);
        llNumbContract.addView(vEmpty);

        //     HERE  1
        TextView tvNumbContract = new CustomTextView().onCustom(con, PARAM_NUMB_CONTRACT_TV, res.getString(R.string.contract));
        llNumbContract.addView(tvNumbContract);
        if (isEditContract) {
            String nameContract = m.getTitle();
            if (nameContract != null) {
                tvNumbContract.setText(nameContract);
                title = nameContract;
            }
        }


        ImageView ivNumbContract = new CustomImageView().onCustom(con, PARAM_IMAGE_MOVE, R.mipmap.move_red);
        llNumbContract.addView(ivNumbContract);
        ivNumbContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //"prt:LinLay", "layW:mPrt", "layH:wCnt", "grv:CHCV", "InTy:NUMB", "Filter:PIN"
                String[] params = {};
                ContractsDialog dialogs = new ContractsDialog(con, res.getString(R.string.new_contract), params, res.getString(R.string.new_contract));
                dialogs.showAlert();
                dialogs.setOnItemClickListener(new ContractsDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(String text) {
                        tvNumbContract.setText(text);
                        title = text;
                        if (isEditContract) {
                            m.setTitle(title);
                        }
                    }
                });
            }
        });

        View v2 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v2);

        //  Purpose
        LinearLayout llPurpose = new CustomLinearLayout().onCustom(con, PARAM_ALL_CONTRACT);
        linearLayout.addView(llPurpose);

        ImageView ivPurposeIcon = new CustomImageView().onCustom(con, PARAM_IMAGE_ICON, R.mipmap.purpose);
        llPurpose.addView(ivPurposeIcon);

        LinearLayout llTextsPurpose = new CustomLinearLayout().onCustom(con, PARAM_TEXTS_LL);
        llPurpose.addView(llTextsPurpose);

        TextView tvTitlePurpose = new CustomTextView().onCustom(con, PARAM_TEXTS_TITLE, arrayTitle[0]);
        llTextsPurpose.addView(tvTitlePurpose);

        //     HERE  1
        TextView tvDescriptionPurpose = new CustomTextView().onCustom(con, PARAM_TEXTS_DESCRIPTION, arrayDescription[0]);
        llTextsPurpose.addView(tvDescriptionPurpose);
        if (isEditContract) {
            String purposeChild = m.getPurposeChild();
            if (purposeChild != null) {
                tvDescriptionPurpose.setText(purposeChild);
                purpose = purposeChild;
            }
        }


        ImageView ivPurposeMove = new CustomImageView().onCustom(con, PARAM_IMAGE_MOVE, R.mipmap.move_red);
        llPurpose.addView(ivPurposeMove);
        ivPurposeMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] params = {};
                ContractsDialog dialogs = new ContractsDialog(con, arrayTitle[0], params, arrayTitle[0]);
                dialogs.showAlert();
                dialogs.setOnItemClickListener(new ContractsDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(String text) {
                        tvDescriptionPurpose.setText(text);
                        purpose = text;
                        if (isEditContract) {
                            m.setPurposeChild(purpose);
                        }
                    }
                });
            }
        });

        View v3 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v3);

        //  Avatar
        LinearLayout llAvatar = new CustomLinearLayout().onCustom(con, PARAM_ALL_CONTRACT);
        linearLayout.addView(llAvatar);

        //     HERE  2
        ivAvatarIcon = new CustomImageView().onCustom(con, PARAM_IMAGE_ICON, R.mipmap.bear);
        llAvatar.addView(ivAvatarIcon);
        if (isEditContract) {
            String present = m.getAvatar();
            if (present != null) {
                Picasso.get().load(present).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivAvatarIcon);
                avatarIcon = present;
            }
        }

        LinearLayout llTextsAvatar = new CustomLinearLayout().onCustom(con, PARAM_TEXTS_LL);
        llAvatar.addView(llTextsAvatar);

        TextView tvTitleAvatar = new CustomTextView().onCustom(con, PARAM_TEXTS_TITLE, arrayTitle[1]);
        llTextsAvatar.addView(tvTitleAvatar);


        TextView tvDescriptionAvatar = new CustomTextView().onCustom(con, PARAM_TEXTS_DESCRIPTION, arrayDescription[1]);
        llTextsAvatar.addView(tvDescriptionAvatar);


        CircleImageView civAvatarMove = new CustomCircleImageView().onCustom(con, PARAM_IMAGE_PHOTO);
        llAvatar.addView(civAvatarMove);
        civAvatarMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvatarDialog dialog = new AvatarDialog(con, "AvatarTask");
                dialog.show();
                dialog.setOnItemClickListener(new AvatarDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int images) {
                        ivAvatarIcon.setImageResource(0);
                        ivAvatarIcon.setImageResource(images);
                        dataImage = ImageViewToByteArray.getData(ivAvatarIcon);
                        getUrlImage(dataImage, m);
                    }
                });

            }
        });

        View v4 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v4);

        //  Task
        LinearLayout llTask = new CustomLinearLayout().onCustom(con, PARAM_ALL_CONTRACT);
        linearLayout.addView(llTask);

        ImageView ivTaskIcon = new CustomImageView().onCustom(con, PARAM_IMAGE_ICON, R.mipmap.done);
        llTask.addView(ivTaskIcon);

        LinearLayout llTextsTask = new CustomLinearLayout().onCustom(con, PARAM_TEXTS_LL);
        llTask.addView(llTextsTask);

        TextView tvTitleTask = new CustomTextView().onCustom(con, PARAM_TEXTS_TITLE, arrayTitle[2]);
        llTextsTask.addView(tvTitleTask);

        //     HERE  3
        TextView tvDescriptionTask = new CustomTextView().onCustom(con, PARAM_TEXTS_DESCRIPTION, arrayDescription[2]);
        llTextsTask.addView(tvDescriptionTask);
        tvDescriptionTask.setTextSize(12);
        if (isEditContract) {
            String descriptionS = m.getDescription();
            if (descriptionS != null) {
                tvDescriptionTask.setText(descriptionS);
                description = descriptionS;
            }
        }
        ImageView ivTaskMove = new CustomImageView().onCustom(con, PARAM_IMAGE_MOVE, R.mipmap.move_red);
        llTask.addView(ivTaskMove);
        ivTaskMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] params = {};
                ContractsDialog dialogs = new ContractsDialog(con, arrayTitle[2], params, arrayTitle[2]);
                dialogs.showAlert();
                dialogs.setOnItemClickListener(new ContractsDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(String text) {
                        tvDescriptionTask.setText(text);
                        description = text;
                        if (isEditContract) {
                            m.setDescription(description);
                        }

                    }
                });
            }
        });

        View v5 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v5);

        //  Step
        LinearLayout llStep = new CustomLinearLayout().onCustom(con, PARAM_ALL_CONTRACT);
        linearLayout.addView(llStep);

        ImageView ivStepIcon = new CustomImageView().onCustom(con, PARAM_IMAGE_ICON, R.mipmap.step);
        llStep.addView(ivStepIcon);

        LinearLayout llTextsStep = new CustomLinearLayout().onCustom(con, PARAM_TEXTS_LL);
        llStep.addView(llTextsStep);

        TextView tvTitleStep = new CustomTextView().onCustom(con, PARAM_TEXTS_TITLE, arrayTitle[3]);
        llTextsStep.addView(tvTitleStep);

        //     HERE  4
        TextView tvDescriptionStep = new CustomTextView().onCustom(con, PARAM_TEXTS_DESCRIPTION, arrayDescription[3]);
        llTextsStep.addView(tvDescriptionStep);
        tvDescriptionStep.setTextSize(10);
        if (isEditContract) {
            String steps = m.getCountSteps();
            if (steps != null) {
                String[] parse = steps.split("/");
                tvDescriptionStep.setText(parse[0]);
                countSteps = parse[0];
            }
        }


        ImageView ivStepMove = new CustomImageView().onCustom(con, PARAM_IMAGE_MOVE, R.mipmap.move_red);
        llStep.addView(ivStepMove);
        ivStepMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] params = {"grv:CHCV", "InTy:NUMB", "Filter:PIN", "MinMax:50"};
                ContractsDialog dialogs = new ContractsDialog(con, arrayTitle[3], params, arrayTitle[3]);
                dialogs.showAlert();
                dialogs.setOnItemClickListener(new ContractsDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(String text) {
                        tvDescriptionStep.setText(text);
                        countSteps = text;
                        if (isEditContract) {
                            m.setCountSteps(countSteps+"/0");
                        }
                    }
                });
            }
        });

        View v6 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v6);

        FrameLayout flSigningAContract = new CustomFrameLayout().onCustom(con, PARAM_CONTAINER_BTN);
        linearLayout.addView(flSigningAContract);

        TextView tvButton = new CustomTextView().onCustom(con, PARAM_BTN, res.getString(R.string.go_to_signing));
        flSigningAContract.addView(tvButton);
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEditContract) {
                    if (onItemClickListener != null) {
                        if (TextUtils.isEmpty(purpose)) {
                            onItemClickListener.onMessageClick(res.getString(R.string.enter_child_purpose));
                            return;
                        }
                        if (TextUtils.isEmpty(description)) {
                            onItemClickListener.onMessageClick(res.getString(R.string.enter_what_the_child_should_do));
                            return;
                        }
                        if (TextUtils.isEmpty(countSteps)) {
                            onItemClickListener.onMessageClick(res.getString(R.string.enter_the_number_of_steps));
                            return;
                        }
                        if (dataImage == null) {
                            onItemClickListener.onMessageClick(res.getString(R.string.select_avatar));
                            return;
                        }
                        if (title == null) {
                            title = res.getString(R.string.contract);
                        }
                        if (title != null && dataImage != null & purpose != null && description != null && countSteps != null) {
                            onItemClickListener.onCreateContractClick(title, avatarIcon, purpose, description, countSteps);
                            dialog.dismiss();
                        }
                    }
                } else {
                    if (onItemClickListener != null) {
                        onItemClickListener.onSaveChangeClick(m);
                        dialog.dismiss();
                    }
                }
            }
        });

        View v7 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        linearLayout.addView(v7);
    }
    protected EditContractDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    private void getUrlImage(byte[] data, ModelContract m) {
        UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(data);//  Set image byte array
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String uriToString = uri.toString();//  Get download URI
                        if (uriToString != null) {
                            avatarIcon = uriToString;
                            if (isEditContract) {
                                m.setAvatar(avatarIcon);
                            }
                        }
                    }
                });
            }
        });
    }
}
