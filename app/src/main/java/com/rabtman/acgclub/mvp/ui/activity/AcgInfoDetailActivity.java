package com.rabtman.acgclub.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerAcgNewsDetailComponent;
import com.rabtman.acgclub.di.module.AcgNewsDetailModule;
import com.rabtman.acgclub.mvp.contract.AcgNewsDetailContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNewsDetail;
import com.rabtman.acgclub.mvp.presenter.AcgNewsDetailPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;
import com.zzhoujay.richtext.RichText;

/**
 * @author Rabtman
 */
public class AcgInfoDetailActivity extends BaseActivity<AcgNewsDetailPresenter> implements
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
    DaggerAcgNewsDetailComponent.builder()
        .appComponent(appComponent)
        .acgNewsDetailModule(new AcgNewsDetailModule(this))
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

    mPresenter.getNewsDetail(getIntent().getStringExtra(IntentConstant.ACG_NEWS_DETAIL_URL));
  }

  @Override
  public void showNewsDetail(AcgNewsDetail acgNewsDetail) {
    tvAcgDetailTitle.setText(getIntent().getStringExtra(IntentConstant.ACG_NEWS_DETAIL_TITLE));
    tvAcgDetailDatetime
        .setText(getIntent().getStringExtra(IntentConstant.ACG_NEWS_DETAIL_DATETIME));
    //文章来源信息
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i <= 3; i++) {
      String text = acgNewsDetail.getLabels().get(i);
      stringBuilder.append(text);
      stringBuilder.append("  ");
    }
    tvAcgDetailLabels.setText(stringBuilder.toString());
    //文章内容
    RichText.fromHtml(acgNewsDetail.getContent())
        .into(tvAcgDetailContent);
  }
}
