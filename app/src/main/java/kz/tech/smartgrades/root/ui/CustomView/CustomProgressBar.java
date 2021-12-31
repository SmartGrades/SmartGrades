package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.widget.ProgressBar;

import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;

public class CustomProgressBar {
    public ProgressBar onCustom(Context context, String[] paramsList) {
        ProgressBar progressBar = new ProgressBar(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                       // case "LinLay": progressBar.setLayoutParams(new kz.tech.esparta.root.ui.CustomLayoutParams.LinearLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": progressBar.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
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
                  //  progressBar.setBackgroundColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":

                    break;
            }
        }

        return progressBar;
    }
}
