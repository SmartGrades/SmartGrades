package kz.tech.smartgrades.school.adaptes;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.school.fragments.SchoolClassFragment;
import kz.tech.smartgrades.school.fragments.SchoolClassessFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditClassesFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditTeacherProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolTeachersFragment;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;

public class SchoolTeachersFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private SchoolTeachersFragment teachersFragment;
    private SchoolEditTeacherProfileFragment editTeacherProfileFragment;
    private SchoolEditClassesFragment editClassesFragment;


    public SchoolTeachersFragmentAdapter(FragmentManager fm, ModelSchoolData mSchoolData){
        super(fm);
        teachersFragment = new SchoolTeachersFragment();
        editTeacherProfileFragment = new SchoolEditTeacherProfileFragment();
        editClassesFragment = new SchoolEditClassesFragment();

        fragmentList.add(teachersFragment);
        fragmentList.add(editTeacherProfileFragment);
        fragmentList.add(editClassesFragment);

        if(teachersFragment != null) teachersFragment.updateData(mSchoolData);
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

    public void updateData(ModelSchoolData mSchoolData) {
        if(teachersFragment != null) teachersFragment.updateData(mSchoolData);
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
