package com.rabtman.acgclub.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerScheduleDetailComponent;
import com.rabtman.acgclub.di.module.ScheduleDetailModule;
import com.rabtman.acgclub.mvp.contract.ScheduleDetailContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.acgclub.mvp.presenter.ScheduleDetailPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;

/**
 * @author Rabtman
 */
public class ScheduleDetailActivity extends BaseActivity<ScheduleDetailPresenter> implements
    View {

  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.tv_acg_detail_content)
  TextView tvAcgDetailContent;
  @BindView(R.id.tv_acg_detail_title)
  TextView tvAcgDetailTitle;
  @BindView(R.id.tv_acg_detail_labels)
  TextView tvAcgDetailLabels;
  @BindView(R.id.tv_acg_detail_datetime)
  TextView tvAcgDetailDatetime;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerScheduleDetailComponent.builder()
        .appComponent(appComponent)
        .scheduleDetailModule(new ScheduleDetailModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_acginfo_detail;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, "");

    mPresenter.getScheduleDetail(getIntent().getStringExtra(IntentConstant.SCHEDULE_DETAIL_URL));
  }

  @Override
  public void showScheduleDetail(ScheduleDetail acgNewsDetail) {

  }
}
