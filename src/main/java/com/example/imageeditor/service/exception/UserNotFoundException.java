package com.example.imageeditor.service.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String username) {
    super(String.format("Role with name '%s' not found", username));
  }
}