package kz.tech.smartgrades.family_room.fragments.family_member_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades.S;
import kz.tech.esparta.R;
import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;
import kz.tech.smartgrades.family_room.fragments.family_member_list.adapters.FamilyRoomAdapter;
import kz.tech.smartgrades.root.dialogs.CustomAlertDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;


public class FamilyMemberListFragment extends Fragment {
    private FamilyMemberListView view;
    private FamilyRoomAdapter adapter;
    protected FamilyRoomActivity activity;
    private List<ModelFamilyRoom> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity = (FamilyRoomActivity) getActivity();
        list = new ArrayList<>();

        view = new FamilyMemberListView(getActivity(), activity.language.getLanguage());
        onLoadFamilyList();

        view.setOnItemClickListener(new FamilyMemberListView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
                String[] arrays = {activity.language.getLanguage().getString(R.string.family_room_dialog_text_one),
                        activity.language.getLanguage().getString(R.string.family_room_dialog_text_two),
                        activity.language.getLanguage().getString(R.string.family_room_dialog_text_three)};
                CustomAlertDialog dialog = new CustomAlertDialog(getActivity(), activity.language.getLanguage(), arrays);
                dialog.showAlert();
                dialog.setOnItemClickListener(new CustomAlertDialog.OnItemClickListener() {
                    @Override
                    public void onOkClick() {
                       activity.onExitLogin();
                    }
                });
            }
            @Override
            public void onMoveButtonCLick(View v) {
                PopupMenu menu = new PopupMenu(getActivity(), v);
                menu.getMenu().add(activity.language.getLanguage().getString(R.string.blocked_app)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        activity.onNextFragment(F.APP_LOCK_LIST);
                        return false;
                    }
                });
                menu.getMenu().add(activity.language.getLanguage().getString(R.string.to_get_a_permission)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        activity.onNextFragment(F.ACCESS);
                        return false;
                    }
                });
                menu.show();

            }
        });

        adapter = new FamilyRoomAdapter(getActivity());
        view.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FamilyRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ModelFamilyRoom model) {
                String status = model.getLogin();
                String title = "";
                if (status != null) {
                    if (status.equals(S.FATHER) || status.equals(S.MOTHER)) {
                        if (title != null) {
                            if (title.equals(S.FATHER) || title.equals(S.MOTHER)) {
                                activity.onReturnPreviousActivity();
                            } else {
                                activity.onNextFragment(F.QUICK_ACCESS_SIGN_IN);
                            }
                        } else {
                            activity.onNextFragment(F.QUICK_ACCESS_SIGN_IN);
                        }
                    } else if (status.equals(S.SON) || status.equals(S.DAUGHTER)) {
                        if (activity.hardwareAccess.isToReadNetworkHistory()) {
                            activity.onNextFragment(F.QUICK_ACCESS_SIGN_IN);
                            activity.family.setFamilyRoom(model);
                        } else {
                            activity.onNextFragment(F.CHILD_ACCESS);
                        }
                    }
                }
            }
        });
    }

    private void onLoadFamilyList() {

        String id = activity.login.loadUserDate(LoginKey.ID);
        DatabaseReference dbrFamilyList = new _Firebase().getRefFamilyRoom();
        dbrFamilyList.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    java.util.Iterator i = dataSnapshot.getChildren().iterator();

                    while (i.hasNext()) {
                        String key = (String) ((DataSnapshot) i.next()).getKey();
                        ModelFamilyRoom modelFamilyRoom = dataSnapshot.child(key).getValue(ModelFamilyRoom.class);
                        list.add(modelFamilyRoom);
                    }
                    adapter.setData(list);
                //   callBackFamilyRoom.onResponseFamilyRoom(list);
                } else {
                //    callBackFamilyRoom.onMessageResult("ErrorServerOff");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              //  callBackFamilyRoom.onMessageResult("ErrorServerOff");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }
    }
}

/*
 switch (model.getStatus()) {
            case "Father": onGoToParentMainView(model); break;
            case "Mother": onGoToParentMainView(model); break;
            case "Son": onGoToChildMainView(model); break;
            case "Daughter": onGoToChildMainView(model); break;
        }



           fragment.main.presenter.model.familyRoomList = familyRoomModel.list;
        //  SECOND  -  Save idZ(Family Member ID)


      //  fragment.main.tasksDao.insertUser(new TableFamilyRoom(data, model.getName(), model.getPin(), model.getStatus(), model.getZId()));



        fragment.main.prefs.onSaveFamilyAccount(
                model.getAvatar(), model.getName(), model.getPin(), model.getStatus(), model.getZId());
        //  THIRD Avatar, name, status
        fragment.main.prefs.initFamilyAccount();
        fragment.main.presenter.onSignInFromFamilyRoom(1);//  FamilyRoomList To ChildList
        // FOUR
        fragment.main.presenter.onSelectFragment("ParentMainFragment");
        fragment.main.hideSoftKeyboard();
        fragment.main.presenter.onParentsNav();




          //  FIRST  -  Send list  ->  from FamilyRoom LIST  ->  to Main Model LIST
        fragment.main.presenter.model.familyRoomList = familyRoomModel.list;
        //  SECOND  -  Save idZ(Family Member ID)
        fragment.main.prefs.onSaveFamilyAccount(
                model.getAvatar(), model.getName(), model.getPin(), model.getStatus(), model.getZId());
        //  THIRD Avatar, name, status
        fragment.main.prefs.initFamilyAccount();
        fragment.main.presenter.onSignInFromFamilyRoom(2);

        fragment.main.presenter.onSelectFragment("ChildMainFragment");
        fragment.main.hideSoftKeyboard();
 */