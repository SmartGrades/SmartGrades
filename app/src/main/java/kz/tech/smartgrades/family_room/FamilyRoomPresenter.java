package kz.tech.smartgrades.family_room;

import kz.tech.smartgrades.family_room.mvp.ICallBack;
import kz.tech.smartgrades.family_room.mvp.IPresenter;

public class FamilyRoomPresenter implements IPresenter {
    private FamilyRoomActivity view;
    private FamilyRoomModel model;
    private ICallBack callBack;

    public FamilyRoomPresenter(FamilyRoomActivity view) {
        this.view = view;
        this.model = new FamilyRoomModel();
        this.callBack = new ICallBack() {};

    }







    @Override
    public void onDestroyView() {
        if (model != null) { model = null; }
        if (view != null) { view = null; }
    }
}
