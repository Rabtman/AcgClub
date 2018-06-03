package com.rabtman.acgnews.api;

import com.rabtman.acgnews.base.constant.HtmlConstant;
import com.rabtman.acgnews.mvp.model.entity.SHPage;
import com.rabtman.acgnews.mvp.model.entity.SHResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Rabtman
 */
public interface AcgNewsService {

  @GET(HtmlConstant.ISH_URL + "article/list/ver/60784573/page/{pageIndex}.json")
  Flowable<SHResponse<SHPage>> getISHNews(@Path("pageIndex") int pageIndex);
}
