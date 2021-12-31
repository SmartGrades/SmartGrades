package kz.tech.smartgrades.mentor.mvp;

import kz.tech.smartgrades.school.models.ModelMessageToGroup;

public interface IPresenter {
    void onStartPresenter();
    void onDestroyView();

    void onSendMessageToGroup(ModelMessageToGroup model);
}
