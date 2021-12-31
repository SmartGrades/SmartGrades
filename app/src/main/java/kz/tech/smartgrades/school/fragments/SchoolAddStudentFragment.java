package kz.tech.smartgrades.school.fragments;

import android.annotation.SuppressLint;
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
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddStudentClassGridAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddTeacherClassListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolUniteMentorAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;
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
import static kz.tech.smartgrades._Web.func_RejectInterFormMentorToSchool;
import static kz.tech.smartgrades._Web.func_SchoolAddStudent;

public class SchoolAddStudentFragment extends Fragment implements View.OnClickListener,
        SchoolAddTeacherClassListAdapter.OnItemCLickListener,
        SchoolUniteMentorAdapter.OnItemCLickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack;
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
    private int[] images_childs = {R.drawable.child_1, R.drawable.child_2, R.drawable.child_3, R.drawable.child_4,
            R.drawable.child_5, R.drawable.child_6, R.drawable.child_7, R.drawable.child_8,
            R.drawable.child_9, R.drawable.child_10, R.drawable.child_11, R.drawable.child_12,
            R.drawable.child_13, R.drawable.child_14, R.drawable.child_15, R.drawable.child_16,
            R.drawable.child_17, R.drawable.child_18, R.drawable.child_19, R.drawable.child_20,
            R.drawable.child_21, R.drawable.child_22, R.drawable.child_23, R.drawable.child_24,
            R.drawable.child_25, R.drawable.child_26, R.drawable.child_27, R.drawable.child_28};

    private ConstraintLayout clClasses;

    private ModelSchoolData mSchoolData;
    private ArrayList<ModelSchoolClass> Classes;
    private ArrayList<ModelSchoolClass> ClassesSelected;
    private RecyclerView rvClassList;
    private RecyclerView rvSelectClassList;
    private SchoolAddTeacherClassListAdapter ClassListAdapter;
    private SchoolAddStudentClassGridAdapter ClassGridAdapter;
    private ModelSchoolRequest mRequest;
    private TextView tvReject;

    private RecyclerView rvUniteMentors;
    private TextView tvUniteLabel, tvUnite;
    private SchoolUniteMentorAdapter UniteMentorAdapter;

    private boolean[] isLoadSuccess = new boolean[2];

    public static SchoolAddStudentFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        SchoolAddStudentFragment fragment = new SchoolAddStudentFragment();
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
        return inflater.inflate(R.layout.fragment_school_add_student, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
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

        clClasses = view.findViewById(R.id.clClasses);
        clClasses.setOnClickListener(this);
        ClassListAdapter = new SchoolAddTeacherClassListAdapter();
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        rvClassList.setAdapter(ClassListAdapter);
        ClassListAdapter.setOnItemCLickListener(this);
        Classes = new ArrayList<>();
        ClassesSelected = new ArrayList<>();
        ClassGridAdapter = new SchoolAddStudentClassGridAdapter(activity);
        rvSelectClassList = view.findViewById(R.id.rvSelectClassList);
        rvSelectClassList.setAdapter(ClassGridAdapter);
        rvSelectClassList.setLayoutManager(new LinearLayoutManager(activity));

        tvItemLabel = view.findViewById(R.id.tvItemLabel);
        tvAddAvatar = view.findViewById(R.id.tvAddAvatar);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvReject = view.findViewById(R.id.tvReject);
        tvReject.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);

        mContact = new ModelSchoolCreateContact();

        tvUniteLabel = view.findViewById(R.id.tvUniteLabel);
        rvUniteMentors = view.findViewById(R.id.rvUniteMentors);
        tvUnite = view.findViewById(R.id.tvUnite);

        if(mRequest != null){
            if(!stringIsNullOrEmpty(mRequest.getSourceAvatar()))
                Picasso.get().load(mRequest.getSourceAvatar()).fit().centerCrop().into(civAvatar);
            else
                civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            tvAddAvatar.setVisibility(View.GONE);
            tvReject.setVisibility(View.VISIBLE);
            etPatronymic.setVisibility(View.GONE);
            etLastName.setText(mRequest.getSourceLastName());
            etFirstName.setText(mRequest.getSourceFirstName());
            etLastName.setEnabled(false);
            etFirstName.setEnabled(false);
            etPatronymic.setEnabled(false);
            civAvatar.setEnabled(false);
            tvUniteLabel.setVisibility(View.VISIBLE);
            rvUniteMentors.setVisibility(View.VISIBLE);
            tvUnite.setVisibility(View.VISIBLE);
        }
        else{
            tvUniteLabel.setVisibility(View.GONE);
            rvUniteMentors.setVisibility(View.GONE);
            tvUnite.setVisibility(View.GONE);
        }
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
                drawingSelectAdapter.UpdateData(images_childs);
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
            if(IsSuccess[0] && IsSuccess[1] && IsSuccess[2] && IsSuccess[4]){
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
        else if(IsSuccess[0] && IsSuccess[1] & IsSuccess[4]){
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
    public void onSelectMentorForUnite(ModelSchoolTeacher index){

    }
    @Override
    public void onClassClick(ModelSchoolClass m){
        rvClassList.setVisibility(View.GONE);
        Classes.remove(m);
        ClassListAdapter.UpdateData(Classes);
        ClassesSelected.add(m);
        ClassGridAdapter.UpdateData(ClassesSelected);
        IsSuccess[4] = true;
        isNextEnable();
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
            case R.id.civAvatar:
                onSelectAvatar();
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
        }
    }
    private void onCancelClick(){
        /*for(Fragment fragment : activity.getSupportFragmentManager().getFragments())
            if(fragment instanceof SchoolAddStudentFragment){
                activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                break;
            }*/
        activity.alert.hideKeyboard(activity);
        activity.onBackPressed();
    }
    private void onRejectClick(){
        activity.alert.hideKeyboard(activity);
        JsonObject json = new JsonObject();
        json.addProperty(F.Id, mRequest.getId());

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
        if(mRequest != null){
            mContact.setId(mRequest.getId());
            mContact.setUserId(mRequest.getSourceId());
        }
        mContact.setSchoolId(SCHOOL_ID);
        mContact.setFirstName(etFirstName.getText().toString());
        mContact.setLastName(etLastName.getText().toString());
        mContact.setPatronymic(etPatronymic.getText().toString());
        mContact.setClasses(ClassesSelected);

        String SOAP = SoapRequest(func_SchoolAddStudent, new Gson().toJson(mContact));
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
                            if(answer.isSuccess()) {
                                activity.presenter.onStartPresenter();
                                onCancelClick();
                            }
                        }
                    });
                }
            }
        });
    }
    private void onAddClassClick(){
        activity.alert.hideKeyboard(activity);
        if(rvClassList.getVisibility() == View.GONE){
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
        else rvClassList.setVisibility(View.GONE);
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
}