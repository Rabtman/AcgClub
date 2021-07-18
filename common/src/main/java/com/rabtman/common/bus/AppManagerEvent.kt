package com.rabtman.common.bus

/**
 * @author Rabtman
 */
class AppManagerEvent(var type: Int, var msg: Any) {
    companion object {
        const val START_ACTIVITY = 1
        const val SHOW_SNACK_BAR = 2
        const val KILL_ALL = 3
        const val APP_EXIT = 4
    }
}