package com.rabtman.acgschedule.mvp.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.R2;
import com.rabtman.acgschedule.base.constant.SystemConstant;
import com.rabtman.acgschedule.mvp.model.dao.ScheduleDAO;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleCollectionAdapter;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.common.utils.RxUtil;
import com.rabtman.router.RouterConstants;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;
import java.util.List;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COLLECTION_SCHEDULE)
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
    rcvScheduleCollection.setLayoutManager(new GridLayoutManager(getContext(), 2));
    rcvScheduleCollection.setAdapter(mAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
    getScheduleCollections();
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
    mDisposable = dao.getScheduleCollections()
        .compose(RxUtil.<List<ScheduleCollection>>rxSchedulerHelper())
        .subscribeWith(new ResourceSubscriber<List<ScheduleCollection>>() {
          @Override
          public void onNext(List<ScheduleCollection> scheduleCollections) {
            mAdapter.setNewData(scheduleCollections);
          }

          @Override
          public void onError(Throwable t) {
            showError(R.string.msg_error_data_null);
          }

          @Override
          public void onComplete() {

          }
        });
  }
}
