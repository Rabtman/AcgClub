package com.rabtman.acgnews.mvp.model.entity;

/**
 * @author Rabtman ishuhui response
 */
public class SHResponse<T> {

  private int errNo;
  private String errMsg;
  private T data;

  public int getErrNo() {
    return errNo;
  }

  public void setErrNo(int errNo) {
    this.errNo = errNo;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
