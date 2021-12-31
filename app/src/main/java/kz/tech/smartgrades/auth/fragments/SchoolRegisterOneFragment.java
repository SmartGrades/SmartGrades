package kz.tech.smartgrades.auth.fragments;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.dialogs.DialogSelectCountry;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.models.ModelCountry;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades.F.isUsernameValid;
import static kz.tech.smartgrades.GET.isEmailOrSite;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._System.getPermissionsToUseCamera;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_IsFreeLogin;

public class SchoolRegisterOneFragment extends Fragment implements View.OnClickListener{

    private AuthActivity activity;
    private ModelSchoolData mSchoolData;

    private ImageView ivStateRegistration1, ivStateRegistration2, ivNext;
    private EditText etSchoolName, etAddress, etLogin;
    private EditText[] etSite, etPhone, etEmail;
    private LinearLayout llCountry;
    private TextView tvCountryValue, tvHold;
    private TextView[] tvPhoneStart;
    private Button[] btnAddSite, btnAddPhone, btnAddEmail;
    private boolean[] NextEnable = new boolean[11];
    private boolean[] isPlusAddSite = new boolean[3];
    private boolean[] isPlusAddPhone = new boolean[3];
    private boolean[] isPlusAddEmail = new boolean[3];
    private boolean resultNextEnable;

    private Bitmap[] StateRegistrationBitmap = new Bitmap[2];
    private String[] StateRegistrationUri = new String[2];
    private File mPhotoFile;
    private int CURRENT_INDEX;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;

