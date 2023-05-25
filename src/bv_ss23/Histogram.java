// BV Ue4 SS2023 Vorgabe
//
// Copyright (C) 2023 by Klaus Jung
// All rights reserved.
// Date: 2023-03-23


package bv_ss23;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

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

        Arrays.fill(histogram, 0);
        for (int y = regionStartY; y < regionStartY + regionHeight; y++) {
            for (int x = regionStartX; x < regionStartX + regionWidth; x++) {

                int pos = y * image.width + x;
                int index = image.argb[pos] & 0xff;

                histogram[index]++;
            }
        }

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

        double max = 0;

        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > max) {
                max = histogram[i];
            }
        }
        double stauchung = maxHeight / max;

        double shift = 0.5;

        for (int i = 0; i < grayLevels; i++) {
            gc.strokeLine(i + shift, maxHeight + shift, i + shift, maxHeight - (histogram[i] * stauchung) + shift);
        }
    }

}







