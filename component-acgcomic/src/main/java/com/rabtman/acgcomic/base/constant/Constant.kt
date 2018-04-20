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
    }
}

class HtmlConstant {
    companion object {
        const val DMZJ_URL = "https://m.dmzj.com/"
        const val DMZJ_IMG_URL = "https://images.dmzj.com/"

        const val OACG_URL = "http://comic.oacg.cn/"
        const val OACG_IMG_URL = "http://gx.cdn.comic.oacg.cn"
    }
}

class IntentConstant {
    companion object {
        const val OACG_COMIC_ITEM = "oacg_comic_item"
        const val OACG_COMIC_ID = "oacg_comic_id"
        const val OACG_COMIC_TITLE = "oacg_comic_title"
        const val OACG_COMIC_CHAPTERID = "oacg_comic_chapterid"
        const val OACG_COMIC_CHAPTERPOS = "oacg_comic_chapterpos"
    }
}
