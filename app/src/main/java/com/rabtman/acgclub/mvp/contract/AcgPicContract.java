package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.jsoup.APic;
import com.rabtman.acgclub.mvp.model.jsoup.APic.PicInfo;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */
public interface AcgPicContract {

  interface View extends IView {

    void showPictures(List<PicInfo> picInfos);

    void showMorePictures(List<PicInfo> picInfos, boolean canLoadMore);

    void onLoadMoreFail();
  }

  interface Model extends IModel {

    Flowable<APic> getAcgPic(String url);
  }
}
