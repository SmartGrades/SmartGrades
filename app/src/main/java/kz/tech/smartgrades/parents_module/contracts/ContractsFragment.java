package kz.tech.smartgrades.parents_module.contracts;

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
import java.util.List;

import kz.tech.smartgrades.P;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.ParentActivity;
import kz.tech.smartgrades.parents_module.contracts.adapter.ContractsAdapter;
import kz.tech.smartgrades.parents_module.contracts.dialog.ContractsGoodDialog;
import kz.tech.smartgrades.parents_module.contracts.dialog.EditContractDialog;
import kz.tech.smartgrades.parents_module.contracts.dialog.SignInContractDialog;
import kz.tech.smartgrades.parents_module.coins.models.ModelChangeStep;
import kz.tech.smartgrades.root.dialogs.CustomAlertDialog;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.models.ModelContract;

public class ContractsFragment extends Fragment implements ContractsListenerClick {
    private ParentActivity activity;
    private ContractsView view;
    private ContractsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        initViews();
        return view;//R.style.CustomDialog2
    }
//    private void initViews() {
//        activity = (ParentActivity) getActivity();
//        view = new ContractsView(getActivity(), activity.language.getLanguage());
//        view.setOnItemClickListener(new ContractsView.OnItemClickListener() {
//            @Override
//            public void onNewContractClick() {
//                //String name = main.iRes.getIRes().getString(R.string.reports_on) + " ";
//                String name = "";
//                String avatar = "";
//
//                if (activity.presenter.model.getChildList() != null) {
//                    int lol = activity.presenter.model.getChildList().size();
//                    if (lol > 0) {
//                        String id = activity.presenter.model.getCurrentChildID();
//                        List<ModelChild> list = activity.presenter.model.getChildList();
//                        for (int i = 0; i < list.size(); i++) {
//                            if (id.equals(list.get(i).getIdChild())) {
//                                name = list.get(i).getName();
//                                avatar = list.get(i).getAvatar();
//                            }
//                        }
//                    }
//                }
//                EditContractDialog dialog = new EditContractDialog(getActivity(), R.style.CustomDialog2,
//                        activity.language.getLanguage(), null, avatar, name);
//                dialog.show();
//                dialog.setOnItemClickListener(new EditContractDialog.OnItemClickListener() {
//                    @Override
//                    public void onCreateContractClick(String title, String avatar, String purpose, String description, String countSteps) {
//                        onInsertContract(title, avatar, purpose, description, countSteps);//  INSERT DATA IN CLOUD
//                        onBackClick();
//                    }
//                    @Override
//                    public void onMessageClick(String msg) {
//                      //  activity.onToastMsg(msg);
//
//                    }
//                    @Override
//                    public void onSaveChangeClick(ModelContract modelContract) {
//
//                    }
//                });
//            }
//        });
//        //////////////            ADAPTER              /////////////////
//        adapter = new ContractsAdapter(getActivity());
//        view.recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new ContractsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(ModelContract modelContract) {
//                String avatarParent = "";
//                String avatarChild = "";
//                boolean isSignIn = false;
//
//                avatarParent = activity.prefs.getFamilyData("avatar");
//
//                String id = activity.presenter.model.getCurrentChildID();
//                List<ModelChild> list = activity.presenter.model.getChildList();
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getIdChild().equals(id)) {
//                        avatarChild = list.get(i).getAvatar();
//                    }
//                }
//                //  IF GOOD DEAL, WE OPEN THIS WINDOW
//                if (!modelContract.getDateParent().equals("") && !modelContract.getDateChild().equals("")) {
//                    ContractsGoodDialog dialog = new ContractsGoodDialog(getActivity(), R.style.CustomDialog, activity.language.getLanguage());//R.style.CustomDialog
//                    dialog.showAlertDialog();
//                    return;
//                } else if (modelContract.getCheckParent().equals("not") && modelContract.getCheckChild().equals("not")) {//  OR NOT, WE OPEN EDIT CONTRACTS
//                    isSignIn = true;
//                } else if (modelContract.getCheckParent().equals("yes") && modelContract.getCheckChild().equals("not")) {
//                    isSignIn = true;
//                } else if (modelContract.getCheckParent().equals("not") && modelContract.getCheckChild().equals("yes")) {
//                    isSignIn = true;
//                }
//                if (isSignIn) {
//                    isSignIn = false;
//                    SignInContractDialog dialog = new SignInContractDialog(
//                            getActivity(), R.style.CustomDialog2, activity.language.getLanguage(), modelContract, avatarParent, avatarChild);
//                    dialog.show();
//                    dialog.setOnItemClickListener(new SignInContractDialog.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(int position, ModelContract modelContract) {
//                            switch (position) {
//                                case 1: onAgreeChildParentOk(position, modelContract); break;//  CHILD
//                                case 2: onAgreeChildParentOk(position, modelContract); break;//  PARENT
//                            }
//                        }
//                        @Override
//                        public void onRemoveContractIdClick(ModelContract modelContract) {
//                            String[] arrays = {activity.language.getLanguage().getString(R.string.alert_delete_contract_text_one),
//                                    activity.language.getLanguage().getString(R.string.alert_delete_contract_text_two),
//                                    activity.language.getLanguage().getString(R.string.alert_delete_contract_text_three)};
//                            CustomAlertDialog dialog2 = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
//                            dialog2.showAlert();
//                            dialog2.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
//                                @Override
//                                public void onOkClick() {
//                                    onRemoveContractID(modelContract);
//                      //              activity.presenter.model.deleteContractInCildDataList(modelContract);
//                                    onBackClick();
//                                    dialog.dismiss();
//                                }
//                            });
//                        }
//                    });
//                } else {
//                    //  YOUR CODE
//                    String name = "";
//                    String avatar = "";
//
//                   if (activity.presenter.model.getChildList() != null) {
//                        int lol = activity.presenter.model.getChildList().size();
//                        if (lol > 0) {
//                            String idZ = activity.presenter.model.getCurrentChildID();
//                            List<ModelChild> listZ = activity.presenter.model.getChildList();
//                            for (int i = 0; i < listZ.size(); i++) {
//                                if (idZ.equals(listZ.get(i).getIdChild())) {
//                                    name = listZ.get(i).getName();
//                                    avatar = listZ.get(i).getAvatar();
//                                }
//                            }
//                        }
//                    }
//                    EditContractDialog dialog = new EditContractDialog(getActivity(), R.style.CustomDialog2,
//                            activity.language.getLanguage(), modelContract, avatar, name);
//                    dialog.show();
//                    dialog.setOnItemClickListener(new EditContractDialog.OnItemClickListener() {
//                        @Override
//                        public void onCreateContractClick(String title, String avatar, String purpose, String description, String countSteps) {
//                            onBackClick();
//                        }
//                        @Override
//                        public void onMessageClick(String msg) {
//                       //     activity.onToastMsg(msg);
//
//                        }
//                        @Override
//                        public void onSaveChangeClick(ModelContract modelContract) {
//                            onUpdateContractID(modelContract);//  UPDATE DATA IN CLOUD
//                      //      activity.presenter.model.updateContractInChildDataList(modelContract);//  UPDATE DATA IN LIST
//                            onBackClick();
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onStepOkClick(int position, ModelChangeStep modelChangeStep) {
//                String[] arrays = {activity.language.getLanguage().getString(R.string.checked_count_steps_text_one),
//                        activity.language.getLanguage().getString(R.string.checked_count_steps_text_two),
//                        activity.language.getLanguage().getString(R.string.checked_count_steps_text_three)};
//                CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
//                dialog.showAlert();
//                dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
//                    @Override
//                    public void onOkClick() {
//                        onCheckedCountSteps(position, modelChangeStep);
//                    }
//                });
//            }
//
//            @Override
//            public void onStepCancelClick(int position, ModelChangeStep modelChangeStep) {
//                String[] arrays = {activity.language.getLanguage().getString(R.string.unchecked_count_steps_text_one),
//                        activity.language.getLanguage().getString(R.string.unchecked_count_steps_text_two),
//                        activity.language.getLanguage().getString(R.string.unchecked_count_steps_text_three)};
//                CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
//                dialog.showAlert();
//                dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
//                    @Override
//                    public void onOkClick() {
//                        onCheckedCountSteps(position, modelChangeStep);
//                    }
//                });
//            }
//
//            @Override
//            public void onMessageClick(String msg) {
//               // activity.onToastMsg(msg);
//            }
//
//            @Override
//            public void onFulfillsClick(int position, ModelContract modelContract) {
//                String[] arrays = {activity.language.getLanguage().getString(R.string.fulfills_dialog_text_one),
//                        activity.language.getLanguage().getString(R.string.fulfills_dialog_text_two),
//                        activity.language.getLanguage().getString(R.string.fulfills_dialog_text_three)};
//                CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
//                dialog.showAlert();
//                dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
//                    @Override
//                    public void onOkClick() {
//                        onDateDoneParent(position, modelContract);
//                    }
//                });
//            }
//        });
//    }
//    private void onCheckedCountSteps(int position, ModelChangeStep modelChangeStep) {
//        Query query = activity.fireBase.getFireBase(P.CHILD_DATA)
//                .child(activity.prefs.getLoginData("id"))
//                .child(modelChangeStep.getIdChild())
//                .child("contracts")
//                .child(modelChangeStep.getIdContract());
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    String dateChild = "";
//                    if (modelChangeStep.getStep() == 777) {
//                        //  If Child all steps done, result == 777 else get current date
//                        dateChild = activity.date.getCurrentDate();
//                    }
//                    dataSnapshot.getRef().child("dateChild").setValue(dateChild);
//                    dataSnapshot.getRef().child("countSteps").setValue(modelChangeStep.getCountSteps());
//
//                    adapter.updateSteps(position, modelChangeStep.getCountSteps(), dateChild);
//                    activity.presenter.model.updateCountStepsByIdContract(modelChangeStep.getIdContract(), modelChangeStep.getCountSteps());
//                } else {
//                    android.util.Log.e("Tag", " Msg");
//                }
//                android.util.Log.e("Tag", " Msg");
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        };
//        query.addListenerForSingleValueEvent(valueEventListener);
//    }
//    private void onDateDoneParent(int position, ModelContract modelContract) {
//        Query query = activity.fireBase.getFireBase(P.CHILD_DATA)
//                .child(activity.prefs.getLoginData("id"))
//                .child(modelContract.getIdChild())
//                .child("contracts")
//                .child(modelContract.getIdContract());
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    dataSnapshot.getRef().child("dateParent").setValue(activity.date.getCurrentDate());
//                    adapter.updateFulfills(position, activity.date.getCurrentDate());
//                    activity.presenter.model.updateDateParentByIdContract(modelContract.getIdContract(), modelContract.getDateParent());
//                } else {
//                    android.util.Log.e("Tag", " Msg");
//                }
//                android.util.Log.e("Tag", " Msg");
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        };
//        query.addListenerForSingleValueEvent(valueEventListener);
//    }
    public void setContractSelect(List<ModelContract> list) {
        if (list != null) {
            if (list.size() == 0) {
                view.setSelectRL("CreateContract");
            } else if (list.size() > 0) {
                adapter.setData(list, activity.presenter.model.getFamilyRooms(), activity.language.getLanguage());
                view.setSelectRL("LoadContracts");
            }
        } else {
            view.setSelectRL("CreateContract");
        }
    }

