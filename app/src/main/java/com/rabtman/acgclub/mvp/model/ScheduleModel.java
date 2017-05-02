package com.rabtman.acgclub.mvp.model;

import com.rabtman.acgclub.mvp.contract.ScheduleContract;
import com.rabtman.common.base.mvp.BaseModel;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.integration.IRepositoryManager;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class ScheduleModel extends BaseModel implements ScheduleContract.Model {

  @Inject
  public ScheduleModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }
}
