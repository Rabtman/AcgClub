package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.api.AcgService;
import com.rabtman.acgclub.mvp.contract.AcgPicContract;
import com.rabtman.acgclub.mvp.model.jsoup.MoePic;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
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
@FragmentScope
public class AcgPicModel extends BaseModel implements AcgPicContract.Model {

  @Inject
  public AcgPicModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<MoePic> getMoePictures(final String url) {
    return mRepositoryManager.obtainRetrofitService(AcgService.class)
        .getAcgPic(url)
        .map(new Function<ResponseBody, MoePic>() {
          @Override
          public MoePic apply(@NonNull ResponseBody body) throws Exception {
            return JP.from(Jsoup.parse(body.string()), MoePic.class);
          }
        });
  }

}
