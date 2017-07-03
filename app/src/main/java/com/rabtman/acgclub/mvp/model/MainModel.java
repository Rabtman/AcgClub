package com.rabtman.acgclub.mvp.model;

import com.rabtman.acgclub.api.AcgService;
import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.acgclub.mvp.model.entity.VersionInfo;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import io.reactivex.Flowable;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

  @Inject
  public MainModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Flowable<VersionInfo> getVersionInfo() {
    return mRepositoryManager.obtainRetrofitService(AcgService.class).getVersionInfo();
  }
}
