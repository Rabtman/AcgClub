package com.rabtman.acgclub.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.model.jsoup.MoePic.PicInfo;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;

/**
 * @author Rabtman
 */

public class CartoonItemAdapter extends BaseQuickAdapter<PicInfo, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public CartoonItemAdapter(ImageLoader imageLoader) {
    super(R.layout.item_cartoon_item, null);
    mImageLoader = imageLoader;
  }


  @Override
  protected void convert(BaseViewHolder helper, PicInfo item) {
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getThumbUrl())
            .imagerView((ImageView) helper.getView(R.id.pic_img))
            .build()
    );
  }
}
