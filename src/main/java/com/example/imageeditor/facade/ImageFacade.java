package com.example.imageeditor.facade;

import com.example.imageeditor.repository.entity.Image;
import com.example.imageeditor.repository.entity.User;
import com.example.imageeditor.service.impl.ImageServiceImpl;
import com.example.imageeditor.state.CropState;
import com.example.imageeditor.state.ResizeState;
import com.example.imageeditor.state.RotateState;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageFacade {

  private final ImageServiceImpl imageService;

  public Image uploadImage(MultipartFile file, User user) throws IOException {
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    return imageService.saveImage(file, user);
  }

  public Image saveImage(Image image) {
    return imageService.saveImage(image);
  }

  public byte[] rotateImage(Image image, double degree) throws IOException {
    imageService.applyEffect(image, new RotateState(degree));
    return imageService.getCurrentImage(image.getUser());
  }

  public byte[] resizeImage(Image image, int width, int height) throws IOException {
    imageService.applyEffect(image, new ResizeState(width, height));
    return imageService.getCurrentImage(image.getUser());

  }

  public byte[] cropImage(Image image, int x, int y, int width, int height) throws IOException {
    imageService.applyEffect(image, new CropState(x, y, width, height));
    return imageService.getCurrentImage(image.getUser());
  }

  public byte[] createCollageFromData(List<byte[]> imageDatas, User user) throws IOException {
    return imageService.createCollageFromData(imageDatas, user);
  }

  public byte[] undo(User user) {
    imageService.undo(user);
    return imageService.getCurrentImage(user);
  }

  public byte[] redo(User user) {
    imageService.redo(user);
    return imageService.getCurrentImage(user);
  }

}
