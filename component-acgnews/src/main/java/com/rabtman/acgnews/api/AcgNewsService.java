package com.rabtman.acgnews.api;

import com.rabtman.acgnews.base.constant.HtmlConstant;
import com.rabtman.acgnews.mvp.model.entity.SHPage;
import com.rabtman.acgnews.mvp.model.entity.SHPostDetail;
import com.rabtman.acgnews.mvp.model.entity.SHResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Rabtman
 */
public interface AcgNewsService {

  @GET(HtmlConstant.ISH_URL + "ver/1f590097/article/list")
  Flowable<SHResponse<SHPage>> getISHNews(@Query("page") int pageIndex,
      @Query("pageSize") int pageSize, @Query("categoryID") int categoryID);

  @GET(HtmlConstant.ISH_URL + "ver/3c590236/article/detail")
  Flowable<SHResponse<SHPostDetail>> getISHNewsDetail(@Query("id") int postId);
}
