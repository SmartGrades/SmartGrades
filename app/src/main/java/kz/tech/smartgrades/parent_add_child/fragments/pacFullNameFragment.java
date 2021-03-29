package kz.tech.smartgrades.parent_add_child.fragments;

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
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

public class pacFullNameFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivNext;
    private TextView tvName, tvSurname, tvFullNameTitle;
    private EditText etName, etSurname;
    private boolean isNameEmpty = false;
    private boolean isSurnameEmpty = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
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
    }

    private void changeText() {
        tvName.setText(activity.onTranslateString(R.string.name));
        tvSurname.setText(activity.onTranslateString(R.string.surname));
        tvFullNameTitle.setText("Как зовут ребенка?");
        etName.setHint("Введите имя ребенка");
        etSurname.setHint("Введите фамилию ребенка");

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
                else if (arg0.length() > 0) isNameEmpty = true;
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
                else if (arg0.length() > 0) isSurnameEmpty = true;
                onImageTernar(isNameEmpty && isSurnameEmpty);
            }
        });
    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }

    private void onNext() {
        if (isNameEmpty && isSurnameEmpty) {
            activity.alert.hideSoftKeyboard(activity);
            activity.onNextFragment();
            activity.setChildFullName(etName.getText().toString(), etSurname.getText().toString());
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
}
