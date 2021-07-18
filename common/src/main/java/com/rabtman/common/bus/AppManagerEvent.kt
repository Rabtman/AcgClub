package com.rabtman.common.bus;

/**
 * @author Rabtman
 */

public class AppManagerEvent {

  public static final int START_ACTIVITY = 1;
  public static final int SHOW_SNACKBAR = 2;
  public static final int KILL_ALL = 3;
  public static final int APP_EXIT = 4;
  public int type;
  public Object msg;

  public AppManagerEvent(int type, Object msg) {
    this.type = type;
    this.msg = msg;
  }
}
