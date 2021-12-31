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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.root.dialogs.DEditProfileField;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.LessonsTagAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolProfile;
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
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_DeleteUserData;
import static kz.tech.smartgrades._Web.func_GetThisSchoolProfile;
import static kz.tech.smartgrades._Web.func_SchoolUpdateData;

public class SchoolEditProfileFragment extends Fragment implements View.OnClickListener, LessonsTagAdapter.OnItemCLickListener {

    private SchoolActivity activity;
    private String SCHOOL_ID;

    private ImageView ivBack;
    private ConstraintLayout clLoading;
    private ImageView ivAvatar, ivEditOriginalAvatar, ivOptions;
    private CircleImageView civAvatar;
    private CardView cvAvatar, cvEditOriginalAvatar;
    private RecyclerView rvLessons;
    private TextView tvName, tvPhone, tvMail, tvAbout, tvLocation, tvNoLessons, tvSchoolStatus, tvLessonsLabel;

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

    private ModelSchoolProfile mProfile;
    private LessonsTagAdapter lessonsTagAdapter;

    private DEditProfileField dialog;

    public SchoolEditProfileFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        this.SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_school_edit_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadModel();
    }
    private void initViews(View view){
        clLoading = view.findViewById(R.id.clLoading);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        civAvatar = view.findViewById(R.id.civAvatar);
        cvAvatar = view.findViewById(R.id.cvAvatar);
        ivOptions = view.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(this);
        ivOptions.setVisibility(View.GONE);
        cvEditOriginalAvatar = view.findViewById(R.id.cvEditOriginalAvatar);
        ivEditOriginalAvatar = view.findViewById(R.id.ivEditOriginalAvatar);
        ivEditOriginalAvatar.setOnClickListener(this);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);
        pgAvatarOriginalUpload = view.findViewById(R.id.pgAvatarOriginalUpload);
        tvName = view.findViewById(R.id.tvName);
        tvName.setOnClickListener(this);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvPhone.setOnClickListener(this);
        tvMail = view.findViewById(R.id.tvMail);
        tvMail.setOnClickListener(this);
        tvAbout = view.findViewById(R.id.tvAbout);
        tvAbout.setOnClickListener(this);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvLocation.setOnClickListener(this);
        tvNoLessons = view.findViewById(R.id.tvNoLessons);
        rvLessons = view.findViewById(R.id.rvLessons);
        tvSchoolStatus = view.findViewById(R.id.tvSchoolStatus);
        tvLessonsLabel = view.findViewById(R.id.tvLessonsLabel);

        cvAvatar.setVisibility(View.GONE);
    }

    public void loadModel(){
        clLoading.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.Id, SCHOOL_ID);

        String SOAP = SoapRequest(func_GetThisSchoolProfile, jsonObject.toString());
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
                            mProfile = new Gson().fromJson(result, ModelSchoolProfile.class);
                            if(mProfile != null) onSetProfileData();
                            clLoading.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
    private void onSetProfileData(){
//        ivAvatar.setImageResource(0);
//        civAvatar.setImageResource(0);
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
                Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
            else civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
            ivAvatar.requestLayout();
            tvName.setTextColor(activity.getResources().getColor(R.color.black));
        }

        if (!stringIsNullOrEmpty(mProfile.getState()) && mProfile.getState().equals("4")) {
            rvLessons.setVisibility(View.GONE);
            tvLessonsLabel.setVisibility(View.GONE);
            tvNoLessons.setVisibility(View.GONE);
        } else {
            rvLessons.setVisibility(View.VISIBLE);
            tvLessonsLabel.setVisibility(View.VISIBLE);
            if (!listIsNullOrEmpty(mProfile.getLessons())) {
                tvNoLessons.setVisibility(View.GONE);
                lessonsTagAdapter = new LessonsTagAdapter();
                lessonsTagAdapter.setOnItemCLickListener(this);
                rvLessons.setAdapter(lessonsTagAdapter);
                rvLessons.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                lessonsTagAdapter.UpdateData(mProfile.getLessons());
            } else {
                tvNoLessons.setVisibility(View.VISIBLE);
                rvLessons.setAdapter(null);
            }
        }
        if (!stringIsNullOrEmpty(mProfile.getStateName())) {
            tvSchoolStatus.setText(mProfile.getStateName());
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
    }

    private void onPhotoSetting(){
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_photo_settings, null, false);
        dialog.setContentView(viewDialog);

        LinearLayout llGallery = viewDialog.findViewById(R.id.llGallery);
        LinearLayout llDrawing = viewDialog.findViewById(R.id.llDrawing);
        llDrawing.setVisibility(View.GONE);
        LinearLayout llCamera = viewDialog.findViewById(R.id.llCamera);
        LinearLayout llDelete = viewDialog.findViewById(R.id.llDelete);

        if(!stringIsNullOrEmpty(mProfile.getAvatarOriginal())) llDelete.setVisibility(View.VISIBLE);
        else{
            if(!stringIsNullOrEmpty(mProfile.getAvatar()))
                llDelete.setVisibility(View.VISIBLE);
            else llDelete.setVisibility(View.GONE);
        }
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
            jsonData.addProperty(F.UserId, SCHOOL_ID);
            jsonData.addProperty(F.Avatar, "1");

            String SOAP = SoapRequest(func_DeleteUserData, jsonData.toString());
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
                                    mProfile.setAvatar(null);
                                    mProfile.setAvatarOriginal(null);
                                    onSetProfileData();
                                    activity.alert.onToast(activity.getResources().getString(R.string.successfully_deleted));
                                    activity.login.updateUserData(LoginKey.AVATAR, null);
                                    activity.setProfileData();
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
                            mProfile.setAvatarOriginal(uri.toString());
                            isLoadSuccess[0] = true;
                            SaveImageToServer();
                        }
                    });
                }
            });
        }
        else mProfile.setAvatarOriginal(null);

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
                        mProfile.setAvatar(uri.toString());
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
                ModelSchoolProfile modelUser = new ModelSchoolProfile();
                modelUser.setId(SCHOOL_ID);
                modelUser.setAvatar(mProfile.getAvatar());
                modelUser.setAvatarOriginal(mProfile.getAvatarOriginal());

                String SOAP = SoapRequest(func_SchoolUpdateData, new Gson().toJson(modelUser));
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
                                        onSetProfileData();
                                        activity.login.updateUserData(LoginKey.AVATAR, mProfile.getAvatar());
                                        activity.setProfileData();
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

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivEditOriginalAvatar:
                onEditAvatar();
                break;
            case R.id.tvName:
                onEditName();
                break;
            case R.id.ivOptions:
                onOptions();
                break;
            case R.id.tvPhone:
                onEditPhone();
                break;
            case R.id.tvMail:
                onEditMail();
                break;
            case R.id.tvAbout:
                onEditAbout();
                break;
            case R.id.tvLocation:
                onEditLocation();
                break;
            case R.id.tvNoLessons:
                onEditLessons();
                break;
        }
    }

    private void onEditLessons() {
    }

    private void onOptions() {

    }

    public void onBackPressed(){
        activity.onBackPressed();
    }
    private void onEditAvatar(){
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        onPhotoSetting();
    }
    private void onEditName(){
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        ModelUser model = new ModelUser();
        model.setName(mProfile.getName());

        dialog = new DEditProfileField(activity, model, "SchoolName");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                ModelSchoolProfile m = new ModelSchoolProfile();
                m.setName(mUser.getName());
                m.setId(SCHOOL_ID);
                String SOAP = SoapRequest(func_SchoolUpdateData, new Gson().toJson(m));
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
    private void onEditMail() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        ModelUser model = new ModelUser();
        model.setMail(mProfile.getMail());

        dialog = new DEditProfileField(activity, model, "Mail");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                ModelSchoolProfile m = new ModelSchoolProfile();
                m.setMail(mUser.getMail());
                m.setId(SCHOOL_ID);
                String SOAP = SoapRequest(func_SchoolUpdateData, new Gson().toJson(m));
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
    private void onEditPhone() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        ModelUser model = new ModelUser();
        model.setPhone(mProfile.getPhone());

        dialog = new DEditProfileField(activity, model, "Phone");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                ModelSchoolProfile m = new ModelSchoolProfile();
                m.setPhone(mUser.getPhone());
                m.setId(SCHOOL_ID);
                String SOAP = SoapRequest(func_SchoolUpdateData, new Gson().toJson(m));
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
    private void onEditLocation() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        ModelUser model = new ModelUser();
        model.setAddress(mProfile.getAddress());

        dialog = new DEditProfileField(activity, model, "SchoolLocation");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                ModelSchoolProfile m = new ModelSchoolProfile();
                m.setAddress(mUser.getAddress());
                m.setId(SCHOOL_ID);
                String SOAP = SoapRequest(func_SchoolUpdateData, new Gson().toJson(m));
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
    private void onEditAbout() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        ModelUser model = new ModelUser();
        model.setAboutMe(mProfile.getAboutMe());

        dialog = new DEditProfileField(activity, model, "Bio");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                ModelSchoolProfile m = new ModelSchoolProfile();
                m.setAboutMe(mUser.getAboutMe());
                m.setId(SCHOOL_ID);
                String SOAP = SoapRequest(func_SchoolUpdateData, new Gson().toJson(m));
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

    @Override
    public void onSelectLessonForClassClick(ModelLesson m) {
        onEditLessons();
    }

    @Override
    public void onSelectLessonForDeleteClick(ModelLesson m) {

    }
}
