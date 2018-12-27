package com.rabtman.acgnews.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.mvp.model.entity.SHPostItem;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.rabtman.common.utils.TimeUtils;

/**
 * @author Rabtman
 */

public class ISHNewsItemAdapter extends BaseQuickAdapter<SHPostItem, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ISHNewsItemAdapter(ImageLoader imageLoader) {
    super(R.layout.acgnews_item_news_item, null);
    mImageLoader = imageLoader;
  }


  @Override
  protected void convert(BaseViewHolder helper, SHPostItem item) {
    helper.setText(R.id.news_title, item.getTitle())
        .setText(R.id.news_datetime, TimeUtils.millis2String(item.getTime()));
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getThumb())
            .imagerView((ImageView) helper.getView(R.id.news_img))
            .build()
    );
  }
}
