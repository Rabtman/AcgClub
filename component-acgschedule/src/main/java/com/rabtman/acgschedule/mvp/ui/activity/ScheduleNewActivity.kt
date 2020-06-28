package com.rabtman.acgschedule.mvp.ui.activity

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.di.component.DaggerScheduleNewComponent
import com.rabtman.acgschedule.di.module.ScheduleNewModule
import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew.ScheduleNewItem
import com.rabtman.acgschedule.mvp.presenter.ScheduleNewPresenter
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleNewAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.base.widget.CommonItemDecoration
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_NEW)
class ScheduleNewActivity : BaseActivity<ScheduleNewPresenter>(), ScheduleNewContract.View {
    @JvmField
    @BindView(R2.id.toolbar)
    var toolbar: Toolbar? = null

    @JvmField
    @BindView(R2.id.rcv_schedule_new)
    var rcvScheduleNew: RecyclerView? = null
    private var mAdapter: ScheduleNewAdapter? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerScheduleNewComponent.builder()
                .appComponent(appComponent)
                .scheduleNewModule(ScheduleNewModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgschedule_activity_schedule_new
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.scheduleWeekRank
    }

    override fun registerTarget(): Any? {
        return rcvScheduleNew
    }

    override fun initData() {
        setToolBar(toolbar!!, "动漫周排行")
        mAdapter = ScheduleNewAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                val scheduleNewItem = adapter.data[position] as ScheduleNewItem
                val intent = Intent(baseContext, ScheduleDetailActivity::class.java)
                intent.putExtra(IntentConstant.SCHEDULE_DETAIL_URL, scheduleNewItem.animeLink)
                startActivity(intent)
            }
        }
        val layoutManager = LinearLayoutManager(baseContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcvScheduleNew!!.addItemDecoration(CommonItemDecoration(2, CommonItemDecoration.UNIT_DP))
        rcvScheduleNew!!.layoutManager = layoutManager
        rcvScheduleNew!!.adapter = mAdapter
        mPresenter.scheduleWeekRank
    }

    override fun showScheduleNew(scheduleNew: ScheduleNew?) {
        mAdapter?.setList(scheduleNew!!.scheduleNewItems)
    }
}