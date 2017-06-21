package com.rabtman.acgclub.mvp.model;

import com.rabtman.acgclub.mvp.contract.APicDetailContract;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.integration.IRepositoryManager;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class APicDetailModel extends BaseModel implements APicDetailContract.Model {

  @Inject
  public APicDetailModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }


}