    private ProgressBar progressbar1;
    private ProgressBar progressbar2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_register_school_page1, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        etSchoolName = view.findViewById(R.id.etSchoolName);
        etSchoolName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0) NextEnable[0] = true;
                else NextEnable[0] = false;
                isNextEnable();
            }
        });
        llCountry = view.findViewById(R.id.llCountry);
        llCountry.setOnClickListener(this);
        tvCountryValue = view.findViewById(R.id.tvCountryValue);
        tvHold = view.findViewById(R.id.tvHold);
        tvPhoneStart = new TextView[3];
        tvPhoneStart[0] = view.findViewById(R.id.tvPhoneStart1);
        tvPhoneStart[1] = view.findViewById(R.id.tvPhoneStart2);
        tvPhoneStart[2] = view.findViewById(R.id.tvPhoneStart3);
        etAddress = view.findViewById(R.id.etAddress);
        etLogin = view.findViewById(R.id.etLogin);
        etAddress.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0) NextEnable[1] = true;
                else NextEnable[1] = false;
                isNextEnable();
            }
        });
        etLogin.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if (editable.length() == 0) NextEnable[10] = false;
                else NextEnable[10] = isUsernameValid(editable.toString());
                isNextEnable();
                String s=editable.toString();
                if (!s.equals(s.toLowerCase())){
                    s=s.toLowerCase();
                    etLogin.setText(s);
                    etLogin.setSelection(s.length()); // what to do
                }
            }
        });
        etSite = new EditText[3];
        etSite[0] = view.findViewById(R.id.etSite1);
        etSite[0].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0 && isEmailOrSite(editable.toString(), false)){
                    btnAddSite[0].setVisibility(View.VISIBLE);
                }
                else{
                    btnAddSite[0].setVisibility(View.GONE);
                }
                isNextEnable();
            }
        });
        etSite[1] = view.findViewById(R.id.etSite2);
        etSite[1].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0 && isEmailOrSite(editable.toString(), false)){
                    //NextEnable[4] = true;
                    btnAddSite[1].setVisibility(View.VISIBLE);
                }
                else{
                    //NextEnable[4] = false;
                    btnAddSite[1].setVisibility(View.GONE);
                }
                isNextEnable();
            }
        });
        etSite[2] = view.findViewById(R.id.etSite3);
        etSite[2].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0 && isEmailOrSite(editable.toString(), false)){
                    //NextEnable[4] = true;
                    //btnAddSite[2].setVisibility(View.VISIBLE);
                }
                else{
                    //NextEnable[4] = false;
                    //btnAddSite[2].setVisibility(View.GONE);
                }
                //onNextEnable();
            }
        });
        btnAddSite = new Button[3];
        btnAddSite[0] = view.findViewById(R.id.btnAddSite1);
        btnAddSite[0].setOnClickListener(this);
        btnAddSite[1] = view.findViewById(R.id.btnAddSite2);
        btnAddSite[1].setOnClickListener(this);
        btnAddSite[2] = view.findViewById(R.id.btnAddSite3);
        btnAddSite[2].setOnClickListener(this);

        etPhone = new EditText[3];
        etPhone[0] = view.findViewById(R.id.etPhone1);
        etPhone[0].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() == 10){
                    NextEnable[2] = true;
                    btnAddPhone[0].setVisibility(View.VISIBLE);
                }
                else{
                    NextEnable[2] = false;
                    btnAddPhone[0].setVisibility(View.GONE);
                }
                isNextEnable();
            }
        });
        etPhone[1] = view.findViewById(R.id.etPhone2);
        etPhone[1].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0){
                    //NextEnable[5] = true;
                    btnAddPhone[1].setVisibility(View.VISIBLE);
                }
                else{
                    //NextEnable[5] = false;
                    btnAddPhone[1].setVisibility(View.GONE);
                }
                isNextEnable();
            }
        });
        etPhone[2] = view.findViewById(R.id.etPhone3);
        etPhone[2].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                /*if (editable.length() > 0) NextEnable[0] = true;
                else NextEnable[0] = false;
                onNextEnable();
                if (editable.length() > 0 && isEmailOrSite(editable.toString())) {
                    NextEnable[5] = true;
                    btnAddPhone[0].setVisibility(View.VISIBLE);
                }
                else {
                    NextEnable[5] = false;
                    btnAddPhone[0].setVisibility(View.GONE);
                }
                onNextEnable();*/
            }
        });
        btnAddPhone = new Button[3];
        btnAddPhone[0] = view.findViewById(R.id.btnAddPhone1);
        btnAddPhone[0].setOnClickListener(this);
        btnAddPhone[1] = view.findViewById(R.id.btnAddPhone2);
        btnAddPhone[1].setOnClickListener(this);
        btnAddPhone[2] = view.findViewById(R.id.btnAddPhone3);
        btnAddPhone[2].setOnClickListener(this);

        etEmail = new EditText[3];
        etEmail[0] = view.findViewById(R.id.etEmail1);
        etEmail[0].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0 && isEmailOrSite(editable.toString(), true)){
                    NextEnable[3] = true;
                    btnAddEmail[0].setVisibility(View.VISIBLE);
                }
                else{
                    NextEnable[3] = false;
                    btnAddEmail[0].setVisibility(View.GONE);
                }
                isNextEnable();
            }
        });
        etEmail[1] = view.findViewById(R.id.etEmail2);
        etEmail[1].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                if(editable.length() > 0 && isEmailOrSite(editable.toString(), true)){
                    //NextEnable[6] = true;
                    btnAddEmail[1].setVisibility(View.VISIBLE);
                }
                else{
                    //NextEnable[6] = false;
                    btnAddEmail[1].setVisibility(View.GONE);
                }
                isNextEnable();
            }
        });
        etEmail[2] = view.findViewById(R.id.etEmail3);
        etEmail[2].addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                /*if (editable.length() > 0) NextEnable[0] = true;
                else NextEnable[0] = false;
                onNextEnable();*/
            }
        });
        btnAddEmail = new Button[3];
        btnAddEmail[0] = view.findViewById(R.id.btnAddEmail1);
        btnAddEmail[0].setOnClickListener(this);
        btnAddEmail[1] = view.findViewById(R.id.btnAddEmail2);
        btnAddEmail[1].setOnClickListener(this);
        btnAddEmail[2] = view.findViewById(R.id.btnAddEmail3);
        btnAddEmail[2].setOnClickListener(this);

        ivStateRegistration1 = view.findViewById(R.id.ivStateRegistration1);
        ivStateRegistration1.setOnClickListener(this);
        ivStateRegistration2 = view.findViewById(R.id.ivStateRegistration2);
        ivStateRegistration2.setOnClickListener(this);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        progressbar1 = view.findViewById(R.id.progressbar1);
        progressbar2 = view.findViewById(R.id.progressbar2);
    }

    private void isNextEnable(){
        if (NextEnable[5] && NextEnable[6]) {
            tvHold.setVisibility(View.GONE);
        }
        resultNextEnable = NextEnable[0] && NextEnable[1] && (NextEnable[2] || NextEnable[3])
                && NextEnable[4] && NextEnable[5] && NextEnable[6] && NextEnable[10];
        ivNext.setImageResource(resultNextEnable ? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }

    private void onNext(){
        if(!resultNextEnable) return;

        activity.alert.hideKeyboard(activity);

        String Login = etLogin.getText().toString();
        JsonObject json = new JsonObject();
        json.addProperty("Login", Login);
        String SOAP = SoapRequest(func_IsFreeLogin, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!answer.isSuccess()) activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });

        mSchoolData = new ModelSchoolData();
        mSchoolData.setName(etSchoolName.getText().toString());
        mSchoolData.setLogin(etLogin.getText().toString());
        mSchoolData.setAddress(tvCountryValue.getText().toString() + ", " + etAddress.getText().toString());

        String site = etSite[0].getText().toString();
        if(!stringIsNullOrEmpty(etSite[1].getText().toString()))
            site += "|" + etSite[1].getText().toString();
        if(!stringIsNullOrEmpty(etSite[2].getText().toString()))
            site += "|" + etSite[2].getText().toString();
        mSchoolData.setSite(site);

        String phone = "";
        if(!stringIsNullOrEmpty(etPhone[0].getText().toString()) && etPhone[0].getText().toString().length() == 10)
            phone += "+7" + etPhone[0].getText().toString();
        if(!stringIsNullOrEmpty(etPhone[1].getText().toString()) && etPhone[1].getText().toString().length() == 10)
            phone += "|" + "+7" + etPhone[1].getText().toString();
        if(!stringIsNullOrEmpty(etPhone[2].getText().toString()) && etPhone[2].getText().toString().length() == 10)
            phone += "|" + "+7" + etPhone[2].getText().toString();
        mSchoolData.setPhone(phone);

        String mail = etEmail[0].getText().toString();
        if(!stringIsNullOrEmpty(etEmail[1].getText().toString()))
            mail += "|" + etEmail[1].getText().toString();
        if(!stringIsNullOrEmpty(etEmail[2].getText().toString()))
            mail += "|" + etEmail[2].getText().toString();
        mSchoolData.setMail(mail);

        mSchoolData.setStateRegistration(StateRegistrationUri[0] + "|" + StateRegistrationUri[1]);
