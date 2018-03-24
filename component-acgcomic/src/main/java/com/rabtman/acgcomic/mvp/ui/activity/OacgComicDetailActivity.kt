package com.rabtman.acgcomic.mvp.ui.activity

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.jaeger.library.StatusBarUtil
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.DaggerOacgComicDetailComponent
import com.rabtman.acgcomic.di.OacgComicDetailModule
import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.presenter.OacgComicDetailPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.OacgComicEpisodeItemAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.imageloader.glide.transformations.BlurTransformation
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_OACG_DETAIL)
class OacgComicDetailActivity : BaseActivity<OacgComicDetailPresenter>(), OacgComicDetailContract.View {

    @BindView(R2.id.toolbar)
    lateinit internal var mToolBar: Toolbar
    @BindView(R2.id.toolbar_title)
    lateinit internal var mToolBarTitle: TextView
    @BindView(R2.id.app_bar)
    lateinit internal var appBar: AppBarLayout
    @BindView(R2.id.btn_oacg_comic_like)
    lateinit internal var btnOacgComicLike: ImageView
    @BindView(R2.id.btn_oacg_comic_read)
    lateinit internal var btnOacgComicRead: CardView
    @BindView(R2.id.tv_oacg_comic_read)
    lateinit internal var tvOacgComicRead: TextView
    @BindView(R2.id.collapsing_toolbar)
    lateinit internal var collapsingToolbarLayout: CollapsingToolbarLayout
    @BindView(R2.id.img_oacg_comic_title_bg)
    lateinit internal var imgScheduleTitleBg: ImageView
    @BindView(R2.id.img_oacg_comic_detail)
    lateinit internal var imgOacgComicDetail: ImageView
    @BindView(R2.id.tv_oacg_comic_detail_painter)
    lateinit internal var tvOacgComicDetailPainter: TextView
    @BindView(R2.id.tv_oacg_comic_detail_script)
    lateinit internal var tvOacgComicDetailScript: TextView
    @BindView(R2.id.tv_oacg_comic_detail_popluar)
    lateinit internal var tvOacgComicDetailPopluar: TextView
    @BindView(R2.id.tv_oacg_comic_detail_proc)
    lateinit internal var tvOacgComicDetailProc: TextView
    @BindView(R2.id.tv_oacg_comic_detail_spot)
    lateinit internal var tvOacgComicDetailSpot: TextView
    @BindView(R2.id.tv_oacg_comic_detail_description)
    lateinit internal var tvOacgComicDetailDescription: ExpandableTextView
    @BindView(R2.id.layout_description)
    lateinit internal var layoutSceduleDescription: CardView
    @BindView(R2.id.layout_episode)
    lateinit internal var layoutSceduleEpisode: CardView
    @BindView(R2.id.rcv_oacg_comic_detail)
    lateinit internal var rcvOacgComicDetail: RecyclerView

