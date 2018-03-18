package com.fcannizzaro.jsoup.annotations.exceptions;

/**
 * Created by Francesco Cannizzaro (fcannizzaro)
 */

public class ElementNotFoundException extends NullPointerException {

  public ElementNotFoundException(String query) {
    super("Element not found. [" + query + "]");
  }

}
