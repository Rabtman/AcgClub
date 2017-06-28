package com.rabtman.acgclub.mvp.ui.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerFictionComponent;
import com.rabtman.acgclub.di.module.FictionModule;
import com.rabtman.acgclub.mvp.contract.FictionContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.Fiction;
import com.rabtman.acgclub.mvp.presenter.FictionPresenter;
import com.rabtman.acgclub.mvp.ui.activity.FictionSearchActivity;
import com.rabtman.acgclub.mvp.ui.adapter.FictionItemAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;

/**
 * @author Rabtman
 */
public class FictionFragment extends BaseFragment<FictionPresenter> implements
    View {


  @BindView(R.id.rcv_fiction_recent)
  RecyclerView rcvFictionRecent;
  @BindView(R.id.btn_search)
  FloatingActionButton btnSearch;
  private FictionItemAdapter mAdapter;

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerFictionComponent.builder()
        .appComponent(appComponent)
        .fictionModule(new FictionModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_fiction;
  }

  @Override
  protected void initData() {
    mAdapter = new FictionItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        /*PicInfo picInfo = (PicInfo) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), MoePicDetailActivity.class);
        intent.putExtra(IntentConstant.MOE_PIC_URL, picInfo.getThumbUrl());
        startActivity(intent);*/
      }
    });

    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    layoutManager.setOrientation(GridLayoutManager.VERTICAL);
    rcvFictionRecent.setLayoutManager(layoutManager);
    rcvFictionRecent.setAdapter(mAdapter);

    mPresenter.getFictionRecent();
  }

  @Override
  public void showFictionRecent(Fiction fiction) {
    mAdapter.setNewData(fiction.getFictionItems());
  }

  @OnClick(R.id.btn_search)
  public void onViewClicked() {
    startActivity(new Intent(getContext(), FictionSearchActivity.class));
  }
}
