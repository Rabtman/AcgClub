package com.rabtman.common.utils;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmConfiguration;

/**
 * @author Rabtman realm事务转换rx工具类
 */
public class RxRealmUtils {

  private RxRealmUtils() {
  }

  public static Completable exec(final Consumer<Realm> transaction) {
    return exec(Realm.getDefaultConfiguration(), transaction);
  }

  public static Completable exec(final RealmConfiguration configuration,
      final Consumer<Realm> transaction) {
    return Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        try (Realm realm = Realm.getInstance(configuration)) {
          realm.executeTransaction(new Transaction() {
            @Override
            public void execute(Realm r) {
              try {
                transaction.accept(r);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          });
        }
      }
    });
  }
}
