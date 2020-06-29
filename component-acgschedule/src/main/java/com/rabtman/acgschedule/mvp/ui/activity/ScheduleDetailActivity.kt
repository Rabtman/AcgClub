package com.rabtman.acgschedule.mvp.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.jaeger.library.StatusBarUtil
import com.kingja.loadsir.core.LoadSir
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.di.component.DaggerScheduleDetailComponent
import com.rabtman.acgschedule.di.module.ScheduleDetailModule
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail.ScheduleEpisode
import com.rabtman.acgschedule.mvp.presenter.ScheduleDetailPresenter
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleDetailEpisodeItemAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.base.widget.loadsir.EmptyCallback
import com.rabtman.common.base.widget.loadsir.PlaceholderCallback
import com.rabtman.common.base.widget.loadsir.RetryCallback
import com.rabtman.common.di.component.AppComponent
import com.rabtman.eximgloader.ImageLoader.loadImage
import com.rabtman.router.RouterConstants
import com.tbruyelle.rxpermissions2.RxPermissions
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_DETAIL)
class ScheduleDetailActivity : BaseActivity<ScheduleDetailPresenter>(), ScheduleDetailContract.View {

    @BindView(R2.id.toolbar)
    lateinit var mToolBar: Toolbar

    @BindView(R2.id.toolbar_title)
    lateinit var mToolBarTitle: TextView

    @BindView(R2.id.app_bar)
    lateinit var appBar: AppBarLayout

    @BindView(R2.id.collapsing_toolbar)
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    @BindView(R2.id.btn_schedule_detail_like)
    lateinit var btnScheduleDetailLike: ImageView

    @BindView(R2.id.btn_schedule_detail_read)
    lateinit var btnScheduleDetailRead: CardView

    @BindView(R2.id.tv_schedule_detail_read)
    lateinit var tvScheduleDetailRead: TextView

    @BindView(R2.id.btn_schedule_detail_more)
    lateinit var btnScheduleDetailMore: TextView

    @BindView(R2.id.img_schedule_title_bg)
    lateinit var imgScheduleTitleBg: ImageView

    @BindView(R2.id.img_schedule_detail_icon)
    lateinit var imgScheduleDetailIcon: ImageView

    @BindView(R2.id.tv_schedule_detail_time)
    lateinit var tvScheduleDetailTime: TextView

    @BindView(R2.id.tv_schedule_detail_area)
    lateinit var tvScheduleDetailAera: TextView

    @BindView(R2.id.tv_schedule_detail_proc)
    lateinit var tvScheduleDetailProc: TextView

    @BindView(R2.id.tv_schedule_detail_label)
    lateinit var tvScheduleDetailLabel: TextView

    @BindView(R2.id.tv_schedule_detail_description)
    lateinit var tvScheduleDetailDescription: ExpandableTextView

    @BindView(R2.id.layout_description)
    lateinit var layoutSceduleDescription: CardView

    @BindView(R2.id.layout_episode)
    lateinit var layoutSceduleEpisode: CardView

    @BindView(R2.id.rcv_schedule_detail)
    lateinit var rcvScheduleDetail: RecyclerView

