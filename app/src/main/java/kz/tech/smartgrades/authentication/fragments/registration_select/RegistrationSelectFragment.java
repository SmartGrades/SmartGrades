package kz.tech.smartgrades.authentication.fragments.registration_select;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.L;
import kz.tech.smartgrades.authentication.AuthenticationActivity;

public class RegistrationSelectFragment extends Fragment {
    private AuthenticationActivity activity;
    private RegistrationSelectView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity = (AuthenticationActivity) getActivity();
        view = new RegistrationSelectView(getActivity(), activity.language.getLanguage());
        view.setOnItemClickListener(new RegistrationSelectView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
                activity.fragments.onReplaceFragment("SignInFragment", L.layout_authentication);
            }

            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 1:
                        activity.fragments.onReplaceFragment("RegistrationParentFragment", L.layout_authentication);
                        break;
                    case 2:
                        activity.fragments.onReplaceFragment("RegistrationMentorFragment", L.layout_authentication);
                        break;
                }
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
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }
    }
}
