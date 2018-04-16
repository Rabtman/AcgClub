package com.rabtman.acgschedule.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@ActivityScope
public class ScheduleNewModel extends BaseModel implements ScheduleNewContract.Model {

  @Inject
  public ScheduleNewModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<ScheduleNew> getScheduleNew(final String url) {
    return Flowable.create(new FlowableOnSubscribe<ScheduleNew>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ScheduleNew> e) throws Exception {
        Element html = Jsoup.connect(url).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          ScheduleNew scheduleNew = JP.from(html, ScheduleNew.class);
          e.onNext(scheduleNew);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }

}
