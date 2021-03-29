package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;
import android.widget.RelativeLayout;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.CardViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ScrollViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ToolbarParams;

public class CustomRelativeLayout {
    public RelativeLayout onCustom(Context context, String[] paramsList) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": relativeLayout.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "ScrLay": relativeLayout.setLayoutParams(new ScrollViewParams().getParams(context, paramsList)); break;
                        case "Toolbar": relativeLayout.setLayoutParams(new ToolbarParams().getParams(context, paramsList)); break;
                        case "RelLay": relativeLayout.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "CardView": relativeLayout.setLayoutParams(new CardViewParams().getParams(context, paramsList)); break;
                        case "FrmLay": relativeLayout.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "backC"://  Background Color
                       // case "0": editText.setBackgroundColor(0); break;
                        // case "WHITE": relativeLayout.setBackgroundColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1])); break;
                        relativeLayout.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    ;
                  //  frameLayout.setBackgroundColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1]));
                    break;
                case "ID"://  400001
                    relativeLayout.setId((int)Integer.parseInt(data[1]));
                    break;
                case "txtC":
                    //    button.setTextColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1]));
                    break;

            }
        }




        return relativeLayout;
    }
}
