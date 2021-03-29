package kz.tech.smartgrades.parents_module.about_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.MainActivity;

public class AboutAppFragment extends Fragment {
    private MainActivity main;
    private AboutAppView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new AboutAppView(getActivity(), main.language.getLanguage());
        view.setOnItemClickListener(new AboutAppView.OnItemClickListener() {
            @Override
            public void onFacebookClick() {
                main.onToastMsg("Beta test");
            }
            @Override
            public void onInstagramClick() {
                main.onToastMsg("Beta test");
            }
            @Override
            public void onTelegramClick() {
                main.onToastMsg("Beta test");
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
