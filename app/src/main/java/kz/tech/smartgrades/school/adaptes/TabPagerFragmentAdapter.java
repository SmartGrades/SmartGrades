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
    private int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Жалобы", "Предложения", "Запросы"};
    private String tabTitles2[] = new String[]{"Жалобы и Предложения", "Запросы"};
    private Context context;
    private boolean isEnable;
    private ModelSchoolData mSchoolData;
    private SchoolPageTab1Fragment pageTab1Fragment;
    private SchoolPageTab2Fragment pageTab2Fragment;
    private SchoolPageTab3Fragment pageTab3Fragment;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context, boolean isEnable){
        super(fm);
        this.context = context;
        this.isEnable = isEnable;
        if(isEnable) PAGE_COUNT = 3;
        else PAGE_COUNT = 2;
        pageTab1Fragment = SchoolPageTab1Fragment.newInstance(1);
        pageTab2Fragment = SchoolPageTab2Fragment.newInstance(2);
        pageTab3Fragment = SchoolPageTab3Fragment.newInstance(3);
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        if(isEnable){
            if(position == 0) return pageTab1Fragment;
            if(position == 1) return pageTab2Fragment;
            return pageTab3Fragment;
        }
        else{
            if(position == 0) return pageTab1Fragment;
            return pageTab3Fragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(isEnable) return tabTitles[position];
        else return tabTitles2[position];
    }

    public void updateDate(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(pageTab3Fragment != null){
            pageTab3Fragment.updateData(mSchoolData.getRequests());

        }

    }
}