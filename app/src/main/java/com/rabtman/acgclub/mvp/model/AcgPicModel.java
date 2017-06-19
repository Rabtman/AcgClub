package com.rabtman.acgclub.mvp.model;

import com.rabtman.acgclub.api.AcgService;
import com.rabtman.acgclub.mvp.contract.AcgPicContract;
import com.rabtman.acgclub.mvp.model.jsoup.APic;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;
import okhttp3.ResponseBody;

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
    return mRepositoryManager.obtainRetrofitService(AcgService.class)
        .getAcgPic(url)
        .map(new Function<ResponseBody, APic>() {
          @Override
          public APic apply(@NonNull ResponseBody body) throws Exception {
            /*Element element = Jsoup.parse(body.string());
            return JP.from(element, APic.class);*/
            return null;
          }
        });
    /*return Flowable.create(new FlowableOnSubscribe<APic>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<APic> e) throws Exception {
        Connection.Response response = Jsoup.connect(url)
            .timeout(10000)
            .execute();
        if (response.statusCode() == 200) {
          Element html = response.parse();
          if (html == null) {
            e.onError(new Throwable("element html is null"));
          } else {
            APic aPic = JP.from(html, APic.class);
            e.onNext(aPic);
            e.onComplete();
          }
        }else{
          e.onError(new ApiException(response.statusMessage(), response.statusCode()));
        }
      }
    }, BackpressureStrategy.BUFFER);*/
  }

}
