package com.rabtman.acgcomic.base.constant

/**
 * @author Rabtman
 */

class SystemConstant {
    companion object {
        const val DB_NAME = "lib.acgcomic.realm"
        const val DB_VERSION = 1L

        /**
         * 漫画来源
         */
        const val COMIC_SOURCE_OACG = "oacg"
        const val COMIC_SOURCE_QIMIAO = "qimiao"
    }
}

class HtmlConstant {
    companion object {
        const val DMZJ_URL = "https://m.dmzj.com/"
        const val DMZJ_IMG_URL = "https://images.dmzj.com/"

        const val qimiao_URL = "http://comic.oacg.cn/"
        const val qimiao_IMG_URL = "http://gx.cdn.comic.oacg.cn"

        const val QIMIAO_URL = "https://m.qimiaomh.com"
    }
}

class IntentConstant {
    companion object {
        const val QIMIAO_COMIC_ITEM = "qimiao_comic_item"
        const val QIMIAO_COMIC_ID = "qimiao_comic_id"
        const val QIMIAO_COMIC_TITLE = "qimiao_comic_title"
        const val QIMIAO_COMIC_CHAPTER_URL = "qimiao_comic_chapter_url"
        const val QIMIAO_COMIC_CHAPTER_ID = "qimiao_comic_chapter_id"
    }
}

class SPConstant {
    companion object {
        const val VER_3_CLEAR_DB = "ver_3_clear_db"
    }
}
