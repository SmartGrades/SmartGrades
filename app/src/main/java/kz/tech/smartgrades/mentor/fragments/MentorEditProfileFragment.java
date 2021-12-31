package kz.tech.smartgrades.mentor.fragments;

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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorLessonsTagAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorProfileSchoolsAdapter;
import kz.tech.smartgrades.mentor.dialog.MentorDialogEditProfileLessons;
import kz.tech.smartgrades.mentor.models.ModelMentorEditProfileLessons;
import kz.tech.smartgrades.mentor.models.ModelMentorProfile;
import kz.tech.smartgrades.root.dialogs.DEditProfileField;
import kz.tech.smartgrades.root.dialogs.DialogSelectCountry;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelCountry;
import kz.tech.smartgrades.root.models.ModelLesson;
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
import static kz.tech.smartgrades._Web.func_GetMentorProfile;
import static kz.tech.smartgrades._Web.func_MentorUpdateProfileLessons;
import static kz.tech.smartgrades._Web.func_UpdateUserData;

public class MentorEditProfileFragment extends Fragment implements View.OnClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;

    private ImageView ivBack;
    private ImageView ivAvatar, ivEditOriginalAvatar, ivEditAvatar;
    private CircleImageView civAvatar;
    private CardView cvAvatar, cvEditAvatar, cvEditOriginalAvatar;
    private RecyclerView rvLessons, rvSchoolList;
    private TextView tvName, tvStatus, tvLogin, tvPhone, tvEmail, tvBio, tvLocation, tvLessonsEmpty, tvSchoolsEmpty;

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

    private ModelMentorProfile mProfile;

    private MentorDialogEditProfileLessons dialog;


    public MentorEditProfileFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_mentor_edit_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadModel();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        civAvatar = view.findViewById(R.id.civAvatar);
        cvAvatar = view.findViewById(R.id.cvAvatar);
        cvEditAvatar = view.findViewById(R.id.cvEditAvatar);
        cvEditOriginalAvatar = view.findViewById(R.id.cvEditOriginalAvatar);
        ivEditAvatar = view.findViewById(R.id.ivEditAvatar);
        ivEditAvatar.setOnClickListener(this);
        ivEditOriginalAvatar = view.findViewById(R.id.ivEditOriginalAvatar);
        ivEditOriginalAvatar.setOnClickListener(this);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);
        pgAvatarOriginalUpload = view.findViewById(R.id.pgAvatarOriginalUpload);
        rvLessons = view.findViewById(R.id.rvLessons);
        tvLessonsEmpty = view.findViewById(R.id.tvLessonsEmpty);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvName = view.findViewById(R.id.tvName);
        tvName.setOnClickListener(this);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvPhone.setOnClickListener(this);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvEmail.setOnClickListener(this);
        tvBio = view.findViewById(R.id.tvBio);
        tvBio.setOnClickListener(this);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvLocation.setOnClickListener(this);
        tvSchoolsEmpty = view.findViewById(R.id.tvSchoolsEmpty);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);

        cvAvatar.setVisibility(View.GONE);
        cvEditAvatar.setVisibility(View.GONE);
    }

    public void loadModel(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.Id, MENTOR_ID);

        String SOAP = SoapRequest(func_GetMentorProfile, jsonObject.toString());
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
                            mProfile = new Gson().fromJson(result, ModelMentorProfile.class);
                            if(mProfile != null) onSetProfileData();
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
            cvEditAvatar.setVisibility(View.GONE);
            cvEditOriginalAvatar.setVisibility(View.VISIBLE);
            Picasso.get().load(avatarOriginal).fit().centerInside().into(ivAvatar);
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 321, getResources().getDisplayMetrics());
            tvName.setTextColor(activity.getResources().getColor(R.color.white));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.white));
        }
        else{
            ivAvatar.setImageResource(0);
            cvAvatar.setVisibility(View.VISIBLE);
            cvEditAvatar.setVisibility(View.VISIBLE);
            cvEditOriginalAvatar.setVisibility(View.GONE);
            if(!stringIsNullOrEmpty(avatar))
                Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
            else civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
            ivAvatar.requestLayout();
            tvName.setTextColor(activity.getResources().getColor(R.color.black));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.black));
        }

        if(!stringIsNullOrEmpty(mProfile.getFirstName()) && !stringIsNullOrEmpty(mProfile.getLastName())){
            tvName.setText(mProfile.getFirstName() + " " + mProfile.getLastName());
        }
        if(!stringIsNullOrEmpty(mProfile.getLogin())){
            tvLogin.setText(mProfile.getLogin());
        }
        if(!stringIsNullOrEmpty(mProfile.getPhone())){
            tvPhone.setText(mProfile.getPhone());
        }
        if(!stringIsNullOrEmpty(mProfile.getMail())){
            tvEmail.setText(mProfile.getMail());
        }
        else tvEmail.setText(R.string.Email_not_specified);
        if(!stringIsNullOrEmpty(mProfile.getAddress())){
            tvLocation.setText(mProfile.getAddress());
        }
        else tvLocation.setText("Адрес не указан");
        if(!stringIsNullOrEmpty(mProfile.getAboutMe())){
            tvBio.setText(mProfile.getAboutMe());
        }
        else tvBio.setText(activity.getResources().getString(R.string.no_bio));

        if(!listIsNullOrEmpty(mProfile.getLessonsList())){
            tvLessonsEmpty.setVisibility(View.GONE);
            MentorLessonsTagAdapter adapter = new MentorLessonsTagAdapter();
            rvLessons.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
            rvLessons.setAdapter(adapter);
            adapter.updateData(mProfile.getLessonsList());
            adapter.setOnItemCLickListener(new MentorLessonsTagAdapter.OnItemCLickListener(){
                @Override
                public void onItemClick(ModelLesson m){
                    dialog = new MentorDialogEditProfileLessons(activity, mProfile.getLessonsList());
                    dialog.setOnItemClickListener(new MentorDialogEditProfileLessons.OnItemClickListener(){
                        @Override
                        public void onSaveClick(ArrayList<ModelLesson> lessonsList){
                            dialog.dismiss();

                            ModelMentorEditProfileLessons model = new ModelMentorEditProfileLessons();
                            model.setId(MENTOR_ID);
                            model.setLessons(lessonsList);
                            String SOAP = SoapRequest(func_MentorUpdateProfileLessons, new Gson().toJson(model));
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
                                                if(answer.isSuccess()) loadModel();
                                                else activity.alert.onToast(answer.getMessage());
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
        else tvLessonsEmpty.setVisibility(View.VISIBLE);

        if(!listIsNullOrEmpty(mProfile.getSchoolsList())){
            tvSchoolsEmpty.setVisibility(View.GONE);
            MentorProfileSchoolsAdapter adapter = new MentorProfileSchoolsAdapter();
            rvSchoolList.setAdapter(adapter);
            rvSchoolList.setLayoutManager(new LinearLayoutManager(activity));
            adapter.updateData(mProfile.getSchoolsList());
        }
        else tvSchoolsEmpty.setVisibility(View.VISIBLE);
    }
    public void onSaveProfileData(ModelUser mUser, String Type){
        if(Type.equals("Name")){
            activity.login.updateUserData(LoginKey.FIRST_NAME, mUser.getFirstName());
            activity.login.updateUserData(LoginKey.LAST_NAME, mUser.getLastName());
        }
        if(Type.equals("Login"))
            activity.login.updateUserData(LoginKey.LOGIN, mUser.getLogin());
        if(Type.equals("Phone"))
            activity.login.updateUserData(LoginKey.PHONE, mUser.getPhone());
        if(Type.equals("Mail"))
            activity.login.updateUserData(LoginKey.MAIL, mUser.getMail());
        if(Type.equals("Bio"))
            activity.login.updateUserData(LoginKey.ABOUT_ME, mUser.getAboutMe());
        if(Type.equals("Avatar"))
            activity.login.updateUserData(LoginKey.AVATAR, mUser.getAvatar());

        activity.onSetMentorData(null);
    }

    private void onPhotoSetting(){
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_photo_settings, null, false);
        dialog.setContentView(viewDialog);

        LinearLayout llDrawing = viewDialog.findViewById(R.id.llDrawing);
        LinearLayout llGallery = viewDialog.findViewById(R.id.llGallery);
        LinearLayout llCamera = viewDialog.findViewById(R.id.llCamera);
        LinearLayout llDelete = viewDialog.findViewById(R.id.llDelete);

        if(!stringIsNullOrEmpty(mProfile.getAvatarOriginal())) llDelete.setVisibility(View.VISIBLE);
        else{
            if(!stringIsNullOrEmpty(mProfile.getAvatar()))
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
                InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
                setPhoto(BitmapFactory.decodeStream(imageStream), true);
            } catch(Exception ignored){
            }
        }
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
                cvEditAvatar.setVisibility(View.GONE);
                cvEditOriginalAvatar.setVisibility(View.VISIBLE);
            }
            else{
                AvatarOriginal = null;
                civAvatar.setImageBitmap(Avatar);
                civAvatar.setAlpha(0.5f);
                pgAvatarUpload.setVisibility(View.VISIBLE);
                cvAvatar.setVisibility(View.VISIBLE);
                cvEditAvatar.setVisibility(View.VISIBLE);
                cvEditOriginalAvatar.setVisibility(View.GONE);
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
            jsonData.addProperty(F.UserId, MENTOR_ID);
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
            ModelUser modelUser = new ModelUser();
            modelUser.setUserId(MENTOR_ID);
            modelUser.setAvatar(mProfile.getAvatar());
            modelUser.setAvatarOriginal(mProfile.getAvatarOriginal());

            String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(modelUser));
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
                                    onSaveProfileData(modelUser, "Avatar");
                                    onSetProfileData();
                                    if(AvatarOriginal != null){
                                        ivAvatar.setAlpha(1f);
                                        pgAvatarOriginalUpload.setVisibility(View.GONE);
                                    }
                                    else{
                                        civAvatar.setAlpha(1f);
                                        pgAvatarUpload.setVisibility(View.GONE);
                                    }
                                }
                                else activity.alert.onToast(answer.getMessage());
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
            case R.id.ivEditAvatar:
                onEditAvatar();
                break;
            case R.id.ivEditOriginalAvatar:
                onEditAvatar();
                break;
            case R.id.tvName:
                onEditName();
                break;
            case R.id.tvLogin:
                onEditLogin();
                break;
            case R.id.tvPhone:
                onEditPhone();
                break;
            case R.id.tvEmail:
                onEditEmail();
                break;
            case R.id.tvBio:
                onEditBio();
                break;
            case R.id.tvLocation:
                onEditLocation();
                break;
        }
    }
    public void onBackPressed(){
        activity.onRemoveFragment();
    }
    private void onEditAvatar(){
        onPhotoSetting();
    }
    private void onEditName(){
        ModelUser model = new ModelUser();
        model.setFirstName(mProfile.getFirstName());
        model.setLastName(mProfile.getLastName());

        DEditProfileField dialog = new DEditProfileField(activity, model, "Name");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                mUser.setUserId(MENTOR_ID);
                String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(mUser));
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
                                        onSaveProfileData(mUser, "Name");
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
    private void onEditLogin(){
        ModelUser model = new ModelUser();
        model.setLogin(mProfile.getLogin());
        DEditProfileField dialog = new DEditProfileField(activity, model, "Login");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                mUser.setUserId(MENTOR_ID);
                String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(mUser));
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
                                        onSaveProfileData(mUser, "Login");
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
    private void onEditPhone(){
        ModelUser model = new ModelUser();
        model.setPhone(mProfile.getPhone());
        DEditProfileField dialog = new DEditProfileField(activity, model, "Phone");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                mUser.setUserId(MENTOR_ID);
                String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(mUser));
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
                                        onSaveProfileData(mUser, "Phone");
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
    private void onEditEmail(){
        ModelUser model = new ModelUser();
        model.setMail(mProfile.getMail());
        DEditProfileField dialog = new DEditProfileField(activity, model, "Mail");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                mUser.setUserId(MENTOR_ID);
                String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(mUser));
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
                                        onSaveProfileData(mUser, "Mail");
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
    private void onEditBio(){
        ModelUser model = new ModelUser();
        model.setLogin(mProfile.getAboutMe());
        DEditProfileField dialog = new DEditProfileField(activity, model, "Bio");
        dialog.setOnItemClickListener(new DEditProfileField.OnItemClickListener(){
            @Override
            public void onOkClick(ModelUser mUser){
                mUser.setUserId(MENTOR_ID);
                String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(mUser));
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
                                        onSaveProfileData(mUser, "Bio");
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
    private void onEditLocation(){
        DialogSelectCountry dialog = new DialogSelectCountry(activity);
        dialog.setOnItemClickListener(new DialogSelectCountry.OnItemClickListener(){
            @Override
            public void onClick(ModelCountry Country){
                activity.alert.hideKeyboard(activity);
                ModelUser mUser = new ModelUser();
                mUser.setUserId(MENTOR_ID);
                mUser.setAddress(Country.getName());
                String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(mUser));
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
                                        loadModel();
                                    }
                                    else activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}