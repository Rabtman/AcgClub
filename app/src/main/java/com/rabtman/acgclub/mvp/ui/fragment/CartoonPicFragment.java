package com.rabtman.acgclub.mvp.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerAcgPicComponent;
import com.rabtman.acgclub.di.module.AcgPicItemModule;
import com.rabtman.acgclub.mvp.contract.AcgPicContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.MoePic.PicInfo;
import com.rabtman.acgclub.mvp.presenter.CartoonPicPresenter;
import com.rabtman.acgclub.mvp.ui.activity.MoePicDetailActivity;
import com.rabtman.acgclub.mvp.ui.adapter.CartoonItemAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman
 */
public class CartoonPicFragment extends BaseFragment<CartoonPicPresenter> implements
    View<PicInfo> {

  @BindView(R.id.rcv_cartoon_item)
  RecyclerView rcvCartoonItem;
  @BindView(R.id.swipe_refresh_cartoon)
  SwipeRefreshLayout swipeRefresh;
  private CartoonItemAdapter mAdapter;

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerAcgPicComponent.builder()
        .appComponent(appComponent)
        .acgPicItemModule(new AcgPicItemModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_cartoon;
  }

  @Override
  protected void initData() {
    mAdapter = new CartoonItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        PicInfo picInfo = (PicInfo) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), MoePicDetailActivity.class);
        intent.putExtra(IntentConstant.MOE_PIC_URL, picInfo.getThumbUrl());
        startActivity(intent);
      }
    });

    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    layoutManager.setOrientation(GridLayoutManager.VERTICAL);
    rcvCartoonItem.setLayoutManager(layoutManager);
    mAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
      @Override
      public void onLoadMoreRequested() {
        mPresenter.getMoreCartoonPictures();
      }
    }, rcvCartoonItem);
    rcvCartoonItem.setAdapter(mAdapter);

    swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        mPresenter.getCartoonPictures();
      }
    });
    setSwipeRefreshLayout(swipeRefresh);
    mPresenter.getCartoonPictures();
  }

  @Override
  public void showPictures(List<PicInfo> picInfos) {
    mAdapter.setNewData(picInfos);
  }

  @Override
  public void showMorePictures(List<PicInfo> picInfos, boolean canLoadMore) {
    mAdapter.addData(picInfos);
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
