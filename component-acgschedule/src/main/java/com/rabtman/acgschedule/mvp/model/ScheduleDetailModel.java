package com.rabtman.acgschedule.mvp.model;

import android.text.TextUtils;
import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgschedule.base.constant.HtmlConstant;
import com.rabtman.acgschedule.base.constant.SystemConstant;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgschedule.mvp.model.dao.ScheduleDAO;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
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
        Element html = Jsoup.connect(scheduleLink).get();
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
  public Flowable<ScheduleCache> getScheduleCacheByUrl(String scheduleUrl) {
    return DAO.getScheduleCacheByUrl(scheduleUrl);
  }

  public Flowable<ScheduleCache> updateScheduleWatchRecord(final ScheduleCache item,
      final int lastWatchPos) {
    return DAO.getScheduleCacheByUrl(item.getScheduleUrl())
        .flatMap(new Function<ScheduleCache, Flowable<ScheduleCache>>() {
          @Override
          public Flowable<ScheduleCache> apply(ScheduleCache scheduleCache) throws Exception {
            if (TextUtils.isEmpty(scheduleCache.getScheduleUrl())) {
              scheduleCache = item;
            }
            scheduleCache.setLastWatchPos(lastWatchPos);
            return DAO.addScheduleCache(scheduleCache);
          }
        });
  }

  @Override
  public Flowable<ScheduleCache> collectSchedule(final ScheduleCache item, final boolean isAdd) {
    return DAO.getScheduleCacheByUrl(item.getScheduleUrl())
        .flatMap(new Function<ScheduleCache, Flowable<ScheduleCache>>() {
          @Override
          public Flowable<ScheduleCache> apply(ScheduleCache scheduleCache) throws Exception {
            if (TextUtils.isEmpty(scheduleCache.getScheduleUrl())) {
              scheduleCache = item;
            }
            scheduleCache.setCollect(isAdd);
            return DAO.addScheduleCache(scheduleCache);
          }
        });
  }
}
