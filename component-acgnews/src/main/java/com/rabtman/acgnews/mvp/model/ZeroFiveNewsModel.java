package com.rabtman.acgnews.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsPage;
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
public class ZeroFiveNewsModel extends BaseModel implements ZeroFiveNewsContract.Model {

  @Inject
  public ZeroFiveNewsModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<ZeroFiveNewsPage> getAcgNews(final String typeUrl) {
    return Flowable.create(new FlowableOnSubscribe<ZeroFiveNewsPage>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ZeroFiveNewsPage> e) throws Exception {
        Element html = Jsoup.connect(typeUrl).get();
        if(html == null){
          e.onError(new Throwable("element html is null"));
        }else {
          ZeroFiveNewsPage zeroFiveNewsPage = JP.from(html, ZeroFiveNewsPage.class);
          e.onNext(zeroFiveNewsPage);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }

}
