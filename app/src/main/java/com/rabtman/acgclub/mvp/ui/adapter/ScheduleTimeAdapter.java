package com.rabtman.acgclub.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.SystemConstant;
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
    //标题
    helper.setText(R.id.schedule_time_title, item.header);
    //标题图标
    ((TextView) helper.getView(R.id.schedule_time_title)).setCompoundDrawablesWithIntrinsicBounds(
        ContextCompat.getDrawable(mContext,
            SystemConstant.SCHEDULE_WEEK_LIST_TITLE_DRAWABLE[item.headerIndex]), null, null, null);
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleTimeItem item) {
    helper.setText(R.id.schedule_name, item.t.getName())
        .setText(R.id.schedule_episode, item.t.getEpisode())
        .addOnClickListener(R.id.schedule_episode);
  }
}
