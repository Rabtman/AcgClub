package com.rabtman.acgclub.mvp.model;

import android.support.annotation.NonNull;
import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.mvp.contract.APicDetailContract;
import com.rabtman.acgclub.mvp.model.jsoup.APicDetail;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@ActivityScope
public class APicDetailModel extends BaseModel implements APicDetailContract.Model {

  @Inject
  public APicDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<APicDetail> getAPicDetail(final String url) {
    return Flowable.create(new FlowableOnSubscribe<APicDetail>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<APicDetail> e) throws Exception {
        Element html = Jsoup.connect(url).timeout(10000).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          APicDetail aPicDetail = JP.from(html, APicDetail.class);
          e.onNext(aPicDetail);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
