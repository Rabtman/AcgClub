package com.rabtman.acgnews.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@ActivityScope
public class ZeroFiveNewsDetailModel extends BaseModel implements ZeroFiveNewsDetailContract.Model {

  @Inject
  public ZeroFiveNewsDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }


  @Override
  public Flowable<ZeroFiveNewsDetail> getAcgNewsDetail(final String url) {
    return Flowable.create(new FlowableOnSubscribe<ZeroFiveNewsDetail>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ZeroFiveNewsDetail> e) throws Exception {
        Element html = Jsoup.connect(url).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          ZeroFiveNewsDetail zeroFiveNewsDetail = JP.from(html, ZeroFiveNewsDetail.class);
          e.onNext(zeroFiveNewsDetail);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
