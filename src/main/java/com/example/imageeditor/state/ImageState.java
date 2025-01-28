package com.example.imageeditor.state;

import com.example.imageeditor.repository.entity.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageState {

  Image applyEffect(BufferedImage image, Object... params) throws IOException;
}