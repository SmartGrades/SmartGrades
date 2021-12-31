package kz.tech.smartgrades.authentication.fragments.registration_parent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;


import kz.tech.smartgrades.L;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.root.firebase.FireBaseImage;

public class RegistrationParentFragment extends Fragment {
    private AuthenticationActivity activity;
    private RegistrationParentView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity = (AuthenticationActivity) getActivity();
        view = new RegistrationParentView(getActivity(), activity.language.getLanguage());
        view.setOnItemClickListener(new RegistrationParentView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
                activity.fragments.onReplaceFragment("RegistrationSelectFragment", L.layout_authentication);
            }

            @Override
            public void onMessageClick(String msg) {
                activity.alert.onToast(msg);

            }

            @Override
            public void onLoginCreateClick(String login, String mail, String password, byte[] dataAvatar,
                                           String name, String quickAccessCode, String status) {
                onCheckLoginIfExist(login, mail, password, dataAvatar, name, quickAccessCode, status);
            }
        });
        view.onSelectScrollView(1);//  Default
    }
    private void onCheckLoginIfExist(String login, String mail, String password, byte[] dataAvatar,
                                     String name, String quickAccessCode, String status) {
        DatabaseReference dbrUsers = new _Firebase().getRefUsers();
        dbrUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean res = false;
                if (dataSnapshot.exists()) {
                    java.util.Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String result = ((DataSnapshot)i.next()).getKey();
                        if (login != null) {
                            if (login.equals(result)) {
                                res = true;
                            }
                        }
                    }
                    if (res) {
                        activity.alert.onToast("Error: User name already exists");
                    } else {
                        onRegistrationRequest(login, mail, password, dataAvatar, name, quickAccessCode, status);
                    }
                } else {
                    activity.alert.onToast("Error: Server off");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.alert.onToast("Error: Server off");
            }
        });
    }
    private void onRegistrationRequest(String login, String mail, String password, byte[] data, String name, String pin, String status) {

        DatabaseReference dbrUsers = new _Firebase().getRefUsers();//  Users
        DatabaseReference dbrFamilyRoom = new _Firebase().getRefFamilyRoom();//  FamilyRoom

        if (data != null) {
            UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(data);//  Set image byte array
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriToString = uri.toString();//  Get download URI
                            String idUsers = dbrUsers.push().getKey();//  Get generated ID for FireBasePath
                            String idFamilyRoom = dbrFamilyRoom.push().getKey();
                            if (idUsers != null && idFamilyRoom != null) {
                                if (login != null) {

                              /*      ModelLogin modelLogin = new ModelLogin();
                                    modelLogin.setAvatar(uriToString);
                                    modelLogin.setId(idFamilyRoom);
                                    modelLogin.setMail(mail);
                                    modelLogin.setName(name);
                                    modelLogin.setPassword(password);
                                    modelLogin.setPin(pin);
                                    modelLogin.setTimestamp("1");
                                    modelLogin.setTitle(status);
                                    modelLogin.setTypeAccount(S.PARENT);
                                    modelLogin.setZid(idUsers);

                                    dbrUsers.child(login).setValue(modelLogin);

                                    modelLogin.setData(data);
                                    modelLogin.setLogin(login);

                                    activity.login.saveUserData(modelLogin);*/

                             //       main.onLoadAnimation(false);
                                    activity.alert.onToast("Registration complete");
                                    activity.alert.hideKeyboard(activity);


                                    startActivity(new Intent(activity, MainActivity.class));
                                }
                            }
                        }
                    });
                }
            });
        } else {
            activity.alert.onToast("Error: Registration");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }
    }
}
