package com.rabtman.acgcomic.base

import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import io.realm.annotations.RealmModule

/**
 * @author Rabtman
 */
@RealmModule(classes = arrayOf(OacgComicItem::class))
open class AcgComicRealmModule {

}