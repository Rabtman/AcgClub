package com.rabtman.acgpicture.mvp.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.R2
import com.rabtman.acgpicture.base.constant.IntentConstant
import com.rabtman.acgpicture.base.constant.SystemConstant
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.ui.adapter.AcgPictureDetailPageAdapter
import com.rabtman.business.router.RouterConstants
import com.rabtman.common.base.SimpleActivity
import com.rabtman.common.http.ApiException
import com.rabtman.common.utils.FileUtils
import com.rabtman.common.utils.LogUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import zlc.season.rxdownload4.delete
import zlc.season.rxdownload4.download
import zlc.season.rxdownload4.file
import zlc.season.rxdownload4.task.Task
import java.io.File

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_PICTURE_ITEM_DETAIL)
class AcgPictureItemDetailActivity : SimpleActivity() {

    @BindView(R2.id.layout_picture_top)
    lateinit var layoutPictureTop: RelativeLayout

    @BindView(R2.id.btn_picture_back)
    lateinit var btnPictureBack: ImageView

    @BindView(R2.id.tv_picture_title)
    lateinit var tvPictureTitle: TextView

    @BindView(R2.id.layout_picture_bottom)
    lateinit var layoutPictureBottom: FrameLayout

    @BindView(R2.id.btn_picture_download)
    lateinit var btnPictureDownload: ImageView

    @BindView(R2.id.vp_acgpicture_item)
    lateinit var vpAcgItem: ViewPager
    private var mAcgPictureItem: AcgPictureItem? = null
    private var mAdapter: AcgPictureDetailPageAdapter? = null

    /**
     * 控件栏的显示状态
     */
    private var mVisible: Boolean = false

    /**
     * 动画时间
     */
    private val UI_ANIMATION_DELAY = 200
    private lateinit var rxPermissions: RxPermissions

    /**
     * 当前图片位置
     */
    private var curItemPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun setStatusBar() {

    }

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_activity_picture_item_detail
    }

    override fun initData() {
        rxPermissions = RxPermissions(this)
        mAcgPictureItem = intent.getParcelableExtra(IntentConstant.ACGPICTURE_ITEM)
        mAcgPictureItem?.let { it ->
            tvPictureTitle.text = it.title
            mAdapter = AcgPictureDetailPageAdapter(this, it.imgUrls)
            displayDownloadButton(it.imgUrls[0])

        }
        vpAcgItem.adapter = mAdapter
        vpAcgItem.offscreenPageLimit = 3
        vpAcgItem.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                curItemPosition = position
                mAdapter?.let { it ->
                    displayDownloadButton(it.getItems()[position])
                }
            }
        })
        //点击图片触发控件栏显隐
        mAdapter?.setPinchImageViewListener(object : AcgPictureDetailPageAdapter.PinchImageViewListener {
            override fun onClick(v: View, pos: Int) {
                toogleDisplayPanel()
            }
        })
    }

    @OnClick(R2.id.btn_picture_back)
    fun onBack() {
        finish()
    }

    @OnClick(R2.id.btn_picture_download)
    fun onClick2Download() {
        mAdapter?.let { it ->
            downloadPicture(it.imgUrls[curItemPosition])
            btnPictureDownload.visibility = View.INVISIBLE
        }
    }

    /**
     * 根据图片url判断下载按钮显隐
     */
    private fun displayDownloadButton(url: String) {
        val isExits = isPictureExits(url)
        if ((isExits == 1) or (isExits == -2)) {
            btnPictureDownload.visibility = View.INVISIBLE
        } else {
            btnPictureDownload.visibility = View.VISIBLE
        }
    }

    /**
     * 检查图片是否已经下载
     * @return Int
     * 1-------已存在
     * 0-------未知
     * -1------未下载
     * -2------url不存在
     */
    private fun isPictureExits(imgUrl: String): Int {
        if (TextUtils.isEmpty(imgUrl)) {
            return -2
        }
        imgUrl.file().let {
            if (FileUtils.isFileExists(it)) {
                1
            } else {
                -1
            }
        }
        return 0
    }

    /**
     * 图片下载
     */
    private fun downloadPicture(imgUrl: String) {
        if (TextUtils.isEmpty(imgUrl)) {
            showError(R.string.msg_error_url_null)
            return
        }

        imgUrl.delete()

        val imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf(".")) + ".jpg"
        val dir = FileUtils.getStorageFilePath(SystemConstant.ACGPICTURE_PATH).absolutePath
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .doOnNext({ accept ->
                    if (accept.not()) {
                        throw ApiException(getString(R.string.msg_error_check_permission))
                    }
                })
                .observeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.BUFFER)
                .flatMap {
                    Task(imgUrl, saveName = imgName, savePath = dir).download()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->
                    LogUtil.d("img download status:" + status)
                }, { throwable ->
                    throwable.printStackTrace()
                    if (throwable is ApiException) {
                        showError(throwable.message ?: "ApiException")
                    } else {
                        showError(R.string.msg_error_download_picture)
                    }
                }) {
                    showMsg(R.string.msg_success_download_picture)
                    savePictureSuccess(File(dir, imgName))
                }
    }

    /**
     * 下载完毕
     */
    private fun savePictureSuccess(imgFile: File) {
        //通知图库更新
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(imgFile)
        sendBroadcast(intent)
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
     * 展示控件栏
     */
    private fun showPanel() {
        val showTopPanelAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        val showBottomPanelAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        showTopPanelAnimation.duration = UI_ANIMATION_DELAY.toLong()
        showBottomPanelAnimation.duration = UI_ANIMATION_DELAY.toLong()
        layoutPictureTop.animation = showTopPanelAnimation
        layoutPictureBottom.animation = showBottomPanelAnimation
        layoutPictureTop.visibility = View.VISIBLE
        layoutPictureBottom.visibility = View.VISIBLE
    }

    /**
     * 隐藏控件栏
     */
    private fun hidePanel() {
        val hideTopPanelAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f)
        val hideBottomPanelAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f)
        hideTopPanelAnimation.duration = UI_ANIMATION_DELAY.toLong()
        hideBottomPanelAnimation.duration = UI_ANIMATION_DELAY.toLong()
        layoutPictureTop.animation = hideTopPanelAnimation
        layoutPictureBottom.animation = hideBottomPanelAnimation
        layoutPictureTop.visibility = View.INVISIBLE
        layoutPictureBottom.visibility = View.INVISIBLE
    }
}
