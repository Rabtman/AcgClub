package com.rabtman.acgmusic.api

import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * @author Rabtman
 */
interface AcgMusicService {

    /**
     * 获取音乐
     */
    @GET("https://anime-music.jijidown.com/api/v2/music")
    fun getRandomSong(): Flowable<MusicInfo>

}