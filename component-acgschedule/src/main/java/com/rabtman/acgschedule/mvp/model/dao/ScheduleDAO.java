package com.rabtman.acgschedule.mvp.model.dao;

import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache;
import com.rabtman.common.utils.RxRealmUtils;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import java.util.List;

/**
 * @author Rabtman
 */
public class ScheduleDAO {

  private RealmConfiguration realmConfiguration;

  public ScheduleDAO(RealmConfiguration realmConfiguration) {
    this.realmConfiguration = realmConfiguration;
  }

  public Completable addScheduleCache(final ScheduleCache item) {
    return RxRealmUtils.exec(realmConfiguration, new Consumer<Realm>() {
      @Override
      public void accept(Realm realm) throws Exception {
        realm.executeTransactionAsync(new Transaction() {
          @Override
          public void execute(Realm r) {
            r.copyToRealmOrUpdate(item);
          }
        });
      }
    });
  }

  public Completable deleteScheduleCacheByUrl(final String url) {
    return RxRealmUtils.exec(realmConfiguration, new Consumer<Realm>() {
      @Override
      public void accept(Realm realm) throws Exception {
        realm.executeTransactionAsync(new Transaction() {
          @Override
          public void execute(Realm r) {
            r.where(ScheduleCache.class)
                .equalTo("scheduleUrl", url)
                .findAll()
                .deleteAllFromRealm();
          }
        });
      }
    });
  }

  public Flowable<ScheduleCache> getScheduleCacheByUrl(String url) {
    try (final Realm realm = Realm.getInstance(realmConfiguration)) {
      ScheduleCache queryResult = realm.where(ScheduleCache.class)
          .equalTo("scheduleUrl", url)
          .findFirst();
      return Flowable.just(
          queryResult == null ? new ScheduleCache(url, -1) : realm.copyFromRealm(queryResult)
      );
    }
  }

  public Flowable<List<ScheduleCache>> getScheduleCaches() {
    try (final Realm realm = Realm.getInstance(realmConfiguration)) {
      RealmResults<ScheduleCache> queryResult = realm.where(ScheduleCache.class)
          .findAll();
      if (queryResult != null) {
        return Flowable.just(realm.copyFromRealm(queryResult));
      } else {
        return Flowable.empty();
      }
    }
  }
}
