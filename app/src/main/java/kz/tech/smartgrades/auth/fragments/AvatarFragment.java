package kz.tech.smartgrades.auth.fragments;

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
import com.yalantis.ucrop.UCrop;

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
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.root.firebase.FireBaseImage;

import static android.app.Activity.RESULT_OK;

public class AvatarFragment extends Fragment implements View.OnClickListener{

    private AuthActivity activity;
    private TextView tvAvatarSelectTitle, tvToMakeAPhoto, tvContinue;

    private CircleImageView civAvatar;
    private LinearLayout llDrawing, llGallery, llCamera;

    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean isPermissionsGranted = false;
    private int selectedType = 0;

    private boolean IsSelectPhoto = false;
    private boolean[] isLoadSuccess = new boolean[2];
    private ProgressBar pgAvatarUpload;
    private Bitmap AvatarOriginal;
    private Bitmap Avatar;

    private DrawingSelectAdapter drawingSelectAdapter;
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


    public void setType(String type){
        if (drawingSelectAdapter == null) return;
        if(type.equals(S.CHILD)) drawingSelectAdapter.UpdateData(images_childs);
        else drawingSelectAdapter.UpdateData(images_parents);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_avatar_select, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
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

        pgAvatarUpload = view.findViewById(R.id.progressbar);
    }
    private void changeText(){
        tvContinue.setText(R.string.CONTINUE);
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
                setPhoto(img.getDrawingCache(), false);
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
            requestPermissions(permissions, PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && selectedType == 1) {
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED && selectedType == 0) {
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            try {
                startCrop(imageUri);
            } catch (Exception ignored) {}
        }
    }

    private void startCrop(@NonNull Uri uri) {
        String destinationFileName = "AvatarForUCrop.png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(activity.getCacheDir(), destinationFileName)));
        uCrop.withAspectRatio(480, 480);
        uCrop.withMaxResultSize(480, 480);
        uCrop.start(activity);
    }

    public void setPhoto(Bitmap image, boolean flag){
        Bitmap avatarOriginal = Bitmap.createBitmap(image.getWidth(),
                image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(avatarOriginal);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(image, 0F, 0F, null);
        IsSelectPhoto = true;
        isLoadSuccess[0] = isLoadSuccess[1] = false;
        tvContinue.setEnabled(false);
        tvContinue.setTextColor(getResources().getColor(R.color.gray_default));
        changeText();
        isNextEnable();
//        tvAddAvatar.setVisibility(View.GONE);
        AvatarOriginal = Bitmap.createScaledBitmap(avatarOriginal, 480, 480, true);
        Avatar = Bitmap.createScaledBitmap(avatarOriginal, 128, 128, true);
        civAvatar.setImageBitmap(Avatar);
        civAvatar.setAlpha(0.5f);
        pgAvatarUpload.setVisibility(View.VISIBLE);
        UploadImage(flag);
    }
    private void UploadImage(boolean flag){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        UploadTask uploadTask;
        if (flag) {
            AvatarOriginal.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            //UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());
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
//                            activity.onSetAvatarOriginal(uri.toString());
                            isLoadSuccess[0] = true;
                            isNextEnable();
                        }
                    });
                }
            });
        } else {
            isLoadSuccess[0] = true;
            isNextEnable();
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
//                        activity.onSetAvatar(uri.toString());
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
                activity.alert.onToast(activity.getResources().getString(R.string.Photo_uploaded_successfully));
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
    public void onClick(View v){
        switch(v.getId()){
            case R.id.llDrawing:
                onDrawing();
                break;
            case R.id.llGallery:
                selectedType = 0;
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
                break;
            case R.id.llCamera:
                selectedType = 1;
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
                break;
            case R.id.tvContinue:
                onContinue();
                break;
        }
    }
}
