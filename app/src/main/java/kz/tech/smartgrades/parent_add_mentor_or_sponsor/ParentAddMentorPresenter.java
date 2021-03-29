package kz.tech.smartgrades.parent_add_mentor_or_sponsor;

import java.util.ArrayList;
import java.util.List;

import kz.tech.smartgrades.parent.model.ModelFamilyGroup;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.mvp.ICallBack;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.mvp.IPresenter;

public class ParentAddMentorPresenter implements IPresenter, ICallBack {

    private ParentAddSponsorOrMentorActivity activity;
    private ParentAddMentorModel model;
    private ICallBack callBack;

    public ParentAddMentorPresenter(ParentAddSponsorOrMentorActivity activity) {
        this.activity = activity;
        this.model = new ParentAddMentorModel(activity);
        this.callBack = this;
    }

    ////////////       IPresenter       ////////////
    public void onStartPresenter(String type) {
        model.LoadMentorOrSponsorList(callBack, type);
    }

    @Override
    public void onStartPresenter() {

    }

    @Override
    public void onDestroyView() {
        if (activity != null) {
            activity = null;
        }
        if (model != null) {
            model = null;
        }
        if (callBack != null) {
            callBack = null;
        }

    }

    public List<ModelFamilyGroup> getChildList() {
        return model.getList();
    }

    ////////////       ICallBack       ////////////
    @Override
    public void onResultMentorSponsorList(ArrayList<ModelMentorSponsorRoom> mentorList) {
        activity.parentAddMentorFragmentAdapter.updateData(mentorList);
    }
}
