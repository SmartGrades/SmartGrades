package kz.tech.smartgrades.school.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.yalantis.ucrop.UCrop;

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
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddTeacherClassGridAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddTeacherClassListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsGridAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddLessonToClassAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolUniteMentorAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades.F.isNameValid;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetLessons;
import static kz.tech.smartgrades._Web.func_RejectInterFormMentorToSchool;
import static kz.tech.smartgrades._Web.func_SchoolAddClass;
import static kz.tech.smartgrades._Web.func_SchoolAddTeacher;

public class SchoolAddTeacherFragment extends Fragment implements View.OnClickListener,
        SchoolAddContactLessonsListAdapter.OnItemCLickListener,
        SchoolAddContactLessonsGridAdapter.OnItemCLickListener,
        SchoolAddTeacherClassListAdapter.OnItemCLickListener,
        SchoolAddTeacherClassGridAdapter.OnItemCLickListener,
        SchoolUniteMentorAdapter.OnItemCLickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack, ivAdd;
    private CircleImageView civAvatar;
    private EditText etLastName, etFirstName, etPatronymic, etSearch;
    private TextView tvItemLabel, tvAddAvatar, tvCancel, tvAdd;

    private Bitmap AvatarOriginal;
    private Bitmap Avatar;
    private boolean IsSelectPhoto = false;
    private boolean[] IsSuccess = new boolean[5];
    private ProgressBar pgAvatarUpload;
    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private int SELECT_TYPE = 0;

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
    private SchoolAddTeacherClassListAdapter ClassListAdapter;
    private SchoolAddTeacherClassGridAdapter ClassGridAdapter;
    private ModelInterForm InterForm;
    private TextView tvReject, tvAddNewClass;

    private ArrayList<ModelSchoolTeacher> TeachersList;
    private RecyclerView rvUniteMentors;
    private TextView tvUniteLabel, tvUnite;
    private SchoolUniteMentorAdapter UniteMentorAdapter;

    private boolean[] isLoadSuccess = new boolean[2];

    public static SchoolAddTeacherFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        SchoolAddTeacherFragment fragment = new SchoolAddTeacherFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        mSchoolData = activity.getSchoolData();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_school_add_teacher, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivAdd = view.findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(this);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);
        civAvatar = view.findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        etLastName = view.findViewById(R.id.etLastName);
        etLastName.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){ }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){ }
            @Override
            public void afterTextChanged(Editable arg0){
                if (arg0.length() == 0) IsSuccess[0] = false;
                else if (arg0.length() > 0) {
                    if (Character.isLowerCase(arg0.toString().charAt(0))) etLastName.setText(arg0.toString().toUpperCase());
                    etLastName.setSelection(etLastName.getText().length());
                    IsSuccess[0] = isNameValid(arg0.toString());
                }
                isNextEnable();
            }
        });
        etFirstName = view.findViewById(R.id.etFirstName);
        etFirstName.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                if (arg0.length() == 0) IsSuccess[1] = false;
                else if (arg0.length() > 0) {
                    if (Character.isLowerCase(arg0.toString().charAt(0))) etFirstName.setText(arg0.toString().toUpperCase());
                    etFirstName.setSelection(etFirstName.getText().length());
                    IsSuccess[1] = isNameValid(arg0.toString());
                }
                isNextEnable();
            }
        });
        etPatronymic = view.findViewById(R.id.etPatronymic);
        etPatronymic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if (Character.isLowerCase(editable.toString().charAt(0))) etPatronymic.setText(editable.toString().toUpperCase());
                    etPatronymic.setSelection(etPatronymic.getText().length());
                }
            }
        });
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
                LessonsAdapter.filter(arg0.toString());
            }
        });

        clClasses = view.findViewById(R.id.clClasses);
        clClasses.setOnClickListener(this);
        ClassListAdapter = new SchoolAddTeacherClassListAdapter();
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        rvClassList.setAdapter(ClassListAdapter);
        ClassListAdapter.setOnItemCLickListener(this);
        Classes = new ArrayList<>();
        ClassesSelected = new ArrayList<>();
        ClassGridAdapter = new SchoolAddTeacherClassGridAdapter(activity);
        rvSelectClassList = view.findViewById(R.id.rvSelectClassList);
        rvSelectClassList.setAdapter(ClassGridAdapter);
        rvSelectClassList.setLayoutManager(new LinearLayoutManager(activity));
        ClassGridAdapter.setOnItemCLickListener(this);

        tvUniteLabel = view.findViewById(R.id.tvUniteLabel);
        tvUnite = view.findViewById(R.id.tvUnite);
        tvUnite.setOnClickListener(this);

        tvAddNewClass = view.findViewById(R.id.tvAddNewClass);
        tvAddNewClass.setOnClickListener(this);

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

        TeachersList = new ArrayList<>();
        UniteMentorAdapter = new SchoolUniteMentorAdapter(activity);
        rvUniteMentors = view.findViewById(R.id.rvUniteMentors);
        rvUniteMentors.setLayoutManager(new LinearLayoutManager(activity));
        rvUniteMentors.setAdapter(UniteMentorAdapter);
        UniteMentorAdapter.setOnItemCLickListener(this);

        rvSelectLessonsList = view.findViewById(R.id.rvSelectLessonsList);
        LessonsSelectedAdapter = new SchoolAddContactLessonsGridAdapter();
        rvSelectLessonsList = view.findViewById(R.id.rvSelectLessonsList);
        rvSelectLessonsList.setLayoutManager(new LinearLayoutManager(activity,
                RecyclerView.VERTICAL, false));
        rvSelectLessonsList.setAdapter(LessonsSelectedAdapter);
        LessonsSelectedAdapter.setOnItemCLickListener(this);
        LessonsSelected = new ArrayList<>();

        if(InterForm != null){
            if(!stringIsNullOrEmpty(InterForm.getSourceAvatar()))
                Picasso.get().load(InterForm.getSourceAvatar()).fit().centerCrop().into(civAvatar);
            else
                civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            tvAddAvatar.setVisibility(View.GONE);
            tvReject.setVisibility(View.VISIBLE);
            etPatronymic.setVisibility(View.GONE);
            etLastName.setText(InterForm.getSourceLastName());
            etFirstName.setText(InterForm.getSourceFirstName());
            etLastName.setEnabled(false);
            etFirstName.setEnabled(false);
            etPatronymic.setEnabled(false);
            civAvatar.setEnabled(false);
        }
        if(!listIsNullOrEmpty(TeachersList)) UniteMentorAdapter.UpdateData(TeachersList);
        else{
            tvUniteLabel.setVisibility(View.GONE);
            tvUnite.setVisibility(View.GONE);
        }
    }

    public void setInterForm(ModelInterForm m){
        InterForm = m;
    }
    public void onUpdateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        boolean IsExist = false;
        if(!listIsNullOrEmpty(mSchoolData.getClassessColumns())){
            Classes.clear();
            for(ModelSchoolClassesColumn _column : mSchoolData.getClassessColumns())
                if(!listIsNullOrEmpty(_column.getClasses()))
                    for(ModelSchoolClass _class : _column.getClasses()){
                        for(ModelSchoolClass _SelectClass : ClassesSelected){
                            if(_SelectClass.getId().equals(_class.getId())){
                                IsExist = true;
                                break;
                            }
                        }
                        if(!IsExist){
                            ModelSchoolClass Class = new ModelSchoolClass();
                            Class.setId(_class.getId());
                            Class.setName(_class.getName());
                            Classes.add(Class);
                        }
                        IsExist = false;
                    }
            ClassListAdapter.UpdateData(Classes);
        }
    }
    public void setTeachersData(ArrayList<ModelSchoolTeacher> teachers){
        TeachersList = teachers;
    }

    //SELECT AVATAR//
    private void onSelectAvatar(){
        View view = getLayoutInflater().inflate(R.layout.bsd_select_avatar, null, false);
        BottomSheetDialog sheetDialog = new BottomSheetDialog(activity);
        sheetDialog.setContentView(view);
        sheetDialog.show();
        ImageView ivBack = view.findViewById(R.id.ivBack);
        LinearLayout llDrawing = view.findViewById(R.id.llDrawing);
        LinearLayout llGallery = view.findViewById(R.id.llGallery);
        LinearLayout llCamera = view.findViewById(R.id.llCamera);
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sheetDialog.dismiss();
            }
        });
        llDrawing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                drawingSelectAdapter.setOnItemClickListener(new DrawingSelectAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(ImageView img){
                        DrawingSheetDialog.dismiss();
                        sheetDialog.dismiss();
                        img.setDrawingCacheEnabled(true);
                        setPhoto(img.getDrawingCache(), false);
                    }
                });
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sheetDialog.dismiss();
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sheetDialog.dismiss();
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
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
        IsSelectPhoto = true;
        Bitmap avatarOriginal = Bitmap.createBitmap(image.getWidth(),
                image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(avatarOriginal);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(image, 0F, 0F, null);
        tvAddAvatar.setVisibility(View.GONE);
        AvatarOriginal = Bitmap.createScaledBitmap(avatarOriginal, 480, 480, true);
        Avatar = Bitmap.createScaledBitmap(avatarOriginal, 128, 128, true);
        civAvatar.setImageBitmap(AvatarOriginal);
        civAvatar.setAlpha(0.5f);
        pgAvatarUpload.setVisibility(View.VISIBLE);
        UploadImage(isSaveOriginal);
    }
    private void UploadImage(boolean isSaveOriginal){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        UploadTask uploadTask;
        if (isSaveOriginal) {
            AvatarOriginal.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
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
                            isLoadSuccess[0] = true;
                            mContact.setAvatarOriginal(uri.toString());
                            isPhotoLoaded();
                        }
                    });
                }
            });
        } else {
            isLoadSuccess[0] = true;
            isPhotoLoaded();
        }
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
                        isLoadSuccess[1] = true;
                        mContact.setAvatar(uri.toString());
                        isPhotoLoaded();
                    }
                });
            }
        });
    }

    private void isPhotoLoaded() {
        if (isLoadSuccess[0] && isLoadSuccess[1]) {
            civAvatar.setAlpha(1f);
            pgAvatarUpload.setVisibility(View.GONE);
            IsSuccess[2] = true;
            isNextEnable();
        }
    }

    private void isNextEnable(){
        if(IsSelectPhoto){
            if(IsSuccess[0] && IsSuccess[1] && IsSuccess[2] && IsSuccess[3] && IsSuccess[4]){
                tvAdd.setEnabled(true);
                tvAdd.setTextColor(activity.getResources().getColor(R.color.white));
                tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
            }
            else{
                tvAdd.setEnabled(false);
                tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
                tvAdd.setBackground(null);
            }
            tvAdd.setPadding(10, 10, 10, 10);
        }
        else if(IsSuccess[0] && IsSuccess[1] && IsSuccess[3] && IsSuccess[4]){
            tvAdd.setEnabled(true);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.white));
            tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
            tvAdd.setPadding(10, 10, 10, 10);
        }
        else{
            tvAdd.setEnabled(false);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
            tvAdd.setBackground(null);
            tvAdd.setPadding(10, 10, 10, 10);
        }
    }
    //SELECT AVATAR//

    @Override
    public void onLessonClick(ModelLesson m){
        if(!etSearch.getText().toString().isEmpty()) etSearch.setText("");
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
    public void onLessonDeleteClick(ModelLesson m){
        Lessons.add(m);
        LessonsAdapter.UpdateData(Lessons);
        LessonsSelected.remove(m);
        LessonsSelectedAdapter.UpdateData(LessonsSelected);
        if(!listIsNullOrEmpty(LessonsSelected)){
            IsSuccess[3] = true;
            isNextEnable();
        }
        else{
            tvItemLabel.setText("Предмет*");
            tvItemLabel.setVisibility(View.VISIBLE);
            IsSuccess[3] = false;
            isNextEnable();
        }
    }
    @Override
    public void onClassClick(ModelSchoolClass m){
        tvAddNewClass.setVisibility(View.GONE);
        rvClassList.setVisibility(View.GONE);
        Classes.remove(m);
        ClassListAdapter.UpdateData(Classes);
        ClassesSelected.add(m);
        ClassGridAdapter.UpdateData(ClassesSelected);
        IsSuccess[4] = false;
        isNextEnable();
    }
    @Override
    public void onAddLessonToClass(int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_school_add_contact_add_lesson, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        RecyclerView rvLessonsList = view.findViewById(R.id.rvLessonsList);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvEmptyLabel = view.findViewById(R.id.tvEmptyLabel);

        if(!listIsNullOrEmpty(LessonsSelected)){
            tvEmptyLabel.setVisibility(View.GONE);
            tvCancel.setText(R.string.cancel);
            SchoolAddLessonToClassAdapter adapter = new SchoolAddLessonToClassAdapter(activity);
            rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
            rvLessonsList.setAdapter(adapter);
            ArrayList<ModelLesson> Lessons = new ArrayList<>();
            Lessons.addAll(LessonsSelected);
            if(!listIsNullOrEmpty(ClassesSelected.get(index).getLessons()))
                for(ModelSchoolLesson _lessonClass : ClassesSelected.get(index).getLessons())
                    for(ModelLesson _lessonSelected : LessonsSelected)
                        if(_lessonClass.getLessonId().equals(_lessonSelected.getLessonId())) {
                            _lessonSelected.setSelected(true);
                        }
            if(!listIsNullOrEmpty(Lessons)){
                adapter.UpdateData(Lessons);
                adapter.setOnItemCLickListener(new SchoolAddLessonToClassAdapter.OnItemCLickListener(){
                    @Override
                    public void onSelectLessonForClassClick(ModelLesson m){
                        ModelSchoolLesson mLesson = new ModelSchoolLesson();
                        mLesson.setLessonId(m.getLessonId());
                        mLesson.setLessonName(m.getLessonName());
                        if(listIsNullOrEmpty(ClassesSelected.get(index).getLessons()))
                            ClassesSelected.get(index).setLessons(new ArrayList<>());
                        ClassesSelected.get(index).getLessons().add(mLesson);
                        ClassGridAdapter.UpdateData(ClassesSelected);
                        alertDialog.dismiss();
                        IsSuccess[4] = false;
                        for(ModelSchoolClass _class : ClassesSelected){
                            if(!listIsNullOrEmpty(_class.getLessons()))
                                IsSuccess[4] = true;
                            else{
                                IsSuccess[4] = false;
                                break;
                            }
                        }
                        isNextEnable();
                    }

                    @Override
                    public void onSelectLessonForRemoveClick(ModelLesson m) {
                        LessonsSelected.remove(m);
                        if(listIsNullOrEmpty(ClassesSelected.get(index).getLessons()))
                            ClassesSelected.get(index).setLessons(new ArrayList<>());
                        else {
                            ArrayList<ModelSchoolLesson> LessonsTemp = new ArrayList<>(ClassesSelected.get(index).getLessons());
                            for (ModelSchoolLesson _lesson : ClassesSelected.get(index).getLessons()) {
                                if (_lesson.getLessonId().equals(m.getLessonId())) {
                                    LessonsTemp.remove(_lesson);
                                }
                            }
                            ClassesSelected.get(index).setLessons(LessonsTemp);
                        }
                        ClassGridAdapter.UpdateData(ClassesSelected);
                        alertDialog.dismiss();
                        IsSuccess[4] = false;
                        for(ModelSchoolClass _class : ClassesSelected){
                            if(!listIsNullOrEmpty(_class.getLessons()))
                                IsSuccess[4] = true;
                            else{
                                IsSuccess[4] = false;
                                break;
                            }
                        }
                        isNextEnable();
                    }
                });
            }
            else{
                tvEmptyLabel.setVisibility(View.VISIBLE);
                tvEmptyLabel.setText("Предметы выбраны.");
            }
        }
        else{
            tvEmptyLabel.setVisibility(View.VISIBLE);
            tvCancel.setText("Ок");
        }
        tvCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onCancelClick();
                break;
            case R.id.btnSend:
                onAcceptClick();
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
                onAcceptClick();
                break;
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.tvReject:
                onRejectClick();
                break;
            case R.id.tvAddNewClass:
                onAddNewClass();
                break;
        }
    }
    private void onCancelClick(){
        activity.alert.hideKeyboard(activity);
        activity.onBackPressed();
    }
    private void onRejectClick(){
        activity.alert.hideKeyboard(activity);
        JsonObject json = new JsonObject();
        json.addProperty(F.Id, InterForm.getId());

        String SOAP = SoapRequest(func_RejectInterFormMentorToSchool, json.toString());
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
    private void onAcceptClick(){
        activity.alert.hideKeyboard(activity);
        if(InterForm != null){
            mContact.setId(InterForm.getId());
            mContact.setUserId(InterForm.getSourceId());
        }
        mContact.setSchoolId(SCHOOL_ID);
        mContact.setFirstName(etFirstName.getText().toString());
        mContact.setLastName(etLastName.getText().toString());
        mContact.setPatronymic(etPatronymic.getText().toString());
        mContact.setLessons(LessonsSelected);
        mContact.setClasses(ClassesSelected);

        String SOAP = SoapRequest(func_SchoolAddTeacher, new Gson().toJson(mContact));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){ }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
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
    private void onAddLessonClick(){
        if(clSearch.getVisibility() == View.VISIBLE){
            clSearch.setVisibility(View.GONE);
            ivAdd.setImageDrawable(getResources().getDrawable(R.drawable.img_edit_gray));
            LessonsSelectedAdapter.setEditEnable(false);
            activity.alert.hideKeyboard(activity);
        }
        else{
            clSearch.setVisibility(View.VISIBLE);
            LessonsSelectedAdapter.setEditEnable(true);
            onShowLessonListClick();
        }
    }
    private void onShowLessonListClick(){
        activity.alert.hideKeyboard(activity);
        if(listIsNullOrEmpty(Lessons)){
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
                                Lessons = new Gson().fromJson(result,
                                        new TypeToken<ArrayList<ModelLesson>>(){
                                        }.getType());
                                LessonsAdapter.UpdateData(Lessons);
                            }
                        });
                    }
                }
            });
        }
        else LessonsAdapter.UpdateData(Lessons);
    }
    private void onHideLessonsListClick(){
        clSearch.setVisibility(View.GONE);
    }
    private void onAddClassClick(){
        activity.alert.hideKeyboard(activity);
        if(rvClassList.getVisibility() == View.GONE){
            tvAddNewClass.setVisibility(View.VISIBLE);
            if(listIsNullOrEmpty(Classes) && listIsNullOrEmpty(ClassesSelected)){
                if(!listIsNullOrEmpty(mSchoolData.getClassessColumns())){
                    Classes.clear();
                    for(ModelSchoolClassesColumn _column : mSchoolData.getClassessColumns())
                        if(!listIsNullOrEmpty(_column.getClasses()))
                            for(ModelSchoolClass _class : _column.getClasses()){
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
        else{
            rvClassList.setVisibility(View.GONE);
            tvAddNewClass.setVisibility(View.GONE);
        }
    }
    private void onAddNewClass(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_school_add_teacher_add_class, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAdd = view.findViewById(R.id.tvAdd);
        EditText etClassName = view.findViewById(R.id.etClassName);
        etClassName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
            @Override
            public void afterTextChanged(Editable editable){
                if(!editable.toString().isEmpty()){
                    tvAdd.setEnabled(true);
                    tvAdd.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                }
                else{
                    tvAdd.setEnabled(false);
                    tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                activity.alert.hideKeyboard(activity);
                alertDialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.SchoolId, SCHOOL_ID);
                jsonData.addProperty(F.Name, etClassName.getText().toString());

                String SOAP = SoapRequest(func_SchoolAddClass, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                        System.out.println(e.toString());
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = _Web.XMLToJson(response.body().string());
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    activity.alert.onToast(answer.getMessage());
                                    if(answer.isSuccess()) {
                                        activity.presenter.onStartPresenter();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onSelectMentorForUnite(ModelSchoolTeacher index){
    }
}