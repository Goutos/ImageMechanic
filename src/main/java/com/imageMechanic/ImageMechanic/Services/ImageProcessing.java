package com.imageMechanic.ImageMechanic.Services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageProcessing {

  public static void ImageIO(
      String selectedFile,
      String filename,
      String selectedFileExport,
      String ActionSelected,
      boolean FlipH,
      boolean FlipV,
      int CompressionIndex) {

    BufferedImage image = null;
    File file = null;
    try {
      selectedFile = selectedFile.replace("\\", "/");
      String importpath = selectedFile + "/" + filename;
      file = new File(importpath);
      image = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println(e);
    }

    switch (ActionSelected) {
      case "BNW":
        image = ImageProcessing.BlackAndWhite(image);
        break;
      case "Sepia":
        image = ImageProcessing.Sepia(image);
        break;
      case "Negative":
        image = ImageProcessing.Negative(image);
        break;
    }
    if (FlipH) {
      image = HorizontalFlip(image);
    }
    if (FlipV) {
      image = VerticalFlip(image);
    }
    switch (CompressionIndex) {
      case 0:
        image = ImageProcessing.compressImage(image, 1);
        break;
      case 1:
        image = ImageProcessing.compressImage(image, 0.75);
        break;
      case 2:
        image = ImageProcessing.compressImage(image, 0.5);
        break;
      case 3:
        image = ImageProcessing.compressImage(image, 0.25);
        break;
    }
    try {
      String exportpath = selectedFileExport + "/" + filename;
      file = new File(exportpath);
      ImageIO.write(image, "jpg", file);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static BufferedImage BlackAndWhite(BufferedImage image) {

    int width = image.getWidth();
    int height = image.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int p = image.getRGB(x, y);
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        int avg = (r + g + b) / 3;
        p = (a << 24) | (avg << 16) | (avg << 8) | avg;
        image.setRGB(x, y, p);
      }
    }
    return image;
  }

  public static BufferedImage Sepia(BufferedImage image) {

    int width = image.getWidth();
    int height = image.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int p = image.getRGB(x, y);
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        int newR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
        int newG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
        int newB = (int) (0.272 * r + 0.534 * g + 0.131 * b);
        if (newR > 255) r = 255;
        else r = newR;

        if (newG > 255) g = 255;
        else g = newG;

        if (newB > 255) b = 255;
        else b = newB;

        p = (a << 24) | (r << 16) | (g << 8) | b;

        image.setRGB(x, y, p);
      }
    }
    return image;
  }

  public static BufferedImage Negative(BufferedImage image) {

    int width = image.getWidth();
    int height = image.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int p = image.getRGB(x, y);
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        r = 255 - r;
        g = 255 - g;
        b = 255 - b;

        p = (a << 24) | (r << 16) | (g << 8) | b;

        image.setRGB(x, y, p);
      }
    }
    return image;
  }

  public static BufferedImage HorizontalFlip(BufferedImage image) {
    BufferedImage newImage =
        new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    int width = image.getWidth();
    int height = image.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int p = image.getRGB(x, y);
        newImage.setRGB(width - 1 - x, y, p);
      }
    }

    return newImage;
  }

  public static BufferedImage VerticalFlip(BufferedImage image) {
    BufferedImage newImage =
        new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    int width = image.getWidth();
    int height = image.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int p = image.getRGB(x, y);
        newImage.setRGB(x, height - 1 - y, p);
      }
    }

    return newImage;
  }

  public static BufferedImage compressImage(BufferedImage image, double Precentage) {
    int newWidth = (int) (image.getWidth() * Precentage);
    int newHeight = (int) (image.getHeight() * Precentage);
    BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
    Graphics2D g = newImage.createGraphics();
    g.drawImage(image, 0, 0, newWidth, newHeight, null);
    g.dispose();
    return newImage;
  }
}
