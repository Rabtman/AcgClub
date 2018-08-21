package com.rabtman.acgclub.api;

import com.rabtman.acgclub.mvp.model.entity.VersionInfo;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author Rabtman
 */

public interface AcgService {

  @GET
  Flowable<ResponseBody> getFictionRecent(@Url String url);

  @GET
  Flowable<ResponseBody> getFictionSearchResult(@Url String url);

  @GET()
  Flowable<VersionInfo> getVersionInfo(@Url String url);
}
