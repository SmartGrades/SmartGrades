package kz.tech.smartgrades.school.dialogs;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades.F.isNameValid;

public class ADSchoolAddContact extends AlertDialog implements View.OnClickListener{

    private AlertDialog dialog;
    private SchoolActivity activity;
    private TextView tvTitle;
    private EditText etLastName, etFirstName, etPatronymic;
    private CircleImageView civAvatar;
    private TextView tvCancel;
    private TextView tvAdd;
    private boolean[] isOkayEnable = new boolean[2];
    private TextView tvAddAvatar;
    private Bitmap AvatarOriginal;
    private Bitmap Avatar;
    private ProgressBar pgAvatarUpload;

    private ModelSchoolCreateContact mContact;
    private boolean IsSelectPhoto = false;
    public File mPhotoFile;
    private boolean isPermissionsGranted = false;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean[] isLoadSuccess = new boolean[2];

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

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onAddClick(ModelSchoolCreateContact mContact);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ADSchoolAddContact(SchoolActivity activity, String title){
        super(activity);
        this.activity = activity;

        View view = getLayoutInflater().inflate(R.layout.ad_school_add_new_contact, null);
        Builder builder = new Builder(activity).setView(view);

        initViews(view);
        tvTitle.setText(title);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void initViews(View view){
        tvTitle = view.findViewById(R.id.tvTitle);
        etLastName = view.findViewById(R.id.etLastName);
        etLastName.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                if (arg0.length() == 0) isOkayEnable[0] = false;
                else if (arg0.length() > 0) {
                    if (Character.isLowerCase(arg0.toString().charAt(0))) etLastName.setText(arg0.toString().toUpperCase());
                    etLastName.setSelection(etLastName.getText().length());
                    isOkayEnable[0] = isNameValid(arg0.toString());
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
                if (arg0.length() == 0) isOkayEnable[1] = false;
                else if (arg0.length() > 0) {
                    if (Character.isLowerCase(arg0.toString().charAt(0))) etFirstName.setText(arg0.toString().toUpperCase());
                    etFirstName.setSelection(etFirstName.getText().length());
                    isOkayEnable[1] = isNameValid(arg0.toString());
                }
                isNextEnable();
            }
        });
        etPatronymic = view.findViewById(R.id.etPatronymic);
        tvAddAvatar = view.findViewById(R.id.tvAddAvatar);
        civAvatar = view.findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);

        mContact = new ModelSchoolCreateContact();
    }

    private void isNextEnable(){
        if(isOkayEnable[0] && isOkayEnable[1]){
            if(IsSelectPhoto && !mContact.getAvatar().isEmpty()){

            }
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
    public void setPhoto(Bitmap image, boolean flag){
        Bitmap avatarOriginal = Bitmap.createBitmap(image.getWidth(),
                image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(avatarOriginal);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(image, 0F, 0F, null);
        IsSelectPhoto = true;
        tvAddAvatar.setVisibility(View.GONE);
        AvatarOriginal = Bitmap.createScaledBitmap(avatarOriginal, 480, 480, true);
        Avatar = Bitmap.createScaledBitmap(avatarOriginal, 128, 128, true);
        civAvatar.setImageBitmap(AvatarOriginal);
        civAvatar.setAlpha(0.5f);
        pgAvatarUpload.setVisibility(View.VISIBLE);
        UploadImage(flag);
    }
    private void UploadImage(boolean flag){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        UploadTask uploadTask;
        if (flag) {
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
            activity.alert.onToast("Фото успешно загружено.");
            pgAvatarUpload.setVisibility(View.GONE);
            civAvatar.setAlpha(1f);
            isNextEnable();
        }
    }
    public void ToMakeAPhoto(int Type){
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
                        activity.startActivityForResult(PictureIntent, REQUEST_TAKE_PHOTO);
                    } catch(IOException ex){
                    }
                }
                else if(Type == REQUEST_LOAD_PHOTO)
                    activity.startActivityForResult(Intent.createChooser(PictureIntent, "Select Picture"), REQUEST_LOAD_PHOTO);
            }
        }
    }
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }
    public void getPermissionsToUseCamera(){
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean check = true;
        for(String p : permissions){
            if(ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED){
                check = false;
            }
        }
        isPermissionsGranted = check;
        if(!isPermissionsGranted)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(permissions, PERMISSIONS_REQUEST);
            }
    }
    public void startCrop(@NonNull Uri uri) {
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

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.civAvatar:
                onSelectAvatar();
                break;
            case R.id.tvAdd:
                onOkClick();
                break;
        }
    }
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
                View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_drawing, null, false);
                DrawingSheetDialog.setContentView(viewDialog);
                View bottomSheet = DrawingSheetDialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                bottomSheet.setLayoutParams(layoutParams);
                layoutParams.height = (_System.getWindowHeight(activity));
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                DrawingSheetDialog.show();

                drawingSelectAdapter = new DrawingSelectAdapter(activity);
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
                        AvatarOriginal = Bitmap.createScaledBitmap(img.getDrawingCache(), 128, 128, true);
                        setPhoto(AvatarOriginal, false);
                    }
                });
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sheetDialog.dismiss();
                activity.selectedType = 0;
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sheetDialog.dismiss();
                activity.selectedType = 1;
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
    }
    private void onCancelClick(){
        dialog.dismiss();
    }
    private void onOkClick(){
        dialog.dismiss();
        if(onItemClickListener == null) return;
        mContact.setFirstName(etFirstName.getText().toString());
        mContact.setLastName(etLastName.getText().toString());
        mContact.setPatronymic(etPatronymic.getText().toString());
        onItemClickListener.onAddClick(mContact);
    }
}