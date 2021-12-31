package kz.tech.smartgrades.parents_module.settings.personal_data;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.dialogs.BottomDialogSelectAvatar;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.tools.GetAvatarByte;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class PersonalDataView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onSave(String strAvatar, String strName, String strStatus);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "orn:ver", "mrg:0,5,0,0", "backC:WHITE"};

    private static final String[] PARAM_CONTAINER_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "orn:hor", "WeiSum:6"};

    private static final String[] PARAM_TEXT_PREFIX = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "mrg:20,0,0,0",
            "grv:LCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_TEXT = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:4", "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_MOVE = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5"};
    private static final String[] PARAM_PHOTO = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:red_add_photo"};

    private static final String[] PARAM_SAVE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:56,56,56,0",
            "grv:CHCV", "txtC:WHITE", "backR:RED_ONE"};

    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private Context context;
    private CircleImageView civAvatar, civAvatarMove;

    public String strAvatar;
    public String strName;
    public String strStatus;
    private TextView tvAvatar, tvName, tvStatus;

    private int selectStatus = 1;
    private Resources res;
    public PersonalDataView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;
        this.res = res;

        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(llContainer);



        //  1
        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v1);

        LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        llContainer.addView(ll1);

        TextView tvNamePrefix = new CustomTextView().onCustom(context, PARAM_TEXT_PREFIX, res.getString(R.string.name));
        ll1.addView(tvNamePrefix);

        tvName = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        ll1.addView(tvName);

        ImageView ivNameMove = new CustomImageView().onCustom(context, PARAM_MOVE, R.mipmap.move_red);
        ll1.addView(ivNameMove);
        ivNameMove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogName dialog = new AlertDialogName(context, res);
                dialog.showAlert();
                dialog.setOnItemClickListener(new AlertDialogName.OnItemClickListener() {
                    @Override
                    public void onItemClick(String name) {
                        tvName.setText(name);
                        strName = name;
                    }
                });
            }
        });

        //  2
        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v2);

        LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        llContainer.addView(ll2);

        TextView tvStatusPrefix = new CustomTextView().onCustom(context, PARAM_TEXT_PREFIX, res.getString(R.string.status));
        ll2.addView(tvStatusPrefix);

        tvStatus = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        ll2.addView(tvStatus);

        ImageView ivStatusMove = new CustomImageView().onCustom(context, PARAM_MOVE, R.mipmap.move_red);
        ll2.addView(ivStatusMove);
        ivStatusMove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusDialog statusDialog = new StatusDialog(context, res.getString(R.string.change_status), selectStatus, res);
                statusDialog.show();
                statusDialog.setOnItemClickListener(new StatusDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 1:
                                tvStatus.setText(res.getString(R.string.father));
                                strAvatar = "Father";
                                selectStatus = 1;
                                break;
                            case 2:
                                tvStatus.setText(res.getString(R.string.mother));
                                strAvatar = "Mother";
                                selectStatus = 2;
                                break;
                        }
                    }
                });
            }

        });
        //  3
        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v3);

        LinearLayout ll3 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        llContainer.addView(ll3);

        TextView tvAvatarPrefix = new CustomTextView().onCustom(context, PARAM_TEXT_PREFIX, res.getString(R.string.avatar));
        ll3.addView(tvAvatarPrefix);

        tvAvatar = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        ll3.addView(tvAvatar);

        civAvatarMove = new CustomCircleImageView().onCustom(context, PARAM_PHOTO);
        ll3.addView(civAvatarMove);
        civAvatarMove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogSelectAvatar bottomDialogSelectAvatar =
                        new BottomDialogSelectAvatar(context, civAvatarMove, strStatus);
                bottomDialogSelectAvatar.show();
                bottomDialogSelectAvatar.setOnItemClickListener(new BottomDialogSelectAvatar.OnItemClickListener() {
                    @Override
                    public void onImageClick(int imageAvatar) {
                        onImageDataByte(GetAvatarByte.getAvatar(civAvatarMove));

                    }
                });
            }
        });

        View v4 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v4);

        ///


        //  BUTTON SAVE

        TextView tvSave = new CustomTextView().onCustom(context, PARAM_SAVE, res.getString(R.string.save));
        llContainer.addView(tvSave);
        tvSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSave(strAvatar, strName, strStatus);
                }

            }
        });

    }

    public PersonalDataView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonalDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PersonalDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void initVariables(String strAvatar, String strName, String strStatus) {
        this.strAvatar = strAvatar;
        this.strName = strName;
        this.strStatus = strStatus;

        if (this.strAvatar != null) {
            Picasso.get().load(this.strAvatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarMove);
        }
        if (this.strName != null) {
            tvName.setText(this.strName);
        }
        if (this.strStatus != null) {
            switch (this.strStatus) {
                case "Father": tvStatus.setText(res.getString(R.string.father)); break;
                case "Mother": tvStatus.setText(res.getString(R.string.mother)); break;
            }

        }
    }

    private void onImageDataByte(byte[] data) {
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
                            strAvatar = uriToString;
                        }
                    }
                });
            }
        });
    }

}
