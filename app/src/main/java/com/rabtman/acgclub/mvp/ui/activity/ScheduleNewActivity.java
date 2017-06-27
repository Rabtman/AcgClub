package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerScheduleNewComponent;
import com.rabtman.acgclub.di.module.ScheduleNewModule;
import com.rabtman.acgclub.mvp.contract.ScheduleNewContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleNew;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleNew.ScheduleNewItem;
import com.rabtman.acgclub.mvp.presenter.ScheduleNewPresenter;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleNewAdapter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.widget.CommonItemDecoration;
import com.rabtman.common.di.component.AppComponent;

/**
 * @author Rabtman
 */
public class ScheduleNewActivity extends BaseActivity<ScheduleNewPresenter> implements
    View {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rcv_schedule_new)
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
    return R.layout.activity_schedule_new;
  }

  @Override
  protected void initData() {
    setToolBar(toolbar, "本季新番");

    mAdapter = new ScheduleNewAdapter(getAppComponent().imageLoader());
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

    mPresenter.getScheduleNew(getIntent().getStringExtra(IntentConstant.SCHEDULE_NEW_URL));
  }

  @Override
  public void showScheduleNew(ScheduleNew scheduleNew) {
    mAdapter.setNewData(scheduleNew.getScheduleNewItems());
  }

}
