package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.mvp.contract.AcgNewsContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNews;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import java.util.List;
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
  public Flowable<List<AcgNews>> getAcgNews(final String typeUrl) {
    return Flowable.create(new FlowableOnSubscribe<List<AcgNews>>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<List<AcgNews>> e) throws Exception {
        Element html = Jsoup.connect(typeUrl).get();
        List<AcgNews> acgNewsList = JP.fromList(html, AcgNews.class);
        e.onNext(acgNewsList);
        e.onComplete();
      }
    }, BackpressureStrategy.BUFFER);
  }
}
