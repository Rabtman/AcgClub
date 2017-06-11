package com.rabtman.acgclub.mvp.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek.ScheduleItem;
import com.rabtman.acgclub.mvp.ui.activity.ScheduleDetailActivity;
import com.rabtman.common.base.widget.CommonItemDecoration;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleTimeMainAdapter extends BaseQuickAdapter<ScheduleWeek, BaseViewHolder> {

  private List<String> weekNames;

  public ScheduleTimeMainAdapter(List<String> weekNames, List<ScheduleWeek> items) {
    super(R.layout.item_schedule_time_main, items);
    this.weekNames = weekNames;
  }


  @Override
  protected void convert(BaseViewHolder helper, ScheduleWeek item) {
    helper.setText(R.id.tv_stm_weekname, weekNames.get(helper.getAdapterPosition()));
    //每天具体的番剧内容
    RecyclerView recyclerView = helper.getView(R.id.rcv_schedule_time_main);
    ScheduleTimeItemAdapter adapter = new ScheduleTimeItemAdapter(item.getScheduleItems());
    adapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ScheduleItem scheduleItem = (ScheduleItem) adapter.getItem(position);
        Intent intent = new Intent(mContext, ScheduleDetailActivity.class);
        intent.putExtra(IntentConstant.SCHEDULE_DETAIL_URL, scheduleItem.getAnimeLink());
        mContext.startActivity(intent);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    recyclerView.addItemDecoration(new CommonItemDecoration(1, CommonItemDecoration.UNIT_DP));
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }
}
