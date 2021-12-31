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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentMentorLessonListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentMentorSchoolListAdapter;
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
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_InterFormParentToMentor;

public class ParentAddMentorProfileFragment extends Fragment implements View.OnClickListener{

    private ModelMentorList mMentor;
    private ParentActivity activity;
    private String lessonId;
    private String id;
    private String parentId;
    private String childId;

    private ImageView ivAvatar;
    private ImageView ivNav;
    private TextView tvMentorName;
    private TextView tvStatus;
    private RecyclerView rvLessons;
    private Button btnAddMentor;
    private TextView tvBio;
    private TextView tvLogin;
    private RecyclerView rvSchoolList;
    private TextView tvMentorLessons;
    private TextView tvMentorSchool;

    private ParentMentorLessonListAdapter parentMentorLessonListAdapter;
    private ParentMentorSchoolListAdapter parentMentorSchoolListAdapter;

    public ParentAddMentorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_mentor_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void setProfileData() {
        ivAvatar.setImageResource(0);
        tvMentorName.setTextColor(activity.getResources().getColor(R.color.white));
        tvStatus.setTextColor(activity.getResources().getColor(R.color.white));
        tvBio.setText(activity.getResources().getString(R.string.no_bio));
        tvMentorLessons.setVisibility(View.VISIBLE);
        tvMentorSchool.setVisibility(View.VISIBLE);

        tvLogin.setText(mMentor.getLogin());
        tvMentorName.setText(mMentor.getFirstName() + " " + mMentor.getLastName());
        ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                321,
                getResources().getDisplayMetrics()
        );
        ivAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String avatar = mMentor.getAvatarOriginal();
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
            tvMentorName.setTextColor(activity.getResources().getColor(R.color.black));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.black));
        }
        if (!stringIsNullOrEmpty(mMentor.getAboutMe())) {
            tvBio.setText(mMentor.getAboutMe());
        }
        if (listIsNullOrEmpty(mMentor.getLessonsList())) {
            tvMentorLessons.setVisibility(View.INVISIBLE);
        }
        if (listIsNullOrEmpty(mMentor.getSchoolsList())) {
            tvMentorSchool.setVisibility(View.INVISIBLE);
        }

        setMentorLessons();
        setMentorSchools();
    }

    private void setMentorSchools() {
        rvSchoolList.setVisibility(View.VISIBLE);
        rvSchoolList.setLayoutManager(new GridLayoutManager(activity, 2));
        parentMentorSchoolListAdapter = new ParentMentorSchoolListAdapter(activity);
        rvSchoolList.setAdapter(parentMentorSchoolListAdapter);
        if (listIsNullOrEmpty(mMentor.getSchoolsList()))
            parentMentorSchoolListAdapter.clear();
        else parentMentorSchoolListAdapter.updateData(mMentor.getSchoolsList());
    }

    private void setMentorLessons() {
        rvLessons.setVisibility(View.VISIBLE);
        rvLessons.setLayoutManager(new GridLayoutManager(activity, 2));
        parentMentorLessonListAdapter = new ParentMentorLessonListAdapter(activity);
        rvLessons.setAdapter(parentMentorLessonListAdapter);
        if (listIsNullOrEmpty(mMentor.getLessonsList()))
            parentMentorLessonListAdapter.clear();
        else parentMentorLessonListAdapter.updateData(mMentor.getLessonsList());
    }

    private void initUI(View view) {
        ivAvatar = view.findViewById(R.id.civAvatar);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvMentorName = view.findViewById(R.id.tvMentorName);
        tvStatus = view.findViewById(R.id.tvStatus);
        rvLessons = view.findViewById(R.id.rvLessons);
        btnAddMentor = view.findViewById(R.id.btnAddMentor);
        btnAddMentor.setOnClickListener(this);
        tvBio = view.findViewById(R.id.tvBio);
        tvLogin = view.findViewById(R.id.tvLogin);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);
        tvMentorLessons = view.findViewById(R.id.tvMentorLessons);
        tvMentorSchool = view.findViewById(R.id.tvMentorSchool);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void addMentor() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.ChildLessonId, lessonId);
        jsonObject.addProperty(F.LessonId, id);
        jsonObject.addProperty(F.ChildId, childId);
        jsonObject.addProperty(F.SourceId, parentId);
        jsonObject.addProperty(F.TargetId, mMentor.getId());

        String SOAP = SoapRequest(func_InterFormParentToMentor, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                activity.alert.onToast(answer.getMessage());
                                activity.updatePresenter();
                                activity.setNewMentor(mMentor);
                                onBack();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
                break;
            case R.id.btnAddMentor:
                addMentor();
                break;
        }
    }

    public void setMentorModel2(ModelMentorList mMentor, String lessonId, String parentId, String childId, String id) {
        this.mMentor = mMentor;
        this.lessonId = lessonId;
        this.id = id;
        this.parentId = parentId;
        this.childId = childId;
        setProfileData();
    }
}