package view;

import java.awt.*;

public class ColorManager {
    private static final Color primaryColor = new Color(0x1976d2);
    private static final Color lightColor = Color.WHITE;
    private static final Color darkColor = Color.BLACK;
    private static final Color opaqueColor = new Color(0, 0, 0, 0);

    public static Color getPrimaryColor() {
        return primaryColor;
    }

    public static Color getLightColor() {
        return lightColor;
    }

    public static Color getDarkColor() {
        return darkColor;
    }

    public static Color getOpaqueColor() {
        return opaqueColor;
    }
}
