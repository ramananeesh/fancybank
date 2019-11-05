package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FontManager {
    private static final Font fontBauhaus = setFont("Bauhaus 93");
    private static final Font fontCascadia = setFont("Cascadia");
    private static final Font fontConsolas = setFont("Consolas");
    private static final Font fontProductSans = setFont("Product Sans");

    private static final Font fontTitle = fontBauhaus;
    private static final Font fontText = fontProductSans;
    private static final Font fontButton = fontProductSans;

    private static Font setFont(String name) {
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/font/" + name + ".ttf"));
        } catch (Exception e) {
            System.out.println("load font fail. use default font.");
            font = new JLabel().getFont();
        }
        return font;
    }

    public static Font getFontTitle() {
        return fontTitle;
    }

    public static Font getFontText() {
        return fontText;
    }

    public static Font getFontButton() {
        return fontButton;
    }
}
