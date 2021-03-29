package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
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

    public static FragmentParentAddNewLesson newInstance(String childId) {
        FragmentParentAddNewLesson fragment = new FragmentParentAddNewLesson();
        Bundle bundle = new Bundle();
        bundle.putString("ChildId", childId);
        fragment.setArguments(bundle);
        return fragment;
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

    private void onLoadFamilyData() {
        if (!listIsNullOrEmpty(mParentData.getParentList())){
            if (mParentData.getParentList().size() >= 2) llAddParent.setVisibility(View.GONE);
            else llAddParent.setVisibility(View.VISIBLE);

            for(int i = 0; i < mParentData.getParentList().size(); i++){
                llParents[i].setVisibility(View.VISIBLE);
                tvNameParents[i].setText(mParentData.getParentList().get(i).getFirstName() + "\n" + mParentData.getParentList().get(i).getLastName());

                String avatar = mParentData.getParentList().get(i).getAvatar();
                if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(civAvatarChildrens[i]);
                else civAvatarChildrens[i].setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            }
        }
        else llAddParent.setVisibility(View.VISIBLE);

        if (!listIsNullOrEmpty(mParentData.getChildrenList())){
            if (mParentData.getChildrenList().size() >= 5) llAddChild.setVisibility(View.GONE);
            else llAddChild.setVisibility(View.VISIBLE);

            for(int i = 0; i < mParentData.getChildrenList().size(); i++){
                llChildrens[i].setVisibility(View.VISIBLE);
                tvNameChildrens[i].setText(mParentData.getChildrenList().get(i).getFirstName() + "\n" + mParentData.getChildrenList().get(i).getLastName());

                String avatar = mParentData.getChildrenList().get(i).getAvatar();
                if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(civAvatarChildrens[i]);
                else civAvatarChildrens[i].setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            }
        }
        else llAddChild.setVisibility(View.VISIBLE);
    }

    private void onAddParentClick() {
        dismiss();
        activity.alert.onToast("На сервере ведутся технические работы");
    }

    private void onAddChildClick() {
        activity.onAddChild();
        activity.closeDrawer();
        dismiss();
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
                if (type.equals("Parent")) jsonData.addProperty(F.UserId, mParentData.getParentList().get(number).getUserId());
//                else jsonData.addProperty(F.UserId, mParentData.getChildrenList().get(number).getChildId());
                jsonData.addProperty(F.GroupId, mParentData.getFamilyGroupId());

                String SOAP = SoapRequest(func_DeleteContactFromFamilyGroup, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLReader(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                    if (answer.isSuccess()) {
                                        if (type.equals("Parent")) mParentData.getParentList().remove(number);
                                        else mParentData.getChildrenList().remove(number);
                                        onLoadFamilyData();
                                        activity.presenter.onStartPresenter();
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