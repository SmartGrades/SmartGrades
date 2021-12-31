package kz.tech.smartgrades.school.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.school.SchoolActivity;

import static android.app.Activity.RESULT_OK;

public class SchoolSetAvatarFragment extends Fragment implements View.OnClickListener {

    private SchoolActivity activity;
    private TextView tvAvatarSelectTitle, tvToMakeAPhoto, tvContinue;;

    private DrawingSelectAdapter drawingSelectAdapter;

    private Bitmap AvatarOriginal;
    private Bitmap Avatar;
    private CircleImageView civAvatar;
    private LinearLayout llDrawing, llGallery, llCamera;

    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean isPermissionsGranted = false;


    private int[] images_parents = {R.drawable.parent_1, R.drawable.parent_2, R.drawable.parent_3, R.drawable.parent_4,
            R.drawable.parent_5, R.drawable.parent_6, R.drawable.parent_7, R.drawable.parent_8,
            R.drawable.parent_9, R.drawable.parent_10, R.drawable.parent_11, R.drawable.parent_12,
            R.drawable.parent_13, R.drawable.parent_14, R.drawable.parent_15, R.drawable.parent_16,
            R.drawable.parent_17, R.drawable.parent_18, R.drawable.parent_19, R.drawable.parent_20,
            R.drawable.parent_21, R.drawable.parent_22, R.drawable.parent_23, R.drawable.parent_24,
            R.drawable.parent_25, R.drawable.parent_26, R.drawable.parent_27, R.drawable.parent_28,
            R.drawable.parent_29, R.drawable.parent_30};
    private int[] images_childs = {R.drawable.child_1, R.drawable.child_2, R.drawable.child_3, R.drawable.child_4,
            R.drawable.child_5, R.drawable.child_6, R.drawable.child_7, R.drawable.child_8,
            R.drawable.child_9, R.drawable.child_10, R.drawable.child_11, R.drawable.child_12,
            R.drawable.child_13, R.drawable.child_14, R.drawable.child_15, R.drawable.child_16,
            R.drawable.child_17, R.drawable.child_18, R.drawable.child_19, R.drawable.child_20,
            R.drawable.child_21, R.drawable.child_22, R.drawable.child_23, R.drawable.child_24,
            R.drawable.child_25, R.drawable.child_26, R.drawable.child_27, R.drawable.child_28};


    public void setType(String type) {
        if (type.equals(S.CHILD)) drawingSelectAdapter.UpdateData(images_childs);
        else drawingSelectAdapter.UpdateData(images_parents);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_avatar_select, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view) {
        civAvatar = view.findViewById(R.id.civAvatar);

        llDrawing = view.findViewById(R.id.llDrawing);
        llDrawing.setOnClickListener(this);
        llGallery = view.findViewById(R.id.llGallery);
        llGallery.setOnClickListener(this);
        llCamera = view.findViewById(R.id.llCamera);
        llCamera.setOnClickListener(this);

        tvToMakeAPhoto = view.findViewById(R.id.tvToMakeAPhoto);
        tvToMakeAPhoto.setOnClickListener(this);
        tvContinue = view.findViewById(R.id.tvContinue);
        tvContinue.setOnClickListener(this);
        tvAvatarSelectTitle = view.findViewById(R.id.tvAvatarSelectTitle);

        drawingSelectAdapter = new DrawingSelectAdapter(activity);
    }
    private void changeText() {
        tvContinue.setText("ПРОДОЛЖИТЬ");
    }

    private void onDrawing() {
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

        RecyclerView rvAvatarsList = viewDialog.findViewById(R.id.rvAvatarsList);
        rvAvatarsList.setLayoutManager(new GridLayoutManager(activity, 5));
        rvAvatarsList.setAdapter(drawingSelectAdapter);
        drawingSelectAdapter.ShowData();
        drawingSelectAdapter.setOnItemClickListener(new DrawingSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView img) {
                dialog.dismiss();
                img.setDrawingCacheEnabled(true);
                AvatarOriginal = Bitmap.createScaledBitmap(img.getDrawingCache(), 480, 321, true);
                Avatar = Bitmap.createScaledBitmap(img.getDrawingCache(), 48, 48, true);
                civAvatar.setBackground(null);
                civAvatar.setImageBitmap(AvatarOriginal);
                changeText();
            }
        });
    }
    private void ToMakeAPhoto(int Type) {
        getPermissionsToUseCamera();
        if (isPermissionsGranted) {
            Intent PictureIntent = null;
            if(Type == REQUEST_TAKE_PHOTO) PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            else if(Type == REQUEST_LOAD_PHOTO){
                PictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                AvatarOriginal = BitmapFactory.decodeStream(imageStream);
                civAvatar.setBackground(null);
                Glide.with(this).load(imageUri).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.img_add_child_active)).into(civAvatar);
            }
            catch (Exception e){ }
            changeText();
        }
    }

    private void onContinue() {
        if (AvatarOriginal == null) ;//activity.onNextFragment();
        else{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            AvatarOriginal.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
            UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //activity.alert.onToast("Ошибка, попробуйте выбрать аватарку позже.");
                    //activity.onNextFragment();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //activity.onNextFragment();
                            //activity.onAvatarSelectString(uri.toString());
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDrawing:
                onDrawing();
                break;
            case R.id.llGallery:
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
                break;
            case R.id.llCamera:
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
                break;
            case R.id.tvContinue:
                onContinue();
                break;
        }
    }
}// crash when set photo from gallery
