package kz.tech.smartgrades.sponsor.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorChildSchoolsListAdapterAdapter;
import kz.tech.smartgrades.sponsor.dialogs.SponsorChildInterFormDialog;
import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
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
import static kz.tech.smartgrades._Web.func_InterFormOfSponsorship;

public class SponsorChildProfileFragment extends Fragment implements View.OnClickListener{

    private SponsorActivity activity;
    private String SPONSOR_ID;
    private ModelChildrenListForSponsorship mChild;

    private TextView tvBio;
    private TextView tvLogin;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvSchoolsLabel;
    private TextView tvChildStatus;
    private ImageView ivAvatar;
    private ImageView ivNav;
    private Button btnSponsoring;
    private RecyclerView rvSchoolList;

    private SponsorChildSchoolsListAdapterAdapter sponsorChildSchoolsListAdapterAdapter;

    public SponsorChildProfileFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SponsorActivity) getActivity();
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_sponsor_child_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        tvChildStatus = view.findViewById(R.id.tvChildStatus);
        tvSchoolsLabel = view.findViewById(R.id.tvSchoolsLabel);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);
        tvBio = view.findViewById(R.id.tvBio);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvName = view.findViewById(R.id.tvName);
        tvStatus = view.findViewById(R.id.tvStatus);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        btnSponsoring = view.findViewById(R.id.btnSponsoring);
        btnSponsoring.setOnClickListener(this);
    }

    public void setChildModel(ModelChildrenListForSponsorship mChild){
        this.mChild = mChild;
        setTargetInfo();
    }
    private void setTargetInfo(){
        ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                321,
                getResources().getDisplayMetrics()
        );
        ivAvatar.setImageResource(0);
        ivAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String avatar = mChild.getAvatarOriginal();
        if(!stringIsNullOrEmpty(avatar))
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivAvatar);
        else{
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    150,
                    getResources().getDisplayMetrics()
            );
            ivAvatar.requestLayout();
        }
        String name = mChild.getFirstName() + " " + mChild.getLastName();
        if (!stringIsNullOrEmpty(name))
            tvName.setText(name);
        else tvName.setText(mChild.getLogin());
        tvName.setTextColor(activity.getResources().getColor(R.color.white));
        tvLogin.setText(mChild.getLogin());
        if(!stringIsNullOrEmpty(mChild.getAboutMe())) tvBio.setText(mChild.getAboutMe());
        else tvBio.setText(R.string.no_information);
        if(!stringIsNullOrEmpty(mChild.getStateName()))
            tvChildStatus.setText(mChild.getStateName());
        else tvChildStatus.setText(R.string.no_information);

        rvSchoolList.setLayoutManager(new LinearLayoutManager(activity));
        sponsorChildSchoolsListAdapterAdapter = new SponsorChildSchoolsListAdapterAdapter(activity);
        rvSchoolList.setAdapter(sponsorChildSchoolsListAdapterAdapter);

        if(!listIsNullOrEmpty(mChild.getSchools())){
            tvSchoolsLabel.setVisibility(View.GONE);
            sponsorChildSchoolsListAdapterAdapter.updateData(mChild.getSchools());
        }
        else{
            sponsorChildSchoolsListAdapterAdapter.updateData(new ArrayList<>());
            tvSchoolsLabel.setVisibility(View.VISIBLE);
            tvSchoolsLabel.setText(R.string.no_information);
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivNav:
                onBack();
                break;
            case R.id.btnSponsoring:
                onAddClick();
                break;
        }
    }
    private void onBack(){
        activity.onBackPressed();
    }
    private void onAddClick(){
        SponsorChildInterFormDialog dialog = new SponsorChildInterFormDialog(activity, mChild);
        dialog.show();
        dialog.setOnItemClickListener(new SponsorChildInterFormDialog.OnItemClickListener(){
            @Override
            public void onItemClick(ModelInterForm model){
                String SOAP = SoapRequest(func_InterFormOfSponsorship, new Gson().toJson(model));
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
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    activity.alert.onToast(answer.getMessage());
                                    onBack();
                                    dialog.dismiss();
                                    activity.presenter.onUpdateData();
                                }
                            });
                        }
                    }
                });

            }
        });
    }
}