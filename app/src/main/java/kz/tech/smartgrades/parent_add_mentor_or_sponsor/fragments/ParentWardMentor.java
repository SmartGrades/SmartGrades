package kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent.model.ModelParentChildList;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters.WardListAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_InterFormParentToMentor;

public class ParentWardMentor extends Fragment implements View.OnClickListener {

    private ParentAddSponsorOrMentorActivity activity;

    private TextView tvFullName, tvLogin;
    private CircleImageView civAvatar, civWardAvatar;
    private CardView cvWard;
    private TextView tvSelectWard;
    private Button btnSend;
    private ImageView ivBack;

    //CHILD MODEL
    private ModelParentChildList mChild;
    private ArrayList<ModelParentChildList> mChildrenList;
    //MENTOR MODEL
    private ModelMentorSponsorRoom mMentor;


    private boolean isFirstChildList = false;
    private int childSelect = 0;
    private String toolType;

    private String PARENT_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddSponsorOrMentorActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_ward_mentor, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);
        civAvatar = view.findViewById(R.id.civAvatar);

        cvWard = view.findViewById(R.id.cvSelectChild);
        cvWard.setOnClickListener(this);
        cvWard.setEnabled(false);

        tvSelectWard = view.findViewById(R.id.tvChildLogin);
        tvSelectWard.setOnClickListener(this);
        civWardAvatar = view.findViewById(R.id.civMentorAvatar);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        mChildrenList = activity.getParentChildLists();
    }

    private void onWard() {
        if (listIsNullOrEmpty(mChildrenList)) return;
        if (!isFirstChildList) {
            childSelect = 0;
            isFirstChildList = true;
        }
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_child_select_child, null, false);
        dialog.setContentView(view);

        WardListAdapter adapter = new WardListAdapter(activity, mChildrenList, childSelect, false);
        RecyclerView rvWard = view.findViewById(R.id.rvWard);
        rvWard.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvWard.setAdapter(adapter);
        dialog.show();
        adapter.setOnItemClickListener(new WardListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ModelParentChildList m) {
                mChild = m;
                childSelect = position;

                String avatar = m.getChildAvatar();
                if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civWardAvatar);
                else civWardAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

                tvSelectWard.setText(m.getChildFirstName() + " " + m.getChildLastName());
                dialog.dismiss();
                btnSend.setBackgroundResource(R.drawable.btn_background_purple);
                btnSend.setPadding(25,0,25,0);
                btnSend.setEnabled(true);
            }
        });
    }

    ///////    SEND    ///////
    private void onSend() {
        String msg = "Прошу добавить моего ребенка \"" + mChild.getChildLogin()  + "\" в качестве Подопечного.";

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ParentId, PARENT_ID);
        jsonData.addProperty(F.MentorId, mMentor.getUserId());
        jsonData.addProperty(F.ChildId, mChild.getChildId());

        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mMentor.getUserId());
        jsonData.addProperty(F.Message, msg);
        //jsonData.addProperty(F.TargetId, mMentor.getUserId());

        String SOAP = SoapRequest(func_InterFormParentToMentor, jsonData.toString());
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
                            if (result.equals("0")) activity.alert.onToast("Произошла ошибка, попробуйте позже.");
                            else if (result.equals("1")) activity.alert.onToast("Заявка на добавление успешно отправлена.");
                            else if (result.equals("2")) activity.alert.onToast("Вы уже отправили заявку.");
                            else activity.alert.onToast(result);
                        }
                    });
                }
                else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.alert.onToast("Произошла ошибка, попробуйте позже.");
                        }
                    });
                }
                Intent intent = new Intent(activity, ParentActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });
    }

    public void setWardData(ModelMentorSponsorRoom m) {
        this.mMentor = m;
        String avatar = m.getAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

        tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        tvLogin.setText(m.getLogin());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChildLogin:
                onWard();
                break;
            case R.id.btnSend:
                onSend();
                break;
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onBack();
    }
}
