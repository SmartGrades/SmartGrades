package kz.tech.smartgrades.child;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelChildWardCoin;
import kz.tech.smartgrades.child.mvp.ICallBack;
import kz.tech.smartgrades.child.mvp.IPresenter;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.parent.model.ModelInteractionToolSteps;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class ChildPresenter implements IPresenter, ICallBack {

    private ChildActivity activity;
    private ChildModel childModel;
    private ICallBack callBack;

    public ChildPresenter(ChildActivity activity) {
        this.activity = activity;
        this.childModel = new ChildModel(activity);
        this.callBack = this;
    }

    @Override
    public void onStartPresenter() {
        childModel.onLoadData(callBack);
    }

    public void onUpdateChildWardCoins() {
        childModel.onLoadChildWardCoins(callBack);
    }

    @Override
    public void onDestroyView() {
        if (childModel != null) childModel = null;
        if (activity != null) activity = null;
    }

    ////////////       ICallBack       ////////////
    @Override
    public void onLoadChildWardCoins(ArrayList<ModelChildWardCoin> childWardCoinList, ArrayList<ModelDefaultMessages> modelDefaultChat) {

    }
    @Override
    public void onLoadChildWardSteps(ArrayList<ModelInteractionToolSteps> childWardStepsList, ArrayList<ModelDefaultMessages> modelDefaultChat) {

    }
    @Override
    public void onLoadChildWardTime(int totalMinute) {
    }

    @Override
    public void ResultLoadChildData(ModelChildData modelChildData) {
        activity.onSetChildData(modelChildData);
        activity.setInvites();
    }

    @Override
    public void ResultAddAboutMe(boolean b) {
        activity.onRemoveFragment();
    }

    public void onUpdateCartList() {
        childModel.onUpdateCartList(callBack);
    }
    @Override
    public void ResultUpdateCartList(ArrayList<ModelDiscontCard> modelUserCards) {
        activity.setModelUserCards(modelUserCards);
    }


}