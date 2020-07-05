package com.rabtman.acgschedule.mvp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.HtmlConstant
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.di.component.DaggerScheduleMainComponent
import com.rabtman.acgschedule.di.module.ScheduleMainModule
import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleInfo
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleInfo.ScheduleRecent
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleInfo.ScheduleRecommend
import com.rabtman.acgschedule.mvp.presenter.ScheduleMainPresenter
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleDetailActivity
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleTimeActivity
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleVideoActivity
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleBannerAdapter
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleRecentAdapter
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleRecommendAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.youth.banner.Banner

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_MAIN)
class ScheduleMainFragment : BaseFragment<ScheduleMainPresenter>(), ScheduleMainContract.View {
    @BindView(R2.id.swipe_refresh_schedule_main)
    lateinit var swipeRefresh: SwipeRefreshLayout

    @BindView(R2.id.layout_schedule_main)
    lateinit var layoutScheduleMain: LinearLayout

    @BindView(R2.id.scroll_schedule)
    lateinit var scrollScheduleView: NestedScrollView

    @BindView(R2.id.banner_schedule)
    lateinit var bannerSchedule: Banner<ScheduleInfo.ScheduleBanner, ScheduleBannerAdapter>

    @BindView(R2.id.tv_schedule_time)
    lateinit var tvScheduleTime: TextView

    /*@BindView(id.tv_schedule_music)
  TextView tvScheduleMusic;*/
    @BindView(R2.id.tv_schedule_new)
    lateinit var tvScheduleNew: TextView

    @BindView(R2.id.layout_schedule_recommand)
    lateinit var layoutScheduleRecommand: RelativeLayout

    @BindView(R2.id.rcv_schedule_recommand)
    lateinit var rcvScheduleRecommand: RecyclerView

    @BindView(R2.id.rcv_schedule_recent)
    lateinit var rcvScheduleRecent: RecyclerView
    private lateinit var rxPermissions: RxPermissions
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerScheduleMainComponent.builder()
                .appComponent(appComponent)
                .scheduleMainModule(ScheduleMainModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgschedule_fragment_schedule_main
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initData() {
        swipeRefresh.setOnRefreshListener {
            bannerSchedule.stop()
            mPresenter.getScheduleInfo()
        }
        setSwipeRefreshLayout(swipeRefresh)
        rxPermissions = RxPermissions(mActivity)
        mPresenter.getScheduleInfo()
    }

    override fun onResume() {
        super.onResume()
        if (bannerSchedule.visibility == View.VISIBLE) {
            bannerSchedule.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (bannerSchedule.visibility == View.VISIBLE) {
            bannerSchedule.stop()
        }
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.getScheduleInfo()
    }

    override fun showScheduleInfo(scheduleInfo: ScheduleInfo) {
        //放送时间表
        tvScheduleTime.setOnClickListener {
            val newIntent = Intent(context, ScheduleTimeActivity::class.java)
            newIntent.putParcelableArrayListExtra(IntentConstant.SCHEDULE_WEEK,
                    scheduleInfo.scheduleWeek)
            startActivity(newIntent)
        }
        //音乐电台
        /*tvScheduleMusic.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(android.view.View v) {
        RouterUtils.getInstance()
            .build(RouterConstants.PATH_MUSIC_RANDOM)
            .navigation();
      }
    });*/
        //往季新番
        tvScheduleNew.setOnClickListener {
            RouterUtils.getInstance()
                    .build(RouterConstants.PATH_SCHEDULE_NEW)
                    .navigation()
        }
        //轮播栏
        scheduleInfo.scheduleBanners?.let { scheduleBanners ->
            if (scheduleBanners.isNullOrEmpty()) {
                bannerSchedule.setVisibility(View.GONE)
            } else {
                bannerSchedule.setAdapter(ScheduleBannerAdapter(scheduleBanners))
                        .setBannerGalleryMZ(16)
                        .setOnBannerListener { _, pos ->
                            startToScheduleDetail(scheduleBanners[pos].animeLink)
                        }
                bannerSchedule.start()
            }
        }

        //近期推荐
        scheduleInfo.scheduleRecommends?.let { scheduleRecommends ->
            if (scheduleRecommends.isNullOrEmpty()) {
                layoutScheduleRecommand.visibility = View.GONE
            } else {
                val scheduleRecommendAdapter = ScheduleRecommendAdapter(scheduleRecommends)
                scheduleRecommendAdapter.setOnItemClickListener { adapter, _, position ->
                    val scheduleRecommend = adapter.getItem(position) as ScheduleRecommend
                    startToScheduleDetail(scheduleRecommend.animeLink)
                }
                rcvScheduleRecommand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rcvScheduleRecommand.adapter = scheduleRecommendAdapter
            }
        }

        //最近更新
        val scheduleRecentAdapter = ScheduleRecentAdapter(scheduleInfo.scheduleRecent)
        scheduleRecentAdapter.setOnItemClickListener { adapter, view, position ->
            val scheduleRecent = adapter.getItem(position) as ScheduleRecent
            startToScheduleDetail(scheduleRecent.animeLink)
        }
        rcvScheduleRecent.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        rcvScheduleRecent.adapter = scheduleRecentAdapter
        rcvScheduleRecent.isNestedScrollingEnabled = false
        layoutScheduleMain.visibility = View.VISIBLE
    }

    private fun startToScheduleDetail(url: String?) {
        if (url?.contains("show") == true) {
            val detailIntent = Intent(context, ScheduleDetailActivity::class.java)
            detailIntent.putExtra(IntentConstant.SCHEDULE_DETAIL_URL, url)
            startActivity(detailIntent)
        } else {
            mPresenter.checkPermission2ScheduleVideo(rxPermissions, url)
        }
    }

    override fun start2ScheduleVideo(videoUrl: String) {
        val intent = Intent(mContext, ScheduleVideoActivity::class.java)
        intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL,
                if (videoUrl.startsWith("http")) videoUrl else HtmlConstant.SCHEDULE_M_URL + videoUrl)
        startActivity(intent)
    }
}