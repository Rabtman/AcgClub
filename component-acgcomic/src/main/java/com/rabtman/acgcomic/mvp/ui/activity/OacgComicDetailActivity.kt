package com.rabtman.acgcomic.mvp.ui.activity

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
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
import com.rabtman.acgcomic.mvp.model.entity.OacgComicDetail
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.presenter.OacgComicDetailPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.OacgComicEpisodeItemAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.imageloader.glide.transformations.BlurTransformation
import com.rabtman.router.RouterConstants

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

    override fun initData() {
        setToolBar(mToolBar, "")
        collapsingToolbarLayout.isTitleEnabled = false

        currentComicInfo = intent.getParcelableExtra(IntentConstant.OACG_COMIC_ITEM)
        mPresenter.getOacgComicDetail(comicId = currentComicInfo!!.id)
    }

    override fun showComicDetail(comicInfos: List<OacgComicDetail>) {
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
        if (comicInfos.isEmpty()) {
            tvOacgComicDetailProc.setText(R.string.acgcomic_label_comic_no_proc)
        } else {
            tvOacgComicDetailProc.text = String.format(getString(R.string.acgcomic_label_comic_update), currentComicInfo?.comicLastOrderidx)
        }
        tvOacgComicDetailPopluar.text = String.format(getString(R.string.acgcomic_label_comic_popluar), currentComicInfo?.clickScore)
        //番剧介绍
        val desc = currentComicInfo?.comicDesc
        if (!TextUtils.isEmpty(desc)) {
            tvOacgComicDetailDescription.text = desc
        } else {
            layoutSceduleDescription.visibility = android.view.View.GONE
        }
        //选集
        if (!comicInfos.isEmpty()) {
            layoutSceduleEpisode.visibility = android.view.View.VISIBLE
            val adapter = OacgComicEpisodeItemAdapter(comicInfos)
            adapter.setOnItemClickListener({ adapter, _, position ->

            })
            val layoutManager = GridLayoutManager(this, 4)
            layoutManager.orientation = GridLayoutManager.VERTICAL
            rcvOacgComicDetail.layoutManager = layoutManager
            rcvOacgComicDetail.adapter = adapter
        }
    }

}
