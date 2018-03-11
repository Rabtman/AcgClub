package com.rabtman.acgcomic

import com.rabtman.acgcomic.base.constant.SystemConstant
import com.rabtman.common.base.BaseApplication
import com.rabtman.common.utils.LogUtil
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */

class AcgComicApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder()
                        .name(SystemConstant.DB_NAME)
                        .schemaVersion(SystemConstant.DB_VERSION)
                        .build())
        LogUtil.init(BuildConfig.DEBUG, BuildConfig.APPLICATION_ID)
    }

    override fun onDefaultProcessCreate() {

    }
}
