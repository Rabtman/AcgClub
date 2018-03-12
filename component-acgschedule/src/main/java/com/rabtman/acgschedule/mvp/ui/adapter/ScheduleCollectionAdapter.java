package com.rabtman.acgschedule.mvp.ui.adapter;

import android.widget.ImageView;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation;
import com.rabtman.common.utils.DimenUtils;

/**
 * @author Rabtman
 */

public class ScheduleCollectionAdapter extends
    BaseQuickAdapter<ScheduleCollection, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ScheduleCollectionAdapter(ImageLoader imageLoader) {
    super(R.layout.acgschedule_item_schedule_collection, null);
    mImageLoader = imageLoader;
  }


  @Override
  protected void convert(BaseViewHolder helper, ScheduleCollection item) {
    helper.setText(R.id.tv_schedule_collection_name, item.getName());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .transformation(
                new MultiTransformation<>(new CenterCrop(),
                    new RoundedCornersTransformation(DimenUtils.dpToPx(mContext, 4), 0)))
            .imagerView((ImageView) helper.getView(R.id.img_schedule_collection))
            .build()
    );
  }
}
