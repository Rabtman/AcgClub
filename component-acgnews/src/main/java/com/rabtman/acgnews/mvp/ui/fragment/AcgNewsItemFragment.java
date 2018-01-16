package com.rabtman.acgnews.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.R2;
import com.rabtman.acgnews.base.constant.IntentConstant;
import com.rabtman.acgnews.base.constant.SystemConstant;
import com.rabtman.acgnews.di.component.DaggerAcgNewsItemComponent;
import com.rabtman.acgnews.di.module.AcgNewsItemModule;
import com.rabtman.acgnews.di.module.jsoup.AcgNews;
import com.rabtman.acgnews.mvp.contract.AcgNewsContract.View;
import com.rabtman.acgnews.mvp.presenter.AcgNewsItemPresenter;
import com.rabtman.acgnews.mvp.ui.activity.AcgInfoDetailActivity;
import com.rabtman.acgnews.mvp.ui.adapter.AcgNewsItemAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.base.widget.CommonItemDecoration;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgNewsItemFragment extends BaseFragment<AcgNewsItemPresenter> implements
    View {

  @BindView(R2.id.rcv_news_item)
  RecyclerView rcvNewsItem;
  @BindView(R2.id.swipe_refresh_news)
  SwipeRefreshLayout swipeRefresh;
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
    int titlePos = SystemConstant.ACG_NEWS_TITLE.indexOf(title);
    if (titlePos == 0) {
      typeUrlId = R.string.acg_news_dmxw;
    } else if (titlePos == 1) {
      typeUrlId = R.string.acg_news_yjdt;
    } else if (titlePos == 2) {
      typeUrlId = R.string.acg_news_qtrd;
    }

    mAdapter = new AcgNewsItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        AcgNews acgNews = (AcgNews) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), AcgInfoDetailActivity.class);
        intent.putExtra(IntentConstant.ACG_NEWS_DETAIL_ITEM, acgNews);
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

    swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        mPresenter.getAcgNewsList();
      }
    });
    setSwipeRefreshLayout(swipeRefresh);
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
  public void showError(String message) {
    if (swipeRefresh.isRefreshing()) {
      swipeRefresh.setRefreshing(false);
    }
    super.showError(message);
  }

  @Override
  public void onLoadMoreFail() {
    mAdapter.loadMoreFail();
  }
}
