package com.rabtman.acgpicture.mvp.ui.activity

import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.AcgPictureContract
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.presenter.AcgPictureItemPresenter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent

/**
 * @author Rabtman
 */
class AcgPictureItemDetailActivity : BaseActivity<AcgPictureItemPresenter>(), AcgPictureContract.View {

    override fun setupActivityComponent(appComponent: AppComponent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_activity_picture_item_detail
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPictures(picItems: List<AcgPictureItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMorePictures(picItems: List<AcgPictureItem>, canLoadMore: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMoreFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
