// BV Ue4 SS2023 Vorgabe
//
// Copyright (C) 2023 by Klaus Jung
// All rights reserved.
// Date: 2023-03-23


package bv_ss23;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Histogram {

    private static final int grayLevels = 256;

    private GraphicsContext gc;
    private int maxHeight;

    private int[] histogram = new int[grayLevels];

    public Histogram() {
    }

    public Histogram(GraphicsContext gc, int maxHeight) {
        this.gc = gc;
        this.maxHeight = maxHeight;
    }

    public int[] getValues() {
        return histogram;
    }

    public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth, int regionHeight) {

        for (int y = regionStartY; y < regionStartY + regionHeight; y++) {
            for (int x = regionStartX; x < regionStartX + regionWidth; x++) {

                int pos = 0;
                //int grayValue = argb[pos];
                //histogram[grayValue]++;
            }
        }
        // TODO: calculate histogram[] out of the gray values found the given image region

        // graustufen zählen, -> dafür über alle pixel laufen und im array eine zahl hochzählen
        // aber das nur in der region machen ( loop von x+w)

    }

    public Integer getMinimum() {
        // Will be used in Exercise 5.
        return null;
    }

    public Integer getMaximum() {
        // Will be used in Exercise 5.
        return null;
    }

    public Double getMean() {
        // Will be used in Exercise 5.
        return null;
    }

    public Integer getMedian() {
        // Will be used in Exercise 5.
        return null;
    }

    public Double getVariance() {
        // Will be used in Exercise 5.
        return null;
    }

    public Double getEntropy() {
        // Will be used in Exercise 5.
        return null;
    }

    public void draw(Color lineColor) {
        if (gc == null) return;
        gc.clearRect(0, 0, grayLevels, maxHeight);
        gc.setStroke(lineColor);
        gc.setLineWidth(1);

        // TODO: draw histogram[] into the gc graphic context
        // Note that we need to add 0.5 to all coordinates to align points to pixel centers

        double shift = 0.5;

        // Remark: This is some dummy code to give you an idea for line drawing
        gc.strokeLine(shift, shift, grayLevels - 1 + shift, maxHeight - 1 + shift);
        gc.strokeLine(grayLevels - 1 + shift, shift, shift, maxHeight - 1 + shift);

       // for(int i = 0; i < grayLevels; i ++){
       //     gc.strokeLine(i, , grayLevels - 1 + shift, maxHeight - 1 + shift);
        //}

        // male senkrechte linien von x achse nach oben ( grayLevels, 0 , grayLevels , häufigkeit)
        // die höhe ist das max vom array mit dem bild

    }

}







