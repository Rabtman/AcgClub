package com.rabtman.acgpicture.api.cache

import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import io.reactivex.Flowable
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

/**
 * @author Rabtman
 */
interface AcgPictureCacheService {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    fun getAcgPictures(pictureItem: Flowable<List<AcgPictureItem>>, keyGroup: DynamicKeyGroup): Flowable<List<AcgPictureItem>>

}