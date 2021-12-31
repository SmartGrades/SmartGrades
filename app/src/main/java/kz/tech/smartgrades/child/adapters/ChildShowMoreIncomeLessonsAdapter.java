package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.fragments.ChildAllSmartGradesFragment;
import kz.tech.smartgrades.child.fragments.ChildChatFragment;
import kz.tech.smartgrades.child.fragments.ChildSmartLessonInfoFragment;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;

public class ChildShowMoreIncomeLessonsAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildAllSmartGradesFragment childAllSmartGradesFragment;
    private ChildSmartLessonInfoFragment childSmartLessonInfoFragment;
    private ChildChatFragment childChatFragment;

    public ChildShowMoreIncomeLessonsAdapter(FragmentManager fm) {
        super(fm);
        childAllSmartGradesFragment = new ChildAllSmartGradesFragment();
        childSmartLessonInfoFragment = new ChildSmartLessonInfoFragment();
        childChatFragment = new ChildChatFragment();

        fragmentArrayList.add(childAllSmartGradesFragment);
        fragmentArrayList.add(childSmartLessonInfoFragment);
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

    public void setLessonList(ArrayList<ModelLessonsWithSmartGrades> lessonsWithSmartGrades) {
        childAllSmartGradesFragment.setLessonList(lessonsWithSmartGrades);
    }

    public void setLessonsWithSmartGradesData(ModelLessonsWithSmartGrades mLessonsWithSmartGrades) {
        if (childSmartLessonInfoFragment != null) {
            childSmartLessonInfoFragment.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
        }
    }

    public void setLessonData(String lessonName, String lessonId) {
        childChatFragment.setData(lessonName, lessonId);
    }
}
