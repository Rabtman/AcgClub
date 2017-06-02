package com.rabtman.acgclub.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek.ScheduleItem;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleItemAdapter extends BaseQuickAdapter<ScheduleItem, BaseViewHolder> {

  public ScheduleItemAdapter(List<ScheduleItem> items) {
    super(R.layout.item_schedule_item, items);
  }


  @Override
  protected void convert(BaseViewHolder helper, ScheduleItem item) {
    helper.setText(R.id.schedule_name, item.getName())
        .setText(R.id.schedule_episode, item.getEpisode());
  }
}
