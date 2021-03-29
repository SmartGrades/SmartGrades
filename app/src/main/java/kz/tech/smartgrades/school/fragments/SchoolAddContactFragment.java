package kz.tech.smartgrades.school.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
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
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactClassGridAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactClassListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsGridAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddLessonToClassAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetLessons;
import static kz.tech.smartgrades._Web.func_RejectInterFormMentorToSchool;
import static kz.tech.smartgrades._Web.func_SchoolAddTeacher;

public class SchoolAddContactFragment extends Fragment implements View.OnClickListener,
        SchoolAddContactLessonsListAdapter.OnItemCLickListener,
        SchoolAddContactLessonsGridAdapter.OnItemCLickListener,
        SchoolAddContactClassListAdapter.OnItemCLickListener,
        SchoolAddContactClassGridAdapter.OnItemCLickListener {

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack, ivAdd;
    private CircleImageView civAvatar;
    private EditText etLastName, etFirstName, etPatronymic, etSearch;
    private TextView tvItemLabel, tvAddAvatar, tvCancel, tvAdd;

    private Bitmap AvatarOriginal;
    private Bitmap Avatar;
    private boolean IsSelectPhoto = false;
    private boolean[] IsSuccess = new boolean[4];
    private ProgressBar pgAvatarUpload;
    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean isPermissionsGranted = false;
    private ModelSchoolCreateContact mContact;
    private int[] images_parents = {R.drawable.parent_1, R.drawable.parent_2, R.drawable.parent_3,
            R.drawable.parent_4,
            R.drawable.parent_5, R.drawable.parent_6, R.drawable.parent_7, R.drawable.parent_8,
            R.drawable.parent_9, R.drawable.parent_10, R.drawable.parent_11, R.drawable.parent_12,
            R.drawable.parent_13, R.drawable.parent_14, R.drawable.parent_15, R.drawable.parent_16,
            R.drawable.parent_17, R.drawable.parent_18, R.drawable.parent_19, R.drawable.parent_20,
            R.drawable.parent_21, R.drawable.parent_22, R.drawable.parent_23, R.drawable.parent_24,
            R.drawable.parent_25, R.drawable.parent_26, R.drawable.parent_27, R.drawable.parent_28,
            R.drawable.parent_29, R.drawable.parent_30};

    private ConstraintLayout clSearch;
    private ArrayList<ModelLesson> Lessons;
    private ArrayList<ModelLesson> LessonsSelected;
    private ArrayList<ModelLesson> ChoseLessons;
    private SchoolAddContactLessonsListAdapter LessonsAdapter;
    private SchoolAddContactLessonsGridAdapter LessonsSelectedAdapter;
    private RecyclerView rvLessonList, rvSelectLessonsList;
    private ConstraintLayout clClasses;

    private ModelSchoolData mSchoolData;
    private ArrayList<ModelSchoolClass> Classes;
    private ArrayList<ModelSchoolClass> ClassesSelected;
    private RecyclerView rvClassList;
    private RecyclerView rvSelectClassList;
    private SchoolAddContactClassListAdapter ClassListAdapter;
    private SchoolAddContactClassGridAdapter ClassGridAdapter;

    private ModelSchoolRequest mRequest;

    private TextView tvReject;

    public static SchoolAddContactFragment newInstance(ModelMentorData mMentorData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        SchoolAddContactFragment fragment = new SchoolAddContactFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        mSchoolData = activity.getSchoolData();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_add_new_contact, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view) {
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivAdd = view.findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(this);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);
        civAvatar = view.findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        etLastName = view.findViewById(R.id.etLastName);
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                IsSuccess[0] = arg0.length() != 0;
                isNextEnable();
            }
        });
        etFirstName = view.findViewById(R.id.etFirstName);
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                IsSuccess[1] = arg0.length() != 0;
                isNextEnable();
            }
        });
        etPatronymic = view.findViewById(R.id.etPatronymic);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) { }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) {
                LessonsAdapter.filter(arg0.toString());
            }
        });

        clClasses = view.findViewById(R.id.clClasses);
        clClasses.setOnClickListener(this);
        ClassListAdapter = new SchoolAddContactClassListAdapter();
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        rvClassList.setAdapter(ClassListAdapter);
        ClassListAdapter.setOnItemCLickListener(this);
        Classes = new ArrayList<>();
        ClassesSelected = new ArrayList<>();
        ClassGridAdapter = new SchoolAddContactClassGridAdapter(activity);
        rvSelectClassList = view.findViewById(R.id.rvSelectClassList);
        rvSelectClassList.setAdapter(ClassGridAdapter);
        rvSelectClassList.setLayoutManager(new LinearLayoutManager(activity));
        ClassGridAdapter.setOnItemCLickListener(this);

        tvItemLabel = view.findViewById(R.id.tvItemLabel);
        tvAddAvatar = view.findViewById(R.id.tvAddAvatar);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvReject = view.findViewById(R.id.tvReject);
        tvReject.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
        clSearch = view.findViewById(R.id.clSearch);
        clSearch.setVisibility(View.GONE);
        mContact = new ModelSchoolCreateContact();

        LessonsAdapter = new SchoolAddContactLessonsListAdapter();
        rvLessonList = view.findViewById(R.id.rvLessonList);
        rvLessonList.setLayoutManager(new LinearLayoutManager(activity));
        rvLessonList.setAdapter(LessonsAdapter);
        LessonsAdapter.setOnItemCLickListener(this);

        rvSelectLessonsList = view.findViewById(R.id.rvSelectLessonsList);
        LessonsSelectedAdapter = new SchoolAddContactLessonsGridAdapter();
        rvSelectLessonsList = view.findViewById(R.id.rvSelectLessonsList);
        rvSelectLessonsList.setLayoutManager(new LinearLayoutManager(activity,
                RecyclerView.HORIZONTAL, false));
        rvSelectLessonsList.setAdapter(LessonsSelectedAdapter);
        LessonsSelectedAdapter.setOnItemCLickListener(this);
        LessonsSelected = new ArrayList<>();

        if (mRequest != null) {
            if (!stringIsNullOrEmpty(mRequest.getSourceAvatar()))
                Picasso.get().load(mRequest.getSourceAvatar()).fit().centerCrop().into(civAvatar);
            else
                civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            tvAddAvatar.setVisibility(View.GONE);
            tvReject.setVisibility(View.VISIBLE);
            etLastName.setText(mRequest.getSourceLastName());
            etFirstName.setText(mRequest.getSourceFirstName());
            etLastName.setEnabled(false);
            etFirstName.setEnabled(false);
            etPatronymic.setEnabled(false);
            civAvatar.setEnabled(false);
        }
    }

    private void ToMakeAPhoto(int Type) {
        getPermissionsToUseCamera();
        if (isPermissionsGranted) {
            Intent PictureIntent = null;
            if (Type == REQUEST_TAKE_PHOTO)
                PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            else if (Type == REQUEST_LOAD_PHOTO) {
                PictureIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            if (PictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                if (Type == REQUEST_TAKE_PHOTO) {
                    try {
                        File photoFile = createImageFile();
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(activity, "kz.tech.esparta" +
                                    ".fileprovider", photoFile);
                            mPhotoFile = photoFile;
                            PictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        }
                        startActivityForResult(PictureIntent, REQUEST_TAKE_PHOTO);
                    }
                    catch (IOException ex) {
                    }
                }
                else if (Type == REQUEST_LOAD_PHOTO)
                    startActivityForResult(Intent.createChooser(PictureIntent, "Select Picture"),
                            REQUEST_LOAD_PHOTO);
            }
        }
    }
    public void getPermissionsToUseCamera() {
        String[] permissions = {android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean check = true;
        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                check = false;
            }
        }
        isPermissionsGranted = check;
        if (!isPermissionsGranted)
            ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST);
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = null;
            if (requestCode == REQUEST_TAKE_PHOTO) imageUri = Uri.fromFile(mPhotoFile);
            else if (requestCode == REQUEST_LOAD_PHOTO && null != data) imageUri = data.getData();
            try {
                InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
                setPhoto(BitmapFactory.decodeStream(imageStream));
            }
            catch (Exception e) { }
        }
    }

    private void onSelectAvatar() {
        View view = getLayoutInflater().inflate(R.layout.bsd_select_avatar, null, false);
        BottomSheetDialog sheetDialog = new BottomSheetDialog(activity);
        sheetDialog.setContentView(view);
        sheetDialog.show();
        ImageView ivBack = view.findViewById(R.id.ivBack);
        LinearLayout llDrawing = view.findViewById(R.id.llDrawing);
        LinearLayout llGallery = view.findViewById(R.id.llGallery);
        LinearLayout llCamera = view.findViewById(R.id.llCamera);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.dismiss();
            }
        });
        llDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog DrawingSheetDialog = new BottomSheetDialog(activity);
                View viewDialog =
                        getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_drawing,
                                null, false);
                DrawingSheetDialog.setContentView(viewDialog);
                View bottomSheet = DrawingSheetDialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                bottomSheet.setLayoutParams(layoutParams);
                layoutParams.height = (_System.getWindowHeight(activity));
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                DrawingSheetDialog.show();
                DrawingSelectAdapter drawingSelectAdapter = new DrawingSelectAdapter(activity);
                RecyclerView rvAvatarsList = viewDialog.findViewById(R.id.rvAvatarsList);
                rvAvatarsList.setLayoutManager(new GridLayoutManager(activity, 5));
                rvAvatarsList.setAdapter(drawingSelectAdapter);
                drawingSelectAdapter.UpdateData(images_parents);
                drawingSelectAdapter.ShowData();
                drawingSelectAdapter.setOnItemClickListener(new DrawingSelectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ImageView img) {
                        DrawingSheetDialog.dismiss();
                        sheetDialog.dismiss();
                        img.setDrawingCacheEnabled(true);
                        setPhoto(img.getDrawingCache());
                    }
                });
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
    }
    public void setPhoto(Bitmap avatarOriginal) {
        IsSelectPhoto = true;
        isNextEnable();
        tvAddAvatar.setVisibility(View.GONE);
        AvatarOriginal = Bitmap.createScaledBitmap(avatarOriginal, 96, 96, true);
        Avatar = Bitmap.createScaledBitmap(avatarOriginal, 96, 96, true);
        civAvatar.setImageBitmap(Avatar);
        civAvatar.setAlpha(0.5f);
        pgAvatarUpload.setVisibility(View.VISIBLE);
        UploadImage();
    }
    private void UploadImage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AvatarOriginal.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        UploadTask uploadTask =
                new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        civAvatar.setAlpha(1f);
                        pgAvatarUpload.setVisibility(View.GONE);
                        mContact.setAvatar(uri.toString());
                        IsSuccess[2] = true;
                        isNextEnable();
                    }
                });
            }
        });
    }
    private void isNextEnable() {
        if (IsSelectPhoto) {
            if (IsSuccess[0] && IsSuccess[1] && IsSuccess[2] && IsSuccess[3]) {
                tvAdd.setEnabled(true);
                tvAdd.setTextColor(activity.getResources().getColor(R.color.white));
                tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
                tvAdd.setPadding(10, 10, 10, 10);
            }
            else {
                tvAdd.setEnabled(false);
                tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
                tvAdd.setBackground(null);
                tvAdd.setPadding(10, 10, 10, 10);
            }
        }
        else if (IsSuccess[0] && IsSuccess[1] && IsSuccess[3]) {
            tvAdd.setEnabled(true);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.white));
            tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
            tvAdd.setPadding(10, 10, 10, 10);
        }
        else {
            tvAdd.setEnabled(false);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
            tvAdd.setBackground(null);
            tvAdd.setPadding(10, 10, 10, 10);
        }
    }

    @Override
    public void onLessonClick(ModelLesson m) {
        if (!etSearch.getText().toString().isEmpty()) etSearch.setText("");
        ivAdd.setImageDrawable(getResources().getDrawable(R.drawable.img_okay_blue));
        Lessons.remove(m);
        LessonsAdapter.UpdateData(Lessons);
        LessonsSelected.add(m);
        LessonsSelectedAdapter.UpdateData(LessonsSelected);
        rvSelectLessonsList.setVisibility(View.VISIBLE);
        tvItemLabel.setVisibility(View.GONE);
        IsSuccess[3] = !listIsNullOrEmpty(LessonsSelected);
        isNextEnable();
    }
    @Override
    public void onLessonDeleteClick(ModelLesson m) {
        Lessons.add(m);
        LessonsAdapter.UpdateData(Lessons);
        LessonsSelected.remove(m);
        LessonsSelectedAdapter.UpdateData(LessonsSelected);
        if (!listIsNullOrEmpty(LessonsSelected)) {
            IsSuccess[3] = true;
            isNextEnable();
        }
        else {
            tvItemLabel.setText("Предмет*");
            tvItemLabel.setVisibility(View.VISIBLE);
            IsSuccess[3] = false;
            isNextEnable();
        }
    }
    @Override
    public void onClassClick(ModelSchoolClass m) {
        rvClassList.setVisibility(View.GONE);
        Classes.remove(m);
        ClassListAdapter.UpdateData(Classes);
        ClassesSelected.add(m);
        ClassGridAdapter.UpdateData(ClassesSelected);
    }
    @Override
    public void onAddLessonToClass(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_school_add_contact_add_lesson, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        RecyclerView rvLessonsList = view.findViewById(R.id.rvLessonsList);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvEmptyLabel = view.findViewById(R.id.tvEmptyLabel);

        if (!listIsNullOrEmpty(LessonsSelected)) {
            tvEmptyLabel.setVisibility(View.GONE);
            SchoolAddLessonToClassAdapter adapter = new SchoolAddLessonToClassAdapter();
            rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
            rvLessonsList.setAdapter(adapter);
            ArrayList<ModelLesson> Lessons = new ArrayList<>();
            Lessons.addAll(LessonsSelected);
            if (!listIsNullOrEmpty(ClassesSelected.get(index).getLessons()))
                for (ModelSchoolLesson _lessonClass : ClassesSelected.get(index).getLessons())
                    for (ModelLesson _lessonSelected : LessonsSelected)
                        if (_lessonClass.getLessonId().equals(_lessonSelected.getLessonId()))
                            Lessons.remove(_lessonSelected);
            if (!listIsNullOrEmpty(Lessons)) {
                adapter.UpdateData(Lessons);
                adapter.setOnItemCLickListener(new SchoolAddLessonToClassAdapter.OnItemCLickListener() {
                    @Override
                    public void onSelectLessonForClassClick(ModelLesson m) {
                        ModelSchoolLesson mLesson = new ModelSchoolLesson();
                        mLesson.setLessonId(m.getLessonId());
                        mLesson.setLessonName(m.getLessonName());
                        if (listIsNullOrEmpty(ClassesSelected.get(index).getLessons()))
                            ClassesSelected.get(index).setLessons(new ArrayList<>());
                        ClassesSelected.get(index).getLessons().add(mLesson);
                        ClassGridAdapter.UpdateData(ClassesSelected);
                        alertDialog.dismiss();
                    }
                });
            }
            else {
                tvEmptyLabel.setVisibility(View.VISIBLE);
                tvEmptyLabel.setText("Предметы выбраны.");
            }
        }
        else tvEmptyLabel.setVisibility(View.VISIBLE);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onCancelClick();
                break;
            case R.id.btnSend:
                onSend();
                break;
            case R.id.clLabel:
                onShowLessonListClick();
                break;
            case R.id.ivSearchArrow:
                onHideLessonsListClick();
                break;
            case R.id.civAvatar:
                onSelectAvatar();
                break;
            case R.id.ivAdd:
                onAddLessonClick();
                break;
            case R.id.clClasses:
                onAddClassClick();
                break;
            case R.id.tvAdd:
                onSend();
                break;
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.tvReject:
                onRejectClick();
                break;
        }
    }
    private void onRejectClick() {
        JsonObject json = new JsonObject();
        json.addProperty(F.Id, mRequest.getId());

        String SOAP = SoapRequest(func_RejectInterFormMentorToSchool, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            activity.presenter.onStartPresenter();
                            onCancelClick();
                        }
                    });
                }
            }
        });
    }
    private void onCancelClick() {
        for (Fragment fragment : activity.getSupportFragmentManager().getFragments())
            if (fragment instanceof SchoolAddContactFragment)
                activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
    private void onAddLessonClick() {
        if (clSearch.getVisibility() == View.VISIBLE) {
            clSearch.setVisibility(View.GONE);
            ivAdd.setImageDrawable(getResources().getDrawable(R.drawable.img_edit_gray));
            LessonsSelectedAdapter.setEditEnable(false);
        }
        else {
            clSearch.setVisibility(View.VISIBLE);
            LessonsSelectedAdapter.setEditEnable(true);
            onShowLessonListClick();
        }
    }
    private void onShowLessonListClick() {
        if (listIsNullOrEmpty(Lessons)) {
            String SOAP = SoapRequest(func_GetLessons, null);
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = _Web.XMLReader(response.body().string());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Lessons = new Gson().fromJson(result,
                                        new TypeToken<ArrayList<ModelLesson>>() {}.getType());
                                LessonsAdapter.UpdateData(Lessons);
                            }
                        });
                    }
                }
            });
        }
        else LessonsAdapter.UpdateData(Lessons);
    }
    private void onHideLessonsListClick() {
        clSearch.setVisibility(View.GONE);
    }
    private void onAddClassClick() {
        if (rvClassList.getVisibility() == View.GONE) {
            if (listIsNullOrEmpty(Classes)) {
                if (!listIsNullOrEmpty(mSchoolData.getClassessColumns())) {
                    Classes.clear();
                    for (ModelSchoolClassesColumn _column : mSchoolData.getClassessColumns())
                        if (!listIsNullOrEmpty(_column.getClasses()))
                            for (ModelSchoolClass _class : _column.getClasses()) {
                                ModelSchoolClass Class = new ModelSchoolClass();
                                Class.setId(_class.getId());
                                Class.setName(_class.getName());
                                Classes.add(Class);
                            }
                    ClassListAdapter.UpdateData(Classes);
                    rvClassList.setVisibility(View.VISIBLE);
                }
            }
            else rvClassList.setVisibility(View.VISIBLE);
        }
        else rvClassList.setVisibility(View.GONE);
    }
    private void onSend() {
        mContact.setSchoolId(SCHOOL_ID);
        mContact.setFirstName(etFirstName.getText().toString());
        mContact.setLastName(etLastName.getText().toString());
        mContact.setPatronymic(etPatronymic.getText().toString());
        mContact.setLessons(LessonsSelected);
        mContact.setClasses(ClassesSelected);

        String SOAP = SoapRequest(func_SchoolAddTeacher, new Gson().toJson(mContact));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            onCancelClick();
                        }
                    });
                }
            }
        });
    }
    public void setRequestData(ModelSchoolRequest m) {
        mRequest = m;
    }
}