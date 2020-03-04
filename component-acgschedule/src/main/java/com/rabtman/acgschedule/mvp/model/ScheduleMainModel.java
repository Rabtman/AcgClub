package com.rabtman.acgschedule.mvp.model;

import android.text.TextUtils;
import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgschedule.base.constant.HtmlConstant;
import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleBanner;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import java.util.Iterator;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@FragmentScope
public class ScheduleMainModel extends BaseModel implements ScheduleMainContract.Model {

  @Inject
  public ScheduleMainModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<DilidiliInfo> getDilidiliInfo() {
    return Flowable.create(new FlowableOnSubscribe<DilidiliInfo>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<DilidiliInfo> e) throws Exception {
        Element html = Jsoup.connect(HtmlConstant.DILIDILI_URL).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          DilidiliInfo dilidiliInfo = JP.from(html, DilidiliInfo.class);
          Iterator<ScheduleWeek> scheudleWeekIterator = dilidiliInfo.getScheduleWeek().iterator();
          while (scheudleWeekIterator.hasNext()) {
            ScheduleWeek scheduleWeek = scheudleWeekIterator.next();
            Iterator<ScheduleWeek.ScheduleItem> scheduleItemIterator = scheduleWeek
                .getScheduleItems().iterator();
            while (scheduleItemIterator.hasNext()) {
              ScheduleWeek.ScheduleItem scheduleItem = scheduleItemIterator.next();
              if (scheduleItem.getAnimeLink().contains("www.005.tv")) {
                scheduleItemIterator.remove();
              }
            }
          }
          Iterator<ScheduleBanner> scheudleBannerIterator = dilidiliInfo.getScheduleBanners()
              .iterator();
          while (scheudleBannerIterator.hasNext()) {
            ScheduleBanner scheudleBanner = scheudleBannerIterator.next();
            if (TextUtils.isEmpty(scheudleBanner.getImgUrl()) |
                TextUtils.isEmpty(scheudleBanner.getAnimeLink()) |
                !scheudleBanner.getAnimeLink().contains("anime")) {
              scheudleBannerIterator.remove();
            }
          }
          e.onNext(dilidiliInfo);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }

}
