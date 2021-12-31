package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;

public class CustomViewPager {
    public ViewPager onCustom(Context context, String[] paramsList) {
        ViewPager viewPager = new ViewPager(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "RelLay": viewPager.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": viewPager.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "pad"://  gravity

                    break;
                case "backC"://  RippleDrawable
                    viewPager.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":

                    break;
            }
        }


        return viewPager;
    }
}
