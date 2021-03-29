package kz.tech.smartgrades.authentication.fragments.forgot_password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.MainActivity;

public class ForgotPasswordFragment extends Fragment {

    private MainActivity main;
    private ForgotPasswordView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new ForgotPasswordView(getActivity(), main.language.getLanguage());
        view.setOnItemClickListener(new ForgotPasswordView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
                main.presenter.onSelectFragment("SignInFragment");
            }

            @Override
            public void onMessageClick(String msg) {
                main.onToastMsg(msg);
            }
        });
    }
    @Override
    public void onPause() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onPause();
    }
    @Override
    public void onResume() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (main != null) {
            main = null;
        }
        if (view != null) {
            view = null;
        }
    }
}
