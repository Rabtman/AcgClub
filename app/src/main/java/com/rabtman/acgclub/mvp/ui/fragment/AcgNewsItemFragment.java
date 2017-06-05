package com.rabtman.acgclub.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.di.component.DaggerAcgNewsItemComponent;
import com.rabtman.acgclub.di.module.AcgNewsItemModule;
import com.rabtman.acgclub.mvp.contract.AcgNewsContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNews;
import com.rabtman.acgclub.mvp.presenter.AcgNewsItemPresenter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgNewsItemFragment extends BaseFragment<AcgNewsItemPresenter> implements
    View {

  @BindView(R.id.rcv_news_item)
  RecyclerView rcvNewsItem;
  @BindView(R.id.swipe_refresh)
  SwipeRefreshLayout swipeRefresh;
  //当前资讯类型地址
  private String typeUrl;
  //当前页面位置
  private int pageNo = 1;

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerAcgNewsItemComponent.builder()
        .appComponent(appComponent)
        .acgNewsItemModule(new AcgNewsItemModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_news_item;
  }

  @Override
  protected void initData() {
    Bundle bundle = getArguments();
    String title = bundle.getString(IntentConstant.ACG_NEWS_TITLE, "");
    switch (SystemConstant.ACG_NEWS_TITLE.indexOf(title)) {
      case 0:
        typeUrl = getString(R.string.acg_news_dmxw, pageNo);
        break;
      case 1:
        typeUrl = getString(R.string.acg_news_dmxw, pageNo);
        break;
      case 2:
        typeUrl = getString(R.string.acg_news_dmxw, pageNo);
        break;
    }
  }

  @Override
  public void showAcgNews(List<AcgNews> acgNewsList) {

  }

  @Override
  public void showMoreAcgNews(List<AcgNews> acgNewsList) {

  }
}
