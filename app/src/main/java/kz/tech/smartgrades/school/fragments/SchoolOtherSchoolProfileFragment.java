package kz.tech.smartgrades.school.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolProfile;
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
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_GetSchoolProfile;

public class SchoolOtherSchoolProfileFragment extends Fragment implements View.OnClickListener{

    private SchoolActivity activity;
    private ModelSchoolProfile mProfile;

    private ImageView ivAvatar, ivAvatarMini;
    private CardView cvAvatar, cvBack;
    private TextView tvName, tvSchoolStatus, tvPhone, tvMail, tvAbout, tvLocation;

    public SchoolOtherSchoolProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (SchoolActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_other_school_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void setTargetInfo() {
        String avatar = mProfile.getAvatar();
        String avatarOriginal = mProfile.getAvatarOriginal();
        if(!stringIsNullOrEmpty(avatarOriginal)){
            cvAvatar.setVisibility(View.GONE);
            Picasso.get().load(avatarOriginal).fit().centerInside().into(ivAvatar);
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 321, getResources().getDisplayMetrics());
            tvName.setTextColor(activity.getResources().getColor(R.color.white));
        }
        else{
            ivAvatar.setImageResource(0);
            cvAvatar.setVisibility(View.VISIBLE);
            if(!stringIsNullOrEmpty(avatar))
                Picasso.get().load(avatar).fit().centerInside().into(ivAvatarMini);
            else ivAvatarMini.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
            ivAvatar.requestLayout();
            tvName.setTextColor(activity.getResources().getColor(R.color.black));
        }

        if (!stringIsNullOrEmpty(mProfile.getName())){
            tvName.setText(mProfile.getName());
        }
        if (!stringIsNullOrEmpty(mProfile.getPhone())){
            tvPhone.setText(mProfile.getPhone());
        } else {
            tvPhone.setText(getString(R.string.no_information));
        }
        if (!stringIsNullOrEmpty(mProfile.getMail())){
            tvMail.setText(mProfile.getMail());
        } else {
            tvMail.setText(R.string.Email_not_specified);
        }
        if (!stringIsNullOrEmpty(mProfile.getAddress())){
            tvLocation.setText(mProfile.getAddress());
        } else {
            tvLocation.setText(getString(R.string.no_information));
        }
        if (!stringIsNullOrEmpty(mProfile.getAboutMe())){
            tvAbout.setText(mProfile.getAboutMe());
            tvAbout.setTextColor(activity.getResources().getColor(R.color.black));
        } else {
            tvAbout.setText(getString(R.string.no_information));
            tvAbout.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
        }
        if (!stringIsNullOrEmpty(mProfile.getStateName())) {
            tvSchoolStatus.setVisibility(View.VISIBLE);
            tvSchoolStatus.setText(mProfile.getStateName());
        } else {
            tvSchoolStatus.setVisibility(View.GONE);
        }
    }

    private void initViews(View view) {
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivAvatarMini = view.findViewById(R.id.ivAvatarMini);
        cvAvatar = view.findViewById(R.id.cvAvatar);
        cvBack = view.findViewById(R.id.cvBack);
        cvBack.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvSchoolStatus = view.findViewById(R.id.tvSchoolStatus);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvMail = view.findViewById(R.id.tvMail);
        tvAbout = view.findViewById(R.id.tvAbout);
        tvLocation = view.findViewById(R.id.tvLocation);
    }

    public void loadModel(String id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.Id, id);
        String SOAP = SoapRequest(func_GetSchoolProfile, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){ }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ModelSchoolProfile m = new Gson().fromJson(result, ModelSchoolProfile.class);
                            if (m != null) {
                                mProfile = m;
                                setTargetInfo();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onBackPressed();
    }
}