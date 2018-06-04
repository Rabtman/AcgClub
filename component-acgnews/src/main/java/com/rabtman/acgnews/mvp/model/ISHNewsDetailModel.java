package com.rabtman.acgnews.mvp.model;

import android.text.TextUtils;
import com.rabtman.acgnews.api.AcgNewsService;
import com.rabtman.acgnews.mvp.contract.ISHNewsDetailContract;
import com.rabtman.acgnews.mvp.model.entity.SHPostDetail;
import com.rabtman.acgnews.mvp.model.entity.SHResponse;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.http.ApiException;
import com.rabtman.common.integration.IRepositoryManager;
import com.rabtman.common.utils.RxUtil;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class ISHNewsDetailModel extends BaseModel implements ISHNewsDetailContract.Model {

  @Inject
  public ISHNewsDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }


  @Override
  public Flowable<SHPostDetail> getAcgNewsDetail(int postId) {
    return mRepositoryManager.obtainRetrofitService(AcgNewsService.class)
        .getISHNewsDetail(postId)
        .flatMap(new Function<SHResponse<SHPostDetail>, Flowable<SHPostDetail>>() {
          @Override
          public Flowable<SHPostDetail> apply(SHResponse<SHPostDetail> response) {
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
}
