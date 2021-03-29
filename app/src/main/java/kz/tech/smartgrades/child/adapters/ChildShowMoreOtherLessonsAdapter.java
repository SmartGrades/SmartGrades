package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.fragments.ChildAllOtherLessonFragment;
import kz.tech.smartgrades.child.fragments.ChildChatFragment;
import kz.tech.smartgrades.child.fragments.ChildOtherLessonInfoFragment;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;

public class ChildShowMoreOtherLessonsAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildAllOtherLessonFragment childAllOtherLessonFragment;
    private ChildOtherLessonInfoFragment childOtherLessonInfoFragment;
    private ChildChatFragment childChatFragment;

    public ChildShowMoreOtherLessonsAdapter(FragmentManager fm) {
        super(fm);
        childAllOtherLessonFragment = new ChildAllOtherLessonFragment();
        childOtherLessonInfoFragment = new ChildOtherLessonInfoFragment();
        childChatFragment = new ChildChatFragment();

        fragmentArrayList.add(childAllOtherLessonFragment);
        fragmentArrayList.add(childOtherLessonInfoFragment);
        fragmentArrayList.add(childChatFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setLessonList(ArrayList<ModelLessonsWithOutSmartGrades> lessonsWithOutSmartGrades) {
        childAllOtherLessonFragment.setLessonList(lessonsWithOutSmartGrades);
    }

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades) {
        if (childOtherLessonInfoFragment != null) {
            childOtherLessonInfoFragment.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades);
        }
    }

    public void setLessonData(String lessonName, String lessonId) {
        childChatFragment.setData(lessonName, lessonId);
    }
}
