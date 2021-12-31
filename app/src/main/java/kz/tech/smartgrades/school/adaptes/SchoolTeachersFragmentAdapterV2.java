package kz.tech.smartgrades.school.adaptes;

import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.school.fragments.SchoolEditClassesFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditTeacherProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolMoveTeachersListFragment;
import kz.tech.smartgrades.school.fragments.SchoolTeachersFragment;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;

public class SchoolTeachersFragmentAdapterV2 extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private SchoolMoveTeachersListFragment moveTeacherListFragment;
    private SchoolEditTeacherProfileFragment editTeacherProfileFragment;
    private SchoolEditClassesFragment editClassesFragment;


    public SchoolTeachersFragmentAdapterV2(FragmentManager fm, ModelSchoolData mSchoolData, ModelSchoolClass mSchoolClass){
        super(fm);
        moveTeacherListFragment = new SchoolMoveTeachersListFragment();
        editTeacherProfileFragment = new SchoolEditTeacherProfileFragment();
        editClassesFragment = new SchoolEditClassesFragment();

        fragmentList.add(moveTeacherListFragment);
        fragmentList.add(editTeacherProfileFragment);
        fragmentList.add(editClassesFragment);

        if(moveTeacherListFragment != null) moveTeacherListFragment.setClassData(mSchoolData, mSchoolClass);
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

    public void updateClassData(ModelSchoolData mSchoolData) {
        if(moveTeacherListFragment != null) moveTeacherListFragment.updateClassData(mSchoolData);
    }

    public void setTeacherData(ModelSchoolTeacher schoolTeacher) {
        if (editTeacherProfileFragment != null) editTeacherProfileFragment.setData(schoolTeacher);
    }

    public void setClasses(ArrayList<ModelTeacherProfileClasses> classesList, ModelTeacherProfile mTeacher) {
        if (editClassesFragment != null) editClassesFragment.setClasses(classesList, mTeacher);
    }

    public void loadTeacherModel() {
        if (editTeacherProfileFragment != null) editTeacherProfileFragment.loadModel();
    }

    public void prepareAvatar(Uri resultUri) {
        if (editTeacherProfileFragment != null) editTeacherProfileFragment.prepareAvatar(resultUri);
    }
}
