package kz.tech.smartgrades.parent_add_child.fragments;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
import kz.tech.smartgrades.root.firebase.FireBaseImage;

import static android.app.Activity.RESULT_OK;

public class pacAvatarFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private TextView tvAvatarSelectTitle, tvToMakeAPhoto, tvContinue;;

    private CircleImageView civAvatar;
    private LinearLayout llDrawing, llGallery, llCamera;

    private boolean[] isLoad = new boolean[3];

    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean isPermissionsGranted = false;

    private ProgressBar pgAvatarUpload;
    private boolean IsSelectPhoto = false;
    private boolean[] isLoadSuccess = new boolean[2];
    private Bitmap AvatarOriginal;
    private Bitmap Avatar;

    private DrawingSelectAdapter drawingSelectAdapter;
    private int[] images_childs = {R.drawable.child_1, R.drawable.child_2, R.drawable.child_3, R.drawable.child_4,
            R.drawable.child_5, R.drawable.child_6, R.drawable.child_7, R.drawable.child_8,
            R.drawable.child_9, R.drawable.child_10, R.drawable.child_11, R.drawable.child_12,
            R.drawable.child_13, R.drawable.child_14, R.drawable.child_15, R.drawable.child_16,
            R.drawable.child_17, R.drawable.child_18, R.drawable.child_19, R.drawable.child_20,
            R.drawable.child_21, R.drawable.child_22, R.drawable.child_23, R.drawable.child_24,
            R.drawable.child_25, R.drawable.child_26, R.drawable.child_27, R.drawable.child_28};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
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
        changeText();
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
        drawingSelectAdapter.UpdateData(images_childs);

        pgAvatarUpload = view.findViewById(R.id.progressbar);
    }

    private void changeText() {
        tvAvatarSelectTitle.setText("Установите аватар для ребенка");
    }


    private void onDrawing(){
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
        drawingSelectAdapter.setOnItemClickListener(new DrawingSelectAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ImageView img){
                dialog.dismiss();
                img.setDrawingCacheEnabled(true);
                setPhoto(img.getDrawingCache());
            }
        });
    }
    private void ToMakeAPhoto(int Type){
        getPermissionsToUseCamera();
        if(isPermissionsGranted){
            Intent PictureIntent = null;
            if(Type == REQUEST_TAKE_PHOTO)
                PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            else if(Type == REQUEST_LOAD_PHOTO){
                PictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
    public void getPermissionsToUseCamera(){
        String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean check = true;
        for(String p : permissions){
            if(ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED){
                check = false;
            }
        }
        isPermissionsGranted = check;
        if(!isPermissionsGranted)
            ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST);
    }
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            Uri imageUri = null;
            if(requestCode == REQUEST_TAKE_PHOTO) imageUri = Uri.fromFile(mPhotoFile);
            else if(requestCode == REQUEST_LOAD_PHOTO && null != data) imageUri = data.getData();
            try{
                InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
                setPhoto(BitmapFactory.decodeStream(imageStream));
            } catch(Exception e){
            }
        }
    }
    public void setPhoto(Bitmap avatarOriginal){
        IsSelectPhoto = true;
        isLoadSuccess[0] = isLoadSuccess[1] = false;
        tvContinue.setEnabled(false);
        tvContinue.setTextColor(getResources().getColor(R.color.gray_default));
        tvContinue.setText("Продолжить");
        isNextEnable();
        AvatarOriginal = Bitmap.createScaledBitmap(avatarOriginal, 480, 321, true);
        Avatar = Bitmap.createScaledBitmap(avatarOriginal, 96, 96, true);
        civAvatar.setImageBitmap(Avatar);
        civAvatar.setAlpha(0.5f);
        pgAvatarUpload.setVisibility(View.VISIBLE);
        UploadImage();
    }
    private void UploadImage(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AvatarOriginal.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
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
                        activity.setChildAvatarOriginal(uri.toString());
                        isLoadSuccess[0] = true;
                        isNextEnable();
                    }
                });
            }
        });
        outputStream = new ByteArrayOutputStream();
        Avatar.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
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
                        activity.setChildAvatar(uri.toString());
                        isLoadSuccess[1] = true;
                        isNextEnable();
                    }
                });
            }
        });
    }
    private void isNextEnable(){
        if(IsSelectPhoto){
            if(isLoadSuccess[0] && isLoadSuccess[1]){
                activity.alert.onToast("Фото успешно загружено.");
                pgAvatarUpload.setVisibility(View.GONE);
                civAvatar.setAlpha(1f);
                tvContinue.setEnabled(true);
                tvContinue.setTextColor(getResources().getColor(R.color.white));
            }
            else{
                tvContinue.setEnabled(false);
                tvContinue.setTextColor(getResources().getColor(R.color.gray_default));
            }
        }
        else{
            tvContinue.setEnabled(true);
            tvContinue.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void onContinue(){
        activity.onNextFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
}
