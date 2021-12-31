package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.widget.Toolbar;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ToolbarParams;
import kz.tech.smartgrades.root.var_resources.DimensionDP;

public class CustomToolbar {
    public Toolbar onCustom(Context context, String[] paramsList) {
        Toolbar toolbar = new Toolbar(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "RelLay": toolbar.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "Toolbar": toolbar.setLayoutParams(new ToolbarParams().getParams(context, paramsList)); break;
                        case "LinLay": toolbar.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": toolbar.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "pad"://  gravity

                    break;
                case "backC":// Background Color
                    switch (data[1]) {//  VALUE
                        case "0": toolbar.setBackgroundColor(0); break;
                        case "WHITE": toolbar.setBackgroundColor(GetColorInText.getIntColor(data[1])); break;
                    }
                   // view.setBackgroundColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1]));
                    break;
                case "txtC":

                    break;
            }
        }
        /*
        (AppCompatActivity) context).getDelegate().setSupportActionBar(toolbar);
            ((AppCompatActivity)context).getDelegate().getSupportActionBar().setDisplayShowTitleEnabled(false);
         */
        toolbar.setContentInsetsAbsolute(0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(DimensionDP.sizeDP(4, context));
        }

        return toolbar;
    }
}
