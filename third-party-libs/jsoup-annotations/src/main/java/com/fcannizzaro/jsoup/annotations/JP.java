package com.fcannizzaro.jsoup.annotations;

import java.util.List;
import org.jsoup.nodes.Element;

/**
 * Created by Francesco Cannizzaro (fcannizzaro)
 */

public class JP {


  public static <T> T from(Element container, Class<T> clazz) throws Exception{
    return JsoupProcessor.from(container, clazz);
  }

  public static <T> List<T> fromList(Element container, Class<T> clazz) throws Exception{
    return JsoupProcessor.fromList(container, clazz);
  }

}
