package kz.tech.smartgrades.mentor_module.cabinet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.mentor_module.MentorActivity;

public class CabinetFragment extends Fragment {
    private MentorActivity activity;
    private CabinetView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity= (MentorActivity) getActivity();
        view = new CabinetView(getActivity(), activity.language.getLanguage());
    }
}
