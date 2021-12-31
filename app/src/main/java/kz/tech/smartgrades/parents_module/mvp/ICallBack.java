package kz.tech.smartgrades.parents_module.mvp;

public interface ICallBack {
    void onMessageResult(String msg);
    void onResponseFamilyList(boolean isNewLoad);
    void onLoadChildList();
    void onUpdateChildList();
}
