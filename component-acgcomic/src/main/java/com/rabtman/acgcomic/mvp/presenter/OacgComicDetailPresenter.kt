package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.Model
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.View
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
                mModel.getLocalOacgComicItemById(comicInfoId)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : ResourceSubscriber<OacgComicItem>() {

                            private var isCollected = false

                            override fun onNext(item: OacgComicItem?) {
                                isCollected = (item != null)
                            }

                            override fun onComplete() {
                                mView.showCollectView(isCollected)
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
        addSubscribe(
                mModel.addOrDeleteLocalOacgComicItem(comicInfo, isCollected.not())
                        .subscribe({
                            mView.showCollectView(isCollected.not())
                            if (isCollected.not()) {
                                mView.showMsg(R.string.msg_success_collect_add)
                            } else {
                                mView.showMsg(R.string.msg_success_collect_cancel)
                            }
                        }, { throwable ->
                            throwable.printStackTrace()
                            if (isCollected.not()) {
                                mView.showError(R.string.msg_error_collect_add)
                            } else {
                                mView.showError(R.string.msg_error_collect_cancel)
                            }
                        })
        )
    }
}
