package kz.tech.smartgrades.parents_module.settings.personal_data;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades._Firebase;

public class PersonalDataFragment extends Fragment {
    private MainActivity main;
    private PersonalDataView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new PersonalDataView(getActivity(), main.language.getLanguage());
        view.setOnItemClickListener(new PersonalDataView.OnItemClickListener() {


            @Override
            public void onSave(String strAvatar, String strName, String strStatus) {
                DatabaseReference dbrFamilyRoom = new _Firebase().getRefFamilyRoom();
               /* dbrFamilyRoom.child(main.prefs.onLoadLoginID())
                        .child(main.presenter.model.idLogin)
                        .setValue(new ModelFamilyGroup(strAvatar, strName,
                                main.presenter.model.pinAccount, strStatus, main.presenter.model.idAccount));*/

                String id = main.prefs.getLoginData("id");

                Query query = dbrFamilyRoom.child(id).child(main.prefs.getFamilyData("id"));
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.getRef().child("avatar").setValue(strAvatar);
                            dataSnapshot.getRef().child("name").setValue(strName);
                            dataSnapshot.getRef().child("status").setValue(strStatus);

                            main.hideSoftKeyboard();
                            main.presenter.onSelectFragment("SettingsParentFragment");
                            main.prefs.setFamilyData("avatar", strAvatar);
                            main.prefs.setFamilyData("name", strName);
                            main.prefs.setFamilyData("status", strStatus);


                            main.onToastMsg(main.language.getLanguage().getString(R.string.personal_data_changed));
                        } else {
                            android.util.Log.e("Tag", " Msg");
                        }
                        android.util.Log.e("Tag", " Msg");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                };
                query.addListenerForSingleValueEvent(valueEventListener);



            }
        });
        view.initVariables(main.prefs.getFamilyData("avatar"),
                main.prefs.getFamilyData("name"),
                main.prefs.getFamilyData("status"));
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
