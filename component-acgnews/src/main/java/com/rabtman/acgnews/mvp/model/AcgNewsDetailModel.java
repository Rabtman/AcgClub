package com.rabtman.acgnews.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgnews.mvp.contract.AcgNewsDetailContract;
import com.rabtman.acgnews.mvp.model.jsoup.AcgNewsDetail;
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
public class AcgNewsDetailModel extends BaseModel implements AcgNewsDetailContract.Model {

  @Inject
  public AcgNewsDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }


  @Override
  public Flowable<AcgNewsDetail> getAcgNewsDetail(final String url) {
    return Flowable.create(new FlowableOnSubscribe<AcgNewsDetail>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<AcgNewsDetail> e) throws Exception {
        Element html = Jsoup.connect(url).timeout(10000).get();
        if(html == null){
          e.onError(new Throwable("element html is null"));
        }else {
          AcgNewsDetail acgNewsDetail = JP.from(html, AcgNewsDetail.class);
          e.onNext(acgNewsDetail);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
