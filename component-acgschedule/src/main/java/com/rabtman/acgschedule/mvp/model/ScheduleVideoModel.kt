package com.rabtman.acgschedule.mvp.model

import android.text.TextUtils
import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgschedule.mvp.contract.ScheduleVideoContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleVideo
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ScheduleVideoModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ScheduleVideoContract.Model {
    override fun getScheduleVideo(url: String): Flowable<ScheduleVideo> {
        return Flowable.create({ e ->
            val html: Element? = Jsoup.connect(url).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val scheduleVideo = JP.from(html, ScheduleVideo::class.java)
                if (!TextUtils.isEmpty(scheduleVideo.videoHtml)) {
                    scheduleVideo.videoUrl = "http://tup.yhdm.tv/?m=1&vid=" + scheduleVideo.videoUrl
                }
                /*StringBuilder scheduleVideoHtmlBuilder = new StringBuilder();
          scheduleVideoHtmlBuilder.append(HtmlConstant.SCHEDULE_VIDEO_CSS);
          scheduleVideoHtmlBuilder.append("<div class=\"player_main\" style=\"position: relative;\"> ");
          scheduleVideoHtmlBuilder.append(scheduleVideo.getVideoHtml());
          scheduleVideoHtmlBuilder.append("</div>");
          scheduleVideo.setVideoHtml(scheduleVideoHtmlBuilder.toString());*/e.onNext(scheduleVideo)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}