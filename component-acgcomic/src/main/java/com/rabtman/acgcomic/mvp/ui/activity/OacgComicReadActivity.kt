package com.rabtman.acgcomic.mvp.ui.activity

import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.OacgComicEpisodeDetailModule
import com.rabtman.acgcomic.mvp.OacgComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.acgcomic.mvp.presenter.OacgComicEpisodeDetailPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.OacgComicReadAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_OACG_READ)
class OacgComicReadActivity : BaseActivity<OacgComicEpisodeDetailPresenter>(), OacgComicEpisodeDetailContract.View, View.OnClickListener {

    //漫画阅读控制栏
    @BindView(R2.id.layout_comic_top)
    lateinit var layoutComicTop: RelativeLayout
    @BindView(R2.id.btn_comic_back)
    lateinit var btnComicBack: ImageView
    @BindView(R2.id.tv_comic_title)
    lateinit var tvComicTitle: TextView
    @BindView(R2.id.layout_comic_bottom)
    lateinit var layoutComicBottom: RelativeLayout
    @BindView(R2.id.btn_comic_before)
    lateinit var btnComicBefore: ImageView
    @BindView(R2.id.btn_comic_next)
    lateinit var btnComicNext: ImageView
    @BindView(R2.id.seekbar_comic_proc)
    lateinit var seekComicProc: AppCompatSeekBar
    @BindView(R2.id.tv_comic_count)
    lateinit var tvComicCount: TextView
    @BindView(R2.id.tv_comic_pos)
    lateinit var tvComicPos: TextView
    @BindView(R2.id.tv_comic_pos_tip)
    lateinit var tvComicPosTip: TextView
    /**
     * 控制栏的显示状态
     */
    private var mVisible: Boolean = false
    /**
     * 动画时间
     */
    private val UI_ANIMATION_DELAY = 200
    //漫画内容
    @BindView(R2.id.rcv_oacg_comic_content)
    lateinit var rcvOacgComicContent: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    private lateinit var oacgComicReadAdapter: OacgComicReadAdapter
    //当前漫画名称
    private var currentComicTitle = ""
    //当前漫画页数
    private var currentPage = 0
    //当前漫画总页数
    private var maxPage = 0

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerOacgComicEpisodeDetailComponent.builder()
                .appComponent(appComponent)
                .oacgComicEpisodeDetailModule(OacgComicEpisodeDetailModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_activity_oacg_comic_content
    }

    override fun setStatusBar() {}

    override fun initData() {
        currentComicTitle = intent.getStringExtra(IntentConstant.OACG_COMIC_TITLE)

        oacgComicReadAdapter = OacgComicReadAdapter(mAppComponent.imageLoader())
        oacgComicReadAdapter.setOnItemClickListener { _, _, _ ->
            toogleDisplayPanel()
        }
        layoutManager = LinearLayoutManager(this)
        rcvOacgComicContent.layoutManager = layoutManager
        rcvOacgComicContent.adapter = oacgComicReadAdapter
        rcvOacgComicContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val pageNo = layoutManager.findFirstVisibleItemPosition()
                if (maxPage > 0 && maxPage == layoutManager.findLastCompletelyVisibleItemPosition() + 1) {
                    currentPage = maxPage - 1
                    seekComicProc.progress = maxPage - 1
                    tvComicPos.text = maxPage.toString()
                } else if (currentPage != pageNo) {
                    currentPage = pageNo
                    seekComicProc.progress = pageNo
                    tvComicPos.text = (pageNo + 1).toString()
                }
            }
        })

        seekComicProc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {
                //先停止滑动
                rcvOacgComicContent.stopScroll()
                tvComicPosTip.visibility = View.VISIBLE
                tvComicPosTip.text = (currentPage + 1).toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                rcvOacgComicContent.scrollToPosition(currentPage)
                tvComicPosTip.visibility = View.GONE
            }

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    currentPage = progress
                    val displayPos = (progress + 1).toString()
                    tvComicPosTip.text = displayPos
                    tvComicPos.text = displayPos
                }
            }

        })

        mPresenter.setComicId(intent.getStringExtra(IntentConstant.OACG_COMIC_ID).toInt())
        mPresenter.getEpisodeDetail(intent.getStringExtra(IntentConstant.OACG_COMIC_CHAPTERID).toInt())
    }

    @OnClick(R2.id.btn_comic_back, R2.id.btn_comic_before, R2.id.btn_comic_next)
    override fun onClick(view: View?) {
        if (view == null) return
        if (view.id == R.id.btn_comic_back) {
            finish()
        } else if (view.id == R.id.btn_comic_before) {
            mPresenter.getPreEpisodeDetail()
        } else if (view.id == R.id.btn_comic_next) {
            mPresenter.getNextEpisodeDetail()
        }
    }

    override fun showEpisodeDetail(episodePage: OacgComicEpisodePage) {
        layoutComicBottom.visibility = View.VISIBLE
        //是否存在上一话
        if (episodePage.preIndex.toInt() <= 0) {
            btnComicBefore.visibility = View.INVISIBLE
        } else {
            btnComicBefore.visibility = View.VISIBLE
        }
        //是否存在下一话
        if (episodePage.nextIndex.toInt() <= 0) {
            btnComicNext.visibility = View.INVISIBLE
        } else {
            btnComicNext.visibility = View.VISIBLE
        }
        tvComicTitle.text = currentComicTitle
        tvComicTitle.append(" ${episodePage.currTitle}")
        oacgComicReadAdapter.setNewData(episodePage.pageContent)
        if (episodePage.pageContent == null || episodePage.pageContent.isEmpty()) {
            maxPage = 0
        } else {
            maxPage = episodePage.pageContent.size
            //每次刷新重置控件
            rcvOacgComicContent.scrollToPosition(0)
            seekComicProc.progress = 1
            seekComicProc.max = maxPage - 1
            tvComicPos.text = "1"
        }
        tvComicCount.text = maxPage.toString()
    }

    /**
     * 触发上下栏的显示与隐藏
     */
    internal fun toogleDisplayPanel() {
        if (mVisible) {
            hidePanel()
            mVisible = false
        } else {
            showPanel()
            mVisible = true
        }
    }

    /**
     * 展示上下栏
     */
    internal fun showPanel() {
        val translateAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        val translateAnimation1 = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        translateAnimation.duration = UI_ANIMATION_DELAY.toLong()
        translateAnimation1.duration = UI_ANIMATION_DELAY.toLong()
        layoutComicTop.animation = translateAnimation1
        layoutComicBottom.animation = translateAnimation
        layoutComicTop.visibility = View.VISIBLE//这里通过改变可见性来播放动画
        layoutComicBottom.visibility = View.VISIBLE//这里通过改变可见性来播放动画
    }

    /**
     * 隐藏上下栏
     */
    internal fun hidePanel() {
        val translateAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f)
        val translateAnimation1 = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f)
        translateAnimation.duration = UI_ANIMATION_DELAY.toLong()
        translateAnimation1.duration = UI_ANIMATION_DELAY.toLong()
        layoutComicTop.animation = translateAnimation
        layoutComicBottom.animation = translateAnimation1
        layoutComicTop.visibility = View.INVISIBLE//这里通过改变可见性来播放动画
        layoutComicBottom.visibility = View.INVISIBLE//这里通过改变可见性来播放动画
    }
}