package com.rabtman.acgschedule.mvp.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.R2;
import com.rabtman.acgschedule.base.constant.IntentConstant;
import com.rabtman.acgschedule.base.constant.SystemConstant;
import com.rabtman.acgschedule.mvp.model.dao.ScheduleDAO;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache;
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleCollectionAdapter;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.common.base.widget.loadsir.EmptyCollectionCallback;
import com.rabtman.common.utils.RxUtil;
import com.rabtman.router.RouterConstants;
import com.rabtman.router.RouterUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;
import java.util.List;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_COLLECTION)
public class ScheduleCollectionFragment extends SimpleFragment {

  @BindView(R2.id.rcv_schedule_collection)
  RecyclerView rcvScheduleCollection;
  private ScheduleCollectionAdapter mAdapter;
  private Disposable mDisposable;

  @Override
  protected int getLayoutId() {
    return R.layout.acgschedule_fragment_schedule_collection;
  }

  @Override
  protected void initData() {
    mAdapter = new ScheduleCollectionAdapter(getAppComponent().imageLoader());
    rcvScheduleCollection.setLayoutManager(new GridLayoutManager(getContext(), 3));
    rcvScheduleCollection.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ScheduleCache item = (ScheduleCache) adapter.getItem(position);
        RouterUtils.getInstance()
            .build(RouterConstants.PATH_SCHEDULE_DETAIL)
            .withString(IntentConstant.SCHEDULE_DETAIL_URL, item.getScheduleUrl())
            .navigation();
      }
    });
    getScheduleCollections();
  }

  @Override
  protected boolean useLoadSir() {
    return true;
  }

  @Override
  protected void onPageRetry(View v) {
    getScheduleCollections();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (isInited && isVisible) {
      getScheduleCollections();
    }
  }

  @Override
  public void onPause() {
    if (mDisposable != null) {
      mDisposable.dispose();
    }
    super.onPause();
  }

  /**
   * 获取收藏的所有番剧信息并显示出来
   */
  private void getScheduleCollections() {
    ScheduleDAO dao = new ScheduleDAO(
        getAppComponent()
            .repositoryManager()
            .obtainRealmConfig(SystemConstant.DB_NAME)
    );
    mDisposable = dao.getScheduleCollectCaches()
        .compose(RxUtil.<List<ScheduleCache>>rxSchedulerHelper())
        .subscribeWith(new ResourceSubscriber<List<ScheduleCache>>() {
          @Override
          public void onNext(List<ScheduleCache> scheduleCaches) {
            mAdapter.setNewData(scheduleCaches);
            if (scheduleCaches.size() > 0) {
              showPageContent();
            } else {
              showPageEmpty();
            }
          }

          @Override
          public void onError(Throwable t) {
            showError(R.string.msg_error_data_null);
            showPageError();
          }

          @Override
          public void onComplete() {

          }
        });
  }

  @Override
  public void showPageEmpty() {
    if (mLoadService != null) {
      mLoadService.showCallback(EmptyCollectionCallback.class);
    }
  }
}
