package com.rabtman.acgnews.mvp.ui.fragment;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.R2;
import com.rabtman.acgnews.base.constant.IntentConstant;
import com.rabtman.acgnews.di.component.DaggerZeroFiveNewsItemComponent;
import com.rabtman.acgnews.di.module.ZeroFiveNewsItemModule;
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract.View;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNews;
import com.rabtman.acgnews.mvp.presenter.ZeroFiveNewsItemPresenter;
import com.rabtman.acgnews.mvp.ui.activity.ZeroFiveNewsDetailActivity;
import com.rabtman.acgnews.mvp.ui.adapter.ZeroFiveNewsItemAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.base.widget.CommonItemDecoration;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman 羁绊资讯
 */
public class ZeroFiveNewsFragment extends BaseFragment<ZeroFiveNewsItemPresenter> implements
    View {

  @BindView(R2.id.rcv_news_item)
  RecyclerView rcvNewsItem;
  @BindView(R2.id.swipe_refresh_news)
  SwipeRefreshLayout swipeRefresh;
  private ZeroFiveNewsItemAdapter mAdapter;

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerZeroFiveNewsItemComponent.builder()
        .appComponent(appComponent)
        .zeroFiveNewsItemModule(new ZeroFiveNewsItemModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.acgnews_fragment_news_item;
  }

  @Override
  protected boolean useLoadSir() {
    return true;
  }

  @Override
  protected void onPageRetry(android.view.View v) {
    mPresenter.getAcgNewsList();
  }

  @Override
  protected void initData() {
    mAdapter = new ZeroFiveNewsItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        ZeroFiveNews zeroFiveNews = (ZeroFiveNews) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), ZeroFiveNewsDetailActivity.class);
        intent.putExtra(IntentConstant.ZERO_FIVE_NEWS_ITEM, zeroFiveNews);
        startActivity(intent);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcvNewsItem.addItemDecoration(new CommonItemDecoration(2, CommonItemDecoration.UNIT_DP));
    rcvNewsItem.setLayoutManager(layoutManager);
    mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore() {

        mPresenter.getMoreAcgNewsList();
      }
    });
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
    return getString(R.string.acgnews_url_zero_five, pageNo);
  }

  @Override
  public void showAcgNews(List<ZeroFiveNews> zeroFiveNewsList) {
    mAdapter.setList(zeroFiveNewsList);
  }

  @Override
  public void showMoreAcgNews(List<ZeroFiveNews> zeroFiveNewsList, boolean canLoadMore) {
    mAdapter.addData(zeroFiveNewsList);
    mAdapter.getLoadMoreModule().loadMoreComplete();
    if (!canLoadMore) {
      mAdapter.getLoadMoreModule().loadMoreEnd();
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
    mAdapter.getLoadMoreModule().loadMoreFail();
  }
}