//    private void onAgreeChildParentOk(int position, ModelContract modelContract) {
//        Query query = activity.fireBase.getFireBase(P.CHILD_DATA)
//                .child(activity.prefs.getLoginData("id"))
//                .child(modelContract.getIdChild())
//                .child("contracts")
//                .child(modelContract.getIdContract());
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    ModelContract m = dataSnapshot.getValue(ModelContract.class);
//                    switch (position) {
//                        case 1://  CHILD
//                            dataSnapshot.getRef().child("checkChild").setValue("yes");
//
//                            activity.presenter.model.updateCheckChildByIdContract(modelContract.getIdContract(), "yes");
//                            break;
//                        case 2://  PARENT
//                            dataSnapshot.getRef().child("checkParent").setValue("yes");
//
//                            activity.presenter.model.updateCheckParentByIdContract(modelContract.getIdContract(), "yes");
//                            break;
//                    }
//                    // contractsAdapter.updateSteps(position, modelChangeStep.getCountSteps());
//                    //  main.presenter.model.updateChildDataListByIdContract(modelChangeStep.getIdContract(), modelChangeStep.getCountSteps());
//                } else {
//                    android.util.Log.e("Tag", " Msg");
//                }
//                android.util.Log.e("Tag", " Msg");
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        };
//        query.addListenerForSingleValueEvent(valueEventListener);
//    }

    /////////////     INSERT CONTRACT       //////////////////
