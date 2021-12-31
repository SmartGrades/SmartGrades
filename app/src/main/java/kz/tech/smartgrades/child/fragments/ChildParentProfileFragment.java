package kz.tech.smartgrades.child.fragments;

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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.child.ChildActivity;
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

public class ChildParentProfileFragment extends Fragment implements View.OnClickListener{

    private ChildActivity activity;

    private ModelUser mParentData;
    private String CHILD_ID;

    private ImageView ivBack;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvStatus;
    private Button btnAddParent;
    private TextView tvBio;
    private TextView tvLogin;

    public ChildParentProfileFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_parent_profile2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvName = view.findViewById(R.id.tvName);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnAddParent = view.findViewById(R.id.btnAddParent);
        btnAddParent.setOnClickListener(this);
        tvBio = view.findViewById(R.id.tvBio);
        tvLogin = view.findViewById(R.id.tvLogin);
    }

    public void onSetParentInfo(ModelUser mParentData){
        this.mParentData = mParentData;

        ivAvatar.setImageResource(0);
        tvBio.setText(activity.getResources().getString(R.string.no_bio));
        tvName.setTextColor(activity.getResources().getColor(R.color.white));
        tvStatus.setTextColor(activity.getResources().getColor(R.color.white));

        tvName.setText(mParentData.getFirstName() + " " + mParentData.getLastName());
        tvLogin.setText(mParentData.getLogin());
        String avatar = mParentData.getAvatarOriginal();
        if(!stringIsNullOrEmpty(avatar)){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivAvatar);
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    321,
                    getResources().getDisplayMetrics()
            );
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
        if (!stringIsNullOrEmpty(mParentData.getAboutMe())) {
            tvBio.setText(mParentData.getAboutMe());
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnAddParent:
                onAddParentClick();
                break;
        }
    }

    private void onBack(){
        activity.onBackPressed();
    }

    private void onAddParentClick(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, CHILD_ID);
        jsonData.addProperty(F.TargetId, mParentData.getUserId());
        //jsonData.addProperty(F.Message, "Приглашаю Вас в свою семейную группу.");

        String SOAP = SoapRequest(func_InterFormInFamilyGroup, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            activity.alert.onToast(answer.getMessage());
                            activity.onBackPressed();
                            if(answer.isSuccess()) activity.presenter.onStartPresenter();
                        }
                    });
                }
            }
        });
    }
}