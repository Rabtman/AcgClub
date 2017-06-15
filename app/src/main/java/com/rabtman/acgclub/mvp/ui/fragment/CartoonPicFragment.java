package com.rabtman.acgclub.mvp.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerAcgPicComponent;
import com.rabtman.acgclub.di.module.AcgPicItemModule;
import com.rabtman.acgclub.mvp.contract.AcgPicContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.APic.PicInfo;
import com.rabtman.acgclub.mvp.presenter.AcgPicItemPresenter;
import com.rabtman.acgclub.mvp.ui.activity.APicDetailActivity;
import com.rabtman.acgclub.mvp.ui.adapter.AcgPicItemAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;
import java.util.List;

/**
 * @author Rabtman
 */
public class CartoonPicFragment extends BaseFragment<AcgPicItemPresenter> implements
    View {

  @BindView(R.id.rcv_cartoon_item)
  RecyclerView rcvCartoonItem;
  //@BindView(R.id.swipe_refresh)
  //SwipeRefreshLayout swipeRefresh;
  //当前资讯类型地址id
  private int typeUrlId;
  private AcgPicItemAdapter mAdapter;

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
    mAdapter = new AcgPicItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        PicInfo picInfo = (PicInfo) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), APicDetailActivity.class);
        intent.putExtra(IntentConstant.APIC_DETAIL_TITLE, picInfo.getTitle());
        intent.putExtra(IntentConstant.APIC_DETAIL_URL, picInfo.getContentLink());
        startActivity(intent);
      }
    });

    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
        StaggeredGridLayoutManager.VERTICAL);
    //rcvCartoonItem.addItemDecoration(new CommonItemDecoration(1, CommonItemDecoration.UNIT_DP));
    rcvCartoonItem.setLayoutManager(layoutManager);
    mAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
      @Override
      public void onLoadMoreRequested() {
        mPresenter.getMoreAcgPicList();
      }
    }, rcvCartoonItem);
    rcvCartoonItem.setAdapter(mAdapter);

    mPresenter.getAcgPicList();
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
  public void onLoadMoreFail() {
    mAdapter.loadMoreFail();
  }
}
