package com.rabtman.common.base.pagestatusmanager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;


public class PageStatusManager {

  public static final int NO_LAYOUT_ID = 0;
  private static int BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID;
  private static int BASE_RETRY_LAYOUT_ID = NO_LAYOUT_ID;
  private static int BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID;

  public PageStatusLayout mPageStatusLayout;


  public PageStatusListener DEFAULT_LISTENER = new PageStatusListener() {
    @Override
    public void receiveRetryEvent(View retryView) {

    }
  };

  public PageStatusManager(Object activityOrFragmentOrView, PageStatusListener listener) {
    if (listener == null) {
      listener = DEFAULT_LISTENER;
    }

    ViewGroup contentParent = null;
    Context context;
    if (activityOrFragmentOrView instanceof Activity) {
      Activity activity = (Activity) activityOrFragmentOrView;
      context = activity;
      contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
    } else if (activityOrFragmentOrView instanceof Fragment) {
      Fragment fragment = (Fragment) activityOrFragmentOrView;
      context = fragment.getActivity();
      contentParent = (ViewGroup) (fragment.getView().getParent());
    } else if (activityOrFragmentOrView instanceof View) {
      View view = (View) activityOrFragmentOrView;
      contentParent = (ViewGroup) (view.getParent());
      context = view.getContext();
    } else {
      throw new IllegalArgumentException(
          "the argument's type must be Fragment or Activity: init(context)");
    }
    int childCount = contentParent.getChildCount();
    //get contentParent
    int index = 0;
    View oldContent;
    if (activityOrFragmentOrView instanceof View) {
      oldContent = (View) activityOrFragmentOrView;
      for (int i = 0; i < childCount; i++) {
        if (contentParent.getChildAt(i) == oldContent) {
          index = i;
          break;
        }
      }
    } else {
      oldContent = contentParent.getChildAt(0);
    }
    contentParent.removeView(oldContent);
    //setup content layout
    PageStatusLayout pageStatusLayout = new PageStatusLayout(context);

    ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
    contentParent.addView(pageStatusLayout, index, lp);
    pageStatusLayout.setContentView(oldContent);
    // setup loading,retry,empty layout
    setupLoadingLayout(listener, pageStatusLayout);
    setupRetryLayout(listener, pageStatusLayout);
    setupEmptyLayout(listener, pageStatusLayout);
    //callback
    listener.receiveRetryEvent(pageStatusLayout.getRetryView());
    listener.onLoading(pageStatusLayout.getLoadingView());
    listener.onEmpty(pageStatusLayout.getEmptyView());
    mPageStatusLayout = pageStatusLayout;
  }

  public static void initConfig(PageStatusConfig config) {
    BASE_LOADING_LAYOUT_ID = config.getLoadingLayoutId();
    BASE_RETRY_LAYOUT_ID = config.getRetryLayoutId();
    BASE_EMPTY_LAYOUT_ID = config.getEmptyLayoutId();
  }

  public static PageStatusManager generate(Object activityOrFragment, PageStatusListener listener) {
    return new PageStatusManager(activityOrFragment, listener);
  }

  private void setupEmptyLayout(PageStatusListener listener, PageStatusLayout pageStatusLayout) {
    if (listener.isSetEmptyLayout()) {
      int layoutId = listener.generateEmptyLayoutId();
      if (layoutId != NO_LAYOUT_ID) {
        pageStatusLayout.setEmptyView(layoutId);
      } else {
        pageStatusLayout.setEmptyView(listener.generateEmptyLayout());
      }
    } else {
      if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID) {
        pageStatusLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
      }
    }
  }

  private void setupLoadingLayout(PageStatusListener listener, PageStatusLayout pageStatusLayout) {
    if (listener.isSetLoadingLayout()) {
      int layoutId = listener.generateLoadingLayoutId();
      if (layoutId != NO_LAYOUT_ID) {
        pageStatusLayout.setLoadingView(layoutId);
      } else {
        pageStatusLayout.setLoadingView(listener.generateLoadingLayout());
      }
    } else {
      if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID) {
        pageStatusLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
      }
    }
  }

  private void setupRetryLayout(PageStatusListener listener, PageStatusLayout pageStatusLayout) {
    if (listener.isSetRetryLayout()) {
      int layoutId = listener.generateRetryLayoutId();
      if (layoutId != NO_LAYOUT_ID) {
        pageStatusLayout.setLoadingView(layoutId);
      } else {
        pageStatusLayout.setLoadingView(listener.generateRetryLayout());
      }
    } else {
      if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID) {
        pageStatusLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
      }
    }
  }

  public void showLoading() {
    mPageStatusLayout.showLoading();
  }

  public void showRetry() {
    mPageStatusLayout.showRetry();
  }

  public void showContent() {
    mPageStatusLayout.showContent();
  }

  public void showEmpty() {
    mPageStatusLayout.showEmpty();
  }


}
