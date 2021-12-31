package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorAddSchoolListAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorAddStudentListAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;
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
import static kz.tech.smartgrades._Web.func_GetSchools;

public class MentorAddSchoolListFragment extends Fragment implements View.OnClickListener, MentorAddStudentListAdapter.OnItemClickListener, MentorAddSchoolListAdapter.OnItemCLickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelMentorData mMentorData;

    private ImageView ivBack;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;
    private String TempText;

    private TextView tvSchoolsNotFound;
    private RecyclerView rvSchools;
    private MentorAddSchoolListAdapter addSchoolListAdapter;


    public static MentorAddSchoolListFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", (Parcelable) mMentorData);
        MentorAddSchoolListFragment fragment = new MentorAddSchoolListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if(getArguments() != null) mMentorData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_mentor_add_school_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadWardOnAddition();
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvSchoolsNotFound = view.findViewById(R.id.tvSchoolsNotFound);

        cvSearch = view.findViewById(R.id.cvSearch);
        ivSearchShow = view.findViewById(R.id.ivSearchShow);
        ivSearchShow.setOnClickListener(this);
        ivSearchHide = view.findViewById(R.id.ivSearchHide);
        ivSearchHide.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                addSchoolListAdapter.onFilter(editable.toString());
                TempText = editable.toString();
            }
        });

        rvSchools = view.findViewById(R.id.rvSchools);
        addSchoolListAdapter = new MentorAddSchoolListAdapter(activity);
        addSchoolListAdapter.setOnItemClickListener(activity);
        rvSchools.setLayoutManager(new LinearLayoutManager(activity));
        rvSchools.setAdapter(addSchoolListAdapter);
    }
    private void onLoadWardOnAddition(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);

        String SOAP = SoapRequest(func_GetSchools, jsonData.toString());
        RequestBody body = RequestBody.create(SOAP, ContentType_XML);
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
                                ArrayList<ModelSchoolData> dataList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolData>>(){
                                }.getType());
                                if(!listIsNullOrEmpty(dataList)){
                                    tvSchoolsNotFound.setVisibility(View.GONE);
                                    addSchoolListAdapter.updateData(dataList);
                                }
                                else tvSchoolsNotFound.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.ivSearchShow:
                onSearchShowClick();
                break;
            case R.id.ivSearchHide:
                onSearchHideClick();
                break;
        }
    }
    private void onBack(){
        activity.onBackPressed();
    }
    private void onSearchShowClick(){
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        ivSearchHide.setVisibility(View.VISIBLE);
    }
    private void onSearchHideClick(){
        if(ivSearchShow.getVisibility() == View.VISIBLE) return;
        TempText = "";
        etSearch.setText(TempText);
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivSearchHide.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(ModelUser m){
        activity.onSetSelectStudentData(m);
        activity.onNextFragment();
    }
    @Override
    public void onItemClick(ModelSchoolData m){

    }
}
