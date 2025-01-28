package com.example.imageeditor.state;

import com.example.imageeditor.repository.entity.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CropState implements ImageState {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public CropState(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Image applyEffect(BufferedImage image, Object... params) throws IOException {
        if (x < 0 || y < 0 || x + width > image.getWidth() || y + height > image.getHeight()) {
            throw new IllegalArgumentException("Invalid crop coordinates.");
        }

        BufferedImage croppedImage = image.getSubimage(x, y, width, height);
        byte[] croppedImageData = convertImageToByteArray(croppedImage);

        Image newImage = new Image();
        newImage.setImageData(croppedImageData);
        newImage.setContentType("image/png");
        newImage.setFilename("cropped_image.png");

        return newImage;
    }

    private byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
