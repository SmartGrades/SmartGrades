package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.widget.Switch;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;

public class CustomSwitch {
    public Switch onCustom(Context context, String[] paramsList) {
        Switch s = new Switch(context);

        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "FrmLay": s.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                        case "LinLay": s.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": s.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
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
                    s.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":

                    break;
            }
        }



        return s;
    }
}
