package com.rabtman.acgschedule.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgschedule.base.constant.HtmlConstant;
import com.rabtman.acgschedule.base.constant.SystemConstant;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgschedule.mvp.model.dao.ScheduleDAO;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleHistory;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@ActivityScope
public class ScheduleDetailModel extends BaseModel implements ScheduleDetailContract.Model {

  private ScheduleDAO DAO = new ScheduleDAO(
      mRepositoryManager.obtainRealmConfig(SystemConstant.DB_NAME));

  @Inject
  public ScheduleDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<ScheduleDetail> getScheduleDetail(final String url) {
    return Flowable.create(new FlowableOnSubscribe<ScheduleDetail>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ScheduleDetail> e) throws Exception {
        String scheduleLink = url;
        if (!url.contains("http")) {
          scheduleLink = HtmlConstant.DILIDILI_URL + url;
        }
        Element html = Jsoup.connect(scheduleLink).timeout(10000).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          ScheduleDetail scheduleDetail = JP.from(html, ScheduleDetail.class);
          e.onNext(scheduleDetail);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }

  @Override
  public Flowable<ScheduleCollection> getScheduleCollection(String scheduleUrl) {
    return DAO.getScheduleCollectionByUrl(scheduleUrl);
  }

  @Override
  public Flowable<ScheduleHistory> getScheduleHistory(String scheduleUrl) {
    return DAO.getScheduleHistoryByUrl(scheduleUrl);
  }

  public Completable updateScheduleHistory(String scheduleUrl, final int lastPos) {
    return DAO.getScheduleHistoryByUrl(scheduleUrl)
        .flatMapCompletable(new Function<ScheduleHistory, Completable>() {
          @Override
          public Completable apply(ScheduleHistory scheduleHistory) throws Exception {
            scheduleHistory.setLastRecord(lastPos);
            return DAO.addScheduleHistory(scheduleHistory);
          }
        });
  }

  @Override
  public Completable addOrDeleteScheduleCollection(ScheduleCollection item, boolean isAdd) {
    if (isAdd) {
      return DAO.addScheduleCollection(item);
    } else {
      return DAO.deleteByUrl(item.getScheduleUrl());
    }
  }
}
