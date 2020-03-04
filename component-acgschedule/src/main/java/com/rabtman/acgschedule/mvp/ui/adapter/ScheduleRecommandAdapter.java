package com.rabtman.acgschedule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleRecommend;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation;
import java.util.List;

/**
 * @author Rabtman
 */
public class ScheduleRecommandAdapter extends BaseQuickAdapter<ScheduleRecommend, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ScheduleRecommandAdapter(ImageLoader imageLoader,
      @Nullable List<ScheduleRecommend> data) {
    super(R.layout.acgschedule_item_schedule_recommand, data);
    this.mImageLoader = imageLoader;
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleRecommend item) {
    helper.setText(R.id.tv_schedule_recommand, item.getName());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .transformation(
                new MultiTransformation<>(new CenterCrop(),
                    new RoundedCornersTransformation(20, 0)))
            .imageView((ImageView) helper.getView(R.id.img_schedule_recommand))
            .build()
    );
  }
}
