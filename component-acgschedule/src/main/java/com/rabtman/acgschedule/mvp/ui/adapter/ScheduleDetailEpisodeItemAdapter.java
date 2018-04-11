package com.rabtman.acgschedule.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail.ScheduleEpisode;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleDetailEpisodeItemAdapter extends
    BaseQuickAdapter<ScheduleEpisode, BaseViewHolder> {

  //上次观看记录
  private int mLastRecordPos = -1;

  public ScheduleDetailEpisodeItemAdapter(List<ScheduleEpisode> data) {
    super(R.layout.acgschedule_item_schedule_detail_episode, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleEpisode item) {
    helper.setText(R.id.tv_sd_episode_name, item.getName());
    if (helper.getAdapterPosition() == mLastRecordPos) {
      helper.setBackgroundRes(R.id.tv_sd_episode_name, R.drawable.acgschedule_btn_episode_record)
          .setTextColor(R.id.tv_sd_episode_name,
              ContextCompat.getColor(mContext, R.color.colorPrimary));
    } else {
      helper.setBackgroundRes(R.id.tv_sd_episode_name, R.drawable.acgschedule_btn_episode)
          .setTextColor(R.id.tv_sd_episode_name, ContextCompat.getColor(mContext, R.color.grey400));
    }
  }

  /**
   * 设置最近一次观看记录
   *
   * @param pos 最近一次观看记录的位置
   */
  public void setRecordPos(int pos) {
    if (mLastRecordPos != -1) {
      notifyItemChanged(mLastRecordPos);
    }
    mLastRecordPos = pos;
    notifyItemChanged(pos);
  }
}
