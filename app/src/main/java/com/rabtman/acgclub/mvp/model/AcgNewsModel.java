package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.mvp.contract.AcgNewsContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNewsPage;
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
public class AcgNewsModel extends BaseModel implements AcgNewsContract.Model {

  @Inject
  public AcgNewsModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<AcgNewsPage> getAcgNews(final String typeUrl) {
    return Flowable.create(new FlowableOnSubscribe<AcgNewsPage>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<AcgNewsPage> e) throws Exception {
        Element html = Jsoup.connect(typeUrl).get();
        AcgNewsPage acgNewsPage = JP.from(html, AcgNewsPage.class);
        e.onNext(acgNewsPage);
        e.onComplete();
      }
    }, BackpressureStrategy.BUFFER);
  }
}
