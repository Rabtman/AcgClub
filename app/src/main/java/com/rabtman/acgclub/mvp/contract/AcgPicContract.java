package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.jsoup.MoePic;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */
public interface AcgPicContract {

  interface View<T> extends IView {

    void showPictures(List<T> pictures);

    void showMorePictures(List<T> pictures, boolean canLoadMore);

    void onLoadMoreFail();
  }

  interface Model extends IModel {

    Flowable<MoePic> getMoePictures(String url);
  }
}
