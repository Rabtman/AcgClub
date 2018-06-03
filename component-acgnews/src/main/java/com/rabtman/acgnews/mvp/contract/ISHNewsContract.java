package com.rabtman.acgnews.mvp.contract;

import com.rabtman.acgnews.mvp.model.entity.SHPage;
import com.rabtman.acgnews.mvp.model.entity.SHPostItem;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */
public interface ISHNewsContract {

  interface View extends IView {

    void showAcgNews(List<SHPostItem> shPostItemList);

    void showMoreAcgNews(List<SHPostItem> shPostItemList, boolean canLoadMore);

    void onLoadMoreFail();
  }

  interface Model extends IModel {

    Flowable<SHPage> getAcgNews(int pageIndex);
  }
}
