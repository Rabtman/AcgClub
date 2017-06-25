package com.rabtman.acgclub.mvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerScheduleMainComponent;
import com.rabtman.acgclub.di.module.ScheduleMainModule;
import com.rabtman.acgclub.mvp.contract.ScheduleMainContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.DilidiliInfo;
import com.rabtman.acgclub.mvp.presenter.ScheduleMainPresenter;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleBannerViewHolder;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleRecommandAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * @author Rabtman
 */

public class ScheduleMainFragment extends BaseFragment<ScheduleMainPresenter> implements
    View {

  @BindView(R.id.swipe_refresh_schedule_main)
  SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.banner_schedule)
  MZBannerView bannerSchedule;
  @BindView(R.id.tv_schedule_search)
  TextView tvScheduleSearch;
  @BindView(R.id.tv_schedule_time)
  TextView tvScheduleTime;
  @BindView(R.id.tv_schedule_new)
  TextView tvScheduleNew;
  @BindView(R.id.rcv_schedule_recommand)
  RecyclerView rcvScheduleRecommand;
  @BindView(R.id.tv_recent_more)
  TextView tvRecentMore;
  @BindView(R.id.rcv_schedule_recent)
  RecyclerView rcvScheduleRecent;

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerScheduleMainComponent.builder()
        .appComponent(appComponent)
        .scheduleMainModule(new ScheduleMainModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_schedule_main;
  }

  @Override
  protected void initData() {
    swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        mPresenter.getDilidiliInfo();
      }
    });
    setSwipeRefreshLayout(swipeRefresh);
    mPresenter.getDilidiliInfo();
  }

  @Override
  public void onResume() {
    super.onResume();
    bannerSchedule.start();
  }

  @Override
  public void onPause() {
    super.onPause();
    bannerSchedule.pause();
  }

  @OnClick({R.id.tv_schedule_search, R.id.tv_schedule_time, R.id.tv_schedule_new,
      R.id.tv_recent_more})
  public void onViewClicked(android.view.View view) {
    switch (view.getId()) {
      case R.id.tv_schedule_search:
        break;
      case R.id.tv_schedule_time:
        break;
      case R.id.tv_schedule_new:
        break;
      case R.id.tv_recent_more:
        break;
    }
  }

  @Override
  public void showDilidiliInfo(DilidiliInfo dilidiliInfo) {
    //轮播栏
    bannerSchedule.setIndicatorVisible(false);
    bannerSchedule.setPages(dilidiliInfo.getScheudleBanners(), new MZHolderCreator() {
      @Override
      public MZViewHolder createViewHolder() {
        return new ScheduleBannerViewHolder();
      }
    });
    bannerSchedule.start();
    //近期推荐
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    ScheduleRecommandAdapter scheduleRecommandAdapter = new ScheduleRecommandAdapter(
        getAppComponent().imageLoader(), dilidiliInfo.getScheduleRecommands());
    rcvScheduleRecommand.setLayoutManager(linearLayoutManager);
    rcvScheduleRecommand.setAdapter(scheduleRecommandAdapter);
  }
}
