package com.rabtman.acgmusic.mvp.ui.activity


import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.jaeger.library.StatusBarUtil
import com.rabtman.acgmusic.IMusicService
import com.rabtman.acgmusic.IMusicStatusListener
import com.rabtman.acgmusic.R
import com.rabtman.acgmusic.di.DaggerRandomMusicComponent
import com.rabtman.acgmusic.di.RandomMusicModule
import com.rabtman.acgmusic.mvp.RandomMusicContract
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import com.rabtman.acgmusic.mvp.presenter.RandomMusicPresenter
import com.rabtman.acgmusic.service.MusicPlayService
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.imageloader.glide.transformations.CircleTransformation
import com.rabtman.common.utils.LogUtil
import com.rabtman.router.RouterConstants
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.acgmusic_activity_random_music.*


/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_MUSIC_RANDOM)
class AcgMusicActivity : BaseActivity<RandomMusicPresenter>(), RandomMusicContract.View, View.OnClickListener {

    private var mSeekBarLock: Boolean = false
    private var mFirstPlay: Boolean = true
    private var mIMusicService: IMusicService? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtil.d("music service onServiceDisconnected")
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            mIMusicService = IMusicService.Stub.asInterface(binder)
            if (mIMusicService == null || binder == null || !binder.isBinderAlive) {
                LogUtil.e("music service onServiceConnected fail !!!!!")
                return
            }
            mPresenter.getRandomMusic(false)
            LogUtil.d("music service onServiceConnected getRandomMusic")
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.acgmusic_activity_random_music
    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerRandomMusicComponent.builder()
                .appComponent(appComponent)
                .randomMusicModule(RandomMusicModule(this))
                .build()
                .inject(this)
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparentForImageView(this, layout_music_top)
    }

    override fun initData() {
        seek_bar_music_progress.thumb.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        seek_bar_music_progress.progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        mContext.bindService(Intent(mContext, MusicPlayService::class.java), mConnection, BIND_AUTO_CREATE)
        btn_music_next.setOnClickListener(this)
        btn_music_back.setOnClickListener(this)
        btn_music_toggle.setOnCheckedChangeListener { view, isChecked ->
            mIMusicService?.let {
                if (isChecked) {
                    it.play()
                    if (mFirstPlay) {
                        mFirstPlay = false
                        image_music_logo.start()
                    } else {
                        image_music_logo.resume()
                    }
                } else {
                    it.pause()
                    image_music_logo.pause()
                }
            }
        }
        seek_bar_music_progress!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var pro: Int = 0

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
                mSeekBarLock = false
                mIMusicService?.seekTo(pro)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
                mSeekBarLock = true
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                           fromUser: Boolean) {
                pro = progress
                tv_music_cur_time.text = getTime(progress)
            }
        })
    }

    override fun onLoadMusicSuccess(info: MusicInfo, ready2Play: Boolean) {
        tv_music_title.text = info.res.title
        seek_bar_music_progress.progress = 0
        tv_music_cur_time.text = "00:00"

        //模糊背景
        mAppComponent.imageLoader().loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(info.res.animeInfo.bg.ifEmpty { info.res.animeInfo.logo })
                        //.url("http://i2.tiimg.com/669018/8444917812b84d81.jpg")
                        .fallback(R.drawable.acgmusic_shape_default_bg)
                        .errorPic(R.drawable.acgmusic_shape_default_bg)
                        .transformation(
                                BlurTransformation(15, 6)
                        )
                        .imageView(image_music_bg)
                        .build()
        )
        //音乐展示图
        image_music_logo.reset()
        mAppComponent.imageLoader().loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(info.res.animeInfo.logo)
                        .fallback(R.drawable.ic_launcher_round)
                        .errorPic(R.drawable.ic_launcher_round)
                        .transformation(CircleTransformation())
                        .imageView(image_music_logo)
                        .build()
        )
        mIMusicService?.let {
            LogUtil.d("mIMusicService next")
            it.next(info.res.playUrl)
            it.setMusicStatusListener(object : IMusicStatusListener.Stub() {
                override fun onPrepareComplete(duration: Int) {
                    runOnUiThread {
                        seek_bar_music_progress.max = duration
                        tv_music_total_time.text = getTime(duration)

                        if (btn_music_toggle.isChecked) {
                            image_music_logo.start()
                            it.play()
                        }
                    }
                    btn_music_toggle.isChecked = ready2Play
                }

                override fun onProgress(curPosition: Int) {
                    runOnUiThread {
                        seek_bar_music_progress.progress = curPosition
                        tv_music_cur_time.text = getTime(curPosition)
                    }
                }

                override fun onCompleted() {
                    mPresenter.getRandomMusic(true)
                    LogUtil.d("onCompleted getRandomMusic")
                }
            })
        }
    }

    override fun onLoadMoreFail() {

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_music_back -> {
                finish()
            }
            R.id.btn_music_next -> {
                mPresenter.getRandomMusic(true)
                LogUtil.d("onClick next getRandomMusic")
            }
        }
    }

    private fun getTime(duration: Int): String {
        return getType((duration / 1000) / 60) + ":" + getType((duration / 1000) % 60)
    }

    private fun getType(time: Int): String {
        return if (time < 10) {
            "0$time"
        } else {
            time.toString()
        }
    }
}