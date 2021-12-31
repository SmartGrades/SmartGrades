package kz.tech.smartgrades.mentor.adapters;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kz.tech.smartgrades.mentor.fragments.MentorStudentsPageTabFragment;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorStudentsTabPagerFragmentAdapter extends FragmentStatePagerAdapter{

    private int PageCount = 0;
    private String PageTitle[];
    private MentorStudentsPageTabFragment[] PageFragment;

    private ModelMentorData mMentorData;


    public MentorStudentsTabPagerFragmentAdapter(FragmentManager fm, Context context, ModelMentorData mMentorData){
        super(fm);
        this.mMentorData = mMentorData;

        if(!listIsNullOrEmpty(mMentorData.getSchools())){
            PageTitle = new String[mMentorData.getSchools().size() + 1];
            PageFragment = new MentorStudentsPageTabFragment[mMentorData.getSchools().size() + 1];

            PageTitle[PageCount] = "Добавленные";
            PageFragment[PageCount] = MentorStudentsPageTabFragment.newInstance(PageCount);
            PageFragment[PageCount].onSetMentorData(mMentorData);
            PageCount++;

//            for(ModelSchoolData _school : mMentorData.getSchools()){
//                PageFragment[PageCount] = MentorStudentsPageTabFragment.newInstance(PageCount);
//                PageFragment[PageCount].onSetSchoolData(_school);
//                PageTitle[PageCount] = _school.getName();
//                PageCount++;
//            }
        }
        else {
            PageTitle = new String[1];
            PageFragment = new MentorStudentsPageTabFragment[1];

            PageTitle[PageCount] = "Добавленные";
            PageFragment[PageCount] = MentorStudentsPageTabFragment.newInstance(PageCount);
            PageFragment[PageCount].onSetMentorData(mMentorData);
        }
    }

    @Override
    public int getCount(){
        return PageCount;
    }

    @Override
    public Fragment getItem(int position){
        return PageFragment[position];
    }

    @Override
    public CharSequence getPageTitle(int position){
        return PageTitle[position];
    }

    public void updateDate(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;

    }
//    public void setData(ArrayList<ModelInterForm> requests){
//        if(pageTabFragment !=null && !listIsNullOrEmpty(requests))
//            pageTabFragment.onSetData(requests);
//    }
//    public void onSetModelMentorData(ModelMentorData mMentorData){
//        pageTabFragment.onSetModelMentorData(mMentorData);
//    }
}