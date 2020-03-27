package com.rabtman.acgschedule.mvp.model;

import android.text.TextUtils;
import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgschedule.mvp.contract.ScheduleVideoContract;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleVideo;
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
public class ScheduleVideoModel extends BaseModel implements ScheduleVideoContract.Model {

  @Inject
  public ScheduleVideoModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<ScheduleVideo> getScheduleVideo(final String url) {
    return Flowable.create(new FlowableOnSubscribe<ScheduleVideo>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<ScheduleVideo> e) throws Exception {
        Element html = Jsoup.connect(url).get();
        if(html == null){
          e.onError(new Throwable("element html is null"));
        }else {
          ScheduleVideo scheduleVideo = JP.from(html, ScheduleVideo.class);
          if (!TextUtils.isEmpty(scheduleVideo.getVideoHtml())) {
            scheduleVideo.setVideoUrl("http://tup.yhdm.tv/?m=1&vid=" + scheduleVideo.getVideoUrl());
          }
          /*StringBuilder scheduleVideoHtmlBuilder = new StringBuilder();
          scheduleVideoHtmlBuilder.append(HtmlConstant.SCHEDULE_VIDEO_CSS);
          scheduleVideoHtmlBuilder.append("<div class=\"player_main\" style=\"position: relative;\"> ");
          scheduleVideoHtmlBuilder.append(scheduleVideo.getVideoHtml());
          scheduleVideoHtmlBuilder.append("</div>");
          scheduleVideo.setVideoHtml(scheduleVideoHtmlBuilder.toString());*/
          e.onNext(scheduleVideo);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
