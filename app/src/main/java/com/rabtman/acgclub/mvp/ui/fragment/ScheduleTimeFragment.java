package com.rabtman.acgclub.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.di.component.DaggerScheduleTimeComponent;
import com.rabtman.acgclub.di.module.ScheduleTimeModule;
import com.rabtman.acgclub.mvp.contract.ScheduleTimeContract.View;
import com.rabtman.acgclub.mvp.model.entity.ScheduleTimeItem;
import com.rabtman.acgclub.mvp.presenter.ScheduleTimePresenter;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleTimeAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleTimeFragment extends BaseFragment<ScheduleTimePresenter> implements
    View {


  @BindView(R.id.tab_schedule_time)
  TabLayout tabScheduleTime;
  @BindView(R.id.rcv_schedule_time)
  RecyclerView rcvScheduleTime;
  private ScheduleTimeAdapter mAdapter;
  private LinearLayoutManager mLayoutManager;
  private int currentHeaderPosition = 0;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_schedule_time;
  }

  @Override
  protected void initData() {
    for (String weekTitle : SystemConstant.SCHEDULE_WEEK_TITLE) {
      Tab tab = tabScheduleTime.newTab().setText(weekTitle);
      tabScheduleTime.addTab(tab);
    }
    tabScheduleTime.addOnTabSelectedListener(new OnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        if (mAdapter.getItemCount() > 0) {
          mLayoutManager
              .scrollToPositionWithOffset(mPresenter.getHeaderPositionByIndex(tab.getPosition()),
                  (int) mLayoutManager.computeScrollVectorForPosition(
                      mPresenter.getHeaderPositionByIndex(tab.getPosition())).y);
        }
      }

      @Override
      public void onTabUnselected(Tab tab) {

      }

      @Override
      public void onTabReselected(Tab tab) {

      }
    });
    mAdapter = new ScheduleTimeAdapter(null);

    mLayoutManager = new LinearLayoutManager(mContext);
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcvScheduleTime.setLayoutManager(mLayoutManager);

    rcvScheduleTime.setAdapter(mAdapter);

    rcvScheduleTime.addOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int fistVisibleItemPos = mLayoutManager.findFirstVisibleItemPosition();
        if (mAdapter.getItemCount() > 0) {
          ScheduleTimeItem scheduleTimeItem = mAdapter.getItem(fistVisibleItemPos);
          if (scheduleTimeItem != null && scheduleTimeItem.isHeader
              && currentHeaderPosition != fistVisibleItemPos) {
            currentHeaderPosition = fistVisibleItemPos;
            tabScheduleTime.getTabAt(scheduleTimeItem.headerIndex).select();
          }
        }
      }
    });

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
  public void showAcgScheduleInfo(List<ScheduleTimeItem> scheduleTimeItems) {
    mAdapter.setNewData(scheduleTimeItems);
  }
}
