package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.fragments.ParentAddLessonFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddLessonMentorProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentMentorListFragment;
import kz.tech.smartgrades.parent.model.ModelParentData;

public class ParentAddLessonFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentAddLessonFragment parentAddLessonFragment;
    private ParentMentorListFragment parentMentorListFragment;
    private ParentAddLessonMentorProfileFragment parentAddLessonMentorProfileFragment;

    public ParentAddLessonFragmentAdapter(FragmentManager fm) {
        super(fm);
        parentAddLessonFragment = new ParentAddLessonFragment();
        parentMentorListFragment = new ParentMentorListFragment();
        parentAddLessonMentorProfileFragment = new ParentAddLessonMentorProfileFragment();

        fragmentArrayList.add(parentAddLessonFragment);
        fragmentArrayList.add(parentMentorListFragment);
        fragmentArrayList.add(parentAddLessonMentorProfileFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setChildData(ModelChildData mChildData){
        if(parentAddLessonFragment != null)
            parentAddLessonFragment.setData(mChildData);
    }

    public void setMentorList(ArrayList<ModelMentorList> mentorList) {
        parentMentorListFragment.setMentorList(mentorList);
    }

    public void setMentorModel(ModelMentorList mMentor) {
        parentAddLessonMentorProfileFragment.setMentorModel(mMentor);
    }

    public void setSelectedMentor(ModelMentorList mMentor) {
        parentMentorListFragment.setSelectedMentor(mMentor);
    }

    public void checkToSelected() {
        parentMentorListFragment.setMentorListAdapter();
    }

    public void onDeleteMentorFromSelectedList(int p) {
        if(parentMentorListFragment!=null){
            parentMentorListFragment.onDeleteMentorFromSelectedList(p);
        }
    }

    public void addMentors(ArrayList<ModelMentorList> selectedMentorList) {
        parentAddLessonFragment.setMentors(selectedMentorList);
    }

    public void setParentModel(ModelParentData mParent) {
        parentAddLessonFragment.setParentModel(mParent);
    }

    public void setInterFormId(String interFormId) {
        parentAddLessonFragment.setInterFormId(interFormId);
    }
}
