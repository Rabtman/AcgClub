package com.rabtman.acgmusic.service

import android.annotation.TargetApi
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.NotificationTarget
import com.rabtman.acgmusic.IMusicService
import com.rabtman.acgmusic.IMusicStatusListener
import com.rabtman.acgmusic.R
import com.rabtman.acgmusic.api.AcgMusicService
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import com.rabtman.acgmusic.mvp.ui.activity.AcgMusicActivity
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import com.rabtman.common.utils.Utils
import com.rabtman.eximgloader.glide.GlideApp
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MusicPlayService : Service() {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val NOTIFICATION_MUSIC_ID: Int = 609

    private val NOTIFICATION_MUSIC_CHANNEL_ID: String = "AcgMusicChannelID"

    private val NOTIFICATION_MUSIC_CHANNEL_NAME: String = "AcgMusicChannel"

    private val NOTIFICATION_ACTION_PLAY_OR_PAUSE: String = "ACTION_PLAY_OR_PAUSE"

    private val NOTIFICATION_ACTION_NEXT: String = "ACTION_NEXT"

    private val NOTIFICATION_ACTION_CLOSE: String = "ACTION_CLOSE"

    private var mediaPlayer: MediaPlayer? = null

    //第一次播放音乐
    private var firstTimePlay: Boolean = true

    private var musicStatusListener: IMusicStatusListener? = null

    private var progressDisposable: Disposable? = null

    //当前音乐信息
    private var mMusicInfo: MusicInfo? = null

    private lateinit var notificationManager: NotificationManager

    private var musicNotification: Notification? = null

    private var mRemoteView: RemoteViews? = null

    private var mBigRemoteView: RemoteViews? = null

    private val mIMusicServiceAidl = object : IMusicService.Stub() {
        override fun play() {
            if (!mediaPlayer!!.isPlaying) {
                if (firstTimePlay) {
                    firstTimePlay = false
                }
                mediaPlayer!!.start()
                musicStatusListener?.onPlayStatusChange(true)
                getRemoteView().setImageViewResource(R.id.btn_music_toggle, R.drawable.ic_notification_pause)
                getBigRemoteView().setImageViewResource(R.id.btn_music_toggle, R.drawable.ic_notification_pause)
                startForeground(NOTIFICATION_MUSIC_ID, getMusicNotification())
            }
        }

        override fun pause() {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                musicStatusListener?.onPlayStatusChange(false)
                getRemoteView().setImageViewResource(R.id.btn_music_toggle, R.drawable.ic_notification_play)
                getBigRemoteView().setImageViewResource(R.id.btn_music_toggle, R.drawable.ic_notification_play)
                startForeground(NOTIFICATION_MUSIC_ID, getMusicNotification())
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

        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        override fun getCurPosition(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }


        override fun registerMusicStatusListener(listener: IMusicStatusListener?) {
            musicStatusListener = listener
        }

        override fun unregisterMusicStatusListener() {
            musicStatusListener = null
        }
    }

    /**
     * 通知栏操作接收
     */
    private val musicReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                NOTIFICATION_ACTION_PLAY_OR_PAUSE -> {
                    if (mediaPlayer!!.isPlaying) {
                        mIMusicServiceAidl.pause()
                    } else {
                        mIMusicServiceAidl.play()
                    }
                }
                NOTIFICATION_ACTION_NEXT -> {
                    mIMusicServiceAidl.next()
                }
                NOTIFICATION_ACTION_CLOSE -> {
                    stopForeground(true)
                    stopService()
                    musicStatusListener?.onClosed()
                }
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("MusicPlayService onCreate")
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        IntentFilter().run {
            this.addAction(NOTIFICATION_ACTION_NEXT)
            this.addAction(NOTIFICATION_ACTION_PLAY_OR_PAUSE)
            this.addAction(NOTIFICATION_ACTION_CLOSE)
            registerReceiver(musicReceiver, this)
        }
    }

    /**
     * 获取音乐通知栏，不存在则进行初始化
     */
    private fun getMusicNotification(): Notification {
        if (musicNotification == null) {
            val builder: NotificationCompat.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
                builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_MUSIC_CHANNEL_ID)
            } else {
                builder = NotificationCompat.Builder(applicationContext)
                builder.priority = NotificationCompat.PRIORITY_MAX
            }
            builder.setSmallIcon(R.drawable.ic_launcher_round)
            builder.setContentTitle("AcgClubMusic")
            builder.setContentText("AcgClubMusic Notification")
            builder.setContent(getRemoteView())
            builder.setCustomContentView(getRemoteView())
            builder.setCustomBigContentView(getBigRemoteView())
            builder.setOngoing(true)
            builder.setOnlyAlertOnce(false)
            builder.setAutoCancel(false)
            builder.setVibrate(LongArray(1) { 0 })
            builder.setSound(null)
            builder.setDefaults(Notification.DEFAULT_VIBRATE)
            builder.setContentIntent(
                    PendingIntent.getActivity(
                            this,
                            1,
                            Intent(this, AcgMusicActivity::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT)
            )
            musicNotification = builder.build()
            musicNotification!!.flags = NotificationCompat.FLAG_FOREGROUND_SERVICE
        }
        return musicNotification as Notification
    }

    //Android O 通知栏新增channel设置
    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(NOTIFICATION_MUSIC_CHANNEL_ID, NOTIFICATION_MUSIC_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_MIN)
        channel.canBypassDnd() //是否绕过请勿打扰模式
        channel.enableLights(false) //闪光灯
        channel.lockscreenVisibility = NotificationCompat.VISIBILITY_SECRET //锁屏显示通知
        channel.enableVibration(false) //是否允许震动
        channel.vibrationPattern = LongArray(1) { 0 }
        channel.setSound(null, null)
        channel.group //获取通知取到组
        channel.setBypassDnd(true) //设置可绕过 请勿打扰模式
        notificationManager.createNotificationChannel(channel)
    }

    //自定义通知栏布局
    private fun getRemoteView(): RemoteViews {
        if (mRemoteView == null) {
            mRemoteView = initRemoteView(R.layout.acgmusic_view_custom_notifcation)
        }
        return mRemoteView as RemoteViews
    }

    //自定义通知栏展开布局
    private fun getBigRemoteView(): RemoteViews {
        if (mBigRemoteView == null) {
            mBigRemoteView = initRemoteView(R.layout.acgmusic_view_big_notifcation)
        }
        return mBigRemoteView as RemoteViews
    }

    /**
     * 初始化自定义通知栏布局
     */
    private fun initRemoteView(layoutId: Int): RemoteViews {
        val remoteView = RemoteViews(packageName, layoutId)
        remoteView.setOnClickPendingIntent(R.id.btn_music_toggle, getPendingIntent(NOTIFICATION_ACTION_PLAY_OR_PAUSE))
        remoteView.setOnClickPendingIntent(R.id.btn_music_next, getPendingIntent(NOTIFICATION_ACTION_NEXT))
        remoteView.setOnClickPendingIntent(R.id.btn_music_close, getPendingIntent(NOTIFICATION_ACTION_CLOSE))
        return remoteView
    }

    /**
     * 更新通知栏信息
     */
    private fun updateRemoteViewInfo() {
        mRemoteView = getRemoteView()
        mRemoteView!!.setTextViewText(R.id.tv_music_title, mMusicInfo!!.res.title)
        GlideApp.with(this)
                .asBitmap()
                .load(mMusicInfo!!.res.animeInfo.logo)
                .fallback(R.drawable.ic_launcher_round)
                .error(R.drawable.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(NotificationTarget(this, R.id.img_music_logo, mRemoteView, getMusicNotification(), NOTIFICATION_MUSIC_ID))

        mBigRemoteView = getBigRemoteView()
        mBigRemoteView!!.setTextViewText(R.id.tv_music_title, mMusicInfo!!.res.title)
        GlideApp.with(this)
                .asBitmap()
                .load(mMusicInfo!!.res.animeInfo.logo)
                .fallback(R.drawable.ic_launcher_round)
                .error(R.drawable.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(NotificationTarget(this, R.id.img_music_logo, mBigRemoteView, getMusicNotification(), NOTIFICATION_MUSIC_ID))
    }

    private fun getPendingIntent(action: String): PendingIntent {
        return PendingIntent.getBroadcast(this, 0, Intent(action), PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.d("MusicPlayService onStartCommand")
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
        unregisterReceiver(musicReceiver)
        mediaPlayer!!.release()
        //stopForeground()
        super.onDestroy()
    }

    /**
     * 网络请求下一首随机音乐信息
     */
    private fun getRandomMusic() {
        mCompositeDisposable.add(Utils.getAppComponent().repositoryManager().obtainRetrofitService(AcgMusicService::class.java)
                .getRandomSong()
                .compose(RxUtil.rxSchedulerHelper<MusicInfo>())
                .subscribeWith(object : CommonSubscriber<MusicInfo>(this) {
                    override fun onNext(musicInfo: MusicInfo) {
                        LogUtil.d("service music info:" + musicInfo.toString())
                        if (musicInfo.code == 0) {
                            mMusicInfo = musicInfo
                            musicStatusListener?.onMusicPrepared(musicInfo)
                            updateRemoteViewInfo()
                            startForeground(NOTIFICATION_MUSIC_ID, getMusicNotification())
                            progressDisposable?.dispose()
                            mediaPlayer!!.stop()
                            mediaPlayer!!.reset()
                            mediaPlayer!!.setDataSource(musicInfo.res.playUrl)
                            mediaPlayer!!.prepareAsync()
                            //mediaPlayer!!.isLooping = true // 循环播放
                            progressDisposable = Flowable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                                    .subscribe({
                                        if (mediaPlayer!!.isPlaying && mediaPlayer!!.duration > 0) {
                                            if (mediaPlayer!!.duration - mediaPlayer!!.currentPosition > 500) {
                                                musicStatusListener?.onProgress(mediaPlayer!!.currentPosition)
                                            } else {
                                                musicStatusListener?.onMusicEnd()
                                                progressDisposable?.dispose()
                                                //播放完毕，播放下一首
                                                getRandomMusic()
                                            }
                                        }
                                    }, {
                                        it.printStackTrace()
                                    })
                            mediaPlayer!!.setOnPreparedListener {
                                musicStatusListener?.onMusicReady(mediaPlayer!!.duration, !firstTimePlay)
                                if (!firstTimePlay) {
                                    mediaPlayer!!.start()
                                    getRemoteView().setImageViewResource(R.id.btn_music_toggle, R.drawable.ic_notification_pause)
                                    getBigRemoteView().setImageViewResource(R.id.btn_music_toggle, R.drawable.ic_notification_pause)
                                    startForeground(NOTIFICATION_MUSIC_ID, getMusicNotification())
                                }
                            }
                        } else {
                            musicStatusListener?.onFail()
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        musicStatusListener?.onFail()
                    }
                })
        )
    }

    companion object {
        fun startService() {
            Utils.app.startService(Intent(Utils.app, MusicPlayService::class.java))
        }

        fun stopService() {
            Utils.app.stopService(Intent(Utils.app, MusicPlayService::class.java))
        }
    }
}