    private var episodeItemAdapter: ScheduleDetailEpisodeItemAdapter? = null
    private lateinit var rxPermissions: RxPermissions

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerScheduleDetailComponent.builder()
                .appComponent(appComponent)
                .scheduleDetailModule(ScheduleDetailModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgschedule_activity_schedule_detail
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolBar)
    }

    override fun initData() {
        initPageStatus()
        setToolBar(mToolBar, "")
        collapsingToolbarLayout.isTitleEnabled = false
        rxPermissions = RxPermissions(this)
        val scheduleUrl = intent.getStringExtra(IntentConstant.SCHEDULE_DETAIL_URL)
        if (TextUtils.isEmpty(scheduleUrl)) {
            showError(R.string.msg_error_url_null)
            return
        }
        btnScheduleDetailLike.tag = false
        mPresenter.setCurrentScheduleUrl(scheduleUrl)
        mPresenter.getScheduleDetail()
        mPresenter.getCurrentScheduleCache(rxPermissions, false)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initPageStatus() {
        mLoadService = LoadSir.Builder()
                .addCallback(PlaceholderCallback())
                .addCallback(EmptyCallback())
                .addCallback(RetryCallback())
                .setDefaultCallback(PlaceholderCallback::class.java)
                .build()
                .register(this) { }
    }

    override fun showScheduleDetail(scheduleDetail: ScheduleDetail?) {
        if (scheduleDetail == null) {
            return
        }
        mToolBarTitle.text = scheduleDetail.scheduleTitle ?: ""
        //模糊背景
        imgScheduleTitleBg.loadImage(mContext, scheduleDetail.getImgUrl(), BlurTransformation(25, 2))
        //番剧展示图
        imgScheduleDetailIcon.loadImage(mContext, scheduleDetail.getImgUrl())
        tvScheduleDetailLabel.text = scheduleDetail.scheduleLabel
        tvScheduleDetailTime.text = scheduleDetail.scheduleTime

        scheduleDetail.scheduleProc?.takeIf { it.isNotBlank() }?.let {
            tvScheduleDetailProc.text = it
        } ?: run {
            tvScheduleDetailProc.setText(R.string.acgschedule_label_schedule_no_proc)
        }

        tvScheduleDetailAera.text = scheduleDetail.scheduleAera ?: ""
        //番剧介绍
        scheduleDetail.description?.takeIf { it.isNotBlank() }?.let {
            tvScheduleDetailDescription.text = it.replace("简介：", "")
        } ?: run {
            layoutSceduleDescription.visibility = View.GONE
        }

        //选集
        if (scheduleDetail.scheduleEpisodes != null
                && scheduleDetail.scheduleEpisodes?.size ?: 0 > 1) {
            layoutSceduleEpisode.visibility = View.VISIBLE
            btnScheduleDetailRead.visibility = View.VISIBLE
            episodeItemAdapter = ScheduleDetailEpisodeItemAdapter(scheduleDetail.scheduleEpisodes).apply {
                setOnItemClickListener { adapter, view, position ->
                    val scheduleEpisode = adapter.data[position] as ScheduleEpisode
                    mPresenter.updateScheduleReadRecord(position)
                    mPresenter.checkPermission2ScheduleVideo(rxPermissions, scheduleEpisode.link)
                }
            }
            val layoutManager = GridLayoutManager(this, 4).apply {
                orientation = GridLayoutManager.VERTICAL
            }
            rcvScheduleDetail.layoutManager = layoutManager
            rcvScheduleDetail.isNestedScrollingEnabled = false
            if (scheduleDetail.scheduleEpisodes?.size ?: 0 > ScheduleDetailEpisodeItemAdapter.DEFAULT_ITEM_COUNT) {
                btnScheduleDetailMore.visibility = View.VISIBLE
            }
        }
    }

    @OnClick(R2.id.btn_schedule_detail_more)
    fun loadMoreEpisode() {
        episodeItemAdapter?.setItemCount()
        btnScheduleDetailMore.visibility = View.GONE
    }

    override fun start2ScheduleVideo(videoUrl: String?) {
        val intent = Intent(baseContext, ScheduleVideoActivity::class.java)
        intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL, videoUrl)
        startActivity(intent)
    }

    @OnClick(R2.id.btn_schedule_detail_like)
    fun collectSchedule() {
        val isCollected = btnScheduleDetailLike.tag ?: return
        mPresenter.collectOrCancelSchedule((isCollected as Boolean))
    }

    @OnClick(R2.id.btn_schedule_detail_read)
    fun getNextVideo() {
        mPresenter.getCurrentScheduleCache(rxPermissions, true)
    }

    override fun showScheduleCacheStatus(scheduleCache: ScheduleCache?) {
        if (scheduleCache == null) {
            return
        }
        //是否有收藏
        btnScheduleDetailLike.tag?.let {
            it as Boolean
            if (scheduleCache.isCollect != it) {
                btnScheduleDetailLike.tag = scheduleCache.isCollect
                if (scheduleCache.isCollect) {
                    btnScheduleDetailLike
                            .setImageDrawable(
                                    ContextCompat.getDrawable(baseContext, R.drawable.ic_heart_solid))
                } else {
                    btnScheduleDetailLike
                            .setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_heart))
                }
            }
        }

        //更新上一次观看状态
        if (episodeItemAdapter != null) {
            val lastWatchPos = scheduleCache.lastWatchPos
            if (lastWatchPos != -1) {
                var episodeName: String? = null

                episodeItemAdapter?.data?.takeIf { it.isNotEmpty() }?.let { scheduleEpisodes ->
                    episodeName = if (lastWatchPos + 1 >= scheduleEpisodes.size - 1) {
                        scheduleEpisodes[scheduleEpisodes.size - 1].name
                    } else {
                        scheduleEpisodes[lastWatchPos + 1].name
                    }
                }

                val episodeBuilder = StringBuilder()
                episodeBuilder.append("续看 ")
                episodeName?.toIntOrNull()?.let {
                    episodeBuilder.append("第").append(it).append("话")
                } ?: run {
                    episodeBuilder.append(episodeName)
                }
                tvScheduleDetailRead.text = episodeBuilder.toString()
            }
            episodeItemAdapter?.setRecordPos(lastWatchPos)
        }
    }
}