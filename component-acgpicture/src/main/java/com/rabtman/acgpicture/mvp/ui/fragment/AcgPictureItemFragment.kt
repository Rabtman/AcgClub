package com.rabtman.acgpicture.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.di.AcgPictureModule
import com.rabtman.acgpicture.di.DaggerAcgPictureComponent
import com.rabtman.acgpicture.mvp.AcgPictureContract
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.presenter.AcgPictureItemPresenter
import com.rabtman.acgpicture.mvp.ui.adapter.AcgPictureItemAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants


/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_PICTURE_ANIMATE)
class AcgPictureItemFragment : BaseFragment<AcgPictureItemPresenter>(), AcgPictureContract.View {

    @BindView(R.id.rcv_animate_item)
    lateinit var rcvAcgItem: RecyclerView
    @BindView(R.id.swipe_refresh_animate)
    lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mAdapter: AcgPictureItemAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerAcgPictureComponent.builder()
                .appComponent(appComponent)
                .acgPictureModule(AcgPictureModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_fragment_animate
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: android.view.View) {
        mPresenter.getAcgPictures()
    }

    override fun initData() {
        mAdapter = AcgPictureItemAdapter(appComponent.imageLoader())
        mAdapter.setOnItemClickListener { adapter, view, position -> }

        val layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        rcvAcgItem?.layoutManager = layoutManager
        mAdapter.setOnLoadMoreListener({ mPresenter.getMoreAcgPictures() }, rcvAcgItem)
        rcvAcgItem?.adapter = mAdapter

        swipeRefresh?.setOnRefreshListener { mPresenter.getAcgPictures() }
        setSwipeRefreshLayout(swipeRefresh)
        mPresenter.getAcgPictures()
    }

    override fun showPictures(pictureItems: List<AcgPictureItem>) {
        mAdapter.setNewData(pictureItems)
    }

    override fun showMorePictures(pictureItems: List<AcgPictureItem>, canLoadMore: Boolean) {
        if (!canLoadMore) {
            mAdapter.loadMoreEnd()
        } else {
            pictureItems?.let { mAdapter.addData(it) }
            mAdapter.loadMoreComplete()
        }
    }

    override fun hideLoading() {
        swipeRefresh?.let { it ->
            if (it.isRefreshing) {
                it.isRefreshing = false
            }
        }
        super.hideLoading()
    }

    override fun onLoadMoreFail() {
        mAdapter.loadMoreFail()
    }

}