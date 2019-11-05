package view;

import java.awt.*;

public class SizeManager {

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int dialogX = (int) (screenSize.getWidth() * 0.15);
    private static int dialogY = (int) (screenSize.getHeight() * 0.15);
    private static int dialogWidth = (int) (screenSize.getWidth() - 2 * dialogX);
    private static int dialogHeight = (int) (screenSize.getHeight() - 2 * dialogY);
    private static Rectangle dialogBounds = new Rectangle(dialogX, dialogY, dialogWidth, dialogHeight);
    private static int windowX = (int) (screenSize.getWidth() * 0.05);
    private static int windowY = (int) (screenSize.getHeight() * 0.05);
    private static int windowWidth = (int) (screenSize.getWidth() - 2 * windowX);
    private static int windowHeight = (int) (screenSize.getHeight() - 2 * windowY);
    private static Rectangle windowBounds = new Rectangle(windowX, windowY, windowWidth, windowHeight);
    private static int textSizeButton = (int) (screenSize.getHeight() * 0.06);
    private static int textSizeText = (int) (screenSize.getHeight() * 0.03);
    private static int textSizeTitle = (int) (screenSize.getHeight() * 0.08);
    private static int textSizeDescription = (int) (screenSize.getHeight() * 0.05);

    public static Dimension getScreenSize() {
        return screenSize;
    }

    public static int getDialogX() {
        return dialogX;
    }

    public static int getDialogY() {
        return dialogY;
    }

    public static int getDialogWidth() {
        return dialogWidth;
    }

    public static int getDialogHeight() {
        return dialogHeight;
    }

    public static Rectangle getDialogBounds() {
        return dialogBounds;
    }

    public static int getWindowX() {
        return windowX;
    }

    public static int getWindowY() {
        return windowY;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static Rectangle getWindowBounds() {
        return windowBounds;
    }

    public static int getTextSizeButton() {
        return textSizeButton;
    }

    public static int getTextSizeText() {
        return textSizeText;
    }

    public static int getTextSizeTitle() {
        return textSizeTitle;
    }

    public static int getTextSizeDescription() {
        return textSizeDescription;
    }
}
