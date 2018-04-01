package org.minecord.minecord.gui;

public enum GuiButtonTexture {

    DISCORD(0, 135, 176, 216),
    TRUE(60, 155, 196, 236),
    FALSE(40, 155, 196, 236),
    LEFT(40, 135, 176, 216),
    RIGHT(60, 135, 176, 216);

    public final int xTex;
    public final int yTex;
    public final int yDifTex;
    public final int yDisTex;

    GuiButtonTexture(int x, int y, int yDif, int yDis){
        this.xTex = x;
        this.yTex = y;
        this.yDifTex = yDif;
        this.yDisTex = yDis;
    }
}
