package com.rabtman.common.utils;

import com.rabtman.common.http.BaseResponse;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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
   */
  public static <T> FlowableTransformer<BaseResponse<T>, T> handleResult() {
    return new FlowableTransformer<BaseResponse<T>, T>() {
      @Override
      public Flowable<T> apply(Flowable<BaseResponse<T>> httpResponseFlowable) {
        return httpResponseFlowable.flatMap(new Function<BaseResponse<T>, Flowable<T>>() {
          @Override
          public Flowable<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
            /*if (tBaseResponse.code == 200) {
              return createData(tBaseResponse.data);
            } else {
              return Flowable.error(new ApiException(tBaseResponse.message));
            }*/
            return createData(tBaseResponse.data);
          }
        });

      }
    };
  }

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
