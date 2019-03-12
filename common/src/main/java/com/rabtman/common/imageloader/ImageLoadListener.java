package com.rabtman.common.imageloader;

import android.widget.ImageView;

/**
 * @author Rabtman
 */
public interface ImageLoadListener {

  public void loadFail(ImageView target, Exception e);

  public void loadReady(ImageView target);
}
