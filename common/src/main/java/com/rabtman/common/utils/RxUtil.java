package com.rabtman.common.utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RxUtil {

  /**
   * Flowable线程切换简化
   */
  public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
    return new FlowableTransformer<T, T>() {
      @Override
      public Flowable<T> apply(Flowable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * Completable线程切换简化
   */
  public static CompletableTransformer completableSchedulerHelper() {
    return new CompletableTransformer() {
      @Override
      public Completable apply(Completable observable) {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * 统一返回结果处理
   * @param <T>
   * @return
   */
    /*public static <T> FlowableTransformer<GoldHttpResponse<T>, T> handleGoldResult() {   //compose判断结果
        return new FlowableTransformer<GoldHttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<GoldHttpResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<GoldHttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(GoldHttpResponse<T> tGoldHttpResponse) {
                        if(tGoldHttpResponse.getResults() != null) {
                            return createData(tGoldHttpResponse.getResults());
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }*/

  /**
   * 生成Flowable
   */
  public static <T> Flowable<T> createData(final T t) {
    return Flowable.create(new FlowableOnSubscribe<T>() {
      @Override
      public void subscribe(FlowableEmitter<T> emitter) throws Exception {
        try {
          emitter.onNext(t);
          emitter.onComplete();
        } catch (Exception e) {
          emitter.onError(e);
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
