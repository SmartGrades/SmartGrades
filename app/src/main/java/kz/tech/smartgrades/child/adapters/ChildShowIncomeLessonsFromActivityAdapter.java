package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.fragments.ChildChatFragment;
import kz.tech.smartgrades.child.fragments.ChildSmartLessonInfoFragment;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;

public class ChildShowIncomeLessonsFromActivityAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildSmartLessonInfoFragment childSmartLessonInfoFragment;
    private ChildChatFragment childChatFragment;

    public ChildShowIncomeLessonsFromActivityAdapter(FragmentManager fm) {
        super(fm);
        childSmartLessonInfoFragment = new ChildSmartLessonInfoFragment();
        childChatFragment = new ChildChatFragment();

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

    public void setLessonsWithSmartGradesData(ModelLessonsWithSmartGrades mLessonsWithSmartGrades) {
        if (childSmartLessonInfoFragment != null) {
            childSmartLessonInfoFragment.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
        }
    }

    public void setLessonData(String lessonName, String lessonId) {
        childChatFragment.setData(lessonName, lessonId);
    }
}
