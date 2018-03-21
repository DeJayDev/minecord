package org.minecord.minecord.gui;

public enum GuiButtonEnum {
    DISCORD(0, 176, 216),
    TRUE(60, 196, 236),
    FALSE(40, 196, 236),
    LEFT(40, 176, 216),
    RIGHT(60, 176, 216);

    private final int xTex;
    private final int yTex;
    private final int yDifTex;

    GuiButtonEnum(int x, int y, int yDif){
        this.xTex = x;
        this.yTex = y;
        this.yDifTex = yDif;
    }

    public int getxTex() {
        return xTex;
    }

    public int getyTex() {
        return yTex;
    }

    public int getYdifTex() {
        return yDifTex;
    }
}
