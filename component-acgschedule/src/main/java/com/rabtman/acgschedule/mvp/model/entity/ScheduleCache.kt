package com.rabtman.acgschedule.mvp.model.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Rabtman
 */
open class ScheduleCache : RealmObject {
    @PrimaryKey
    var scheduleUrl: String? = null
    var name: String? = null
    var imgUrl: String? = null
    var isCollect = false
    var lastWatchPos = -1

    constructor() {}
    constructor(scheduleUrl: String?, lastWatchPos: Int) {
        this.scheduleUrl = scheduleUrl
        this.lastWatchPos = lastWatchPos
    }

    constructor(scheduleUrl: String?, name: String?, imgUrl: String?, isCollect: Boolean,
                lastWatchPos: Int) {
        this.scheduleUrl = scheduleUrl
        this.name = name
        this.imgUrl = imgUrl
        this.isCollect = isCollect
        this.lastWatchPos = lastWatchPos
    }

    override fun toString(): String {
        return "ScheduleCache{" +
                "scheduleUrl='" + scheduleUrl + '\'' +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", isCollect=" + isCollect +
                ", lastWatchPos=" + lastWatchPos +
                '}'
    }

}