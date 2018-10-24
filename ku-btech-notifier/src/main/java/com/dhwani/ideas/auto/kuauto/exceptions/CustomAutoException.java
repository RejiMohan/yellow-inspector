package com.dhwani.ideas.auto.kuauto.exceptions;

public class CustomAutoException extends Exception {

  private static final long serialVersionUID = -6203378586249028401L;

  public CustomAutoException(String message) {
    super(message);
  }

  public CustomAutoException(String message, Throwable t) {
    super(message, t);
  }

}
