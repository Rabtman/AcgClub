package com.rabtman.acgschedule.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.R2;
import com.rabtman.acgschedule.base.constant.IntentConstant;
import com.rabtman.acgschedule.di.component.DaggerScheduleNewComponent;
import com.rabtman.acgschedule.di.module.ScheduleNewModule;
import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract.View;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew.ScheduleNewItem;
import com.rabtman.acgschedule.mvp.presenter.ScheduleNewPresenter;
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleNewAdapter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.widget.CommonItemDecoration;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.router.RouterConstants;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_NEW)
public class ScheduleNewActivity extends BaseActivity<ScheduleNewPresenter> implements
    View {

  @BindView(R2.id.toolbar)
  Toolbar toolbar;
  @BindView(R2.id.rcv_schedule_new)
  RecyclerView rcvScheduleNew;
  private ScheduleNewAdapter mAdapter;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerScheduleNewComponent.builder()
        .appComponent(appComponent)
        .scheduleNewModule(new ScheduleNewModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.acgschedule_activity_schedule_new;
  }

  @Override
  protected boolean useLoadSir() {
    return true;
  }

  @Override
  protected Object registerTarget() {
    return rcvScheduleNew;
  }

  @Override
  protected void initData() {
    setToolBar(toolbar, "本季新番");

    mAdapter = new ScheduleNewAdapter(mAppComponent.imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        ScheduleNewItem scheduleNewItem = (ScheduleNewItem) adapter.getData().get(position);
        Intent intent = new Intent(getBaseContext(), ScheduleDetailActivity.class);
        intent.putExtra(IntentConstant.SCHEDULE_DETAIL_URL, scheduleNewItem.getAnimeLink());
        startActivity(intent);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcvScheduleNew.addItemDecoration(new CommonItemDecoration(2, CommonItemDecoration.UNIT_DP));
    rcvScheduleNew.setLayoutManager(layoutManager);
    rcvScheduleNew.setAdapter(mAdapter);

    mPresenter.getScheduleNew();
  }

  @Override
  public void showScheduleNew(ScheduleNew scheduleNew) {
    mAdapter.setNewData(scheduleNew.getScheduleNewItems());
  }

}
