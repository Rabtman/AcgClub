package com.rabtman.acgnews.mvp.contract;

import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface ZeroFiveNewsDetailContract {

  interface View extends IView {

    void showNewsDetail(ZeroFiveNewsDetail zeroFiveNewsDetail);

    void showShareView();
  }

  interface Model extends IModel {

    Flowable<ZeroFiveNewsDetail> getAcgNewsDetail(String url);
  }
}
