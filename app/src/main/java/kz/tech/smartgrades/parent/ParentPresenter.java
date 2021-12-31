package kz.tech.smartgrades.parent;

import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.parent.mvp.ICallBack;
import kz.tech.smartgrades.parent.mvp.IPresenter;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class ParentPresenter implements IPresenter, ICallBack {

    private ParentActivity activity;
    private ParentModel model;
    private ICallBack callBack;

    ////////////       IPresenter       ////////////
    public ParentPresenter(ParentActivity activity) {
        this.activity = activity;
        this.model = new ParentModel(activity);
        this.callBack = this;
    }
    @Override
    public void onStartPresenter() {
        model.onLoadData(callBack);
    }
    @Override
    public void onDestroyView() {
        if (callBack != null) callBack = null;
        if (model != null) {
            model.onDestroyModel();
            model = null;
        }
        if (activity != null) activity = null;
    }


    ////////////       ICallBack       ////////////
    @Override
    public void onResultLoadData(ModelParentData modelParentData) {
        //activity.onNextFragment(3);//ПЕРЕХОД НА ФРАГМЕНТ СО СПИСКОМ КОНТАКТОВ
        //activity.FragmentAdapter.onParentUsersList(modelParentData);
        activity.setParentData(modelParentData);
        if(!listIsNullOrEmpty(modelParentData.getInterForms())){
            activity.setInvites();
        }
    }
}
