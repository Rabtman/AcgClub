package com.rabtman.acgschedule.mvp.model.dao;

import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.common.utils.RxRealmUtils;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmConfiguration;

/**
 * @author Rabtman
 */
public class ScheduleDAO {

  private RealmConfiguration realmConfiguration;

  public ScheduleDAO(RealmConfiguration realmConfiguration) {
    this.realmConfiguration = realmConfiguration;
  }

  public Completable add(final ScheduleCollection item) {
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

  public Completable deleteByUrl(final String url) {
    return RxRealmUtils.exec(realmConfiguration, new Consumer<Realm>() {
      @Override
      public void accept(Realm realm) throws Exception {
        realm.executeTransactionAsync(new Transaction() {
          @Override
          public void execute(Realm r) {
            r.where(ScheduleCollection.class)
                .equalTo("scheduleUrl", url)
                .findAll()
                .deleteAllFromRealm();
          }
        });
      }
    });
  }

  public Flowable<ScheduleCollection> getScheduleCollectionByUrl(String url) {
    try (final Realm realm = Realm.getInstance(realmConfiguration)) {
      ScheduleCollection queryResult = realm.where(ScheduleCollection.class)
          .equalTo("scheduleUrl", url)
          .findFirst();
      if (queryResult != null) {
        return Flowable.just(realm.copyFromRealm(queryResult));
      } else {
        return Flowable.empty();
      }
    }
  }
}
