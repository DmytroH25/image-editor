package com.example.imageeditor.repository.entity;

import com.example.imageeditor.prototype.CloneableImage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image implements CloneableImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "image_data")
  private byte[] imageData;

  @Column(name = "filename")
  private String filename;

  @Column(name = "content_type")
  private String contentType;

  @OneToOne
  private User user;

  @Override
  public Image clone() {
    try {
      Image clonedImage = new Image();
      clonedImage.setId(this.id);
      clonedImage.setImageData(this.imageData != null ? this.imageData.clone() : null);
      clonedImage.setFilename(this.filename);
      clonedImage.setContentType(this.contentType);
      clonedImage.setUser(this.user);
      return clonedImage;
    } catch (Exception e) {
      throw new RuntimeException("Failed to clone Image object.", e);
    }
  }
}
