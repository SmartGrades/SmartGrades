package kz.tech.smartgrades.parents_module.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MainActivity;

public class SettingsFragment extends Fragment {
    private MainActivity main;
    private SettingsView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new SettingsView(getActivity(), main.language.getLanguage());

        view.setOnItemClickListener(new SettingsView.OnItemClickListener() {
            @Override
            public void onNextPageClick(String fragment) {
                if (!fragment.equals("")) {
                    main.presenter.onSelectFragment(fragment);
                    switch (fragment) {
                        case "PersonalDataFragment":
                            main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.personal_data));
                            break;
                        case "NotificationFragment":
                            main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.notification));
                            break;
                        case "LocalityFragment":
                            main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.change_language));
                            break;
                        case "ChangePinCodeFragment":
                            main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.change_quick_access_code));
                            break;
                        case "ChangePasswordFragment":
                            main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.change_password));
                            break;
                    }
                }
            }
        });

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
