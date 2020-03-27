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
import com.kingja.loadsir.core.LoadSir
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.DaggerQiMiaoComicDetailComponent
import com.rabtman.acgcomic.di.QiMiaoComicDetailModule
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicEpisode
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.acgcomic.mvp.presenter.QiMiaoComicDetailPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.QiMiaoComicEpisodeItemAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.base.widget.loadsir.EmptyCallback
import com.rabtman.common.base.widget.loadsir.PlaceholderCallback
import com.rabtman.common.base.widget.loadsir.RetryCallback
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.imageloader.glide.transformations.BlurTransformation
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_QIMIAO_DETAIL)
class QiMiaoComicDetailActivity : BaseActivity<QiMiaoComicDetailPresenter>(), QiMiaoComicDetailContract.View {

    @BindView(R2.id.toolbar)
    lateinit internal var mToolBar: Toolbar
    @BindView(R2.id.toolbar_title)
    lateinit internal var mToolBarTitle: TextView
    @BindView(R2.id.app_bar)
    lateinit internal var appBar: AppBarLayout
    @BindView(R2.id.btn_qimiao_comic_like)
    lateinit internal var btnOacgComicLike: ImageView
    @BindView(R2.id.btn_qimiao_comic_read)
    lateinit internal var btnOacgComicRead: CardView
    @BindView(R2.id.tv_qimiao_comic_read)
    lateinit internal var tvOacgComicRead: TextView
    @BindView(R2.id.btn_qimiao_comic_detail_more)
    lateinit internal var btnOacgComicDetailMore: TextView
    @BindView(R2.id.collapsing_toolbar)
    lateinit internal var collapsingToolbarLayout: CollapsingToolbarLayout
    @BindView(R2.id.img_qimiao_comic_title_bg)
    lateinit internal var imgScheduleTitleBg: ImageView
    @BindView(R2.id.img_qimiao_comic_detail)
    lateinit internal var imgOacgComicDetail: ImageView
    @BindView(R2.id.tv_qimiao_comic_detail_author)
    lateinit internal var tvOacgComicDetailAuthor: TextView
    @BindView(R2.id.tv_qimiao_comic_detail_proc)
    lateinit internal var tvOacgComicDetailProc: TextView
    @BindView(R2.id.tv_qimiao_comic_detail_spot)
    lateinit internal var tvOacgComicDetailSpot: TextView
    @BindView(R2.id.tv_qimiao_comic_detail_description)
    lateinit internal var tvOacgComicDetailDescription: ExpandableTextView
    @BindView(R2.id.layout_description)
    lateinit internal var layoutSceduleDescription: CardView
    @BindView(R2.id.layout_episode)
    lateinit internal var layoutSceduleEpisode: CardView
    @BindView(R2.id.rcv_qimiao_comic_detail)
    lateinit internal var rcvOacgComicDetail: RecyclerView

