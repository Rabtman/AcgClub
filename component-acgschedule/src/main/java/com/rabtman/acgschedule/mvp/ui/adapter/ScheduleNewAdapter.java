package com.rabtman.acgschedule.mvp.ui.adapter;

import android.widget.ImageView;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew.ScheduleNewItem;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation;
import com.rabtman.common.utils.DimenUtils;

/**
 * @author Rabtman
 */

public class ScheduleNewAdapter extends BaseQuickAdapter<ScheduleNewItem, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ScheduleNewAdapter(ImageLoader imageLoader) {
    super(R.layout.acgschedule_item_schedule_new, null);
    mImageLoader = imageLoader;
  }


  @Override
  protected void convert(BaseViewHolder helper, ScheduleNewItem item) {
    helper.setText(R.id.schedule_new_title, item.getTitle())
        .setText(R.id.schedule_new_spot, item.getSpot())
        .setText(R.id.schedule_new_type, item.getType())
        .setText(R.id.schedule_new_desc, item.getDesc());
    mImageLoader.loadImage(getContext(),
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .transformation(
                new MultiTransformation<>(new CenterCrop(),
                    new RoundedCornersTransformation(DimenUtils.dpToPx(getContext(), 4), 0)))
            .imageView((ImageView) helper.getView(R.id.schedule_new_img))
            .build()
    );
  }
}
