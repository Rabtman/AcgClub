package com.rabtman.acgnews.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNews;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;

/**
 * @author Rabtman
 */

public class ZeroFiveNewsItemAdapter extends BaseQuickAdapter<ZeroFiveNews, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ZeroFiveNewsItemAdapter(ImageLoader imageLoader) {
    super(R.layout.acgnews_item_news_item, null);
    mImageLoader = imageLoader;
  }


  @Override
  protected void convert(BaseViewHolder helper, ZeroFiveNews item) {
    helper.setText(R.id.news_title, item.getTitle())
        .setText(R.id.news_datetime, item.getDateTime());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .imageView((ImageView) helper.getView(R.id.news_img))
            .build()
    );
  }
}
