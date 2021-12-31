package kz.tech.smartgrades.parents_module.settings.change_password;

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

public class ChangePasswordFragment extends Fragment {
    private MainActivity main;
    private ChangePasswordView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new ChangePasswordView(getActivity(), main.language.getLanguage());

        view.setOnItemClickListener(new ChangePasswordView.OnItemClickListener() {

            @Override
            public void onDropPasswordClick() {

            }
            @Override
            public void onMessageClick(String msg) {
                main.onToastMsg(msg);

            }
            @Override
            public void onOkClick(String currentPassword, String newPassword, String newPasswordAgain) {
                String loginName = main.prefs.getLoginData("name");
                String loginPassword =  main.prefs.getLoginData("password");
                if (loginName != null && loginPassword != null) {
                    if (!loginName.equals("") && !loginPassword.equals("")) {
                        if(!currentPassword.equals(loginPassword)) {
                            main.onToastMsg(main.language.getLanguage().getString(R.string.the_current_password_is_not_correct));
                            return;
                        }
                        if(!newPassword.equals(newPasswordAgain)) {
                            main.onToastMsg(main.language.getLanguage().getString(R.string.new_password_and_confirmation_password_do_not_match));
                            return;
                        }
                DatabaseReference dbrUsers = new _Firebase().getRefUsers();//  Users
                        Query query = dbrUsers.child(loginName);
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    dataSnapshot.getRef().child("password").setValue(newPasswordAgain);
                                  //  main.prefs.onSaveLogin(loginName, newPasswordAgain);
                                    main.hideSoftKeyboard();
                                    main.presenter.onSelectFragment("SettingsParentFragment");
                                    main.onToastMsg(main.language.getLanguage().getString(R.string.password_changed));
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
                }


            }
        });
    }
}