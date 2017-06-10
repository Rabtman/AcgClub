package com.rabtman.acgclub.mvp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerScheduleTimeComponent;
import com.rabtman.acgclub.di.module.ScheduleTimeModule;
import com.rabtman.acgclub.mvp.contract.ScheduleTimeContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.acgclub.mvp.presenter.ScheduleTimePresenter;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleTimeMainAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;

/**
 * @author Rabtman
 */

public class ScheduleTimeFragment extends BaseFragment<ScheduleTimePresenter> implements
    View {

  @BindView(R.id.rcv_schedule_time_main)
  RecyclerView rcvScheduleTimeMain;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_schedule_time_main;
  }

  @Override
  protected void initData() {
    mPresenter.getAcgSchedule();
  }

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerScheduleTimeComponent
        .builder()
        .appComponent(appComponent)
        .scheduleTimeModule(new ScheduleTimeModule(this))
        .build()
        .inject(this);
  }

  @Override
  public void showAcgScheduleInfo(AcgScheduleInfo info) {
    ScheduleTimeMainAdapter adapter = new ScheduleTimeMainAdapter(info.getWeekName(),
        info.getScheduleWeek());

    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    rcvScheduleTimeMain.setLayoutManager(layoutManager);

    PagerSnapHelper snapHelper = new PagerSnapHelper();
    snapHelper.attachToRecyclerView(rcvScheduleTimeMain);

    rcvScheduleTimeMain.setAdapter(adapter);
  }
}
