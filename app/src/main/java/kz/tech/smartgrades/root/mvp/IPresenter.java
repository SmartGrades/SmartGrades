package kz.tech.smartgrades.root.mvp;

public interface IPresenter {
    void onStart();
    void onSelectFragment(String fragment);
    void onDestroyView();
    void onSignInFromFamilyRoom(int position);
}
