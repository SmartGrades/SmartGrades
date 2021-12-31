package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.view.View;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;

public class CustomView {
    public View onCustom(Context context, String[] paramsList) {
        View v = new View(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": v.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": v.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": v.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
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
                    v.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":

                    break;
                case "VIS"://  Visibility
                    switch (data[1]) {//  VALUE
                        case "GONE"://  GONE
                            v.setVisibility(View.GONE);
                            break;
                        case "VISIBLE"://  GONE
                            v.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case "ID":
                    v.setId((int)Integer.parseInt(data[1]));
                    break;
            }
        }


        return v;
    }
}
