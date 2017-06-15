package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.ScheduleTimeContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
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
@FragmentScope
public class ScheduleModel extends BaseModel implements ScheduleTimeContract.Model {

  @Inject
  public ScheduleModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<AcgScheduleInfo> getScheduleInfo() {
    return Flowable.create(new FlowableOnSubscribe<AcgScheduleInfo>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<AcgScheduleInfo> e) throws Exception {
        Element html = Jsoup.connect(HtmlConstant.DILIDILI_URL).timeout(10000).get();
        if(html == null){
          e.onError(new Throwable("element html is null"));
        }else {
          AcgScheduleInfo acgScheduleInfo = JP.from(html, AcgScheduleInfo.class);
          e.onNext(acgScheduleInfo);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
