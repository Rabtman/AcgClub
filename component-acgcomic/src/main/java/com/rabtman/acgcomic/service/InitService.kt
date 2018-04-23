package com.rabtman.acgcomic.service

import android.app.IntentService
import android.content.Intent
import com.rabtman.acgcomic.base.constant.SPConstant
import com.rabtman.acgcomic.base.constant.SystemConstant
import com.rabtman.acgcomic.mvp.model.dao.ComicDAO
import com.rabtman.common.BuildConfig
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.SPUtils
import com.rabtman.common.utils.Utils

/**
 * @author Rabtman
 * 用于启动后做一些异步的初始化操作
 */

class InitService : IntentService("InitService") {

    override fun onHandleIntent(intent: Intent?) {
        dealWithDiffVer(BuildConfig.appVerCode)
    }

    /**
     * 根据不同版本做一些针对处理
     */
    private fun dealWithDiffVer(versionCode: Int) {
        if (versionCode == 3) {
            if (!SPUtils.getInstance().getBoolean(SPConstant.VER_3_CLEAR_DB, false)) {
                val DAO = ComicDAO(
                        Utils.getAppComponent()
                                .repositoryManager()
                                .obtainRealmConfig(SystemConstant.DB_NAME)
                )
                //线上该id漫画数据异常导致了本地缓存错误，进行针对性数据清理
                DAO.getComicCacheById("381")
                        .flatMap({ comicCache ->
                            comicCache.chapterPos = 0
                            comicCache.pagePos = 0
                            DAO.addComicCache(comicCache)
                        }).subscribe(
                        {
                            SPUtils.getInstance().put(SPConstant.VER_3_CLEAR_DB, true)
                            LogUtil.d("comic id 381 cache clear")
                        },
                        { throwable -> throwable.printStackTrace() })
            }
        }
    }
}
