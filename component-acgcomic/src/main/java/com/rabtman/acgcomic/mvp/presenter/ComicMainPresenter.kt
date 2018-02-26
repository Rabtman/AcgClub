package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.ComicMainContract
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ComicMainPresenter
@Inject constructor(model: ComicMainContract.Model,
                    view: ComicMainContract.View) : BasePresenter<ComicMainContract.Model, ComicMainContract.View>(model, view) {

}