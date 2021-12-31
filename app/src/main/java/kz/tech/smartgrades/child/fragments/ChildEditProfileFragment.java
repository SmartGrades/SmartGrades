package kz.tech.smartgrades.child.fragments;

import android.app.Dialog;
import android.content.Context;
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
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.adapters.DrawingSelectAdapter;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelSMSCode;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.adapters.ParentEditChildSchoolListAdapter;
import kz.tech.smartgrades.parent.model.ModelUserProfile;
import kz.tech.smartgrades.root.dialogs.DialogSelectCountry;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelCountry;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static kz.tech.smartgrades.F.isNameValid;
import static kz.tech.smartgrades.F.isUsernameValid;
import static kz.tech.smartgrades.GET.isEmailOrSite;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_DeleteUserData;
import static kz.tech.smartgrades._Web.func_GetUserProfile;
import static kz.tech.smartgrades._Web.func_IsFreeEmail;
import static kz.tech.smartgrades._Web.func_IsFreeLogin;
import static kz.tech.smartgrades._Web.func_SendSMSCode;
import static kz.tech.smartgrades._Web.func_UpdateUserData;


public class ChildEditProfileFragment extends Fragment implements View.OnClickListener {

    private ChildActivity activity;
    private ModelUserProfile mChild;
    private String CHILD_ID;
    private ModelUser m4Avatar = new ModelUser();

    private CardView cvBack;
    private CardView cvEditAvatar;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvLoginLabel;
    private TextView tvLogin;
    private TextView tvPhoneLabel;
    private TextView tvPhone;
    private TextView tvEmailLabel;
    private TextView tvEmail;
    private TextView tvBioLabel;
    private TextView tvBio;
    private TextView tvLocationLabel;
    private TextView tvLocation;
    private RecyclerView rvSchoolList;
    private ImageView ivAvatar;
    private TextView tvSchool;
    private Dialog dialog;
    private View dialogView;

    private TextView tvLabel;
    private TextView tvInputLabel1;
    private TextView tvInputLabel2;
    private EditText etInput1;
    private EditText etInput2;
    private View view1;
    private View view2;
    private View view3;
    private CardView cvBackDialog;
    private CardView cvOk;
    private TextView tvDes;

    private ConstraintLayout container;

    private boolean[] isOkName = new boolean[2];
    private boolean isLoginEmpty = false;
    private boolean isPhoneNotEmpty = false;
    private boolean isMailNotEmpty = false;
    private boolean isBioLess50 = true;

    private CardView cvAvatar;
    private CircleImageView civAvatar;

    private Bitmap SelectAvatar;
    private Bitmap Avatar, AvatarOriginal;
    private File mPhotoFile;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    private ProgressBar pgAvatarOriginalUpload, pgAvatarUpload, pgLoading;
    private int SELECT_TYPE = 0;
    private boolean IsSelectPhoto = false;
    private boolean[] isLoadSuccess = new boolean[2];

    private ParentEditChildSchoolListAdapter parentEditChildSchoolListAdapter;