    private var currentComicInfo: OacgComicItem? = null
    private lateinit var episodeItemAdpater: OacgComicEpisodeItemAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerOacgComicDetailComponent.builder()
                .appComponent(appComponent)
                .oacgComicDetailModule(OacgComicDetailModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_activity_oacg_comic_detail
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolBar)
    }

    override fun onResume() {
        super.onResume()
        currentComicInfo?.let { it ->
            mPresenter.getCurrentComicCache(it.id, false)
        }
    }

    override fun initData() {
        setToolBar(mToolBar, "")
        collapsingToolbarLayout.isTitleEnabled = false

        currentComicInfo = intent.getParcelableExtra(IntentConstant.OACG_COMIC_ITEM)

        currentComicInfo?.let { it ->
            episodeItemAdpater = OacgComicEpisodeItemAdapter()
            mPresenter.getOacgComicDetail(it.id)
        }
    }

    @OnClick(R2.id.btn_oacg_comic_like)
    fun collectComic() {
        val isCollect = btnOacgComicLike.tag as Boolean
        currentComicInfo?.let {
            mPresenter.collectOrCancelComic(it, isCollect)
        }
    }

    @OnClick(R2.id.btn_oacg_comic_read)
    fun continueComicRead() {
        currentComicInfo?.let { it ->
            mPresenter.getCurrentComicCache(it.id, true)
        }
    }

    override fun showComicCacheStatus(comicCache: ComicCache) {
        //是否有收藏
        if (btnOacgComicLike.tag != comicCache.isCollect) {
            btnOacgComicLike.tag = comicCache.isCollect
            btnOacgComicLike.setImageDrawable(
                    ContextCompat.getDrawable(
                            baseContext,
                            if (comicCache.isCollect) {
                                R.drawable.ic_heart_solid
                            } else {
                                R.drawable.ic_heart
                            }
                    )
            )
        }
        //更新历史观看记录
        if (episodeItemAdpater.data.isNotEmpty()) {
            if (comicCache.chapterPos == 0 && comicCache.pagePos == 0) {
                tvOacgComicRead.text = getString(R.string.acgcomic_label_comic_start)
            } else {
                tvOacgComicRead.text = String.format(
                        getString(R.string.acgcomic_label_comic_continue),
                        episodeItemAdpater.data[comicCache.chapterPos].orderTitle
                )
                episodeItemAdpater.setRecordPos(comicCache.chapterPos)
            }
        }
    }

    override fun showComicDetail(comicInfos: List<OacgComicEpisode>?) {
        mToolBarTitle.text = currentComicInfo?.comicName
        //模糊背景
        mApplication.appComponent.imageLoader().loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(HtmlConstant.OACG_IMG_URL + currentComicInfo?.comicPicUrl)
                        .transformation(BlurTransformation(25, 2))
                        .imagerView(imgScheduleTitleBg)
                        .build()
        )
        //番剧展示图
        mApplication.appComponent.imageLoader().loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(HtmlConstant.OACG_IMG_URL + currentComicInfo?.comicPicUrl)
                        .imagerView(imgOacgComicDetail)
                        .build()
        )
        tvOacgComicDetailSpot.text = currentComicInfo?.comicTagName
        tvOacgComicDetailPainter.text = currentComicInfo?.painterUserNickname
        tvOacgComicDetailScript.text = currentComicInfo?.scriptUserNickname
        if (comicInfos == null || comicInfos.isEmpty()) {
            tvOacgComicDetailProc.setText(R.string.acgcomic_label_comic_no_proc)
        } else {
            btnOacgComicRead.visibility = View.VISIBLE
            tvOacgComicDetailProc.text = String.format(getString(R.string.acgcomic_label_comic_update), currentComicInfo?.comicLastOrderidx)
            //选集内容
            layoutSceduleEpisode.visibility = android.view.View.VISIBLE
            episodeItemAdpater.setNewData(comicInfos)
            episodeItemAdpater.setOnItemClickListener({ adapter, _, position ->
                val item = adapter.getItem(position) as OacgComicEpisode
                mPresenter.updateScheduleReadChapter(item.comicId, position)
                start2ComicRead(item.comicId, item.orderIdx)
            })
            val layoutManager = GridLayoutManager(this, 4)
            layoutManager.orientation = GridLayoutManager.VERTICAL
            rcvOacgComicDetail.layoutManager = layoutManager
            rcvOacgComicDetail.adapter = episodeItemAdpater
            rcvOacgComicDetail.isNestedScrollingEnabled = false
        }
        tvOacgComicDetailPopluar.text = String.format(getString(R.string.acgcomic_label_comic_popluar), currentComicInfo?.clickScore)
        //番剧介绍
        val desc = currentComicInfo?.comicDesc
        if (!TextUtils.isEmpty(desc)) {
            tvOacgComicDetailDescription.text = desc
        } else {
            layoutSceduleDescription.visibility = android.view.View.GONE
        }
    }

    override fun start2ComicRead(id: String, lastChapterIndex: String) {
        RouterUtils.getInstance()
                .build(RouterConstants.PATH_COMIC_OACG_READ)
                .withString(IntentConstant.OACG_COMIC_ID, id)
                .withString(IntentConstant.OACG_COMIC_TITLE, currentComicInfo?.comicName)
                .withString(IntentConstant.OACG_COMIC_CHAPTERID, lastChapterIndex)
                .navigation()
    }
}
