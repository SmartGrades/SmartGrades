package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;

import androidx.drawerlayout.widget.DrawerLayout;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;

public class CustomDrawerLayout {
    public DrawerLayout onCustom(Context context, String[] paramsList) {
        DrawerLayout drawerLayout = new DrawerLayout(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "FrmLay": drawerLayout.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                      //  case "DrwLay": drawerLayout.setLayoutParams(new kz.tech.esparta.root.ui.CustomLayoutParams.DrawerLayoutParams().getParams(context, paramsList)); break;
                        //case "Toolbar": relativeLayout.setLayoutParams(new ToolbarParams().getParams(context, array)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "backC"://  Background Color
                    drawerLayout.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":
                    //    button.setTextColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1]));
                    break;
            }
        }
        drawerLayout.setFitsSystemWindows(true);



        return drawerLayout;
    }
}
