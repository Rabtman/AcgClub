package com.rabtman.acgschedule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleRecommand;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import java.util.List;

/**
 * @author Rabtman
 */
public class ScheduleRecommandAdapter extends BaseQuickAdapter<ScheduleRecommand, BaseViewHolder> {

  private ImageLoader mImageLoader;

  public ScheduleRecommandAdapter(ImageLoader imageLoader,
      @Nullable List<ScheduleRecommand> data) {
    super(R.layout.acgschedule_item_schedule_recommand, data);
    this.mImageLoader = imageLoader;
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleRecommand item) {
    helper.setText(R.id.tv_schedule_recommand, item.getName());
    mImageLoader.loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(item.getImgUrl())
            .imagerView((ImageView) helper.getView(R.id.img_schedule_recommand))
            .build()
    );
  }
}
