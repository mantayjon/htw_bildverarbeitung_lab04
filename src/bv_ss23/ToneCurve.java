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
            grayTable[i] = grayTable[i] + brightness;
            grayTable[i] = (int) (contrast * (grayTable[i] - 128) + 128);
            grayTable[i] = (int) ((255 * (Math.pow(grayTable[i], (1 / gamma)))) / (Math.pow(255, (1 / gamma))));


        }
    }

    public void applyTo(RasterImage image) {

        // TODO: apply the gray value mapping to the given image

    }

    public void draw(Color lineColor) {
        if (gc == null) return;
        gc.clearRect(0, 0, grayLevels, grayLevels);
        gc.setStroke(lineColor);
        gc.setLineWidth(3);

        double shift = 0.5;
        
        gc.beginPath();
        gc.moveTo(0 + shift, grayLevels - grayTable[0] + shift);
        int line = 0;

        for (int i = 0; i < grayTable.length; i++) {
            if (grayLevels - grayTable[i] < 0) {
                line = 0;
            } else if (grayLevels - grayTable[i] > 255) {
                line = 255;
            } else {
                line = grayLevels - grayTable[i];
            }
            gc.lineTo(i + shift, line + shift);
        }

        gc.stroke();
    }


}





