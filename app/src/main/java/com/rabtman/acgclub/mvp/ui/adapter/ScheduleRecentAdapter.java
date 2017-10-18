package com.rabtman.acgclub.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.model.jsoup.DilidiliInfo.ScheduleRecent;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation;
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation.CornerType;
import java.util.List;

/**
 * @author Rabtman
 */
public class ScheduleRecentAdapter extends BaseQuickAdapter<ScheduleRecent, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ScheduleRecentAdapter(ImageLoader imageLoader,
      @Nullable List<ScheduleRecent> data) {
    super(R.layout.item_schedule_recent, data);
    this.mImageLoader = imageLoader;
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleRecent item) {
    helper.setText(R.id.tv_schedule_recent, item.getName())
        .setText(R.id.tv_schedule_recent_desc, item.getDesc());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .transformation(
                new MultiTransformation<>(new RoundedCornersTransformation(20, 0, CornerType.TOP),
                    new CenterCrop()))
            .imagerView((ImageView) helper.getView(R.id.img_schedule_recent))
            .build()
    );
  }
}
