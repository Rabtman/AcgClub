package com.rabtman.common.integration;

import io.realm.RealmMigration;

/**
 * @author Rabtman
 */

public interface IDbMigrationManager extends RealmMigration {

  /**
   * 使用{@link RealmMigration}注入各个组件中编写的迁移
   */
  void injectDbMigrations(RealmMigration... dbMigrations);
}
