package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.NavigationViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ToolbarParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomScrollView {
    public ScrollView onCustom(Context context, String[] paramsList) {
        ScrollView sv = new ScrollView(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "FrmLay": sv.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                        case "LinLay": sv.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "NavLay": sv.setLayoutParams(new NavigationViewParams().getParams(context, paramsList)); break;
                        case "RelLay": sv.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "Toolbar": sv.setLayoutParams(new ToolbarParams().getParams(context, paramsList)); break;
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
                            sv.setVisibility(View.GONE);
                            break;
                        case "VISIBLE"://  GONE
                            sv.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case "FillView":
                    switch (data[1]) {
                        case "TRUE":
                            sv.setFillViewport(true);
                            break;
                        case "FALSE":
                            sv.setFillViewport(false);
                            break;
                    }
                    break;
                case "backC"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                        case "WHITE"://  WHITE
                            sv.setBackgroundColor(ColorsRGB.WHITE);
                            break;
                    }
                    break;
            }
        }

        return sv;
    }
}
