package kz.tech.smartgrades.authentication.fragments.sign_in;

import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades.L;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.childs_module.ChildActivity;
import kz.tech.smartgrades.mentor_module.MentorActivity;
import kz.tech.smartgrades.parents_module.ParentActivity;

public class SignInFragment extends Fragment {
    private AuthenticationActivity activity;
    private SignInView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }

    private void initViews() {
        activity = (AuthenticationActivity) getActivity();
        view = new SignInView(getActivity(), activity.language.getLanguage());
        view.setOnItemClickListener(new SignInView.OnItemClickListener() {
            @Override
            public void onMessageClick(String msg) {
                activity.alert.onToast(msg);
            }
            @Override
            public void onSignInClick(String login, String password) {
                onSignInRequest(login, password);
            }
            @Override
            public void onRegistrationClick() {
                activity.alert.hideKeyboard(activity);
                activity.fragments.onReplaceFragment(F.REGISTRATION_SELECT, L.layout_authentication);
            }
            @Override
            public void onForgotPasswordClick() {
                activity.fragments.onReplaceFragment(F.FORGOT_PASSWORD, L.layout_authentication);
            }
        });
    }

    private void onSignInRequest(String login, String password) {
        DatabaseReference dbrUsers = new _Firebase().getRefUsers();
        dbrUsers.child(login).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean res = false;
                String rAvatar = null, rId  = null, rMail  = null, rName = null, rPassword = null,
                        rPin = null, rTimestamp = null, rTitle = null, rTypeAccount = null, zid = null;
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        rAvatar = (String) ((DataSnapshot)i.next()).getValue();
                        rId = (String) ((DataSnapshot)i.next()).getValue();
                        rMail = (String) ((DataSnapshot)i.next()).getValue();
                        rName = (String) ((DataSnapshot)i.next()).getValue();
                        rPassword = (String) ((DataSnapshot)i.next()).getValue();
                        rPin = (String) ((DataSnapshot)i.next()).getValue();
                        rTimestamp = (String) ((DataSnapshot)i.next()).getValue();
                        rTitle = (String) ((DataSnapshot)i.next()).getValue();
                        rTypeAccount = (String) ((DataSnapshot)i.next()).getValue();
                        zid = (String) ((DataSnapshot)i.next()).getValue();

                        if (password.equals(rPassword)) {
                            res = true;    // Equal Login == Login Service
                        }
                    }
                    if (res) {
                        if (rId != null && rMail != null && rPassword != null && rTypeAccount != null) {

                         /*   ModelLogin modelLogin = new ModelLogin();
                            modelLogin.setAvatar(rAvatar);
                            modelLogin.setData(null);
                            modelLogin.setId(rId);
                            modelLogin.setLogin(login);
                            modelLogin.setMail(rMail);
                            modelLogin.setName(rName);
                            modelLogin.setPassword(rPassword);
                            modelLogin.setPin(rPin);
                            modelLogin.setTimestamp(rTimestamp);
                            modelLogin.setTitle(rTitle);
                            modelLogin.setTypeAccount(rTypeAccount);
                            modelLogin.setZid(zid);*/

                            // If Equal Good, Send ID, for continue
                            switch (rTypeAccount) {
                                case S.PARENT:
                                 //   activity.login.saveUserData(modelLogin);
                                    startActivity(new Intent(getActivity(), ParentActivity.class));
                                    break;
                                case S.CHILD:
                                  //  activity.login.saveUserData(modelLogin);
                                    startActivity(new Intent(getActivity(), ChildActivity.class));
                                    break;
                                case S.MENTOR:
                                  //  activity.login.saveUserData(modelLogin);
                                    startActivity(new Intent(getActivity(), MentorActivity.class));
                                    break;
                            }
                        }
                    } else {
                        activity.alert.onToast("Error: Wrong Password");
                    }
                } else {
                    activity.alert.onToast("Error: Wrong Login");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.alert.onToast("Error: Server off");
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }
    }
}
