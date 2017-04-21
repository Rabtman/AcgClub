package com.rabtman.common.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public interface GlobeHttpHandler {

  GlobeHttpHandler EMPTY = new GlobeHttpHandler() {
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain,
        Response response) {
      return null;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
      return null;
    }
  };

  Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

  Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

}
