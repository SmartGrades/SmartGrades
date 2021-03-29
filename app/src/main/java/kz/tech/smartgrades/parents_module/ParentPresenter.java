package kz.tech.smartgrades.parents_module;

import kz.tech.smartgrades.P;
import kz.tech.smartgrades.parents_module.mvp.IPresenter;
import kz.tech.smartgrades.parents_module.mvp.ICallBack;
import kz.tech.smartgrades.root.db.IFamilyDao;
import kz.tech.smartgrades.root.firebase.IFireBase;
import kz.tech.smartgrades.root.login.LoginKey;


public class ParentPresenter implements IPresenter {

    private ParentActivity activity;
    public ParentModel model;
    private ICallBack callBack;

    public ParentPresenter(ParentActivity activity, IFireBase fireBase, IFamilyDao familyDao) {
        this.activity = activity;
        this.model = new ParentModel(fireBase, familyDao);
        this.callBack = new ICallBack() {
            @Override
            public void onMessageResult(String msg) {
                activity.onShowToast(msg);
            }

            @Override
            public void onResponseFamilyList(boolean isNewLoad) {
                if (isNewLoad) {
                    model.onRequestFamilyRoom(P.USERS, callBack);
                } else {
                    model.onLoadStorage(callBack);
                }
                model.onRequestChildData(P.CHILD_DATA, activity.login.loadUserDate(LoginKey.ID), callBack);
                activity.view.onToolbarSelect(1, null);

            }

            @Override
            public void onLoadChildList() {
                if (model.getChildList() != null) {
                    int lol = model.getChildList().size();
                    if (lol > 0) {
                        activity.view.getAdapterPhotoChild().onUpdateDate(model.getChildList());
                        activity.view.setParentClickListener(activity);



                    /*    int lol2 = activity.view.getViewPager().getCurrentItem();

                        String idSelectChild = model.getIdChildFromChildList(lol2);

                        activity.adapter.onFragmentListenerID(2, idSelectChild);

                    //    activity.view.onDefaultPageSelect(0);
                    //    activity.view.onDefaultPageSelect(lol2);*/
                    }
                }
            }

            @Override
            public void onUpdateChildList() {


            }
        };

    }


    @Override
    public void onStart() {
        model.onRequestFamilyList(P.FAMILY_ROOM, activity.login.loadUserDate(LoginKey.ID), callBack);
    }











    @Override
    public void onDestroyView() {
        if (model != null) { model = null; }
        if (callBack != null) { callBack = null; }
        if (activity != null) { activity = null; }
    }
}
