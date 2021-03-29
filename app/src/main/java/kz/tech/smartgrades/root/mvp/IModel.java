package kz.tech.smartgrades.root.mvp;

import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.root.models.ModelDevice;

public interface IModel {
    Fragment getFragment(String fragment);
    void onRequestFamilyRoom(ICallBack callBack, String id);
    void onRequestChildData(ICallBack callBack, String id, int n);
    void onDeviceCheckIfExist(ModelDevice modelDevice, String serialNumberOrIMEI, String id, String idAccount);
    void onRequestMentor(ICallBack callBack, String id);
}
