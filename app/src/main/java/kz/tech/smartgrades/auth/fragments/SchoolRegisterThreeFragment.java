package kz.tech.smartgrades.auth.fragments;

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
import android.widget.EditText;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.models.ModelRepresentative;
import kz.tech.smartgrades.root.firebase.FireBaseImage;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades.GET.isEmailOrSite;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SchoolRegisterThreeFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private ImageView ivStateRegistration1, ivStateRegistration2, ivNext;
    private EditText etLastName, etFirstName, etPatronymic, etPhone1, etEmail1;

    private TextView tvSkip, tvHold;

    private boolean[] NextEnable = new boolean[10];
    private boolean resultNextEnable;

    private Bitmap[] StateRegistrationBitmap = new Bitmap[2];
    private String[] StateRegistrationUri = new String[2];
    private File mPhotoFile;
    private int CURRENT_INDEX;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private boolean isPermissionsGranted = false;

    private ProgressBar progressbar1;
    private ProgressBar progressbar2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgmt_register_school_page3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        etLastName = view.findViewById(R.id.etLastName);
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) NextEnable[0] = true;
                else NextEnable[0] = false;
                isNextEnable();
            }
        });
        etFirstName = view.findViewById(R.id.etFirstName);
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) NextEnable[1] = true;
                else NextEnable[1] = false;
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
                /*if (editable.length() > 0) NextEnable[2] = true;
                else NextEnable[2] = false;
                onNextEnable();*/
            }
        });
        etPhone1 = view.findViewById(R.id.etPhone1);
        etPhone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 10) NextEnable[2] = true;
                else NextEnable[2] = false;
                isNextEnable();
            }
        });
        etEmail1 = view.findViewById(R.id.etEmail1);
        etEmail1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && isEmailOrSite(editable.toString(), false)) {
                    NextEnable[3] = true;
                }
                else {
                    NextEnable[3] = false;
                }
                isNextEnable();
            }
        });

        ivStateRegistration1 = view.findViewById(R.id.ivStateRegistration1);
        ivStateRegistration1.setOnClickListener(this);
        ivStateRegistration2 = view.findViewById(R.id.ivStateRegistration2);
        ivStateRegistration2.setOnClickListener(this);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        tvHold = view.findViewById(R.id.tvHold);
        tvSkip = view.findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(this);

        progressbar1 = view.findViewById(R.id.progressbar1);
        progressbar2 = view.findViewById(R.id.progressbar2);
    }

    private void isNextEnable() {
        if (NextEnable[4] && NextEnable[5]) {
            tvHold.setVisibility(View.GONE);
        }
        resultNextEnable = NextEnable[0] && NextEnable[1] && (NextEnable[2] || NextEnable[3]) && NextEnable[4] &&
                NextEnable[5];
        ivNext.setImageResource(resultNextEnable ? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }

    private void onNext() {
        if (!resultNextEnable) return;
        ModelRepresentative m = new ModelRepresentative();
        m.setLastName(etLastName.getText().toString());
        m.setFirstName(etFirstName.getText().toString());
        m.setPatronymic(etPatronymic.getText().toString());
        if (NextEnable[2]) m.setPhone(etPhone1.getText().toString());
        m.setEmail(etEmail1.getText().toString());
        m.setStateRegistration(StateRegistrationUri[0] + "|" + StateRegistrationUri[1]);
//        activity.getSchoolData().setModelRepresentative(m);
        activity.onNextFragment();
    }

    private void onSkip() {
        activity.onNextFragment();
    }

    private void onLoadImage(int Index) {
        CURRENT_INDEX = Index;

        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.bsd_load_image_select_type, null, false);
        ImageView ivBack = viewDialog.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(viewDialog);
        dialog.show();

        LinearLayout llCamera = viewDialog.findViewById(R.id.llCamera);
        LinearLayout llGallery = viewDialog.findViewById(R.id.llGallery);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
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
                if (CURRENT_INDEX==0){
                    progressbar1.setVisibility(View.VISIBLE);
                    tvHold.setVisibility(View.VISIBLE);
                    ivStateRegistration1.setAlpha(0.5f);
                    Glide.with(this).load(imageUri).apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.img_add_child_active)).into(ivStateRegistration1);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    StateRegistrationBitmap[0] = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
                    StateRegistrationBitmap[0].compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    UploadTask uploadTask = new FireBaseImage().uploadImage("StateRegistrations").putBytes(outputStream.toByteArray());
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            activity.alert.onToast(getString(R.string.try_again));
                            progressbar1.setVisibility(View.GONE);
                            ivStateRegistration1.setAlpha(1f);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StateRegistrationUri[0] = uri.toString();
                                    if (!stringIsNullOrEmpty(StateRegistrationUri[0])) NextEnable[4] = true;
                                    isNextEnable();
                                    progressbar1.setVisibility(View.GONE);
                                    ivStateRegistration1.setAlpha(1f);
                                }
                            });
                        }
                    });
                }
                else if (CURRENT_INDEX==1){
                    progressbar2.setVisibility(View.VISIBLE);
                    tvHold.setVisibility(View.VISIBLE);
                    ivStateRegistration2.setAlpha(0.5f);
                    Glide.with(this).load(imageUri).apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.img_add_child_active)).into(ivStateRegistration2);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    StateRegistrationBitmap[1] = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
                    StateRegistrationBitmap[1].compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    UploadTask uploadTask = new FireBaseImage().uploadImage("StateRegistrations").putBytes(outputStream.toByteArray());
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            activity.alert.onToast(getString(R.string.try_again));
                            progressbar2.setVisibility(View.GONE);
                            ivStateRegistration2.setAlpha(1f);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StateRegistrationUri[1] = uri.toString();
                                    if (!stringIsNullOrEmpty(StateRegistrationUri[1])) NextEnable[5] = true;
                                    isNextEnable();
                                    progressbar2.setVisibility(View.GONE);
                                    ivStateRegistration2.setAlpha(1f);
                                }
                            });
                        }
                    });
                }
            }
            catch (Exception e){ }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                onNext();
                break;
                case R.id.tvSkip:
                onSkip();
                break;

            case R.id.ivStateRegistration1:
                onLoadImage(0);
                break;
            case R.id.ivStateRegistration2:
                onLoadImage(1);
                break;

        }
    }
}
//