//    private void onInsertContract(String title, String avatar, String purpose, String description, String countSteps) {
//        String idLogin = activity.prefs.getLoginData("id");
//        String idParent = activity.prefs.getFamilyData("id");
//        String idChild = activity.presenter.model.getCurrentChildID();
//        String idContract = activity.fireBase.getFireBase(P.CHILD_DATA).push().getKey();
//        String not = "not";
//        String dateS = "";
//        String contracts = "contracts";
//
//        ModelContract modelContract = new ModelContract(avatar, not, not, countSteps +"/0",
//                dateS, dateS, description, idChild, idContract, idParent, purpose, title);
//
//        ////    INSERT IN CLOUD    //////
//        activity.fireBase.getFireBase(P.CHILD_DATA)
//                .child(idLogin)
//                .child(idChild)
//                .child(contracts)
//                .child(idContract)
//                .setValue(modelContract);
//
//        ////    INSERT IN LIST    //////
//        activity.presenter.model.insertContractInChildDataList(modelContract);
//    }


    ////////////      UPDATE CONTRACT       //////////////////
    private void onUpdateContractID(ModelContract m) {
        Query query = activity.fireBase.getFireBase(P.CHILD_DATA)
                .child(activity.prefs.getLoginData("id"))
                .child(m.getIdChild())
                .child("contracts")
                .child(m.getIdContract());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelContract modelContract = dataSnapshot.getValue(ModelContract.class);
                    dataSnapshot.getRef().child("avatar").setValue(m.getAvatar());
                    dataSnapshot.getRef().child("checkChild").setValue(m.getCheckChild());
                    dataSnapshot.getRef().child("checkParent").setValue(m.getCheckParent());
                    dataSnapshot.getRef().child("countSteps").setValue(m.getCountSteps());
                    dataSnapshot.getRef().child("dateChild").setValue(m.getDateChild());
                    dataSnapshot.getRef().child("dateParent").setValue(m.getDateParent());
                    dataSnapshot.getRef().child("description").setValue(m.getDescription());
                    dataSnapshot.getRef().child("idChild").setValue(m.getIdChild());
                    dataSnapshot.getRef().child("idContract").setValue(m.getIdContract());
                    dataSnapshot.getRef().child("idParent").setValue(m.getIdParent());
                    dataSnapshot.getRef().child("purposeChild").setValue(m.getPurposeChild());
                    dataSnapshot.getRef().child("title").setValue(m.getTitle());

                    // contractsAdapter.updateSteps(position, modelChangeStep.getCountSteps());
                    //  main.presenter.model.updateChildDataListByIdContract(modelChangeStep.getIdContract(), modelChangeStep.getCountSteps());
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

    ////////////      REMOVE CONTRACT       /////////////////
    private void onRemoveContractID(ModelContract modelContract) {
        DatabaseReference dbrChildData = activity.fireBase.getFireBase(P.CHILD_DATA)
                .child(activity.prefs.getLoginData("id"))
                .child(modelContract.getIdChild())
                .child("contracts")
                .child(modelContract.getIdContract());
        dbrChildData.removeValue();
    }

    @Override
    public void onChildIDClick(String id) {

    }

    @Override
    public void onBackClick() {

    }
//    @Override
//    public void onChildIDClick(String id) {
//        List<ModelContract> contractList = activity.presenter.model.getContractsChildDataList(id);
//        if (contractList != null) {
//            setContractSelect(contractList);
//        }
//    }
//    @Override
//    public void onBackClick() {
//        String id = activity.presenter.model.getCurrentChildID();
//        if (id != null) {
//            List<ModelContract> contractList = activity.presenter.model.getContractsChildDataList(id);
//            if (contractList != null) {
//                setContractSelect(contractList);
//            }
//        }
//    }
}
