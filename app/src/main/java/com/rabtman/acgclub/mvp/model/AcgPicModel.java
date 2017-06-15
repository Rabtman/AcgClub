package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.mvp.contract.AcgPicContract;
import com.rabtman.acgclub.mvp.model.jsoup.APic;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
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
@FragmentScope
public class AcgPicModel extends BaseModel implements AcgPicContract.Model {

  @Inject
  public AcgPicModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<APic> getAcgPic(final String url) {
    return Flowable.create(new FlowableOnSubscribe<APic>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<APic> e) throws Exception {
        Element html = Jsoup.connect(url).get();
        APic aPic = JP.from(html, APic.class);
        e.onNext(aPic);
        e.onComplete();
      }
    }, BackpressureStrategy.BUFFER);
  }

}
