package com.rabtman.acgschedule.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheudleBanner;
import com.rabtman.common.base.App;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * @author Rabtman
 */
public class ScheduleBannerViewHolder implements MZViewHolder<ScheudleBanner> {

  private ImageView bannerImg;
  private TextView bannerTitle;

  @Override
  public View createView(Context context) {
    View view = LayoutInflater.from(context)
        .inflate(R.layout.acgschedule_item_schedule_main_banner, null);
    bannerImg = view.findViewById(R.id.banner_scheudle_img);
    bannerTitle = view.findViewById(R.id.banner_schedule_title);
    return view;
  }

  @Override
  public void onBind(Context context, int position, ScheudleBanner scheudleBanner) {
    ((App) context.getApplicationContext()).getAppComponent().imageLoader()
        .loadImage(context,
            GlideImageConfig
                .builder()
                .url(scheudleBanner.getImgUrl())
                .imagerView(bannerImg)
                .build());
    bannerTitle.setText(scheudleBanner.getName());
  }
}
