package com.rabtman.common.base.mvp;


import com.rabtman.common.bus.RxBus;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxPresenter<M extends IModel, V extends BaseView> implements BasePresenter {

  protected final String TAG = this.getClass().getSimpleName();
  protected CompositeDisposable mCompositeDisposable;

  protected M mModel;
  protected V mRootView;


  public RxPresenter(M model, V rootView) {
    this.mModel = model;
    this.mRootView = rootView;
    onStart();
  }

  public RxPresenter(V rootView) {
    this.mRootView = rootView;
    onStart();
  }

  public RxPresenter() {
    onStart();
  }


  @Override
  public void onStart() {
    //if (useEventBus())//如果要使用bus请将此方法返回true
    //EventBus.getDefault().register(this);//注册
  }

  @Override
  public void onDestroy() {
    if (mModel != null) {
      mModel.onDestory();
      this.mModel = null;
    }
    this.mRootView = null;
    unSubscribe();
  }

  /**
   * 是否使用eventBus,默认为使用(true)，
   */
  protected boolean useEventBus() {
    return true;
  }


  protected void unSubscribe() {
    if (mCompositeDisposable != null) {
      mCompositeDisposable.dispose();
    }
  }

  protected void addSubscribe(Disposable subscription) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = new CompositeDisposable();
    }
    mCompositeDisposable.add(subscription);
  }

  protected <U> void addRxBusSubscribe(Class<U> eventType, Consumer<U> act) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = new CompositeDisposable();
    }
    mCompositeDisposable.add(RxBus.getDefault().toDefaultFlowable(eventType, act));
  }

}
