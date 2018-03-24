package com.rabtman.acgcomic.mvp.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.base.constant.SystemConstant
import com.rabtman.acgcomic.mvp.model.dao.ComicDAO
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.ui.adapter.ComicCollectionAdapter
import com.rabtman.common.base.SimpleFragment
import com.rabtman.common.utils.RxUtil
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_COLLECTION)
class ComicCollectionFragment : SimpleFragment() {

    @BindView(R2.id.rcv_comic_collection)
    internal lateinit var rcvOacgComicItem: RecyclerView
    private lateinit var mAdapter: ComicCollectionAdapter
    private var mDisposable: Disposable? = null

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_fragment_comic_collection
    }

    override fun initData() {
        mAdapter = ComicCollectionAdapter(appComponent.imageLoader())
        rcvOacgComicItem.layoutManager = GridLayoutManager(context, 3)
        rcvOacgComicItem.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ComicCache?
            RouterUtils.getInstance()
                    .build(RouterConstants.PATH_COMIC_OACG_DETAIL)
                    .withParcelable(
                            IntentConstant.OACG_COMIC_ITEM,
                            Gson().fromJson(item?.comicDetailJson, OacgComicItem::class.java)
                    )
                    .navigation()
        }
        getOacgComicItems()
    }

    override fun onResume() {
        super.onResume()
        if (isInited && isVisible) {
            getOacgComicItems()
        }
    }

    override fun onPause() {
        if (mDisposable != null) {
            mDisposable!!.dispose()
        }
        super.onPause()
    }

    /**
     * 获取收藏的所有番剧信息并显示出来
     */
    private fun getOacgComicItems() {
        val dao = ComicDAO(
                appComponent
                        .repositoryManager()
                        .obtainRealmConfig(SystemConstant.DB_NAME)
        )
        mDisposable = dao.getComicCacheList()
                .compose(RxUtil.rxSchedulerHelper<List<ComicCache>>())
                .subscribeWith(object : ResourceSubscriber<List<ComicCache>>() {
                    override fun onNext(comicCacheList: List<ComicCache>) {
                        mAdapter.setNewData(comicCacheList)
                    }

                    override fun onError(t: Throwable) {
                        showError(R.string.msg_error_data_null)
                    }

                    override fun onComplete() {

                    }
                })
    }
}
