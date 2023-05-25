// BV Ue4 SS2023 Vorgabe
//
// Copyright (C) 2023 by Klaus Jung
// All rights reserved.
// Date: 2023-03-23


package bv_ss23;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToneCurve {

    private static final int grayLevels = 256;

    private GraphicsContext gc;

    private int[] grayTable = new int[grayLevels];

    public int[] getGrayTable() {
        return grayTable;
    }

    public ToneCurve(GraphicsContext gc) {
        this.gc = gc;
    }

    public void updateTable(int brightness, double contrast, double gamma) {

        for (int i = 0; i < grayTable.length; i++) {
            grayTable[i] = i;

            grayTable[i] = Math.max(Math.min(grayTable[i] + brightness,255),0);
            grayTable[i] = (int) Math.min(Math.max((contrast * (grayTable[i] - 128) + 128),0),255);
            grayTable[i] = (int) Math.min(Math.max(((255 * (Math.pow(grayTable[i], (1 / gamma)))) /
                    (Math.pow(255, (1 / gamma)))),0),255);

        }
    }

    public void applyTo(RasterImage image) {
        image.convertToGray();

        for (int i = 0; i < image.argb.length; i++) {
            int index = image.argb[i] & 0xff;
            int grey = grayTable[index];

            image.argb[i] = (0xFF << 24) | (grey << 16) | (grey << 8) | grey;
        }
    }

    public void draw(Color lineColor) {
        if (gc == null) return;
        gc.clearRect(0, 0, grayLevels, grayLevels);
        gc.setStroke(lineColor);
        gc.setLineWidth(3);

        double shift = 0.5;

        gc.beginPath();
        gc.moveTo(0 + shift, grayLevels - grayTable[0] + shift);

        for (int i = 0; i < grayTable.length; i++) {
            gc.lineTo(i + shift, grayLevels - grayTable[i] + shift);
        }
        gc.stroke();
    }


}





