package com.rabtman.acgschedule.mvp.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.di.component.DaggerScheduleOtherComponent
import com.rabtman.acgschedule.di.module.ScheduleOtherModule
import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage.ScheduleOtherItem
import com.rabtman.acgschedule.mvp.presenter.ScheduleOtherPresenter
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleOtherAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.base.widget.CommonItemDecoration
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_OTHER)
class ScheduleOtherActivity : BaseActivity<ScheduleOtherPresenter>(), ScheduleOtherContract.View {
    @BindView(R2.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R2.id.swipe_schedule_other)
    lateinit var swipeRefresh: SwipeRefreshLayout

    @BindView(R2.id.rcv_schedule_other)
    lateinit var rcvScheduleOther: RecyclerView

    private var mAdapter: ScheduleOtherAdapter? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerScheduleOtherComponent.builder()
                .appComponent(appComponent)
                .scheduleOtherModule(ScheduleOtherModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgschedule_activity_schedule_other
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter!!.getScheduleOther()
    }

    override fun registerTarget(): Any? {
        return rcvScheduleOther
    }

    override fun initData() {
        setToolBar(toolbar!!, "")
        val scheduleOtherUrl = intent.getStringExtra(IntentConstant.SCHEDULE_DETAIL_URL)
        if (TextUtils.isEmpty(scheduleOtherUrl)) {
            showError(R.string.msg_error_url_null)
            return
        }
        mAdapter = ScheduleOtherAdapter()
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            val scheduleOtherItem = adapter
                    .data[position] as ScheduleOtherItem
            val intent = Intent(baseContext, ScheduleVideoActivity::class.java)
            intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL, scheduleOtherItem.videolLink)
            startActivity(intent)
        }
        mAdapter!!.loadMoreModule.setOnLoadMoreListener { mPresenter!!.getMoreScheduleOther() }
        val layoutManager = GridLayoutManager(baseContext, 2)
        rcvScheduleOther!!.addItemDecoration(CommonItemDecoration(2, CommonItemDecoration.UNIT_DP))
        rcvScheduleOther!!.layoutManager = layoutManager
        rcvScheduleOther!!.adapter = mAdapter
        swipeRefresh!!.setOnRefreshListener { mPresenter!!.getScheduleOther() }
        mPresenter!!.setCurScheduleOtherUrl(scheduleOtherUrl)
        mPresenter!!.getScheduleOther()
    }

    override fun showScheduleOther(scheduleOtherPage: ScheduleOtherPage?) {
        toolbar!!.title = scheduleOtherPage!!.title
        mAdapter!!.setList(scheduleOtherPage.scheduleOtherItems)
    }

    override fun showMoreScheduleOther(scheduleOtherPage: ScheduleOtherPage?, canLoadMore: Boolean) {
        mAdapter!!.addData(scheduleOtherPage!!.scheduleOtherItems!!)
        mAdapter!!.loadMoreModule.loadMoreComplete()
        if (!canLoadMore) {
            mAdapter!!.loadMoreModule.loadMoreEnd()
        }
    }

    override fun onLoadMoreFail() {
        mAdapter!!.loadMoreModule.loadMoreFail()
    }

    override fun showLoading() {
        if (swipeRefresh != null) {
            if (!swipeRefresh!!.isRefreshing) {
                swipeRefresh!!.isRefreshing = true
            }
        } else {
            super.showLoading()
        }
    }

    override fun hideLoading() {
        if (swipeRefresh != null && swipeRefresh!!.isRefreshing) {
            swipeRefresh!!.isRefreshing = false
        }
        super.hideLoading()
    }
}