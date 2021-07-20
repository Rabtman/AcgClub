package com.rabtman.acgschedule.mvp.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.base.constant.SystemConstant
import com.rabtman.acgschedule.mvp.model.dao.ScheduleDAO
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleCollectionAdapter
import com.rabtman.common.base.SimpleFragment
import com.rabtman.common.base.widget.loadsir.EmptyCollectionCallback
import com.rabtman.common.utils.RxUtil
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_COLLECTION)
class ScheduleCollectionFragment : SimpleFragment() {
    @BindView(R2.id.rcv_schedule_collection)
    lateinit var rcvScheduleCollection: RecyclerView
    private var mAdapter: ScheduleCollectionAdapter? = null
    private var mDisposable: Disposable? = null
    override fun getLayoutId(): Int {
        return R.layout.acgschedule_fragment_schedule_collection
    }

    override fun initData() {
        mAdapter = ScheduleCollectionAdapter()
        rcvScheduleCollection.layoutManager = GridLayoutManager(context, 3)
        rcvScheduleCollection.adapter = mAdapter
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ScheduleCache
            RouterUtils.instance
                    .build(RouterConstants.PATH_SCHEDULE_DETAIL)
                    .withString(IntentConstant.SCHEDULE_DETAIL_URL, item.scheduleUrl)
                    .navigation()
        }
        getScheduleCollections()
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        getScheduleCollections()
    }

    override fun onResume() {
        super.onResume()
        if (isInited && isVisibled) {
            getScheduleCollections()
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
    private fun getScheduleCollections() {
        val dao = ScheduleDAO(
                mAppComponent
                        .repositoryManager()
                        .obtainRealmConfig(SystemConstant.DB_NAME)
        )
        mDisposable = dao.scheduleCollectCaches
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(object : ResourceSubscriber<List<ScheduleCache>?>() {
                    override fun onNext(scheduleCaches: List<ScheduleCache>?) {
                        mAdapter!!.setList(scheduleCaches)
                        if (scheduleCaches.isNullOrEmpty()) {
                            showPageEmpty()
                        } else {
                            showPageContent()
                        }
                    }

                    override fun onError(t: Throwable) {
                        showError(R.string.msg_error_data_null)
                        showPageError()
                    }

                    override fun onComplete() {}
                })
    }

    override fun showPageEmpty() {
        mLoadService?.showCallback(EmptyCollectionCallback::class.java)
    }
}