package kz.tech.smartgrades.root.tools;

import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class GetColorInText {
    public static int getIntColor(String textColor) {
        int color = 0;
        switch (textColor) {
            case "WHITE": color = ColorsRGB.WHITE; break;
            case "BLACK": color = ColorsRGB.BLACK; break;
            case "GRAY_THREE": color = ColorsRGB.GRAY_THREE; break;
            case "RED_ONE": color = ColorsRGB.RED_ONE; break;
            case "BLUE_ONE": color = ColorsRGB.BLUE_ONE; break;
            case "GRAY_FOUR": color = ColorsRGB.GRAY_FOUR; break;
            case "GRAY_TWO": color = ColorsRGB.GRAY_TWO; break;
            case "GRAY_FIVE": color = ColorsRGB.GRAY_FIVE; break;
            case "GREEN_ONE": color = ColorsRGB.GREEN_ONE; break;
            case "GRAY_ONE": color = ColorsRGB.GRAY_ONE; break;
        }

        return color;
    }
}
