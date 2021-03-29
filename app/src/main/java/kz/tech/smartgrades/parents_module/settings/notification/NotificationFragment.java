package kz.tech.smartgrades.parents_module.settings.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.MainActivity;

public class NotificationFragment extends Fragment {
    private MainActivity main;
    private NotificationView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new NotificationView(getActivity(), main.language.getLanguage());




        view.setOnItemClickListener(new NotificationView.OnItemClickListener() {

            @Override
            public void onSwitchClick(int position, boolean select) {

            }
        });

    }
}
