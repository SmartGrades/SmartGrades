package kz.tech.smartgrades.root.mvp;

import android.view.View;
import androidx.fragment.app.Fragment;

public interface IView {
    void onReplaceFragment(Fragment fragment);
    void onRemoveFragment(String s);
    void onToastMsg(String msg);
    void onMenu(View view, int n);
    void hideSoftKeyboard();
    void onLoadAnimation(boolean isLoad);
}
