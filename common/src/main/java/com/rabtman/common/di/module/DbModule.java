package com.rabtman.common.di.module;

import com.rabtman.common.integration.DbMigrationManager;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import javax.inject.Singleton;

/**
 * @author Rabtman
 */
@Module
public class DbModule {

  private static final String DB_NAME = "acgclub.realm";
  private static final int DB_VERSION = 1;

  @Singleton
  @Provides
  public RealmConfiguration providerRealmConfiguration(RealmMigration migration) {
    return new RealmConfiguration.Builder()
        .name(DB_NAME)
        .schemaVersion(DB_VERSION)
        .migration(migration)
        .build();
  }

  @Singleton
  @Provides
  public RealmMigration providerRealmMigration(DbMigrationManager dbMigrationManager) {
    return dbMigrationManager;
  }
}
