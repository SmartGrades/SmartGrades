package kz.tech.smartgrades.parents_module.add_user;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parents_module.add_user.models.ModelUserReg;
import kz.tech.smartgrades.parents_module.auto_charge.models.ModelAutoCharge;
import kz.tech.smartgrades.parents_module.children_time.models.ModelChildrenTime;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.firebase.FireBaseImage;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.root.tools.ContractEmpty;

public class AddUserFragment extends Fragment {
    public MainActivity main;
    private AddUserView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new AddUserView(getActivity(), main.language.getLanguage(), main.prefs.getFamilyData("status"));
        view.setOnItemClickListener(new AddUserView.OnItemClickListener() {
            @Override
            public void onAddUserClick(ModelUserReg modelUserReg) {
                onRequestAddUser(modelUserReg);//  Add New User
                main.onLoadAnimation(true);//  Show Load Animation
                main.hideSoftKeyboard();//  Hide KeyBoard
            }
            @Override
            public void onMessageClick(String msg) {
                main.onToastMsg(msg);
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
    private void onRequestAddUser(ModelUserReg modelUserReg) {
//        String id = main.prefs.getLoginData("id");
//
//        DatabaseReference dbrFamilyRoom = FirebaseDatabase.getInstance().getReference("FamilyRoom");
//        DatabaseReference dbrAutoCharge = FirebaseDatabase.getInstance().getReference("AutoCharge");
//        DatabaseReference dbrChildrenTime = FirebaseDatabase.getInstance().getReference("ChildrenTime");
//        DatabaseReference dbrChildData = FirebaseDatabase.getInstance().getReference("ChildData");
//
//        if (modelUserReg.getData() != null) {
//            UploadTask uploadTask = new FireBaseImage().uploadImage("Avatars").putBytes(modelUserReg.getData());//  Set image byte array
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
//                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            String uriToString = uri.toString();//  Get download URI
//                            String idChild = dbrFamilyRoom.push().getKey();
//                            String zero = "0";//  Default Child have 0 coins
//                            ArrayList<ModelContract> contractsList = new ArrayList<>();
//                            contractsList.add(new ContractEmpty().getModelContract());//  Zero all values
//                            if (idChild != null) {
//                                if (modelUserReg.getName() != null && modelUserReg.getStatus() != null) {
//                                    //  Family Room -> imageUri - Name - statusParents - PIN
//                                    /*dbrFamilyRoom.child(id)
//                                            .child(idChild)
//                                            .setValue(new ModelFamilyGroup(uriToString, modelUserReg.getName(), modelUserReg.getPin(), modelUserReg.getStatus(), idChild));*/
//
//                                //    callBack.onMessageCallBack("User: Added");
//                                    if (modelUserReg.getStatus().equals("Son") || modelUserReg.getStatus().equals("Daughter")) {
//                                        String n20 = "20";//  AUTO CHARGE
//                                        dbrAutoCharge.child(id).child(idChild).setValue(
//                                                new ModelAutoCharge(n20, n20, n20, n20, n20, n20, n20, "08:00", "open"));
//                                        String t = "22:00-12:00";//  DEFAULT TIME
//                                        dbrChildrenTime.child(id).child(idChild).setValue(
//                                                new ModelChildrenTime(t, t, t, t, t, t, t, "open"));
//                                        dbrChildData.child(id).child(idChild).setValue(
//                                                new ModelChildData(zero, zero, zero, zero, contractsList, idChild));
//                                    }
//                                    //main.presenter.model.addFamilyRoom(new ModelFamilyGroup(uriToString, modelUserReg.getName(), modelUserReg.getPin(), modelUserReg.getStatus(), idChild));
//
//
//                                    main.presenter.onSelectFragment("FamilyGroupFragment");
//                                    main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.family_group));
//
//
//
//                                   // callBack.onResponseAddUser();
//                                }
//                            }//
//                        }
//                    });
//                }
//            });
//        } else {
//         //   callBack.onMessageCallBack("User: Not added");
//        }
    }
}