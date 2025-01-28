package com.example.imageeditor.memento;

public record ImageMemento(byte[] imageData) {

  @Override
  public byte[] imageData() {
    return imageData.clone();
  }
}

