package kz.tech.smartgrades.parents_module.settings.locality;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.MainActivity;

public class LocalityFragment extends Fragment {
    private MainActivity main;
    private LocalityView view;
    private LocalityAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new LocalityView(getActivity(), main.language.getLanguage());
        adapter = new LocalityAdapter(getActivity());
        view.getRecyclerView().setAdapter(adapter);

        adapter.setData(main.language.getLanguage());

        adapter.setOnItemClickListener(new LocalityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String language) {
                main.language.setLocalLanguage(language);
                main.view.changeLocaleText(main.language.getLanguage());
                main.presenter.onNavChangeLanguage(main.language.getLanguage());
                adapter.changeLanguage(main.language.getLanguage());
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
