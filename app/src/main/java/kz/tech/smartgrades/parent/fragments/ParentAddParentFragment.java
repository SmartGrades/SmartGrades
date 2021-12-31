package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentsListAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetParentsList;
import static kz.tech.smartgrades._Web.func_InterFormParentToParent;

public class ParentAddParentFragment extends Fragment implements View.OnClickListener, ParentsListAdapter.OnItemCLickListener {

    private ParentActivity activity;
    private String PARENT_ID;
    private ImageView ivBack;
    private ParentsListAdapter parentsListAdapter;
    private RecyclerView rvParentsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    public static ParentAddParentFragment newInstance() {
        ParentAddParentFragment fragment = new ParentAddParentFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_add_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadParentsList();
    }

    private void initViews(View view) {
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        parentsListAdapter = new ParentsListAdapter(activity);
        parentsListAdapter.setOnItemClickListener(this);
        rvParentsList = view.findViewById(R.id.rvParentsList);
        rvParentsList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvParentsList.setAdapter(parentsListAdapter);

    }
    
    private void onLoadParentsList() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);

        String SOAP = SoapRequest(func_GetParentsList, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {
                            } else {
                                ArrayList<ModelUser> modelChats = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUser>>() {}.getType());
                                parentsListAdapter.updateData(modelChats);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                onBackClick();
                break;
        }
    }

    private void onBackClick() {
        activity.onHideParentAddParenFragment();
    }

    @Override
    public void onItemClick(ModelUser m) {
        BottomSheetDialog bsdAddNewCard = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.fragment_parent_profile, null, false);
        bsdAddNewCard.setContentView(view);
        View bottomSheet = bsdAddNewCard.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = _System.getWindowHeight(activity) / 100 * 90;
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bsdAddNewCard.show();

        ImageView ivBack = view.findViewById(R.id.ivBack);
        TextView tvLogin = view.findViewById(R.id.tvLogin);
        TextView tvFullName = view.findViewById(R.id.tvFullName);
        TextView tvAboutMe = view.findViewById(R.id.tvAboutMe);
        CircleImageView civAvatar = view.findViewById(R.id.civAvatar);
        Button btnSend = view.findViewById(R.id.btnSend);

        tvLogin.setText(m.getLogin());
        tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        tvAboutMe.setText(m.getAboutMe());

        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsdAddNewCard.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.SourceId, PARENT_ID);
                jsonData.addProperty(F.TargetId, m.getUserId());
                jsonData.addProperty(F.Message, "Приглашаю Вас в свою семейную группу.");

                String SOAP = SoapRequest(func_InterFormParentToParent, jsonData.toString());
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
                                    if (answer.isSuccess()) activity.presenter.onStartPresenter();
                                    else {
                                    }
                                    activity.alert.onToast(answer.getMessage());
                                    bsdAddNewCard.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
