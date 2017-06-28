package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.api.AcgService;
import com.rabtman.acgclub.mvp.contract.FictionSearchContract;
import com.rabtman.acgclub.mvp.model.jsoup.Fiction;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;

/**
 * @author Rabtman
 */
@ActivityScope
public class FictionSearchModel extends BaseModel implements FictionSearchContract.Model {

  @Inject
  public FictionSearchModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<Fiction> getSearchResult(String url) {
    return mRepositoryManager.obtainRetrofitService(AcgService.class)
        .getFictionRecent(url)
        .map(new Function<ResponseBody, Fiction>() {
          @Override
          public Fiction apply(@NonNull ResponseBody body) throws Exception {
            return JP.from(Jsoup.parse(body.string()), Fiction.class);
          }
        });
  }
}
