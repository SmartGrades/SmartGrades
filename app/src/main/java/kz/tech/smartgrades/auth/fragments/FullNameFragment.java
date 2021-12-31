package kz.tech.smartgrades.auth.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;

import static kz.tech.smartgrades.F.isNameValid;

public class FullNameFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private ImageView ivNext;
    private TextView tvName, tvSurname, tvFullNameTitle;
    private EditText etName, etSurname;
    private boolean isNameEmpty = false;
    private boolean isSurnameEmpty = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_full_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvFullNameTitle = view.findViewById(R.id.tvFullNameTitle);
        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        etName.requestFocus();
    }

    private void changeText() {
        tvName.setText(activity.onTranslateString(R.string.name));
        tvSurname.setText(activity.onTranslateString(R.string.surname));
        tvFullNameTitle.setText(activity.onTranslateString(R.string.what_is_your_name));
        etName.setHint(activity.onTranslateString(R.string.enter_name));
        etSurname.setHint(activity.onTranslateString(R.string.enter_surname));

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) isNameEmpty = false;
                else if (arg0.length() > 0) {
                    if (Character.isLowerCase(arg0.toString().charAt(0))) etName.setText(arg0.toString().toUpperCase());
                    etName.setSelection(etName.getText().length());
                    isNameEmpty = isNameValid(arg0.toString());
                }
                onImageTernar(isNameEmpty && isSurnameEmpty);
            }
        });
        etSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) isSurnameEmpty = false;
                else if (arg0.length() > 0) {
                    if (Character.isLowerCase(arg0.toString().charAt(0))) etSurname.setText(arg0.toString().toUpperCase());
                    etSurname.setSelection(etSurname.getText().length());
                    isSurnameEmpty = isNameValid(arg0.toString());
                }
                onImageTernar(isNameEmpty && isSurnameEmpty);
            }
        });
    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }


    private void onBack() {
        activity.onBackPressed();
    }

    private void onNext() {
        if (isNameEmpty && isSurnameEmpty) {
            activity.alert.hideKeyboard(activity);
            //String type = activity.presenter.getType();
            /*if (type.equals(S.SCHOOL) || type.equals(S.SPONSOR)) {
                Drawable img = getResources().getDrawable(R.drawable.avatar_school);
                if (type.equals(S.SPONSOR)) img = getResources().getDrawable(R.drawable.avatar_sponsor);
                Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(outputStream.toByteArray());//  Set image byte array
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) { }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                activity.CURRENT_PAGE++;
                                activity.onNextFragment();
                                activity.onFullNameString(etName.getText().toString(), etSurname.getText().toString());
                                activity.onAvatarSelectString(uri.toString());//  Get download URI
                            }
                        });
                    }
                });
            }
            else{*/
            activity.onNextFragment();
//            activity.onFullNameString(etName.getText().toString(), etSurname.getText().toString());
            //}
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                onNext();
                break;
        }
    }
} //
