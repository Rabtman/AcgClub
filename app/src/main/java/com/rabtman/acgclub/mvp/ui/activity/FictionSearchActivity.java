package com.rabtman.acgclub.mvp.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerFictionSearchComponent;
import com.rabtman.acgclub.di.module.FictionSearchModule;
import com.rabtman.acgclub.mvp.contract.FictionSearchContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.Fiction;
import com.rabtman.acgclub.mvp.presenter.FictionSearchPresenter;
import com.rabtman.acgclub.mvp.ui.adapter.FictionItemAdapter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;

/**
 * @author Rabtman
 */
public class FictionSearchActivity extends BaseActivity<FictionSearchPresenter> implements
    View {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rcv_fiction_search)
  RecyclerView rcvFictionSearch;
  private FictionItemAdapter mAdapter;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerFictionSearchComponent.builder()
        .appComponent(appComponent)
        .fictionSearchModule(new FictionSearchModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_fiction_search;
  }

  @Override
  protected void initData() {
    setToolBar(toolbar, "");
    mAdapter = new FictionItemAdapter(getAppComponent().imageLoader());
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
        /*PicInfo picInfo = (PicInfo) adapter.getData().get(position);
        Intent intent = new Intent(getContext(), );
        intent.putExtra(IntentConstant.MOE_PIC_URL, picInfo.getThumbUrl());
        startActivity(intent);*/
      }
    });

    GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
    layoutManager.setOrientation(GridLayoutManager.VERTICAL);
    rcvFictionSearch.setLayoutManager(layoutManager);
    rcvFictionSearch.setAdapter(mAdapter);

  }

  @Override
  public void showSearchResult(Fiction fiction) {
    mAdapter.setNewData(fiction.getFictionItems());
  }

}
