package com.example.imageeditor.state;

import com.example.imageeditor.repository.entity.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResizeState implements ImageState {
    private final int width;
    private final int height;

    public ResizeState(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Image applyEffect(BufferedImage image, Object... params) throws IOException {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        byte[] resizedImageData = convertImageToByteArray(resizedImage);

        Image newImage = new Image();
        newImage.setImageData(resizedImageData);
        newImage.setContentType("image/png");
        newImage.setFilename("resized_image.png");

        return newImage;
    }

    private byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
