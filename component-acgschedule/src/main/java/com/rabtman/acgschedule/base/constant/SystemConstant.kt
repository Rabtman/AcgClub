package com.rabtman.acgschedule.base.constant

import com.rabtman.acgschedule.R

/**
 * @author Rabtman
 */
object SystemConstant {
    const val DB_NAME = "lib.schedule.realm"
    const val DB_VERSION: Long = 1

    //番剧标题栏
    @JvmField
    val ACG_SCHEDULE_TITLE = arrayOf("放送表")

    //番剧时间表TAB标题栏
    @JvmField
    val SCHEDULE_WEEK_TITLE = arrayOf("一", "二", "三", "四", "五", "六", "日")

    //番剧时间表LIST标题栏
    @JvmField
    val SCHEDULE_WEEK_LIST_TITLE = arrayOf("月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日",
            "日曜日")

    //番剧时间表LIST标题栏图标
    @JvmField
    val SCHEDULE_WEEK_LIST_TITLE_DRAWABLE = intArrayOf(R.drawable.ic_schedule_time_monday,
            R.drawable.ic_schedule_time_tuesday, R.drawable.ic_schedule_time_wednesday,
            R.drawable.ic_schedule_time_thursday, R.drawable.ic_schedule_time_friday,
            R.drawable.ic_schedule_time_saturday, R.drawable.ic_schedule_time_sunday)
}