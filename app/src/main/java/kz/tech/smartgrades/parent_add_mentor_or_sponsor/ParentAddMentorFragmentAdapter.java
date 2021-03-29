package kz.tech.smartgrades.parent_add_mentor_or_sponsor;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelUserList;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments.ParentChatAddMentorFragment;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments.ParentProfileMentor;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments.ParentProfileSponsor;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments.ParentSearchMentorOrSponsorFragment;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments.ParentWardMentor;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments.ParentWardSponsor;

public class ParentAddMentorFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentSearchMentorOrSponsorFragment searchFragment;
    private ParentProfileMentor profileMentor;
    private ParentProfileSponsor profileSponsor;
    private ParentWardMentor wardMentor;
    private ParentWardSponsor wardSponsor;
    private ParentChatAddMentorFragment chatFragment;

    ParentAddMentorFragmentAdapter(FragmentManager fm) {
        super(fm);
        searchFragment = new ParentSearchMentorOrSponsorFragment();
        profileMentor = new ParentProfileMentor();
        profileSponsor = new ParentProfileSponsor();
        wardMentor = new ParentWardMentor();
        wardSponsor = new ParentWardSponsor();
        chatFragment = new ParentChatAddMentorFragment();

        fragmentArrayList.add(searchFragment);
        fragmentArrayList.add(profileMentor);
        fragmentArrayList.add(profileSponsor);
        fragmentArrayList.add(wardMentor);
        fragmentArrayList.add(wardSponsor);
        fragmentArrayList.add(chatFragment);
    }


    public void setMentorProfileData(ModelMentorSponsorRoom model) {
        if (model != null) profileMentor.setProfileData(model);
    }

    public void setSponsorProfileData(ModelMentorSponsorRoom model) {
        if (model != null) profileSponsor.setProfileData(model);
    }

    public void setMentorWardData(ModelMentorSponsorRoom model) {
        if (model != null) wardMentor.setWardData(model);
    }

    public void setSponsorWardData(ModelMentorSponsorRoom model) {
        if (model != null) wardSponsor.setWardData(model);
    }

    public void updateData(ArrayList<ModelMentorSponsorRoom> userList) {
        if (searchFragment.getUsersListAdapter() != null) searchFragment.getUsersListAdapter().updateData(userList);
        else System.out.println("Нет адаптера" + searchFragment.getUsersListAdapter());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }


    public void setMentorChatData(ModelMentorSponsorRoom value) {
        chatFragment.setWardData(value);
    }

    public void setSponsorChatData(ModelMentorSponsorRoom value) {
        chatFragment.setWardData(value);
    }
}
