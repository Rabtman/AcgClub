package com.rabtman.acgclub.mvp.presenter;

import android.Manifest.permission;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.mvp.contract.APicDetailContract;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.FileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import javax.inject.Inject;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * @author Rabtman
 */
@ActivityScope
public class APicDetailPresenter extends
    BasePresenter<APicDetailContract.Model, APicDetailContract.View> {

  @Inject
  public APicDetailPresenter(APicDetailContract.Model model,
      APicDetailContract.View rootView) {
    super(model, rootView);
  }

  public void downloadPicture(RxPermissions rxPermissions, RxDownload rxDownload, String imgUrl) {
    File[] files = rxDownload.getRealFiles(imgUrl);
    if (files != null) {
      if (FileUtils.isFileExists(files[0])) {
        mView.showMsg(R.string.msg_success_download_picture);
        return;
      } else {
        rxDownload.deleteServiceDownload(imgUrl, true);
      }
    }
    final String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
    final String dir = FileUtils.getStorageFilePath(SystemConstant.ACG_IMG_PATH).getAbsolutePath();
    rxPermissions
        .request(permission.WRITE_EXTERNAL_STORAGE)
        .doOnNext(new Consumer<Boolean>() {
          @Override
          public void accept(@NonNull Boolean aBoolean) throws Exception {
            if (!aBoolean) {
              throw new RuntimeException("no permission");
            }
          }
        })
        .observeOn(Schedulers.io())
        .compose(rxDownload.transform(imgUrl, imgName, dir))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<DownloadStatus>() {
          @Override
          public void accept(@NonNull DownloadStatus downloadStatus) throws Exception {

          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            mView.showError(R.string.msg_error_download_picture);
          }
        }, new Action() {
          @Override
          public void run() throws Exception {
            mView.showMsg(R.string.msg_success_download_picture);
            mView.savePictureSuccess(new File(dir, imgName));
          }
        });

  }
}
