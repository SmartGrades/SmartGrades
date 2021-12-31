package kz.tech.smartgrades.school.adaptes;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import kz.tech.smartgrades.school.fragments.SchoolPageTab1Fragment;
import kz.tech.smartgrades.school.fragments.SchoolPageTab2Fragment;
import kz.tech.smartgrades.school.fragments.SchoolPageTab3Fragment;
import kz.tech.smartgrades.school.models.ModelSchoolData;

public class TabPagerFragmentAdapter extends FragmentPagerAdapter{

    private int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Жалобы", "Предложения", "Запросы"};
    private String tabTitles2[] = new String[]{"Запросы", "Жалобы и Предложения"};

    private Context context;
    private ModelSchoolData mSchoolData;
    private SchoolPageTab1Fragment pageTab1Fragment;
    private SchoolPageTab2Fragment pageTab2Fragment;
    private SchoolPageTab3Fragment pageTab3Fragment;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context, boolean isEnable){
        super(fm);
        this.context = context;
        pageTab1Fragment = SchoolPageTab1Fragment.newInstance(1);
        pageTab2Fragment = SchoolPageTab2Fragment.newInstance(3);
        pageTab3Fragment = SchoolPageTab3Fragment.newInstance(2);
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        if(PAGE_COUNT < 3){
            if(position == 0) return pageTab3Fragment;
            if(position == 1) return pageTab1Fragment;
            return pageTab1Fragment;
        }
        else{
            if(position == 0) return pageTab3Fragment;
            return pageTab1Fragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(PAGE_COUNT == 3) return tabTitles[position];
        else return tabTitles2[position];
    }

    public void updateDate(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(pageTab3Fragment != null) pageTab3Fragment.updateData(mSchoolData.getInterForms());
        if(mSchoolData.isComplaint()){
            PAGE_COUNT = 3;
            notifyDataSetChanged();
        }
    }
}