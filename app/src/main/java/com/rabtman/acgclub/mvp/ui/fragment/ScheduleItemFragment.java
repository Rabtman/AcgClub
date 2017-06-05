package com.rabtman.acgclub.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek.ScheduleItem;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleItemAdapter;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.common.base.widget.CommonItemDecoration;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleItemFragment extends SimpleFragment {

  @BindView(R.id.rcv_schedule_item)
  RecyclerView rcvScheduleItem;
  List<ScheduleItem> scheduleItems;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_schedule_item;
  }

  @Override
  protected void initData() {
    Bundle bundle = getArguments();
    ScheduleWeek scheduleWeek = bundle.getParcelable(IntentConstant.SCHEDULE_WEEK);

    ScheduleItemAdapter adapter = new ScheduleItemAdapter(scheduleWeek.getScheduleItems());
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcvScheduleItem.addItemDecoration(new CommonItemDecoration(1, CommonItemDecoration.UNIT_DP));
    rcvScheduleItem.setLayoutManager(layoutManager);
    rcvScheduleItem.setAdapter(adapter);
  }

}
