package kz.tech.smartgrades.mentor.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

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
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades._System.AvatarHasImage;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_DeleteUserData;
import static kz.tech.smartgrades._Web.func_UpdateUserData;

public class MentorEditProfileFragment extends Fragment implements View.OnClickListener {

    private MentorActivity activity;

    private CircleImageView civAvatar, ivPhotoSetting;
    private TextView tvFullName, tvLogin;
    private EditText etAboutMe, etKeyWordsYourMentors;
    private boolean isAboutMe = false;
    private boolean isKeyWordsYourMentors = false;
    private Button btnSend;
    private ImageView ivBack;

    private Bitmap SelectAvatar;
    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean isPermissionsGranted = false;


    public MentorEditProfileFragment() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_dialog_edit_profile_mentor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setProfileData();
    }
    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivPhotoSetting = view.findViewById(R.id.ivPhotoSetting);
        ivPhotoSetting.setOnClickListener(this);

        civAvatar = view.findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);

        etAboutMe = view.findViewById(R.id.etAboutMe);
        etKeyWordsYourMentors = view.findViewById(R.id.etKeyWordsYourMentors);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        etAboutMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) { }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() > 0) isAboutMe = true;
                else isAboutMe = false;
                onButtonSendIsReady();
            }
        });
        etKeyWordsYourMentors.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) { }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() > 0) isKeyWordsYourMentors = true;
                else isKeyWordsYourMentors = false;
                onButtonSendIsReady();
            }
        });
    }

    private void setProfileData() {
        String avatar = activity.login.loadUserDate(LoginKey.AVATAR);
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

        String fullName = activity.login.loadUserDate(LoginKey.FIRST_NAME) + " " + activity.login.loadUserDate(LoginKey.LAST_NAME);
        if (fullName != null && !fullName.isEmpty()) tvFullName.setText(fullName);

        String login = activity.login.loadUserDate(LoginKey.LOGIN);
        if (login != null && !login.isEmpty()) tvFullName.setText(login);

        String aboutMe = activity.login.loadUserDate(LoginKey.ABOUT_ME);
        if (aboutMe != null && !aboutMe.isEmpty()) etAboutMe.setText(aboutMe);

        String keyWords = activity.login.loadUserDate(LoginKey.DESCRIPTION);
        if (keyWords != null && !keyWords.isEmpty()) etKeyWordsYourMentors.setText(keyWords);
    }

    private void onButtonSendIsReady() {
        btnSend.setBackgroundResource(isAboutMe || isKeyWordsYourMentors ? R.drawable.btn_background_purple :
                R.drawable.btn_background_purple_alpha);
        btnSend.setEnabled(isAboutMe || isKeyWordsYourMentors);
    }

    private void onSendClick() {
        activity.presenter.getAddMentorWorksheet(etAboutMe.getText().toString(), etKeyWordsYourMentors.getText().toString());
    }

    public void onBackPressed() {
        activity.onRemoveFragment();
    }


    private void ToMakeAPhoto(int Type) {
        getPermissionsToUseCamera();
        if (isPermissionsGranted) {
            Intent PictureIntent = null;
            if(Type == REQUEST_TAKE_PHOTO) PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            else if(Type == REQUEST_LOAD_PHOTO){
                PictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //PictureIntent.setType("image/*");
            }
            if (PictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                if(Type == REQUEST_TAKE_PHOTO) {
                    try {
                        File photoFile = createImageFile();
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(activity, "kz.tech.esparta.fileprovider", photoFile);
                            mPhotoFile = photoFile;
                            PictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        }
                        startActivityForResult(PictureIntent, REQUEST_TAKE_PHOTO);
                    } catch (IOException ex) { }
                }
                else if(Type == REQUEST_LOAD_PHOTO)
                    startActivityForResult(Intent.createChooser(PictureIntent, "Select Picture"), REQUEST_LOAD_PHOTO);
            }
        }
    }
    public void getPermissionsToUseCamera() {
        String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean check = true;
        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                check = false;
            }
        }
        isPermissionsGranted = check;
        if (!isPermissionsGranted) ActivityCompat.requestPermissions(activity,permissions,PERMISSIONS_REQUEST);
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
                SelectAvatar = BitmapFactory.decodeStream(imageStream);
                civAvatar.setBackground(null);
                Glide.with(this).load(imageUri).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.img_add_child_active)).into(civAvatar);
                UpdateAvatar();
            }
            catch (Exception e){ }
        }
    }

    private void onPhotoSetting() {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_photo_settings, null, false);
        dialog.setContentView(viewDialog);

        LinearLayout llDrawing = viewDialog.findViewById(R.id.llDrawing);
        LinearLayout llGallery = viewDialog.findViewById(R.id.llGallery);
        LinearLayout llCamera = viewDialog.findViewById(R.id.llCamera);
        LinearLayout llDelete = viewDialog.findViewById(R.id.llDelete);

        if (AvatarHasImage(activity, civAvatar)) llDelete.setVisibility(View.VISIBLE);
        else llDelete.setVisibility(View.GONE);
        dialog.show();

        llDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                drawingSelectAdapter.setOnItemClickListener(new DrawingSelectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ImageView img) {
                        dialog.dismiss();
                        img.setDrawingCacheEnabled(true);
                        SelectAvatar = Bitmap.createBitmap(img.getDrawingCache());
                        civAvatar.setImageBitmap(SelectAvatar);
                        UpdateAvatar();
                    }
                });
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
                SelectAvatar = null;
                UpdateAvatar();
            }
        });
    }
    private void UpdateAvatar(){
        if (SelectAvatar != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bitmap.createScaledBitmap(SelectAvatar, 100, 100, true);
            SelectAvatar.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            activity.login.updateUserData(LoginKey.AVATAR, result.getResult().toString());
                            activity.onSetMentorData(null);

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                            jsonData.addProperty(F.Avatar, result.getResult().toString());

                            String SOAP = SoapRequest(func_UpdateUserData, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(final Call call, IOException e) { }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    if (response.code() >= 200 && response.code() <= 300) {
                                        String xml = response.body().string();
                                        String result = _Web.XMLReader(xml);
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                                else activity.alert.onToast("Фото успешно обновлено.");
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
        else{
            activity.login.updateUserData(LoginKey.AVATAR, null);
            activity.onSetMentorData(null);

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
            jsonData.addProperty(F.Avatar, "1");

            String SOAP = SoapRequest(func_DeleteUserData, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.code() >= 200 && response.code() <= 300) {
                        String xml = response.body().string();
                        String result = _Web.XMLReader(xml);
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                else activity.alert.onToast("Фото успешно удалено.");
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnSend:
                onSendClick();
                break;
            case R.id.ivPhotoSetting:
                onPhotoSetting();
                break;
            case R.id.civAvatar:
                onPhotoSetting();
                break;
        }
    }
}
