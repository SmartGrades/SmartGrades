package kz.tech.smartgrades.parents_module.settings.pin_code;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MainActivity;

public class ChangePinCodeFragment extends Fragment {
    private MainActivity main;
    private ChangePinCodeView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new ChangePinCodeView(getActivity(), main.language.getLanguage());

        view.setOnItemClickListener(new ChangePinCodeView.OnItemClickListener() {


            @Override
            public void onMessageClick(String msg) {
                main.onToastMsg(msg);
            }

            @Override
            public void onOkClick(String currentPIN, String newPIN, String newPINAgain) {
                if(!currentPIN.equals(main.prefs.getFamilyData("quickAccessCode"))) {
                    main.onToastMsg(main.language.getLanguage().getString(R.string.the_current_quick_access_code_is_not_correct));
                    return;
                }
                if(!newPIN.equals(newPINAgain)) {
                    main.onToastMsg(main.language.getLanguage().getString(R.string.new_quick_access_code_and_confirmation_quick_access_code_do_not_match));
                    return;
                }
                Query query = main.presenter.model.getDbrFamilyRoom()
                        .child(main.prefs.getLoginData("id"))
                        .child(main.prefs.getFamilyData("id"));
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.getRef().child("pin").setValue(newPINAgain);
                            main.prefs.setFamilyData("quickAccessCode", newPINAgain);
                            //String avatar, String name, String pin, String status, String id
                            main.prefs.onSaveFamilyAccount(main.prefs.getFamilyData("avatar"), main.prefs.getFamilyData("name"),
                                    main.prefs.getFamilyData("quickAccessCode"), main.prefs.getFamilyData("status"), main.prefs.getFamilyData("id"));
                            main.hideSoftKeyboard();
                            main.presenter.onSelectFragment("SettingsParentFragment");
                            main.onToastMsg(main.language.getLanguage().getString(R.string.quick_access_code_changed));
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
    }
}
