package com.rabtman.acgclub.mvp.model;

import android.text.TextUtils;
import com.fcannizzaro.jsoup.annotations.JP;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.ScheduleMainContract;
import com.rabtman.acgclub.mvp.model.jsoup.DilidiliInfo;
import com.rabtman.acgclub.mvp.model.jsoup.DilidiliInfo.ScheudleBanner;
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
        Element html = Jsoup.connect(HtmlConstant.DILIDILI_URL).timeout(10000).get();
        if (html == null) {
          e.onError(new Throwable("element html is null"));
        } else {
          DilidiliInfo dilidiliInfo = JP.from(html, DilidiliInfo.class);
          Iterator<ScheudleBanner> scheudleBannerIterator = dilidiliInfo.getScheudleBanners()
              .iterator();
          while (scheudleBannerIterator.hasNext()) {
            ScheudleBanner scheudleBanner = scheudleBannerIterator.next();
            if (TextUtils.isEmpty(scheudleBanner.getName()) |
                TextUtils.isEmpty(scheudleBanner.getImgUrl()) |
                TextUtils.isEmpty(scheudleBanner.getAnimeLink())) {
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
