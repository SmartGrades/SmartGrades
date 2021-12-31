package kz.tech.smartgrades.mentor.mvp;

import kz.tech.smartgrades.school.models.ModelMessageToGroup;

public interface IModel {
    void onCheckMentorWorksheet(ICallBack callBack);
    void onSendMessageToGroup(ICallBack callBack, ModelMessageToGroup model);
}
