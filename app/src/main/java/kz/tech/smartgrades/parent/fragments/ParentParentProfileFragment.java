package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelUser;
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
import static kz.tech.smartgrades._Web.func_InterFormInFamilyGroup;

public class ParentParentProfileFragment extends Fragment implements View.OnClickListener {

    private ParentActivity activity;
    private ModelUser mParent;
    private String PARENT_ID;
    private int TIMER_SECOND;

    private ImageView ivNav;
    private TextView tvName;
    private TextView tvStatus;
    private Button btnAddParent;
    private TextView tvBio;
    private TextView tvLogin;
    private ImageView ivAvatar;

    public ParentParentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_add_parent_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void setTargetInfo() {
        tvName.setText(mParent.getFirstName() + " " + mParent.getLastName());
        tvBio.setText(activity.getResources().getString(R.string.no_bio));
        ivAvatar.setImageResource(0);

        tvLogin.setText(mParent.getLogin());
        String avatar = mParent.getAvatarOriginal();
        if(!stringIsNullOrEmpty(avatar)){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivAvatar);
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    321,
                    getResources().getDisplayMetrics()
            );
            tvName.setTextColor(activity.getResources().getColor(R.color.white));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.white));
        }
        else{
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    150,
                    getResources().getDisplayMetrics()
            );
            ivAvatar.requestLayout();
            tvName.setTextColor(activity.getResources().getColor(R.color.black));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.black));
        }

        if (!stringIsNullOrEmpty(mParent.getAboutMe())) {
            tvBio.setText(mParent.getAboutMe());
        }
    }

    private void initViews(View view) {
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnAddParent = view.findViewById(R.id.btnAddParent);
        btnAddParent.setOnClickListener(this);
        tvBio = view.findViewById(R.id.tvBio);
        tvLogin = view.findViewById(R.id.tvLogin);
        ivAvatar = view.findViewById(R.id.ivAvatar);
    }

    public void setParentModel(ModelUser mChild) {
        this.mParent = mChild;
        setTargetInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivNav:
                onBack();
                break;
            case R.id.btnAddParent:
                onAddParent();
                break;
        }
    }

    private void onAddParent() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mParent.getUserId());

        String SOAP = SoapRequest(func_InterFormInFamilyGroup, jsonData.toString());
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
                            if (!result.equals("null")) {
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                if (answer.isSuccess()) {
                                    btnAddParent.setClickable(false);
                                    onBack();
                                }
                                activity.alert.onToast(answer.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void onBack() {
        activity.onBackPressed();
    }
}