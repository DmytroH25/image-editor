package com.example.imageeditor.composite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageComposite implements ImageComponent {

  private final List<ImageComponent> imageComponents = new ArrayList<>();

  public void add(ImageComponent imageComponent) {
    imageComponents.add(imageComponent);
  }

  public void remove(ImageComponent imageComponent) {
    imageComponents.remove(imageComponent);
  }

  public ImageComponent getChild(int i) {
    return imageComponents.get(i);
  }

  @Override
  public byte[] getImageData() {
    try {
      return createCollage();
    } catch (IOException e) {
      throw new RuntimeException("Failed to create collage", e);
    }
  }

  private byte[] createCollage() throws IOException {
    if (imageComponents.isEmpty()) {
      throw new IllegalArgumentException("No images provided");
    }

    BufferedImage baseImage = toBufferedImage(imageComponents.get(0).getImageData());
    Graphics2D g2d = baseImage.createGraphics();

    for (int i = 1; i < imageComponents.size(); i++) {
      BufferedImage overlayImage = toBufferedImage(imageComponents.get(i).getImageData());
      int newHeight = baseImage.getHeight() / 3;
      int newWidth = (overlayImage.getWidth() * newHeight) / overlayImage.getHeight();
      BufferedImage resizedOverlayImage = new BufferedImage(newWidth, newHeight, overlayImage.getType());
      Graphics2D g2dOverlay = resizedOverlayImage.createGraphics();
      g2dOverlay.drawImage(overlayImage, 0, 0, newWidth, newHeight, null);
      g2dOverlay.dispose();

      int x = (i % 2 == 0) ? baseImage.getWidth() - newWidth : 0;
      int y = (i < 3) ? 0 : baseImage.getHeight() - newHeight;

      g2d.drawImage(resizedOverlayImage, x, y, null);
    }

    g2d.dispose();
    return toByteArray(baseImage);
  }

  private BufferedImage toBufferedImage(byte[] imageData) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
    return ImageIO.read(bis);
  }

  private byte[] toByteArray(BufferedImage image) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", bos);
    return bos.toByteArray();
  }
}