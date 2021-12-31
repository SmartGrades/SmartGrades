package kz.tech.smartgrades.school.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.root.dialogs.DEditProfileField;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.ClassesTagAdapter;
import kz.tech.smartgrades.school.adaptes.LessonsTagAdapterV2;
import kz.tech.smartgrades.school.adaptes.SchoolEditTeacherSchoolListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileLessons;
import kz.tech.smartgrades.school.models.ModelTeacherProfileSchools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_DeleteTeacherData;
import static kz.tech.smartgrades._Web.func_GetTeacherProfile;
import static kz.tech.smartgrades._Web.func_SchoolDeleteTeacher;
import static kz.tech.smartgrades._Web.func_SchoolDeleteTeacherProfile;
import static kz.tech.smartgrades._Web.func_SchoolEditTeacherProfile;

public class SchoolEditTeacherProfileFragment extends Fragment implements View.OnClickListener, LessonsTagAdapterV2.OnItemCLickListener, SchoolEditTeacherSchoolListAdapter.OnItemClickListener {

    private SchoolActivity activity;
    private ModelTeacherProfile mTeacher;
    private String SCHOOL_ID;
    private String MENTOR_ID;

    private CardView cvBack;
    private ConstraintLayout clLoading;
    private CardView cvEditAvatar;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvLoginLabel;
    private TextView tvLogin;
    private TextView tvPhoneLabel;
    private TextView tvPhone;
    private TextView tvEmailLabel;
    private TextView tvEmail;
    private TextView tvBioLabel;
    private TextView tvBio;
    private TextView tvLocationLabel;
    private TextView tvLocation;
    private RecyclerView rvSchoolList;
    private ImageView ivAvatar;
    private TextView tvSchool;
    private CardView cvAvatar;
    private CircleImageView civAvatar;

    private TextView tvNoLessons;
    private RecyclerView rvLessons;
    private TextView tvNoClasses;
    private RecyclerView rvClasses;
    private ImageView ivEditClasses;
    private ImageView ivEditName;
    private ImageView ivOptions;

    private Bitmap SelectAvatar;
    private Bitmap Avatar, AvatarOriginal;
    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private ProgressBar pgAvatarOriginalUpload, pgAvatarUpload;
    private int SELECT_TYPE = 0;
    private boolean IsSelectPhoto = false;
    private boolean[] isLoadSuccess = new boolean[2];

    private SchoolEditTeacherSchoolListAdapter schoolEditTeacherSchoolListAdapter;

    public SchoolEditTeacherProfileFragment() {
    }

