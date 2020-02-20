package com.rabtman.acgclub.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.model.jsoup.Fiction.FictionItem;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;

/**
 * @author Rabtman
 */
public class FictionItemAdapter extends BaseQuickAdapter<FictionItem, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public FictionItemAdapter(ImageLoader imageLoader) {
    super(R.layout.item_fiction);
    this.mImageLoader = imageLoader;
  }

  @Override
  protected void convert(BaseViewHolder helper, FictionItem item) {
    helper.setText(R.id.tv_fiction, item.getTitle());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .imageView((ImageView) helper.getView(R.id.img_fiction))
            .build()
    );
  }
}
