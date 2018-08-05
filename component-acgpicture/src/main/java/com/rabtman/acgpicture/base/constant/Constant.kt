package com.rabtman.acgpicture.base.constant

import com.rabtman.common.base.constant.SystemConstant

/**
 * @author Rabtman
 */

class SystemConstant {
    companion object {
        const val DB_NAME = "lib.acgpicture.realm"
        const val DB_VERSION = 1L
        //acg图库标题栏
        val ACG_PICTURE_TITLE: Array<String> = arrayOf("萌图", "Cosplay")
        val ACG_PICTURE_TYPE: Array<String> = arrayOf("moeimg", "cosplay")
        //图片保存路径
        const val ACGPICTURE_PATH = SystemConstant.pathRoot + "/pictures"
    }
}

class HtmlConstant {
    companion object {
        const val ACG_PICTURE_URL = "http://111.231.79.214:8000/api/acgclub/v1/"
        const val ANIME_PICTURE_URL = "https://anime-pictures.net/"
        const val APICTURE_ANIME_URL = "http://www.acg.fi/anime/page/"
    }
}

class IntentConstant {
    companion object {
        const val ACGPICTURE_TYPE = "acgpicture_type"
        const val ACGPICTURE_ITEM = "acgpicture_item"
    }
}

class SPConstant {
    companion object {

    }
}
