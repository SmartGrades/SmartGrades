package kz.tech.smartgrades.school.adaptes;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.school.fragments.SchoolEditClassesFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditStudentClassesFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditStudentProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditTeacherProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolStudentsFragment;
import kz.tech.smartgrades.school.fragments.SchoolTeachersFragment;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelStudentProfile;
import kz.tech.smartgrades.school.models.ModelStudentProfileClasses;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;

public class SchoolStudentsFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private SchoolStudentsFragment studentsFragment;
    private SchoolEditStudentProfileFragment editStudentProfileFragment;
    private SchoolEditStudentClassesFragment editStudentClassesFragment;


    public SchoolStudentsFragmentAdapter(FragmentManager fm, ModelSchoolData mSchoolData){
        super(fm);
        studentsFragment = new SchoolStudentsFragment();
        editStudentProfileFragment = new SchoolEditStudentProfileFragment();
        editStudentClassesFragment = new SchoolEditStudentClassesFragment();

        fragmentList.add(studentsFragment);
        fragmentList.add(editStudentProfileFragment);
        fragmentList.add(editStudentClassesFragment);

        if(studentsFragment != null) studentsFragment.updateData(mSchoolData);
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
        if(studentsFragment != null) studentsFragment.updateData(mSchoolData);
    }

    public void setStudentData(ModelSchoolStudent mProfile) {
        if (editStudentProfileFragment != null) editStudentProfileFragment.setData(mProfile);
    }

    public void setClasses(ArrayList<ModelStudentProfileClasses> classesList, ModelStudentProfile mStudent) {
        if (editStudentClassesFragment != null) editStudentClassesFragment.setClasses(classesList, mStudent);
    }

    public void prepareAvatar(Uri resultUri) {
        if (editStudentProfileFragment != null) editStudentProfileFragment.prepareAvatar(resultUri);
    }

    public void loadStudentModel() {
        if (editStudentProfileFragment != null) editStudentProfileFragment.loadModel();
    }
}
