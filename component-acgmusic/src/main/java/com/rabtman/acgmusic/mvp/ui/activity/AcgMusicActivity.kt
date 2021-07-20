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
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import com.rabtman.acgmusic.service.MusicPlayService
import com.rabtman.business.router.RouterConstants
import com.rabtman.common.base.SimpleActivity
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import com.rabtman.eximgloader.ImageLoader.loadImage
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.acgmusic_activity_random_music.*


/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_MUSIC_RANDOM)
class AcgMusicActivity : SimpleActivity(), View.OnClickListener {

    private var mSeekBarLock: Boolean = false
    private var mFirstPlay: Boolean = true
    private var isServiceBind: Boolean = false
    private var mIMusicService: IMusicService? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtil.d("music service onServiceDisconnected")
            isServiceBind = false
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            mIMusicService = IMusicService.Stub.asInterface(binder)
            if (mIMusicService == null || binder == null || !binder.isBinderAlive) {
                LogUtil.e("music service onServiceConnected fail !!!!!")
                mIMusicService = null
                return
            }
            LogUtil.d("music service onServiceConnected getMusicInfo")
            isServiceBind = true
            mIMusicService?.let { musicService ->
                Flowable.zip(Flowable.create({ emitter ->
                    val info = musicService.musicInfo
                    if (info == null) {
                        emitter.onNext(MusicInfo())
                    } else {
                        emitter.onNext(info)
                    }
                    emitter.onComplete()
                }, BackpressureStrategy.BUFFER), Flowable.just(musicService.isPlaying),
                        BiFunction<MusicInfo, Boolean, Pair<MusicInfo, Boolean>> { info, isPlaying ->
                            Pair(info, isPlaying)
                        })
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe {
                            if (it.first.code == 0) {
                                showMusicInfo(it.first)
                                seek_bar_music_progress.max = musicService.duration
                                tv_music_total_time.text = getTime(seek_bar_music_progress.max)
                                btn_music_toggle.isEnabled = true
                                btn_music_toggle.isChecked = it.second
                            } else {
                                musicService.next()
                            }
                            musicService.registerMusicStatusListener(mMusicStatusListener)
                        }
            }
        }

    }

    private val mMusicStatusListener = object : IMusicStatusListener.Stub() {

        override fun onMusicPrepared(info: MusicInfo) {
            LogUtil.d("acitivty music info:" + info.toString())
            runOnUiThread {
                showMusicInfo(info)
            }
        }

        override fun onMusicReady(duration: Int, playNow: Boolean) {
            runOnUiThread {
                btn_music_toggle.isEnabled = true
                seek_bar_music_progress.max = duration
                tv_music_total_time.text = getTime(duration)
                btn_music_toggle.isChecked = playNow
                if (playNow) {
                    image_music_logo.start()
                }
            }
        }

        override fun onPlayStatusChange(isPlaying: Boolean) {
            runOnUiThread {
                btn_music_toggle.isChecked = isPlaying
            }
        }

        override fun onProgress(curPosition: Int) {
            runOnUiThread {
                seek_bar_music_progress.progress = curPosition
                tv_music_cur_time.text = getTime(curPosition)
            }
        }

        override fun onFail() {

        }

        override fun onMusicEnd() {

        }

        override fun onClosed() {
            mContext.unbindService(mConnection)
            MusicPlayService.stopService()
            isServiceBind = false
            runOnUiThread {
                btn_music_toggle.isEnabled = false
                btn_music_toggle.isChecked = false
                seek_bar_music_progress.progress = 0
                tv_music_cur_time.text = "00:00"
                image_music_logo.reset()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.acgmusic_activity_random_music
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparentForImageView(this, layout_music_top)
    }

    var test: Boolean = false

    override fun initData() {
        test.let {

        }
        MusicPlayService.startService()
        seek_bar_music_progress.thumb.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        seek_bar_music_progress.progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        btn_music_next.setOnClickListener(this)
        btn_music_back.setOnClickListener(this)
        btn_music_toggle.setOnCheckedChangeListener { view, isChecked ->
            if (!isServiceBind) return@setOnCheckedChangeListener
            mIMusicService?.let {
                if (isChecked) {
                    it.play()
                    image_music_logo.start()
                } else {
                    it.pause()
                    image_music_logo.pause()
                }
            }
        }
        seek_bar_music_progress!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var pro: Int = 0

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mSeekBarLock = false
                mIMusicService?.seekTo(pro)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mSeekBarLock = true
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                           fromUser: Boolean) {
                pro = progress
                tv_music_cur_time.text = getTime(progress)
            }
        })
        bindService(Intent(mContext, MusicPlayService::class.java), mConnection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        mIMusicService?.unregisterMusicStatusListener()
        unbindService(mConnection)
        super.onDestroy()
    }

    private fun showMusicInfo(info: MusicInfo) {
        tv_music_title.text = info.res.title
        seek_bar_music_progress.progress = 0
        tv_music_cur_time.text = "00:00"

        //模糊背景
        image_music_bg.loadImage(info.res.animeInfo.bg.ifEmpty { info.res.animeInfo.logo }) {
            fallback = R.drawable.acgmusic_shape_default_bg
            errorPic = R.drawable.acgmusic_shape_default_bg
            transformation(BlurTransformation(15, 6))
        }

        //音乐展示图
        image_music_logo.reset()
        image_music_logo.loadImage(info.res.animeInfo.logo) {
            isCenterCrop = true
            isCircleCrop = true
            fallback = R.drawable.ic_launcher_round
            errorPic = R.drawable.ic_launcher_round
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_music_back -> {
                finish()
            }
            R.id.btn_music_next -> {
                btn_music_toggle.isEnabled = false
                if (isServiceBind) {
                    mIMusicService?.next()
                } else {
                    MusicPlayService.startService()
                    bindService(Intent(mContext, MusicPlayService::class.java), mConnection, BIND_AUTO_CREATE)
                }
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