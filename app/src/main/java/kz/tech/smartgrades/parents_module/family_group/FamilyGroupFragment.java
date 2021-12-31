package kz.tech.smartgrades.parents_module.family_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.fragments.registration_mentor.models.ModelMentor;
import kz.tech.smartgrades.parents_module.family_group.adapters.FamilyMembersAdapter;
import kz.tech.smartgrades.parents_module.family_group.dialogs_page.MentorListAdapter;
import kz.tech.smartgrades.parents_module.family_group.adapters.MentorsAdapter;
import kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form.InteractionFormDialog;
import kz.tech.smartgrades.parents_module.family_group.dialogs_page.MentorListDialog;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelChild;

public class FamilyGroupFragment extends Fragment {
    private MainActivity main;
    private FamilyGroupView view;
    private MentorsAdapter mentorsAdapter;
    private FamilyMembersAdapter familyMembersAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new FamilyGroupView(getActivity(), main.language.getLanguage());

        familyMembersAdapter = new FamilyMembersAdapter(getActivity());
        mentorsAdapter = new MentorsAdapter(getActivity());



        view.getFamilyMembers().setAdapter(familyMembersAdapter);
        view.getMentors().setAdapter(mentorsAdapter);



        view.setOnItemClickListener(new FamilyGroupView.OnItemClickListener() {
            @Override
            public void onAddFamilyMembersClick() {
                main.presenter.onSelectFragment("AddUserFragment");
                main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.add_user));
            }
            @Override
            public void onAddMentorsClick() {
                onLoadMentorList();
            }
        });
        if (main.presenter.model.getFamilyList().size() > 0) {
            familyMembersAdapter.setData(main.presenter.model.getFamilyList(), main.language.getLanguage());
        }
    }


    private void onLoadMentorList() {
        String avatar = main.prefs.getFamilyData("avatar");
        MentorListDialog dialog = new MentorListDialog(
                getActivity(), R.style.CustomDialog2, main.language.getLanguage(), avatar);
        dialog.show();
        dialog.getMentorListAdapter().setOnItemClickListener(new MentorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ModelMentor m) {
                List<ModelChild> list = main.presenter.model.getChildList();
                InteractionFormDialog dialog1 = new InteractionFormDialog(
                        getActivity(), R.style.CustomDialog2, main.language.getLanguage(),
                        avatar, m, list, main);
                dialog1.show();
            }
        });


        DatabaseReference dbrMentorList = FirebaseDatabase.getInstance().getReference("Mentors");
        dbrMentorList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<ModelMentor> list = new ArrayList<>();
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String key = ((DataSnapshot)i.next()).getKey();
                        ModelMentor m = dataSnapshot.child(key).getValue(ModelMentor.class);
                        list.add(m);

                    }
                    dialog.updateData(list);
                    android.util.Log.e("Tag", " Msg");
                } else {
                    android.util.Log.e("Tag", " Msg");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                android.util.Log.e("Tag", " Msg");

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
