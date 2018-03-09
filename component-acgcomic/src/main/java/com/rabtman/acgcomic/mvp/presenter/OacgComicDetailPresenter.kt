package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.Model
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.View
import com.rabtman.acgcomic.mvp.model.dao.OacgComicDAO
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class OacgComicDetailPresenter @Inject
constructor(model: OacgComicDetailContract.Model,
            rootView: OacgComicDetailContract.View) : BasePresenter<Model, View>(model, rootView) {

    private val DAO = OacgComicDAO()

    fun getOacgComicDetail(comicId: String) {
        addSubscribe(
                mModel.getComicDetail(comicId.toInt())
                        .compose(RxUtil.rxSchedulerHelper<List<OacgComicEpisode>>())
                        .subscribeWith(object : CommonSubscriber<List<OacgComicEpisode>>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicEpisodes: List<OacgComicEpisode>) {
                                LogUtil.d("getOacgComicDetail" + comicEpisodes.toString())
                                mView.showComicDetail(comicEpisodes)
                            }
                        })
        )
    }

    /**
     * 查询是否已经收藏过该漫画
     */
    fun isCollected(comicInfoId: String) {
        addSubscribe(
                DAO.getOacgComicItemById(comicInfoId)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : ResourceSubscriber<OacgComicItem>() {
                            override fun onNext(item: OacgComicItem?) {
                                LogUtil.d("OacgComicItem" + item.toString())
                                mView.showCollectView(item != null)
                            }

                            override fun onComplete() {
                            }

                            override fun onError(t: Throwable?) {
                            }

                        })
        )
    }

    /**
     * 漫画收藏、取消
     */
    fun collectOrCancelComic(comicInfo: OacgComicItem, isCollected: Boolean) {

        if (isCollected) {
            DAO.saveOacgComicItem(comicInfo)
        } else {
            DAO.deleteOacgComicItem(comicInfo)
        }
    }
}
