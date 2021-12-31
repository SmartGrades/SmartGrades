package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.fragments.ParentAddMentorListFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddMentorProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentChatFragment;
import kz.tech.smartgrades.parent.fragments.ParentChildLessonInfoFragment;
import kz.tech.smartgrades.parent.fragments.ParentSchoolProfile;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.parent.model.ModelMentorList;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolInfo;

public class ParentShowOtherLessonsFromActivityAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentChildLessonInfoFragment parentChildLessonInfoFragment;
    private ParentChatFragment parentChatFragment;

    private ParentAddMentorListFragment parentAddMentorListFragment;
    private ParentAddMentorProfileFragment parentAddMentorProfileFragment;

    private ParentSchoolProfile parentSchoolProfile;

    public ParentShowOtherLessonsFromActivityAdapter(FragmentManager fm) {
        super(fm);
        parentChildLessonInfoFragment = new ParentChildLessonInfoFragment();
        parentChatFragment = new ParentChatFragment();

        parentAddMentorListFragment = new ParentAddMentorListFragment();
        parentAddMentorProfileFragment = new ParentAddMentorProfileFragment();

        parentSchoolProfile = new ParentSchoolProfile();

        fragmentArrayList.add(parentChildLessonInfoFragment);
        fragmentArrayList.add(parentChatFragment);

        fragmentArrayList.add(parentAddMentorListFragment);
        fragmentArrayList.add(parentAddMentorProfileFragment);

        fragmentArrayList.add(parentSchoolProfile);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades, ModelChildData mChild) {
        if (parentChildLessonInfoFragment != null) {
            parentChildLessonInfoFragment.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades, mChild);
        }
    }
    public void setLessonData(String lessonName, String lessonId) {
        parentChatFragment.setData(lessonName, lessonId);
    }

    public void onRemoveMentor(ModelMentorList mMentor) {
        parentChildLessonInfoFragment.onRemoveMentor(mMentor);
    }

    public void openProveWindow(ModelMentorList mMentor) {
        parentChildLessonInfoFragment.openProveWindow(mMentor);
    }

    public void onRemoveLessonProve() {
        parentChildLessonInfoFragment.onRemoveLessonProve();
    }

    public void loadMentorList(String lessonId, String id) {
        parentAddMentorListFragment.loadMentorList(lessonId, id);
    }

    public void setMentorModel2(kz.tech.smartgrades.mentor.models.ModelMentorList mMentor, String lessonId, String parentId, String childId, String id) {
        parentAddMentorProfileFragment.setMentorModel2(mMentor, lessonId, parentId, childId, id);
    }

    public void setNewMentor(kz.tech.smartgrades.mentor.models.ModelMentorList mMentor) {
        parentChildLessonInfoFragment.setNewMentor(mMentor);
    }

    public void setSchoolModel(ModelSchoolInfo mSchool) {
        parentSchoolProfile.setSchoolModel(mSchool);
    }
}
