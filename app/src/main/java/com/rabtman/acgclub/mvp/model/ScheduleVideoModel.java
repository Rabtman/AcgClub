package com.rabtman.acgclub.mvp.model;

import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.ScheduleVideoContract;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleVideo;
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
        Element html = Jsoup.connect(url).timeout(10000).get();
        if(html == null){
          e.onError(new Throwable("element html is null"));
        }else {
          ScheduleVideo scheduleVideo = JP.from(html, ScheduleVideo.class);
          StringBuilder scheduleVideoHtmlBuilder = new StringBuilder();
          scheduleVideoHtmlBuilder.append(HtmlConstant.SCHEDULE_VIDEO_CSS);
          scheduleVideoHtmlBuilder.append("<div id=\"vedio\">");
          scheduleVideoHtmlBuilder.append(scheduleVideo.getVideoHtml());
          scheduleVideoHtmlBuilder.append("</div>");
          scheduleVideo.setVideoHtml(scheduleVideoHtmlBuilder.toString());
          e.onNext(scheduleVideo);
          e.onComplete();
        }
      }
    }, BackpressureStrategy.BUFFER);
  }
}
