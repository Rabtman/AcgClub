package com.rabtman.acgclub.mvp.contract;

import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import java.io.File;

/**
 * @author Rabtman
 */
public interface APicDetailContract {

  interface View extends IView {

    void showPicture(String picUrl);

    void savePictureSuccess(File imgFile);
  }

  interface Model extends IModel {

  }
}
