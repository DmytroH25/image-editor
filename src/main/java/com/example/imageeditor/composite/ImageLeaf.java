package com.example.imageeditor.composite;

public class ImageLeaf implements ImageComponent {

  private final byte[] imageData;

  public ImageLeaf(byte[] imageData) {
    this.imageData = imageData;
  }


  @Override
  public byte[] getImageData() {
    return imageData;
  }
}