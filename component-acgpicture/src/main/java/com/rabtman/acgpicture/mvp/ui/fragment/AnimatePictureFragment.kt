package com.rabtman.acgpicture.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.R2
import com.rabtman.acgpicture.di.AnimatePictureModule
import com.rabtman.acgpicture.di.DaggerAnimatePictureComponent
import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.entity.AnimatePictureItem
import com.rabtman.acgpicture.mvp.presenter.AnimatePicturePresenter
import com.rabtman.acgpicture.mvp.ui.adapter.AnimateItemAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants


/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_PICTURE_ANIMATE)
class AnimatePictureFragment : BaseFragment<AnimatePicturePresenter>(), AnimatePictureContract.View {

    @BindView(R2.id.rcv_animate_item)
    lateinit var rcvAnimateItem: RecyclerView
    @BindView(R2.id.swipe_refresh_animate)
    lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mAdapter: AnimateItemAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerAnimatePictureComponent.builder()
                .appComponent(appComponent)
                .animatePictureModule(AnimatePictureModule(this))
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
        mPresenter.getAnimatePictures()
    }

    override fun initData() {
        mAdapter = AnimateItemAdapter(appComponent.imageLoader())
        mAdapter.setOnItemClickListener { adapter, view, position -> }

        val layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        rcvAnimateItem?.layoutManager = layoutManager
        mAdapter.setOnLoadMoreListener({ mPresenter.getMoreAnimatePictures() }, rcvAnimateItem)
        rcvAnimateItem?.adapter = mAdapter

        swipeRefresh?.setOnRefreshListener { mPresenter.getAnimatePictures() }
        setSwipeRefreshLayout(swipeRefresh)
        mPresenter.getAnimatePictures()
    }

    override fun showAnimatePictures(animatePictureItems: List<AnimatePictureItem>?) {
        mAdapter.setNewData(animatePictureItems)
    }

    override fun showMoreAnimatePictures(animatePictureItems: List<AnimatePictureItem>?, canLoadMore: Boolean) {
        if (!canLoadMore) {
            mAdapter.loadMoreEnd()
        } else {
            animatePictureItems?.let { mAdapter.addData(it) }
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