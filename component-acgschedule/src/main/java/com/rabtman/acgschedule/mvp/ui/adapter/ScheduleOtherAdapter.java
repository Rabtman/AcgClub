package com.rabtman.acgschedule.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;

/**
 * @author Rabtman
 */

public class ScheduleOtherAdapter extends
    BaseQuickAdapter<ScheduleOtherPage.ScheduleOtherItem, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ScheduleOtherAdapter(ImageLoader imageLoader) {
    super(R.layout.acgschedule_item_schedule_other, null);
    mImageLoader = imageLoader;
  }


  @Override
  protected void convert(BaseViewHolder helper, ScheduleOtherPage.ScheduleOtherItem item) {
    helper.setText(R.id.tv_schedule_other_title, item.getTitle());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .imagerView((ImageView) helper.getView(R.id.img_schedule_other))
            .build()
    );
  }
}
