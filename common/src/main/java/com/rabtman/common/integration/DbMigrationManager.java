package com.rabtman.common.integration;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Rabtman
 */
@Singleton
public class DbMigrationManager implements IDbMigrationManager, RealmMigration {

  private final Map<Integer, RealmMigration> mMigrattions = new LinkedHashMap<>();

  @Inject
  public DbMigrationManager() {

  }

  @Override
  public void injectDbMigrations(RealmMigration... dbMigrations) {
    int currentHashCode;
    for (RealmMigration migration : dbMigrations) {
      currentHashCode = migration.hashCode();
      if (mMigrattions.containsKey(currentHashCode)) {
        continue;
      }
      mMigrattions.put(currentHashCode, migration);
    }
  }

  @Override
  public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
    for (Entry<Integer, RealmMigration> migration : mMigrattions.entrySet()) {
      migration.getValue().migrate(realm, oldVersion, newVersion);
    }
  }

}
