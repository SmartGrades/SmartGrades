package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;

import androidx.cardview.widget.CardView;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.var_resources.DimensionDP;


public class CustomCardView {
    public CardView onCustom(Context context, String[] paramsList) {
        CardView cardView = new CardView(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": cardView.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": cardView.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": cardView.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "pad"://  gravity
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), context);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), context);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), context);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), context);
                    cardView.setPadding(left, top, right, bottom);
                    break;
                case "backC"://  RippleDrawable
                    cardView.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "Radius":
                    cardView.setRadius(DimensionDP.sizeDP(Integer.parseInt(data[1]), context));
                    break;
                case "ID":
                    cardView.setId((int)Integer.parseInt(data[1]));
                    break;
            }
        }


        return cardView;
    }
}
