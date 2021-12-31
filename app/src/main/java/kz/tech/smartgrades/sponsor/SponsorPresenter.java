package kz.tech.smartgrades.sponsor;

import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.mvp.ICallBack;
import kz.tech.smartgrades.sponsor.mvp.IPresenter;

public class SponsorPresenter implements IPresenter, ICallBack {

    private SponsorActivity activity;
    private SponsorModel sponsorModel;
    private ICallBack callBack;


    public SponsorPresenter(SponsorActivity activity) {
        this.activity = activity;
        this.sponsorModel = new SponsorModel(activity);
        this.callBack = this;
    }

    @Override
    public void onStartPresenter() {
        sponsorModel.onCheckSponsorWorksheet(callBack);
        sponsorModel.onLoadData(callBack);
    }
    public void onUpdateData() {
        sponsorModel.onLoadData(callBack);
    }
    @Override
    public void onDestroyView() {
        if (sponsorModel != null) sponsorModel = null;
        if (activity != null) activity = null;
    }

    public void getAddSponsorWorksheet(String aboutMe, String keyWordsYourMentors) {
        sponsorModel.onAddSponsorWorksheet(callBack, aboutMe, keyWordsYourMentors);
    }


    ////////////       ICallBack       ////////////
    @Override
    public void onCheckSponsorWorksheetResult(boolean isEmpty) {
        if (isEmpty) activity.onNextFragment(1, null);
    }

    @Override
    public void onAddSponsorWorksheetResult(boolean isDone) {
        if (isDone) activity.onRemoveFragment();
    }

    @Override
    public void onLoadDataResult(ModelSponsorData modelSponsorData) {
        //activity.sponsorUsersListAdapter.updateData(modelSponsorData, false);
        activity.setPrivateAccount(modelSponsorData.getPrivateAccount());
        activity.onSetSponsorData(modelSponsorData);
    }

    @Override
    public void ResultAddAboutMe(boolean b) {
        activity.onRemoveFragment();
    }

    public void onAddAboutMe(String aboutMe) {
        sponsorModel.onAddAboutMe(callBack, aboutMe);
    }
}