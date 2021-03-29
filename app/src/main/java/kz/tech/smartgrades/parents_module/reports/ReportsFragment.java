package kz.tech.smartgrades.parents_module.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.MainActivity;

public class ReportsFragment extends Fragment {
    private MainActivity main;
    private ReportsView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new ReportsView(getActivity(), main.language.getLanguage());

        if (main.presenter.model.getChildList() != null) {
            view.onSelectChild(main.presenter.model.getChildList());
        }

    }
}