    private lateinit var currentComicInfo: QiMiaoComicItem
    private lateinit var episodeItemAdpater: QiMiaoComicEpisodeItemAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerQiMiaoComicDetailComponent.builder()
                .appComponent(appComponent)
                .qiMiaoComicDetailModule(QiMiaoComicDetailModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_activity_qimiao_comic_detail
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolBar)
    }

    override fun onResume() {
        super.onResume()
        currentComicInfo?.let { it ->
            mPresenter.getCurrentComicCache(it.comicLink, false)
        }
    }

    override fun initData() {
        initPageStatus()
        setToolBar(mToolBar, "")
        collapsingToolbarLayout.isTitleEnabled = false

        currentComicInfo = intent.getParcelableExtra(IntentConstant.QIMIAO_COMIC_ITEM)

        episodeItemAdpater = QiMiaoComicEpisodeItemAdapter()
        mPresenter.getComicDetail(currentComicInfo.comicLink)
    }

    private fun initPageStatus() {
        mLoadService = LoadSir.Builder()
                .addCallback(PlaceholderCallback())
                .addCallback(EmptyCallback())
                .addCallback(RetryCallback())
                .setDefaultCallback(PlaceholderCallback::class.java)
                .build()
                .register(this, { _ ->
                    currentComicInfo?.let { it ->
                        mPresenter.getComicDetail(it.comicLink)
                    }
                })
    }

    @OnClick(R2.id.btn_qimiao_comic_like)
    fun collectComic() {
        val isCollect = btnOacgComicLike.tag as Boolean
        currentComicInfo?.let {
            mPresenter.collectOrCancelComic(it, isCollect)
        }
    }

    @OnClick(R2.id.btn_qimiao_comic_read)
    fun continueComicRead() {
        currentComicInfo?.let { it ->
            mPresenter.getCurrentComicCache(it.comicLink, true)
        }
    }

    @OnClick(R2.id.btn_qimiao_comic_detail_more)
    fun loadMoreEpisode() {
        episodeItemAdpater.setItemCount()
        btnOacgComicDetailMore.visibility = View.GONE
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
                /*tvOacgComicRead.text = String.format(
                        getString(R.string.acgcomic_label_comic_continue),
                        episodeItemAdpater.data[comicCache.chapterPos].orderTitle
                )
                episodeItemAdpater.setRecordPos(comicCache.chapterPos)*/
            }
        }
    }

    override fun showComicDetail(comicInfo: QiMiaoComicDetail) {
        mToolBarTitle.text = comicInfo.title
        //模糊背景
        mAppComponent.imageLoader().loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(comicInfo.imgUrl)
                        .transformation(BlurTransformation(25, 2))
                        .imageView(imgScheduleTitleBg)
                        .build()
        )
        //番剧展示图
        mAppComponent.imageLoader().loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(comicInfo.imgUrl)
                        .imageView(imgOacgComicDetail)
                        .build()
        )
        tvOacgComicDetailSpot.text = comicInfo.labels
        tvOacgComicDetailAuthor.text = comicInfo.author

        btnOacgComicLike.visibility = View.VISIBLE
        btnOacgComicRead.visibility = View.VISIBLE
        tvOacgComicDetailProc.text = currentComicInfo.now
        comicInfo.comicEpisodes.let {
            if (it != null && it.size > QiMiaoComicEpisodeItemAdapter.DEFAULT_ITEM_COUNT) {
                btnOacgComicDetailMore.visibility = View.VISIBLE
            }
        }

        //选集内容
        layoutSceduleEpisode.visibility = android.view.View.VISIBLE
        episodeItemAdpater.setNewData(comicInfo.comicEpisodes)
        episodeItemAdpater.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as QiMiaoComicEpisode
            mPresenter.updateScheduleReadChapter(currentComicInfo.comicLink, position)
            item.link?.let { start2ComicRead(currentComicInfo.comicLink, position, it) }
        }
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        rcvOacgComicDetail.layoutManager = layoutManager
        rcvOacgComicDetail.adapter = episodeItemAdpater
        rcvOacgComicDetail.isNestedScrollingEnabled = false

        //番剧介绍
        val desc = comicInfo.desc
        if (!TextUtils.isEmpty(desc)) {
            tvOacgComicDetailDescription.text = desc
        } else {
            layoutSceduleDescription.visibility = android.view.View.GONE
        }
    }

    override fun start2ComicRead(id: String, lastChapterPos: Int, lastChapterIndex: String) {
        RouterUtils.getInstance()
                .build(RouterConstants.PATH_COMIC_QIMIAO_READ)
                .withString(IntentConstant.QIMIAO_COMIC_ID, id)
                .withString(IntentConstant.QIMIAO_COMIC_TITLE, currentComicInfo.title)
                .withInt(IntentConstant.QIMIAO_COMIC_CHAPTERPOS, lastChapterPos)
                .withString(IntentConstant.QIMIAO_COMIC_CHAPTERID, lastChapterIndex)
                .navigation()
    }
}
