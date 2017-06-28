package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.jsoup.Fiction;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface FictionSearchContract {

  interface View extends IView {

    void showSearchResult(Fiction fiction);
  }

  interface Model extends IModel {

    Flowable<Fiction> getSearchResult(String keyword);
  }
}
