package kz.tech.smartgrades.parents_module.family_member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;

public class FamilyMemberFragment extends Fragment {
    private MainActivity main;
    private FamilyMemberView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new FamilyMemberView(getActivity(), main.language.getLanguage());
        if (main.presenter.model.getIdSelectFamilyMember() != null) {
            ModelFamilyRoom model = main.presenter.model.getSelectModelFamilyMember(main.presenter.model.getIdSelectFamilyMember());
            /*String avatar = model.getAvatar();
            String name = model.getName();
            String status = model.getLogin();
            String pin = model.getPin();
            view.setData(avatar, name, status, pin);*/
        }
    }
}
