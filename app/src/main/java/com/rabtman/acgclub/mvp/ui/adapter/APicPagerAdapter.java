package com.rabtman.acgclub.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.rabtman.acgclub.base.App;
import com.rabtman.acgclub.base.view.PinchImageView;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Rabtman
 */

public class APicPagerAdapter extends PagerAdapter {

  private final LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();
  private Context mContext;
  private List<String> apicList;
  private ImageLoader mImageLoader;
  private PinchImageViewListener listener;

  public APicPagerAdapter(Context context, List<String> apicList) {
    this.mContext = context;
    this.apicList = apicList;
    mImageLoader = ((App) context.getApplicationContext()).getAppComponent().imageLoader();
  }

  public void setPinchImageViewListener(PinchImageViewListener listener) {
    this.listener = listener;
  }

  @Override
  public int getCount() {
    return apicList.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    PinchImageView piv;
    if (viewCache.size() > 0) {
      piv = viewCache.remove();
      piv.reset();
    } else {
      piv = new PinchImageView(mContext);
      piv.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onClick(v);
          }
        }
      });
    }
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(apicList.get(position))
            .zoomStrategy(GlideImageConfig.FIT_CENTER)
            .imagerView(piv)
            .build());
    container.addView(piv);
    return piv;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    PinchImageView piv = (PinchImageView) object;
    container.removeView(piv);
    viewCache.add(piv);
  }

  @Override
  public void setPrimaryItem(ViewGroup container, int position, Object object) {
    PinchImageView piv = (PinchImageView) object;
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(apicList.get(position))
            .zoomStrategy(GlideImageConfig.FIT_CENTER)
            .imagerView(piv)
            .build());
  }

  public interface PinchImageViewListener {

    void onClick(View v);
  }
}
