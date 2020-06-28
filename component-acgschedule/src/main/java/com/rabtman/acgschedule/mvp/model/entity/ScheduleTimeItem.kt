package com.rabtman.acgschedule.mvp.model.entity

import com.chad.library.adapter.base.entity.JSectionEntity
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleWeek.ScheduleItem

/**
 * @author Rabtman
 */
class ScheduleTimeItem : JSectionEntity {
    override var isHeader = false

    @JvmField
    var header: String? = null

    @JvmField
    var headerIndex: Int

    @JvmField
    var data: ScheduleItem? = null

    constructor(isHeader: Boolean, header: String?, index: Int) {
        this.isHeader = isHeader
        this.header = header
        headerIndex = index
    }

    constructor(scheduleItem: ScheduleItem?, index: Int) {
        data = scheduleItem
        headerIndex = index
    }

}