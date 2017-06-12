package com.fcannizzaro.jsoup.annotations.exceptions;

/**
 * Created by Francesco Cannizzaro (fcannizzaro)
 */

public class AttributeNotFoundException extends NullPointerException {

  public AttributeNotFoundException(String attr) {
    super("Attribute not found. [" + attr + "]");
  }

}
