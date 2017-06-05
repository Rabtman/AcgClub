package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.jsoup.AcgNews;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */
public interface AcgNewsContract {

  interface View extends IView {

    void showAcgNews(List<AcgNews> acgNewsList);

    void showMoreAcgNews(List<AcgNews> acgNewsList);
  }

  interface Model extends IModel {

    Flowable<List<AcgNews>> getAcgNews(String typeUrl);
  }
}
