package com.example.imageeditor.controller;

import com.example.imageeditor.facade.ImageFacade;
import com.example.imageeditor.repository.entity.Image;
import com.example.imageeditor.repository.entity.User;
import com.example.imageeditor.service.impl.UserServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageFacade imageFacade;
  private final UserServiceImpl userService;

  private static final Set<String> SUPPORTED_FORMATS = Set.of(
      "image/jpeg", "image/png", "image/gif", "image/bmp", "image/tiff"
  );

  @PostMapping("/upload")
  public ResponseEntity<byte[]> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      String fileContentType = file.getContentType();
      if (!SUPPORTED_FORMATS.contains(fileContentType)) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
      }
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByUsername(username);
      byte[] imageData = imageFacade.uploadImage(file, user).getImageData();
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
          .body(imageData);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


  @PostMapping("/rotate")
  public ResponseEntity<byte[]> rotateImage(@RequestParam("degree") double degree) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByUsername(username);
      byte[] imageData = imageFacade.rotateImage(user.getImage(), degree);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, user.getImage().getContentType())
          .body(imageData);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/resize")
  public ResponseEntity<byte[]> resizeImage(
      @RequestParam("width") int width,
      @RequestParam("height") int height) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByUsername(username);
      byte[] imageData = imageFacade.resizeImage(user.getImage(), width, height);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, user.getImage().getContentType())
          .body(imageData);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/crop")
  public ResponseEntity<byte[]> cropImage(
      @RequestParam("x") int x,
      @RequestParam("y") int y,
      @RequestParam("width") int width,
      @RequestParam("height") int height) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByUsername(username);
      byte[] imageData = imageFacade.cropImage(user.getImage(), x, y, width, height);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, user.getImage().getContentType())
          .body(imageData);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/uploadAndCreateCollage")
  public ResponseEntity<byte[]> uploadImagesAndCreateCollage(@RequestParam("files") List<MultipartFile> files) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByUsername(username);

      List<byte[]> imageDatas = files.stream()
          .filter(file -> SUPPORTED_FORMATS.contains(file.getContentType()))
          .map(file -> {
            try {
              return file.getBytes();
            } catch (IOException e) {
              throw new RuntimeException("Failed to upload image", e);
            }
          })
          .collect(Collectors.toList());

      byte[] collageData = imageFacade.createCollageFromData(imageDatas, user);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, "image/png")
          .body(collageData);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/undo")
  public ResponseEntity<byte[]> undo() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.getUserByUsername(username);
    byte[] imageData = imageFacade.undo(user);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, user.getImage().getContentType())
        .body(imageData);
  }

  @PostMapping("/redo")
  public ResponseEntity<byte[]> redo() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.getUserByUsername(username);
    byte[] imageData = imageFacade.redo(user);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, user.getImage().getContentType())
        .body(imageData);
  }

  @PostMapping("/save")
  public ResponseEntity<byte[]> saveImage() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.getUserByUsername(username);
    byte[] imageData = imageFacade.saveImage(user.getImage()).getImageData();
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, user.getImage().getContentType())
        .body(imageData);
  }

  @GetMapping("/download")
  public ResponseEntity<byte[]> downloadImage(@RequestParam("format") String format) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.getUserByUsername(username);
    try {
      String requestedContentType = "image/" + format.toLowerCase();
      if (!SUPPORTED_FORMATS.contains(requestedContentType)) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
      }
      Image image = user.getImage();
      if (image == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      byte[] imageData = image.getImageData();
      String filename = "image." + format.toLowerCase();
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, requestedContentType)
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
          .body(imageData);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
