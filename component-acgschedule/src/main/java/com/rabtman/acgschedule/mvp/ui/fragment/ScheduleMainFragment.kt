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
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleRecent
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleRecommend
import com.rabtman.acgschedule.mvp.presenter.ScheduleMainPresenter
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleDetailActivity
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleTimeActivity
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleVideoActivity
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleBannerViewHolder
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleRecentAdapter
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleRecommandAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhouwei.mzbanner.MZBannerView

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
    lateinit var bannerSchedule: MZBannerView<DilidiliInfo.ScheduleBanner>

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
        swipeRefresh!!.setOnRefreshListener {
            bannerSchedule!!.pause()
            mPresenter.getDilidiliInfo()
        }
        setSwipeRefreshLayout(swipeRefresh)
        rxPermissions = RxPermissions(activity!!)
        mPresenter.getDilidiliInfo()
    }

    override fun onResume() {
        super.onResume()
        if (bannerSchedule!!.visibility == View.VISIBLE) {
            bannerSchedule!!.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (bannerSchedule!!.visibility == View.VISIBLE) {
            bannerSchedule!!.pause()
        }
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.getDilidiliInfo()
    }

    override fun showDilidiliInfo(dilidiliInfo: DilidiliInfo) {
        //放送时间表
        tvScheduleTime.setOnClickListener {
            val newIntent = Intent(context, ScheduleTimeActivity::class.java)
            newIntent.putParcelableArrayListExtra(IntentConstant.SCHEDULE_WEEK,
                    dilidiliInfo.scheduleWeek)
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
        tvScheduleNew!!.setOnClickListener {
            RouterUtils.getInstance()
                    .build(RouterConstants.PATH_SCHEDULE_NEW)
                    .navigation()
        }
        //轮播栏
        if (dilidiliInfo.scheduleBanners != null && dilidiliInfo.scheduleBanners!!.size > 0) {
            bannerSchedule!!.setIndicatorVisible(false)
            bannerSchedule!!.setBannerPageClickListener { view, i -> startToScheduleDetail(dilidiliInfo.scheduleBanners!![i].animeLink) }
            bannerSchedule!!.setPages(dilidiliInfo.scheduleBanners) { ScheduleBannerViewHolder() }
            bannerSchedule!!.start()
        } else {
            bannerSchedule!!.setVisibility(View.GONE)
        }
        //近期推荐
        if (dilidiliInfo.scheduleRecommends != null
                && dilidiliInfo.scheduleRecommends!!.size > 0) {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            val scheduleRecommandAdapter = ScheduleRecommandAdapter(dilidiliInfo.scheduleRecommends)
            scheduleRecommandAdapter.setOnItemClickListener { adapter, view, position ->
                val scheduleRecommend = adapter.getItem(position) as ScheduleRecommend
                startToScheduleDetail(scheduleRecommend.animeLink)
            }
            rcvScheduleRecommand!!.layoutManager = linearLayoutManager
            rcvScheduleRecommand!!.adapter = scheduleRecommandAdapter
        } else {
            layoutScheduleRecommand!!.visibility = View.GONE
        }
        //最近更新
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        val scheduleRecentAdapter = ScheduleRecentAdapter(dilidiliInfo.scheduleRecent)
        scheduleRecentAdapter.setOnItemClickListener { adapter, view, position ->
            val scheduleRecent = adapter.getItem(position) as ScheduleRecent
            startToScheduleDetail(scheduleRecent.animeLink)
        }
        rcvScheduleRecent!!.layoutManager = gridLayoutManager
        rcvScheduleRecent!!.adapter = scheduleRecentAdapter
        rcvScheduleRecent!!.isNestedScrollingEnabled = false
        layoutScheduleMain!!.visibility = View.VISIBLE
    }

    private fun startToScheduleDetail(url: String?) {
        if (url!!.contains("show")) {
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
                if (videoUrl.startsWith("http")) videoUrl else HtmlConstant.YHDM_M_URL + videoUrl)
        startActivity(intent)
    }
}