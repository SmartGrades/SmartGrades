package kz.tech.smartgrades.parents_module.cabinet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.tech.smartgrades.parents_module.ParentActivity;
import kz.tech.smartgrades.parents_module.cabinet.adapters.ChildAdapter;
import kz.tech.smartgrades.parents_module.cabinet.models.ModelChatListChild;
import kz.tech.smartgrades.root.models.ModelChild;

public class CabinetFragment extends Fragment {
    private ParentActivity activity;
    private CabinetView view;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private ChildAdapter adapter1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        onLoadData();
    }

    private void initViews(View view) {



      //  view = new CabinetView(getActivity());
      /*  view.setOnItemClickListener(new CabinetView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              //  Toast.makeText(getActivity(), "Fuck You", Toast.LENGTH_SHORT).show();
                ContractsGoodDialog dialog = new ContractsGoodDialog(getActivity(), R.style.CustomDialog);//R.style.CustomDialog
                dialog.showAlertDialog();


            }
        });*/
    }


    private void onLoadData() {
        List<ModelChild> childList = activity.presenter.model.getChildList();
        ArrayList<ModelChatListChild> arrayList = new ArrayList<>();

        for (int i = 0; i < childList.size(); i++) {
            arrayList.add(new ModelChatListChild(1, childList.get(i).getName()));
        }


        adapter1.setListData(arrayList);
    }
}
