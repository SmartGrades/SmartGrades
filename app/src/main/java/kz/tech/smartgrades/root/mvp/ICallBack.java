package kz.tech.smartgrades.root.mvp;




public interface ICallBack {
    void onMessageCallBack(String msg);
    void onResponseAddUser();
    void onResponseFamilyRoom();
    void onResponseChildData();
    void onResponseSignIn(int n);
    void onResponseMentors(String a, String n);
}
