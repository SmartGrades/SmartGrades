package kz.tech.smartgrades.family_room.fragments.quick_access_sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades.L;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.childs_module.ChildActivity;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;
import kz.tech.smartgrades.parents_module.ParentActivity;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;

public class QuickAccessSignInFragment extends Fragment {
    private FamilyRoomActivity activity;
    private QuickAccessSignInView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity = (FamilyRoomActivity) getActivity();
        view = new QuickAccessSignInView(getActivity(), activity.language.getLanguage());
        view.setOnItemClickListener(new QuickAccessSignInView.OnItemClickListener() {

            @Override
            public void onBackButtonClick() {
                activity.fragments.onReplaceFragment(F.FAMILY_MEMBER_LIST, L.layout_family_room);
            }

            @Override
            public void onMenuClick(View view) {

            }

            @Override
            public void onMessageClick(String msg) {
                activity.alert.onToast(msg);
            }

            @Override
            public void onAccessOkClick(ModelFamilyRoom modelFamilyRoom) {
                String status = modelFamilyRoom.getLogin();
                if (status != null) {
                    if (status.equals(S.FATHER) || status.equals(S.MOTHER)) {
                        onNextParentOrChildPage(modelFamilyRoom, S.PARENT);
                    } else if (status.equals(S.SON) || status.equals(S.DAUGHTER)) {
                        onNextParentOrChildPage(modelFamilyRoom, S.CHILD);
                    }
                }
            }
        });
       if (activity.family.getFamilyRoom() != null) {
            view.setData(activity.family.getFamilyRoom());
       }
    }
    private void onNextParentOrChildPage(ModelFamilyRoom m, String status) {
        switch (status) {
            case S.PARENT:
                startActivity(new Intent(activity, ParentActivity.class));

                activity.alert.hideKeyboard(activity);
                activity.login.deleteUserDate();
             //   activity.login.saveUserData();


                break;
            case S.CHILD:
                activity.alert.hideKeyboard(activity);
                activity.login.deleteUserDate();
             //   activity.login.saveUserData();

                startActivity(new Intent(activity, ChildActivity.class));
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }
    }

    public byte[] recoverImageFromUrl(String urlText) throws Exception {
        try {

            URL imageUrl = new URL(urlText);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;

            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }

            baos.flush();

            return  baos.toByteArray();

        } catch (Exception e) {
           // Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

}
