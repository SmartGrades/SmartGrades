package kz.tech.smartgrades.parent_add_mentor_or_sponsor.mvp;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelUserList;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;

public interface ICallBack {
    void onResultMentorSponsorList(ArrayList<ModelMentorSponsorRoom> mentorList);
}
