package kz.tech.smartgrades.mentor.mvp;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;

public interface ICallBack {
    void onResultMentorWorksheet(boolean isEmpty);
    void ResultAddMentorWorksheet(boolean isDone);
    void onLoadMentorGroups(ArrayList<ModelMentorGroups> list);

    void onResultLoadMentorData(ModelMentorData modelMentorData);
    void ResultSendMessageToGroup(boolean success);
    void onResultMentorAddLesson(ModelAnswer answer);
}
