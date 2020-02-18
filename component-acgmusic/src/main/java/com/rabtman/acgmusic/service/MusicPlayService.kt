package com.rabtman.acgmusic.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.rabtman.acgmusic.IMusicService
import com.rabtman.acgmusic.IMusicStatusListener
import com.rabtman.common.utils.LogUtil
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

class MusicPlayService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    private var firstTimePlay: Boolean = false

    private var musicStatusListener: IMusicStatusListener? = null

    private var progressDisposable: Disposable? = null

    private val mIMusicServiceAidl = object : IMusicService.Stub() {
        override fun play() {
            if (!mediaPlayer!!.isPlaying) {
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

        override fun next(url: String?) {
            LogUtil.d("mIMusicServiceAidl next")
            try {
                progressDisposable?.dispose()
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(url)
                mediaPlayer!!.prepareAsync()
                //mediaPlayer!!.isLooping = true // 循环播放
                progressDisposable = Flowable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                        .subscribe({
                            if (mediaPlayer!!.duration > 0) {
                                if (mediaPlayer!!.duration - mediaPlayer!!.currentPosition > 200) {
                                    musicStatusListener!!.onProgress(mediaPlayer!!.currentPosition)
                                } else {
                                    musicStatusListener!!.onCompleted()
                                    progressDisposable?.dispose()
                                }
                            }
                        }, {
                            it.printStackTrace()
                        })

                mediaPlayer!!.setOnPreparedListener {
                    musicStatusListener!!.onPrepareComplete(mediaPlayer!!.duration)
                    firstTimePlay = false
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun getCurPosition(): Int {
            LogUtil.d("mIMusicServiceAidl curPosition")
            return mediaPlayer!!.currentPosition
        }

        override fun setMusicStatusListener(listener: IMusicStatusListener?) {
            LogUtil.d("mIMusicServiceAidl setMusicStatusListener")
            musicStatusListener = listener
        }

    }

    override fun onCreate() {
        super.onCreate()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            firstTimePlay = true
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return mIMusicServiceAidl.asBinder()
    }

    override fun onDestroy() {
        mediaPlayer!!.release()
        super.onDestroy()
    }
}
