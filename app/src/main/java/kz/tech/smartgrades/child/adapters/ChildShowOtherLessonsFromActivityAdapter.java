package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.fragments.ChildChatFragment;
import kz.tech.smartgrades.child.fragments.ChildOtherLessonInfoFragment;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;

public class ChildShowOtherLessonsFromActivityAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildOtherLessonInfoFragment childOtherLessonInfoFragment;
    private ChildChatFragment childChatFragment;

    public ChildShowOtherLessonsFromActivityAdapter(FragmentManager fm) {
        super(fm);
        childOtherLessonInfoFragment = new ChildOtherLessonInfoFragment();
        childChatFragment = new ChildChatFragment();

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

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades) {
        if (childOtherLessonInfoFragment != null) {
            childOtherLessonInfoFragment.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades);
        }
    }

    public void setLessonData(String lessonName, String lessonId) {
        childChatFragment.setData(lessonName, lessonId);
    }
}
