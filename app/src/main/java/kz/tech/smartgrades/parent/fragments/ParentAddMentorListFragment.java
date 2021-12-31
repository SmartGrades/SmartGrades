package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentMentorListAdapter;
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
import static kz.tech.smartgrades._Web.func_GetMentorList;

public class ParentAddMentorListFragment extends Fragment implements View.OnClickListener,
        ParentMentorListAdapter.OnItemClickListener{

    private ParentActivity activity;
    private String lessonId;
    private String id;

    private ImageView ivBack;
    private RecyclerView rvMentorsList;
    private RecyclerView rvSelectedMentors;

    private CardView cvOk;

    private ArrayList<ModelMentorList> mentorList;
    private ParentMentorListAdapter parentMentorListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_mentor_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    public void setMentorListAdapter() {
        rvMentorsList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentMentorListAdapter = new ParentMentorListAdapter(activity);
        parentMentorListAdapter.setOnItemClickListener(this);
        rvMentorsList.setAdapter(parentMentorListAdapter);
        parentMentorListAdapter.updateData(mentorList);
    }

    public void loadMentorList(String lessonId, String id) {
        this.lessonId = lessonId;
        this.id = id;
        String SOAP = SoapRequest(func_GetMentorList, null);
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<ModelMentorList> mentorListData = new Gson().fromJson(result, new TypeToken<ArrayList<ModelMentorList>>() {
                            }.getType());
                            if (!listIsNullOrEmpty(mentorListData)) {
                                mentorList = mentorListData;
                                setMentorListAdapter();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        rvMentorsList = view.findViewById(R.id.rvMentorsList);
        rvSelectedMentors = view.findViewById(R.id.rvSelectedMentors);
        rvSelectedMentors.setVisibility(View.GONE);
        cvOk = view.findViewById(R.id.cvOk);
        cvOk.setVisibility(View.GONE);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    @Override
    public void onClick(ModelMentorList mMentor) {
        activity.setMentorModel2(mMentor, lessonId, id);
        activity.onNextFragment();
    }
}
