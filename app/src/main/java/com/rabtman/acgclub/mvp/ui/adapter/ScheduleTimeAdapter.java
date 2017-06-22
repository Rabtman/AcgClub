package com.rabtman.acgclub.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.model.entity.ScheduleTimeItem;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleTimeAdapter extends BaseSectionQuickAdapter<ScheduleTimeItem, BaseViewHolder> {

  public ScheduleTimeAdapter(List<ScheduleTimeItem> data) {
    super(R.layout.item_schedule_time_item, R.layout.item_schedule_time_header, data);
  }

  @Override
  protected void convertHead(BaseViewHolder helper, ScheduleTimeItem item) {
    helper.setText(R.id.schedule_time_title, item.header);
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleTimeItem item) {
    helper.setText(R.id.schedule_name, item.t.getName())
        .setText(R.id.schedule_episode, item.t.getEpisode());
  }
}
