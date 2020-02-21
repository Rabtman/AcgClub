package com.rabtman.acgmusic.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.rabtman.acgmusic.IMusicService
import com.rabtman.acgmusic.IMusicStatusListener
import com.rabtman.acgmusic.api.AcgMusicService
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import com.rabtman.common.utils.Utils
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MusicPlayService : Service() {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private var mediaPlayer: MediaPlayer? = null

    private var firstTimePlay: Boolean = true

    private var musicStatusListener: IMusicStatusListener? = null

    private var progressDisposable: Disposable? = null

    private var mMusicInfo: MusicInfo? = null

    private val mIMusicServiceAidl = object : IMusicService.Stub() {
        override fun play() {
            if (!mediaPlayer!!.isPlaying) {
                if (firstTimePlay) {
                    firstTimePlay = false
                }
                mediaPlayer!!.start()
            }
        }

        override fun pause() {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
            }
        }

        override fun seekTo(position: Int) {
            mediaPlayer?.seekTo(position)
        }

        override fun next() {
            getRandomMusic()
        }

        override fun isPlaying(): Boolean {
            return mediaPlayer?.isPlaying ?: false
        }

        override fun getMusicInfo(): MusicInfo? {
            return mMusicInfo
        }

        override fun getCurPosition(): Int {
            return mediaPlayer!!.currentPosition
        }

        override fun setMusicStatusListener(listener: IMusicStatusListener?) {
            musicStatusListener = listener
        }

    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("MusicPlayService onCreate")
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        LogUtil.d("MusicPlayService onBind")
        return mIMusicServiceAidl.asBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogUtil.d("MusicPlayService onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        LogUtil.d("MusicPlayService onRebind")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        LogUtil.d("MusicPlayService onDestroy")
        mCompositeDisposable.dispose()
        mediaPlayer!!.release()
        super.onDestroy()
    }

    private fun getRandomMusic() {
        mCompositeDisposable.add(Utils.getAppComponent().repositoryManager().obtainRetrofitService(AcgMusicService::class.java)
                .getRandomSong()
                .compose(RxUtil.rxSchedulerHelper<MusicInfo>())
                .subscribeWith(object : CommonSubscriber<MusicInfo>(this) {
                    override fun onNext(musicInfo: MusicInfo) {
                        LogUtil.d("service music info:" + musicInfo.toString())
                        if (musicInfo.code == 0) {
                            mMusicInfo = musicInfo
                            musicStatusListener!!.onPrepareComplete(musicInfo)
                            progressDisposable?.dispose()
                            mediaPlayer!!.stop()
                            mediaPlayer!!.reset()
                            mediaPlayer!!.setDataSource(musicInfo.res.playUrl)
                            mediaPlayer!!.prepareAsync()
                            //mediaPlayer!!.isLooping = true // 循环播放
                            progressDisposable = Flowable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                                    .subscribe({
                                        if (mediaPlayer!!.duration > 0) {
                                            if (mediaPlayer!!.duration - mediaPlayer!!.currentPosition > 500) {
                                                musicStatusListener!!.onProgress(mediaPlayer!!.currentPosition)
                                            } else {
                                                musicStatusListener!!.onCompleted()
                                                progressDisposable?.dispose()
                                                //播放完毕，播放下一首
                                                getRandomMusic()
                                            }
                                        }
                                    }, {
                                        it.printStackTrace()
                                    })
                            mediaPlayer!!.setOnPreparedListener {
                                musicStatusListener!!.onMusicReady(mediaPlayer!!.duration, !firstTimePlay)
                                if (!firstTimePlay) {
                                    mediaPlayer!!.start()
                                }
                            }
                        } else {
                            musicStatusListener!!.onFail()
                        }
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        musicStatusListener!!.onFail()
                    }
                })
        )
    }
}
