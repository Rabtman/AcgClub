package com.rabtman.acgclub.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNews;
import com.rabtman.common.imageloader.ImageLoader;
import java.util.List;

/**
 * @author Rabtman
 */

public class AcgNewsItemAdapter extends BaseQuickAdapter<AcgNews, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public AcgNewsItemAdapter(List<AcgNews> items) {
    super(R.layout.item_news_item, items);
    //mImageLoader = ((App) mContext.getApplicationContext()).getAppComponent().imageLoader();
  }


  @Override
  protected void convert(BaseViewHolder helper, AcgNews item) {
    helper.setText(R.id.news_title, item.getTitle())
        .setText(R.id.news_datetime, item.getDateTime());
    /*mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .imagerView((ImageView) helper.getView(R.id.news_img))
            .build()
    );*/
  }
}
