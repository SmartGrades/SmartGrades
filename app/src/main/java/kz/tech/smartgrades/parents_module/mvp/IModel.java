package kz.tech.smartgrades.parents_module.mvp;

public interface IModel {
    void onRequestFamilyList(String path, String id, ICallBack callBack);
    void onRequestFamilyRoom(String path, ICallBack callBack);
    void onLoadStorage(ICallBack callBack);
    void onRequestChildData(String path, String id, ICallBack callBack);
}
