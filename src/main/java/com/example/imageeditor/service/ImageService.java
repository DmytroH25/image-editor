package com.example.imageeditor.service;

import com.example.imageeditor.repository.entity.Image;
import com.example.imageeditor.repository.entity.User;
import com.example.imageeditor.state.ImageState;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
  Image getImageById(Long id);
  Image saveImage(MultipartFile file, User user) throws IOException;
  Image saveImage(Image image);
  void applyEffect(Image image, ImageState imageState) throws IOException;
  byte[] createCollageFromData(List<byte[]> imageDatas, User user) throws IOException;
  void undo(User user);
  void redo(User user);
  byte[] getCurrentImage(User user);
}