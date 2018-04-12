package com.rabtman.acgschedule.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;
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

  public final static int DEFAULT_ITEM_COUNT = 20;
  //上次观看记录
  private int mLastRecordPos = -1;
  private int itemCount = DEFAULT_ITEM_COUNT;

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

  @Override
  public int getItemCount() {
    if (mData != null && mData.size() < DEFAULT_ITEM_COUNT) {
      itemCount = mData.size();
    }
    return itemCount;
  }

  /**
   * 设置展示的项目数
   */
  public void setItemCount() {
    this.itemCount = mData.size();
    notifyDataSetChanged();
  }

  /**
   * 设置最近一次观看记录,并刷新ui
   *
   * @param pos 最近一次观看记录的位置
   */
  public void setRecordPos(int pos) {
    if (getRecyclerView() == null) {
      return;
    }
    if (mLastRecordPos != -1) {
      TextView oldView = (TextView) getViewByPosition(mLastRecordPos, R.id.tv_sd_episode_name);
      if (oldView != null) {
        oldView.setBackgroundResource(R.drawable.acgschedule_btn_episode);
        oldView.setTextColor(ContextCompat.getColor(mContext, R.color.grey400));
      }
    }
    mLastRecordPos = pos;
    TextView newView = (TextView) getViewByPosition(mLastRecordPos, R.id.tv_sd_episode_name);
    if (newView != null) {
      newView.setBackgroundResource(R.drawable.acgschedule_btn_episode_record);
      newView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }
  }
}
