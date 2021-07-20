package com.rabtman.acgcomic.mvp.ui.activity

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
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.DaggerQiMiaoComicDetailComponent
import com.rabtman.acgcomic.di.QiMiaoComicDetailModule
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicChapter
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.acgcomic.mvp.presenter.QiMiaoComicDetailPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.QiMiaoComicEpisodeItemAdapter
import com.rabtman.business.router.RouterConstants
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.base.widget.loadsir.EmptyCallback
import com.rabtman.common.base.widget.loadsir.PlaceholderCallback
import com.rabtman.common.base.widget.loadsir.RetryCallback
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.utils.RouterUtils
import com.rabtman.eximgloader.ImageLoader.loadImage
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_QIMIAO_DETAIL)
class QiMiaoComicDetailActivity : BaseActivity<QiMiaoComicDetailPresenter>(),
    QiMiaoComicDetailContract.View {

    @BindView(R2.id.toolbar)
    lateinit internal var mToolBar: Toolbar

    @BindView(R2.id.toolbar_title)
    lateinit internal var mToolBarTitle: TextView

    @BindView(R2.id.app_bar)
    lateinit internal var appBar: AppBarLayout

    @BindView(R2.id.btn_qimiao_comic_like)
    lateinit internal var btnQiMiaoComicLike: ImageView

    @BindView(R2.id.btn_qimiao_comic_read)
    lateinit internal var btnQiMiaoComicRead: CardView

    @BindView(R2.id.tv_qimiao_comic_read)
    lateinit internal var tvQiMiaoComicRead: TextView

    @BindView(R2.id.btn_qimiao_comic_detail_more)
    lateinit internal var btnQiMiaoComicDetailMore: TextView

    @BindView(R2.id.collapsing_toolbar)
    lateinit internal var collapsingToolbarLayout: CollapsingToolbarLayout

    @BindView(R2.id.img_qimiao_comic_title_bg)
    lateinit internal var imgScheduleTitleBg: ImageView

    @BindView(R2.id.img_qimiao_comic_detail)
    lateinit internal var imgQiMiaoComicDetail: ImageView

    @BindView(R2.id.tv_qimiao_comic_detail_author)
    lateinit internal var tvQiMiaoComicDetailAuthor: TextView

    @BindView(R2.id.tv_qimiao_comic_detail_proc)
    lateinit internal var tvQiMiaoComicDetailProc: TextView

    @BindView(R2.id.tv_qimiao_comic_detail_spot)
    lateinit internal var tvQiMiaoComicDetailSpot: TextView

    @BindView(R2.id.tv_qimiao_comic_detail_description)
    lateinit internal var tvQiMiaoComicDetailDescription: ExpandableTextView

    @BindView(R2.id.layout_description)
    lateinit internal var layoutSceduleDescription: CardView

    @BindView(R2.id.layout_episode)
    lateinit internal var layoutSceduleEpisode: CardView

    @BindView(R2.id.rcv_qimiao_comic_detail)
    lateinit internal var rcvQiMiaoComicDetail: RecyclerView

    private lateinit var currentComicInfo: QiMiaoComicItem
    private lateinit var episodeItemAdapter: QiMiaoComicEpisodeItemAdapter

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
            mPresenter.getCurrentComicCache(it.comicId, false)
        }
    }

    override fun initData() {
        initPageStatus()
        setToolBar(mToolBar, "")
        collapsingToolbarLayout.isTitleEnabled = false

        currentComicInfo =
            intent.getParcelableExtra(IntentConstant.QIMIAO_COMIC_ITEM) ?: QiMiaoComicItem()

        episodeItemAdapter = QiMiaoComicEpisodeItemAdapter()
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
        val isCollect = btnQiMiaoComicLike.tag as Boolean
        currentComicInfo?.let {
            mPresenter.collectOrCancelComic(it, isCollect)
        }
    }

    @OnClick(R2.id.btn_qimiao_comic_read)
    fun continueComicRead() {
        currentComicInfo?.let { it ->
            mPresenter.getCurrentComicCache(it.comicId, true)
        }
    }

    @OnClick(R2.id.btn_qimiao_comic_detail_more)
    fun loadMoreEpisode() {
        episodeItemAdapter.setItemCount()
        btnQiMiaoComicDetailMore.visibility = View.GONE
    }

    override fun showComicCacheStatus(comicCache: ComicCache) {
        //是否有收藏
        if (btnQiMiaoComicLike.tag != comicCache.isCollect) {
            btnQiMiaoComicLike.tag = comicCache.isCollect
            btnQiMiaoComicLike.setImageDrawable(
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
        if (episodeItemAdapter.data.isNotEmpty()) {
            if (comicCache.chapterPos == 0 && comicCache.pagePos == 0) {
                tvQiMiaoComicRead.text = getString(R.string.acgcomic_label_comic_start)
            } else {
                episodeItemAdapter.setRecordPos(comicCache.chapterPos.toString())
                episodeItemAdapter.data.map {
                    if (comicCache.chapterPos.toString() == it.chapterId) {
                        tvQiMiaoComicRead.text = String.format(
                            getString(R.string.acgcomic_label_comic_continue),
                            it.name
                        )
                    }
                }
            }
        }
    }

    override fun showComicDetail(comicInfo: QiMiaoComicDetail) {
        mToolBarTitle.text = comicInfo.title
        //模糊背景
        imgScheduleTitleBg.loadImage(comicInfo.imgUrl) {
            transformation(BlurTransformation(25, 2))
        }
        //番剧展示图
        imgQiMiaoComicDetail.loadImage(comicInfo.imgUrl)
        tvQiMiaoComicDetailSpot.text = comicInfo.labels
        tvQiMiaoComicDetailAuthor.text = comicInfo.author

        btnQiMiaoComicLike.visibility = View.VISIBLE
        btnQiMiaoComicRead.visibility = View.VISIBLE
        tvQiMiaoComicDetailProc.text = currentComicInfo.now
        comicInfo.comicChapters.let {
            if (it != null && it.size > QiMiaoComicEpisodeItemAdapter.DEFAULT_ITEM_COUNT) {
                btnQiMiaoComicDetailMore.visibility = View.VISIBLE
            }
        }

        //选集内容
        layoutSceduleEpisode.visibility = android.view.View.VISIBLE
        episodeItemAdapter.setList(comicInfo.comicChapters)
        episodeItemAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as QiMiaoComicChapter
            mPresenter.updateScheduleReadChapter(currentComicInfo.comicId, item.chapterId.toInt())
            start2ComicRead(currentComicInfo.comicId, item.chapterId)
        }
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        rcvQiMiaoComicDetail.layoutManager = layoutManager
        rcvQiMiaoComicDetail.adapter = episodeItemAdapter
        rcvQiMiaoComicDetail.isNestedScrollingEnabled = false

        //番剧介绍
        val desc = comicInfo.desc
        if (!TextUtils.isEmpty(desc)) {
            tvQiMiaoComicDetailDescription.text = desc
        } else {
            layoutSceduleDescription.visibility = android.view.View.GONE
        }
    }

    override fun start2ComicRead(id: String, lastChapterId: String) {
        RouterUtils.instance
            .build(RouterConstants.PATH_COMIC_QIMIAO_READ)
            .withString(IntentConstant.QIMIAO_COMIC_ID, id)
            .withString(IntentConstant.QIMIAO_COMIC_TITLE, currentComicInfo.title)
            .withString(IntentConstant.QIMIAO_COMIC_CHAPTER_ID, lastChapterId)
            .navigation()
    }
}
