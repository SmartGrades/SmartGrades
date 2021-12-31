package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.fragments.FragmentParentAddNewLesson;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_DeleteContactFromFamilyGroup;

public class ParentDialogMyFamily extends Dialog implements View.OnClickListener {

    private ParentActivity activity;
    private String PARENT_ID;
    private ImageView ivBack;

    private ModelParentData mParentData;

    private LinearLayout[] llParents = new LinearLayout[2];
    private LinearLayout[] llChildrens = new LinearLayout[5];
    private CircleImageView[] civAvatarParents = new CircleImageView[2];
    private CircleImageView[] civAvatarChildrens = new CircleImageView[5];
    private TextView[] tvNameParents = new TextView[2];
    private TextView[] tvNameChildrens = new TextView[5];

    private LinearLayout llAddParent, llAddChild;


    public ParentDialogMyFamily(ParentActivity activity, ModelParentData modelParentData) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.mParentData = modelParentData;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_my_family, null, false);
        setContentView(view);

        initViews(view);
        onLoadFamilyData();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        llParents[0] = view.findViewById(R.id.llParent1);
        llParents[0].setOnClickListener(this);
        llParents[1] = view.findViewById(R.id.llParent2);
        llParents[1].setOnClickListener(this);
        llChildrens[0] = view.findViewById(R.id.llChild1);
        llChildrens[0].setOnClickListener(this);
        llChildrens[1] = view.findViewById(R.id.llChild2);
        llChildrens[1].setOnClickListener(this);
        llChildrens[2] = view.findViewById(R.id.llChild3);
        llChildrens[2].setOnClickListener(this);
        llChildrens[3] = view.findViewById(R.id.llChild4);
        llChildrens[3].setOnClickListener(this);
        llChildrens[4] = view.findViewById(R.id.llChild5);
        llChildrens[4].setOnClickListener(this);

        civAvatarParents[0] = view.findViewById(R.id.civAvatarParent1);
        civAvatarParents[1] = view.findViewById(R.id.civAvatarParent2);
        civAvatarChildrens[0] = view.findViewById(R.id.civAvatarChild1);
        civAvatarChildrens[1] = view.findViewById(R.id.civAvatarChild2);
        civAvatarChildrens[2] = view.findViewById(R.id.civAvatarChild3);
        civAvatarChildrens[3] = view.findViewById(R.id.civAvatarChild4);
        civAvatarChildrens[4] = view.findViewById(R.id.civAvatarChild5);

        tvNameParents[0] = view.findViewById(R.id.tvNameParent1);
        tvNameParents[1] = view.findViewById(R.id.tvNameParent2);
        tvNameChildrens[0] = view.findViewById(R.id.tvNameChild1);
        tvNameChildrens[1] = view.findViewById(R.id.tvNameChild2);
        tvNameChildrens[2] = view.findViewById(R.id.tvNameChild3);
        tvNameChildrens[3] = view.findViewById(R.id.tvNameChild4);
        tvNameChildrens[4] = view.findViewById(R.id.tvNameChild5);

        llAddParent = view.findViewById(R.id.llAddParent);
        llAddParent.setOnClickListener(this);
        llAddChild = view.findViewById(R.id.llAddChild);
        llAddChild.setOnClickListener(this);
    }
    public void onLoadFamilyData() {
        this.mParentData = activity.getMParentData();
        if (mParentData.getFamilyGroup() == null || stringIsNullOrEmpty(mParentData.getFamilyGroup().getFamilyGroupId())) {
            onBackClick();
        }
        llParents[0].setVisibility(View.GONE);
        llParents[1].setVisibility(View.GONE);
        llChildrens[0].setVisibility(View.GONE);
        llChildrens[1].setVisibility(View.GONE);
        llChildrens[2].setVisibility(View.GONE);
        llChildrens[3].setVisibility(View.GONE);
        llChildrens[4].setVisibility(View.GONE);
        if (mParentData.getFamilyGroup() != null && !listIsNullOrEmpty(mParentData.getFamilyGroup().getParents())){
            if (mParentData.getFamilyGroup().getParents().size() >= 2) llAddParent.setVisibility(View.GONE);
            else llAddParent.setVisibility(View.VISIBLE);

            for(int i = 0; i < mParentData.getFamilyGroup().getParents().size(); i++){
                llParents[i].setVisibility(View.VISIBLE);
                String name = mParentData.getFamilyGroup().getParents().get(i).getFirstName()
                        + "\n" + mParentData.getFamilyGroup().getParents().get(i).getLastName();
                if (!stringIsNullOrEmpty(name))
                    tvNameParents[i].setText(name);
                else tvNameParents[i].setText(mParentData.getFamilyGroup().getParents().get(i).getLogin());

                String avatar = mParentData.getFamilyGroup().getParents().get(i).getAvatar();
                if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarParents[i]);
                else civAvatarChildrens[i].setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            }
        }
        else llAddParent.setVisibility(View.VISIBLE);

        if (mParentData.getFamilyGroup() != null && !listIsNullOrEmpty(mParentData.getFamilyGroup().getChildrens())){
            if (mParentData.getFamilyGroup().getChildrens().size() >= 5) llAddChild.setVisibility(View.GONE);
            else llAddChild.setVisibility(View.VISIBLE);

            for(int i = 0; i < mParentData.getFamilyGroup().getChildrens().size(); i++){
                llChildrens[i].setVisibility(View.VISIBLE);
                String name = mParentData.getFamilyGroup().getChildrens().get(i).getFirstName()
                        + "\n" + mParentData.getFamilyGroup().getChildrens().get(i).getLastName();
                if (!stringIsNullOrEmpty(name))
                    tvNameChildrens[i].setText(name);
                else tvNameChildrens[i].setText(mParentData.getFamilyGroup().getChildrens().get(i).getLogin());

                String avatar = mParentData.getFamilyGroup().getChildrens().get(i).getAvatar();
                if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarChildrens[i]);
                else civAvatarChildrens[i].setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            }
        }
        else llAddChild.setVisibility(View.VISIBLE);
    }

    public static FragmentParentAddNewLesson newInstance(String childId) {
        FragmentParentAddNewLesson fragment = new FragmentParentAddNewLesson();
        Bundle bundle = new Bundle();
        bundle.putString("ChildId", childId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void onAddParentClick() {
        activity.onEnrollFamilyGroup();
        activity.closeDrawer();
        dismiss();
    }
    private void onAddChildClick() {
        Dialog dialog = new Dialog(activity, R.style.CustomDialog2);
        dialog.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.activity_parent_add_child, null, false);
        dialog.show();
        ImageView ivBack = view.findViewById(R.id.ivBack);
        Button btnAddChild = view.findViewById(R.id.btnAddChild);
        Button btnSearchChild = view.findViewById(R.id.btnSearchChild);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onCreateChild();
//                onBackClick();
//                dialog.dismiss();
            }
        });
        btnSearchChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onAddChild();
                onBackClick();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        activity.closeDrawer();
    }

    private void onRemoveClick(String type, int number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_my_family_delete_contact, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvRemove = view.findViewById(R.id.tvRemove);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.SourceId, PARENT_ID);
                if (type.equals("Parent")) jsonData.addProperty(F.TargetId, mParentData.getFamilyGroup().getParents().get(number).getUserId());
                else jsonData.addProperty(F.TargetId, mParentData.getFamilyGroup().getChildrens().get(number).getId());
                jsonData.addProperty(F.GroupId, mParentData.getFamilyGroup().getFamilyGroupId());

                String SOAP = SoapRequest(func_DeleteContactFromFamilyGroup, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                    if (answer.isSuccess()) {
                                        if (type.equals("Parent")) mParentData.getFamilyGroup().getParents().remove(number);
                                        else mParentData.getFamilyGroup().getChildrens().remove(number);
                                        activity.updatePresenter();
                                    }
                                    activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void onBackClick() {
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.llAddParent:
                onAddParentClick();
                break;
            case R.id.llAddChild:
                onAddChildClick();
                break;

            case R.id.llParent1:
                onRemoveClick("Parent", 0);
                break;
            case R.id.llParent2:
                onRemoveClick("Parent", 1);
                break;
            case R.id.llChild1:
                onRemoveClick("Child", 0);
                break;
            case R.id.llChild2:
                onRemoveClick("Child", 1);
                break;
            case R.id.llChild3:
                onRemoveClick("Child", 2);
                break;
            case R.id.llChild4:
                onRemoveClick("Child", 3);
                break;
            case R.id.llChild5:
                onRemoveClick("Child", 4);
                break;
        }
    }
}