package kz.tech.smartgrades.parent.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
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
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentAddMentorListAdapter;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDSetupRewardForGrades;
import kz.tech.smartgrades.parent.model.ModelGradesSettings;
import kz.tech.smartgrades.parent.model.ModelParentAddLessonForChild;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.adapters.LessonsListAdapter;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
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
import static kz.tech.smartgrades._Web.func_GetLessons;
import static kz.tech.smartgrades._Web.func_GetMentorList;
import static kz.tech.smartgrades._Web.func_ParentAddLessonForChild;

public class ParentAddLessonFragment extends Fragment implements View.OnClickListener,
        kz.tech.smartgrades.root.adapters.LessonsListAdapter.OnItemCLickListener,
        ParentAddMentorListAdapter.OnItemClickListener,
        DefaultDialog.OnClickListener{

    private ParentActivity activity;
    private ModelChildData mChildData;
    private ModelParentData mParent;
    private String PARENT_ID;
    private String interFormId;

    private ImageView ivBack;

    private CardView cvLessonsList;
    private TextView tvChooseFromList;
    private ImageView ivArrow;

    private CardView cvOwnLesson;
    private EditText etMyLesson;

    private CardView cvIam;
    private TextView tvIam;

    private CardView cvSecondParent;
    private TextView tvSecondParent;

    private RecyclerView rvMentorList;
    private CardView cvFindMentor;
    private TextView tvFindMentor;

    private CardView cvSmartGrades;
    private TextView tvSmartGrades;

    private CardView cvStandartGrades;
    private TextView tvStandartGrades;

    private CardView cvAddLesson;

    private ConstraintLayout clLabel;
    private ConstraintLayout clSearch;
    private EditText etSearch;
    private ImageView ivSearchArrow;
    private TextView tvSelectLesson;
    private LessonsListAdapter LessonsListAdapter;
    private RecyclerView rvLessonsList;
    private ArrayList<ModelLesson> LessonsList;
    private ModelLesson mLesson;

    private TextView tvOnSmartGrades;
    private CircleImageView civAvatar;

    private ArrayList<ModelMentorList> mentorList;

    private ArrayList<ModelMentorList> addedMentorList;
    private ParentAddMentorListAdapter parentAddMentorListAdapter;

    private ModelGradesSettings mRewardForGeades;

    private boolean[] isBtnOkEnable = new boolean[10];

    public static ParentAddLessonFragment newInstance(ModelChildData m){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", m);
        ParentAddLessonFragment fragment = new ParentAddLessonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        if(getArguments() != null) mChildData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_parent_add_lesson, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        tvOnSmartGrades = view.findViewById(R.id.tvOnSmartGrades);
        tvOnSmartGrades.setOnClickListener(this);
        clLabel = view.findViewById(R.id.clLabel);
        clLabel.setOnClickListener(this);
        clSearch = view.findViewById(R.id.clSearch);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                LessonsListAdapter.filter(arg0.toString());
            }
        });
        ivSearchArrow = view.findViewById(R.id.ivSearchArrow);
        ivSearchArrow.setOnClickListener(this);
        tvSelectLesson = view.findViewById(R.id.tvSelectLesson);

        LessonsListAdapter = new LessonsListAdapter();
        LessonsListAdapter.setOnItemCLickListener(this);
        rvLessonsList = view.findViewById(R.id.rvLessonsList);
        rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
        rvLessonsList.setAdapter(LessonsListAdapter);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        cvLessonsList = view.findViewById(R.id.cvLessonsList);
        cvLessonsList.setOnClickListener(this);
