package kz.tech.smartgrades.mentor;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.mentor.models.ModelMentorLesson;
import kz.tech.smartgrades.mentor.mvp.ICallBack;
import kz.tech.smartgrades.mentor.mvp.IPresenter;
import kz.tech.smartgrades.school.models.ModelMessageToGroup;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;

public class MentorPresenter implements IPresenter, ICallBack {

    private MentorActivity activity;
    private MentorModel mentorModel;
    private ICallBack callBack;

    public MentorPresenter(MentorActivity activity) {
        this.activity = activity;
        this.mentorModel = new MentorModel(activity);
        this.callBack = this;
    }

    @Override
    public void onStartPresenter() {
        mentorModel.onCheckMentorWorksheet(callBack);
        mentorModel.onLoadMentorData(callBack);
    }
    @Override
    public void onDestroyView() {
        if (mentorModel != null) mentorModel = null;
        if (activity != null) activity = null;
    }

    public void onUpdateMentorData() {
        mentorModel.onLoadMentorData(callBack);
    }
    public void getAddMentorWorksheet(String aboutMe, String keyWordsYourMentors) {
        mentorModel.onAddWorksheet(callBack, aboutMe, keyWordsYourMentors);
    }
    public void onLoadMentorGroup() {
        mentorModel.onLoadMentorGroups(callBack);
    }
    public void onAddMentorGroup(String GroupName, ModelSchoolLessons modelSchoolLessons) {
        mentorModel.onAddMentorGroup(callBack, GroupName, modelSchoolLessons);
    }
    public void onAddLesson(ModelMentorLesson model){
        mentorModel.onAddLesson(callBack, model);
    }

    ////////////       ICallBack       ////////////
    @Override
    public void onResultLoadMentorData(ModelMentorData modelMentorData) {
        /*activity.setMentorGroups(modelMentorData.getMentorGroups());
        onLoadMentorGroups(modelMentorData.getMentorGroups());
        activity.MentorGroupsForChildAddedAdapter.updateData(modelMentorData.getMentorGroups());

        activity.mentorUserListAdapter.updateData(modelMentorData);

        if (!listIsNullOrEmpty(modelMentorData.getMentorGroups()))
            activity.SelectMentorDefaultGroup(modelMentorData.getMentorGroups().get(0));
        */
        activity.onSetMentorData(modelMentorData);
    }
    @Override
    public void onResultMentorWorksheet(boolean isEmpty) {
        if (isEmpty) activity.onNextFragment(1, null);
    }
    @Override
    public void ResultAddMentorWorksheet(boolean isDone) {
        if (isDone) activity.onRemoveFragment();
    }
    public void onLoadMentorGroups(ArrayList<ModelMentorGroups> list) {
        //activity.MentorGroupsAdapter.updateData(list);
    }
    @Override
    public void onSendMessageToGroup(ModelMessageToGroup model){
        mentorModel.onSendMessageToGroup(callBack, model);
    }
    @Override
    public void ResultSendMessageToGroup(boolean success){

    }
    @Override
    public void onResultMentorAddLesson(ModelAnswer answer){
        if(answer.isSuccess()) mentorModel.onLoadMentorData(callBack);
    }
}