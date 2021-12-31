package kz.tech.smartgrades.parents_module.devices;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.ParentActivity;
import kz.tech.smartgrades.parents_module.devices.dialogs.DeviceGroupAlertDialog;
import kz.tech.smartgrades.parents_module.devices.dialogs.DeviceGroupBottomDialogChange;
import kz.tech.smartgrades.root.dialogs.CustomAlertDialog;
import kz.tech.smartgrades.root.firebase.FireBaseDevices;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelDevice;

public class DevicesFragment extends Fragment {
    private ParentActivity activity;
    private DevicesView view;
    private DeviceGroupAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity = (ParentActivity) getActivity();
        view = new DevicesView(getActivity(), activity.language.getLanguage());
        adapter = new DeviceGroupAdapter(getActivity());
        view.getRecyclerView().setAdapter(adapter);

        adapter.setOnItemClickListener(new DeviceGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(String idDevice, int position) {
                String[] arrays = {activity.language.getLanguage().getString(R.string.dialog_title_delete_device),
                        activity.language.getLanguage().getString(R.string.device_deleted_permanently),
                        activity.language.getLanguage().getString(R.string.devices_cannot_be_restored)};
                CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
                dialog.showAlert();
                dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
                    @Override
                    public void onOkClick() {
                        onDeleteDeviceByID(idDevice);//  DELETE DEVICE IN SERVICES
                        adapter.removeData(position);
                        activity.onShowToast(activity.language.getLanguage().getString(R.string.devices_removed));
                    }
                });
            }
            @Override
            public void onItemClick(ModelDevice modelDevice) {
                DeviceGroupBottomDialogChange dialog = new DeviceGroupBottomDialogChange(
                        getActivity(), activity.language.getLanguage(), modelDevice, activity.presenter.model.getChildList());
                dialog.show();
                dialog.setOnItemClickListener(new DeviceGroupBottomDialogChange.OnItemClickListener() {
                    @Override
                    public void onLockClick(ModelDevice modelDevice, String lockStatus) {//   LOCK    UNLOCK
                        final String[] arrays = new String[3];
                        switch (lockStatus) {
                            case "lock":
                                arrays[0] = activity.language.getLanguage().getString(R.string.lock_text_one);
                                arrays[1] = activity.language.getLanguage().getString(R.string.lock_text_two);
                                arrays[2] = activity.language.getLanguage().getString(R.string.lock_text_three);
                                break;
                            case "unlock":
                                arrays[0] = activity.language.getLanguage().getString(R.string.unlock_text_one);
                                arrays[1] = activity.language.getLanguage().getString(R.string.unlock_text_two);
                                arrays[2] = activity.language.getLanguage().getString(R.string.unlock_text_three);
                                break;
                        }
                        CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
                        dialog.showAlert();
                        dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
                            @Override
                            public void onOkClick() {
                                onLockUnlockDevice(modelDevice, lockStatus);
                            }
                        });
                    }
                    @Override
                    public void onUserClick(String idChild, ModelDevice modelDevice, String lockStatus) {//  USER
                        onChangeDeviceLocked(idChild, modelDevice, lockStatus);
                    }
                });
            }

            @Override
            public void onRenameClick(ModelDevice modelDevice) {
                DeviceGroupAlertDialog dialog = new DeviceGroupAlertDialog(getActivity(), activity.language.getLanguage());
                dialog.showAlertDialog();
                dialog.setOnItemClickListener(new DeviceGroupAlertDialog.OnItemClickListener() {
                    @Override
                    public void onMessageAlert(String msg) {
                        activity.onShowToast(msg);
                    }
                    @Override
                    public void onEnterTextClick(String text) {
                        onRenameDevice(modelDevice, text);//  CALL METHOD RENAME DEVICE
                    }
                });
            }
        });
        onLoadDeviceList();
    }


    private void onLoadDeviceList() {
       // activity.onLoadAnimation(true);
        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        DatabaseReference dbrDevices = new FireBaseDevices().getDevices();//  Devices
        dbrDevices.child(idLogin)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterator i = dataSnapshot.getChildren().iterator();
                            List<ModelDevice> arrayList = new ArrayList<>();
                            while (i.hasNext()) {
                                String key = (String) ((DataSnapshot) i.next()).getKey();
                                ModelDevice modelDevice = dataSnapshot.child(key).getValue(ModelDevice.class);
                                arrayList.add(modelDevice);
                            }
                            adapter.setData(arrayList, activity.presenter.model.getFamilyRooms(), activity.language.getLanguage());
                            android.util.Log.e("Tag", " Msg");
                       //     activity.onLoadAnimation(false);
                        } else {
                            android.util.Log.e("Tag", " Msg");
                    //        activity.onLoadAnimation(false);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        android.util.Log.e("Tag", " Msg");
                    //    activity.onLoadAnimation(false);
                    }
                });
    }
    private void onDeleteDeviceByID(String idDevice) {
        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        DatabaseReference dbrDevices = new FireBaseDevices().getDevices();//  Devices
        dbrDevices.child(idLogin)
                .child(idDevice)
                .removeValue();
    }
    private void onChangeDeviceLocked(String idChild, ModelDevice modelDevice, String lockStatus) {
        DatabaseReference dbrDevices = new FireBaseDevices().getDevices();//  Devices

        String idLogin = activity.login.loadUserDate(LoginKey.ID);


        Query query = dbrDevices.child(idLogin).child(modelDevice.getIdDevice());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelDevice m = dataSnapshot.getValue(ModelDevice.class);
                    if (!m.getIdUser().equals(idChild)) {
                        dataSnapshot.getRef().child("idUser").setValue(idChild);
                    }
                    dataSnapshot.getRef().child("lockStatus").setValue(lockStatus);
                    dataSnapshot.getRef().child("onlineStatus").setValue("true");
                    modelDevice.setLockStatus(lockStatus);
                    adapter.updateData(idChild, modelDevice);
                } else {

                }
                android.util.Log.e("Tag", " Msg");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    private void onLockUnlockDevice(ModelDevice modelDevice, String lockStatus) {
        DatabaseReference dbrDevices = new FireBaseDevices().getDevices();//  Devices

        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        Query query = dbrDevices.child(idLogin).child(modelDevice.getIdDevice());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelDevice m = dataSnapshot.getValue(ModelDevice.class);

                    dataSnapshot.getRef().child("lockStatus").setValue(lockStatus);
                    dataSnapshot.getRef().child("onlineStatus").setValue("true");
                    adapter.updateDataLockUnlock(lockStatus, modelDevice);
                } else {

                }
                android.util.Log.e("Tag", " Msg");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    private void onRenameDevice(ModelDevice modelDevice, String nameDevice) {
        DatabaseReference dbrDevices = new FireBaseDevices().getDevices();//  Devices

        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        Query query = dbrDevices.child(idLogin).child(modelDevice.getIdDevice());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().child("nameDevice").setValue(nameDevice);
                    adapter.updateRenameDevice(nameDevice, modelDevice);
                } else {

                }
                android.util.Log.e("Tag", " Msg");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }




}
