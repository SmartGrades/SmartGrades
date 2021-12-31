package kz.tech.smartgrades.family_room.fragments.app_lock_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.family_room.fragments.app_lock_list.adapters.AppLockListAdapter;
import kz.tech.smartgrades.MainActivity;

public class AppLockListFragment extends Fragment {
    private MainActivity main;
    private AppLockListView view;
    private AppLockListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new AppLockListView(getActivity(), main.language.getLanguage());
        adapter = new AppLockListAdapter(getActivity());
        view.getRecyclerView().setAdapter(adapter);
        view.setOnItemClickListener(new AppLockListView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
                main.presenter.onSelectFragment("FamilyMemberListFragment");
            }
            @Override
            public void onCheckBoxClick(String select) {
                switch (select) {
                    case "standard":
                        adapter.setData("standardList");
                        main.prefs.onSaveAppLockList("standardList");
                        view.setData("standardList");
                        break;
                    case "customizable":
                        adapter.setData("customizableList");
                        main.prefs.onSaveAppLockList("customizableList");
                        view.setData("customizableList");
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new AppLockListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onRemoveAllViewsClick() {
                view.getRecyclerView().removeAllViewsInLayout();
            }
        });
        adapter.setData(main.prefs.onLoadAppLockList());
        view.setData(main.prefs.onLoadAppLockList());
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
