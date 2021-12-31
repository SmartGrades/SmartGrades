package kz.tech.smartgrades.parents_module.auto_charge;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.ParentActivity;
import kz.tech.smartgrades.parents_module.auto_charge.adapters.AutoChargeAdapter;
import kz.tech.smartgrades.parents_module.auto_charge.dialogs.AutoChargeDialog;
import kz.tech.smartgrades.parents_module.auto_charge.dialogs.AutoChargeTimeDialog;
import kz.tech.smartgrades.parents_module.auto_charge.models.ModelAutoCharge;
import kz.tech.smartgrades.root.dialogs.CustomAlertDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelContract;

public class AutoChargeFragment extends Fragment implements AutoChargeListenerClick {
    private ParentActivity activity;
    private AutoChargeView view;
    private AutoChargeAdapter adapter;
    private String idChild = "";
    private DatabaseReference dbrAutoCharge;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        dbrAutoCharge = FirebaseDatabase.getInstance().getReference("AutoCharge");
        activity = (ParentActivity) getActivity();
        view = new AutoChargeView(getActivity(), activity.language.getLanguage());
        adapter = new AutoChargeAdapter(getActivity());
        view.recyclerView.setAdapter(adapter);


        view.setOnItemClickListener(new AutoChargeView.OnItemClickListener() {


            @Override
            public void onCurrentChildClick(int position, boolean isSelect) {
                String idChild = activity.presenter.model.getIdChildFromChildList(position);


                String idLogin = activity.login.loadUserDate(LoginKey.ID);

                activity.presenter.model.setChildPosition(position);
                if (isSelect) {
                    if (idLogin != null && idChild != null) {
                        onRequestAutoCharge(idLogin, idChild);
                    }
                }
            }

            @Override
            public void onDisableAutoChargeClick(boolean isOpen) {
                onDisableAutoCharge(isOpen);
            }

            @Override
            public void onAutoChargeTimeClick(String text) {
                AutoChargeTimeDialog dialog = new AutoChargeTimeDialog(getActivity(), text, activity.language.getLanguage());
                dialog.show();
                dialog.setOnItemClickListener(new AutoChargeTimeDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(String coinsAutoTime) {
                        onChangeAutoChargeTime(coinsAutoTime);
                        view.setAutoTime(coinsAutoTime);
                    }
                });
            }
        });

        adapter.setOnItemClickListener(new AutoChargeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String time) {
                AutoChargeDialog dialog = new AutoChargeDialog(getActivity(), time, position, activity.language.getLanguage());
                dialog.show();
                dialog.setOnItemClickListener(new AutoChargeDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String incrementTime) {
                        onChangeCountCoinsEveryDay(position, incrementTime);
                        adapter.changeDataByDayPosition(position, incrementTime);
                    }
                });
            }

            @Override
            public void onCoinsCheckedOffClick(int position, String coins) {
                String[] arrays = {activity.language.getLanguage().getString(R.string.auto_charge_coins_dialog_text_one),
                        activity.language.getLanguage().getString(R.string.auto_charge_coins_dialog_text_two),
                        activity.language.getLanguage().getString(R.string.auto_charge_coins_dialog_text_three)};
                CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
                dialog.showAlert();
                dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
                    @Override
                    public void onOkClick() {
                        onChangeCountCoinsEveryDay(position, "close");
                        adapter.changeDataByDayPosition(position, "close");
                    }
                });
            }
        });



    }


    public void onRequestAutoCharge(String idLogin, String idChild) {

       // activity.onLoadAnimation(true);
        dbrAutoCharge.child(idLogin).child(idChild).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String monday = (String) ((DataSnapshot) i.next()).getValue();
                        String tuesday = (String) ((DataSnapshot) i.next()).getValue();
                        String wednesday = (String) ((DataSnapshot) i.next()).getValue();
                        String thursday = (String) ((DataSnapshot) i.next()).getValue();
                        String friday = (String) ((DataSnapshot) i.next()).getValue();
                        String saturday = (String) ((DataSnapshot) i.next()).getValue();
                        String sunday = (String) ((DataSnapshot) i.next()).getValue();
                        String timeAutoCharge = (String) ((DataSnapshot) i.next()).getValue();
                        String isSwitch = (String) ((DataSnapshot) i.next()).getValue();

                        adapter.setData(new ModelAutoCharge(
                                monday, tuesday, wednesday, thursday, friday, saturday, sunday, timeAutoCharge, isSwitch));
                        android.util.Log.e("Tag", " Msg");
                        view.setTimeData(timeAutoCharge, isSwitch);
                    }
                    android.util.Log.e("Tag", " Msg");
                //    activity.onLoadAnimation(false);
                    // callBack.onResponseFamilyRoom();
                } else {
                    //  callBack.onMessageResult("ErrorServerOff");
                //    activity.onLoadAnimation(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
             //   activity.onLoadAnimation(false);
                //  callBack.onMessageResult("ErrorServerOff");
            }
        });
    }
    private void onChangeCountCoinsEveryDay(int position, String incrementTime) {
        String idChild = activity.presenter.model.getIdChildFromChildList(activity.presenter.model.getChildPosition());
        String day = "";
        switch (position) {
            case 0: day = "aMonday"; break;
            case 1: day = "bTuesday"; break;
            case 2: day = "cWednesday"; break;
            case 3: day = "dThursday"; break;
            case 4: day = "eFriday"; break;
            case 5: day = "fSaturday"; break;
            case 6: day = "gSunday"; break;
        }

        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        Query query = dbrAutoCharge.child(idLogin).child(idChild);

        final String dayTime = day;
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelContract m = dataSnapshot.getValue(ModelContract.class);
                    dataSnapshot.getRef().child(dayTime).setValue(incrementTime);
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
    private void onDisableAutoCharge(boolean isOpen) {
        String disableSwitch = "";
        if (isOpen) {
            disableSwitch = "close";
        } else {
            disableSwitch = "open";
        }
        final String disableSwitch2 = disableSwitch;
        idChild = activity.presenter.model.getIdChildFromChildList(activity.presenter.model.getChildPosition());

        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        Query query = dbrAutoCharge.child(idLogin).child(idChild);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelContract m = dataSnapshot.getValue(ModelContract.class);
                    dataSnapshot.getRef().child("zSwitch").setValue(disableSwitch2);
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
    private void onChangeAutoChargeTime(String autoTime) {
        String idChild = activity.presenter.model.getIdChildFromChildList(activity.presenter.model.getChildPosition());

        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        Query query = dbrAutoCharge.child(idLogin).child(idChild);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelContract m = dataSnapshot.getValue(ModelContract.class);
                    dataSnapshot.getRef().child("hTimeAutoCharge").setValue(autoTime);
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

    @Override
    public void onChildIDClick(String id) {
        idChild = id;


        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        if (idLogin != null && id != null) {
                onRequestAutoCharge(idLogin, id);
        }
    }
}
