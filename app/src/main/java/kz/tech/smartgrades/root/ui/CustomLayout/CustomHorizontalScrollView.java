package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;

import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.NavigationViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ToolbarParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomHorizontalScrollView {
    public HorizontalScrollView onCustom(Context context, String[] paramsList) {
        HorizontalScrollView hsv = new HorizontalScrollView(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "FrmLay": hsv.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                        case "LinLay": hsv.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "NavLay": hsv.setLayoutParams(new NavigationViewParams().getParams(context, paramsList)); break;
                        case "RelLay": hsv.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "Toolbar": hsv.setLayoutParams(new ToolbarParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "VIS"://  Visibility
                    switch (data[1]) {//  VALUE
                        case "GONE"://  GONE
                            hsv.setVisibility(View.GONE);
                            break;
                        case "VISIBLE"://  GONE
                            hsv.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case "FillView":
                    switch (data[1]) {
                        case "TRUE":
                            hsv.setFillViewport(true);
                            break;
                        case "FALSE":
                            hsv.setFillViewport(false);
                            break;
                    }
                    break;
                case "backC"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                        case "WHITE"://  WHITE
                            hsv.setBackgroundColor(ColorsRGB.WHITE);
                            break;
                    }
                    break;
            }
        }



        return hsv;
    }
}
