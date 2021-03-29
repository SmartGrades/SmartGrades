package kz.tech.smartgrades.sponsor.mvp;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form.ModelInteractionForm;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;

public interface ICallBack {
    void onCheckSponsorWorksheetResult(boolean isEmpty);
    void onAddSponsorWorksheetResult(boolean isDone);

    void onLoadDataResult(ModelSponsorData modelSponsorData);

    void ResultAddAboutMe(boolean b);
}
