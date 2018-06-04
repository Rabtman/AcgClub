package com.rabtman.acgnews.mvp.contract;

import com.rabtman.acgnews.mvp.model.entity.SHPostDetail;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface ISHNewsDetailContract {

  interface View extends IView {

    void showNewsDetail(SHPostDetail zeroFiveNewsDetail);

    void showShareView();
  }

  interface Model extends IModel {

    Flowable<SHPostDetail> getAcgNewsDetail(int postId);
  }
}
