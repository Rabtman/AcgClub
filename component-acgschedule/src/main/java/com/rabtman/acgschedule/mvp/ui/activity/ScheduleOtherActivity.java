package com.rabtman.acgschedule.mvp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.R2;
import com.rabtman.acgschedule.base.constant.IntentConstant;
import com.rabtman.acgschedule.di.component.DaggerScheduleOtherComponent;
import com.rabtman.acgschedule.di.module.ScheduleOtherModule;
import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract.View;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage;
import com.rabtman.acgschedule.mvp.presenter.ScheduleOtherPresenter;
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleOtherAdapter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.widget.CommonItemDecoration;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.router.RouterConstants;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_OTHER)
public class ScheduleOtherActivity extends BaseActivity<ScheduleOtherPresenter> implements
    View {

  @BindView(R2.id.toolbar)
  Toolbar toolbar;
  @BindView(R2.id.swipe_schedule_other)
  SwipeRefreshLayout swipeRefresh;
  @BindView(R2.id.rcv_schedule_other)
  RecyclerView rcvScheduleOther;
  private ScheduleOtherAdapter mAdapter;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerScheduleOtherComponent.builder()
        .appComponent(appComponent)
        .scheduleOtherModule(new ScheduleOtherModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.acgschedule_activity_schedule_other;
  }

  @Override
  protected boolean useLoadSir() {
    return true;
  }

  @Override
  protected void onPageRetry(android.view.View v) {
    mPresenter.getScheduleOther();
  }

  @Override
  protected Object registerTarget() {
    return rcvScheduleOther;
  }

  @Override
  protected void initData() {
    setToolBar(toolbar, "");

    String scheduleOtherUrl = getIntent().getStringExtra(IntentConstant.SCHEDULE_DETAIL_URL);
    if (TextUtils.isEmpty(scheduleOtherUrl)) {
      showError(R.string.msg_error_url_null);
      return;
    }

    mAdapter = new ScheduleOtherAdapter(mAppComponent.imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        ScheduleOtherPage.ScheduleOtherItem scheduleOtherItem = (ScheduleOtherPage.ScheduleOtherItem) adapter
            .getData().get(position);
        Intent intent = new Intent(getBaseContext(), ScheduleVideoActivity.class);
        intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL, scheduleOtherItem.getVideolLink());
        startActivity(intent);
      }
    });
    mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore() {
        mPresenter.getMoreScheduleOther();
      }
    });

    GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
    rcvScheduleOther.addItemDecoration(new CommonItemDecoration(2, CommonItemDecoration.UNIT_DP));
    rcvScheduleOther.setLayoutManager(layoutManager);
    rcvScheduleOther.setAdapter(mAdapter);

    swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        mPresenter.getScheduleOther();
      }
    });

    mPresenter.setCurScheduleOtherUrl(scheduleOtherUrl);
    mPresenter.getScheduleOther();
  }

  @Override
  public void showScheduleOther(ScheduleOtherPage scheduleOtherPage) {
    toolbar.setTitle(scheduleOtherPage.getTitle());
    mAdapter.setList(scheduleOtherPage.getScheduleOtherItems());
  }

  @Override
  public void showMoreScheduleOther(ScheduleOtherPage scheduleOtherPage, boolean canLoadMore) {
    mAdapter.addData(scheduleOtherPage.getScheduleOtherItems());
    mAdapter.getLoadMoreModule().loadMoreComplete();
    if (!canLoadMore) {
      mAdapter.getLoadMoreModule().loadMoreEnd();
    }
  }

  @Override
  public void onLoadMoreFail() {
    mAdapter.getLoadMoreModule().loadMoreFail();
  }

  @Override
  public void showLoading() {
    if (swipeRefresh != null) {
      if (!swipeRefresh.isRefreshing()) {
        swipeRefresh.setRefreshing(true);
      }
    } else {
      super.showLoading();
    }
  }

  @Override
  public void hideLoading() {
    if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
      swipeRefresh.setRefreshing(false);
    }
    super.hideLoading();
  }
}
