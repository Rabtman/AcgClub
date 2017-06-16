package com.rabtman.acgclub.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.di.component.DaggerAcgNewsItemComponent;
import com.rabtman.acgclub.di.module.AcgNewsItemModule;
import com.rabtman.acgclub.mvp.contract.AcgNewsContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNews;
import com.rabtman.acgclub.mvp.presenter.AcgNewsItemPresenter;
import com.rabtman.acgclub.mvp.ui.activity.AcgInfoDetailActivity;
import com.rabtman.acgclub.mvp.ui.adapter.AcgNewsItemAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.base.widget.CommonItemDecoration;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgNewsItemFragment extends BaseFragment<AcgNewsItemPresenter> implements
    View {

  @BindView(R.id.rcv_news_item)
  RecyclerView rcvNewsItem;
  //@BindView(R.id.swipe_refresh)
  //SwipeRefreshLayout swipeRefresh;
  //当前资讯类型地址id
  private int typeUrlId;
  private AcgNewsItemAdapter mAdapter;

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
        typeUrlId = R.string.acg_news_dmxw;
        break;
      case 1:
        typeUrlId = R.string.acg_news_yjdt;
        break;
      case 2:
        typeUrlId = R.string.acg_news_qtrd;
        break;
    }

    mAdapter = new AcgNewsItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        AcgNews acgNews = (AcgNews) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), AcgInfoDetailActivity.class);
        intent.putExtra(IntentConstant.ACG_NEWS_DETAIL_TITLE, acgNews.getTitle());
        intent.putExtra(IntentConstant.ACG_NEWS_DETAIL_DATETIME, acgNews.getDateTime());
        intent.putExtra(IntentConstant.ACG_NEWS_DETAIL_URL, acgNews.getContentLink());
        startActivity(intent);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcvNewsItem.addItemDecoration(new CommonItemDecoration(2, CommonItemDecoration.UNIT_DP));
    rcvNewsItem.setLayoutManager(layoutManager);
    mAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
      @Override
      public void onLoadMoreRequested() {
        mPresenter.getMoreAcgNewsList();
      }
    }, rcvNewsItem);
    rcvNewsItem.setAdapter(mAdapter);

    mPresenter.getAcgNewsList();
  }

  @Override
  public String getNewsUrl(int pageNo) {
    return getString(typeUrlId, pageNo);
  }

  @Override
  public void showAcgNews(List<AcgNews> acgNewsList) {
    mAdapter.setNewData(acgNewsList);
  }

  @Override
  public void showMoreAcgNews(List<AcgNews> acgNewsList, boolean canLoadMore) {
    mAdapter.addData(acgNewsList);
    mAdapter.loadMoreComplete();
    if (!canLoadMore) {
      mAdapter.loadMoreEnd();
    }
  }

  @Override
  public void onLoadMoreFail() {
    mAdapter.loadMoreFail();
  }
}
