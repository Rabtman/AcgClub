package com.rabtman.acgschedule.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage;
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
public class ScheduleOtherModel extends BaseModel implements ScheduleOtherContract.Model {

  @Inject
  public ScheduleOtherModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<ScheduleOtherPage> getScheduleOtherPage(final String url) {
    return Flowable.create(new FlowableOnSubscribe<ScheduleOtherPage>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ScheduleOtherPage> e) throws Exception {
        Element html = Jsoup.connect(url).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          ScheduleOtherPage scheduleOtherPage = JP.from(html, ScheduleOtherPage.class);
          e.onNext(scheduleOtherPage);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }

}
