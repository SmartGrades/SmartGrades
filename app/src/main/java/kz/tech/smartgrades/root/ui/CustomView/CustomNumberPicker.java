package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;

public class CustomNumberPicker {
    public NumberPicker onCustom(Context context, String[] paramsList) {
        NumberPicker np = new NumberPicker(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": np.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": np.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": np.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
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
                    np.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "Wrap"://  Wrap Selector Wheel
                    switch (data[1]) {//  VALUE
                        case "false":
                            np.setWrapSelectorWheel(false);
                            break;
                    }
                    break;
                case "MinValue":
                    np.setMinValue(Integer.parseInt(data[1]));
                    break;
                case "MaxValue":
                    np.setMaxValue(Integer.parseInt(data[1]));
                    break;
                case "Value":
                    np.setValue(Integer.parseInt(data[1]));
                    break;
                case "Focus":
                    switch (data[1]) {//  VALUE
                        case "block":
                            np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                            break;
                    }
                    break;
                case "DisplayValue":
                    switch (data[1]) {//  VALUE
                        case "First":
                            final String[] values1 = new String[]{"-10", "-9", "-8", "-7", "-6", "-5", "-4","-3","-2","-1", "0",
                                    "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10", "+11", "+12", "+13", "+14", "+15"};
                            np.setDisplayedValues(values1);
                            break;
                        case "Second":
                            final String[] values2 = new String[]{"0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10",
                                    "+11", "+12", "+13", "+14", "+15", "+16", "+17", "+18", "+19", "+20",
                                    "+21", "+22", "+23", "+24", "+25", "+26", "+27", "+28", "+29", "+30"};
                            np.setDisplayedValues(values2);
                            break;
                    }
                    break;
            }
        }

      //  setSelectedTextColor(np, context);
        return np;

    }
    private void setSelectedTextColor(NumberPicker np, Context c) {
        int selectedColorCode = Color.rgb(0, 137, 208);
        int defaultColor = Color.rgb(152, 152, 152);
        final ColorStateList colors = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{-android.R.attr.state_selected}
                },
                new int[]{selectedColorCode, defaultColor}
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            final int count = np.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = np.getChildAt(i);
                if (child instanceof EditText) {
                    try {
                        ((EditText) child).setTextColor(colors);
                        np.invalidate();

                        Field selectorWheelPaintField = np.getClass().getDeclaredField("mSelectorWheelPaint");
                        boolean accessible = selectorWheelPaintField.isAccessible();
                        selectorWheelPaintField.setAccessible(true);
                    //    ((Paint) selectorWheelPaintField.get(np)).setColor(defaultColor);
                        selectorWheelPaintField.setAccessible(accessible);
                        np.invalidate();

                        Field selectionDividerField = np.getClass().getDeclaredField("mSelectionDivider");
                        accessible = selectionDividerField.isAccessible();
                        selectionDividerField.setAccessible(true);
                        selectionDividerField.set(np, null);
                        selectionDividerField.setAccessible(accessible);
                        np.invalidate();
                    } catch (Exception exception) {
                      //  Logger.exc(exception);
                    }
                }
            }
        } else {
            np.setTextColor(defaultColor);
        }
    }
}
/*
 np.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
 */