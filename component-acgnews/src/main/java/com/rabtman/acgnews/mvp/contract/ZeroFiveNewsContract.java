package com.rabtman.acgnews.mvp.contract;

import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNews;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsPage;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */
public interface ZeroFiveNewsContract {

  interface View extends IView {

    String getNewsUrl(int pageNo);

    void showAcgNews(List<ZeroFiveNews> zeroFiveNewsList);

    void showMoreAcgNews(List<ZeroFiveNews> zeroFiveNewsList, boolean canLoadMore);

    void onLoadMoreFail();
  }

  interface Model extends IModel {

    Flowable<ZeroFiveNewsPage> getAcgNews(String typeUrl);
  }
}