//        activity.setSchoolData(mSchoolData);
        activity.onNextFragment();
    }

    private void onAddSite(int index){
        if(!isPlusAddSite[index]){
            isPlusAddSite[index] = true;
            etSite[index + 1].setVisibility(View.VISIBLE);
            btnAddSite[index].setBackgroundResource(R.drawable.img_delete_v2);
        }
        else{
            isPlusAddSite[index] = false;
            etSite[index + 1].setVisibility(View.GONE);
            etSite[index + 1].setText("");
            btnAddSite[index].setBackgroundResource(R.drawable.img_add);
        }
    }

    private void onAddPhone(int index){
        if(!isPlusAddPhone[index]){
            isPlusAddPhone[index] = true;
            etPhone[index + 1].setVisibility(View.VISIBLE);
            tvPhoneStart[index + 1].setVisibility(View.VISIBLE);
            btnAddPhone[index].setBackgroundResource(R.drawable.img_delete_v2);
        }
        else{
            isPlusAddPhone[index] = false;
            etPhone[index + 1].setVisibility(View.GONE);
            tvPhoneStart[index + 1].setVisibility(View.GONE);
            etPhone[index + 1].setText("");
            btnAddPhone[index].setBackgroundResource(R.drawable.img_add);
        }
    }

    private void onAddEmail(int index){
        if(!isPlusAddEmail[index]){
            isPlusAddEmail[index] = true;
            etEmail[index + 1].setVisibility(View.VISIBLE);
            btnAddEmail[index].setBackgroundResource(R.drawable.img_delete_v2);
        }
        else{
            isPlusAddEmail[index] = false;
            etEmail[index + 1].setVisibility(View.GONE);
            etEmail[index + 1].setText("");
            btnAddEmail[index].setBackgroundResource(R.drawable.img_add);
        }
    }

    private void onLoadImage(int Index){
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

        llCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_TAKE_PHOTO);
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                ToMakeAPhoto(REQUEST_LOAD_PHOTO);
            }
        });
    }

    private void ToMakeAPhoto(int Type){
        if(getPermissionsToUseCamera(activity)){
            Intent PictureIntent = null;
            if(Type == REQUEST_TAKE_PHOTO)
                PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            else if(Type == REQUEST_LOAD_PHOTO){
                PictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //PictureIntent.setType("image/*");
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
                    } catch(Exception ex){
                        System.out.print(ex.toString());

                    }
                }
                else if(Type == REQUEST_LOAD_PHOTO)
                    startActivityForResult(Intent.createChooser(PictureIntent, "Select Picture"), REQUEST_LOAD_PHOTO);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            Uri imageUri = null;
            if(requestCode == REQUEST_TAKE_PHOTO) imageUri = Uri.fromFile(mPhotoFile);
            else if(requestCode == REQUEST_LOAD_PHOTO && null != data) imageUri = data.getData();
            try{
                if(CURRENT_INDEX == 0){
                    progressbar1.setVisibility(View.VISIBLE);
                    tvHold.setVisibility(View.VISIBLE);
                    ivStateRegistration1.setAlpha(0.5f);
                    Glide.with(this).load(imageUri).apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.img_add_child_active)).into(ivStateRegistration1);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    StateRegistrationBitmap[0] = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
                    StateRegistrationBitmap[0].compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    UploadTask uploadTask = new FireBaseImage().uploadImage("StateRegistrations").putBytes(outputStream.toByteArray());
                    uploadTask.addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception exception){
                            activity.alert.onToast(getString(R.string.try_again));
                            progressbar1.setVisibility(View.GONE);
                            ivStateRegistration1.setAlpha(1f);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>(){
                                @Override
                                public void onSuccess(Uri uri){
                                    StateRegistrationUri[0] = uri.toString();
                                    if(!stringIsNullOrEmpty(StateRegistrationUri[0]))
                                        NextEnable[5] = true;
                                    isNextEnable();
                                    progressbar1.setVisibility(View.GONE);
                                    ivStateRegistration1.setAlpha(1f);
                                }
                            });
                        }
                    });
                }
                else if(CURRENT_INDEX == 1){
                    progressbar2.setVisibility(View.VISIBLE);
                    tvHold.setVisibility(View.VISIBLE);
                    ivStateRegistration2.setAlpha(0.5f);
                    Glide.with(this).load(imageUri).apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.img_add_child_active)).into(ivStateRegistration2);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    StateRegistrationBitmap[1] = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
                    StateRegistrationBitmap[1].compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    UploadTask uploadTask = new FireBaseImage().uploadImage("StateRegistrations").putBytes(outputStream.toByteArray());
                    uploadTask.addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception exception){
                            activity.alert.onToast(getString(R.string.try_again));
                            progressbar2.setVisibility(View.GONE);
                            ivStateRegistration2.setAlpha(1f);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>(){
                                @Override
                                public void onSuccess(Uri uri){
                                    StateRegistrationUri[1] = uri.toString();
                                    if(!stringIsNullOrEmpty(StateRegistrationUri[1]))
                                        NextEnable[6] = true;
                                    isNextEnable();
                                    progressbar2.setVisibility(View.GONE);
                                    ivStateRegistration2.setAlpha(1f);
                                }
                            });
                        }
                    });
                }
            } catch(Exception e){
            }
        }
    }

    private void onShowList(int type){
        DialogSelectCountry dialogSelectCountry = new DialogSelectCountry(activity);
        dialogSelectCountry.setOnItemClickListener(new DialogSelectCountry.OnItemClickListener(){
            @Override
            public void onClick(ModelCountry Country){
                tvCountryValue.setText(Country.getName());
                NextEnable[4] = true;
                isNextEnable();
            }
        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivNext:
                onNext();
                break;

            case R.id.llCountry:
                onShowList(0);
                break;

            case R.id.btnAddSite1:
                onAddSite(0);
                break;
            case R.id.btnAddSite2:
                onAddSite(1);
                break;
            case R.id.btnAddSite3:
                onAddSite(2);
                break;

            case R.id.btnAddPhone1:
                onAddPhone(0);
                break;
            case R.id.btnAddPhone2:
                onAddPhone(1);
                break;
            case R.id.btnAddPhone3:
                onAddPhone(2);
                break;

            case R.id.btnAddEmail1:
                onAddEmail(0);
                break;
            case R.id.btnAddEmail2:
                onAddEmail(1);
                break;
            case R.id.btnAddEmail3:
                onAddEmail(2);
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