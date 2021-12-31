package kz.tech.smartgrades.mentor.adapters;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.mentor.fragments.MentorPageTab1Fragment;
import kz.tech.smartgrades.mentor.fragments.MentorPageTab2Fragment;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorTabPagerFragmentAdapter extends FragmentPagerAdapter{
    private int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Запросы", "Сообщения"};
    private ModelMentorData mMentorData;
    private MentorPageTab1Fragment pageTab1Fragment;
    private MentorPageTab2Fragment pageTab2Fragment;

    public MentorTabPagerFragmentAdapter(FragmentManager fm, Context context, boolean isEnable){
        super(fm);
        pageTab1Fragment = MentorPageTab1Fragment.newInstance(1);
        pageTab2Fragment = MentorPageTab2Fragment.newInstance(2);
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0) return pageTab1Fragment;
        else return pageTab2Fragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    public void updateDate(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        /*if(pageTab3Fragment != null){
            pageTab3Fragment.updateData(mSchoolData.getRequests());

        }*/

    }
    public void setData(ArrayList<ModelInterForm> InterForms){
        if(pageTab1Fragment!=null && !listIsNullOrEmpty(InterForms))
            pageTab1Fragment.onSetData(InterForms);
    }
    public void onSetModelMentorData(ModelMentorData mMentorData){
        pageTab1Fragment.onSetMentorData(mMentorData);
        pageTab2Fragment.onSetMentorData(mMentorData);
    }
}