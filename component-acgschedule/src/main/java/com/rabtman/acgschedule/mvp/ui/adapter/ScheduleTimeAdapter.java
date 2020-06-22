package com.rabtman.acgschedule.mvp.ui.adapter;

import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.base.constant.SystemConstant;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleTimeItem;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rabtman
 */

public class ScheduleTimeAdapter extends BaseSectionQuickAdapter<ScheduleTimeItem, BaseViewHolder> {

  public ScheduleTimeAdapter(List<ScheduleTimeItem> data) {
    super(R.layout.acgschedule_item_schedule_time_item,
        R.layout.acgschedule_item_schedule_time_header, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, ScheduleTimeItem item) {
    helper.setText(R.id.schedule_name, item.data.getName())
        .setText(R.id.schedule_episode, item.data.getEpisode());
  }

  @Override
  protected void convertHeader(@NotNull BaseViewHolder helper,
      @NotNull ScheduleTimeItem item) {
    //标题
    helper.setText(R.id.schedule_time_title, item.header);
    //标题图标
    ((TextView) helper.getView(R.id.schedule_time_title)).setCompoundDrawablesWithIntrinsicBounds(
        ContextCompat.getDrawable(getContext(),
            SystemConstant.SCHEDULE_WEEK_LIST_TITLE_DRAWABLE[item.headerIndex]), null, null, null);
  }
}
