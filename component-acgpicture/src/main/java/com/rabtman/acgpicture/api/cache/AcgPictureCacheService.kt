package com.rabtman.acgpicture.api.cache

import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureType
import io.reactivex.Flowable
import io.rx_cache2.DynamicKey
import io.rx_cache2.LifeCache
import io.rx_cache2.ProviderKey
import java.util.concurrent.TimeUnit

/**
 * @author Rabtman
 */
interface AcgPictureCacheService {

    @ProviderKey("get-acgpicture-types")
    @LifeCache(duration = 8, timeUnit = TimeUnit.HOURS)
    fun getAcgPictureType(type: Flowable<List<AcgPictureType>>): Flowable<List<AcgPictureType>>

    @ProviderKey("get-acgpictures")
    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getAcgPictures(pictureItem: Flowable<List<AcgPictureItem>>, keyGroup: DynamicKey): Flowable<List<AcgPictureItem>>

}