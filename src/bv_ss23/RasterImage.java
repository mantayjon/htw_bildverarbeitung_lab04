// BV Ue4 SS2023 Vorgabe
//
// Copyright (C) 2023 by Klaus Jung
// All rights reserved.
// Date: 2023-03-23


package bv_ss23;

import java.io.File;
import java.util.Arrays;

import bv_ss23.ImageAnalysisAppController.Visualization;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {

    private static final int gray = 0xffa0a0a0;

    public int[] argb;    // pixels represented as ARGB values in scanline order
    public int width;    // image width in pixels
    public int height;    // image height in pixels

    public RasterImage(int width, int height) {
        // creates an empty RasterImage of given size
        this(width, height, gray);
    }

    public RasterImage(int width, int height, int argbColor) {
        // creates an empty RasterImage of given size and color
        this.width = width;
        this.height = height;
        argb = new int[width * height];
        Arrays.fill(argb, argbColor);
    }

    public RasterImage(RasterImage image) {
        // copy constructor
        this.width = image.width;
        this.height = image.height;
        argb = image.argb.clone();
    }

    public RasterImage(File file) {
        // creates a RasterImage by reading the given file
        Image image = null;
        if (file != null && file.exists()) {
            image = new Image(file.toURI().toString());
        }
        if (image != null && image.getPixelReader() != null) {
            width = (int) image.getWidth();
            height = (int) image.getHeight();
            argb = new int[width * height];
            image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
        } else {
            // file reading failed: create an empty RasterImage
            this.width = 256;
            this.height = 256;
            argb = new int[width * height];
            Arrays.fill(argb, gray);
        }
    }

    public RasterImage(ImageView imageView) {
        // creates a RasterImage from that what is shown in the given ImageView
        Image image = imageView.getImage();
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        argb = new int[width * height];
        image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
    }

    public Image getImage() {
        // returns a JavaFX image
        if (argb != null) {
            WritableImage wr = new WritableImage(width, height);
            PixelWriter pw = wr.getPixelWriter();
            pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
            return wr;
        }
        return null;
    }

    public void setToView(ImageView imageView) {
        // sets the current argb pixels to be shown in the given ImageView
        Image image = getImage();
        if (image != null) {
            imageView.setImage(image);
        }
    }


    // image point operations to be added here

    public void convertToGray() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pos = y * width + x;
                int argValue = argb[pos];

                int r = (argValue >> 16) & 0xff;
                int g = (argValue >> 8) & 0xff;
                int b = argValue & 0xff;

                int grey = (r + g + b) / 3;

                argb[pos] = (0xFF << 24) | (grey << 16) | (grey << 8) | grey;
            }
        }

    }

    public RasterImage getOverlayImage(int regionSize, Visualization visualization, double threshold) {

        // Create an overlay image that contains half transparent green pixels where a
        // statistical property locally exceeds the given threshold.
        // Use a sliding window of size regionSize x regionSize.
        // Use "switch(visualization)" to determine, what statistical property should be used

        RasterImage overlayImage = new RasterImage(width, height, 0x0000ff00);
        Histogram histo = new Histogram();
        int regionWidth = 0;
        int regionHeight = 0;

        if (visualization == Visualization.ENTROPY) {

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int pos = y * width + x;

                    int xFilter = x - (regionSize / 2);
                    regionWidth = regionSize;
                    if (xFilter < 0) {
                        regionWidth = regionSize + xFilter;
                        xFilter = 0;
                    }
                    if (x + (regionSize / 2) >= width){
                        regionWidth = width - x + regionSize/2;
                    }

                    int yFilter = y - (regionSize / 2);
                    regionHeight = regionSize;
                    if (yFilter < 0) {
                        regionHeight = regionSize + yFilter;
                        yFilter = 0;
                    }
                    if (y + (regionSize / 2) >= height){
                        regionHeight = height - y + regionSize/2;
                    }

                    histo.setImageRegion(this, xFilter, yFilter, regionWidth, regionHeight);

                    double entro = histo.getEntropy();

                    if (entro > threshold) {
                        overlayImage.argb[pos] = 0x8800ff00;
                    }

                }

            }

        } else if (visualization == Visualization.VARIANCE) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int pos = y * width + x;

                    int xFilter = x - (regionSize / 2);
                    regionWidth = regionSize;
                    if (xFilter < 0) {
                        regionWidth = regionSize + xFilter;
                        xFilter = 0;
                    }
                    if (x + (regionSize / 2) >= width){
                        regionWidth = width - x + regionSize/2;
                    }

                    int yFilter = y - (regionSize / 2);
                    regionHeight = regionSize;
                    if (yFilter < 0) {
                        regionHeight = regionSize + yFilter;
                        yFilter = 0;
                    }
                    if (y + (regionSize / 2) >= height){
                        regionHeight = height - y + regionSize/2;
                    }

                    histo.setImageRegion(this, xFilter, yFilter, regionWidth, regionHeight);

                    double variance = histo.getVariance();

                    if (variance > threshold) {
                        overlayImage.argb[pos] = 0x8800ff00;
                    }

                }

            }
        }

        return overlayImage;
    }

}






