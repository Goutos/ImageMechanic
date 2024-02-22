package com.imageMechanic.ImageMechanic.Services;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ImagePreview {

  public static BufferedImage toBufferedImage(
      Image image, String ActionSelected, boolean FlipH, boolean FlipV) {
    BufferedImage bufferedPreview =
        new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = bufferedPreview.createGraphics();
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();

    switch (ActionSelected) {
      case "BNW":
        bufferedPreview = ImageProcessing.BlackAndWhite(bufferedPreview);
        break;
      case "Sepia":
        bufferedPreview = ImageProcessing.Sepia(bufferedPreview);
        break;
      case "Negative":
        bufferedPreview = ImageProcessing.Negative(bufferedPreview);
        break;
    }
    if (FlipH) {
      bufferedPreview = ImageProcessing.HorizontalFlip(bufferedPreview);
    }
    if (FlipV) {
      bufferedPreview = ImageProcessing.VerticalFlip(bufferedPreview);
    }

    return bufferedPreview;
  }
}
