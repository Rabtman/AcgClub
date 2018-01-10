package com.rabtman.acgnews.mvp.contract;

import com.rabtman.acgnews.di.module.jsoup.AcgNews;
import com.rabtman.acgnews.di.module.jsoup.AcgNewsPage;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */
public interface AcgNewsContract {

  interface View extends IView {

    String getNewsUrl(int pageNo);

    void showAcgNews(List<AcgNews> acgNewsList);

    void showMoreAcgNews(List<AcgNews> acgNewsList, boolean canLoadMore);

    void onLoadMoreFail();
  }

  interface Model extends IModel {

    Flowable<AcgNewsPage> getAcgNews(String typeUrl);
  }
}
