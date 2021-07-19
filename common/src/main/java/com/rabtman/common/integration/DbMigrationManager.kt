package com.rabtman.common.integration

import io.realm.DynamicRealm
import io.realm.RealmMigration
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rabtman
 */
@Singleton
class DbMigrationManager @Inject constructor() : IDbMigrationManager, RealmMigration {

    private val mMigrations: MutableMap<Int, RealmMigration> = mutableMapOf()

    override fun injectDbMigrations(vararg dbMigrations: RealmMigration) {
        var currentHashCode: Int
        for (migration in dbMigrations) {
            currentHashCode = migration.hashCode()
            if (mMigrations.containsKey(currentHashCode)) {
                continue
            }
            mMigrations[currentHashCode] = migration
        }
    }

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        for ((_, value) in mMigrations) {
            value.migrate(realm, oldVersion, newVersion)
        }
    }
}