package com.rabtman.acgschedule.mvp.model.dao;

import android.util.Pair;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache;
import com.rabtman.common.utils.RxRealmUtils;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
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

  public Flowable<ScheduleCache> addScheduleCache(final ScheduleCache item) {
    return RxRealmUtils
        .flowableExec(realmConfiguration, new Consumer<Pair<FlowableEmitter, Realm>>() {
          @Override
          public void accept(final Pair<FlowableEmitter, Realm> pair) throws Exception {
            pair.second.executeTransaction(new Transaction() {
              @Override
              public void execute(Realm r) {
                pair.first.onNext(r.copyFromRealm(r.copyToRealmOrUpdate(item)));
                pair.first.onComplete();
              }
            });
          }
        });
  }

  public Flowable<Boolean> deleteScheduleCacheByUrl(final String url) {
    return RxRealmUtils
        .flowableExec(realmConfiguration, new Consumer<Pair<FlowableEmitter, Realm>>() {
          @Override
          public void accept(final Pair<FlowableEmitter, Realm> pair) throws Exception {
            pair.second.executeTransactionAsync(new Transaction() {
              @Override
              public void execute(Realm r) {
                final boolean isSuccess = r.where(ScheduleCache.class)
                    .equalTo("scheduleUrl", url)
                    .findAll()
                    .deleteAllFromRealm();
                pair.first.onNext(isSuccess);
                pair.first.onComplete();
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
          queryResult == null ? new ScheduleCache("", -1) : realm.copyFromRealm(queryResult)
      );
    }
  }

  public Flowable<List<ScheduleCache>> getScheduleCollectCaches() {
    try (final Realm realm = Realm.getInstance(realmConfiguration)) {
      RealmResults<ScheduleCache> queryResult = realm.where(ScheduleCache.class)
          .equalTo("isCollect", true)
          .findAll();
      if (queryResult != null) {
        return Flowable.just(realm.copyFromRealm(queryResult));
      } else {
        return Flowable.empty();
      }
    }
  }
}
