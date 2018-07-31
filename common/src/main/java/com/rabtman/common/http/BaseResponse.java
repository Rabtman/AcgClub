package com.rabtman.common.http;

/**
 * @author Rabtman
 */
public class BaseResponse<T> {

  public int code;
  public String msg;
  public T data;
}
