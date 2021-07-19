package com.rabtman.common.utils;

import android.util.Pair;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
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

  public static Completable completableExec(final RealmConfiguration configuration,
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

  public static <T> Single<T> singleExec(final RealmConfiguration configuration,
      final Consumer<Pair<SingleEmitter, Realm>> emitter) {
    return Single.create(new SingleOnSubscribe<T>() {
      @Override
      public void subscribe(SingleEmitter<T> e) throws Exception {
        try (Realm realm = Realm.getInstance(configuration)) {
          emitter.accept(new Pair<SingleEmitter, Realm>(e, realm));
        }
      }
    });
  }

  public static <T> Flowable<T> flowableExec(final RealmConfiguration configuration,
      final Consumer<Pair<FlowableEmitter, Realm>> emitter) {
    return Flowable.create(new FlowableOnSubscribe<T>() {
      @Override
      public void subscribe(FlowableEmitter<T> e) throws Exception {
        try (Realm realm = Realm.getInstance(configuration)) {
          emitter.accept(new Pair<FlowableEmitter, Realm>(e, realm));
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
