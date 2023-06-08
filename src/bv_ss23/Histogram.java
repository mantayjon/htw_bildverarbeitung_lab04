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
        int min = 0;
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] != 0) {
                min = i;
                break;
            }
        }
        return min;

    }

    public Integer getMaximum() {
        int max = 1234;
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[255 - i] != 0) {
                max = 255 - i;
                break;
            }
        }
        return max;
    }

    public Double getMean() {
        double mean = 0;
        int n = 0;

        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] != 0) {
                mean += i * histogram[i];
                n += histogram[i];
            }
        }
        mean = mean / n;

        return mean;
    }

    public Integer getMedian() {

        int n = sumPixel();
        int median = 0;

        n = n / 2;

        for (int i = 0; i < histogram.length; i++) {
            if (n > 0) {
                n -= histogram[i];
                median = i;
            }
        }

        return median;
    }

    public Double getVariance() {

        int n = sumPixel();
        double mean = getMean();
        double variance = 0;

        for (int i = 0; i < histogram.length; i++) {
            variance += Math.pow(i - mean, 2) * histogram[i];
        }

        return variance / n;
    }

    public Double getEntropy() {
        double n = sumPixel();
        double entropy = 0;

        for (int i = 0; i < histogram.length; i++) {

            if (histogram[i] != 0) {
                entropy += ((double) histogram[i] / n) * (Math.log((double) histogram[i] / n) / Math.log(2));

            }
        }

        return - entropy;
    }

    public void draw(Color lineColor) {
        if (gc == null) return;
        gc.clearRect(0, 0, grayLevels, maxHeight);
        gc.setStroke(lineColor);
        gc.setLineWidth(1);

        double max = 0;

        for (int j : histogram) {
            if (j > max) {
                max = j;
            }
        }
        double stauchung = maxHeight / max;

        double shift = 0.5;

        for (int i = 0; i < grayLevels; i++) {
            gc.strokeLine(i + shift, maxHeight + shift,
                    i + shift, maxHeight - (histogram[i] * stauchung) + shift);
        }
    }

    public Integer sumPixel() {
        int n = 0;
        for (int j : histogram) {
            n += j;
        }
        return n;
    }

}







