package com.rabtman.acgnews.mvp.contract;

import com.rabtman.acgnews.mvp.model.jsoup.AcgNewsDetail;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface AcgNewsDetailContract {

  interface View extends IView {

    void showNewsDetail(AcgNewsDetail acgNewsDetail);

    void showShareView();
  }

  interface Model extends IModel {

    Flowable<AcgNewsDetail> getAcgNewsDetail(String url);
  }
}
