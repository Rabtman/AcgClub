package com.rabtman.common.integration

import io.realm.RealmMigration

/**
 * @author Rabtman
 */
interface IDbMigrationManager : RealmMigration {
    /**
     * 使用[RealmMigration]注入各个组件中编写的迁移
     */
    fun injectDbMigrations(vararg dbMigrations: RealmMigration)
}