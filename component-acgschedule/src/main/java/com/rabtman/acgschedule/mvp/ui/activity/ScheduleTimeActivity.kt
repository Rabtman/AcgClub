package com.rabtman.acgschedule.mvp.ui.activity

import android.content.Intent
import android.util.SparseIntArray
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.base.constant.SystemConstant
import com.rabtman.acgschedule.mvp.model.entity.ScheduleTimeItem
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleWeek
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleTimeAdapter
import com.rabtman.common.base.SimpleActivity
import com.rabtman.router.RouterConstants
import java.util.*

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_TIME)
class ScheduleTimeActivity : SimpleActivity() {
    @BindView(R2.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R2.id.tab_schedule_time)
    lateinit var tabScheduleTime: TabLayout

    @BindView(R2.id.rcv_schedule_time)
    lateinit var rcvScheduleTime: RecyclerView
    private var mAdapter: ScheduleTimeAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private val headerArray = SparseIntArray()
    override fun getLayoutId(): Int {
        return R.layout.acgschedule_activity_schedule_time
    }

    override fun initData() {
        setToolBar(toolbar, getString(R.string.title_schedule_new))
        val scheduleWeeks = intent.getParcelableArrayListExtra<ScheduleWeek>(IntentConstant.SCHEDULE_WEEK)
        scheduleWeeks?.takeIf { it.isNotEmpty() }?.let {
            //将数据源转化为列表展示的格式
            val scheduleTimeItems: MutableList<ScheduleTimeItem> = ArrayList()
            var headerPos = 0
            for (i in scheduleWeeks.indices) {
                //tab 标题栏
                tabScheduleTime.addTab(tabScheduleTime.newTab().apply {
                    this.text = SystemConstant.SCHEDULE_WEEK_TITLE[i]
                })
                //一周中每天的番剧列表数据合并为用于展示的单一列表
                val schduleWeek = scheduleWeeks[i]
                scheduleTimeItems
                        .add(ScheduleTimeItem(true, SystemConstant.SCHEDULE_WEEK_LIST_TITLE[i], i))
                headerArray.put(i, headerPos)
                headerPos++
                schduleWeek.scheduleItems?.forEachIndexed { index, scheduleItem ->
                    scheduleTimeItems.add(ScheduleTimeItem(scheduleItem, index))
                    headerPos++
                }
            }

            tabScheduleTime.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (mAdapter?.itemCount ?: 0 > 0) {
                        mLayoutManager?.let {
                            it.computeScrollVectorForPosition(headerArray[tab.position])?.y?.toInt()?.let { scrollY ->
                                it.scrollToPositionWithOffset(headerArray[tab.position], scrollY)
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
            mAdapter = ScheduleTimeAdapter(scheduleTimeItems).apply {
                addChildClickViewIds(R.id.schedule_episode)
                //item点击事件
                setOnItemClickListener { adapter, view, position ->
                    adapter.getItem(position)?.let { scheduleItem ->
                        scheduleItem as ScheduleTimeItem
                        val intent = Intent(baseContext, ScheduleDetailActivity::class.java)
                        intent.putExtra(IntentConstant.SCHEDULE_DETAIL_URL, scheduleItem.data?.animeLink
                                ?: "")
                        startActivity(intent)
                    }
                }
                //item子项点击事件
                setOnItemChildClickListener { adapter, view, position ->
                    if (view.id == R.id.schedule_episode) {
                        adapter.getItem(position)?.let { scheduleItem ->
                            scheduleItem as ScheduleTimeItem
                            val intent = Intent(baseContext, ScheduleVideoActivity::class.java)
                            intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL, scheduleItem.data?.episodeLink
                                    ?: "")
                            startActivity(intent)
                        }
                    }
                }
            }

            mLayoutManager = LinearLayoutManager(mContext)
            rcvScheduleTime.layoutManager = mLayoutManager
            rcvScheduleTime.adapter = mAdapter
            rcvScheduleTime.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private var currentHeaderPosition = 0
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    mLayoutManager?.let { layoutManager ->
                        mAdapter?.let { adapter ->
                            val fistVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
                            val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()
                            if (adapter.itemCount > 0) {
                                val scheduleTimeItem = adapter.getItemOrNull(fistVisibleItemPos)
                                if (scheduleTimeItem != null && scheduleTimeItem.isHeader
                                        && currentHeaderPosition != fistVisibleItemPos) {
                                    currentHeaderPosition = fistVisibleItemPos
                                    tabScheduleTime.getTabAt(scheduleTimeItem.headerIndex)?.select()
                                } else if (lastVisibleItemPos == adapter.itemCount - 1) {
                                    adapter.getItemOrNull(lastVisibleItemPos)?.let { lastScheduleTimeItem ->
                                        currentHeaderPosition = headerArray[lastScheduleTimeItem.headerIndex]
                                        tabScheduleTime.getTabAt(lastScheduleTimeItem.headerIndex)?.select()
                                    }
                                }
                            }
                        }
                    }
                }
            })
        } ?: run {
            showError(R.string.msg_error_data_null)
        }
    }
}