//      tvChooseFromList = view.findViewById(R.id.tvChooseFromList);
//      ivArrow = view.findViewById(R.id.ivArrow);
//      ivArrow.setOnClickListener(this);
        cvOwnLesson = view.findViewById(R.id.cvOwnLesson);
        etMyLesson = view.findViewById(R.id.etMyLesson);
        etMyLesson.setOnClickListener(this);
        etMyLesson.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(!editable.toString().isEmpty()){
                    cvLessonsList.setCardBackgroundColor(activity.getResources().getColor(R.color.white)); //
                    isBtnOkEnable[0] = false;
                    mLesson = null;
                    tvSelectLesson.setText("");
                    cvOwnLesson.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
                    etMyLesson.setTextColor(activity.getResources().getColor(R.color.white)); //---
                    isBtnOkEnable[1] = true;
                }
                else{
                    cvOwnLesson.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                    isBtnOkEnable[1] = false;
                }
                if (isBtnOkEnable[4]) {
                    setMentors(new ArrayList<>());
                    isBtnOkEnable[4] = false;
                }

                cvFindMentor.setCardBackgroundColor(activity.getResources().getColor(R.color.gray_average_dark));
                cvFindMentor.setClickable(false);
                tvFindMentor.setText(R.string.Teachers_can_only_be_added_for_general_subjects);
                tvFindMentor.setTextColor(activity.getResources().getColor(R.color.white));

                if (editable.toString().isEmpty()) {
                    //
                    cvFindMentor.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                    cvFindMentor.setClickable(true);
                    tvFindMentor.setText(R.string.find_and_add_mentor);
                    tvFindMentor.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                    //
                }


                isOkEnable();
            }
        });
        cvIam = view.findViewById(R.id.cvIam);
        cvIam.setOnClickListener(this);
        tvIam = view.findViewById(R.id.tvIam);
        cvSecondParent = view.findViewById(R.id.cvSecondParent);
        cvSecondParent.setOnClickListener(this);
        tvSecondParent = view.findViewById(R.id.tvSecondParent);
        rvMentorList = view.findViewById(R.id.rvMentorList);
        cvFindMentor = view.findViewById(R.id.cvFindMentor);
        cvFindMentor.setOnClickListener(this);
        tvFindMentor = view.findViewById(R.id.tvFindMentor);
        cvSmartGrades = view.findViewById(R.id.cvSmartGrades);
        cvSmartGrades.setOnClickListener(this);
        tvSmartGrades = view.findViewById(R.id.tvSmartGrades);
        cvStandartGrades = view.findViewById(R.id.cvStandartGrades);
        cvStandartGrades.setOnClickListener(this);
        tvStandartGrades = view.findViewById(R.id.tvStandartGrades);
        civAvatar = view.findViewById(R.id.civAvatar);
        if (mChildData != null && !stringIsNullOrEmpty(mChildData.getAvatar())) {
            Picasso.get().load(mChildData.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        }
//        cvAddLesson = view.findViewById(R.id.cvAddLesson);
//        cvAddLesson.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.clLabel:
                onShowLessonListClick();
                break;
            case R.id.ivSearchArrow:
                onHideLessonsListClick();
                break;
            case R.id.ivBack:
                onBack();
                break;
            case R.id.cvIam:
                onIamClick();
                break;
            case R.id.cvSecondParent:
                onSecondParentClick();
                break;
            case R.id.cvFindMentor:
                onFindMentor();
                break;
            case R.id.cvSmartGrades:
                if (listIsNullOrEmpty(activity.getMParentData().getPrivateAccount().getCards())) {
                    DefaultDialog dialog = new DefaultDialog(activity);
                    dialog.setCancel(activity.getResources().getColor(R.color.gray_average_dark), getString(R.string.cancel));
                    dialog.setExit(activity.getResources().getColor(R.color.blue_sea), getString(R.string.bind));
                    dialog.setMessage(getString(R.string.To_create_an_item_with_smart_ratings_link_card));
                    dialog.setOnClickListener(this);
                    dialog.show();
                    break;
                }
                onLessonTypeClick(1);
                break;
            case R.id.cvStandartGrades:
                onLessonTypeClick(0);
                break;
            case R.id.tvOnSmartGrades:
                onOkClick();
                break;
            case R.id.etMyLesson:
                onMyLessonClick();
        }
    }

    private void onMyLessonClick() {
        if (isBtnOkEnable[4]) {
            //предупреждение
            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message to be shown");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        isOkEnable(); ///
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void onNextFragment() {
        activity.onNextFragment();
    }

    private void onIamClick(){
        isBtnOkEnable[2] = !isBtnOkEnable[2];

        cvIam.setCardBackgroundColor(isBtnOkEnable[2] ? activity.getResources().getColor(R.color.green_background)
                : activity.getResources().getColor(R.color.white));

        if (isBtnOkEnable[2]) tvIam.setTextColor(activity.getResources().getColor(R.color.white));
        else tvIam.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));

        isOkEnable();
    }
    private void onSecondParentClick(){
        isBtnOkEnable[3] = !isBtnOkEnable[3];

        cvSecondParent.setCardBackgroundColor(isBtnOkEnable[3] ? activity.getResources().getColor(R.color.green_background)
                : activity.getResources().getColor(R.color.white));

        if (isBtnOkEnable[3]) tvSecondParent.setTextColor(activity.getResources().getColor(R.color.white));
        else tvSecondParent.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
        isOkEnable();
    }
    private void onFindMentor(){

        if (!listIsNullOrEmpty(mentorList)) {
            activity.setMentorList(mentorList);
            isBtnOkEnable[4] = true;
        } else {
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
                                    activity.setMentorList(mentorListData);
                                    mentorList = mentorListData;
                                    isBtnOkEnable[4] = true;
                                }
                            }
                        });
                    }
                }
            });
        }
        onNextFragment();
        isOkEnable();
    }
    private void onLessonTypeClick(int type){
        if(type == 0){
            if(!isBtnOkEnable[5]){
                cvStandartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
                tvStandartGrades.setTextColor(activity.getResources().getColor(R.color.white));
                if(isBtnOkEnable[6]){
                    cvSmartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                    tvSmartGrades.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                    isBtnOkEnable[6] = !isBtnOkEnable[6];
                }
            }
            else {
                cvStandartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                tvStandartGrades.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            }
            isBtnOkEnable[5] = !isBtnOkEnable[5];
        }
        else if(type == 1){
            cvSmartGrades.setClickable(false);
            BSDSetupRewardForGrades dialog = new BSDSetupRewardForGrades(activity, "1", mRewardForGeades);
            dialog.show();
            dialog.setOnItemClickListener(new BSDSetupRewardForGrades.OnItemClickListener() {
                @Override
                public void onClick(ModelGradesSettings m) {
                    dialog.dismiss();
                    mRewardForGeades = m;

                    if(!isBtnOkEnable[6]){
                        cvSmartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
                        tvSmartGrades.setTextColor(activity.getResources().getColor(R.color.white));
                        if(isBtnOkEnable[5]){
                            cvStandartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                            tvStandartGrades.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                            isBtnOkEnable[5] = false;
                        }
                    }
                    else {
                        cvSmartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                        tvSmartGrades.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                    }
                    isBtnOkEnable[6] = !isBtnOkEnable[6];
                    isOkEnable();
                }

                @Override
                public void onCancel() {
                    dialog.dismiss();
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    cvSmartGrades.setClickable(true);
                }
            });

//            if(!isBtnOkEnable[6]){
//                cvSmartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
//                if(isBtnOkEnable[5]){
//                    cvStandartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
//                    isBtnOkEnable[5] = !isBtnOkEnable[5];
//                }
//            }
//            else cvSmartGrades.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
//            isBtnOkEnable[6] = !isBtnOkEnable[6];
        }
        isOkEnable();
    }
    private void isOkEnable(){
//        if(mSelectClass != null){
//            btnSend.setEnabled(true);
//            btnSend.setBackgroundResource(R.drawable.btn_background_purple);
//        }
//        else{
//            btnSend.setEnabled(false);
//            btnSend.setBackgroundResource(R.drawable.btn_background_purple_alpha);
//        }
        if((isBtnOkEnable[0] || isBtnOkEnable[1]) &&
                (isBtnOkEnable[2] || isBtnOkEnable[3] || isBtnOkEnable[4]) &&
                (isBtnOkEnable[5] || isBtnOkEnable[6])){
            tvOnSmartGrades.setClickable(true);
            tvOnSmartGrades.setBackgroundResource(R.drawable.btn_background_purple);
            int _dp = Convert.DpToPx(activity, 20);
            tvOnSmartGrades.setPadding(_dp,_dp,_dp,_dp);
        }
        else{
            tvOnSmartGrades.setClickable(false);
            tvOnSmartGrades.setBackgroundResource(R.drawable.btn_background_purple_alpha);
            int _dp = Convert.DpToPx(activity, 20);
            tvOnSmartGrades.setPadding(_dp,_dp,_dp,_dp);
        }
    }
    private void onOkClick(){
        ModelParentAddLessonForChild model = new ModelParentAddLessonForChild();
        model.setSourceId(PARENT_ID);
        model.setChildId(mChildData.getId());
        if (mLesson != null) {
            model.setLessonId(mLesson.getLessonId());
            model.setLessonName(mLesson.getLessonName());
        } else {
            model.setLessonName(etMyLesson.getText().toString());
        }

        if (interFormId != null) {
            model.setInterFormId(interFormId);
        }

        if (isBtnOkEnable[5]) {
            model.setLessonType(0);
        } else if (isBtnOkEnable[6]) {
            model.setLessonType(1);
            model.setGradesSettings(mRewardForGeades);
        }

        ArrayList<ModelMentorList> newMentorList = new ArrayList<>();

        ModelMentorList modelIam = new ModelMentorList();
        modelIam.setId(PARENT_ID);
        if (isBtnOkEnable[2]) newMentorList.add(modelIam);

        if (isBtnOkEnable[3]) {
            ModelMentorList modelSecondParent = new ModelMentorList();
            if (mParent.getFamilyGroup().getParents().get(0).getUserId().equals(PARENT_ID))
                modelSecondParent.setId(mParent.getFamilyGroup().getParents().get(1).getUserId());
            else
                modelSecondParent.setId(mParent.getFamilyGroup().getParents().get(0).getUserId());
            newMentorList.add(modelSecondParent);
        }
        //else newMentorList.add(null);

        if (addedMentorList != null) newMentorList.addAll(addedMentorList);
        model.setMentors(newMentorList);

        String SOAP = SoapRequest(func_ParentAddLessonForChild, new Gson().toJson(model));
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
                                onBack();
                                activity.updatePresenter();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemClick(ModelLesson m){
        mLesson = m;
        LessonsListAdapter.ClearData();
        tvSelectLesson.setText(m.getLessonName());
        tvSelectLesson.setTextColor(activity.getResources().getColor(R.color.white));
        isBtnOkEnable[0] = true;
        onHideLessonsListClick();
        isOkEnable();
    }
    private void onShowLessonListClick(){
        cvLessonsList.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
        if(listIsNullOrEmpty(LessonsList)){
            String SOAP = SoapRequest(func_GetLessons, null);
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
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                if(!result.equals("null")){
                                    LessonsList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelLesson>>(){
                                    }.getType());
                                    LessonsListAdapter.updateData(LessonsList);
                                    clLabel.setVisibility(View.GONE);
                                    clSearch.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            });
        }
        else{
            LessonsListAdapter.updateData(LessonsList);
            clLabel.setVisibility(View.GONE);
            clSearch.setVisibility(View.VISIBLE);
        }
    }
    private void onHideLessonsListClick(){
        etSearch.setText("");
        LessonsListAdapter.ClearData();
        clLabel.setVisibility(View.VISIBLE);
        clSearch.setVisibility(View.GONE);
        if(isBtnOkEnable[0]){
            etMyLesson.setText("");
            cvLessonsList.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
        }
    }

    public void setData(ModelChildData mChildData) {
        this.mChildData = mChildData;
    }

    public void setMentors(ArrayList<ModelMentorList> addedMentorList) {
        this.addedMentorList = addedMentorList;

        if (listIsNullOrEmpty(addedMentorList)) {
            cvFindMentor.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
            tvFindMentor.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tvFindMentor.setText(R.string.find_and_add_mentor);
            isBtnOkEnable[4] = false;
        } else {
            cvFindMentor.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
            tvFindMentor.setTextColor(activity.getResources().getColor(R.color.white));
            isBtnOkEnable[4] = true;
        }

        rvMentorList.setVisibility(View.VISIBLE);
        rvMentorList.setLayoutManager(new GridLayoutManager(activity, 2));
        parentAddMentorListAdapter = new ParentAddMentorListAdapter(activity);
        parentAddMentorListAdapter.setOnItemClickListener(this);
        rvMentorList.setAdapter(parentAddMentorListAdapter);
        parentAddMentorListAdapter.updateData(addedMentorList);
    }

    @Override
    public void onRemoveSelectedClick(int p) {
        addedMentorList.remove(p);
        setMentors(addedMentorList);
        activity.setNewSelectedMentorList(addedMentorList);
    }

    public void setParentModel(ModelParentData mParent) {
        this.mParent = mParent;
        if (mParent.getFamilyGroup().getParents().size() <= 1) {
            cvSecondParent.setCardBackgroundColor(activity.getResources().getColor(R.color.gray_average_dark));
            cvSecondParent.setClickable(false);
            tvSecondParent.setTextColor(activity.getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onCancelDialog() {

    }

    @Override
    public void onExitDialog() {
        activity.onNavCashExtract();
        activity.closeDrawer();
    }

    public void setInterFormId(String interFormId) {
        this.interFormId = interFormId;
    }
}
