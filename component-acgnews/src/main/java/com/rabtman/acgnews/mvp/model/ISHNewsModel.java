package com.rabtman.acgnews.mvp.model;

import android.text.TextUtils;
import com.rabtman.acgnews.api.AcgNewsService;
import com.rabtman.acgnews.mvp.contract.ISHNewsContract;
import com.rabtman.acgnews.mvp.model.entity.SHPage;
import com.rabtman.acgnews.mvp.model.entity.SHResponse;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.http.ApiException;
import com.rabtman.common.integration.IRepositoryManager;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class ISHNewsModel extends BaseModel implements ISHNewsContract.Model {

  @Inject
  public ISHNewsModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<SHPage> getAcgNews(int pageIndex) {
    return mRepositoryManager.obtainRetrofitService(AcgNewsService.class)
        .getISHNews(pageIndex)
        .compose(new FlowableTransformer<SHResponse<SHPage>, SHPage>() {
          @Override
          public Flowable<SHPage> apply(Flowable<SHResponse<SHPage>> httpResponseFlowable) {
            return httpResponseFlowable
                .flatMap(new Function<SHResponse<SHPage>, Flowable<SHPage>>() {
                  @Override
                  public Flowable<SHPage> apply(SHResponse<SHPage> response) {
                    LogUtil.d(response.toString());
                    if (!TextUtils.isEmpty(response.getErrMsg())) {
                      return Flowable.error(new ApiException(response.getErrMsg()));
                    } else if (response.getData() != null) {
                      return RxUtil.createData(response.getData());
                    } else {
                      return Flowable.error(new ApiException("数据加载失败"));
                    }
                  }
                });
          }
        });
  }
}