    public void setData(ModelSchoolTeacher m) {
        this.MENTOR_ID = m.getId();
        loadModel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (SchoolActivity) getActivity();
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_edit_teacher_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void setTargetInfo() {
        ivAvatar.setImageResource(0);
        tvName.setTextColor(activity.getResources().getColor(R.color.white));
        tvStatus.setTextColor(activity.getResources().getColor(R.color.white));
        pgAvatarOriginalUpload.setVisibility(View.GONE);
        pgAvatarUpload.setVisibility(View.GONE);

        if (!stringIsNullOrEmpty(mTeacher.getFirstName()) && !stringIsNullOrEmpty(mTeacher.getLastName())) {
            tvName.setText(mTeacher.getFirstName() + " " + mTeacher.getLastName());
        }

        String avatar = mTeacher.getAvatarOriginal();
        if(!stringIsNullOrEmpty(avatar)){
            Picasso.get().load(avatar).fit().centerInside().into(ivAvatar);
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    321,
                    activity.getResources().getDisplayMetrics()
            );
            cvAvatar.setVisibility(View.GONE);
        }
        else{
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    150,
                    getResources().getDisplayMetrics()
            );
            cvAvatar.setVisibility(View.VISIBLE);
            ivAvatar.requestLayout();
            tvName.setTextColor(activity.getResources().getColor(R.color.black));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.black));
        }

        if (!stringIsNullOrEmpty(mTeacher.getUserId())) {
            ivEditName.setVisibility(View.GONE);
            cvEditAvatar.setVisibility(View.GONE);

            if (!stringIsNullOrEmpty(mTeacher.getLogin())) {
                tvLogin.setVisibility(View.VISIBLE);
                tvLoginLabel.setVisibility(View.VISIBLE);
                tvLogin.setText(mTeacher.getLogin());
            } else tvLogin.setText(R.string.Not_indicated);

            if (!stringIsNullOrEmpty(mTeacher.getPhone())) {
                tvPhone.setVisibility(View.VISIBLE);
                tvPhoneLabel.setVisibility(View.VISIBLE);
                tvPhone.setText(mTeacher.getPhone());
            } else tvPhone.setText(R.string.Not_indicated);

            if (!stringIsNullOrEmpty(mTeacher.getMail())) {
                tvEmail.setVisibility(View.VISIBLE);
                tvEmailLabel.setVisibility(View.VISIBLE);
                tvEmail.setText(mTeacher.getMail());
            } else tvEmail.setText(R.string.Email_not_specified);

            if (!stringIsNullOrEmpty(mTeacher.getAddress())) {
                tvLocation.setVisibility(View.VISIBLE);
                tvLocationLabel.setVisibility(View.VISIBLE);
                tvLocation.setText(mTeacher.getAddress());
            } else tvLocation.setText(R.string.Not_indicated);

            if (!stringIsNullOrEmpty(mTeacher.getAboutMe())) {
                tvBio.setVisibility(View.VISIBLE);
                tvBioLabel.setVisibility(View.VISIBLE);
                tvBio.setText(mTeacher.getAboutMe());
            } else tvBio.setText(activity.getResources().getString(R.string.no_bio));

            if (!listIsNullOrEmpty(mTeacher.getSchools())) {
                tvSchool.setVisibility(View.VISIBLE);
                setSchools();
            } else {
                tvSchool.setVisibility(View.GONE);
                rvSchoolList.setAdapter(null);
            }
        } else {
            ivEditName.setVisibility(View.VISIBLE);
            cvEditAvatar.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            tvLoginLabel.setVisibility(View.GONE);
            tvPhone.setVisibility(View.GONE);
            tvPhoneLabel.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);
            tvEmailLabel.setVisibility(View.GONE);
            tvLocation.setVisibility(View.GONE);
            tvLocationLabel.setVisibility(View.GONE);
            tvBio.setVisibility(View.GONE);
            tvBioLabel.setVisibility(View.GONE);
            tvSchool.setVisibility(View.GONE);
            rvSchoolList.setAdapter(null);
        }

        if (!listIsNullOrEmpty(mTeacher.getLessons())) {
            LessonsTagAdapterV2 adapter = new LessonsTagAdapterV2();
            rvLessons.setAdapter(adapter);
            rvLessons.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
            adapter.UpdateData(mTeacher.getLessons());
            tvNoLessons.setVisibility(View.GONE);
        } else {
            tvNoLessons.setVisibility(View.VISIBLE);
            rvLessons.setAdapter(null);
        }

        if (!listIsNullOrEmpty(mTeacher.getClasses())) {
            ClassesTagAdapter adapter = new ClassesTagAdapter(activity);
            rvClasses.setAdapter(adapter);
            rvClasses.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
            adapter.UpdateData(mTeacher.getClasses());
            tvNoClasses.setVisibility(View.GONE);
            ivEditClasses.setVisibility(View.VISIBLE);
        } else {
            tvNoClasses.setVisibility(View.VISIBLE);
            ivEditClasses.setVisibility(View.GONE);
            rvClasses.setAdapter(null);
        }
    }

    private void setSchools() {
        rvSchoolList.setVisibility(View.VISIBLE);
        rvSchoolList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        schoolEditTeacherSchoolListAdapter = new SchoolEditTeacherSchoolListAdapter(activity);
        schoolEditTeacherSchoolListAdapter.setOnItemClickListener(this);
        rvSchoolList.setAdapter(schoolEditTeacherSchoolListAdapter);
        if (listIsNullOrEmpty(mTeacher.getSchools()))
            schoolEditTeacherSchoolListAdapter.clear();
        else schoolEditTeacherSchoolListAdapter.updateData(mTeacher.getSchools());
    }

    private void initViews(View view) {
        clLoading = view.findViewById(R.id.clLoading);
        cvBack = view.findViewById(R.id.cvBack);
        cvAvatar = view.findViewById(R.id.cvAvatar);
        civAvatar = view.findViewById(R.id.civAvatar);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);
        pgAvatarOriginalUpload = view.findViewById(R.id.pgAvatarOriginalUpload);
        cvBack.setOnClickListener(this);
        cvEditAvatar = view.findViewById(R.id.cvEditAvatar);
        cvEditAvatar.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvName.setOnClickListener(this);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvLoginLabel = view.findViewById(R.id.tvLoginLabel);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvPhoneLabel = view.findViewById(R.id.tvPhoneLabel);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvEmailLabel = view.findViewById(R.id.tvEmailLabel);
        tvBio = view.findViewById(R.id.tvBio);
        tvBioLabel = view.findViewById(R.id.tvBioLabel);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvLocationLabel = view.findViewById(R.id.tvLocationLabel);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvSchool = view.findViewById(R.id.tvSchool);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);

        tvNoLessons = view.findViewById(R.id.tvNoLessons);
        rvLessons = view.findViewById(R.id.rvLessons);
        tvNoClasses = view.findViewById(R.id.tvNoClasses);
        tvNoClasses.setOnClickListener(this);
        rvClasses = view.findViewById(R.id.rvClasses);
        ivEditClasses = view.findViewById(R.id.ivEditClasses);
        ivEditClasses.setOnClickListener(this);
        ivEditName = view.findViewById(R.id.ivEditName);
        ivEditName.setOnClickListener(this);
        ivOptions = view.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(this);

        cvAvatar.setVisibility(View.GONE);
    }

    public void loadModel() {
        clLoading.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.Id, MENTOR_ID);
        jsonObject.addProperty(F.SchoolId, SCHOOL_ID);
        String SOAP = SoapRequest(func_GetTeacherProfile, jsonObject.toString());
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
                            ModelTeacherProfile teacherProfile = new Gson().fromJson(result, ModelTeacherProfile.class);
                            if (teacherProfile != null) {
                                mTeacher = teacherProfile;
                                setTargetInfo();
                                clLoading.setVisibility(View.GONE);
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
            case R.id.cvEditAvatar:
                onEditAvatar();
                break;
            case R.id.ivEditName:
                onEditName();
                break;
            case R.id.ivEditClasses:
            case R.id.tvNoClasses:
                onEditClasses();
                break;
            case R.id.ivOptions:
                onOption();
                break;
        }
    }

    private void onEditName() {
        ModelUser model = new ModelUser();
        model.setFirstName(mTeacher.getFirstName());
        model.setLastName(mTeacher.getLastName());

        DEditProfileField dialog = new DEditProfileField(activity, model, "Name");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                mUser.setId(MENTOR_ID);
                mUser.setSchoolId(SCHOOL_ID);
                String SOAP = SoapRequest(func_SchoolEditTeacherProfile, new Gson().toJson(mUser));
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){ }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(answer.isSuccess()){
                                        dialog.dismiss();
                                        activity.alert.hideKeyboard(activity);
                                        activity.alert.onToast(answer.getMessage());
                                        activity.updatePresenter();
                                        loadModel();
                                    }
                                    else activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
            @Override
            public void onCancelClick(){
                dialog.dismiss();
                activity.alert.hideKeyboard(activity);
            }
        });
    }

    private void onOption() {
        PopupWindow popupWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_delete_mentor, null);
        TextView tvDelete = view.findViewById(R.id.tvDelete);
        if (stringIsNullOrEmpty(mTeacher.getUserId())) {
            tvDelete.setText(R.string.Delete_profile);
        }
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(ivOptions);
        view.findViewById(R.id.tvDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteMentor();
                popupWindow.dismiss();
            }
        });
    }
    private void onDeleteMentor() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, MENTOR_ID);
        jsonData.addProperty(F.SchoolId, SCHOOL_ID);
        if (!stringIsNullOrEmpty(mTeacher.getUserId())) {
            String SOAP = SoapRequest(func_SchoolDeleteTeacher, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.code() >= 200 && response.code() <= 300) {
                        String xml = response.body().string();
                        String result = _Web.XMLToJson(xml);
                        activity.runOnUiThread(new Runnable() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            public void run() {
                                activity.alert.onToast(answer.getMessage());
                                if (answer.isSuccess()) {
                                    activity.updatePresenter();
                                    loadModel();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            String SOAP = SoapRequest(func_SchoolDeleteTeacherProfile, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.code() >= 200 && response.code() <= 300) {
                        String xml = response.body().string();
                        String result = _Web.XMLToJson(xml);
                        activity.runOnUiThread(new Runnable() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            public void run() {
                                activity.alert.onToast(answer.getMessage());
                                if (answer.isSuccess()) {
                                    activity.updatePresenter();
                                    onBack();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    private void onEditClasses() {
        activity.onNextFragment();
        activity.setTeacherClasses(mTeacher.getClasses(), mTeacher);
    }
    private void onEditAvatar() {
        onPhotoSetting();
    }

    private void onPhotoSetting(){
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_photo_settings, null, false);
        dialog.setContentView(viewDialog);

        LinearLayout llDrawing = viewDialog.findViewById(R.id.llDrawing);
        LinearLayout llGallery = viewDialog.findViewById(R.id.llGallery);
        LinearLayout llCamera = viewDialog.findViewById(R.id.llCamera);
        LinearLayout llDelete = viewDialog.findViewById(R.id.llDelete);

        if(!stringIsNullOrEmpty(mTeacher.getAvatarOriginal())) llDelete.setVisibility(View.VISIBLE);
        else{
            if(!stringIsNullOrEmpty(mTeacher.getAvatar()))
                llDelete.setVisibility(View.VISIBLE);
            else llDelete.setVisibility(View.GONE);
        }

        llDrawing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                int[] images_parents = {R.drawable.parent_1, R.drawable.parent_2, R.drawable.parent_3, R.drawable.parent_4,
                        R.drawable.parent_5, R.drawable.parent_6, R.drawable.parent_7, R.drawable.parent_8,
                        R.drawable.parent_9, R.drawable.parent_10, R.drawable.parent_11, R.drawable.parent_12,
                        R.drawable.parent_13, R.drawable.parent_14, R.drawable.parent_15, R.drawable.parent_16,
                        R.drawable.parent_17, R.drawable.parent_18, R.drawable.parent_19, R.drawable.parent_20,
                        R.drawable.parent_21, R.drawable.parent_22, R.drawable.parent_23, R.drawable.parent_24,
                        R.drawable.parent_25, R.drawable.parent_26, R.drawable.parent_27, R.drawable.parent_28,
                        R.drawable.parent_29, R.drawable.parent_30};

                BottomSheetDialog dialog = new BottomSheetDialog(activity);
                View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_drawing, null, false);
                dialog.setContentView(viewDialog);
                View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                bottomSheet.setLayoutParams(layoutParams);
                layoutParams.height = (_System.getWindowHeight(activity));
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                dialog.show();

                DrawingSelectAdapter drawingSelectAdapter = new DrawingSelectAdapter(activity);
                RecyclerView rvAvatarsList = viewDialog.findViewById(R.id.rvAvatarsList);
                CardView cvBack = viewDialog.findViewById(R.id.cvBack);
                cvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                rvAvatarsList.setLayoutManager(new GridLayoutManager(activity, 5));
                rvAvatarsList.setAdapter(drawingSelectAdapter);
                drawingSelectAdapter.UpdateData(images_parents);
                drawingSelectAdapter.ShowData();
                drawingSelectAdapter.setOnItemClickListener(new DrawingSelectAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(ImageView img){
                        dialog.dismiss();
                        img.setDrawingCacheEnabled(true);
                        SelectAvatar = Bitmap.createBitmap(img.getDrawingCache());
                        setPhoto(SelectAvatar, false);
                    }
                });
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
        llDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                SelectAvatar = null;
                setPhoto(SelectAvatar, false);
            }
        });

        dialog.show();
    }
    private void ToMakeAPhoto(int Type){
        SELECT_TYPE = Type;
        if(getPermissionsToUseCamera()){
            Intent PictureIntent = null;
            if(Type == REQUEST_TAKE_PHOTO)
                PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            else if(Type == REQUEST_LOAD_PHOTO){
                PictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            if(PictureIntent.resolveActivity(activity.getPackageManager()) != null){
                if(Type == REQUEST_TAKE_PHOTO){
                    try{
                        File photoFile = createImageFile();
                        if(photoFile != null){
                            Uri photoURI = FileProvider.getUriForFile(activity, "kz.tech.esparta.fileprovider", photoFile);
                            mPhotoFile = photoFile;
                            PictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        }
                        startActivityForResult(PictureIntent, REQUEST_TAKE_PHOTO);
                    } catch(IOException ex){
                    }
                }
                else if(Type == REQUEST_LOAD_PHOTO)
                    startActivityForResult(Intent.createChooser(PictureIntent, "Select Picture"), REQUEST_LOAD_PHOTO);
            }
        }
    }
    public boolean getPermissionsToUseCamera(){
        String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean check = true;
        for(String p : permissions)
            if(ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED)
                check = false;
        if(!check) requestPermissions(permissions, PERMISSIONS_REQUEST);
        return check;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == PERMISSIONS_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && SELECT_TYPE == REQUEST_TAKE_PHOTO){
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
            else if(grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED && SELECT_TYPE == REQUEST_LOAD_PHOTO){
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(mFileName, ".jpg", storageDir);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            Uri imageUri = null;
            if(requestCode == REQUEST_TAKE_PHOTO) imageUri = Uri.fromFile(mPhotoFile);
            else if(requestCode == REQUEST_LOAD_PHOTO && null != data) imageUri = data.getData();
            try{
                startCrop(imageUri);
            } catch(Exception ignored){
            }
        }
    }
    private void startCrop(@NonNull Uri uri) {
        String destinationFileName = "AvatarForUCrop.png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(activity.getCacheDir(), destinationFileName)));
        uCrop.withAspectRatio(480, 480);
        uCrop.withMaxResultSize(480, 480);
        uCrop.start(activity);
    }
    public void prepareAvatar(Uri imageUri) {
        try {
            InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
            setPhoto(BitmapFactory.decodeStream(imageStream), true);
        }
        catch (Exception ignored){ }
    }
    public void setPhoto(Bitmap image, boolean isSaveOriginal){
        if(image != null){
            Bitmap avatarOriginal = Bitmap.createBitmap(image.getWidth(),
                    image.getHeight(), image.getConfig());
            Canvas canvas = new Canvas(avatarOriginal);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(image, 0F, 0F, null);

            IsSelectPhoto = true;
            isNextEnable();

            Avatar = Bitmap.createScaledBitmap(avatarOriginal, 128, 128, true);
            if(isSaveOriginal){
                AvatarOriginal = Bitmap.createScaledBitmap(avatarOriginal, 480, 480, true);
                ivAvatar.setImageBitmap(AvatarOriginal);
                ivAvatar.setAlpha(0.5f);
                pgAvatarOriginalUpload.setVisibility(View.VISIBLE);
                cvAvatar.setVisibility(View.GONE);
            }
            else{
                AvatarOriginal = null;
                civAvatar.setImageBitmap(Avatar);
                civAvatar.setAlpha(0.5f);
                pgAvatarUpload.setVisibility(View.VISIBLE);
                cvAvatar.setVisibility(View.VISIBLE);
                ivAvatar.setImageResource(0);
                ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        150,
                        getResources().getDisplayMetrics());
            }

            UploadImage();
        }
        else{
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(F.Id, MENTOR_ID);
            jsonData.addProperty(F.SchoolId, SCHOOL_ID);
            jsonData.addProperty(F.Avatar, "1");

            String SOAP = SoapRequest(func_DeleteTeacherData, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(final Call call, IOException e){ }
                @Override
                public void onResponse(Call call, final Response response) throws IOException{
                    if(response.isSuccessful()){
                        String result = _Web.XMLToJson(response.body().string());
                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                        activity.runOnUiThread(new Runnable(){
                            public void run(){
                                if(answer.isSuccess()){
                                    IsSelectPhoto = false;
                                    mTeacher.setAvatar(null);
                                    mTeacher.setAvatarOriginal(null);
                                    setTargetInfo();
                                    activity.updatePresenter();
                                    activity.alert.onToast(activity.getResources().getString(R.string.successfully_deleted));
                                    pgAvatarOriginalUpload.setVisibility(View.GONE);
                                    pgAvatarUpload.setVisibility(View.GONE);
                                }
                                else
                                    activity.alert.onToast(activity.getResources().getString(R.string.try_again));
                            }
                        });
                    }
                }
            });
        }
    }
    private void UploadImage(){
        ByteArrayOutputStream outputStream;
        UploadTask uploadTask;

        if(AvatarOriginal != null){
            outputStream = new ByteArrayOutputStream();
            AvatarOriginal.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
            uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
            uploadTask.addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception exception){
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>(){
                        @Override
                        public void onSuccess(Uri uri){
                            mTeacher.setAvatarOriginal(uri.toString());
                            isLoadSuccess[0] = true;
                            SaveImageToServer();
                        }
                    });
                }
            });
        }
        else mTeacher.setAvatarOriginal(null);

        outputStream = new ByteArrayOutputStream();
        Avatar.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
        uploadTask.addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception exception){
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri){
                        mTeacher.setAvatar(uri.toString());
                        isLoadSuccess[1] = true;
                        SaveImageToServer();
                    }
                });
            }
        });
    }
    private void SaveImageToServer(){
        if(IsSelectPhoto)
            if((AvatarOriginal != null && isLoadSuccess[0] && isLoadSuccess[1]) ||
                    (AvatarOriginal == null && isLoadSuccess[1])){
                ModelUser modelUser = new ModelUser();
                modelUser.setId(MENTOR_ID);
                modelUser.setSchoolId(SCHOOL_ID);
                modelUser.setAvatar(mTeacher.getAvatar());
                modelUser.setAvatarOriginal(mTeacher.getAvatarOriginal());

                String SOAP = SoapRequest(func_SchoolEditTeacherProfile, new Gson().toJson(modelUser));
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){ }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(answer.isSuccess()){
                                        setTargetInfo();
                                    }
                                    else {
                                        loadModel();
                                        activity.alert.onToast(answer.getMessage());
                                    }
                                    if(AvatarOriginal != null){
                                        ivAvatar.setAlpha(1f);
                                        pgAvatarOriginalUpload.setVisibility(View.GONE);
                                    }
                                    else{
                                        civAvatar.setAlpha(1f);
                                        pgAvatarUpload.setVisibility(View.GONE);
                                    }
                                    activity.updatePresenter();
                                }
                            });
                        }
                    }
                });
            }
    }
    private void isNextEnable(){
        if(isLoadSuccess[0] && isLoadSuccess[1]){
            activity.alert.onToast(activity.getResources().getString(R.string.successfully_updated));
            pgAvatarOriginalUpload.setVisibility(View.GONE);
            ivAvatar.setAlpha(1f);
//            onUpdate(m4Avatar);
        }
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onSelectLessonForClassClick(ModelTeacherProfileLessons m) {

    }

    @Override
    public void onSelectLessonForDeleteClick(ModelTeacherProfileLessons m) {

    }

    @Override
    public void onSchoolClick(ModelTeacherProfileSchools m) {
        activity.onSchoolProfileClick(m.getSchoolId());
    }
}