package com.rabtman.common.base.pagestatusmanager;

import static com.rabtman.common.base.pagestatusmanager.PageStatusManager.NO_LAYOUT_ID;

/**
 * @author Rabtman
 */

public class PageStatusConfig {

  private int loadingLayoutId;
  private int retryLayoutId;
  private int emptyLayoutId;

  private PageStatusConfig(Builder builder) {
    loadingLayoutId = builder.loadingLayoutId;
    retryLayoutId = builder.retryLayoutId;
    emptyLayoutId = builder.emptyLayoutId;
  }

  public int getLoadingLayoutId() {
    return loadingLayoutId;
  }

  public int getRetryLayoutId() {
    return retryLayoutId;
  }

  public int getEmptyLayoutId() {
    return emptyLayoutId;
  }

  public static final class Builder {

    private int loadingLayoutId = NO_LAYOUT_ID;
    private int retryLayoutId = NO_LAYOUT_ID;
    private int emptyLayoutId = NO_LAYOUT_ID;

    public Builder() {
    }

    public Builder loadingLayoutId(int val) {
      loadingLayoutId = val;
      return this;
    }

    public Builder retryLayoutId(int val) {
      retryLayoutId = val;
      return this;
    }

    public Builder emptyLayoutId(int val) {
      emptyLayoutId = val;
      return this;
    }

    public PageStatusConfig build() {
      return new PageStatusConfig(this);
    }
  }
}
