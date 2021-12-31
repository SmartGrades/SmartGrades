package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;

import com.google.android.material.tabs.TabLayout;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomTabLayout {
    public TabLayout onCustom(Context context, String[] paramsList, String[] tabText) {
        TabLayout tabLayout = new TabLayout(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": tabLayout.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": tabLayout.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": tabLayout.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int z = 0; z < tabText.length; z++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabText[z]));
        }
        for (int j = 0; paramsList.length > j; j++) {//
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "pad"://  gravity

                    break;
                case "backC"://
                    tabLayout.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":

                    break;
            }
        }

        tabLayout.setTabTextColors(ColorsRGB.BLACK, ColorsRGB.RED_ONE);//  Text color select and unselect
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        return tabLayout;
    }
}
