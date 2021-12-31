package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.DrawerLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ViewPagerParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomFrameLayout {
    public FrameLayout onCustom(Context c, String[] param) {
        FrameLayout fl = new FrameLayout(c);
        for (int i = 0; param.length > i; i++) {//  LayoutParams
            String[] data = param[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": fl.setLayoutParams(new LinearLayoutParams().getParams(c, param)); break;
                        case "DrwLay": fl.setLayoutParams(new DrawerLayoutParams().getParams(c, param)); break;
                        case "RelLay": fl.setLayoutParams(new RelativeLayoutParams().getParams(c, param)); break;
                        case "ViewPager": fl.setLayoutParams(new ViewPagerParams().getParams(c, param)); break;
                        case "FrmLay": fl.setLayoutParams(new FrameLayoutParams().getParams(c, param)); break;
                    }
                    break;
            }
        }
        for (int j = 0; param.length > j; j++) {//  TextView
            String[] data = param[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "backC"://  Background Color
                    fl.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "Alpha":
                    switch (data[1]) {
                        case "0.5": fl.setAlpha(0.5f); break;
                    }
                    break;
                case "VIS"://  Visibility
                    switch (data[1]) {//  VALUE
                        case "GONE"://  GONE
                            fl.setVisibility(View.GONE);
                            break;
                        case "VISIBLE"://  GONE
                            fl.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case "ID"://  Circle Background Color
                    fl.setId((int)Integer.parseInt(data[1]));
                    break;
                case "backRR"://  RippleDrawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fl.setBackgroundDrawable(new GetButtonRipple()
                                .getRipple(0f, 0, ColorsRGB.GRAY_THREE,
                                        GetColorInText.getIntColor(data[1]),//  First Color
                                        ColorsRGB.BLUE_ONE));//  Second Color
                    } else {
                        fl.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    }
                    break;
            }
        }
        return fl;
    }
}
