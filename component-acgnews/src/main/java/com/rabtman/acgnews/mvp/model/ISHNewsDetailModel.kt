package com.rabtman.acgnews.mvp.model

import android.text.TextUtils
import com.rabtman.acgnews.api.AcgNewsService
import com.rabtman.acgnews.mvp.contract.ISHNewsDetailContract
import com.rabtman.acgnews.mvp.model.entity.SHPostDetail
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.http.ApiException
import com.rabtman.common.integration.IRepositoryManager
import com.rabtman.common.utils.RxUtil
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ISHNewsDetailModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ISHNewsDetailContract.Model {
    override fun getAcgNewsDetail(postId: Int): Flowable<SHPostDetail> {
        return mRepositoryManager.obtainRetrofitService(AcgNewsService::class.java)
                .getISHNewsDetail(postId)
                .flatMap { response ->
                    if (!TextUtils.isEmpty(response.errMsg)) {
                        Flowable.error(ApiException(response.errMsg))
                    } else if (response.data != null) {
                        RxUtil.createData(response.data)
                    } else {
                        Flowable.error(ApiException("数据加载失败"))
                    }
                }
    }
}