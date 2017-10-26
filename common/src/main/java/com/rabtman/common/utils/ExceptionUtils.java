package com.rabtman.common.utils;

public class ExceptionUtils {

  public static String errToStr(Exception e) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(e.toString() + "\n");
    StackTraceElement[] stack = e.getStackTrace();
    for (StackTraceElement aStack : stack) {
      stringBuilder.append(aStack.toString() + "\n");
    }
    return stringBuilder.toString();
  }

  public static String errToStr(Throwable t) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(t.toString() + "\n");
    StackTraceElement[] stack = t.getStackTrace();
    for (StackTraceElement aStack : stack) {
      stringBuilder.append(aStack.toString() + "\n");
    }
    return stringBuilder.toString();
  }
}