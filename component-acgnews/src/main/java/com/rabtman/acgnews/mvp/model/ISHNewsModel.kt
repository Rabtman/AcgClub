package com.rabtman.acgnews.mvp.model

import android.text.TextUtils
import com.rabtman.acgnews.api.AcgNewsService
import com.rabtman.acgnews.mvp.contract.ISHNewsContract
import com.rabtman.acgnews.mvp.model.entity.SHPage
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.http.ApiException
import com.rabtman.common.integration.IRepositoryManager
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ISHNewsModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ISHNewsContract.Model {
    override fun getAcgNews(pageIndex: Int): Flowable<SHPage> {
        return mRepositoryManager.obtainRetrofitService(AcgNewsService::class.java)
                .getISHNews(pageIndex, 15, 3)
                .compose { httpResponseFlowable ->
                    httpResponseFlowable
                            .flatMap { response ->
                                LogUtil.d(response.toString())
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
}