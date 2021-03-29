package kz.tech.smartgrades.ab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.mentor_module.coins.list_view.ListItemCoinsChild;
import kz.tech.smartgrades.MainActivity;

public class TestFragment extends Fragment {
    private MainActivity main;
    private TestView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return new ListItemCoinsChild(getActivity());
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new TestView(getActivity());

        String coins = "570";

        view.setData(coins);
    }
}
