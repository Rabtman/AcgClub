package com.rabtman.acgnews.mvp.ui.activity

import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgnews.R
import com.rabtman.acgnews.R2
import com.rabtman.acgnews.base.constant.IntentConstant
import com.rabtman.acgnews.di.component.DaggerZeroFiveNewsDetailComponent
import com.rabtman.acgnews.di.module.ZeroFiveNewsDetailModule
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNews
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail
import com.rabtman.acgnews.mvp.presenter.ZeroFiveNewsDetailPresenter
import com.rabtman.acgnews.mvp.ui.activity.ZeroFiveNewsDetailActivity
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.utils.ExceptionUtils
import com.rabtman.common.utils.LogUtil
import com.rabtman.router.RouterConstants
import com.tbruyelle.rxpermissions2.RxPermissions
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.shareboard.ShareBoardConfig
import com.zzhoujay.richtext.RichText

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_ACGNEWS_DETAIL)
class ZeroFiveNewsDetailActivity : BaseActivity<ZeroFiveNewsDetailPresenter>(), ZeroFiveNewsDetailContract.View {
    @BindView(R2.id.toolbar)
    lateinit var mToolBar: Toolbar

    @BindView(R2.id.layout_content)
    lateinit var layoutContent: NestedScrollView

    @BindView(R2.id.tv_acg_detail_content)
    lateinit var tvAcgDetailContent: TextView

    @BindView(R2.id.tv_acg_detail_title)
    lateinit var tvAcgDetailTitle: TextView

    @BindView(R2.id.tv_acg_detail_labels)
    lateinit var tvAcgDetailLabels: TextView

    @BindView(R2.id.tv_acg_detail_datetime)
    lateinit var tvAcgDetailDatetime: TextView
    private var mZeroFiveNewsItem: ZeroFiveNews? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerZeroFiveNewsDetailComponent.builder()
                .appComponent(appComponent)
                .zeroFiveNewsDetailModule(ZeroFiveNewsDetailModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgnews_activity_acginfo_detail
    }

    override fun registerTarget(): Any? {
        return layoutContent
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.getNewsDetail(mZeroFiveNewsItem?.contentLink)
    }

    override fun initData() {
        setToolBar(mToolBar, "")
        mZeroFiveNewsItem = intent.getParcelableExtra(IntentConstant.ZERO_FIVE_NEWS_ITEM)
        if (mZeroFiveNewsItem == null) {
            showError(R.string.msg_error_unknown)
            return
        }
        mToolBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.acginfo_share) {
                mPresenter.start2Share(RxPermissions(this@ZeroFiveNewsDetailActivity))
            }
            false
        }
        mPresenter.getNewsDetail(mZeroFiveNewsItem?.contentLink)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.acgnews_activity_acginfo_share_menu, menu)
        return true
    }

    override fun showNewsDetail(zeroFiveNewsDetail: ZeroFiveNewsDetail) {
        tvAcgDetailTitle.setText(mZeroFiveNewsItem?.title)
        tvAcgDetailDatetime
                .setText(intent.getStringExtra(mZeroFiveNewsItem?.dateTime))
        //文章来源信息
        val stringBuilder = StringBuilder()
        for (i in 0..3) {
            val text = zeroFiveNewsDetail.labels[i]
            stringBuilder.append(text)
            stringBuilder.append("  ")
        }
        tvAcgDetailLabels!!.text = stringBuilder.toString()
        //文章内容
        RichText.fromHtml(zeroFiveNewsDetail.content)
                .into(tvAcgDetailContent)
    }

    override fun showShareView() {
        val umWeb = UMWeb(mZeroFiveNewsItem?.contentLink)
        umWeb.setThumb(UMImage(this, mZeroFiveNewsItem?.imgUrl))
        umWeb.title = mZeroFiveNewsItem?.title
        umWeb.description = mZeroFiveNewsItem?.description

        //分享面板ui
        val config = ShareBoardConfig()
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER)
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR) // 圆角背景
        ShareAction(this@ZeroFiveNewsDetailActivity)
                .withMedia(umWeb)
                .setDisplayList(
                        SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.WEIXIN_FAVORITE,
                        SHARE_MEDIA.MORE)
                .setCallback(object : UMShareListener {
                    override fun onStart(share_media: SHARE_MEDIA) {}
                    override fun onResult(share_media: SHARE_MEDIA) {
                        LogUtil.d("share media:$share_media")
                        if (share_media != SHARE_MEDIA.MORE) {
                            showMsg(R.string.msg_success_umeng_share)
                        }
                    }

                    override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {
                        if (share_media != SHARE_MEDIA.MORE) {
                            showError(R.string.msg_error_umeng_share)
                        }
                        if (throwable != null) {
                            LogUtil.e(ExceptionUtils.errToStr(throwable))
                        }
                    }

                    override fun onCancel(share_media: SHARE_MEDIA) {}
                })
                .open(config)
    }
}