    public ChildEditProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (ChildActivity) getActivity();
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_edit_child_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadModel();
    }

    private void setTargetInfo() {
        ivAvatar.setImageResource(0);
        tvName.setTextColor(activity.getResources().getColor(R.color.white));
        tvStatus.setTextColor(activity.getResources().getColor(R.color.white));

        if (!stringIsNullOrEmpty(mChild.getFirstName()) && !stringIsNullOrEmpty(mChild.getLastName())) {
            tvName.setText(mChild.getFirstName() + " " + mChild.getLastName());
        }

        if (!stringIsNullOrEmpty(mChild.getLogin())) {
            tvLogin.setText(mChild.getLogin());
        }

        if (!stringIsNullOrEmpty(mChild.getPhone())) {
            tvPhone.setText(mChild.getPhone());
        }

        if (!stringIsNullOrEmpty(mChild.getMail())) {
            tvEmail.setText(mChild.getMail());
        } else tvEmail.setText(R.string.Email_not_specified);

        if (!stringIsNullOrEmpty(mChild.getAddress())) {
            tvLocation.setText(mChild.getAddress());
        }

        if (!stringIsNullOrEmpty(mChild.getAboutMe())) {
            tvBio.setText(mChild.getAboutMe());
        } else tvBio.setText(activity.getResources().getString(R.string.no_bio));

        String avatar = mChild.getAvatar();
        String avatarOriginal = mChild.getAvatarOriginal();
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

        if (!listIsNullOrEmpty(mChild.getSchools())) {
            tvSchool.setVisibility(View.VISIBLE);
            setChildSchools();
        }
    }

    private void setChildSchools() {
        rvSchoolList.setVisibility(View.VISIBLE);
        rvSchoolList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentEditChildSchoolListAdapter = new ParentEditChildSchoolListAdapter(activity);
        rvSchoolList.setAdapter(parentEditChildSchoolListAdapter);
        if (listIsNullOrEmpty(mChild.getSchools()))
            parentEditChildSchoolListAdapter.clear();
        else parentEditChildSchoolListAdapter.updateData(mChild.getSchools());
    }

    private void initViews(View view) {
        container = view.findViewById(R.id.container);
        pgLoading = view.findViewById(R.id.pgLoading);
        cvBack = view.findViewById(R.id.cvBack);
        pgAvatarUpload = view.findViewById(R.id.pgAvatarUpload);
        pgAvatarOriginalUpload = view.findViewById(R.id.pgAvatarOriginalUpload);
        cvBack.setOnClickListener(this);
        cvEditAvatar = view.findViewById(R.id.cvEditAvatar);
        cvEditAvatar.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvName.setOnClickListener(this);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvLoginLabel = view.findViewById(R.id.tvLoginLabel);
        tvLogin.setOnClickListener(this);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvPhoneLabel = view.findViewById(R.id.tvPhoneLabel);
        tvPhone.setOnClickListener(this);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvEmailLabel = view.findViewById(R.id.tvEmailLabel);
        tvEmail.setOnClickListener(this);
        tvBio = view.findViewById(R.id.tvBio);
        tvBioLabel = view.findViewById(R.id.tvBioLabel);
        tvBio.setOnClickListener(this);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvLocationLabel = view.findViewById(R.id.tvLocationLabel);
        tvLocation.setOnClickListener(this);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvSchool = view.findViewById(R.id.tvSchool);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);
        cvAvatar = view.findViewById(R.id.cvAvatar);
        civAvatar = view.findViewById(R.id.civAvatar);
    }

    public void loadModel() {
        container.setVisibility(View.GONE);
        pgLoading.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.UserId, CHILD_ID);
        String SOAP = SoapRequest(func_GetUserProfile, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelUserProfile modelChildProfile = new Gson().fromJson(result, ModelUserProfile.class);
                            if (modelChildProfile != null) {
                                mChild = modelChildProfile;
                                container.setVisibility(View.VISIBLE);
                                pgLoading.setVisibility(View.GONE);
                                setTargetInfo();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvBack:
                onBack();
                break;
            case R.id.cvEditAvatar:
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

    private void onEditLocation() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        DialogSelectCountry dialogSelectCountry = new DialogSelectCountry(activity);
        dialogSelectCountry.setOnItemClickListener(new DialogSelectCountry.OnItemClickListener(){
            @Override
            public void onClick(ModelCountry Country){
                activity.alert.hideKeyboard(activity);
                ModelUser m = new ModelUser();
                m.setAddress(Country.getName());
                onUpdate(m);
            }
        });
    }
    private void onEditBio() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        dialog = new Dialog(activity, R.style.CustomDialog2);
        dialogView = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        dialog.setContentView(dialogView);

        tvLabel = dialogView.findViewById(R.id.tvLabel);
        tvInputLabel1 = dialogView.findViewById(R.id.tvInputLabel1);
        tvInputLabel2 = dialogView.findViewById(R.id.tvInputLabel2);
        etInput1 = dialogView.findViewById(R.id.etInput1);
        etInput2 = dialogView.findViewById(R.id.etInput2);
        view1 = dialogView.findViewById(R.id.view1);
        view2 = dialogView.findViewById(R.id.view2);
        cvBackDialog = dialogView.findViewById(R.id.cvBackDialog);
        cvOk = dialogView.findViewById(R.id.cvOk);
        tvDes = dialogView.findViewById(R.id.tvDes);
        tvDes.setText(R.string.bio_des);

        tvLabel.setText(R.string.About_me);
        etInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 50) isBioLess50 = true;
                else isBioLess50 = false;
            }
        });
        etInput1.setText(mChild.getAboutMe());
        etInput1.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        cvBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activity.alert.hideKeyboard(activity);
            }
        });
        cvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBioLess50) {
                    dialog.dismiss();
                    activity.alert.hideKeyboard(activity);
                    ModelUser m = new ModelUser();
                    m.setAboutMe(etInput1.getText().toString());
                    onUpdate(m);
                }
            }
        });

        dialog.show();
    }
    private void onEditEmail() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        dialog = new Dialog(activity, R.style.CustomDialog2);
        dialogView = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        dialog.setContentView(dialogView);

        tvLabel = dialogView.findViewById(R.id.tvLabel);
        tvInputLabel1 = dialogView.findViewById(R.id.tvInputLabel1);
        tvInputLabel2 = dialogView.findViewById(R.id.tvInputLabel2);
        etInput1 = dialogView.findViewById(R.id.etInput1);
        etInput2 = dialogView.findViewById(R.id.etInput2);
        view1 = dialogView.findViewById(R.id.view1);
        view2 = dialogView.findViewById(R.id.view2);
        cvBackDialog = dialogView.findViewById(R.id.cvBackDialog);
        cvOk = dialogView.findViewById(R.id.cvOk);
        tvDes = dialogView.findViewById(R.id.tvDes);
        tvDes.setText(R.string.mail_des);

        tvLabel.setText(R.string.email);
        etInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) isMailNotEmpty = true;
                else isMailNotEmpty = editable.length() > 0 && isEmailOrSite(editable.toString(), true);
            }
        });
        etInput1.setText(mChild.getMail());
        etInput1.setMaxLines(1);
        etInput1.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        cvBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activity.alert.hideKeyboard(activity);
            }
        });
        cvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMailNotEmpty) return;
                String Email = etInput1.getText().toString();
                JsonObject json = new JsonObject();
                json.addProperty("Email", Email);
                String SOAP = SoapRequest(func_IsFreeEmail, json.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        String xml = e.getMessage();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (answer.isSuccess()) {
                                        dialog.dismiss();
                                        activity.alert.hideKeyboard(activity);
                                        ModelUser m = new ModelUser();
                                        m.setMail(etInput1.getText().toString());
                                        onUpdate(m);
                                    } else activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void onEditPhone() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        dialog = new Dialog(activity, R.style.CustomDialog2);
        dialogView = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        dialog.setContentView(dialogView);

        tvLabel = dialogView.findViewById(R.id.tvLabel);
        tvInputLabel1 = dialogView.findViewById(R.id.tvInputLabel1);
        tvInputLabel2 = dialogView.findViewById(R.id.tvInputLabel2);
        etInput1 = dialogView.findViewById(R.id.etInput1);
        etInput2 = dialogView.findViewById(R.id.etInput2);
        view1 = dialogView.findViewById(R.id.view1);
        view2 = dialogView.findViewById(R.id.view2);
        view3 = dialogView.findViewById(R.id.view3);
        cvBackDialog = dialogView.findViewById(R.id.cvBackDialog);
        cvOk = dialogView.findViewById(R.id.cvOk);
        tvDes = dialogView.findViewById(R.id.tvDes);
        tvDes.setText(R.string.phone_des);

        etInput1.setText(mChild.getPhone());
        etInput1.setInputType(InputType.TYPE_CLASS_PHONE);

        tvLabel.setText(R.string.Phone);

        if (etInput1.getText().length() > 0 && etInput1.getText().charAt(0) == '+') {
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(12);
            etInput1.setFilters(FilterArray);
        } else {
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(11);
            etInput1.setFilters(FilterArray);
        }
        etInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && editable.toString().charAt(0) == '+') {
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(12);
                    etInput1.setFilters(FilterArray);
                } else {
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(11);
                    etInput1.setFilters(FilterArray);
                }
                if (editable.length() == 11) {
                    isPhoneNotEmpty = true;
                } else isPhoneNotEmpty = editable.length() == 12 && editable.toString().charAt(0) == '+';
            }
        });
        etInput1.setMaxLines(1);
        etInput1.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        cvBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activity.alert.hideKeyboard(activity);
            }
        });
        cvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPhoneNotEmpty) return;
                if (etInput1.getText().toString().equals(mChild.getPhone())) {
                    dialog.dismiss();
                    activity.alert.hideKeyboard(activity);
                }
                JsonObject json = new JsonObject();
                json.addProperty(F.Phone, etInput1.getText().toString());
                String SOAP = SoapRequest(func_SendSMSCode, json.toString());
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
                                    if (answer.isSuccess()) {
                                        tvDes.setText(R.string.Enter_code_from_SMS);
                                        tvDes.setGravity(Gravity.CENTER);
                                        ModelSMSCode code = new Gson().fromJson(result, ModelSMSCode.class);
                                        etInput2.setVisibility(View.VISIBLE);
                                        view3.setVisibility(View.VISIBLE);
                                        etInput2.setGravity(Gravity.CENTER);
                                        etInput2.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        etInput2.setMaxLines(1);
                                        etInput2.requestFocus();
                                        etInput2.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                if (code.getCode().equals(editable.toString())) {
                                                    dialog.dismiss();
                                                    activity.alert.hideKeyboard(activity);
                                                    ModelUser m = new ModelUser();
                                                    m.setPhone(etInput1.getText().toString());
                                                    onUpdate(m);
                                                }
                                            }
                                        });
                                    } else activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void onEditLogin() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        dialog = new Dialog(activity, R.style.CustomDialog2);
        dialogView = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        dialog.setContentView(dialogView);

        tvLabel = dialogView.findViewById(R.id.tvLabel);
        tvInputLabel1 = dialogView.findViewById(R.id.tvInputLabel1);
        tvInputLabel2 = dialogView.findViewById(R.id.tvInputLabel2);
        etInput1 = dialogView.findViewById(R.id.etInput1);
        etInput2 = dialogView.findViewById(R.id.etInput2);
        view1 = dialogView.findViewById(R.id.view1);
        view2 = dialogView.findViewById(R.id.view2);
        cvBackDialog = dialogView.findViewById(R.id.cvBackDialog);
        cvOk = dialogView.findViewById(R.id.cvOk);
        tvDes = dialogView.findViewById(R.id.tvDes);
        tvDes.setText(R.string.login_des);

        tvLabel.setText(R.string.login);
        etInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) isLoginEmpty = false;
                else isLoginEmpty = isUsernameValid(editable.toString());
                String s = editable.toString();
                if (!s.equals(s.toLowerCase())){
                    s=s.toLowerCase();
                    etInput1.setText(s);
                    etInput1.setSelection(s.length());
                }
            }
        });
        etInput1.setText(mChild.getLogin());
        etInput1.setMaxLines(1);
        etInput1.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        cvBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activity.alert.hideKeyboard(activity);
            }
        });
        cvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Login = etInput1.getText().toString();
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
                                    if (answer.isSuccess()){
                                        dialog.dismiss();
                                        activity.alert.hideKeyboard(activity);
                                        ModelUser m = new ModelUser();
                                        m.setLogin(etInput1.getText().toString());
                                        onUpdate(m);
                                    }
                                    else activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void onEditName() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        dialog = new Dialog(activity, R.style.CustomDialog2);
        dialogView = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        dialog.setContentView(dialogView);

        tvLabel = dialogView.findViewById(R.id.tvLabel);
        tvInputLabel1 = dialogView.findViewById(R.id.tvInputLabel1);
        tvInputLabel2 = dialogView.findViewById(R.id.tvInputLabel2);
        etInput1 = dialogView.findViewById(R.id.etInput1);
        etInput2 = dialogView.findViewById(R.id.etInput2);
        view1 = dialogView.findViewById(R.id.view1);
        view2 = dialogView.findViewById(R.id.view2);
        cvBackDialog = dialogView.findViewById(R.id.cvBackDialog);
        cvOk = dialogView.findViewById(R.id.cvOk);
        tvDes = dialogView.findViewById(R.id.tvDes);
        tvDes.setText(R.string.name_des);

        tvLabel.setText(R.string.Surname_and_name);
        tvInputLabel1.setVisibility(View.VISIBLE);
        tvInputLabel2.setVisibility(View.VISIBLE);
        etInput2.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        etInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) isOkName[0] = false;
                else if (editable.length() > 0) {
                    if (Character.isLowerCase(editable.toString().charAt(0))) etInput1.setText(editable.toString().toUpperCase());
                    etInput1.setSelection(etInput1.getText().length());
                    isOkName[0] = isNameValid(editable.toString());
                }
            }
        });
        etInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) isOkName[1] = false;
                else if (editable.length() > 0) {
                    if (Character.isLowerCase(editable.toString().charAt(0))) etInput1.setText(editable.toString().toUpperCase());
                    etInput1.setSelection(etInput1.getText().length());
                    isOkName[1] = isNameValid(editable.toString());
                }
            }
        });
        etInput1.setText(mChild.getLastName());
        etInput1.setMaxLines(1);
        etInput2.setMaxLines(1);
        etInput1.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        etInput2.setText(mChild.getFirstName());
        cvBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activity.alert.hideKeyboard(activity);
            }
        });
        cvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOkName[0] && isOkName[1]) {
                    dialog.dismiss();
                    activity.alert.hideKeyboard(activity);
                    ModelUser m = new ModelUser();
                    m.setLastName(etInput1.getText().toString());
                    m.setFirstName(etInput2.getText().toString());
                    onUpdate(m);
                }
            }
        });

        dialog.show();
    }
    private void onEditAvatar() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        onPhotoSetting();
    }

    private void onPhotoSetting(){
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_photo_settings, null, false);
        dialog.setContentView(viewDialog);

        LinearLayout llGallery = viewDialog.findViewById(R.id.llGallery);
        LinearLayout llDrawing = viewDialog.findViewById(R.id.llDrawing);
        LinearLayout llCamera = viewDialog.findViewById(R.id.llCamera);
        LinearLayout llDelete = viewDialog.findViewById(R.id.llDelete);

        if(!stringIsNullOrEmpty(mChild.getAvatarOriginal())) llDelete.setVisibility(View.VISIBLE);
        else{
            if(!stringIsNullOrEmpty(mChild.getAvatar()))
                llDelete.setVisibility(View.VISIBLE);
            else llDelete.setVisibility(View.GONE);
        }
        llDrawing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                int[] images_child = {R.drawable.child_1, R.drawable.child_2, R.drawable.child_3, R.drawable.child_4,
                        R.drawable.child_5, R.drawable.child_6, R.drawable.child_7, R.drawable.child_8,
                        R.drawable.child_9, R.drawable.child_10, R.drawable.child_11, R.drawable.child_12,
                        R.drawable.child_13, R.drawable.child_14, R.drawable.child_15, R.drawable.child_16,
                        R.drawable.child_17, R.drawable.child_18, R.drawable.child_19, R.drawable.child_20,
                        R.drawable.child_21, R.drawable.child_22, R.drawable.child_23, R.drawable.child_24,
                        R.drawable.child_25, R.drawable.child_26, R.drawable.child_27, R.drawable.child_28};

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
                CardView cvBack = viewDialog.findViewById(R.id.cvBack);
                cvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                rvAvatarsList.setLayoutManager(new GridLayoutManager(activity, 5));
                rvAvatarsList.setAdapter(drawingSelectAdapter);
                drawingSelectAdapter.UpdateData(images_child);
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
            jsonData.addProperty(F.UserId, CHILD_ID);
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
                                    mChild.setAvatar(null);
                                    mChild.setAvatarOriginal(null);
                                    setTargetInfo();
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
                            mChild.setAvatarOriginal(uri.toString());
                            isLoadSuccess[0] = true;
                            SaveImageToServer();
                        }
                    });
                }
            });
        }
        else mChild.setAvatarOriginal(null);

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
                        mChild.setAvatar(uri.toString());
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
                modelUser.setUserId(CHILD_ID);
                modelUser.setAvatar(mChild.getAvatar());
                modelUser.setAvatarOriginal(mChild.getAvatarOriginal());

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
                                        setTargetInfo();
                                        activity.login.updateUserData(LoginKey.AVATAR, modelUser.getAvatar());
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
                                    activity.setProfileData();
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

    private void onUpdate(ModelUser m){
        m.setUserId(CHILD_ID);
        String SOAP = SoapRequest(func_UpdateUserData, new Gson().toJson(m));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() >= 200 && response.code() <= 300) {
                    String xml = response.body().string();
                    String result = _Web.XMLToJson(xml);
                    activity.runOnUiThread(new Runnable() {
                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            activity.updatePresenter();
                            activity.setProfileData();
                            loadModel();
                        }
                    });
                }
            }
        });
    }

    private void onBack() {
        activity.onBackPressed();
    }
}